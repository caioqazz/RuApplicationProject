package br.ufop.ruapplicationmvvm.viewmodel;

import android.content.SharedPreferences;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import br.ufop.ruapplicationmvvm.model.result.ClassificationResult;
import br.ufop.ruapplicationmvvm.model.result.DishResult;
import br.ufop.ruapplicationmvvm.service.ClassificationService;
import br.ufop.ruapplicationmvvm.service.DishService;
import br.ufop.ruapplicationmvvm.service.api.TokenManager;
import br.ufop.ruapplicationmvvm.service.listener.ClassificationListener;
import br.ufop.ruapplicationmvvm.service.listener.DishListener;
import br.ufop.ruapplicationmvvm.util.Status;

public class DetailDishViewModel extends ViewModel implements DishListener.OnFinished, ClassificationListener.OnFinished {

    private MediatorLiveData<DishResult> dishResult;
    private MediatorLiveData<ClassificationResult> classificationResult;
    private DishService dishService;
    private ClassificationService classificationService;

    public DetailDishViewModel(SharedPreferences prefs) {
        dishResult = new MediatorLiveData<>();
        classificationResult = new MediatorLiveData<>();
        dishService = new DishService(TokenManager.getInstance(prefs));
        classificationService = new ClassificationService(TokenManager.getInstance(prefs));
    }

    public MediatorLiveData<DishResult> getDishResult() {
        return dishResult;
    }

    public MediatorLiveData<ClassificationResult> getClassificationResult() {
        return classificationResult;
    }

    public void updateClassification(int dish, float rate, boolean option, String comment) {
        classificationResult.setValue(new ClassificationResult(Status.LOADING, null, null, null));

        classificationService.updateClassification(this, dish, rate, option, comment);
    }

    public void showClassification(int id) {
        //classificationResult.setValue(new ClassificationResult(Status.LOADING, null, null, null));

        classificationService.showClassification(this, id);
    }


    public void delete(int id) {
        dishResult.setValue(new DishResult(Status.LOADING, null, null, null));

        dishService.delete(id, this);
    }

    @Override
    public void onRequestFinished(ClassificationResult result) {
        this.classificationResult.setValue(result);
    }

    @Override
    public void onRequestFinished(DishResult result) {
        this.dishResult.setValue(result);
    }
}
