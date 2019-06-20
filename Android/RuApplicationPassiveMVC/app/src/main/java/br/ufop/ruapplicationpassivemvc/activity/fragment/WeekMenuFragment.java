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
import br.ufop.ruapplicationpassivemvc.activity.implementation.DayMealActivity;
import br.ufop.ruapplicationpassivemvc.activity.listener.WeekMenuControllerListener;
import br.ufop.ruapplicationpassivemvc.controller.implementation.WeekMenuController;
import br.ufop.ruapplicationpassivemvc.model.entity.Week;
import br.ufop.ruapplicationpassivemvc.view.WeekMenuView;


public class WeekMenuFragment extends Fragment implements WeekMenuControllerListener {
    WeekMenuController weekMenuController;

    public WeekMenuFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_week_menu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Cardápios");
        WeekMenuView weekMenuView = getActivity().findViewById(R.id.week_menu_container);
        weekMenuController = new WeekMenuController(weekMenuView, this);
        weekMenuView.setListeners(weekMenuController, weekMenuController);
        weekMenuController.onRefresh();
    }


    @Override
    public void days(Week week) {
        Intent intent = new Intent(getContext(), DayMealActivity.class);
        intent.putExtra("week", week);
        startActivity(intent);
    }
}



