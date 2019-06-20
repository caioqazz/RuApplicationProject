package br.ufop.ruapplicationpassivemvc.controller.listener;

import br.ufop.ruapplicationpassivemvc.model.response.AccessToken;

import retrofit2.Response;

public interface LoginServiceListener {

    void onLoginResponseSuccess(Response<AccessToken> response);

    void onLoginResponseError422(Response<AccessToken> response);

    void onLoginResponseError401(Response<AccessToken> response);

    void onLoginFailure(Throwable t);

}
