package br.ufop.ruapplicationmvp.view;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;

import br.ufop.ruapplicationmvp.R;
import br.ufop.ruapplicationmvp.contract.ProfileContract;
import br.ufop.ruapplicationmvp.model.entity.User;
import br.ufop.ruapplicationmvp.presenter.ProfilePresenter;
import br.ufop.ruapplicationmvp.service.api.RetrofitBuilder;
import br.ufop.ruapplicationmvp.service.api.UserManager;
import br.ufop.ruapplicationmvp.util.DialogManager;
import br.ufop.ruapplicationmvp.util.Networking;
import br.ufop.ruapplicationmvp.util.PhotoPath;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class ProfileActivity extends AppCompatActivity
        implements ProfileContract.View {
    private final int REQUEST_GALLERY_CODE = 200;
    @BindView(R.id.profile_cpf)
    TextInputLayout mCpf;
    @BindView(R.id.profile_email)
    TextInputLayout mEmail;
    @BindView(R.id.profile_name)
    TextInputLayout mName;
    @BindView(R.id.profile_imageview)
    ImageView mImage;
    private AlertDialog mDialog;
    private MultipartBody.Part photo;
    private User user;

    private ProfilePresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setTitle("Perfil");
        mPresenter = new ProfilePresenter(this, getSharedPreferences("prefs", MODE_PRIVATE));
        ButterKnife.bind(this);
        user = UserManager.getInstance(getSharedPreferences("prefs", MODE_PRIVATE)).getUser();
        loadUser(user);

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
                    mPresenter.onSendButtonClick(user.getName(), photo);
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


            } else if (resultCode == Activity.RESULT_CANCELED) {

            }
        }
    }

    @Override
    public void showProgress() {
        mDialog = DialogManager.loadingDialog(this);
        mDialog.show();
    }

    @Override
    public void onSuccess(User user) {
        Toasty.success(this, "Perfil Atualizado com Sucesso!", Toasty.LENGTH_LONG).show();
        UserManager.getInstance(getSharedPreferences("prefs", MODE_PRIVATE)).saveUser(user);

    }

    @Override
    public void hideProgress() {
        mDialog.hide();
    }


    @Override
    public void onError(String message) {
        Toasty.error(this, "Erro ao Atualizar Perfil! (" + message + ")", Toasty.LENGTH_LONG).show();
    }

    @Override
    public void onFailure(Throwable t) {
        Toasty.warning(this, "Falha ao Atualizar Perfil! (" + t.getMessage() + ")", Toasty.LENGTH_LONG).show();
    }

    @Override
    public void loadUser(User user) {
        mCpf.getEditText().setText(user.getCpf());
        mEmail.getEditText().setText(user.getEmail());
        mName.getEditText().setText(user.getName());
        if (user.getPhoto() != null && !user.getPhoto().isEmpty()) {
            Picasso.get().load(RetrofitBuilder.BASE_PARSE_URL + user.getPhoto()).error(R.mipmap.user).into(mImage);
        }

    }

    @Override
    public void loadPhoto(Bitmap bitmap) {
        mImage.setImageBitmap(bitmap);
    }


    @OnClick(R.id.profile_camera_button)
    public void onButtoCameraClick() {
        Intent openGalleryIntent = new Intent(Intent.ACTION_PICK);
        openGalleryIntent.setType("image/*");
        startActivityForResult(openGalleryIntent, REQUEST_GALLERY_CODE);
    }


}
