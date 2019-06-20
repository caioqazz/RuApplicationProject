package br.ufop.ruapplicationpassivemvc.service;

import br.ufop.ruapplicationpassivemvc.controller.listener.LoginServiceListener;
import br.ufop.ruapplicationpassivemvc.model.response.AccessToken;
import br.ufop.ruapplicationpassivemvc.service.api.ApiService;
import br.ufop.ruapplicationpassivemvc.service.api.RetrofitBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginService {

    private final LoginServiceListener listener;
    private Call<AccessToken> call;
    private ApiService service;

    public LoginService(LoginServiceListener listener) {
        service = RetrofitBuilder.createService(ApiService.class);
        this.listener = listener;
    }

    public void autentication(String email, String password) {
        call = service.login(email, password);
        call.enqueue(new Callback<AccessToken>() {
            @Override
            public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {

                if (response.isSuccessful()) {
                    listener.onLoginResponseSuccess(response);
                } else {
                    if (response.code() == 422) {
                        listener.onLoginResponseError422(response);
                    }
                    if (response.code() == 401) {
                        listener.onLoginResponseError401(response);
                    }
                }

            }

            @Override
            public void onFailure(Call<AccessToken> call, Throwable t) {
                listener.onLoginFailure(t);
            }
        });
    }


}
