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
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.List;

import br.ufop.ruapplicationmvvm.R;
import br.ufop.ruapplicationmvvm.ViewModelFactory;
import br.ufop.ruapplicationmvvm.databinding.FragmentWeekMenuBinding;
import br.ufop.ruapplicationmvvm.model.entity.Week;
import br.ufop.ruapplicationmvvm.util.RecyclerItemClickListener;
import br.ufop.ruapplicationmvvm.util.Status;
import br.ufop.ruapplicationmvvm.view.DayMealActivity;
import br.ufop.ruapplicationmvvm.view.adapter.WeekMenuAdapter;
import br.ufop.ruapplicationmvvm.viewmodel.WeekViewModel;
import es.dmoral.toasty.Toasty;

import static android.content.Context.MODE_PRIVATE;


public class WeekMenuFragment extends Fragment
        implements RecyclerItemClickListener.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {

    private WeekMenuAdapter mAdapter;
    private WeekViewModel mViewModel;
    private FragmentWeekMenuBinding binding;

    public WeekMenuFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_week_menu, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Cardápios");
        mViewModel = ViewModelProviders
                .of(this, new ViewModelFactory(getActivity().getSharedPreferences("prefs", MODE_PRIVATE)))
                .get(WeekViewModel.class);

        mViewModel.getResult().observe(this, result -> {
            onLoading(result.getStatus() == Status.LOADING);

            if (result.getStatus() == Status.SUCCESS)
                onRefreshFinished(result.getData());
            else if (result.getStatus() == Status.ERROR)
                onError(result.getError());
            else if (result.getStatus() == Status.FAILURE)
                onFailure(result.getFailure());
        });


        binding.weekMenuSwipeRefresh.setOnRefreshListener(this);
        mViewModel.getWeeks();

    }

    private void onLoading(boolean b) {
        if (b)
            binding.weekMenuSwipeRefresh.setRefreshing(true);
        else
            binding.weekMenuSwipeRefresh.setRefreshing(false);
    }


    private void onRefreshFinished(List<Week> weeks) {
        if (mAdapter == null) {
            mAdapter = new WeekMenuAdapter(getContext(), weeks);
            setAdapter(mAdapter);
        } else {
            mAdapter.refresh(weeks);
        }
    }

    private void setAdapter(WeekMenuAdapter mAdapter) {
        binding.weekMenuRecyclerview.removeAllViewsInLayout();
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getContext(),
                RecyclerView.VERTICAL, false);
        binding.weekMenuRecyclerview.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL));
        binding.weekMenuRecyclerview.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), binding.weekMenuRecyclerview, this));
        binding.weekMenuRecyclerview.setLayoutManager(mLinearLayoutManager);
        binding.weekMenuRecyclerview.setAdapter(mAdapter);
    }

    private void onError(String message) {
        Toasty.error(getContext(), message, Toasty.LENGTH_LONG).show();
    }


    private void onFailure(String message) {
        Toasty.warning(getContext(), message, Toasty.LENGTH_LONG).show();
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(getContext(), DayMealActivity.class);
        intent.putExtra("week", mAdapter.getItem(position));
        startActivity(intent);
    }

    @Override
    public void onItemLongClick(View view, int position) {

    }


    @Override
    public void onRefresh() {
        mViewModel.getWeeks();
    }
}



