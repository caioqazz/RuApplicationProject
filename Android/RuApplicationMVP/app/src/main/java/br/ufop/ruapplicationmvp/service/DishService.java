package br.ufop.ruapplicationmvp.service;

import java.util.List;

import br.ufop.ruapplicationmvp.contract.DetailDishContract;
import br.ufop.ruapplicationmvp.contract.DishContract;
import br.ufop.ruapplicationmvp.model.entity.Dish;
import br.ufop.ruapplicationmvp.service.api.ApiService;
import br.ufop.ruapplicationmvp.service.api.RetrofitBuilder;
import br.ufop.ruapplicationmvp.service.api.TokenManager;
import br.ufop.ruapplicationmvp.util.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DishService implements DishContract.Model {

    private ApiService service;

    public DishService(TokenManager tokenManager) {
        service = RetrofitBuilder.createServiceWithAuth(ApiService.class, tokenManager);
    }

    @Override
    public void index(OnFinishedListener onFinishedListener, int type) {
        Call<List<Dish>> call = service.dishes(type);
        call.enqueue(new Callback<List<Dish>>() {
            @Override
            public void onResponse(Call<List<Dish>> call, Response<List<Dish>> response) {
                if (response.isSuccessful()) {
                    onFinishedListener.onFinished(response.body());
                } else {
                    onFinishedListener.onError(response.message(), Constants.GET);
                }
            }

            @Override
            public void onFailure(Call<List<Dish>> call, Throwable t) {
                onFinishedListener.onFailure(t.getMessage(), Constants.GET);
            }
        });
    }

    @Override
    public void delete(OnFinishedListener onFinishedListener, int id) {
        Call<Dish> call = service.deletePrato(id);
        call.enqueue(new Callback<Dish>() {
            @Override
            public void onResponse(Call<Dish> call, Response<Dish> response) {
                if (response.isSuccessful()) {
                    onFinishedListener.onFinishedDelete();
                } else {
                    onFinishedListener.onError(response.message(), Constants.DELETE);
                }
            }

            @Override
            public void onFailure(Call<Dish> call, Throwable t) {
                onFinishedListener.onFailure(t.getMessage(), Constants.DELETE);
            }
        });
    }
}
