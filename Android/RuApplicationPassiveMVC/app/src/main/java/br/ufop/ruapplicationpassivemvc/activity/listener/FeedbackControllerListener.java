package br.ufop.ruapplicationpassivemvc.activity.listener;

import br.ufop.ruapplicationpassivemvc.model.entity.Feedback;

public interface FeedbackControllerListener {
    void feedbackDetail(Feedback feedback);

    void onActionExecuted(String message, int result);
}
