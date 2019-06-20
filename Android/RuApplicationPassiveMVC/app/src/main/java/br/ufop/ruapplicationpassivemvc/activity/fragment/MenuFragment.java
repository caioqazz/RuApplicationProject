package br.ufop.ruapplicationpassivemvc.activity.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.usuario.ruapplicationpassivemvc.R;
import br.ufop.ruapplicationpassivemvc.activity.implementation.MenuFormActivity;
import br.ufop.ruapplicationpassivemvc.controller.implementation.MenuController;
import br.ufop.ruapplicationpassivemvc.controller.listener.MenuControllerListener;
import br.ufop.ruapplicationpassivemvc.model.entity.Day;
import br.ufop.ruapplicationpassivemvc.model.entity.Dish;
import br.ufop.ruapplicationpassivemvc.model.entity.User;
import br.ufop.ruapplicationpassivemvc.service.api.TokenManager;
import br.ufop.ruapplicationpassivemvc.util.Constants;
import br.ufop.ruapplicationpassivemvc.view.MenuView;


public class MenuFragment extends Fragment implements MenuControllerListener {

    private Day day;
    private int type;
    private MenuController controller;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            day = (Day) getArguments().getSerializable("day");
            type = getArguments().getInt("type");
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TokenManager tokenManager = TokenManager.getInstance(getActivity().getSharedPreferences("prefs", getActivity().MODE_PRIVATE));
        User user = tokenManager.getUser();

        MenuView menu = (MenuView) view;
        menu.setHeader(type);
        menu.setDate(day.getDate());
        controller = new MenuController(menu, this, type, day.getDate());
        menu.setListeners(controller);

        if (user.getType() == Constants.CLIENT) {
            menu.clientView();
        } else {
            menu.managerView();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_menu, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        controller.show(type, day.getDate());
    }

    @Override
    public void edit(Dish dish, int type) {
        Intent intent = new Intent(getContext(), MenuFormActivity.class);
        if (dish != null) {
            intent.putExtra("dish", dish);
        }
        intent.putExtra("dish_type", type);
        intent.putExtra("day", day);
        intent.putExtra("type", this.type);

        startActivity(intent);
    }


}
