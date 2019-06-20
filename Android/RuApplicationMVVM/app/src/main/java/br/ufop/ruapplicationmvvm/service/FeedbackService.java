package br.ufop.ruapplicationmvvm.service;

import java.util.List;

import br.ufop.ruapplicationmvvm.model.entity.Feedback;
import br.ufop.ruapplicationmvvm.model.result.FeedbackResult;
import br.ufop.ruapplicationmvvm.model.result.FeedbacksResult;
import br.ufop.ruapplicationmvvm.service.api.ApiService;
import br.ufop.ruapplicationmvvm.service.api.RetrofitBuilder;
import br.ufop.ruapplicationmvvm.service.api.TokenManager;
import br.ufop.ruapplicationmvvm.service.listener.FeedbackListener;
import br.ufop.ruapplicationmvvm.util.Status;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeedbackService {
    private ApiService service;

    public FeedbackService(TokenManager tokenManager) {
        service = RetrofitBuilder.createServiceWithAuth(ApiService.class, tokenManager);
    }

    public void index(FeedbackListener.OnListFinished listener) {
        Call<List<Feedback>> callIndex = service.feedbacks();

        callIndex.enqueue(new Callback<List<Feedback>>() {
            @Override
            public void onResponse(Call<List<Feedback>> call, Response<List<Feedback>> response) {
                if (response.isSuccessful()) {
                    listener.onRequestFinished(new FeedbacksResult(Status.SUCCESS, response.body(), null, null));
                } else {
                    listener.onRequestFinished(new FeedbacksResult(Status.ERROR, null, response.message(), null));
                }
            }

            @Override
            public void onFailure(Call<List<Feedback>> call, Throwable t) {
                listener.onRequestFinished(new FeedbacksResult(Status.FAILURE, null, null, t.getMessage()));

            }
        });
    }

    public void indexByUser(FeedbackListener.OnListFinished listener) {

        Call<List<Feedback>> callIndex = service.feedbacksByUser();

        callIndex.enqueue(new Callback<List<Feedback>>() {
            @Override
            public void onResponse(Call<List<Feedback>> call, Response<List<Feedback>> response) {
                if (response.isSuccessful()) {
                    listener.onRequestFinished(new FeedbacksResult(Status.SUCCESS, response.body(), null, null));
                } else {
                    listener.onRequestFinished(new FeedbacksResult(Status.ERROR, null, response.message(), null));
                }
            }

            @Override
            public void onFailure(Call<List<Feedback>> call, Throwable t) {
                listener.onRequestFinished(new FeedbacksResult(Status.FAILURE, null, null, t.getMessage()));

            }
        });
    }

    public void delete(int id, FeedbackListener.OnFinished listener) {
        Call<Feedback> call = service.deleteFeedback(id);
        call.enqueue(new Callback<Feedback>() {
            @Override
            public void onResponse(Call<Feedback> call, Response<Feedback> response) {
                if (response.isSuccessful()) {
                    listener.onRequestFinished(new FeedbackResult(Status.SUCCESS, response.body(), null, null));
                } else {
                    listener.onRequestFinished(new FeedbackResult(Status.ERROR, null, response.message(), null));
                }
            }

            @Override
            public void onFailure(Call<Feedback> call, Throwable t) {
                listener.onRequestFinished(new FeedbackResult(Status.FAILURE, null, null, t.getMessage()));

            }
        });
    }

    public void reply(FeedbackListener.OnFinished listener, int id, String subject, String content, String reply) {
        Call<Feedback> call = service.updateFeedback(id, subject, content, reply);
        call.enqueue(new Callback<Feedback>() {
            @Override
            public void onResponse(Call<Feedback> call, Response<Feedback> response) {
                if (response.isSuccessful()) {
                    listener.onRequestFinished(new FeedbackResult(Status.SUCCESS, response.body(), null, null));
                } else {
                    listener.onRequestFinished(new FeedbackResult(Status.ERROR, null, response.message(), null));
                }
            }

            @Override
            public void onFailure(Call<Feedback> call, Throwable t) {
                listener.onRequestFinished(new FeedbackResult(Status.FAILURE, null, null, t.getMessage()));
            }
        });
    }

    public void insert(FeedbackListener.OnFinished listener, String subject, String content) {
        Call<Feedback> call = service.newFeedback(subject, content);
        call.enqueue(new Callback<Feedback>() {
            @Override
            public void onResponse(Call<Feedback> call, Response<Feedback> response) {
                if (response.isSuccessful()) {
                    listener.onRequestFinished(new FeedbackResult(Status.SUCCESS, response.body(), null, null));
                } else {
                    listener.onRequestFinished(new FeedbackResult(Status.ERROR, null, response.message(), null));
                }
            }

            @Override
            public void onFailure(Call<Feedback> call, Throwable t) {
                listener.onRequestFinished(new FeedbackResult(Status.FAILURE, null, null, t.getMessage()));
            }
        });
    }

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
