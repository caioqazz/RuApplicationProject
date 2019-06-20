package br.ufop.ruapplicationpassivemvc.view;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.usuario.ruapplicationpassivemvc.R;

public class DetailDishView extends RelativeLayout {

    AlertDialog dialog;

    public DetailDishView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setListeners(RatingBar.OnRatingBarChangeListener listener) {
        ((RatingBar) findViewById(R.id.detail_dish_rating)).setOnRatingBarChangeListener(listener);
    }


    public String getDescription() {
        return ((TextView) findViewById(R.id.detail_dish_description)).getText().toString();
    }


    public void setDescription(String description) {
        ((TextView) findViewById(R.id.detail_dish_description)).setText(description);
    }


    public float getRating() {
        return ((RatingBar) findViewById(R.id.detail_dish_rating)).getRating();
    }


    public void setRating(float rating) {
        ((RatingBar) findViewById(R.id.detail_dish_rating)).setRating(rating);
    }


    public String getName() {
        return ((TextView) findViewById(R.id.detail_dish_name)).getText().toString();
    }


    public void setName(String name) {
        ((TextView) findViewById(R.id.detail_dish_name)).setText(name);
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

    public void clientView() {
        ((TextView) findViewById(R.id.detail_dish_caption)).setText("Avalie esse prato");
        findViewById(R.id.detail_dish_checkbox).setVisibility(VISIBLE);
        findViewById(R.id.detail_dish_comment).setVisibility(VISIBLE);
    }

    public boolean getCheckBox() {
        return ((CheckBox) findViewById(R.id.detail_dish_checkbox)).isChecked();
    }

    public String getComment() {
        return ((EditText) findViewById(R.id.detail_dish_comment)).getText().toString();
    }

    public void setComment(String comment) {
        ((EditText) findViewById(R.id.detail_dish_comment)).setText(comment);
    }

    public void showForm() {
        if (dialog != null)
            dialog.dismiss();

    }

    public ImageView getPhotoField() {
        return ((ImageView) findViewById(R.id.detail_dish_imageview));
    }

    public void setCheck(int option) {
        if (option == 1) {
            ((CheckBox) findViewById(R.id.detail_dish_checkbox)).setChecked(false);
        } else {
            ((CheckBox) findViewById(R.id.detail_dish_checkbox)).setChecked(true);
        }
    }
}
