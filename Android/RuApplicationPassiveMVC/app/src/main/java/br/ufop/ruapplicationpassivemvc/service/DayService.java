package br.ufop.ruapplicationpassivemvc.service;

import br.ufop.ruapplicationpassivemvc.controller.listener.DayServiceListener;
import br.ufop.ruapplicationpassivemvc.model.entity.Day;
import br.ufop.ruapplicationpassivemvc.service.api.ApiService;
import br.ufop.ruapplicationpassivemvc.service.api.RetrofitBuilder;
import br.ufop.ruapplicationpassivemvc.service.api.TokenManager;
import br.ufop.ruapplicationpassivemvc.util.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DayService {

    DayServiceListener listener;
    ApiService service;

    public DayService(DayServiceListener listener, TokenManager tokenManager) {
        this.listener = listener;
        service = RetrofitBuilder.createServiceWithAuth(ApiService.class, tokenManager);
    }

    public void mealDayIndex(int weekNum, int month, int year) {
        Call<List<Day>> call = service.daysOpened(weekNum, month, year);
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

    public void demandDayIndex() {
        Call<List<Day>> call = service.demandDays();
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

}
