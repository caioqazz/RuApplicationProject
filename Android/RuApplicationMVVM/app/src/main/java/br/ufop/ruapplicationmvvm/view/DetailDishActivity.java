package br.ufop.ruapplicationmvvm.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import br.ufop.ruapplicationmvvm.R;
import br.ufop.ruapplicationmvvm.ViewModelFactory;
import br.ufop.ruapplicationmvvm.databinding.ActivityDetailDishBinding;
import br.ufop.ruapplicationmvvm.model.entity.Classification;
import br.ufop.ruapplicationmvvm.model.entity.Dish;
import br.ufop.ruapplicationmvvm.model.entity.User;
import br.ufop.ruapplicationmvvm.service.api.UserManager;
import br.ufop.ruapplicationmvvm.util.Constants;
import br.ufop.ruapplicationmvvm.util.DialogManager;
import br.ufop.ruapplicationmvvm.util.Networking;
import br.ufop.ruapplicationmvvm.util.Status;
import br.ufop.ruapplicationmvvm.viewmodel.DetailDishViewModel;
import es.dmoral.toasty.Toasty;

public class DetailDishActivity extends AppCompatActivity {
    private User user;
    private Dish dish;
    private AlertDialog mDialog;
    private ActivityDetailDishBinding binding;
    private DetailDishViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_dish);
        setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mViewModel = ViewModelProviders
                .of(this, new ViewModelFactory(getSharedPreferences("prefs", MODE_PRIVATE)))
                .get(DetailDishViewModel.class);
        mDialog = DialogManager.loadingDialog(this);

        initiate();
        mViewModel.getClassificationResult().observe(this, result -> {
            onLoading(result.getStatus() == Status.LOADING);

            if (result.getStatus() == Status.SUCCESS)
                onShowFinished(result.getData());
            else if (result.getStatus() == Status.ERROR)
                onError(result.getError());
            else if (result.getStatus() == Status.FAILURE)
                onFailure(result.getFailure());
        });
        mViewModel.getDishResult().observe(this, result -> {
            onLoading(result.getStatus() == Status.LOADING);

            if (result.getStatus() == Status.SUCCESS)
                onFinished(Constants.DELETE);
            else if (result.getStatus() == Status.ERROR)
                onError(result.getError());
            else if (result.getStatus() == Status.FAILURE)
                onFailure(result.getFailure());
        });
        UserManager userManager = UserManager.getInstance(getSharedPreferences("prefs", MODE_PRIVATE));
        user = userManager.getUser();
        binding.setUser(user);

        if (user.getType() == Constants.CLIENT) {
            mViewModel.showClassification(dish.getId());
        }

    }

    private void onLoading(boolean b) {
        if (b)
            mDialog.show();
        else
            mDialog.hide();
    }


    private void initiate() {
        Bundle mBundle = getIntent().getExtras();

        if (mBundle != null && mBundle.get("dish") != null) {
            dish = (Dish) mBundle.get("dish");

            // binding.detailDishName.setText(dish.getName());
            //  binding.detailDishDescription.setText(dish.getDescription());

            if (dish.getAverage() != null) {
                binding.detailDishRating.setRating(dish.getAverage());
            } else {
                binding.detailDishRating.setRating(5);
            }


//            if (dish.getPhoto() != null && !dish.getPhoto().isEmpty()) {
//                Picasso.get().load(RetrofitBuilder.BASE_PARSE_URL + dish.getPhoto()).error(R.drawable.image).into(binding.detailDishImageview);
//            }
            binding.setDish(dish);
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
                            mViewModel.delete(dish.getId());
                    }
                }, dish.getName());

                return true;
            case R.id.send:
                mViewModel.updateClassification(dish.getId(), binding.detailDishRating.getRating(), binding.detailDishCheckbox.isChecked(), binding.detailDishComment.getText().toString());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }


    public void onFinished(int option) {
        Toasty.success(this, "Success", Toasty.LENGTH_LONG).show();
        if (option == Constants.DELETE)
            finish();
    }

    public void onShowFinished(Classification classification) {
        binding.detailDishRating.setRating(classification.getRating());
        binding.detailDishComment.setText(classification.getComment());
        setCheck(classification.getOption());
    }

    private void setCheck(int option) {
        if (option == 1) {
            binding.detailDishCheckbox.setChecked(false);
        } else {
            binding.detailDishCheckbox.setChecked(true);
        }
    }

    public void onFailure(String message) {
        Toasty.warning(this, message, Toasty.LENGTH_LONG).show();
    }

    public void onError(String message) {

        Toasty.error(this, message, Toasty.LENGTH_LONG).show();
    }


}
