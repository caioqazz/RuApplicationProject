package br.ufop.ruapplicationpassivemvc.service;

import br.ufop.ruapplicationpassivemvc.controller.listener.MealServiceListener;
import br.ufop.ruapplicationpassivemvc.model.entity.Meal;
import br.ufop.ruapplicationpassivemvc.service.api.ApiService;
import br.ufop.ruapplicationpassivemvc.service.api.RetrofitBuilder;
import br.ufop.ruapplicationpassivemvc.service.api.TokenManager;
import br.ufop.ruapplicationpassivemvc.util.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MealService {
    ApiService service;
    MealServiceListener listener;

    public MealService(MealServiceListener listener, TokenManager tokenManager) {
        this.listener = listener;
        service = RetrofitBuilder.createServiceWithAuth(ApiService.class, tokenManager);
    }

    public void update(String date, int type, int status, String reason) {
        Call<Meal> call = service.updateRefeicao(date, type, status, reason);
        call.enqueue(new Callback<Meal>() {
            @Override
            public void onResponse(Call<Meal> call, Response<Meal> response) {
                if (response.isSuccessful()) {
                    listener.onActionSuccess(response, Constants.UPDATE);
                } else {
                    listener.onActionError(response, Constants.UPDATE);
                }
            }

            @Override
            public void onFailure(Call<Meal> call, Throwable t) {
                listener.onActionFailure(t, Constants.UPDATE);
            }
        });
    }

    /*
        public void indexDaysOpened(int weekNum, int year) {
            Call<List<Day>> call = service.daysOpened(weekNum, year);
            call.enqueue(new Callback<List<Day>>() {
                @Override
                public void onResponse(Call<List<Day>> call, Response<List<Day>> response) {
                    if (response.isSuccessful()) {
                        listener.onIndexSuccess(response);
                    } else {
                        listener.onIndexError(response);
                    }
                }

                @Override
                public void onFailure(Call<List<Day>> call, Throwable t) {
                    listener.onActionFailure(t, Constants.GET);
                }
            });
        }
    */
    public void indexDaysClosed() {
        Call<List<Meal>> call = service.daysClosed();
        call.enqueue(new Callback<List<Meal>>() {
            @Override
            public void onResponse(Call<List<Meal>> call, Response<List<Meal>> response) {
                if (response.isSuccessful()) {
                    listener.onIndexSuccess(response);
                } else {
                    listener.onIndexError(response);
                }
            }

            @Override
            public void onFailure(Call<List<Meal>> call, Throwable t) {
                listener.onActionFailure(t, Constants.GET);
            }
        });
    }
}
