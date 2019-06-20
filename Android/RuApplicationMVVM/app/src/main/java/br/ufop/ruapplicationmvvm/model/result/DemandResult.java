package br.ufop.ruapplicationmvvm.model.result;

import androidx.annotation.Nullable;

import br.ufop.ruapplicationmvvm.model.entity.Demand;
import br.ufop.ruapplicationmvvm.util.Status;

public class DemandResult {

    private final Status status;

    @Nullable
    private final Demand data;
    @Nullable
    private final String error;
    @Nullable
    private final String failure;


    public DemandResult(Status status, @Nullable Demand data, @Nullable String error, @Nullable String failure) {
        this.status = status;
        this.data = data;
        this.error = error;
        this.failure = failure;
    }

    public Status getStatus() {
        return status;
    }

    @Nullable
    public Demand getData() {
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
