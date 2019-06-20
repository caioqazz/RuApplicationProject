package br.ufop.ruapplicationmvp.service;

import java.util.List;

import br.ufop.ruapplicationmvp.contract.FeedbackContract;
import br.ufop.ruapplicationmvp.model.entity.Feedback;
import br.ufop.ruapplicationmvp.service.api.ApiService;
import br.ufop.ruapplicationmvp.service.api.RetrofitBuilder;
import br.ufop.ruapplicationmvp.service.api.TokenManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeedbackService implements FeedbackContract.Model {
    private ApiService service;

    public FeedbackService(TokenManager tokenManager) {
        service = RetrofitBuilder.createServiceWithAuth(ApiService.class, tokenManager);
    }

    @Override
    public void index(OnFinishedListener onFinishedListener) {
        Call<List<Feedback>> callIndex = service.feedbacks();

        callIndex.enqueue(new Callback<List<Feedback>>() {
            @Override
            public void onResponse(Call<List<Feedback>> call, Response<List<Feedback>> response) {
                if (response.isSuccessful()) {
                    onFinishedListener.onFinished(response.body());
                } else {
                    onFinishedListener.onError(response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Feedback>> call, Throwable t) {
                onFinishedListener.onFailure(t.getMessage());
            }
        });
    }

    @Override
    public void indexByUser(OnFinishedListener onFinishedListener) {

        Call<List<Feedback>> callIndex = service.feedbacksByUser();

        callIndex.enqueue(new Callback<List<Feedback>>() {
            @Override
            public void onResponse(Call<List<Feedback>> call, Response<List<Feedback>> response) {
                if (response.isSuccessful()) {
                    onFinishedListener.onFinished(response.body());
                } else {
                    onFinishedListener.onError(response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Feedback>> call, Throwable t) {
                onFinishedListener.onFailure(t.getMessage());
            }
        });
    }

    @Override
    public void delete(OnFinishedListener onFinishedListener, int id) {
        Call<Feedback> call = service.deleteFeedback(id);
        call.enqueue(new Callback<Feedback>() {
            @Override
            public void onResponse(Call<Feedback> call, Response<Feedback> response) {
                if (response.isSuccessful()) {
                    onFinishedListener.onFinishedDelete();
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
