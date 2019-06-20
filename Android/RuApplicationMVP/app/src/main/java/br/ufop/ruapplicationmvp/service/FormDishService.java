package br.ufop.ruapplicationmvp.service;

import androidx.annotation.NonNull;

import br.ufop.ruapplicationmvp.contract.FormDishContract;
import br.ufop.ruapplicationmvp.model.entity.Dish;
import br.ufop.ruapplicationmvp.service.api.ApiService;
import br.ufop.ruapplicationmvp.service.api.RetrofitBuilder;
import br.ufop.ruapplicationmvp.service.api.TokenManager;
import br.ufop.ruapplicationmvp.util.Constants;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormDishService implements FormDishContract.Model {
    private ApiService service;

    public FormDishService(TokenManager tokenManager) {
        service = RetrofitBuilder.createServiceWithAuth(ApiService.class, tokenManager);
    }

    @Override
    public void update(OnFinishedListener onFinishedListener, MultipartBody.Part photo, int id, String name, int type, String description) {
        Call<Dish> call = service.updatePrato(id, photo, type, name, description);
        call.enqueue(new Callback<Dish>() {
            @Override
            public void onResponse(Call<Dish> call, Response<Dish> response) {
                if (response.isSuccessful()) {
                    onFinishedListener.onFinished(response.body(), Constants.UPDATE);
                } else {
                    onFinishedListener.onError(response.message(), Constants.UPDATE);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Dish> call, @NonNull Throwable t) {
                onFinishedListener.onFailure(t.getMessage(), Constants.UPDATE);
            }
        });
    }

    @Override
    public void insert(OnFinishedListener onFinishedListener, MultipartBody.Part photo, String name, int type, String description) {
        Call<Dish> call = service.newPrato(photo, name, description, type);
        call.enqueue(new Callback<Dish>() {
            @Override
            public void onResponse(Call<Dish> call, Response<Dish> response) {
                if (response.isSuccessful()) {
                    onFinishedListener.onFinished(response.body(), Constants.UPDATE);
                } else {
                    onFinishedListener.onError(response.message(), Constants.UPDATE);
                }
            }

            @Override
            public void onFailure(Call<Dish> call, Throwable t) {
                onFinishedListener.onFailure(t.getMessage(), Constants.UPDATE);
            }
        });
    }
}
