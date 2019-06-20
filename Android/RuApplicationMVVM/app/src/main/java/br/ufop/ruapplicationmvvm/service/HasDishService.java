package br.ufop.ruapplicationmvvm.service;

import br.ufop.ruapplicationmvvm.model.entity.Dish;
import br.ufop.ruapplicationmvvm.model.result.DishResult;
import br.ufop.ruapplicationmvvm.service.api.ApiService;
import br.ufop.ruapplicationmvvm.service.api.RetrofitBuilder;
import br.ufop.ruapplicationmvvm.service.api.TokenManager;
import br.ufop.ruapplicationmvvm.service.listener.DishListener;
import br.ufop.ruapplicationmvvm.util.Status;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HasDishService {
    private ApiService service;

    public HasDishService(TokenManager tokenManager) {
        service = RetrofitBuilder.createServiceWithAuth(ApiService.class, tokenManager);
    }

    public void insert(DishListener.OnFinished listener, String date, int type, int dishId, int dishType) {
        Call<Dish> call = service.addPratoNoCardapio(date, type, dishId, dishType);
        call.enqueue(new Callback<Dish>() {
            @Override
            public void onResponse(Call<Dish> call, Response<Dish> response) {
                if (response.isSuccessful()) {
                    listener.onRequestFinished(new DishResult(Status.SUCCESS, response.body(), null, null));
                } else {
                    listener.onRequestFinished(new DishResult(Status.ERROR, null, response.message(), null));
                }
            }

            @Override
            public void onFailure(Call<Dish> call, Throwable t) {
                listener.onRequestFinished(new DishResult(Status.FAILURE, null, null, t.getMessage()));

            }
        });
    }
    public void update(DishListener.OnFinished listener, String date, int type, int dishId, int dishType) {
        Call<Dish> call = service.addPratoNoCardapio(date, type, dishId, dishType);
        call.enqueue(new Callback<Dish>() {
            @Override
            public void onResponse(Call<Dish> call, Response<Dish> response) {
                if (response.isSuccessful()) {
                    listener.onRequestFinished(new DishResult(Status.SUCCESS, response.body(), null, null));
                } else {
                    listener.onRequestFinished(new DishResult(Status.ERROR, null, response.message(), null));
                }
            }

            @Override
            public void onFailure(Call<Dish> call, Throwable t) {
                listener.onRequestFinished(new DishResult(Status.FAILURE, null, null, t.getMessage()));

            }
        });
    }
}
