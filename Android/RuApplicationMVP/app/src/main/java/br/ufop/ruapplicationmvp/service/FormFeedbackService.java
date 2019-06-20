package br.ufop.ruapplicationmvp.service;

import br.ufop.ruapplicationmvp.contract.FormFeedbackContract;
import br.ufop.ruapplicationmvp.model.entity.Feedback;
import br.ufop.ruapplicationmvp.service.api.ApiService;
import br.ufop.ruapplicationmvp.service.api.RetrofitBuilder;
import br.ufop.ruapplicationmvp.service.api.TokenManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormFeedbackService implements FormFeedbackContract.Model {
    private ApiService service;

    public FormFeedbackService(TokenManager tokenManager) {
        service = RetrofitBuilder.createServiceWithAuth(ApiService.class, tokenManager);
    }

    @Override
    public void insert(OnFinishedListener onFinishedListener, String subject, String content) {

        Call<Feedback> call = service.newFeedback(subject, content);
        call.enqueue(new Callback<Feedback>() {
            @Override
            public void onResponse(Call<Feedback> call, Response<Feedback> response) {
                if (response.isSuccessful()) {
                    onFinishedListener.onFinished(response.body());
                } else {
                    onFinishedListener.onError(response.message());
                }
            }

            @Override
            public void onFailure(Call<Feedback> call, Throwable t) {
                onFinishedListener.onFailure(t.getMessage());
            }
        });
    }
}
