package br.ufop.ruapplicationpassivemvc.controller.implementation;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.example.usuario.ruapplicationpassivemvc.R;
import br.ufop.ruapplicationpassivemvc.controller.listener.DemandServiceListener;
import br.ufop.ruapplicationpassivemvc.controller.listener.MenuControllerListener;
import br.ufop.ruapplicationpassivemvc.controller.listener.MenuServiceListener;
import br.ufop.ruapplicationpassivemvc.model.entity.Demand;
import br.ufop.ruapplicationpassivemvc.model.entity.Dish;
import br.ufop.ruapplicationpassivemvc.model.entity.Meal;
import br.ufop.ruapplicationpassivemvc.model.response.MenuResponse;
import br.ufop.ruapplicationpassivemvc.service.DemandService;
import br.ufop.ruapplicationpassivemvc.service.MenuService;
import br.ufop.ruapplicationpassivemvc.service.api.TokenManager;
import br.ufop.ruapplicationpassivemvc.util.Constants;
import br.ufop.ruapplicationpassivemvc.view.MenuView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

public class MenuController implements MenuServiceListener, DemandServiceListener, View.OnClickListener {


    private MenuControllerListener listener;
    private MenuResponse menu;
    private Demand demand;
    private int type;
    private String date;
    private TokenManager tokenManager;
    private MenuView view;

    public MenuController(MenuView view, MenuControllerListener listener, int type, String date) {
        this.type = type;
        this.date = date;
        this.view = view;
        this.listener = listener;
        tokenManager = TokenManager.getInstance(view.getContext().getSharedPreferences("prefs", Context.MODE_PRIVATE));


    }

    private void insertParticipation(int option) {
        view.showLoading();
        DemandService demandService = new DemandService(this, tokenManager);
        demandService.insertParticipation(type, date, 0);

    }

    private void deleteParticipation() {
        view.showLoading();
        DemandService demandService = new DemandService(this, tokenManager);
        demandService.deleteParticipation(type, date);
    }

    public void showParticipation() {
        if (tokenManager.getUser().getType() == Constants.CLIENT) {
            DemandService demandService = new DemandService(this, tokenManager);
            demandService.showParticipation(type, date);
        }
    }

    public void show(int type, String date) {
        view.setVisibilityDialog(View.VISIBLE);
        MenuService menuService = new MenuService(this, tokenManager);
        menuService.getMenu(type, date);
    }

    private Dish getByType(int type) {
        for (int i = 0; i < menu.getDishes().size(); i++) {
            if (menu.getDishes().get(i).getType() == type) {
                return menu.getDishes().get(i);
            }
        }
        return null;
    }

    private void setMenu(ArrayList<Dish> menu) {
        for (int i = 0; i < menu.size(); i++) {
            switch (menu.get(i).getType()) {
                case Constants.PRINCIPAL:
                    view.setPrincipal(menu.get(i));
                    break;
                case Constants.VEGETARIANO:
                    view.setVegetariano(menu.get(i));
                    break;
                case Constants.GUARNICAO:
                    view.setGuarnicao(menu.get(i));
                    break;
                case Constants.ARROZ:
                    view.setArroz(menu.get(i));
                    break;
                case Constants.FEIJAO:
                    view.setFeijao(menu.get(i));
                    break;
                case Constants.BEBIDAS:
                    view.setBebida(menu.get(i));
                    break;
                case Constants.SOBREMESA:
                    view.setSobremesa(menu.get(i));
                    break;
            }
        }

    }

    @Override
    public void onIndexSuccessMenu(Response<MenuResponse> response) {

        menu = response.body();
        if (menu != null) {
            setResponse(menu.getMeal(), menu.getDishes());
        }


    }

    private void setResponse(Meal meal, List<Dish> dishes) {
        view.setVisibilityDialog(View.INVISIBLE);

        if (meal.getStatus() == Constants.FECHADO) {
            setMeal(meal.getReason());
        } else if (menu.getDishes() != null) {
            showParticipation();
            setMenu((ArrayList<Dish>) menu.getDishes());

        }
    }

    private void setMeal(String reason) {
        view.showErrorContainer(reason);
    }


    @Override
    public void onActionSuccessDemand(Response<Demand> response, int option) {
        if (demand == null)
            demand = response.body();

        switch (option) {
            case Constants.GET:
                view.setButton(demand.getParcipation());
                break;
            case Constants.INSERT:
                demand.setParcipation(Constants.CONFIRMED);
                view.setButton(Constants.CONFIRMED);
                break;
            case Constants.DELETE:
                demand.setParcipation(Constants.NOT_CONFIRMED);
                view.setButton(Constants.NOT_CONFIRMED);
                break;
        }
        view.showForm();

    }

    @Override
    public void onActionErrorDemand(Response<Demand> response, int option) {
        view.showForm();
        Toast.makeText(view.getContext(), "Error", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onActionFailureDemand(Throwable t, int option) {
        view.showForm();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.menu_vegetariano_edit:
                listener.edit(getByType(Constants.VEGETARIANO), Constants.VEGETARIANO);
                break;
            case R.id.menu_principal_edit:
                listener.edit(getByType(Constants.PRINCIPAL), Constants.PRINCIPAL);
                break;

            case R.id.menu_guarnicao_edit:
                listener.edit(getByType(Constants.GUARNICAO), Constants.GUARNICAO);
                break;
            case R.id.menu_arroz_edit:
                listener.edit(getByType(Constants.ARROZ), Constants.ARROZ);
                break;
            case R.id.menu_feijao_edit:
                listener.edit(getByType(Constants.FEIJAO), Constants.FEIJAO);
                break;
            case R.id.menu_bebida_edit:
                listener.edit(getByType(Constants.BEBIDAS), Constants.BEBIDAS);
                break;
            case R.id.menu_sobremesa_edit:
                listener.edit(getByType(Constants.SOBREMESA), Constants.SOBREMESA);
                break;
            case R.id.menu_participation_button:
                participationButton();
                break;
        }

    }

    private void participationButton() {
        if (demand != null) {
            switch (demand.getParcipation()) {
                case Constants.CONFIRMED:
                    deleteParticipation();
                    break;
                case Constants.NOT_CONFIRMED:
                    insertParticipation(0);
                    break;
            }
        }
    }

    @Override
    public void onIndexErrorMenu(Response<MenuResponse> response) {
        view.setVisibilityDialog(View.INVISIBLE);
        if (response.code() == 404) {
            setMeal("");
        }

    }

    @Override
    public void onActionSuccessMenu(Response<Dish> response, int option) {

    }

    @Override
    public void onActionErrorMenu(Response<Dish> response, int option) {

    }

    @Override
    public void onActionFailureMenu(Throwable t, int option) {

    }
}
