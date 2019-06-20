package br.ufop.ruapplicationmvp.view;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;

import br.ufop.ruapplicationmvp.R;
import br.ufop.ruapplicationmvp.contract.FormDishContract;
import br.ufop.ruapplicationmvp.model.entity.Dish;
import br.ufop.ruapplicationmvp.presenter.FormDishPresenter;
import br.ufop.ruapplicationmvp.service.api.RetrofitBuilder;
import br.ufop.ruapplicationmvp.util.DialogManager;
import br.ufop.ruapplicationmvp.util.PhotoPath;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class FormDishActivity extends AppCompatActivity
        implements FormDishContract.View {
    private static final int REQUEST_GALLERY_CODE = 200;
    @BindView(R.id.form_dish_imageview)
    CircleImageView mImageview;
    @BindView(R.id.form_dish_name)
    EditText mName;
    @BindView(R.id.form_dish_spinner)
    Spinner mSpinner;
    @BindView(R.id.form_dish_description)
    EditText mDescription;
    private Dish dish;
    private MultipartBody.Part photo;
    private FormDishPresenter mPresenter;
    private AlertDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_dish);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Novo Prato");

        mDialog = DialogManager.loadingDialog(this);
        mPresenter = new FormDishPresenter(this, getSharedPreferences("prefs", MODE_PRIVATE));
        initiateSpinner();
        initiate();
    }

    private void initiateSpinner() {
        String[] types = {"Selecione", "Principal", "Vegetariano", "Guarnição",
                "Arroz", "Feijão", "Bebida", "Sobremesa"};
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, types);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(spinnerArrayAdapter);// The drop down view

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
                if (dish == null) {
                    mPresenter.insert(photo, mName.getText().toString(), mSpinner.getSelectedItemPosition() - 1,
                            mDescription.getText().toString());
                } else {
                    mPresenter.update(photo, dish.getId(),
                            mName.getText().toString(), mSpinner.getSelectedItemPosition() - 1,
                            mDescription.getText().toString());
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }


    private void initiate() {
        Bundle mBundle = getIntent().getExtras();
        if (mBundle != null && mBundle.get("dish") != null) {
            setTitle("Editar");
            dish = (Dish) mBundle.get("dish");
            mName.setText(dish.getName());
            mDescription.setText(dish.getDescription());
            mSpinner.setSelection(dish.getType() + 1);
            if (dish.getPhoto() != null && !dish.getPhoto().isEmpty()) {
                Picasso.get().load(RetrofitBuilder.BASE_PARSE_URL + dish.getPhoto()).error(R.drawable.user).into(mImageview);
            }
            mBundle.clear();
        }
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_GALLERY_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {

                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
                        loadPhoto(bitmap);
                        String filePath = PhotoPath.getRealPathFromURI(this, data.getData());
                        File file = new File(filePath);
                        RequestBody mFile = RequestBody.create(MediaType.parse("image/*"), file);
                        photo = MultipartBody.Part.createFormData("image", file.getName(), mFile);
                        RequestBody photoname = RequestBody.create(MediaType.parse("text/plain"), file.getName());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }


            } else if (resultCode == Activity.RESULT_CANCELED) {
            }
        }
    }


    private void loadPhoto(Bitmap bitmap) {
        mImageview.setImageBitmap(bitmap);
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
    public void onCommitFinished(Dish dish, int option) {
        Toasty.success(this, dish.getName(), Toasty.LENGTH_LONG).show();
    }

    @Override
    public void onFailure(String message, int option) {
        Toasty.warning(this, message, Toasty.LENGTH_LONG).show();
    }

    @Override
    public void onError(String message, int option) {
        Toasty.error(this, message, Toasty.LENGTH_LONG).show();
    }

    @OnClick(R.id.form_dish_camera_button)
    public void onViewClicked() {
        Intent openGalleryIntent = new Intent(Intent.ACTION_PICK);
        openGalleryIntent.setType("image/*");
        startActivityForResult(openGalleryIntent, REQUEST_GALLERY_CODE);

    }
}
