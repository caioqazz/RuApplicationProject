package br.ufop.ruapplicationmvp.service;

import java.util.List;

import br.ufop.ruapplicationmvp.contract.ReportDishesContract;
import br.ufop.ruapplicationmvp.model.entity.Dish;
import br.ufop.ruapplicationmvp.model.entity.ReportDish;
import br.ufop.ruapplicationmvp.service.api.ApiService;
import br.ufop.ruapplicationmvp.service.api.RetrofitBuilder;
import br.ufop.ruapplicationmvp.service.api.TokenManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportDishesService implements ReportDishesContract.Model {

    private ApiService service;

    public ReportDishesService(TokenManager tokenManager) {
        service = RetrofitBuilder.createServiceWithAuth(ApiService.class, tokenManager);
    }

    @Override
    public void reportDish(OnFinishedListener onFinishedListener, int id) {
        Call<ReportDish> call = service.reportClassification(id);
        call.enqueue(new Callback<ReportDish>() {
            @Override
            public void onResponse(Call<ReportDish> call, Response<ReportDish> response) {
                if (response.isSuccessful()) {
                    onFinishedListener.onFinished(response.body());
                } else {
                    onFinishedListener.onError(response.message());
                }
            }

            @Override
            public void onFailure(Call<ReportDish> call, Throwable t) {
                onFinishedListener.onFailure(t.getMessage());
            }
        });
    }

    @Override
    public void index(OnFinishedListener onFinishedListener) {
        Call<List<Dish>> call = service.dishes();
        call.enqueue(new Callback<List<Dish>>() {
            @Override
            public void onResponse(Call<List<Dish>> call, Response<List<Dish>> response) {
                if (response.isSuccessful()) {
                    onFinishedListener.onFinishedIndex(response.body());
                } else {
                    onFinishedListener.onError(response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Dish>> call, Throwable t) {
                onFinishedListener.onFailure(t.getMessage());
            }
        });
    }
}
