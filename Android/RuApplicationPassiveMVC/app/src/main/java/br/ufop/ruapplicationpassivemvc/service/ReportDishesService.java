package br.ufop.ruapplicationpassivemvc.service;

import android.content.Context;

import br.ufop.ruapplicationpassivemvc.controller.listener.RegisterServiceListener;
import br.ufop.ruapplicationpassivemvc.model.entity.ReportDish;
import br.ufop.ruapplicationpassivemvc.service.api.ApiService;
import br.ufop.ruapplicationpassivemvc.service.api.RetrofitBuilder;
import br.ufop.ruapplicationpassivemvc.service.api.TokenManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportDishesService {

    private ApiService service;

    public ReportDishesService(TokenManager tokenManager) {
        service = RetrofitBuilder.createServiceWithAuth(ApiService.class, tokenManager);
    }

    public void reportDish(int dishId, Callback<ReportDish> callback) {
        Call<ReportDish> call = service.reportClassification(dishId);
        call.enqueue(callback);
    }
}
