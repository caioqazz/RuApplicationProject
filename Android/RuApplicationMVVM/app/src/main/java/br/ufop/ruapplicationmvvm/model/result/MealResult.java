package br.ufop.ruapplicationmvvm.model.result;

import androidx.annotation.Nullable;

import br.ufop.ruapplicationmvvm.model.entity.Meal;
import br.ufop.ruapplicationmvvm.util.Status;
import retrofit2.Response;

public class MealResult {

    private final Status status;

    @Nullable
    private final Meal data;
    @Nullable
    private final String error;
    @Nullable
    private final String failure;

    public MealResult(Status status, @Nullable Meal data, @Nullable String error, @Nullable String failure) {
        this.status = status;
        this.data = data;
        this.error = error;
        this.failure = failure;
    }

    public Status getStatus() {
        return status;
    }

    @Nullable
    public Meal getData() {
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
