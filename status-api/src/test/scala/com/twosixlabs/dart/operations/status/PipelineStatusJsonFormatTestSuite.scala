package com.twosixlabs.dart.operations.status

import com.twosixlabs.dart.operations.status.PipelineStatus.ProcessorType
import com.twosixlabs.dart.test.base.StandardTestBase3x

class PipelineStatusJsonFormatTestSuite extends StandardTestBase3x {

    val format = new PipelineStatusJsonFormat

    "Pipeline Status JSON Format" should "marshal a message" in {
        val statusMsg : PipelineStatus = new PipelineStatus( "doc1", "test-app", ProcessorType.CORE, PipelineStatus.Status.SUCCESS, "DART", 1L, 2L, "hello" )

        val expected : String = """{"status":"SUCCESS","scope":"DART","message":"hello","documentId":"doc1","applicationId":"test-app","processorType":"CORE","startTime":1,"endTime":2}"""
        val actual : Option[ String ] = format.toStatusJson( statusMsg )

        actual.isDefined shouldBe true
        actual.get shouldBe expected


    }
    "Pipeline Status JSON Format" should "unmarshal a message" in {
        val statusMsg : String = """{"status":"FAILURE","scope":"TA1","message":"hello","documentId":"doc1","applicationId":"test-app","processorType":"READER","startTime":1,"endTime":2}"""

        val expected : PipelineStatus = new PipelineStatus( "doc1", "test-app", ProcessorType.READER, PipelineStatus.Status.FAILURE, "TA1", 1L, 2L, "hello" )
        val actual : Option[ PipelineStatus ] = format.fromStatusJson( statusMsg )

        actual.isDefined shouldBe true
        actual.get shouldBe expected
    }
}
