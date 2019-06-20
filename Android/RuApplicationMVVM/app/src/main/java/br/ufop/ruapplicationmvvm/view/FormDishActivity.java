package br.ufop.ruapplicationmvvm.view;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;

import br.ufop.ruapplicationmvvm.R;
import br.ufop.ruapplicationmvvm.ViewModelFactory;
import br.ufop.ruapplicationmvvm.databinding.ActivityFormDishBinding;
import br.ufop.ruapplicationmvvm.model.entity.Dish;
import br.ufop.ruapplicationmvvm.service.api.RetrofitBuilder;
import br.ufop.ruapplicationmvvm.util.DialogManager;
import br.ufop.ruapplicationmvvm.util.PhotoPath;
import br.ufop.ruapplicationmvvm.viewmodel.FormDishViewModel;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class FormDishActivity extends AppCompatActivity {
    private static final int REQUEST_GALLERY_CODE = 200;
    private Dish dish;
    private MultipartBody.Part photo;
    private AlertDialog mDialog;
    private FormDishViewModel mViewModel;
    private ActivityFormDishBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_form_dish);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Novo Prato");

        mDialog = DialogManager.loadingDialog(this);
        initiateSpinner();
        initiate();
        mViewModel = ViewModelProviders
                .of(this, new ViewModelFactory(getSharedPreferences("prefs", MODE_PRIVATE)))
                .get(FormDishViewModel.class);


        mViewModel.getResult().observe(this, result -> {
//            loading(result.getStatus() == Status.LOADING);
//            //    onCommitFinished(result.getData().body());
        });

    }


    private void loading(boolean isLoading) {
        if (isLoading) {
            mDialog.show();
        } else {
            mDialog.hide();
        }
    }

    private void initiateSpinner() {
        String[] types = {"Selecione", "Principal", "Vegetariano", "Guarnição",
                "Arroz", "Feijão", "Bebida", "Sobremesa"};
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, types);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.formDishSpinner.setAdapter(spinnerArrayAdapter);// The drop down view

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_save, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save:
                onButtonCommitClick();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void onButtonCommitClick() {
//        if (dish == null) {
//            mViewModel.insert(FormDishViewModeltedItemPosition() - 1,
//                    mDescription.getText().toString());
//        } else {
//            mViewModel.update(photo, dish.getId(),
//                    mName.getText().toString(), mSpinner.getSelectedItemPosition() - 1,
//                    mDescription.getText().toString());
//        }
    }

    private void initiate() {
        Bundle mBundle = getIntent().getExtras();
        if (mBundle != null && mBundle.get("dish") != null) {
            setTitle("Editar");
            dish = (Dish) mBundle.get("dish");
            binding.formDishName.setText(dish.getName());
            binding.formDishDescription.setText(dish.getDescription());
            binding.formDishSpinner.setSelection(dish.getType() + 1);
            if (dish.getPhoto() != null && !dish.getPhoto().isEmpty()) {
                Picasso.get().load(RetrofitBuilder.BASE_PARSE_URL + dish.getPhoto()).error(R.drawable.user).into(binding.formDishImageview);
            }
            mBundle.clear();
        }
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_GALLERY_CODE && resultCode == Activity.RESULT_OK && data != null) {
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
                binding.formDishImageview.setImageBitmap(bitmap);
                String filePath = PhotoPath.getRealPathFromURI(this, data.getData());
                File file = new File(filePath);
                RequestBody mFile = RequestBody.create(MediaType.parse("image/*"), file);
                photo = MultipartBody.Part.createFormData("image", file.getName(), mFile);
                RequestBody photoname = RequestBody.create(MediaType.parse("text/plain"), file.getName());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private void onCommitFinished(Dish dish) {
        Toasty.success(this, dish.getName(), Toasty.LENGTH_LONG).show();
    }

    private void onFailure(String message) {
        Toasty.warning(this, message, Toasty.LENGTH_LONG).show();

    }

    private void onError(String message) {
        Toasty.error(this, message, Toasty.LENGTH_LONG).show();

    }

    @OnClick(R.id.form_dish_camera_button)
    public void onViewClicked() {
        Intent openGalleryIntent = new Intent(Intent.ACTION_PICK);
        openGalleryIntent.setType("image/*");
        startActivityForResult(openGalleryIntent, REQUEST_GALLERY_CODE);

    }
}
