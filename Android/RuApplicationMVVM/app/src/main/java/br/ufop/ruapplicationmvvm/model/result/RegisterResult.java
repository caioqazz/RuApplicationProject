package br.ufop.ruapplicationmvvm.model.result;

import androidx.annotation.Nullable;

import br.ufop.ruapplicationmvvm.model.response.AccessToken;
import br.ufop.ruapplicationmvvm.util.Status;
import retrofit2.Response;

public class RegisterResult {

    private final Status status;

    @Nullable
    private final Response<AccessToken> data;
    @Nullable
    private final String failure;

    public RegisterResult(Status status, @Nullable Response<AccessToken> data, @Nullable String failure) {
        this.status = status;
        this.data = data;
        this.failure = failure;
    }

    public Status getStatus() {
        return status;
    }

    @Nullable
    public Response<AccessToken> getData() {
        return data;
    }

    @Nullable
    public String getFailure() {
        return failure;
    }
}
