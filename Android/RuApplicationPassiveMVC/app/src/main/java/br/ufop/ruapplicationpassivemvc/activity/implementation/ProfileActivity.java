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
import br.ufop.ruapplicationpassivemvc.activity.listener.ProfileControllerListener;
import br.ufop.ruapplicationpassivemvc.controller.implementation.ProfileController;
import br.ufop.ruapplicationpassivemvc.model.entity.User;
import br.ufop.ruapplicationpassivemvc.util.Networking;
import br.ufop.ruapplicationpassivemvc.util.PhotoPath;
import br.ufop.ruapplicationpassivemvc.view.ProfileView;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class ProfileActivity extends AppCompatActivity implements ProfileControllerListener, View.OnClickListener {
    private final int REQUEST_GALLERY_CODE = 200;
    private ProfileView view;
    private ProfileController controller;
    private MultipartBody.Part photo;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setTitle("Perfil");

        view = findViewById(R.id.profile_container);
        controller = new ProfileController(view, this);
        view.setListeners(this);

        initiate();

    }

    private void initiate() {
        Bundle mBundle = getIntent().getExtras();
        if (mBundle != null && mBundle.get("user") != null) {
            user = (User) mBundle.get("user");
            view.setEmail(user.getEmail());
            view.setName(user.getName());
            view.setCPF(user.getCpf());
            view.loadPhoto(user.getPhoto());

        }
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
                if (Networking.isNetworkConnected(view))
                    controller.update(user.getName(), photo);
                return true;
            default:
                return super.onOptionsItemSelected(item);
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
                        // RequestBody photoname = RequestBody.create(MediaType.parse("text/plain"), file.getName());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }


            } else if (resultCode == Activity.RESULT_CANCELED) {

            }
        }
    }

    @Override
    public void onClick(View v) {
        Intent openGalleryIntent = new Intent(Intent.ACTION_PICK);
        openGalleryIntent.setType("image/*");
        startActivityForResult(openGalleryIntent, REQUEST_GALLERY_CODE);
    }

    @Override
    public void onActionExecuted(String reply) {
        Toast.makeText(this, reply, Toast.LENGTH_SHORT).show();
    }
}
