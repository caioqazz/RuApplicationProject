package br.ufop.ruapplicationpassivemvc.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Spinner;

import com.example.usuario.ruapplicationpassivemvc.R;

public class FormDishView extends ScrollView {

    AlertDialog dialog;

    public FormDishView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setListeners(OnClickListener listener) {
        this.findViewById(R.id.form_dish_camera_button).setOnClickListener(listener);
    }

    public void setAdapterSpinner(ArrayAdapter<String> adapter) {
        ((Spinner) this.findViewById(R.id.form_dish_spinner)).setAdapter(adapter);
    }

    public int getType() {
        Spinner spinner = this.findViewById(R.id.form_dish_spinner);
        return spinner.getSelectedItemPosition() - 1;
    }

    public void setType(int type) {
        ((Spinner) this.findViewById(R.id.form_dish_spinner)).setSelection(type + 1);
    }

    public String getDescription() {
        return ((EditText) this.findViewById(R.id.form_dish_description)).getText().toString();
    }

    public void setDescription(String description) {
        ((EditText) this.findViewById(R.id.form_dish_description)).setText(description);
    }

    public String getName() {
        return ((EditText) this.findViewById(R.id.form_dish_name)).getText().toString();
    }

    public void setName(String name) {
        ((EditText) this.findViewById(R.id.form_dish_name)).setText(name);
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

    public Bitmap getPhoto() {
        BitmapDrawable bitmapDrawable = (BitmapDrawable) ((ImageView) this.findViewById(R.id.form_dish_imageview)).getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();
        return bitmap;
    }

    public void setPhoto(Bitmap bitmap) {
        ((ImageView) this.findViewById(R.id.form_dish_imageview)).setImageBitmap(bitmap);
    }

    public ImageView getFieldPhoto() {
        return ((ImageView) this.findViewById(R.id.form_dish_imageview));
    }
}
