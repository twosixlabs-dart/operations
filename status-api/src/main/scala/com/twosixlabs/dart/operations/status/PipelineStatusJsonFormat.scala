package com.twosixlabs.dart.operations.status

import com.fasterxml.jackson.core.JsonProcessingException
import com.twosixlabs.dart.json.JsonFormat
import org.slf4j.{Logger, LoggerFactory}

class PipelineStatusJsonFormat extends JsonFormat {

    private val LOG : Logger = LoggerFactory.getLogger( getClass )

    def toStatusJson( status : PipelineStatus ) : Option[ String ] = {
        try Some( objectMapper.writeValueAsString( status ) )
        catch {
            case jpe : JsonProcessingException => {
                LOG.error( s"PipelineStatus json exception - unable to convert message to json: ${jpe.getMessage} : ${jpe.getCause}" )
                jpe.printStackTrace()
                None
            }
        }
    }

    def fromStatusJson( status : String ) : Option[ PipelineStatus ] = {
        try Some( objectMapper.readValue( status, classOf[ PipelineStatus ] ) )
        catch {
            case jpe : JsonProcessingException => {
                LOG.error( s"PipelineStatus json exception - unable to read json value : ${jpe.getMessage} : ${jpe.getCause}" )
                LOG.error( s"failed json was : ${status}" )
                jpe.printStackTrace()
                None
            }
        }

    }

}
