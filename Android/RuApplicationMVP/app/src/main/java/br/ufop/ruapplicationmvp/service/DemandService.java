package br.ufop.ruapplicationmvp.service;

import br.ufop.ruapplicationmvp.contract.DemandContract;
import br.ufop.ruapplicationmvp.model.entity.Demand;
import br.ufop.ruapplicationmvp.service.api.ApiService;
import br.ufop.ruapplicationmvp.service.api.RetrofitBuilder;
import br.ufop.ruapplicationmvp.service.api.TokenManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DemandService implements DemandContract.Model {


    private ApiService service;

    public DemandService(TokenManager tokenManager) {
        service = RetrofitBuilder.createServiceWithAuth(ApiService.class, tokenManager);
    }

    @Override
    public void getDemand(OnFinishedListener onFinishedListener, int type, String date) {
        Call<Demand> call = service.getDemand(type, date);
        call.enqueue(new Callback<Demand>() {
            @Override
            public void onResponse(Call<Demand> call, Response<Demand> response) {
                if (response.isSuccessful()) {
                    onFinishedListener.onFinished(response.body());
                } else {
                    onFinishedListener.onError(response);
                }
            }

            @Override
            public void onFailure(Call<Demand> call, Throwable t) {
                onFinishedListener.onFailure(t);
            }
        });
    }
}
