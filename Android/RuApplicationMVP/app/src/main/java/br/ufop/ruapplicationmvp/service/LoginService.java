package br.ufop.ruapplicationmvp.service;

import br.ufop.ruapplicationmvp.contract.LoginContract;
import br.ufop.ruapplicationmvp.model.response.AccessToken;
import br.ufop.ruapplicationmvp.service.api.ApiService;
import br.ufop.ruapplicationmvp.service.api.RetrofitBuilder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginService implements LoginContract.Model {


    private ApiService service;

    public LoginService() {
        service = RetrofitBuilder.createService(ApiService.class);

    }

    @Override
    public void authentication(final OnFinishedListener listener, String email, String password) {
        Call<AccessToken> call = service.login(email, password);
        call.enqueue(new Callback<AccessToken>() {
            @Override
            public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {

                if (response.isSuccessful()) {
                    listener.onFinished(response.body());
                } else {
                    listener.onError(response);
                }

            }

            @Override
            public void onFailure(Call<AccessToken> call, Throwable t) {
                listener.onFailure(t);
            }
        });
    }
}
