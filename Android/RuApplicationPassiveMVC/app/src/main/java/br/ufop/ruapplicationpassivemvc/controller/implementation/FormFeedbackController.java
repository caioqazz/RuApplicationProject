package br.ufop.ruapplicationpassivemvc.controller.implementation;

import android.content.Context;

import br.ufop.ruapplicationpassivemvc.activity.listener.FormFeedbackControllerListener;
import br.ufop.ruapplicationpassivemvc.controller.listener.FeedbackServiceListener;
import br.ufop.ruapplicationpassivemvc.model.entity.Feedback;
import br.ufop.ruapplicationpassivemvc.service.FeedbackService;
import br.ufop.ruapplicationpassivemvc.service.api.TokenManager;
import br.ufop.ruapplicationpassivemvc.util.Constants;
import br.ufop.ruapplicationpassivemvc.util.ReplyAction;
import br.ufop.ruapplicationpassivemvc.view.FormFeedbackView;

import java.util.List;

import retrofit2.Response;

public class FormFeedbackController implements FeedbackServiceListener {

    FormFeedbackControllerListener listener;
    FormFeedbackView view;
    TokenManager tokenManager;

    public FormFeedbackController(FormFeedbackControllerListener listener, FormFeedbackView view) {
        this.listener = listener;
        this.view = view;
        tokenManager = TokenManager.getInstance(view.getContext().getSharedPreferences("prefs", Context.MODE_PRIVATE));

    }

    public void insert() {
        if (validate()) {
            FeedbackService service = new FeedbackService(this, tokenManager);
            view.showLoading();
            service.insert(view.getSubject(), view.getSubject());
        }
    }

    private boolean validate() {

        if (view.getSubject().isEmpty()) {
            view.setSubjectError(Constants.EMPTY_ERROR_MESSAGE);
            return false;
        }
        if (view.getContent().isEmpty()) {
            view.setContentError(Constants.EMPTY_ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    @Override
    public void onActionSuccess(Response<Feedback> response, int option) {
        view.showForm();
        listener.onActionExecuted(ReplyAction.replyActionSucess(option, view.getSubject()), Constants.SUCCESS);
    }

    @Override
    public void onActionError(Response<Feedback> response, int option) {
        view.showForm();
        listener.onActionExecuted(ReplyAction.replyActionError(option, view.getSubject()), Constants.ERROR);
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
