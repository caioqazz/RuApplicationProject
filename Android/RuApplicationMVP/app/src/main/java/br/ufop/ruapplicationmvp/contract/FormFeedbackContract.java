package br.ufop.ruapplicationmvp.contract;

import br.ufop.ruapplicationmvp.model.entity.Feedback;

public interface FormFeedbackContract {
    interface Model {
        void insert(final OnFinishedListener onFinishedListener, String subject, String content);

        interface OnFinishedListener {
            void onFinished(Feedback feedback);

            void onError(String message);

            void onFailure(String message);
        }
    }

    interface Presenter {
        void onDestroy();

        boolean validate(String subject, String content);

        void onCommitButton(String subject, String content);
    }

    interface View {
        void showProgress();

        void hideProgress();

        void onCommitFinished(Feedback feedback);

        void onFailure(String message);

        void onError(String message);

    }
}
