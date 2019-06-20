package br.ufop.ruapplicationmvvm.viewmodel;

import android.content.SharedPreferences;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import br.ufop.ruapplicationmvvm.model.info.DishForm;
import br.ufop.ruapplicationmvvm.model.result.DishResult;
import br.ufop.ruapplicationmvvm.service.DishService;
import br.ufop.ruapplicationmvvm.service.api.TokenManager;
import br.ufop.ruapplicationmvvm.service.listener.DishListener;
import br.ufop.ruapplicationmvvm.util.Status;
import okhttp3.MultipartBody;


public class FormDishViewModel extends ViewModel implements DishListener.OnFinished {
    private DishForm state = new DishForm();
    private MediatorLiveData<DishResult> result;
    private DishService service;

    public FormDishViewModel(SharedPreferences prefs) {
        result = new MediatorLiveData<>();
        service = new DishService(TokenManager.getInstance(prefs));

    }


    public MutableLiveData<DishResult> getResult() {
        return result;
    }


    public void update(MultipartBody.Part photo, int id, int type) {
        result.setValue(new DishResult(Status.LOADING, null, null, null));

     //   service.update(photo, id, name, type, description, this);

    }

    public void insert(MultipartBody.Part photo, int type) {
        result.setValue(new DishResult(Status.LOADING, null, null, null));

//        service.insert(photo, state, type, description, this);

    }

    @Override
    public void onRequestFinished(DishResult result) {
        this.result.setValue(result);
    }
}
