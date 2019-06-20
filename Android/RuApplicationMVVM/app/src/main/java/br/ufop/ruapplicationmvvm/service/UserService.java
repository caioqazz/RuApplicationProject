package br.ufop.ruapplicationmvvm.service;

import br.ufop.ruapplicationmvvm.model.response.AccessToken;
import br.ufop.ruapplicationmvvm.model.result.LoginResult;
import br.ufop.ruapplicationmvvm.model.result.RegisterResult;
import br.ufop.ruapplicationmvvm.service.api.ApiService;
import br.ufop.ruapplicationmvvm.service.api.RetrofitBuilder;
import br.ufop.ruapplicationmvvm.service.listener.LoginListener;
import br.ufop.ruapplicationmvvm.service.listener.RegisterListener;
import br.ufop.ruapplicationmvvm.util.Status;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserService {

    private ApiService service;

    public UserService() {
        service = RetrofitBuilder.createService(ApiService.class);
    }


    public void authentication(String email, String password, LoginListener.OnFinished listener) {

        Call<AccessToken> call = service.login(email, password);
        call.enqueue(new Callback<AccessToken>() {
            @Override
            public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
                if (response.isSuccessful()) {
                    listener.onRequestFinished(new LoginResult(Status.SUCCESS, response, null));
                } else {
                    listener.onRequestFinished(new LoginResult(Status.ERROR, response, null));
                }
            }

            @Override
            public void onFailure(Call<AccessToken> call, Throwable t) {
                listener.onRequestFinished(new LoginResult(Status.SUCCESS, null, t));

            }
        });

    }

    public void register(String name, String email, String cpf, String password, RegisterListener.OnFinished listener) {
        Call<AccessToken> call = service.register(name, email, cpf, password);
        call.enqueue(new Callback<AccessToken>() {
            @Override
            public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
                if (response.isSuccessful()) {
                    listener.onRequestFinished(new RegisterResult(Status.SUCCESS, response, null));
                } else {
                    listener.onRequestFinished(new RegisterResult(Status.ERROR, null, null));
                }
            }

            @Override
            public void onFailure(Call<AccessToken> call, Throwable t) {
                listener.onRequestFinished(new RegisterResult(Status.SUCCESS, null, t.getMessage()));

            }
        });
    }
}
