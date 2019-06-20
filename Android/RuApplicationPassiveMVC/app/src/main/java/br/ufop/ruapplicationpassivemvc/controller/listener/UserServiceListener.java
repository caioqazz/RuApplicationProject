package br.ufop.ruapplicationpassivemvc.controller.listener;

import br.ufop.ruapplicationpassivemvc.model.entity.User;

import retrofit2.Response;

public interface UserServiceListener {

    void onActionSuccess(Response<User> response, int option);
    void onActionError(Response<User> response, int option);
    void onActionFailure(Throwable t, int option);
}
