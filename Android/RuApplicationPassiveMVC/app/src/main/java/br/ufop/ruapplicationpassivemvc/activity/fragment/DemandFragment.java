package br.ufop.ruapplicationpassivemvc.activity.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.usuario.ruapplicationpassivemvc.R;
import br.ufop.ruapplicationpassivemvc.activity.listener.DemandControllerListener;
import br.ufop.ruapplicationpassivemvc.controller.implementation.DemandController;
import br.ufop.ruapplicationpassivemvc.model.entity.Day;
import br.ufop.ruapplicationpassivemvc.view.DemandView;

public class DemandFragment extends Fragment implements DemandControllerListener {

    Day day;
    int type;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_demand, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        DemandView demandView = (DemandView) view;
        demandView.setDate(day.getDate());
        demandView.setHeader(type);
        DemandController controller = new DemandController(demandView, this, day);
        controller.show(type);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            day = (Day) getArguments().getSerializable("day");
            type = getArguments().getInt("type");
            getArguments().clear();
        }


    }
}
