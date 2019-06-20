package br.ufop.ruapplicationmvp.util;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AlertDialog;

import br.ufop.ruapplicationmvp.R;

public class DialogManager {

    public static AlertDialog loadingDialog(Context context) {
        AlertDialog mDialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(true);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dialog_layout_message, null);
        builder.setView(view);
        mDialog = builder.create();
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.setCancelable(false);
        return mDialog;
    }
    public static void confimationDelete(Context context, DialogInterface.OnClickListener listener, String name) {
        new AlertDialog.Builder(context)
                .setTitle("Excluir")
                .setMessage("Tem certeza que seja excluir o '" + name + "'?")
                .setIcon(R.drawable.alert)
                .setPositiveButton("Sim", listener)
                .setNegativeButton("Não", null).show();

    }
}
