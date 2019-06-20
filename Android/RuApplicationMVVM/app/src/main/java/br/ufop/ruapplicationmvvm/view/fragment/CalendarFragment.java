package br.ufop.ruapplicationmvvm.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.List;

import br.ufop.ruapplicationmvvm.R;
import br.ufop.ruapplicationmvvm.ViewModelFactory;
import br.ufop.ruapplicationmvvm.databinding.FragmentCalendarBinding;
import br.ufop.ruapplicationmvvm.model.entity.Meal;
import br.ufop.ruapplicationmvvm.model.entity.User;
import br.ufop.ruapplicationmvvm.service.api.UserManager;
import br.ufop.ruapplicationmvvm.util.Networking;
import br.ufop.ruapplicationmvvm.util.RecyclerItemClickListener;
import br.ufop.ruapplicationmvvm.util.Status;
import br.ufop.ruapplicationmvvm.view.FormCalendarActivity;
import br.ufop.ruapplicationmvvm.view.adapter.CalendarAdapter;
import br.ufop.ruapplicationmvvm.viewmodel.CalendarViewModel;
import es.dmoral.toasty.Toasty;

import static android.content.Context.MODE_PRIVATE;

public class CalendarFragment extends Fragment
        implements SwipeRefreshLayout.OnRefreshListener, RecyclerItemClickListener.OnItemClickListener, View.OnClickListener {

    private CalendarViewModel mViewModel;
    private CalendarAdapter mAdapter;
    private FragmentCalendarBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_calendar, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Calendário de Funcionamento");
        mViewModel = ViewModelProviders
                .of(this, new ViewModelFactory(getActivity().getSharedPreferences("prefs", MODE_PRIVATE)))
                .get(CalendarViewModel.class);
        binding.calendarBtnAdd.setOnClickListener(this);
        mViewModel.getResult().observe(this, result -> {
            onLoading(result.getStatus() == Status.LOADING);

            if (result.getStatus() == Status.SUCCESS)
                onRefreshFinished(result.getData());
            else if (result.getStatus() == Status.ERROR)
                onError(result.getError());
            else if (result.getStatus() == Status.FAILURE)
                onFailure(result.getFailure());
        });
        binding.calendarSwipeRefresh.setOnRefreshListener(this);


        UserManager userManager = UserManager.getInstance(getActivity().getSharedPreferences("prefs", getActivity().MODE_PRIVATE));
        User user = userManager.getUser();
        binding.setUser(user);
        mViewModel.getCalendar();
    }

    private void onLoading(boolean b) {
        if (b)
            binding.calendarSwipeRefresh.setRefreshing(true);
        else
            binding.calendarSwipeRefresh.setRefreshing(false);
    }


    private void form(Meal meal) {
        Intent intent = new Intent(getContext(), FormCalendarActivity.class);
        intent.putExtra("meal", meal);
        startActivity(intent);
    }


    private void onRefreshFinished(List<Meal> meals) {
        binding.calendarSwipeRefresh.setRefreshing(false);
        if (mAdapter == null) {
            mAdapter = new CalendarAdapter(getContext(), meals);
            setAdapter(mAdapter);
        } else {
            mAdapter.refresh(meals);
        }

    }

    private void setAdapter(CalendarAdapter mAdapter) {
        binding.calendarRecyclerview.removeAllViewsInLayout();
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getContext(),
                RecyclerView.VERTICAL, false);
        binding.calendarRecyclerview.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), binding.calendarRecyclerview, this));

        binding.calendarRecyclerview.setLayoutManager(mLinearLayoutManager);
        binding.calendarRecyclerview.setAdapter(mAdapter);
    }

    private void onError(String message) {
        Toasty.error(getContext(), message, Toasty.LENGTH_LONG).show();
    }


    private void onFailure(String message) {
        Toasty.warning(getContext(), message, Toasty.LENGTH_LONG).show();
    }

    @Override
    public void onRefresh() {
        mViewModel.getCalendar();
    }

    @Override
    public void onItemClick(View view, int position) {

    }

    @Override
    public void onItemLongClick(View view, int position) {
        if (UserManager.getInstance(getActivity().getSharedPreferences("prefs", getContext().MODE_PRIVATE)).getUser().getType() != 1) {
            PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
            popupMenu.inflate(R.menu.menu_detail);
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {

                    switch (item.getItemId()) {
                        case R.id.menu_edit:
                            if (Networking.isNetworkConnected(getContext()))
                                form(mAdapter.getItem(position));
                            break;
                        case R.id.menu_delete:
                            if (Networking.isNetworkConnected(getContext()))
                                form(mAdapter.getItem(position));
                            break;

                        default:
                            break;
                    }
                    return false;
                }
            });
            popupMenu.show();
        }
    }

    @Override
    public void onClick(View view) {
        startActivity(new Intent(getContext(), FormCalendarActivity.class));
    }
}
