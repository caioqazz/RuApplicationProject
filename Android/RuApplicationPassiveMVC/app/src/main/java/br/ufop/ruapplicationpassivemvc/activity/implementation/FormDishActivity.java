package br.ufop.ruapplicationpassivemvc.activity.implementation;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.usuario.ruapplicationpassivemvc.R;
import br.ufop.ruapplicationpassivemvc.activity.listener.FormDishControllerListener;
import br.ufop.ruapplicationpassivemvc.controller.implementation.FormDishController;
import br.ufop.ruapplicationpassivemvc.model.entity.Dish;
import br.ufop.ruapplicationpassivemvc.service.api.RetrofitBuilder;
import br.ufop.ruapplicationpassivemvc.util.PhotoPath;
import br.ufop.ruapplicationpassivemvc.view.FormDishView;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class FormDishActivity extends AppCompatActivity implements FormDishControllerListener, View.OnClickListener {
    private static final String TAG = "FormDishActivity";
    private static final int REQUEST_GALLERY_CODE = 200;
    FormDishController controller;
    Dish dish;
    FormDishView view;
    MultipartBody.Part photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_dish);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Novo Prato");

        view = findViewById(R.id.form_dish_container);
        controller = new FormDishController(view, this);
        view.setListeners(this);
        initiate();
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
    public void onActionExecuted(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.save:
                if (dish == null) {
                    controller.insert(photo);
                } else {
                    controller.upload(photo, dish.getId());
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onClick(View v) {
        Intent openGalleryIntent = new Intent(Intent.ACTION_PICK);
        openGalleryIntent.setType("image/*");
        startActivityForResult(openGalleryIntent, REQUEST_GALLERY_CODE);


    }

    private void initiate() {
        Bundle mBundle = getIntent().getExtras();
        if (mBundle != null && mBundle.get("dish") != null) {
            setTitle("Editar");
            dish = (Dish) mBundle.get("dish");
            view.setName(dish.getName());
            view.setDescription(dish.getDescription());
            view.setType(dish.getType());
            if (dish.getPhoto() != null && !dish.getPhoto().isEmpty()) {
                Picasso.get().load(RetrofitBuilder.BASE_PARSE_URL + dish.getPhoto()).error(R.drawable.user).into(view.getFieldPhoto());
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
                        view.setPhoto(bitmap);
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
                Toast.makeText(this, "Canceled", Toast.LENGTH_SHORT).show();
            }
        }
    }


}
