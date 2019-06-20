package br.ufop.ruapplicationpassivemvc.controller.listener;

import br.ufop.ruapplicationpassivemvc.model.entity.Dish;
import br.ufop.ruapplicationpassivemvc.model.response.MenuResponse;

import retrofit2.Response;

public interface MenuServiceListener {

    void onIndexSuccessMenu(Response<MenuResponse> response);

    void onIndexErrorMenu(Response<MenuResponse> response);

    void onActionSuccessMenu(Response<Dish> response, int option);

    void onActionErrorMenu(Response<Dish> response, int option);

    void onActionFailureMenu(Throwable t, int option);
}

