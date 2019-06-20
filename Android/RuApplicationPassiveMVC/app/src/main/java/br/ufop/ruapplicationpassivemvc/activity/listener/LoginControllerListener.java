package br.ufop.ruapplicationpassivemvc.activity.listener;

import br.ufop.ruapplicationpassivemvc.model.entity.User;

public interface LoginControllerListener {

    void onLoginSuccess(User user);

    void onLoginError(String error);
}
