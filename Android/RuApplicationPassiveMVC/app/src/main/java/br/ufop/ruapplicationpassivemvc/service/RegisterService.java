package br.ufop.ruapplicationpassivemvc.service;

import br.ufop.ruapplicationpassivemvc.controller.listener.RegisterServiceListener;
import br.ufop.ruapplicationpassivemvc.model.response.AccessToken;
import br.ufop.ruapplicationpassivemvc.service.api.ApiService;
import br.ufop.ruapplicationpassivemvc.service.api.RetrofitBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterService {
    private RegisterServiceListener registerServiceListener;
    private ApiService service;


    public RegisterService(RegisterServiceListener registerServiceListener) {
        this.registerServiceListener = registerServiceListener;
        service = RetrofitBuilder.createService(ApiService.class);
    }

    public void register(String name, String email, String cpf, String password) {
        Call<AccessToken> call = service.register(name, email, cpf, password);

        call.enqueue(new Callback<AccessToken>() {

            @Override
            public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
                if (response.isSuccessful()) {
                    registerServiceListener.onRegisterResponseSuccess(response);
                } else {
                    registerServiceListener.onRegisterResponseSuccessError(response);
                }
            }

            @Override
            public void onFailure(Call<AccessToken> call, Throwable t) {
                registerServiceListener.onRegisterFailure(t);
            }
        });


    }


}
