package br.ufop.ruapplicationmvvm.util;

import android.widget.RelativeLayout;

import androidx.databinding.BindingAdapter;

import br.ufop.ruapplicationmvvm.R;

public class HeaderColorUtil {
    @BindingAdapter("colorBack")
    public static void setColorBack(RelativeLayout relativeLayout, Integer type) {
        switch (type) {
            case 0:
                relativeLayout.setBackgroundResource(R.color.lightBlue);
                break;
            case 1:
                relativeLayout.setBackgroundResource(R.color.darkYellow);
                break;
            case 2:
                relativeLayout.setBackgroundResource(R.color.lightRed);
                break;
        }
    }
}
