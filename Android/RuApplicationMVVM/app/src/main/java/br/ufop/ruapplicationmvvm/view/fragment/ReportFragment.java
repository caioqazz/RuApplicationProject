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
import br.ufop.ruapplicationmvvm.databinding.FragmentReportBinding;
import br.ufop.ruapplicationmvvm.view.ReportDemandAcitvity;
import br.ufop.ruapplicationmvvm.view.ReportDishesActivity;
import butterknife.ButterKnife;


public class ReportFragment extends Fragment implements View.OnClickListener {
    private FragmentReportBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        getActivity().setTitle("Relatórios");
        binding.reportDemand.setOnClickListener(this);
        binding.reportDishes.setOnClickListener(this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_report, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onClick(View view) {
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
