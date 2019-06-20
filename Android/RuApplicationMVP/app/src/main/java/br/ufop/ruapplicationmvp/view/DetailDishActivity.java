package br.ufop.ruapplicationmvp.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import br.ufop.ruapplicationmvp.R;
import br.ufop.ruapplicationmvp.contract.DetailDishContract;
import br.ufop.ruapplicationmvp.model.entity.Classification;
import br.ufop.ruapplicationmvp.model.entity.Dish;
import br.ufop.ruapplicationmvp.model.entity.User;
import br.ufop.ruapplicationmvp.presenter.DetailDishPresenter;
import br.ufop.ruapplicationmvp.service.api.RetrofitBuilder;
import br.ufop.ruapplicationmvp.service.api.UserManager;
import br.ufop.ruapplicationmvp.util.Constants;
import br.ufop.ruapplicationmvp.util.DialogManager;
import br.ufop.ruapplicationmvp.util.Networking;
import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;

public class DetailDishActivity extends AppCompatActivity
        implements DetailDishContract.View {
    @BindView(R.id.detail_dish_imageview)
    ImageView mImageview;
    @BindView(R.id.detail_dish_name)
    TextView mName;
    @BindView(R.id.detail_dish_description)
    TextView mDescription;
    @BindView(R.id.detail_dish_rating)
    RatingBar mRating;
    @BindView(R.id.detail_dish_checkbox)
    CheckBox mCheckbox;
    @BindView(R.id.detail_dish_comment)
    EditText mComment;
    @BindView(R.id.detail_dish_caption)
    TextView mCaption;
    private User user;
    private Dish dish;
    private DetailDishPresenter mPresenter;
    private AlertDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_dish);
        ButterKnife.bind(this);
        setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mDialog = DialogManager.loadingDialog(this);
        mPresenter = new DetailDishPresenter(this, getSharedPreferences("prefs", MODE_PRIVATE));

        initiate();

        UserManager userManager = UserManager.getInstance(getSharedPreferences("prefs", MODE_PRIVATE));
        user = userManager.getUser();
        if (user.getType() == Constants.CLIENT) {
            clientView();
            mPresenter.showClassification(dish.getId());
        }

    }

    private void clientView() {
        mCaption.setText("Avalie esse prato");
        mCheckbox.setVisibility(View.VISIBLE);
        mComment.setVisibility(View.VISIBLE);

    }

    private void initiate() {
        Bundle mBundle = getIntent().getExtras();

        if (mBundle != null && mBundle.get("dish") != null) {
            dish = (Dish) mBundle.get("dish");

            mName.setText(dish.getName());
            mDescription.setText(dish.getDescription());

            if (dish.getAverage() != null) {
                mRating.setRating(dish.getAverage());
            } else {
                mRating.setRating(5);
            }


            if (dish.getPhoto() != null && !dish.getPhoto().isEmpty()) {
                Picasso.get().load(RetrofitBuilder.BASE_PARSE_URL + dish.getPhoto()).error(R.drawable.image).into(mImageview);
            }
            mBundle.clear();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (user.getType() == Constants.MANAGER) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_detail, menu);
        } else {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.send_menu, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_edit:
                Intent intent = new Intent(this, FormDishActivity.class);
                intent.putExtra("dish", dish);
                startActivity(intent);
                finish();
                return true;
            case R.id.menu_delete:
                DialogManager.confimationDelete(this, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (Networking.isNetworkConnected(DetailDishActivity.this))
                            mPresenter.delete(dish.getId());
                    }
                }, dish.getName());

                return true;
            case R.id.send:
                mPresenter.updateClassification(dish.getId(), mRating.getRating(), mCheckbox.isChecked(), mComment.getText().toString());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }


    @Override
    public void showProgress() {
        mDialog.show();
    }


    @Override
    public void hideProgress() {
        mDialog.hide();
    }


    @Override
    public void onFinished(int option) {
        Toasty.success(this, "Success", Toasty.LENGTH_LONG).show();
        if (option == Constants.DELETE)
            finish();
    }

    @Override
    public void onShowFinished(Classification classification) {
        mRating.setRating(classification.getRating());
        mComment.setText(classification.getComment());
        setCheck(classification.getOption());
    }

    private void setCheck(int option) {
        if (option == 1) {
            mCheckbox.setChecked(false);
        } else {
            mCheckbox.setChecked(true);
        }
    }

    @Override
    public void onFailure(String message, int option) {
        Toasty.warning(this, message, Toasty.LENGTH_LONG).show();
    }

    @Override
    public void onError(String message, int option) {

        Toasty.error(this, message, Toasty.LENGTH_LONG).show();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }
}
