package br.ufop.ruapplicationmvp.service;

import br.ufop.ruapplicationmvp.contract.ReportDemandContract;
import br.ufop.ruapplicationmvp.model.response.ReportDemandResponse;
import br.ufop.ruapplicationmvp.service.api.ApiService;
import br.ufop.ruapplicationmvp.service.api.RetrofitBuilder;
import br.ufop.ruapplicationmvp.service.api.TokenManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportDemandService implements ReportDemandContract.Model {

    private ApiService service;

    public ReportDemandService(TokenManager tokenManager) {
        service = RetrofitBuilder.createServiceWithAuth(ApiService.class, tokenManager);
    }

    @Override
    public void reportDemand(OnFinishedListener onFinishedListener, int month, int year) {
        Call<ReportDemandResponse> call = service.reportDemand(month, year);
        call.enqueue(new Callback<ReportDemandResponse>() {
            @Override
            public void onResponse(Call<ReportDemandResponse> call, Response<ReportDemandResponse> response) {
                if (response.isSuccessful()) {
                    onFinishedListener.onFinished(response.body());
                } else {
                    onFinishedListener.onError(response.message());
                }
            }

            @Override
            public void onFailure(Call<ReportDemandResponse> call, Throwable t) {
                onFinishedListener.onFailure(t.getMessage());
            }
        });
    }
}
