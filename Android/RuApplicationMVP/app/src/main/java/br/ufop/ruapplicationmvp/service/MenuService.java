package br.ufop.ruapplicationmvp.service;

import br.ufop.ruapplicationmvp.contract.MenuContract;
import br.ufop.ruapplicationmvp.model.entity.Demand;
import br.ufop.ruapplicationmvp.model.response.MenuResponse;
import br.ufop.ruapplicationmvp.service.api.ApiService;
import br.ufop.ruapplicationmvp.service.api.RetrofitBuilder;
import br.ufop.ruapplicationmvp.service.api.TokenManager;
import br.ufop.ruapplicationmvp.util.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuService implements MenuContract.Model {

    private ApiService service;

    public MenuService(TokenManager tokenManager) {
        service = RetrofitBuilder.createServiceWithAuth(ApiService.class, tokenManager);
    }

    @Override
    public void getMenu(OnFinishedListener onFinishedListener, int type, String date) {
        Call<MenuResponse> call = service.menus(type, date);
        call.enqueue(new Callback<MenuResponse>() {
            @Override
            public void onResponse(Call<MenuResponse> call, Response<MenuResponse> response) {
                if (response.isSuccessful()) {
                    onFinishedListener.onFinished(response.body());
                } else {
                    onFinishedListener.onError(response.message(), response.code());
                }
            }

            @Override
            public void onFailure(Call<MenuResponse> call, Throwable t) {
                onFinishedListener.onFailure(t.getMessage());
            }
        });
    }

    @Override
    public void insertParticipation(OnFinishedListener listener, int type, String date, int option) {
        Call<Demand> call = service.addDemand(type, date, option);
        call.enqueue(new Callback<Demand>() {
            @Override
            public void onResponse(Call<Demand> call, Response<Demand> response) {
                if (response.isSuccessful()) {
                    listener.onFinished(response.body(), Constants.INSERT);
                } else {
                    listener.onError(response.message(), 0);
                }
            }

            @Override
            public void onFailure(Call<Demand> call, Throwable t) {
                listener.onFailure(t.getMessage());
            }
        });
    }

    @Override
    public void deleteParticipation(OnFinishedListener listener, int type, String date) {
        Call<Demand> call = service.deleteDemand(type, date);
        call.enqueue(new Callback<Demand>() {
            @Override
            public void onResponse(Call<Demand> call, Response<Demand> response) {
                if (response.isSuccessful()) {
                    listener.onFinished(response.body(), Constants.DELETE);
                } else {
                    listener.onError(response.message(), 0);
                }
            }

            @Override
            public void onFailure(Call<Demand> call, Throwable t) {
                listener.onFailure(t.getMessage());
            }
        });
    }

    @Override
    public void getParticipation(OnFinishedListener listener, int type, String date) {
        Call<Demand> call = service.getMineDemand(type, date);
        call.enqueue(new Callback<Demand>() {
            @Override
            public void onResponse(Call<Demand> call, Response<Demand> response) {
                if (response.isSuccessful()) {
                    listener.onFinished(response.body(), Constants.SHOW);
                } else {
                    listener.onError(response.message(), 0);
                }
            }

            @Override
            public void onFailure(Call<Demand> call, Throwable t) {
                listener.onFailure(t.getMessage());
            }
        });
    }
}
