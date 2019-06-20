package br.ufop.ruapplicationmvvm.model.result;

import androidx.annotation.Nullable;

import br.ufop.ruapplicationmvvm.model.response.MenuResponse;
import br.ufop.ruapplicationmvvm.util.Status;

public class MenuResult {
    private final Status status;

    @Nullable
    private final MenuResponse data;
    @Nullable
    private final String error;
    @Nullable
    private final String failure;


    public MenuResult(Status status, @Nullable MenuResponse data, @Nullable String error, @Nullable String failure) {
        this.status = status;
        this.data = data;
        this.error = error;
        this.failure = failure;
    }

    public Status getStatus() {
        return status;
    }

    @Nullable
    public MenuResponse getData() {
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
