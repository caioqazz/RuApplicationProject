package br.ufop.ruapplicationpassivemvc.service;

import br.ufop.ruapplicationpassivemvc.controller.listener.DemandServiceListener;
import br.ufop.ruapplicationpassivemvc.model.entity.Demand;
import br.ufop.ruapplicationpassivemvc.service.api.ApiService;
import br.ufop.ruapplicationpassivemvc.service.api.RetrofitBuilder;
import br.ufop.ruapplicationpassivemvc.service.api.TokenManager;
import br.ufop.ruapplicationpassivemvc.util.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DemandService {
    ApiService service;
    DemandServiceListener listener;

    public DemandService(DemandServiceListener listener, TokenManager tokenManager) {
        this.listener = listener;
        service = RetrofitBuilder.createServiceWithAuth(ApiService.class, tokenManager);
    }

    public void show(int type, String date) {
        Call<Demand> call = service.getDemand(type, date);
        call.enqueue(new Callback<Demand>() {
            @Override
            public void onResponse(Call<Demand> call, Response<Demand> response) {
                if (response.isSuccessful()) {
                    listener.onActionSuccessDemand(response, Constants.GET);
                } else {
                    listener.onActionErrorDemand(response, Constants.GET);
                }
            }

            @Override
            public void onFailure(Call<Demand> call, Throwable t) {
                listener.onActionFailureDemand(t, Constants.GET);
            }
        });
    }

    public void showParticipation(int type, String date) {
        Call<Demand> call = service.getMineDemand(type, date);
        call.enqueue(new Callback<Demand>() {
            @Override
            public void onResponse(Call<Demand> call, Response<Demand> response) {
                if (response.isSuccessful()) {
                    listener.onActionSuccessDemand(response, Constants.GET);
                } else {
                    listener.onActionErrorDemand(response, Constants.GET);
                }
            }

            @Override
            public void onFailure(Call<Demand> call, Throwable t) {
                listener.onActionFailureDemand(t, Constants.GET);
            }
        });
    }

    public void insertParticipation(int type, String date, int option) {
        Call<Demand> call = service.addDemand(type, date, option);
        call.enqueue(new Callback<Demand>() {
            @Override
            public void onResponse(Call<Demand> call, Response<Demand> response) {
                if (response.isSuccessful()) {
                    listener.onActionSuccessDemand(response, Constants.INSERT);
                } else {
                    listener.onActionErrorDemand(response, Constants.INSERT);
                }
            }

            @Override
            public void onFailure(Call<Demand> call, Throwable t) {
                listener.onActionFailureDemand(t, Constants.INSERT);
            }
        });
    }

    public void deleteParticipation(int type, String date) {
        Call<Demand> call = service.deleteDemand(type, date);
        call.enqueue(new Callback<Demand>() {
            @Override
            public void onResponse(Call<Demand> call, Response<Demand> response) {
                if (response.isSuccessful()) {
                    listener.onActionSuccessDemand(response, Constants.DELETE);
                } else {
                    listener.onActionErrorDemand(response, Constants.DELETE);
                }
            }

            @Override
            public void onFailure(Call<Demand> call, Throwable t) {
                listener.onActionFailureDemand(t, Constants.DELETE);
            }
        });
    }
}
