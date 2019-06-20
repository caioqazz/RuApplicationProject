package br.ufop.ruapplicationmvvm.service;

import br.ufop.ruapplicationmvvm.model.entity.Classification;
import br.ufop.ruapplicationmvvm.model.result.ClassificationResult;
import br.ufop.ruapplicationmvvm.service.api.ApiService;
import br.ufop.ruapplicationmvvm.service.api.RetrofitBuilder;
import br.ufop.ruapplicationmvvm.service.api.TokenManager;
import br.ufop.ruapplicationmvvm.service.listener.ClassificationListener;
import br.ufop.ruapplicationmvvm.util.Status;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClassificationService {
    private ApiService service;

    public ClassificationService(TokenManager tokenManager) {
        this.service = RetrofitBuilder.createServiceWithAuth(ApiService.class, tokenManager);
    }

    public void showClassification(ClassificationListener.OnFinished listener, int id) {
        Call<Classification> call = service.showClassification(id);
        call.enqueue(new Callback<Classification>() {
            @Override
            public void onResponse(Call<Classification> call, Response<Classification> response) {
                if (response.isSuccessful()) {
                    listener.onRequestFinished(new ClassificationResult(Status.SUCCESS, response.body(), null, null));
                } else {
                    listener.onRequestFinished(new ClassificationResult(Status.ERROR, null, response.message(), null));
                }
            }

            @Override
            public void onFailure(Call<Classification> call, Throwable t) {
                listener.onRequestFinished(new ClassificationResult(Status.FAILURE, null, null, t.getMessage()));
            }
        });
    }

    public void updateClassification(ClassificationListener.OnFinished listener, int dish, float rate, boolean option, String comment) {
        int optionInt = 1;
        if (option)
            optionInt = 0;

        Call<Classification> call = service.addClassification(dish, rate, optionInt, comment);
        call.enqueue(new Callback<Classification>() {
            @Override
            public void onResponse(Call<Classification> call, Response<Classification> response) {
                if (response.isSuccessful()) {
                    listener.onRequestFinished(new ClassificationResult(Status.SUCCESS, response.body(), null, null));
                } else {
                    listener.onRequestFinished(new ClassificationResult(Status.ERROR, null, response.message(), null));
                }
            }

            @Override
            public void onFailure(Call<Classification> call, Throwable t) {
                listener.onRequestFinished(new ClassificationResult(Status.FAILURE, null, null, t.getMessage()));

            }
        });
    }
}
