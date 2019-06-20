package br.ufop.ruapplicationmvvm.model.result;

import androidx.annotation.Nullable;

import java.util.List;

import br.ufop.ruapplicationmvvm.model.entity.Week;
import br.ufop.ruapplicationmvvm.util.Status;

public class WeekResult {
    private final Status status;

    @Nullable
    private final List<Week> data;
    @Nullable
    private final String error;
    @Nullable
    private final String failure;


    public WeekResult(Status status, @Nullable List<Week> data, @Nullable String error, @Nullable String failure) {
        this.status = status;
        this.data = data;
        this.error = error;
        this.failure = failure;
    }

    public Status getStatus() {
        return status;
    }

    @Nullable
    public List<Week> getData() {
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

