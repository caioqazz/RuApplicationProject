package br.ufop.ruapplicationpassivemvc.service;


import android.support.annotation.NonNull;

import java.util.List;

import br.ufop.ruapplicationpassivemvc.controller.listener.DishServiceListener;
import br.ufop.ruapplicationpassivemvc.model.entity.Dish;
import br.ufop.ruapplicationpassivemvc.service.api.ApiService;
import br.ufop.ruapplicationpassivemvc.service.api.RetrofitBuilder;
import br.ufop.ruapplicationpassivemvc.service.api.TokenManager;
import br.ufop.ruapplicationpassivemvc.util.Constants;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DishService {
    DishServiceListener listener;
    ApiService service;
    Call<Dish> call;

    public DishService(DishServiceListener listener, TokenManager tokenManager) {
        this.listener = listener;
        service = RetrofitBuilder.createServiceWithAuth(ApiService.class, tokenManager);
    }

    public void index() {
        Call<List<Dish>> call = service.dishes();
        call.enqueue(new Callback<List<Dish>>() {
            @Override
            public void onResponse(Call<List<Dish>> call, Response<List<Dish>> response) {
                if (response.isSuccessful()) {
                    listener.onIndexSuccess(response);
                } else {
                    listener.onIndexError(response);
                }
            }

            @Override
            public void onFailure(Call<List<Dish>> call, Throwable t) {
                listener.onActionFailure(t, Constants.GET);
            }
        });
    }

    public void indexByType(int type) {
        Call<List<Dish>> call = service.dishes(type);
        call.enqueue(new Callback<List<Dish>>() {
            @Override
            public void onResponse(Call<List<Dish>> call, Response<List<Dish>> response) {
                if (response.isSuccessful()) {
                    listener.onIndexSuccess(response);
                } else {
                    listener.onIndexError(response);
                }
            }

            @Override
            public void onFailure(Call<List<Dish>> call, Throwable t) {
                listener.onActionFailure(t, Constants.GET);
            }
        });
    }

    public void delete(int id) {
        call = service.deletePrato(id);
        call.enqueue(new Callback<Dish>() {
            @Override
            public void onResponse(Call<Dish> call, Response<Dish> response) {
                if (response.isSuccessful()) {
                    listener.onActionSuccess(response, Constants.DELETE);
                } else {
                    listener.onActionError(response, Constants.DELETE);
                }
            }

            @Override
            public void onFailure(Call<Dish> call, Throwable t) {
                listener.onActionFailure(t, Constants.DELETE);
            }
        });
    }

    public void insert(MultipartBody.Part photo, String name, int type, String description) {

        call = service.newPrato(photo, name, description, type);
        call.enqueue(new Callback<Dish>() {
            @Override
            public void onResponse(Call<Dish> call, Response<Dish> response) {
                if (response.isSuccessful()) {
                    listener.onActionSuccess(response, Constants.INSERT);
                } else {
                    listener.onActionError(response, Constants.INSERT);
                }
            }

            @Override
            public void onFailure(Call<Dish> call, Throwable t) {
                listener.onActionFailure(t, Constants.INSERT);
            }
        });
    }


    public void update(int id, MultipartBody.Part photo, String name, int type, String description) {
        call = service.updatePrato(id, photo, type, name, description);
        call.enqueue(new Callback<Dish>() {
            @Override
            public void onResponse(Call<Dish> call, Response<Dish> response) {
                if (response.isSuccessful()) {
                    listener.onActionSuccess(response, Constants.UPDATE);
                } else {
                    listener.onActionError(response, Constants.UPDATE);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Dish> call, @NonNull Throwable t) {
                listener.onActionFailure(t, Constants.UPDATE);
            }
        });
    }
}
