package br.ufop.ruapplicationmvp.service;

import br.ufop.ruapplicationmvp.contract.DetailFeedbackContract;
import br.ufop.ruapplicationmvp.model.entity.Feedback;
import br.ufop.ruapplicationmvp.service.api.ApiService;
import br.ufop.ruapplicationmvp.service.api.RetrofitBuilder;
import br.ufop.ruapplicationmvp.service.api.TokenManager;
import br.ufop.ruapplicationmvp.util.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailFeedbackService implements DetailFeedbackContract.Model {

    private ApiService service;

    public DetailFeedbackService(TokenManager tokenManager) {
        service = RetrofitBuilder.createServiceWithAuth(ApiService.class, tokenManager);
    }

    @Override
    public void delete(OnFinishedListener onFinishedListener, int id) {
        Call<Feedback> call = service.deleteFeedback(id);
        call.enqueue(new Callback<Feedback>() {
            @Override
            public void onResponse(Call<Feedback> call, Response<Feedback> response) {
                if (response.isSuccessful()) {
                    onFinishedListener.onFinished(response.body(), Constants.DELETE);
                } else {
                    onFinishedListener.onError(response.message(), Constants.DELETE);
                }
            }

            @Override
            public void onFailure(Call<Feedback> call, Throwable t) {
                onFinishedListener.onFailure(t.getMessage(), Constants.DELETE);
            }
        });
    }

    @Override
    public void reply(OnFinishedListener onFinishedListener, int id, String subject, String content, String reply) {
        Call<Feedback> call = service.updateFeedback(id, subject, content, reply);
        call.enqueue(new Callback<Feedback>() {
            @Override
            public void onResponse(Call<Feedback> call, Response<Feedback> response) {
                if (response.isSuccessful()) {
                    onFinishedListener.onFinished(response.body(), Constants.UPDATE);
                } else {
                    onFinishedListener.onError(response.message(), Constants.UPDATE);
                }
            }

            @Override
            public void onFailure(Call<Feedback> call, Throwable t) {
                onFinishedListener.onFailure(t.getMessage(), Constants.UPDATE);
            }
        });
    }

    @Override
    public void setFeedbackView(int id, int managerView, int userView) {
        Call<Feedback> call = service.setViewFeedback(id, managerView, userView);
        call.enqueue(new Callback<Feedback>() {
            @Override
            public void onResponse(Call<Feedback> call, Response<Feedback> response) {

            }

            @Override
            public void onFailure(Call<Feedback> call, Throwable t) {

            }
        });
    }
}
