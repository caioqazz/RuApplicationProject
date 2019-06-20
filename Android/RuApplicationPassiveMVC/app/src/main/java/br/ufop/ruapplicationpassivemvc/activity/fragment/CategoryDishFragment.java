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
import br.ufop.ruapplicationpassivemvc.activity.implementation.DishActivity;
import br.ufop.ruapplicationpassivemvc.activity.implementation.FormDishActivity;
import br.ufop.ruapplicationpassivemvc.activity.listener.CategoryDishControllerListener;
import br.ufop.ruapplicationpassivemvc.model.entity.User;
import br.ufop.ruapplicationpassivemvc.service.api.TokenManager;
import br.ufop.ruapplicationpassivemvc.util.Constants;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryDishFragment extends Fragment implements CategoryDishControllerListener, View.OnClickListener {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_category_dish, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Pratos");
        view.findViewById(R.id.category_dish_principal).setOnClickListener(this);
        view.findViewById(R.id.category_dish_arroz).setOnClickListener(this);
        view.findViewById(R.id.category_dish_bebidas).setOnClickListener(this);
        view.findViewById(R.id.category_dish_feijao).setOnClickListener(this);
        view.findViewById(R.id.category_dish_guarnicao).setOnClickListener(this);
        view.findViewById(R.id.category_dish_sobremesa).setOnClickListener(this);
        view.findViewById(R.id.category_dish_vegetariano).setOnClickListener(this);
        view.findViewById(R.id.category_dish_btn_add).setOnClickListener(this);

        TokenManager tokenManager = TokenManager.getInstance(getActivity().getSharedPreferences("prefs", getActivity().MODE_PRIVATE));
        User user = tokenManager.getUser();
        if (user.getType() == 1) {
            view.findViewById(R.id.category_dish_btn_add).setVisibility(View.GONE);
        }

    }


    @Override
    public void categorySelected(int type) {
        Intent intent = new Intent(getContext(), DishActivity.class);
        intent.putExtra("category_dish_type", type);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.category_dish_principal:
                categorySelected(Constants.PRINCIPAL);
                break;
            case R.id.category_dish_vegetariano:
                categorySelected(Constants.VEGETARIANO);
                break;
            case R.id.category_dish_guarnicao:
                categorySelected(Constants.GUARNICAO);
                break;
            case R.id.category_dish_arroz:
                categorySelected(Constants.ARROZ);
                break;
            case R.id.category_dish_feijao:
                categorySelected(Constants.FEIJAO);
                break;
            case R.id.category_dish_bebidas:
                categorySelected(Constants.BEBIDAS);
                break;
            case R.id.category_dish_sobremesa:
                categorySelected(Constants.SOBREMESA);
                break;
            case R.id.category_dish_btn_add:
                startActivity(new Intent(getContext(), FormDishActivity.class));
                break;
        }
    }
}
