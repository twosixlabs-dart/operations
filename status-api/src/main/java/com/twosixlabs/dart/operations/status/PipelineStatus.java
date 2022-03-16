package com.twosixlabs.dart.operations.status;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Objects;

public class PipelineStatus implements Serializable {

    private static final long serialVersionUID = 1L;

    public enum Status {
        SUCCESS, FAILURE
    }

    public enum ProcessorType {
        CORE, ANNOTATOR, NLP_PROCESSOR, READER, AGGREGATOR
    }

    private final String documentId;
    private final String applicationId;
    private final Status status;
    private final ProcessorType processorType;
    private final String scope;
    private final long startTime;
    private final long endTime;
    private final String message;

    @JsonCreator( mode = JsonCreator.Mode.PROPERTIES )
    public PipelineStatus( @JsonProperty( "document_id" ) String documentId,
                           @JsonProperty( "application_id" ) String applicationId,
                           @JsonProperty( "processor_type" ) PipelineStatus.ProcessorType processorType,
                           @JsonProperty( "status" ) PipelineStatus.Status status,
                           @JsonProperty( "scope" ) String scope,
                           @JsonProperty( "start_time" ) long start,
                           @JsonProperty( "end_time" ) long stop,
                           @JsonProperty( "message" ) String message ) {
        this.documentId = documentId;
        this.applicationId = applicationId;
        this.processorType = processorType;
        this.status = status;
        this.scope = scope;
        this.startTime = start;
        this.endTime = stop;
        this.message = message;
    }

    public String getDocumentId() {
        return documentId;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public Status getStatus() {
        return status;
    }

    public ProcessorType getProcessorType() {
        return processorType;
    }

    public String getScope() {
        return scope;
    }

    public long getStartTime() {
        return startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) {
            return true;
        }
        if ( o == null || getClass() != o.getClass() ) {
            return false;
        }
        PipelineStatus that = (PipelineStatus) o;
        return startTime == that.startTime &&
                endTime == that.endTime &&
                documentId.equals( that.documentId ) &&
                applicationId.equals( that.applicationId ) &&
                status == that.status &&
                processorType == that.processorType &&
                scope.equals( that.scope ) &&
                Objects.equals( message, that.message );
    }

    @Override
    public int hashCode() {
        return Objects.hash( documentId, applicationId, status, processorType, scope, startTime, endTime, message );
    }
}