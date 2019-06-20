package br.ufop.ruapplicationmvp.contract;

import br.ufop.ruapplicationmvp.model.entity.Demand;
import br.ufop.ruapplicationmvp.model.response.MenuResponse;

public interface MenuContract {

    interface Model {
        void getMenu(final OnFinishedListener onFinishedListener, int type, String date);

        void insertParticipation(OnFinishedListener listener, int type, String date, int option);

        void deleteParticipation(OnFinishedListener listener, int type, String date);

        void getParticipation(OnFinishedListener listener, int type, String date);


        interface OnFinishedListener {
            void onFinished(Demand demand, int option);

            void onFinished(MenuResponse response);

            void onError(String message, int code);

            void onFailure(String message);
        }
    }

    interface Presenter {
        void onDestroy();

        void insertParticipation(int type, String date, int option);

        void deleteParticipation(int type, String date);

        void getParticipation(int type, String date);

        void onRefresh(int type, String date);

    }

    interface View {
        void showProgress();

        void hideProgress();

        void onCommitParticipation(Demand demand, int option);

        void onRefreshFinished(MenuResponse response);

        void onFailure(String message);

        void onError(String message, int code);

        void showButtonProgress();

        void hideButtonProgress();
    }
}
