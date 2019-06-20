package br.ufop.ruapplicationmvvm.model.result;

import androidx.annotation.Nullable;

import br.ufop.ruapplicationmvvm.model.response.ReportDemandResponse;
import br.ufop.ruapplicationmvvm.util.Status;

public class ReportDemandtResult {
    private final Status status;

    @Nullable
    private final ReportDemandResponse data;
    @Nullable
    private final String error;
    @Nullable
    private final String failure;


    public ReportDemandtResult(Status status, @Nullable ReportDemandResponse data, @Nullable String error, @Nullable String failure) {
        this.status = status;
        this.data = data;
        this.error = error;
        this.failure = failure;
    }

    public Status getStatus() {
        return status;
    }

    @Nullable
    public ReportDemandResponse getData() {
        return data;
    }

    @Nullable
    public String getError() {
        return error;
    }

    @Nullable
    public String getFailure() {
        return failure;
    }
}
