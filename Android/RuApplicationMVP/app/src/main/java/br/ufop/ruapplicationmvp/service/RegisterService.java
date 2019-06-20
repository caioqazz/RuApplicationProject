package br.ufop.ruapplicationmvp.service;

import br.ufop.ruapplicationmvp.contract.RegisterContract;
import br.ufop.ruapplicationmvp.model.response.AccessToken;
import br.ufop.ruapplicationmvp.service.api.ApiService;
import br.ufop.ruapplicationmvp.service.api.RetrofitBuilder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterService implements RegisterContract.Model {

    private ApiService service;


    public RegisterService() {
        service = RetrofitBuilder.createService(ApiService.class);
    }


    @Override
    public void register(final OnFinishedListener listener, String name, String email, String cpf, String password) {
        Call<AccessToken> call = service.register(name, email, cpf, password);

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
