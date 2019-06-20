package br.ufop.ruapplicationmvvm.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Networking {

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean result = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
     //   if (!result) {
            //Snackbar.make(view, R.string.err_network, Snackbar.LENGTH_LONG).show();
    //    }
        return result;
    }
}
