package br.ufop.ruapplicationmvvm.model.result;

import androidx.annotation.Nullable;

import br.ufop.ruapplicationmvvm.model.entity.User;
import br.ufop.ruapplicationmvvm.util.Status;

public class UserResult {
    private final Status status;

    @Nullable
    private final User data;
    @Nullable
    private final String error;
    @Nullable
    private final String failure;


    public UserResult(Status status, @Nullable User data, @Nullable String error, @Nullable String failure) {
        this.status = status;
        this.data = data;
        this.error = error;
        this.failure = failure;
    }

    public Status getStatus() {
        return status;
    }

    @Nullable
    public User getData() {
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
