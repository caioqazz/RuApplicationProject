package br.ufop.ruapplicationpassivemvc.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.usuario.ruapplicationpassivemvc.R;
import br.ufop.ruapplicationpassivemvc.service.api.RetrofitBuilder;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileView extends RelativeLayout {
    AlertDialog dialog;

    public ProfileView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void setListeners(OnClickListener listener) {
        this.findViewById(R.id.profile_camera_button).setOnClickListener(listener);
    }

    public void setCPF(String cpf) {
        ((TextInputLayout) this.findViewById(R.id.profile_cpf)).getEditText().setText(cpf);
    }

    public String getName() {
        return ((TextInputLayout) this.findViewById(R.id.profile_name)).getEditText().getText().toString();
    }

    public void setName(String name) {
        ((TextInputLayout) this.findViewById(R.id.profile_name)).getEditText().setText(name);
    }

    public void setEmail(String email) {
        ((TextInputLayout) this.findViewById(R.id.profile_email)).getEditText().setText(email);


    }

    public void showLoading() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(true);

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dialog_layout_message, null);
        builder.setView(view);
        dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();
    }

    public void showForm() {
        dialog.dismiss();
    }

    public void setPhoto(Bitmap bitmap) {
        ((CircleImageView) this.findViewById(R.id.profile_imageview)).setImageBitmap(bitmap);
    }

    public CircleImageView getFieldPhoto() {
        return ((CircleImageView) this.findViewById(R.id.profile_imageview));
    }

    public void loadPhoto(String photo) {
        if (photo != null) {
            if (!photo.isEmpty())
                Picasso.get().load(RetrofitBuilder.BASE_PARSE_URL + photo).error(R.drawable.user).into(this.getFieldPhoto());
        }
    }
}
