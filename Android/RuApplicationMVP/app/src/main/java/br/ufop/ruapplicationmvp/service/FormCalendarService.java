package br.ufop.ruapplicationmvp.service;

import br.ufop.ruapplicationmvp.contract.FormCalendarContract;
import br.ufop.ruapplicationmvp.model.entity.Meal;
import br.ufop.ruapplicationmvp.service.api.ApiService;
import br.ufop.ruapplicationmvp.service.api.RetrofitBuilder;
import br.ufop.ruapplicationmvp.service.api.TokenManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormCalendarService implements FormCalendarContract.Model {
    private ApiService service;

    public FormCalendarService(TokenManager tokenManager) {
        service = RetrofitBuilder.createServiceWithAuth(ApiService.class, tokenManager);
    }

    @Override
    public void update(OnFinishedListener onFinishedListener, String date, int type, int status, String reason) {
        Call<Meal> call = service.updateRefeicao(date, type, status, reason);
        call.enqueue(new Callback<Meal>() {
            @Override
            public void onResponse(Call<Meal> call, Response<Meal> response) {
                if (response.isSuccessful()) {
                    onFinishedListener.onFinished(response.body());
                } else {
                    onFinishedListener.onError(response.message());
                }
            }

            @Override
            public void onFailure(Call<Meal> call, Throwable t) {
                onFinishedListener.onFailure(t.getMessage());
            }
        });
    }
}
