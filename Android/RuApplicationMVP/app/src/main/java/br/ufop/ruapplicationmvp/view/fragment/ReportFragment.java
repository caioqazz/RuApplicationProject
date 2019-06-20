package br.ufop.ruapplicationmvp.view.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import br.ufop.ruapplicationmvp.R;
import br.ufop.ruapplicationmvp.view.ReportDemandAcitvity;
import br.ufop.ruapplicationmvp.view.ReportDishesActivity;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ReportFragment extends Fragment {


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
        ButterKnife.bind(this, view);
        getActivity().setTitle("Relatórios");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_report, container, false);
    }


    @OnClick({R.id.report_demand, R.id.report_dishes})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.report_demand:
                startActivity(new Intent(getContext(), ReportDemandAcitvity.class));
                break;

            case R.id.report_dishes:
                startActivity(new Intent(getContext(), ReportDishesActivity.class));
                break;
        }
    }
}
