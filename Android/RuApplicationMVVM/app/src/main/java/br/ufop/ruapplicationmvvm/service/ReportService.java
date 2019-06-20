package br.ufop.ruapplicationmvvm.service;

import br.ufop.ruapplicationmvvm.model.entity.ReportDish;
import br.ufop.ruapplicationmvvm.model.response.ReportDemandResponse;
import br.ufop.ruapplicationmvvm.model.result.ReportDemandtResult;
import br.ufop.ruapplicationmvvm.model.result.ReportDishResult;
import br.ufop.ruapplicationmvvm.service.api.ApiService;
import br.ufop.ruapplicationmvvm.service.api.RetrofitBuilder;
import br.ufop.ruapplicationmvvm.service.api.TokenManager;
import br.ufop.ruapplicationmvvm.service.listener.ReportListener;
import br.ufop.ruapplicationmvvm.util.Status;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportService {
    private ApiService service;

    public ReportService(TokenManager tokenManager) {
        service = RetrofitBuilder.createServiceWithAuth(ApiService.class, tokenManager);
    }

    public void reportDish(ReportListener.OnDishFinished listener, int id) {
        Call<ReportDish> call = service.reportClassification(id);
        call.enqueue(new Callback<ReportDish>() {
            @Override
            public void onResponse(Call<ReportDish> call, Response<ReportDish> response) {
                if (response.isSuccessful()) {
                    listener.onRequestFinished(new ReportDishResult(Status.SUCCESS, response.body(), null, null));
                } else {
                    listener.onRequestFinished(new ReportDishResult(Status.ERROR, null, response.message(), null));
                }
            }

            @Override
            public void onFailure(Call<ReportDish> call, Throwable t) {
                listener.onRequestFinished(new ReportDishResult(Status.FAILURE, null, null, t.getMessage()));
            }
        });
    }

    public void reportDemand(ReportListener.OnDemandFinished listener, int month, int year) {
        Call<ReportDemandResponse> call = service.reportDemand(month, year);
        call.enqueue(new Callback<ReportDemandResponse>() {
            @Override
            public void onResponse(Call<ReportDemandResponse> call, Response<ReportDemandResponse> response) {
                if (response.isSuccessful()) {
                    listener.onRequestFinished(new ReportDemandtResult(Status.SUCCESS, response.body(), null, null));
                } else {
                    listener.onRequestFinished(new ReportDemandtResult(Status.ERROR, null, response.message(), null));
                }
            }

            @Override
            public void onFailure(Call<ReportDemandResponse> call, Throwable t) {
                listener.onRequestFinished(new ReportDemandtResult(Status.FAILURE, null, null, t.getMessage()));
            }
        });
    }
}

