package br.ufop.ruapplicationpassivemvc.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.example.usuario.ruapplicationpassivemvc.R;

public class Networking {

    public static boolean isNetworkConnected(View view) {
        ConnectivityManager cm =
                (ConnectivityManager) view.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean result = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        if (!result) {
            Snackbar.make(view, R.string.err_network, Snackbar.LENGTH_LONG).show();
        }
        return result;
    }
}
