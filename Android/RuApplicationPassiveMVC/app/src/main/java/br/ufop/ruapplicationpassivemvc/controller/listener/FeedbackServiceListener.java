package br.ufop.ruapplicationpassivemvc.controller.listener;

import br.ufop.ruapplicationpassivemvc.model.entity.Feedback;

import java.util.List;

import retrofit2.Response;

public interface FeedbackServiceListener {

    void onActionSuccess(Response<Feedback> response, int option);

    void onActionError(Response<Feedback> response, int option);

    void onActionFailure(Throwable t, int option);

    void onIndexSuccess(Response<List<Feedback>> response);

    void onIndexError(Response<List<Feedback>> response);
}
