package br.ufop.ruapplicationmvvm.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import br.ufop.ruapplicationmvvm.R;
import br.ufop.ruapplicationmvvm.ViewModelFactory;
import br.ufop.ruapplicationmvvm.databinding.FragmentDemandBinding;
import br.ufop.ruapplicationmvvm.model.entity.Day;
import br.ufop.ruapplicationmvvm.util.Status;
import br.ufop.ruapplicationmvvm.viewmodel.DemandViewModel;
import es.dmoral.toasty.Toasty;

import static android.content.Context.MODE_PRIVATE;


public class DemandFragment extends Fragment {
    private DemandViewModel mViewModel;
    private Day day;
    private int type;
    private FragmentDemandBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_demand, container, false);
        View view = binding.getRoot();
        //here data must be an instance of the class MarsDataProvider
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();

        mViewModel = ViewModelProviders
                .of(this, new ViewModelFactory(getActivity().getSharedPreferences("prefs", MODE_PRIVATE)))
                .get(DemandViewModel.class);
        binding.setViewmodel(mViewModel);

        mViewModel.getResult().observe(this, result -> {

            if (result.getStatus() == Status.SUCCESS)
                binding.setDemand(result.getData());
            else if (result.getStatus() == Status.ERROR)
                onError(result.getError());
            else if (result.getStatus() == Status.FAILURE)
                onFailure(result.getFailure());

        });
        mViewModel.getDemand(day.getDate(), type);
    }

    private void init() {
        if (getArguments() != null) {
            day = (Day) getArguments().getSerializable("day");
            type = getArguments().getInt("type");
            getArguments().clear();
            binding.setType(type);
        }
    }


    private void onError(String message) {
        Toasty.error(getContext(), message, Toasty.LENGTH_LONG).show();
    }


    private void onFailure(String message) {
        Toasty.warning(getContext(), message, Toasty.LENGTH_LONG).show();
    }


}
