package br.ufop.ruapplicationmvp.service;

import java.util.List;

import br.ufop.ruapplicationmvp.contract.FormMenuContract;
import br.ufop.ruapplicationmvp.model.entity.Dish;
import br.ufop.ruapplicationmvp.service.api.ApiService;
import br.ufop.ruapplicationmvp.service.api.RetrofitBuilder;
import br.ufop.ruapplicationmvp.service.api.TokenManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormMenuService implements FormMenuContract.Model {
    private ApiService service;

    public FormMenuService(TokenManager tokenManager) {
        service = RetrofitBuilder.createServiceWithAuth(ApiService.class, tokenManager);
    }

    @Override
    public void insert(OnFinishedListener onFinishedListener, String date, int type, int dishId, int dishType) {
        Call<Dish> call = service.addPratoNoCardapio(date, type, dishId, dishType);
        call.enqueue(new Callback<Dish>() {
            @Override
            public void onResponse(Call<Dish> call, Response<Dish> response) {
                if (response.isSuccessful()) {
                    onFinishedListener.onFinished(response.body());
                } else {
                    onFinishedListener.onError(response.message());
                }
            }

            @Override
            public void onFailure(Call<Dish> call, Throwable t) {
                onFinishedListener.onFailure(t.getMessage());
            }
        });
    }

    @Override
    public void update(OnFinishedListener onFinishedListener, String date, int type, int dishId, int dishType) {
        Call<Dish> call = service.updatePratoNoCardapio(date, type, dishId, dishType);
        call.enqueue(new Callback<Dish>() {
            @Override
            public void onResponse(Call<Dish> call, Response<Dish> response) {
                if (response.isSuccessful()) {
                    onFinishedListener.onFinished(response.body());
                } else {
                    onFinishedListener.onError(response.message());
                }
            }

            @Override
            public void onFailure(Call<Dish> call, Throwable t) {
                onFinishedListener.onFailure(t.getMessage());
            }
        });
    }

    @Override
    public void indexByType(OnFinishedListener onFinishedListener, int type) {
        Call<List<Dish>> call = service.dishes(type);
        call.enqueue(new Callback<List<Dish>>() {
            @Override
            public void onResponse(Call<List<Dish>> call, Response<List<Dish>> response) {
                if (response.isSuccessful()) {
                    onFinishedListener.onFinished(response.body());
                } else {
                    onFinishedListener.onError(response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Dish>> call, Throwable t) {
                onFinishedListener.onFailure(t.getMessage());
            }
        });
    }
}
