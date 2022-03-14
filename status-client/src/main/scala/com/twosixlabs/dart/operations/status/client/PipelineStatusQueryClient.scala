package com.twosixlabs.dart.operations.status.client

import com.twosixlabs.dart.operations.status.PipelineStatus

trait PipelineStatusQueryClient {

    //TODO - @john -> fill this interface with types of queries you want and then i'll implement them...

    def historyForDoc( documentId : String ) : Seq[ PipelineStatus ]

    def all( ) : Seq[ PipelineStatus ]

}
