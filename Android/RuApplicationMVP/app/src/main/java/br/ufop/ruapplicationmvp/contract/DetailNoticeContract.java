package br.ufop.ruapplicationmvp.contract;

import br.ufop.ruapplicationmvp.model.entity.Notice;

public interface DetailNoticeContract {
    interface Model {
        void delete(final OnFinishedListener onFinishedListener, int id);

        void setViewed(int id);

        interface OnFinishedListener {
            void onFinished();

            void onError(String message);

            void onFailure(String message);
        }
    }

    interface Presenter {
        void onDestroy();

        void onCommitButton(int id);

        void setViewed(int id);
    }

    interface View {
        void showProgress();

        void hideProgress();

        void onCommitFinished();

        void onFailure(String message);

        void onError(String message);

    }
}
