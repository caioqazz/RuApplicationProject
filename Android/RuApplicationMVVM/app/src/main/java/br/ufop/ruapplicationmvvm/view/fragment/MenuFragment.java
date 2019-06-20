package br.ufop.ruapplicationmvvm.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import br.ufop.ruapplicationmvvm.R;
import br.ufop.ruapplicationmvvm.ViewModelFactory;
import br.ufop.ruapplicationmvvm.databinding.FragmentMenuBinding;
import br.ufop.ruapplicationmvvm.model.entity.Day;
import br.ufop.ruapplicationmvvm.model.entity.Demand;
import br.ufop.ruapplicationmvvm.model.entity.Dish;
import br.ufop.ruapplicationmvvm.model.entity.User;
import br.ufop.ruapplicationmvvm.model.response.MenuResponse;
import br.ufop.ruapplicationmvvm.service.api.UserManager;
import br.ufop.ruapplicationmvvm.util.Constants;
import br.ufop.ruapplicationmvvm.util.DialogManager;
import br.ufop.ruapplicationmvvm.util.RecyclerItemClickListener;
import br.ufop.ruapplicationmvvm.util.Status;
import br.ufop.ruapplicationmvvm.view.DetailDishActivity;
import br.ufop.ruapplicationmvvm.view.FormMenuActivity;
import br.ufop.ruapplicationmvvm.view.adapter.MenuAdapter;
import br.ufop.ruapplicationmvvm.viewmodel.MenuViewModel;
import es.dmoral.toasty.Toasty;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;
import static android.view.View.VISIBLE;


