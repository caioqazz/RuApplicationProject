package br.ufop.ruapplicationmvp.contract;

import java.util.List;

import br.ufop.ruapplicationmvp.model.entity.Week;

public interface WeekMenuContract {
    interface Model {
        void index(final OnFinishedListener onFinishedListener);

        interface OnFinishedListener {
            void onFinished(List<Week> weeks);

            void onError(String message);

            void onFailure(String message);
        }
    }

    interface Presenter {
        void onDestroy();

        void onRefresh();

    }

    interface View {
        void showProgress();

        void hideProgress();

        void onRefreshFinished(List<Week> weeks);

        void onFailure(String message);

        void onError(String message);

    }
}
