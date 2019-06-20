package br.ufop.ruapplicationmvvm.model.result;

import androidx.annotation.Nullable;

import br.ufop.ruapplicationmvvm.model.response.AccessToken;
import br.ufop.ruapplicationmvvm.util.Status;
import retrofit2.Response;

public class LoginResult {

    private final Status status;

    @Nullable
    private final Response<AccessToken> data;

    @Nullable
    private final Throwable failure;

    public LoginResult(Status status, @Nullable Response<AccessToken> data, @Nullable Throwable failure) {
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
    public Throwable getFailure() {
        return failure;
    }
}
