package br.ufop.ruapplicationmvvm.service;

import br.ufop.ruapplicationmvvm.model.entity.User;
import br.ufop.ruapplicationmvvm.model.result.UserResult;
import br.ufop.ruapplicationmvvm.service.api.ApiService;
import br.ufop.ruapplicationmvvm.service.api.RetrofitBuilder;
import br.ufop.ruapplicationmvvm.service.api.TokenManager;
import br.ufop.ruapplicationmvvm.service.listener.UserListener;
import br.ufop.ruapplicationmvvm.util.Status;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserAuthService {
    private ApiService service;

    public UserAuthService(TokenManager tokenManager) {
        this.service = RetrofitBuilder.createServiceWithAuth(ApiService.class, tokenManager);
    }

    public void getUser(Callback<User> listener) {
        Call<User> call = service.user();
        call.enqueue(listener);
    }

    public void update(UserListener.OnFinished listener, String name, MultipartBody.Part photo) {
        Call<User> call = service.updateUser(photo, name);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    listener.onRequestFinished(new UserResult(Status.SUCCESS, response.body(), null, null));
                } else {
                    listener.onRequestFinished(new UserResult(Status.ERROR, null, response.message(), null));
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                listener.onRequestFinished(new UserResult(Status.FAILURE, null, null, t.getMessage()));

            }
        });
    }
}
