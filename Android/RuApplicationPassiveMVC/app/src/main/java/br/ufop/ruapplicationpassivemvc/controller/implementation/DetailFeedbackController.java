package br.ufop.ruapplicationpassivemvc.controller.implementation;

import android.content.Context;
import android.widget.Toast;

import br.ufop.ruapplicationpassivemvc.activity.listener.DetailFeedbackControllerListener;
import br.ufop.ruapplicationpassivemvc.controller.listener.FeedbackServiceListener;
import br.ufop.ruapplicationpassivemvc.model.entity.Feedback;
import br.ufop.ruapplicationpassivemvc.service.FeedbackService;
import br.ufop.ruapplicationpassivemvc.service.api.TokenManager;
import br.ufop.ruapplicationpassivemvc.util.Constants;
import br.ufop.ruapplicationpassivemvc.util.ReplyAction;
import br.ufop.ruapplicationpassivemvc.view.DetailFeedbackView;

import java.util.List;

import retrofit2.Response;

public class DetailFeedbackController implements FeedbackServiceListener {
    private DetailFeedbackView view;
    private Feedback feedback;
    private DetailFeedbackControllerListener listener;
    private TokenManager tokenManager;

    public DetailFeedbackController(DetailFeedbackView view, Feedback feedback, DetailFeedbackControllerListener listener) {
        this.view = view;
        this.feedback = feedback;
        this.listener = listener;
        tokenManager = TokenManager.getInstance(view.getContext().getSharedPreferences("prefs", Context.MODE_PRIVATE));
    }

    public void reply() {
        FeedbackService feedbackService = new FeedbackService(this, tokenManager);
        if (validate()) {
            view.showLoading();
            feedbackService.update(feedback.getId(), feedback.getSubject(), feedback.getContent(), view.getReply());
        }

    }

    public void setFeedback() {
        FeedbackService feedbackService = new FeedbackService(this, tokenManager);
        switch (tokenManager.getUser().getType()) {
            case Constants.MANAGER:
                if (feedback.getManagerViewed() == Constants.NOT_VISUALIZED) {
                    feedbackService.setFeedbackView(feedback.getId(), Constants.VISUALIZED, feedback.getUserView());
                }
                break;
            case Constants.CLIENT:
                if (feedback.getUserView() == Constants.NOT_VISUALIZED) {
                    feedbackService.setFeedbackView(feedback.getId(), feedback.getManagerViewed(), Constants.VISUALIZED);
                }
                break;
        }

    }

    @Override
    public void onActionSuccess(Response<Feedback> response, int option) {
        view.showForm();
        listener.onActionExecuted(ReplyAction.replyActionSucess(option, feedback.getSubject()), Constants.SUCCESS);

    }

    @Override
    public void onActionError(Response<Feedback> response, int option) {
        view.showForm();
        listener.onActionExecuted(ReplyAction.replyActionError(option, feedback.getSubject()), Constants.ERROR);
    }

    public boolean validate() {
        if (view.getReply().isEmpty()) {
            Toast.makeText(view.getContext(), "Preencha a resposta para enviar.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public void delete() {
        view.showLoading();
        FeedbackService feedbackService = new FeedbackService(this, tokenManager);
        feedbackService.delete(feedback.getId());
    }

    @Override
    public void onActionFailure(Throwable t, int option) {
        view.showForm();
    }

    @Override
    public void onIndexSuccess(Response<List<Feedback>> response) {

    }

    @Override
    public void onIndexError(Response<List<Feedback>> response) {

    }

}
