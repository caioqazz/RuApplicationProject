package br.ufop.ruapplicationmvvm.service;

import java.util.List;

import br.ufop.ruapplicationmvvm.model.entity.Day;
import br.ufop.ruapplicationmvvm.model.entity.Meal;
import br.ufop.ruapplicationmvvm.model.entity.Week;
import br.ufop.ruapplicationmvvm.model.result.DayResult;
import br.ufop.ruapplicationmvvm.model.result.MealResult;
import br.ufop.ruapplicationmvvm.model.result.MealsResult;
import br.ufop.ruapplicationmvvm.model.result.WeekResult;
import br.ufop.ruapplicationmvvm.service.api.ApiService;
import br.ufop.ruapplicationmvvm.service.api.RetrofitBuilder;
import br.ufop.ruapplicationmvvm.service.api.TokenManager;
import br.ufop.ruapplicationmvvm.service.listener.DayListener;
import br.ufop.ruapplicationmvvm.service.listener.MealListener;
import br.ufop.ruapplicationmvvm.service.listener.WeekListener;
import br.ufop.ruapplicationmvvm.util.Status;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MealService {
    private ApiService service;

    public MealService(TokenManager tokenManager) {
        this.service = RetrofitBuilder.createServiceWithAuth(ApiService.class, tokenManager);
    }

    public void indexWeeks(WeekListener.OnFinished listener) {
        Call<List<Week>> call = service.weeks();
        call.enqueue(new Callback<List<Week>>() {
            @Override
            public void onResponse(Call<List<Week>> call, Response<List<Week>> response) {
                if (response.isSuccessful()) {
                    listener.onRequestFinished(new WeekResult(Status.SUCCESS, response.body(), null, null));
                } else {
                    listener.onRequestFinished(new WeekResult(Status.ERROR, null, response.message(), null));
                }
            }

            @Override
            public void onFailure(Call<List<Week>> call, Throwable t) {
                listener.onRequestFinished(new WeekResult(Status.FAILURE, null, null, t.getMessage()));

            }
        });

    }

    public void indexDays(int weekNum, int month, int year, DayListener.OnFinished listener) {
        Call<List<Day>> call = service.daysOpened(weekNum, month, year);
        call.enqueue(new Callback<List<Day>>() {
            @Override
            public void onResponse(Call<List<Day>> call, Response<List<Day>> response) {
                if (response.isSuccessful()) {
                    listener.onRequestFinished(new DayResult(Status.SUCCESS, response.body(), null, null));
                } else {
                    listener.onRequestFinished(new DayResult(Status.ERROR, null, response.message(), null));
                }
            }

            @Override
            public void onFailure(Call<List<Day>> call, Throwable t) {
                listener.onRequestFinished(new DayResult(Status.FAILURE, null, null, t.getMessage()));

            }
        });
    }

    public void indexDaysClosed(MealListener.OnListFinished listener) {
        Call<List<Meal>> call = service.daysClosed();
        call.enqueue(new Callback<List<Meal>>() {
            @Override
            public void onResponse(Call<List<Meal>> call, Response<List<Meal>> response) {
                if (response.isSuccessful()) {
                    listener.onRequestFinished(new MealsResult(Status.SUCCESS, response.body(), null, null));
                } else {
                    listener.onRequestFinished(new MealsResult(Status.ERROR, null, response.message(), null));
                }
            }

            @Override
            public void onFailure(Call<List<Meal>> call, Throwable t) {
                listener.onRequestFinished(new MealsResult(Status.FAILURE, null, null, t.getMessage()));

            }
        });
    }

    public void update(String date, int type, int status, String reason, MealListener.OnFinished listener) {
        Call<Meal> call = service.updateRefeicao(date, type, status, reason);
        call.enqueue(new Callback<Meal>() {

            @Override
            public void onResponse(Call<Meal> call, Response<Meal> response) {
                if (response.isSuccessful())
                    listener.onRequestFinished(new MealResult(Status.SUCCESS, response.body(), null, null));
                else
                    listener.onRequestFinished(new MealResult(Status.ERROR, null, response.message(), null));
            }

            @Override
            public void onFailure(Call<Meal> call, Throwable t) {
                listener.onRequestFinished(new MealResult(Status.FAILURE, null, null, t.getMessage()));
            }
        });
    }
}