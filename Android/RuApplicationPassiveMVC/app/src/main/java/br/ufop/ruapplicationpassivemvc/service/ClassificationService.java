package br.ufop.ruapplicationpassivemvc.service;

import android.util.Log;

import br.ufop.ruapplicationpassivemvc.controller.listener.ClassificationServiceListener;
import br.ufop.ruapplicationpassivemvc.model.entity.Classification;
import br.ufop.ruapplicationpassivemvc.service.api.ApiService;
import br.ufop.ruapplicationpassivemvc.service.api.RetrofitBuilder;
import br.ufop.ruapplicationpassivemvc.service.api.TokenManager;
import br.ufop.ruapplicationpassivemvc.util.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClassificationService {

    ClassificationServiceListener listener;
    ApiService service;
    private String Tag = "class";

    public ClassificationService(ClassificationServiceListener listener, TokenManager tokenManager) {
        this.listener = listener;
        service = RetrofitBuilder.createServiceWithAuth(ApiService.class, tokenManager);
    }

    public void store(int dish, float rate, boolean option, String comment) {
        int optionInt = 1;
        if (option) {
            optionInt = 0;
        }
        Call<Classification> call = service.addClassification(dish, rate, optionInt, comment);
        call.enqueue(new Callback<Classification>() {
            @Override
            public void onResponse(Call<Classification> call, Response<Classification> response) {
                if (response.isSuccessful()) {
                    listener.onActionSuccessClassification(response, Constants.UPDATE);
                } else {
                    listener.onActionErrorClassification(response, Constants.UPDATE);
                }
            }

            @Override
            public void onFailure(Call<Classification> call, Throwable t) {
                listener.onActionFailureClassification(t, Constants.UPDATE);
            }
        });
    }

    public void show(int dish) {
        Log.d(Tag, "show");
        Call<Classification> call = service.showClassification(dish);
        call.enqueue(new Callback<Classification>() {
            @Override
            public void onResponse(Call<Classification> call, Response<Classification> response) {
                if (response.isSuccessful()) {
                    Log.d(Tag, "succ");
                    listener.onActionSuccessClassification(response, Constants.SHOW);
                } else {
                    Log.d(Tag, "error");
                    listener.onActionErrorClassification(response, Constants.SHOW);
                }
            }

            @Override
            public void onFailure(Call<Classification> call, Throwable t) {
                Log.d(Tag, "fail "+t.getMessage());
                listener.onActionFailureClassification(t, Constants.SHOW);
            }
        });
    }
}
