package br.ufop.ruapplicationmvp.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import br.ufop.ruapplicationmvp.R;
import br.ufop.ruapplicationmvp.contract.MenuContract;
import br.ufop.ruapplicationmvp.model.entity.Day;
import br.ufop.ruapplicationmvp.model.entity.Demand;
import br.ufop.ruapplicationmvp.model.entity.Dish;
import br.ufop.ruapplicationmvp.model.entity.User;
import br.ufop.ruapplicationmvp.model.response.MenuResponse;
import br.ufop.ruapplicationmvp.presenter.MenuPresenter;
import br.ufop.ruapplicationmvp.service.api.UserManager;
import br.ufop.ruapplicationmvp.util.Constants;
import br.ufop.ruapplicationmvp.util.DialogManager;
import br.ufop.ruapplicationmvp.util.RecyclerItemClickListener;
import br.ufop.ruapplicationmvp.view.DetailDishActivity;
import br.ufop.ruapplicationmvp.view.FormMenuActivity;
import br.ufop.ruapplicationmvp.view.adapter.MenuAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

import static android.app.Activity.RESULT_OK;
import static android.view.View.VISIBLE;


public class MenuFragment extends Fragment
        implements MenuContract.View, RecyclerItemClickListener.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {


    @BindView(R.id.menu_recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.menu_header)
    RelativeLayout mHeader;
    @BindView(R.id.menu_imageview)
    ImageView mImageview;
    @BindView(R.id.menu_type)
    TextView mType;
    @BindView(R.id.menu_date)
    TextView mDate;
    @BindView(R.id.menu_SwipeRefresh)
    SwipeRefreshLayout mRefreshLayout;
    @BindView(R.id.menu_participation_button)
    Button mParticipationButton;
    @BindView(R.id.menu_close_reason)
    TextView mReason;
    @BindView(R.id.menu_error_container)
    LinearLayout mErrorContainer;
    @BindView(R.id.menu_container)
    FrameLayout menuContainer;

    private Day day;
    private int type;
    private MenuAdapter mAdapter;
    private MenuPresenter mPresenter;
    private int requestCode = 321;
    private AlertDialog mDialog;
    private int participation;

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

        ButterKnife.bind(this, view);
        mPresenter = new MenuPresenter(this, getActivity().getSharedPreferences("prefs", getActivity().MODE_PRIVATE));
        mRefreshLayout.setOnRefreshListener(this);
        setTypeHeader(type);
        mDate.setText(day.getDate());
        mPresenter.onRefresh(type, day.getDate());
        mDialog = DialogManager.loadingDialog(getContext());
        User user = UserManager.getInstance(getActivity().getSharedPreferences("prefs", getActivity().MODE_PRIVATE)).getUser();
        if (user.getType() == Constants.CLIENT)
            mPresenter.getParticipation(type, day.getDate());


    }

    private void setTypeHeader(int type) {
        switch (type) {
            case Constants.ALMOCO:
                mImageview.setImageResource(R.drawable.sun);
                mType.setText(R.string.almoco);
                mHeader.setBackgroundResource(R.color.darkYellow);
                break;
            case Constants.JANTAR:
                mImageview.setImageResource(R.drawable.night);
                mType.setText(R.string.jantar);
                mHeader.setBackgroundResource(R.color.night);
                break;
        }
    }

    private void setAdapter(MenuAdapter mAdapter) {
        mRecyclerView.removeAllViewsInLayout();
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getContext(),
                RecyclerView.VERTICAL, false);

        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), mRecyclerView, this));
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_menu, container, false);
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
            mPresenter.onRefresh(type, day.getDate());
        }
    }

    @Override
    public void onItemLongClick(View view, int position) {

    }

    @Override
    public void onRefresh() {
        mPresenter.onRefresh(type, day.getDate());
    }


    @Override
    public void showProgress() {
        mRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        mRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onCommitParticipation(Demand demand, int option) {


        switch (option) {
            case Constants.SHOW:
                participation = demand.getParcipation();
                setButton(demand.getParcipation());
                break;
            case Constants.INSERT:
                participation = Constants.CONFIRMED;
                setButton(Constants.CONFIRMED);
                break;
            case Constants.DELETE:
                participation = Constants.NOT_CONFIRMED;
                setButton(Constants.NOT_CONFIRMED);
                break;
        }

    }

    public void setButton(int participation) {
        if (participation == Constants.NOT_CONFIRMED) {
            mParticipationButton.setBackgroundResource(R.drawable.rounded_button_commit);
            mParticipationButton.setText("Participar");
        } else {
            mParticipationButton.setBackgroundResource(R.drawable.rounded_button_return);
            mParticipationButton.setText("Cancelar Participação");
        }
        mParticipationButton.setVisibility(VISIBLE);
    }

    @Override
    public void onRefreshFinished(MenuResponse response) {

        if (response.getMeal().getStatus() == Constants.FECHADO) {
            setCloseMeal(response.getMeal().getReason());
        }
        if (response.getDishes() != null) {
            setDishList(completeData(response.getDishes()));
        }


    }

    private void setCloseMeal(String reason) {
        mErrorContainer.setVisibility(VISIBLE);
        mReason.setText(reason);
    }

    private void setDishList(List<Dish> dishes) {
        if (mAdapter == null) {
            mAdapter = new MenuAdapter(getContext(), dishes);
            setAdapter(mAdapter);
        } else {
            mAdapter.refresh(dishes);
        }
    }

    @Override
    public void onFailure(String message) {
        Toasty.warning(getContext(), message, Toasty.LENGTH_LONG).show();
    }

    @Override
    public void onError(String message, int code) {
        if (code == 404)
            setCloseMeal("");
        else
            Toasty.error(getContext(), message, Toasty.LENGTH_LONG).show();
    }

    @Override
    public void showButtonProgress() {
        mDialog.show();
    }

    @Override
    public void hideButtonProgress() {
        mDialog.hide();
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


    @OnClick(R.id.menu_participation_button)
    public void onViewClicked() {
        switch (participation) {
            case Constants.CONFIRMED:
                mPresenter.deleteParticipation(type, day.getDate());
                break;
            case Constants.NOT_CONFIRMED:
                mPresenter.insertParticipation(type, day.getDate(), 0);
                break;
        }
    }
}
