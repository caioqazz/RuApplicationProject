package br.ufop.ruapplicationmvvm.model.result;

import androidx.annotation.Nullable;

import java.util.List;

import br.ufop.ruapplicationmvvm.model.entity.Feedback;
import br.ufop.ruapplicationmvvm.util.Status;

public class FeedbacksResult {
    private final Status status;

    @Nullable
    private final List<Feedback> data;
    @Nullable
    private final String error;
    @Nullable
    private final String failure;


    public FeedbacksResult(Status status, @Nullable List<Feedback> data, @Nullable String error, @Nullable String failure) {
        this.status = status;
        this.data = data;
        this.error = error;
        this.failure = failure;
    }

    public Status getStatus() {
        return status;
    }

    @Nullable
    public List<Feedback> getData() {
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
