package com.twosixlabs.dart.operations.status.client

import java.sql.ResultSet

import com.twosixlabs.dart.operations.status.PipelineStatus
import com.twosixlabs.dart.operations.status.PipelineStatus.{ProcessorType, Status}
import com.twosixlabs.dart.sql.SqlClient
import org.slf4j.{Logger, LoggerFactory}

import scala.collection.mutable.ListBuffer

class SqlPipelineStatusQueryClient( sqlClient : SqlClient, tableName : String ) extends PipelineStatusQueryClient {

    private val LOG : Logger = LoggerFactory.getLogger( getClass )

    override def historyForDoc( documentId : String ) : Seq[ PipelineStatus ] = {
        val selectSql : String = s"""SELECT * FROM ${tableName} WHERE document_id = '${documentId}'"""
        val connection = sqlClient.connect()
        try {
            val results = sqlClient.executeQuery( selectSql, connection )
            val buffer : ListBuffer[ PipelineStatus ] = new ListBuffer[ PipelineStatus ]

            while ( results.next() ) buffer.append( rowToStatus( results ) )
            buffer.toList // make immutable
        } catch {
            case e : Throwable => {
                LOG.error( s"failure retrieving statuses for document : ${documentId} : ${e.getCause} : ${e.getMessage} : ${e.getCause}" )
                e.printStackTrace()
                throw e
            }
        } finally connection.close()
    }

    override def all( ) : Seq[ PipelineStatus ] = ???

    private def rowToStatus( results : ResultSet ) : PipelineStatus = {
        val docId = results.getString( "document_id" )
        val appId = results.getString( "application_id" )
        val processorType = ProcessorType.valueOf( results.getString( "processor_type" ).toUpperCase )
        val status = Status.valueOf( results.getString( "status" ).toUpperCase )
        val scope = results.getString( "scope" )
        val start = results.getLong( "start_time" )
        val end = results.getLong( "end_time" )
        val message = results.getString( "message" )
        new PipelineStatus( docId, appId, processorType, status, scope, start, end, message )
    }
}
