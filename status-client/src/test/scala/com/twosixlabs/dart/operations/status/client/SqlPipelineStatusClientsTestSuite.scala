package com.twosixlabs.dart.operations.status.client

import com.twosixlabs.dart.operations.status.PipelineStatus
import com.twosixlabs.dart.operations.status.PipelineStatus.{ProcessorType, Status}
import com.twosixlabs.dart.sql.SqlClient
import com.twosixlabs.dart.test.base.{ScalaTestBase, StandardTestBase3x}

class SqlPipelineStatusClientsTestSuite extends StandardTestBase3x {

    private val sqlClient : SqlClient = SqlClient.newClient( "h2", "update_client_test", null, -1, None, None )

    "SQL Pipeline Status Clients" should "write and retrieve a single status message" in {
        val table : String = "insert_single_test"
        createTable( table )

        val updateClient : PipelineStatusUpdateClient = new SqlPipelineStatusUpdateClient( sqlClient, table )
        val queryClient : PipelineStatusQueryClient = new SqlPipelineStatusQueryClient( sqlClient, table )

        val status : PipelineStatus = new PipelineStatus( "doc1", "app1", ProcessorType.ANNOTATOR, Status.SUCCESS, "DART", 1L, 2L, null )
        updateClient.writeStatus( status )

        val results : Seq[ PipelineStatus ] = queryClient.historyForDoc( status.getDocumentId )

        results.size shouldBe 1
        results.head shouldBe status
    }

    "SQL Pipeline Status Clients" should "write and retrieve multiple messages by document id" in {
        val table : String = "get_doc_history"
        createTable( table )

        val updateClient : PipelineStatusUpdateClient = new SqlPipelineStatusUpdateClient( sqlClient, table )
        val queryClient : PipelineStatusQueryClient = new SqlPipelineStatusQueryClient( sqlClient, table )

        val statusOne : PipelineStatus = new PipelineStatus( "doc1", "app1", ProcessorType.ANNOTATOR, Status.SUCCESS, "DART", 1L, 2L, null )
        val statusTwo : PipelineStatus = new PipelineStatus( "doc1", "app2", ProcessorType.CORE, Status.SUCCESS, "DART", 3L, 4L, null )
        updateClient.writeStatus( statusOne )
        updateClient.writeStatus( statusTwo )

        val results : Seq[ PipelineStatus ] = queryClient.historyForDoc( statusOne.getDocumentId )

        results.size shouldBe 2
        results.head shouldBe statusOne
        results( 1 ) shouldBe statusTwo
    }

    "SQL Pipeline Status Clients" should "fire and forget should ignore any errors" in {
        val table = "does_not_exist"
        val updateClient : PipelineStatusUpdateClient = new SqlPipelineStatusUpdateClient( sqlClient, table )
        val statusOne : PipelineStatus = new PipelineStatus( "doc1", "app1", ProcessorType.ANNOTATOR, Status.SUCCESS, "DART", 1L, 2L, null )
        updateClient.fireAndForget( statusOne ) // the table is missing but we should ignore this
        succeed
    }

    private def createTable( name : String ) : Unit = {
        val tableSql =
            s"""CREATE TABLE ${name}(
               |    id SERIAL PRIMARY KEY,
               |    document_id TEXT NOT NULL,
               |    application_id TEXT NOT NULL,
               |    processor_type TEXT NOT NULL,
               |    status TEXT NOT NULL,
               |    scope TEXT NOT NULL,
               |    start_time BIGINT NOT NULL,
               |    end_Time BIGINT NOT NULL,
               |    message TEXT
               |);""".stripMargin

        val connection = sqlClient.connect()
        sqlClient.executeUpdate( tableSql, connection )
    }


}
