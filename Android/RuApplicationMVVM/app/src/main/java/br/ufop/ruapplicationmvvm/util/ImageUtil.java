package br.ufop.ruapplicationmvvm.util;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.squareup.picasso.Picasso;

import br.ufop.ruapplicationmvvm.R;
import br.ufop.ruapplicationmvvm.service.api.RetrofitBuilder;

public class ImageUtil {
    @BindingAdapter("imageUrl")
    public static void setImageUrl(ImageView imageView, String url) {
        if (url != null && !url.isEmpty()) {
            Picasso.get().load(RetrofitBuilder.BASE_PARSE_URL + url).error(R.mipmap.user).into(imageView);
        } else {
            imageView.setImageResource(R.drawable.image);
        }
    }
}
