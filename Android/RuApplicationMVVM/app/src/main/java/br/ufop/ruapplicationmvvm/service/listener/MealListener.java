package br.ufop.ruapplicationmvvm.service.listener;

import br.ufop.ruapplicationmvvm.model.result.MealResult;
import br.ufop.ruapplicationmvvm.model.result.MealsResult;

public interface MealListener {
    interface OnListFinished {
        void onRequestFinished(MealsResult result);
    }
    interface OnFinished {
        void onRequestFinished(MealResult result);
    }
}
