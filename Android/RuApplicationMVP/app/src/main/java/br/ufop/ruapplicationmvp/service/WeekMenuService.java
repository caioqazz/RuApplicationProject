package br.ufop.ruapplicationmvp.service;

import java.util.List;

import br.ufop.ruapplicationmvp.contract.WeekMenuContract;
import br.ufop.ruapplicationmvp.model.entity.Week;
import br.ufop.ruapplicationmvp.service.api.ApiService;
import br.ufop.ruapplicationmvp.service.api.RetrofitBuilder;
import br.ufop.ruapplicationmvp.service.api.TokenManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeekMenuService implements WeekMenuContract.Model {

    private ApiService service;

    public WeekMenuService(TokenManager tokenManager) {
        service = RetrofitBuilder.createServiceWithAuth(ApiService.class, tokenManager);
    }

    @Override
    public void index(OnFinishedListener onFinishedListener) {
        Call<List<Week>> call = service.weeks();
        call.enqueue(new Callback<List<Week>>() {
            @Override
            public void onResponse(Call<List<Week>> call, Response<List<Week>> response) {
                if (response.isSuccessful()) {
                    onFinishedListener.onFinished(response.body());
                } else {
                    onFinishedListener.onError(response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Week>> call, Throwable t) {
                onFinishedListener.onFailure(t.getMessage());
            }
        });
    }
}
