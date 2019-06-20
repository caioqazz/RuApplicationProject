package br.ufop.ruapplicationmvvm.view.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import br.ufop.ruapplicationmvvm.R;
import br.ufop.ruapplicationmvvm.databinding.FragmentCategoryDishBinding;
import br.ufop.ruapplicationmvvm.model.entity.User;
import br.ufop.ruapplicationmvvm.service.api.UserManager;
import br.ufop.ruapplicationmvvm.util.Constants;
import br.ufop.ruapplicationmvvm.view.DishActivity;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryDishFragment extends Fragment {
    private FragmentCategoryDishBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_category_dish, container, false);
        View view = binding.getRoot();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Pratos");

        ButterKnife.bind(this, view);
        User user = UserManager.getInstance(getActivity().
                getSharedPreferences("prefs", getActivity().MODE_PRIVATE)).getUser();
        binding.setUser(user);

    }

    @OnClick({R.id.category_dish_principal, R.id.category_dish_arroz, R.id.category_dish_bebidas,
            R.id.category_dish_feijao, R.id.category_dish_guarnicao, R.id.category_dish_sobremesa,
            R.id.category_dish_vegetariano, R.id.category_dish_btn_add})
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
                //  startActivity(new Intent(getContext(), FormDishActivity.class));
                break;
        }
    }


    private void categorySelected(int type) {
        Intent intent = new Intent(getContext(), DishActivity.class);
        intent.putExtra("category_dish_type", type);
        startActivity(intent);
    }


}
