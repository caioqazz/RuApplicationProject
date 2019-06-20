package br.ufop.ruapplicationmvp.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import br.ufop.ruapplicationmvp.R;
import br.ufop.ruapplicationmvp.contract.DemandContract;
import br.ufop.ruapplicationmvp.model.entity.Day;
import br.ufop.ruapplicationmvp.presenter.DemandPresenter;
import br.ufop.ruapplicationmvp.util.Constants;
import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;


public class DemandFragment extends Fragment implements DemandContract.View {
    @BindView(R.id.demand_imageview)
    ImageView mImage;
    @BindView(R.id.demand_type)
    TextView mTextTitle;
    @BindView(R.id.demand_header)
    RelativeLayout mLayout;

    @BindView(R.id.demand_textview_normal)
    TextView mTvNormal;
    @BindView(R.id.demand_textview_vegetariano)
    TextView mTvVeg;
    @BindView(R.id.demand_textview_total)
    TextView mTvTotal;

    @BindView(R.id.demand_loading_dialog)
    LinearLayout mLoading;
    @BindView(R.id.demand_content_container)
    LinearLayout mContainer;
    private DemandPresenter mPresenter;


    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_demand, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);


        if (getArguments() != null) {
            Day day = (Day) getArguments().getSerializable("day");
            int type = getArguments().getInt("type");
            getArguments().clear();
            setHeader(type);
            mPresenter = new DemandPresenter(this,
                    getActivity().getSharedPreferences("prefs", getActivity().MODE_PRIVATE), day.getDate(), type);

            mPresenter.getDemand();

        }

    }

    @Override
    public void showProgress() {
        mLoading.setVisibility(View.VISIBLE);
        mContainer.setVisibility(View.INVISIBLE);
    }

    @Override
    public void hideProgress() {
        mLoading.setVisibility(View.INVISIBLE);
        mContainer.setVisibility(View.VISIBLE);
    }

    @Override
    public void onError(String message) {
        Toasty.error(getContext(), message, Toasty.LENGTH_LONG).show();
    }

    @Override
    public void onFailure(Throwable t) {
        Toasty.warning(getContext(), t.getMessage(), Toasty.LENGTH_LONG).show();
    }

    @Override
    public void setHeader(int type) {
        switch (type) {
            case Constants.ALMOCO:
                mImage.setImageResource(R.mipmap.sun);
                mTextTitle.setText("Almoço");
                mLayout.setBackgroundResource(R.color.darkYellow);
                break;
            case Constants.JANTAR:
                mImage.setImageResource(R.mipmap.night);
                mTextTitle.setText("Jantar");
                mLayout.setBackgroundResource(R.color.night);
                break;
        }
    }

    @Override
    public void setValues(int countPrincipal, int countVegetariano, int countTotal) {
        mTvNormal.setText(countPrincipal + " Pessoa(s)");
        mTvVeg.setText(countVegetariano + " Pessoa(s)");
        mTvTotal.setText(countTotal + " Pessoa(s)");
    }


}
