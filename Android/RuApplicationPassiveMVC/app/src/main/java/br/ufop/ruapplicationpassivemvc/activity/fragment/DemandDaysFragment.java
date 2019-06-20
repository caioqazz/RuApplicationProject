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
import br.ufop.ruapplicationpassivemvc.activity.implementation.DemandActivity;
import br.ufop.ruapplicationpassivemvc.activity.listener.DemandDaysControllerListener;
import br.ufop.ruapplicationpassivemvc.controller.implementation.DemandDaysController;
import br.ufop.ruapplicationpassivemvc.model.entity.Day;
import br.ufop.ruapplicationpassivemvc.util.Networking;
import br.ufop.ruapplicationpassivemvc.view.DemandDaysView;

/**
 * A simple {@link Fragment} subclass.
 */
public class DemandDaysFragment extends Fragment implements DemandDaysControllerListener {
    DemandDaysController controller;
    DemandDaysView demandView;

    public DemandDaysFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_demand_days, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Demanda");
        demandView = getActivity().findViewById(R.id.demand_days_container);
        controller = new DemandDaysController(demandView, this);
        demandView.setListeners(controller, controller);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Networking.isNetworkConnected(demandView))
            controller.onRefresh();
    }

    @Override
    public void detailDemand(Day day) {
        Intent intent = new Intent(getContext(), DemandActivity.class);
        intent.putExtra("day", day);
        startActivity(intent);
    }
}
