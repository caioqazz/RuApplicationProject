package br.ufop.ruapplicationmvvm.service;

import java.util.List;

import br.ufop.ruapplicationmvvm.model.entity.Dish;
import br.ufop.ruapplicationmvvm.model.result.DishResult;
import br.ufop.ruapplicationmvvm.model.result.DishesResult;
import br.ufop.ruapplicationmvvm.service.api.ApiService;
import br.ufop.ruapplicationmvvm.service.api.RetrofitBuilder;
import br.ufop.ruapplicationmvvm.service.api.TokenManager;
import br.ufop.ruapplicationmvvm.service.listener.DishListener;
import br.ufop.ruapplicationmvvm.util.Status;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DishService {

    private ApiService service;

    public DishService(TokenManager tokenManager) {
        service = RetrofitBuilder.createServiceWithAuth(ApiService.class, tokenManager);
    }

    public void index(int type, DishListener.OnListFinished listener) {
        Call<List<Dish>> call = service.dishes(type);
        call.enqueue(new Callback<List<Dish>>() {
            @Override
            public void onResponse(Call<List<Dish>> call, Response<List<Dish>> response) {
                if (response.isSuccessful()) {
                    listener.onRequestFinished(new DishesResult(Status.SUCCESS, response.body(), null, null));
                } else {
                    listener.onRequestFinished(new DishesResult(Status.ERROR, null, response.message(), null));
                }
            }

            @Override
            public void onFailure(Call<List<Dish>> call, Throwable t) {
                listener.onRequestFinished(new DishesResult(Status.FAILURE, null, null, t.getMessage()));

            }
        });
    }

    public void delete(int id, DishListener.OnFinished listener) {
        Call<Dish> call = service.deletePrato(id);
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

    public void update(MultipartBody.Part photo, int id, String name, int type, String description, DishListener.OnFinished listener) {
        Call<Dish> call = service.updatePrato(id, photo, type, name, description);
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

    public void insert(MultipartBody.Part photo, String name, int type, String description, DishListener.OnFinished listener) {
        Call<Dish> call = service.newPrato(photo, name, description, type);
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

    public void index(DishListener.OnListFinished listener) {
        Call<List<Dish>> call = service.dishes();
        call.enqueue(new Callback<List<Dish>>() {
            @Override
            public void onResponse(Call<List<Dish>> call, Response<List<Dish>> response) {
                if (response.isSuccessful()) {
                    listener.onRequestFinished(new DishesResult(Status.SUCCESS, response.body(), null, null));
                } else {
                    listener.onRequestFinished(new DishesResult(Status.ERROR, null, response.message(), null));
                }
            }

            @Override
            public void onFailure(Call<List<Dish>> call, Throwable t) {
                listener.onRequestFinished(new DishesResult(Status.FAILURE, null, null, t.getMessage()));

            }
        });
    }
}
