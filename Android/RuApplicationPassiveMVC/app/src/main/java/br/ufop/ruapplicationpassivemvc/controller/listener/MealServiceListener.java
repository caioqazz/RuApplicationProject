package br.ufop.ruapplicationpassivemvc.controller.listener;

import br.ufop.ruapplicationpassivemvc.model.entity.Meal;

import java.util.List;

import retrofit2.Response;

public interface MealServiceListener {
    void onActionFailure(Throwable t, int option);

    void onActionSuccess(Response<Meal> response, int option);

    void onActionError(Response<Meal> response, int option);

    void onIndexSuccess(Response<List<Meal>> response);

    void onIndexError(Response<List<Meal>> response);
}
