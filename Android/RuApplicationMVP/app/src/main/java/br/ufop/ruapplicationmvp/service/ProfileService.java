package br.ufop.ruapplicationmvp.service;

import br.ufop.ruapplicationmvp.contract.ProfileContract;
import br.ufop.ruapplicationmvp.model.entity.User;
import br.ufop.ruapplicationmvp.service.api.ApiService;
import br.ufop.ruapplicationmvp.service.api.RetrofitBuilder;
import br.ufop.ruapplicationmvp.service.api.TokenManager;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileService implements ProfileContract.Model {
    private ApiService service;

    public ProfileService(TokenManager tokenManager) {
        this.service = RetrofitBuilder.createServiceWithAuth(ApiService.class, tokenManager);
    }


    @Override
    public void update(OnFinishedListener onFinishedListener, String name, MultipartBody.Part photo) {
        Call<User> call = service.updateUser(photo, name);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    onFinishedListener.onFinished(response.body());
                } else {
                    onFinishedListener.onError(response.message());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                onFinishedListener.onFailure(t);
            }
        });
    }
}
