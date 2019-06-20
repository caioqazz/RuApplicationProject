package br.ufop.ruapplicationmvp.contract;

import br.ufop.ruapplicationmvp.model.entity.Demand;
import retrofit2.Response;

public interface DemandContract {
    interface Model {
        void getDemand(final OnFinishedListener onFinishedListener, int type, String date);

        interface OnFinishedListener {
            void onFinished(Demand demand);

            void onError(Response<Demand> response);

            void onFailure(Throwable t);
        }
    }

    interface Presenter {
        void onDestroy();

        void getDemand();
    }

    interface View {
        void showProgress();

        void hideProgress();

        void onFailure(Throwable t);

        void onError(String message);

        void setHeader(int type);

        void setValues(int countPrincipal, int countVegetariano, int countTotal);
    }
}
