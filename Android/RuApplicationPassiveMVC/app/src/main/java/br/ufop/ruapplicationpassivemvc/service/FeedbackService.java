package br.ufop.ruapplicationpassivemvc.service;

import br.ufop.ruapplicationpassivemvc.controller.listener.FeedbackServiceListener;
import br.ufop.ruapplicationpassivemvc.model.entity.Feedback;
import br.ufop.ruapplicationpassivemvc.service.api.ApiService;
import br.ufop.ruapplicationpassivemvc.service.api.RetrofitBuilder;
import br.ufop.ruapplicationpassivemvc.service.api.TokenManager;
import br.ufop.ruapplicationpassivemvc.util.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeedbackService {
    private FeedbackServiceListener listener;
    private ApiService service;


    public FeedbackService(FeedbackServiceListener listener, TokenManager tokenManager) {
        this.listener = listener;
        service = RetrofitBuilder.createServiceWithAuth(ApiService.class, tokenManager);
    }

    public void index() {

        Call<List<Feedback>> callIndex = service.feedbacks();

        callIndex.enqueue(new Callback<List<Feedback>>() {
            @Override
            public void onResponse(Call<List<Feedback>> call, Response<List<Feedback>> response) {
                if (response.isSuccessful()) {
                    listener.onIndexSuccess(response);
                } else {
                    listener.onIndexError(response);
                }
            }

            @Override
            public void onFailure(Call<List<Feedback>> call, Throwable t) {
                listener.onActionFailure(t, Constants.GET);
            }
        });
    }

    public void indexByUser() {

        Call<List<Feedback>> callIndex = service.feedbacksByUser();

        callIndex.enqueue(new Callback<List<Feedback>>() {
            @Override
            public void onResponse(Call<List<Feedback>> call, Response<List<Feedback>> response) {
                if (response.isSuccessful()) {
                    listener.onIndexSuccess(response);
                } else {
                    listener.onIndexError(response);
                }
            }

            @Override
            public void onFailure(Call<List<Feedback>> call, Throwable t) {
                listener.onActionFailure(t, Constants.GET);
            }
        });
    }

    public void delete(int id) {
        Call<Feedback> call = service.deleteFeedback(id);
        call.enqueue(new Callback<Feedback>() {
            @Override
            public void onResponse(Call<Feedback> call, Response<Feedback> response) {
                if (response.isSuccessful()) {
                    listener.onActionSuccess(response, Constants.DELETE);
                } else {
                    listener.onActionError(response, Constants.DELETE);
                }
            }

            @Override
            public void onFailure(Call<Feedback> call, Throwable t) {
                listener.onActionFailure(t, Constants.DELETE);
            }
        });

    }

    public void insert(String subject, String content) {

        Call<Feedback> call = service.newFeedback(subject, content);
        call.enqueue(new Callback<Feedback>() {
            @Override
            public void onResponse(Call<Feedback> call, Response<Feedback> response) {
                if (response.isSuccessful()) {
                    listener.onActionSuccess(response, Constants.INSERT);
                } else {
                    listener.onActionError(response, Constants.INSERT);
                }
            }

            @Override
            public void onFailure(Call<Feedback> call, Throwable t) {
                listener.onActionFailure(t, Constants.INSERT);
            }
        });
    }

    public void update(int id, String subject, String content, String reply) {
        Call<Feedback> call = service.updateFeedback(id, subject, content, reply);
        call.enqueue(new Callback<Feedback>() {
            @Override
            public void onResponse(Call<Feedback> call, Response<Feedback> response) {
                if (response.isSuccessful()) {
                    listener.onActionSuccess(response, Constants.UPDATE);
                } else {
                    listener.onActionError(response, Constants.UPDATE);
                }
            }

            @Override
            public void onFailure(Call<Feedback> call, Throwable t) {
                listener.onActionFailure(t, Constants.UPDATE);
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
