package br.ufop.ruapplicationmvvm.service;

import java.util.List;

import br.ufop.ruapplicationmvvm.model.entity.Day;
import br.ufop.ruapplicationmvvm.model.entity.Demand;
import br.ufop.ruapplicationmvvm.model.result.DayResult;
import br.ufop.ruapplicationmvvm.model.result.DemandResult;
import br.ufop.ruapplicationmvvm.service.api.ApiService;
import br.ufop.ruapplicationmvvm.service.api.RetrofitBuilder;
import br.ufop.ruapplicationmvvm.service.api.TokenManager;
import br.ufop.ruapplicationmvvm.service.listener.DayListener;
import br.ufop.ruapplicationmvvm.service.listener.DemandListener;
import br.ufop.ruapplicationmvvm.util.Status;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DemandService {
    private ApiService service;

    public DemandService(TokenManager tokenManager) {
        this.service = RetrofitBuilder.createServiceWithAuth(ApiService.class, tokenManager);
    }

    public void getDemand(int type, String date, DemandListener.OnFinished listener) {
        Call<Demand> call = service.getDemand(type, date);
        call.enqueue(new Callback<Demand>() {
            @Override
            public void onResponse(Call<Demand> call, Response<Demand> response) {
                if (response.isSuccessful()) {
                    listener.onRequestFinished(new DemandResult(Status.SUCCESS, response.body(), null, null));
                } else {
                    listener.onRequestFinished(new DemandResult(Status.ERROR, null, response.message(), null));
                }
            }

            @Override
            public void onFailure(Call<Demand> call, Throwable t) {
                listener.onRequestFinished(new DemandResult(Status.FAILURE, null, null, t.getMessage()));

            }
        });
    }

    public void indexDays(DayListener.OnFinished listener) {
        Call<List<Day>> call = service.demandDays();
        call.enqueue(new Callback<List<Day>>() {
            @Override
            public void onResponse(Call<List<Day>> call, Response<List<Day>> response) {
                if (response.isSuccessful()) {
                    listener.onRequestFinished(new DayResult(Status.SUCCESS, response.body(), null, null));
                } else {
                    listener.onRequestFinished(new DayResult(Status.ERROR, null, response.message(), null));
                }
            }

            @Override
            public void onFailure(Call<List<Day>> call, Throwable t) {
                listener.onRequestFinished(new DayResult(Status.FAILURE, null, null, t.getMessage()));

            }
        });
    }

    public void insertParticipation(DemandListener.OnFinished listener, int type, String date,
                                    int option) {
        Call<Demand> call = service.addDemand(type, date, option);
        call.enqueue(new Callback<Demand>() {
            @Override
            public void onResponse(Call<Demand> call, Response<Demand> response) {
                if (response.isSuccessful()) {
                    listener.onRequestFinished(new DemandResult(Status.SUCCESS, response.body(), null, null));
                } else {
                    listener.onRequestFinished(new DemandResult(Status.ERROR, null, response.message(), null));
                }
            }

            @Override
            public void onFailure(Call<Demand> call, Throwable t) {
                listener.onRequestFinished(new DemandResult(Status.FAILURE, null, null, t.getMessage()));

            }
        });
    }

    public void deleteParticipation(DemandListener.OnFinished listener, int type, String date) {
        Call<Demand> call = service.deleteDemand(type, date);
        call.enqueue(new Callback<Demand>() {
            @Override
            public void onResponse(Call<Demand> call, Response<Demand> response) {
                if (response.isSuccessful()) {
                    listener.onRequestFinished(new DemandResult(Status.SUCCESS, response.body(), null, null));
                } else {
                    listener.onRequestFinished(new DemandResult(Status.ERROR, null, response.message(), null));
                }
            }

            @Override
            public void onFailure(Call<Demand> call, Throwable t) {
                listener.onRequestFinished(new DemandResult(Status.FAILURE, null, null, t.getMessage()));

            }
        });
    }

    public void getParticipation(DemandListener.OnFinished listener, int type, String date) {
        Call<Demand> call = service.getMineDemand(type, date);
        call.enqueue(new Callback<Demand>() {
            @Override
            public void onResponse(Call<Demand> call, Response<Demand> response) {
                if (response.isSuccessful()) {
                    listener.onRequestFinished(new DemandResult(Status.SUCCESS, response.body(), null, null));
                } else {
                    listener.onRequestFinished(new DemandResult(Status.ERROR, null, response.message(), null));
                }
            }

            @Override
            public void onFailure(Call<Demand> call, Throwable t) {
                listener.onRequestFinished(new DemandResult(Status.FAILURE, null, null, t.getMessage()));

            }
        });
    }
}

