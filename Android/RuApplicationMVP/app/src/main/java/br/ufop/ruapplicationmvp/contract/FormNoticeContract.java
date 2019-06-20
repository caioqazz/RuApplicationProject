package br.ufop.ruapplicationmvp.contract;

import br.ufop.ruapplicationmvp.model.entity.Notice;

public interface FormNoticeContract {
    interface Model {
        void insert(final OnFinishedListener onFinishedListener, String subject, String content, int type);

        interface OnFinishedListener {
            void onFinished(Notice notice);

            void onError(String message);

            void onFailure(String message);
        }
    }

    interface Presenter {
        void onDestroy();

        boolean validate(String subject, String content, int type);

        void onCommitButton(String subject, String content, int type);
    }

    interface View {
        void showProgress();

        void hideProgress();

        void onCommitFinished(Notice notice);

        void onFailure(String message);

        void onError(String message);

    }
}
