package br.ufop.ruapplicationmvp.contract;

import br.ufop.ruapplicationmvp.model.entity.Feedback;

public interface DetailFeedbackContract {
    interface Model {
        void delete(final OnFinishedListener onFinishedListener, int id);

        void reply(final OnFinishedListener onFinishedListener, int id, String subject, String content, String reply);

        void setFeedbackView(int id, int managerView, int userView);

        interface OnFinishedListener {
            void onFinished(Feedback feedback, int option);

            void onError(String message, int option);

            void onFailure(String message, int option);
        }
    }

    interface Presenter {
        void onDestroy();

        boolean validate(String reply);

        void reply(int id, String subject, String content, String reply);

        void delete(int id);

        void onFeedbackVisualized(int type, int managerView, int userView);
    }

    interface View {
        void showProgress();

        void hideProgress();

        void onFinished(Feedback feedback, int option);

        void onFailure(String message, int option);

        void onError(String message, int option);

    }
}
