package br.ufop.ruapplicationpassivemvc.util;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.example.usuario.ruapplicationpassivemvc.R;

public class Dialog {

    public static void confimationDelete(Context context, DialogInterface.OnClickListener listener, String name) {
        new AlertDialog.Builder(context)
                .setTitle("Excluir")
                .setMessage("Tem certeza que seja excluir o '" + name + "'?")
                .setIcon(R.drawable.alert)
                .setPositiveButton("Sim", listener)
                .setNegativeButton("Não", null).show();

    }
}
