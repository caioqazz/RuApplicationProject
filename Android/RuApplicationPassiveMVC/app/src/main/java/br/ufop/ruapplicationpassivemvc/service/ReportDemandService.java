package br.ufop.ruapplicationpassivemvc.service;

import br.ufop.ruapplicationpassivemvc.model.response.ReportDemandResponse;
import br.ufop.ruapplicationpassivemvc.service.api.ApiService;
import br.ufop.ruapplicationpassivemvc.service.api.RetrofitBuilder;
import br.ufop.ruapplicationpassivemvc.service.api.TokenManager;
import retrofit2.Call;
import retrofit2.Callback;

public class ReportDemandService {

    private ApiService service;

    public ReportDemandService(TokenManager tokenManager) {
        service = RetrofitBuilder.createServiceWithAuth(ApiService.class, tokenManager);
    }

    public void reportDemand(int month,int year, Callback<ReportDemandResponse> callback) {
        Call<ReportDemandResponse> call = service.reportDemand(month,year);
        call.enqueue(callback);
    }
}
