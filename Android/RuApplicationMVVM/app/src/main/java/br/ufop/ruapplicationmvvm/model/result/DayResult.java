package br.ufop.ruapplicationmvvm.model.result;

import androidx.annotation.Nullable;

import java.util.List;

import br.ufop.ruapplicationmvvm.model.entity.Day;
import br.ufop.ruapplicationmvvm.util.Status;

public class DayResult {
    private final Status status;

    @Nullable
    private final List<Day> data;
    @Nullable
    private final String error;
    @Nullable
    private final String failure;


    public DayResult(Status status, @Nullable List<Day> data, @Nullable String error, @Nullable String failure) {
        this.status = status;
        this.data = data;
        this.error = error;
        this.failure = failure;
    }

    public Status getStatus() {
        return status;
    }

    @Nullable
    public List<Day> getData() {
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
