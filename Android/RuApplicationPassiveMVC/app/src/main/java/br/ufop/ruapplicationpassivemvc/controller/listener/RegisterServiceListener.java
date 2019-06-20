package br.ufop.ruapplicationpassivemvc.controller.listener;

import br.ufop.ruapplicationpassivemvc.model.response.AccessToken;

import retrofit2.Response;

public interface RegisterServiceListener {

    void onRegisterResponseSuccess(Response<AccessToken> response);

    void onRegisterResponseSuccessError(Response<AccessToken> response);

    void onRegisterFailure(Throwable t);
}
