package br.ufop.ruapplicationmvvm.model.result;

import androidx.annotation.Nullable;

import br.ufop.ruapplicationmvvm.model.entity.Classification;
import br.ufop.ruapplicationmvvm.util.Status;

public class ClassificationResult {
    private final Status status;

    @Nullable
    private final Classification data;
    @Nullable
    private final String error;
    @Nullable
    private final String failure;


    public ClassificationResult(Status status, @Nullable Classification data, @Nullable String error, @Nullable String failure) {
        this.status = status;
        this.data = data;
        this.error = error;
        this.failure = failure;
    }

    public Status getStatus() {
        return status;
    }

    @Nullable
    public Classification getData() {
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
