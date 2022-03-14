package com.twosixlabs.dart.operations.status.client

import com.twosixlabs.dart.operations.status.PipelineStatus

trait PipelineStatusUpdateClient {

    /**
      * Write the status without regard for if the update is successful or not...
      *
      * shorthand for statusClient.writeStatus( status, true )
      *
      * @param status - the DART pipeline status object to be recorded
      */
    def fireAndForget( status : PipelineStatus ) : Unit

    /**
      * write the status
      *
      * @param status
      * @throws - an exception
      */
    @throws[ Exception ]
    def writeStatus( status : PipelineStatus, silent : Boolean = false ) : Unit

}

