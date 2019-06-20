package br.ufop.ruapplicationmvvm.model.result;

import androidx.annotation.Nullable;

import java.util.List;

import br.ufop.ruapplicationmvvm.model.entity.Meal;
import br.ufop.ruapplicationmvvm.util.Status;

public class MealsResult {
    private final Status status;

    @Nullable
    private final List<Meal> data;
    @Nullable
    private final String error;
    @Nullable
    private final String failure;


    public MealsResult(Status status, @Nullable List<Meal> data, @Nullable String error, @Nullable String failure) {
        this.status = status;
        this.data = data;
        this.error = error;
        this.failure = failure;
    }

    public Status getStatus() {
        return status;
    }

    @Nullable
    public List<Meal> getData() {
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
