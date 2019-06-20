package br.ufop.ruapplicationpassivemvc.service;

import br.ufop.ruapplicationpassivemvc.controller.listener.MenuServiceListener;
import br.ufop.ruapplicationpassivemvc.model.entity.Dish;
import br.ufop.ruapplicationpassivemvc.model.response.MenuResponse;
import br.ufop.ruapplicationpassivemvc.service.api.ApiService;
import br.ufop.ruapplicationpassivemvc.service.api.RetrofitBuilder;
import br.ufop.ruapplicationpassivemvc.service.api.TokenManager;
import br.ufop.ruapplicationpassivemvc.util.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuService {
    MenuServiceListener listener;
    ApiService service;

    public MenuService(MenuServiceListener listener, TokenManager tokenManager) {
        this.listener = listener;
        service = RetrofitBuilder.createServiceWithAuth(ApiService.class, tokenManager);
    }

    public void getMenu(int type, String date) {
        Call<MenuResponse> call = service.menus(type, date);
        call.enqueue(new Callback<MenuResponse>() {
            @Override
            public void onResponse(Call<MenuResponse> call, Response<MenuResponse> response) {
                if (response.isSuccessful()) {
                    listener.onIndexSuccessMenu(response);
                } else {
                    listener.onIndexErrorMenu(response);
                }
            }

            @Override
            public void onFailure(Call<MenuResponse> call, Throwable t) {
                listener.onActionFailureMenu(t, Constants.FAILURE);
            }
        });
    }

    public void insertDish(String date, int type, int dishId, int dishType) {
        Call<Dish> call = service.addPratoNoCardapio(date, type, dishId, dishType);
        call.enqueue(new Callback<Dish>() {
            @Override
            public void onResponse(Call<Dish> call, Response<Dish> response) {
                if (response.isSuccessful()) {
                    listener.onActionSuccessMenu(response, Constants.INSERT);
                } else {
                    listener.onActionErrorMenu(response, Constants.INSERT);
                }
            }

            @Override
            public void onFailure(Call<Dish> call, Throwable t) {
                listener.onActionFailureMenu(t, Constants.INSERT);
            }
        });

    }

    public void updateDish(String date, int type, int dishId, int dishType) {
        Call<Dish> call = service.updatePratoNoCardapio(date, type, dishId, dishType);
        call.enqueue(new Callback<Dish>() {
            @Override
            public void onResponse(Call<Dish> call, Response<Dish> response) {
                if (response.isSuccessful()) {
                    listener.onActionSuccessMenu(response, Constants.INSERT);
                } else {
                    listener.onActionErrorMenu(response, Constants.INSERT);
                }
            }

            @Override
            public void onFailure(Call<Dish> call, Throwable t) {
                listener.onActionFailureMenu(t, Constants.INSERT);
            }
        });
    }


}
