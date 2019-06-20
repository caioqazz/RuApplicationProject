package br.ufop.ruapplicationmvp.contract;

import java.util.List;

import br.ufop.ruapplicationmvp.model.entity.Notice;

public interface NoticeContract {
    interface Model {
        void index(final OnFinishedListener onFinishedListener);

        void delete(final OnFinishedListener onFinishedListener, int id);

        interface OnFinishedListener {
            void onFinished(List<Notice> notices);


            void onFinishedDelete();

            void onError(String message, int option);

            void onFailure(String message, int option);
        }
    }

    interface Presenter {
        void onDestroy();

        void onDeleteButtonClick(int id);

        void onRefresh();

    }

    interface View {
        void showProgress();

        void hideProgress();

        void onRefreshFinished(List<Notice> notices);

        void onFinishedDelete();


        void onFailure(String message, int option);

        void onError(String message, int option);

    }
}