public class MenuFragment extends Fragment
        implements RecyclerItemClickListener.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {


    private Day day;
    private int type;
    private MenuAdapter mAdapter;
    private int requestCode = 321;
    private int participation;
    private FragmentMenuBinding binding;
    private MenuViewModel mViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            day = (Day) getArguments().getSerializable("day");
            type = getArguments().getInt("type");
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.setType(type);
        binding.menuSwipeRefresh.setOnRefreshListener(this);
        binding.menuParticipationButton.setOnClickListener(this);
        binding.menuDate.setText(day.getDate());
        mViewModel = ViewModelProviders
                .of(this, new ViewModelFactory(getActivity().getSharedPreferences("prefs", MODE_PRIVATE)))
                .get(MenuViewModel.class);

        mViewModel.getMenuResult().observe(this, result -> {
            onListLoading(result.getStatus() == Status.LOADING);

            if (result.getStatus() == Status.SUCCESS)
                onRefreshFinished(result.getData());
            else if (result.getStatus() == Status.ERROR)
                onListError();
            else if (result.getStatus() == Status.FAILURE)
                onFailure(result.getFailure());

        });

        mViewModel.getDemandResult().observe(this, result -> {
            onLoading(result.getStatus() == Status.LOADING);

            if (result.getStatus() == Status.SUCCESS)
                onCommitParticipation(result.getData());
            else if (result.getStatus() == Status.ERROR)
                onError(result.getError());
            else if (result.getStatus() == Status.FAILURE)
                onFailure(result.getFailure());
        });
        User user = UserManager.getInstance(getActivity().getSharedPreferences("prefs", getActivity().MODE_PRIVATE)).getUser();
        mViewModel.getMenu(type, day.getDate());
        if (user.getType() == Constants.CLIENT)
            mViewModel.getParticipation(type, day.getDate());


    }

    private void onLoading(boolean b) {
        if (b) {
            binding.menuParticipationButton.setVisibility(View.INVISIBLE);
        } else {
            binding.menuParticipationButton.setVisibility(VISIBLE);

        }
    }

    private void onListError() {
        setCloseMeal("");
    }

    private void onListLoading(boolean b) {
        if (b)
            binding.menuSwipeRefresh.setRefreshing(true);
        else
            binding.menuSwipeRefresh.setRefreshing(false);
    }


    private void setAdapter(MenuAdapter mAdapter) {
        binding.menuRecyclerview.removeAllViewsInLayout();
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getContext(),
                RecyclerView.VERTICAL, false);

        binding.menuRecyclerview.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), binding.menuRecyclerview, this));
        binding.menuRecyclerview.setLayoutManager(mLinearLayoutManager);
        binding.menuRecyclerview.setAdapter(mAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_menu, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onItemClick(View view, int position) {
        User user = UserManager.getInstance(getActivity().getSharedPreferences("prefs", getActivity().MODE_PRIVATE)).getUser();
        if (user.getType() == Constants.MANAGER) {
            openEditForm(position);
        } else {
            openDetailDish(position);
        }
    }

    private void openDetailDish(int position) {
        Intent intent = new Intent(getContext(), DetailDishActivity.class);
        if (mAdapter.getItem(position).getId() != -1) {
            intent.putExtra("dish", mAdapter.getItem(position));
            startActivity(intent);
        }
    }

    private void openEditForm(int position) {
        Intent intent = new Intent(getContext(), FormMenuActivity.class);
        if (mAdapter.getItem(position).getId() != -1) {
            intent.putExtra("dish", mAdapter.getItem(position));
        }
        intent.putExtra("dish_type", type);
        intent.putExtra("day", day);
        intent.putExtra("type", this.type);

        startActivityForResult(intent, requestCode);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (this.requestCode == requestCode && resultCode == RESULT_OK) {
            mViewModel.getMenu(type, day.getDate());
        }
    }

    @Override
    public void onItemLongClick(View view, int position) {

    }

    @Override
    public void onRefresh() {
        mViewModel.getMenu(type, day.getDate());
    }


    public void onCommitParticipation(Demand demand) {

        if (demand != null) {
            participation = demand.getParcipation();
            setButton(demand.getParcipation());
        } else {
            participation = Constants.NOT_CONFIRMED;
            setButton(Constants.NOT_CONFIRMED);
        }


    }

    public void setButton(int participation) {
        if (participation == Constants.NOT_CONFIRMED) {
            binding.menuParticipationButton.setBackgroundResource(R.drawable.rounded_button_commit);
            binding.menuParticipationButton.setText("Participar");
        } else {
            binding.menuParticipationButton.setBackgroundResource(R.drawable.rounded_button_return);
            binding.menuParticipationButton.setText("Cancelar Participação");
        }
        binding.menuParticipationButton.setVisibility(VISIBLE);
    }

    private void onRefreshFinished(MenuResponse response) {

        if (response.getMeal().getStatus() == Constants.FECHADO) {
            setCloseMeal(response.getMeal().getReason());
        }
        if (response.getDishes() != null) {
            setDishList(completeData(response.getDishes()));
        }


    }

    private void setCloseMeal(String reason) {
        binding.menuErrorContainer.setVisibility(VISIBLE);
        binding.menuCloseReason.setText(reason);
    }

    private void setDishList(List<Dish> dishes) {
        if (mAdapter == null) {
            mAdapter = new MenuAdapter(getContext(), dishes);
            setAdapter(mAdapter);
        } else {
            mAdapter.refresh(dishes);
        }
    }

    public void onFailure(String message) {
        Toasty.warning(getContext(), message, Toasty.LENGTH_LONG).show();
    }

    public void onError(String message) {
        Toasty.error(getContext(), message, Toasty.LENGTH_LONG).show();
    }


    private List<Dish> completeData(List<Dish> dishes) {
        ArrayList<Dish> dishArrayList = new ArrayList<Dish>();
        for (int i = 0; i < 8; i++) {
            dishArrayList.add(new Dish(-1, i, "Não selecionado", "", "Não selecionado"));
        }


        for (int j = 0; j < dishes.size(); j++) {
            dishArrayList.add(dishes.get(j).getType(), dishes.get(j));
        }
        return dishArrayList;

    }


    @Override
    public void onClick(View view) {
        switch (participation) {
            case Constants.CONFIRMED:
                mViewModel.deleteParticipation(type, day.getDate());
                break;
            case Constants.NOT_CONFIRMED:
                mViewModel.insertParticipation(type, day.getDate(), 0);
                break;
        }
    }
}
