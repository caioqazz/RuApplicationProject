package br.ufop.ruapplicationmvp.service;

import br.ufop.ruapplicationmvp.contract.PosLoginContract;
import br.ufop.ruapplicationmvp.model.entity.User;
import br.ufop.ruapplicationmvp.service.api.ApiService;
import br.ufop.ruapplicationmvp.service.api.RetrofitBuilder;
import br.ufop.ruapplicationmvp.service.api.TokenManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PosLoginService implements PosLoginContract.Model {
    private ApiService service;

    public PosLoginService(TokenManager tokenManager) {
        this.service = RetrofitBuilder.createServiceWithAuth(ApiService.class, tokenManager);
    }

    @Override
    public void getUser(OnFinishedListener onFinishedListener) {
        Call<User> call = service.user();
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    onFinishedListener.onFinished(response.body());
                } else {
                    onFinishedListener.onError();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                onFinishedListener.onFailure();
            }
        });
    }
}
