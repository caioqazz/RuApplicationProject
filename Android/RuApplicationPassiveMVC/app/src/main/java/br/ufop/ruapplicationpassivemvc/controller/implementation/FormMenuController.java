package br.ufop.ruapplicationpassivemvc.controller.implementation;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import br.ufop.ruapplicationpassivemvc.activity.listener.FormMenuControllerListener;
import br.ufop.ruapplicationpassivemvc.controller.listener.DishServiceListener;
import br.ufop.ruapplicationpassivemvc.controller.listener.MenuServiceListener;
import br.ufop.ruapplicationpassivemvc.model.entity.Dish;
import br.ufop.ruapplicationpassivemvc.model.response.MenuResponse;
import br.ufop.ruapplicationpassivemvc.service.DishService;
import br.ufop.ruapplicationpassivemvc.service.MenuService;
import br.ufop.ruapplicationpassivemvc.service.api.TokenManager;
import br.ufop.ruapplicationpassivemvc.util.Constants;
import br.ufop.ruapplicationpassivemvc.util.ReplyAction;
import br.ufop.ruapplicationpassivemvc.view.FormMenuView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

public class FormMenuController implements DishServiceListener, MenuServiceListener, AdapterView.OnItemSelectedListener {
    private FormMenuView view;
    private FormMenuControllerListener listener;
    private TokenManager tokenManager;
    private Dish dish;
    private ArrayList<Dish> dishes;
    private int positionSelected = 0;


    public FormMenuController(FormMenuView view, FormMenuControllerListener listener, Dish dish) {
        this.view = view;
        this.dish = dish;
        this.listener = listener;
        tokenManager = TokenManager.getInstance(view.getContext().getSharedPreferences("prefs", Context.MODE_PRIVATE));

    }

    public void insert(String date, int type) {
        if (validate()) {
            view.showLoading();
            MenuService service = new MenuService(this, tokenManager);
            Dish select = dishes.get(positionSelected - 1);
            service.insertDish(date, type, select.getId(), select.getType());
        }
    }

    private boolean validate() {
        if (positionSelected == 0) {
            Toast.makeText(view.getContext(), "Selecione uma prato para proseguir", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    public void update(String date, int type) {
        if (validate()) {
            view.showLoading();
            MenuService service = new MenuService(this, tokenManager);
            Dish select = dishes.get(positionSelected - 1);
            service.updateDish(date, type, select.getId(), select.getType());
        }
    }

    public void getDishes(int type) {

        DishService service = new DishService(this, tokenManager);
        service.indexByType(type);
    }


    private void setSpinner(String[] data) {
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_item, data);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        view.setAdapterSpinner(spinnerArrayAdapter);// The drop down view
        if (dish != null) {
            view.setPositionSpinner(positionDish());
        }
    }


    @Override
    public void onIndexSuccess(Response<List<Dish>> response) {
        dishes = (ArrayList<Dish>) response.body();
        setSpinner(getNames(dishes));
    }

    private int positionDish() {
        for (int i = 0; i < dishes.size(); i++) {
            if (dish.getId() == dishes.get(i).getId()) {
                return i + 1;
            }
        }
        return 0;
    }

    private String[] getNames(ArrayList<Dish> dishes) {
        String[] data = new String[dishes.size() + 1];
        data[0] = "Selecione";
        for (int i = 1; i < dishes.size() + 1; i++) {
            data[i] = dishes.get(i - 1).getName();
        }
        return data;
    }

    @Override
    public void onIndexError(Response<List<Dish>> response) {

    }

    @Override
    public void onActionSuccess(Response<Dish> response, int option) {

    }

    @Override
    public void onActionError(Response<Dish> response, int option) {

    }

    @Override
    public void onActionFailure(Throwable t, int option) {
        view.showForm();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        positionSelected = position;
        if (position > 0) {
            this.view.setPreView(dishes.get(position - 1));
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onIndexSuccessMenu(Response<MenuResponse> response) {

    }

    @Override
    public void onIndexErrorMenu(Response<MenuResponse> response) {

    }

    @Override
    public void onActionSuccessMenu(Response<Dish> response, int option) {
        view.showForm();
        String name = "";
        listener.onActionExecuted(ReplyAction.replyActionSucess(option, name), Constants.SUCCESS);

    }

    @Override
    public void onActionErrorMenu(Response<Dish> response, int option) {
        view.showForm();
        String name = "";
        listener.onActionExecuted(ReplyAction.replyActionError(option, name), Constants.ERROR);
    }

    @Override
    public void onActionFailureMenu(Throwable t, int option) {

    }
}
