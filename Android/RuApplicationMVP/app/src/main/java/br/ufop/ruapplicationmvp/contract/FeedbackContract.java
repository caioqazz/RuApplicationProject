package br.ufop.ruapplicationmvp.contract;

import java.util.List;

import br.ufop.ruapplicationmvp.model.entity.Feedback;

public interface FeedbackContract {
    interface Model {
        void index(final OnFinishedListener onFinishedListener);

        void indexByUser(final OnFinishedListener onFinishedListener);

        void delete(final OnFinishedListener onFinishedListener, int id);

        interface OnFinishedListener {
            void onFinished(List<Feedback> feedbacks);

            void onFinishedDelete();

            void onError(String message);

            void onFailure(String message);
        }
    }

    interface Presenter {
        void onDestroy();

        void onDeleteButtonClick(int id);

        void onRefresh(int option);
    }

    interface View {
        void showProgress();

        void hideProgress();

        void onFinishedDelete();

        void onRefreshFinished(List<Feedback> feedbacks);

        void onFailure(String message);

        void onError(String message);

    }
}
