package com.twosixlabs.dart.operations.status.client

import com.twosixlabs.dart.operations.status.PipelineStatus
import com.twosixlabs.dart.sql.SqlClient
import org.slf4j.{Logger, LoggerFactory}

class SqlPipelineStatusUpdateClient( client : SqlClient, tableName : String ) extends PipelineStatusUpdateClient {

    private val LOG : Logger = LoggerFactory.getLogger( getClass )

    override def fireAndForget( status : PipelineStatus ) : Unit = {
        try writeStatus( status, true )
        catch {
            case e : Throwable => {
                LOG.error( s"failed to write status update for ${status.getDocumentId} : ${status.getApplicationId}" )
                if ( LOG.isDebugEnabled ) {
                    LOG.debug( s"exception was : ${e.getClass} : ${e.getMessage} : ${e.getCause}" )
                    e.printStackTrace()
                }
            }
        }
    }

    @throws[ Exception ]
    override def writeStatus( status : PipelineStatus, silent : Boolean = false ) : Unit = {
        // cowboy code the raw sql insert statement... (╯°□°）╯︵ (\﻿ .o.)\
        val msg = if ( status.getMessage == null ) null else s"'${status.getMessage}'"
        val insertSql : String =
            s"""INSERT INTO ${tableName} (
                    document_id,
                    application_id,
                    processor_type,
                    status,
                    scope,
                    start_time,
                    end_Time,
                    message
          ) VALUES( '${status.getDocumentId}',
                    '${status.getApplicationId}',
                    '${status.getProcessorType}',
                    '${status.getStatus}',
                    '${status.getScope}',
                    ${status.getStartTime},
                    ${status.getEndTime},
                    ${msg})""".stripMargin


        val connection = client.connect()
        try client.executeInsert( insertSql, connection )
        catch {
            case e : Exception => throw e
        } finally connection.close()
    }

}
