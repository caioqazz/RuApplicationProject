package br.ufop.ruapplicationmvp.service;

import br.ufop.ruapplicationmvp.contract.DetailDishContract;
import br.ufop.ruapplicationmvp.model.entity.Classification;
import br.ufop.ruapplicationmvp.model.entity.Dish;
import br.ufop.ruapplicationmvp.service.api.ApiService;
import br.ufop.ruapplicationmvp.service.api.RetrofitBuilder;
import br.ufop.ruapplicationmvp.service.api.TokenManager;
import br.ufop.ruapplicationmvp.util.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailDishService implements DetailDishContract.Model {

    private ApiService service;

    public DetailDishService(TokenManager tokenManager) {
        service = RetrofitBuilder.createServiceWithAuth(ApiService.class, tokenManager);
    }

    @Override
    public void delete(OnFinishedListener onFinishedListener, int id) {
        Call<Dish> call = service.deletePrato(id);
        call.enqueue(new Callback<Dish>() {
            @Override
            public void onResponse(Call<Dish> call, Response<Dish> response) {
                if (response.isSuccessful()) {
                    onFinishedListener.onFinished(Constants.DELETE);
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

    @Override
    public void showClassification(OnFinishedListener onFinishedListener, int id) {
        Call<Classification> call = service.showClassification(id);
        call.enqueue(new Callback<Classification>() {
            @Override
            public void onResponse(Call<Classification> call, Response<Classification> response) {
                if (response.isSuccessful()) {
                    onFinishedListener.onFinished(response.body());
                } else {
                    onFinishedListener.onError(response.message(), Constants.SHOW);
                }
            }

            @Override
            public void onFailure(Call<Classification> call, Throwable t) {
                onFinishedListener.onFailure(t.getMessage(), Constants.SHOW);
            }
        });
    }

    @Override
    public void updateClassification(OnFinishedListener onFinishedListener, int dish, float rate, boolean option, String comment) {
        int optionInt = 1;
        if (option)
            optionInt = 0;

        Call<Classification> call = service.addClassification(dish, rate, optionInt, comment);
        call.enqueue(new Callback<Classification>() {
            @Override
            public void onResponse(Call<Classification> call, Response<Classification> response) {
                if (response.isSuccessful()) {
                    onFinishedListener.onFinished(Constants.UPDATE);
                } else {
                    onFinishedListener.onError(response.message(), Constants.UPDATE);
                }
            }

            @Override
            public void onFailure(Call<Classification> call, Throwable t) {
                onFinishedListener.onFailure(t.getMessage(), Constants.UPDATE);
            }
        });
    }
}
