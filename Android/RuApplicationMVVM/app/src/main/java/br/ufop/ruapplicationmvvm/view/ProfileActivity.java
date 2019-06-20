package br.ufop.ruapplicationmvvm.view;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import java.io.File;
import java.io.IOException;

import br.ufop.ruapplicationmvvm.R;
import br.ufop.ruapplicationmvvm.ViewModelFactory;
import br.ufop.ruapplicationmvvm.databinding.ActivityProfileBinding;
import br.ufop.ruapplicationmvvm.model.entity.User;
import br.ufop.ruapplicationmvvm.service.api.UserManager;
import br.ufop.ruapplicationmvvm.util.DialogManager;
import br.ufop.ruapplicationmvvm.util.Networking;
import br.ufop.ruapplicationmvvm.util.PhotoPath;
import br.ufop.ruapplicationmvvm.util.Status;
import br.ufop.ruapplicationmvvm.viewmodel.ProfileViewModel;
import es.dmoral.toasty.Toasty;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {
    private final int REQUEST_GALLERY_CODE = 200;

    private AlertDialog mDialog;
    private MultipartBody.Part photo;
    private User user;
    private ActivityProfileBinding binding;
    private ProfileViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mDialog = DialogManager.loadingDialog(this);
        setTitle("Perfil");
        mViewModel = ViewModelProviders
                .of(this, new ViewModelFactory(getSharedPreferences("prefs", MODE_PRIVATE)))
                .get(ProfileViewModel.class);

        binding.profileCameraButton.setOnClickListener(this);

        mViewModel.getResult().observe(this, result -> {
            onLoading(result.getStatus() == Status.LOADING);

            if (result.getStatus() == Status.SUCCESS)
                onSuccess(result.getData());
            else if (result.getStatus() == Status.ERROR)
                onError(result.getError());
            else if (result.getStatus() == Status.FAILURE)
                onFailure(result.getFailure());
        });
        user = UserManager.getInstance(getSharedPreferences("prefs", MODE_PRIVATE)).getUser();
        binding.setUser(user);

    }

    private void onLoading(boolean b) {
        if (b)
            mDialog.show();
        else
            mDialog.hide();
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
                if (Networking.isNetworkConnected(this))
                    mViewModel.store(binding.profileName.getEditText().getText().toString(), photo);
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
                        loadPhoto(bitmap);
                        String filePath = PhotoPath.getRealPathFromURI(this, data.getData());
                        File file = new File(filePath);
                        RequestBody mFile = RequestBody.create(MediaType.parse("image/*"), file);
                        photo = MultipartBody.Part.createFormData("image", file.getName(), mFile);
                        // RequestBody photoname = RequestBody.create(MediaType.parse("text/plain"), file.getName());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }


            }
        }
    }


    public void onSuccess(User user) {
        Toasty.success(this, "Perfil Atualizado com Sucesso!", Toasty.LENGTH_LONG).show();
        UserManager.getInstance(getSharedPreferences("prefs", MODE_PRIVATE)).saveUser(user);

    }

    public void onError(String message) {
        Toasty.error(this, "Erro ao Atualizar Perfil! (" + message + ")", Toasty.LENGTH_LONG).show();
    }

    public void onFailure(String message) {
        Toasty.warning(this, "Falha ao Atualizar Perfil! (" + message + ")", Toasty.LENGTH_LONG).show();
    }


    public void loadPhoto(Bitmap bitmap) {
        binding.profileImageview.setImageBitmap(bitmap);
    }


    @Override
    public void onClick(View view) {
        Intent openGalleryIntent = new Intent(Intent.ACTION_PICK);
        openGalleryIntent.setType("image/*");
        startActivityForResult(openGalleryIntent, REQUEST_GALLERY_CODE);
    }
}
