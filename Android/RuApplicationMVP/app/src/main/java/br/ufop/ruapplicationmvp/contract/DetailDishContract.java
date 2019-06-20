package br.ufop.ruapplicationmvp.contract;

import br.ufop.ruapplicationmvp.model.entity.Classification;

public interface DetailDishContract {
    interface Model {
        void delete(final OnFinishedListener onFinishedListener, int id);

        void showClassification(final OnFinishedListener onFinishedListener, int id);

        void updateClassification(OnFinishedListener onFinishedListener, int dish, float rate, boolean option, String comment);

        interface OnFinishedListener {
            void onFinished(int option);

            void onFinished(Classification classification);

            void onError(String message, int option);

            void onFailure(String message, int option);
        }
    }

    interface Presenter {
        void onDestroy();

        void updateClassification(int dish, float rate, boolean option, String comment);


        void showClassification(int id);


        void delete(int id);

    }

    interface View {
        void showProgress();

        void hideProgress();

        void onFinished(int option);
        void onShowFinished(Classification classification);
        void onFailure(String message, int option);

        void onError(String message, int option);

    }
}
