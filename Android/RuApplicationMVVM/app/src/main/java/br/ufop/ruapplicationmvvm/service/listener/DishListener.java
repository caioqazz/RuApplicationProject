package br.ufop.ruapplicationmvvm.service.listener;

import br.ufop.ruapplicationmvvm.model.result.DishResult;
import br.ufop.ruapplicationmvvm.model.result.DishesResult;

public interface DishListener {
    interface OnFinished {
        void onRequestFinished(DishResult result);
    }

    interface OnListFinished {
        void onRequestFinished(DishesResult result);
    }
}
