package br.ufop.ruapplicationpassivemvc.controller.listener;

import br.ufop.ruapplicationpassivemvc.model.entity.Dish;

import java.util.List;

import retrofit2.Response;

public interface DishServiceListener {

    void onIndexSuccess(Response<List<Dish>> response);

    void onIndexError(Response<List<Dish>> response);

    void onActionSuccess(Response<Dish> response, int option);

    void onActionError(Response<Dish> response, int option);

    void onActionFailure(Throwable t, int option);


}
