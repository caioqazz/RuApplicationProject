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
import br.ufop.ruapplicationpassivemvc.activity.implementation.ReportDemandAcitvity;
import br.ufop.ruapplicationpassivemvc.activity.implementation.ReportDishesActivity;


public class ReportFragment extends Fragment implements View.OnClickListener {


    public ReportFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        (view.findViewById(R.id.report_demand)).setOnClickListener(this);
        (view.findViewById(R.id.report_dishes)).setOnClickListener(this);
        getActivity().setTitle("Relatórios");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_report, container, false);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.report_demand:
                startActivity(new Intent(getContext(), ReportDemandAcitvity.class));
                break;

            case R.id.report_dishes:
                startActivity(new Intent(getContext(), ReportDishesActivity.class));
                break;
        }
    }
}
