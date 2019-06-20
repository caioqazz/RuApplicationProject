package br.ufop.ruapplicationpassivemvc.controller.implementation;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import br.ufop.ruapplicationpassivemvc.activity.listener.FormDishControllerListener;
import br.ufop.ruapplicationpassivemvc.controller.listener.DishServiceListener;
import br.ufop.ruapplicationpassivemvc.model.entity.Dish;
import br.ufop.ruapplicationpassivemvc.service.DishService;
import br.ufop.ruapplicationpassivemvc.service.api.TokenManager;
import br.ufop.ruapplicationpassivemvc.util.ReplyAction;
import br.ufop.ruapplicationpassivemvc.util.Validate;
import br.ufop.ruapplicationpassivemvc.view.FormDishView;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Response;

public class FormDishController implements DishServiceListener {

    FormDishView view;
    FormDishControllerListener listener;
    TokenManager tokenManager;
    DishService service;

    public FormDishController(FormDishView view, FormDishControllerListener listener) {
        this.view = view;
        this.listener = listener;
        tokenManager = TokenManager.getInstance(view.getContext().getSharedPreferences("prefs", Context.MODE_PRIVATE));
        setSpinner();
    }


    public void insert(MultipartBody.Part photo) {
        service = new DishService(this, tokenManager);
        if (Validate.isFormComplete(view.getName(), view.getType(), view.getDescription())
        ) {
            view.getPhoto();
            view.showLoading();
            service.insert(photo, view.getName(), view.getType(), view.getDescription());

        } else {
            Toast.makeText(view.getContext(), "Preencha todos os campos.", Toast.LENGTH_SHORT).show();
        }
    }


    private void setSpinner() {
        String[] types = {"Selecione", "Principal", "Vegetariano", "Guarnição",
                "Arroz", "Feijão", "Bebida", "Sobremesa"};
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, types);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        view.setAdapterSpinner(spinnerArrayAdapter);// The drop down view
    }


    @Override
    public void onActionSuccess(Response<Dish> response, int option) {
        view.showForm();
        listener.onActionExecuted(ReplyAction.replyActionSucess(option, view.getName()));

    }

    @Override
    public void onActionError(Response<Dish> response, int option) {
        view.showForm();
        listener.onActionExecuted(ReplyAction.replyActionSucess(option, view.getName()));

    }

    @Override
    public void onActionFailure(Throwable t, int option) {
        view.showForm();
        listener.onActionExecuted(t.getMessage());
    }

    public void upload(MultipartBody.Part photo, int id) {
        service = new DishService(this, tokenManager);
        if (validate()) {
            view.getPhoto();
            view.showLoading();
            service.update(id, photo, view.getName(), view.getType(), view.getDescription());

        }
    }

    private boolean validate() {
        if (Validate.isFormComplete(view.getName(), view.getType(), view.getDescription())) {
            return true;
        }
        Toast.makeText(view.getContext(), "Preencha todos os campos.", Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public void onIndexSuccess(Response<List<Dish>> response) {
    }

    @Override
    public void onIndexError(Response<List<Dish>> response) {
    }
}
