package br.ufop.ruapplicationmvp.contract;

import br.ufop.ruapplicationmvp.model.response.ReportDemandResponse;

public interface ReportDemandContract {
    interface Model {
        void reportDemand(final OnFinishedListener onFinishedListener, int month, int year);

        interface OnFinishedListener {
            void onFinished(ReportDemandResponse response);

            void onError(String message);

            void onFailure(String message);
        }
    }

    interface Presenter {
        void onDestroy();

        void onCommitButtonClick(int month, int year);

    }

    interface View {
        void showProgress();

        void hideProgress();

        void onFinished(ReportDemandResponse response);

        void onFailure(String message);

        void onError(String message);

    }
}
