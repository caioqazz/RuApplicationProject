package br.ufop.ruapplicationmvvm.util;

import androidx.databinding.BindingAdapter;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class RefreshUtil {
    @BindingAdapter("app:refreshing")
    public static void setRefreshing(SwipeRefreshLayout swipe, boolean isRefreshing) {
        if (isRefreshing) {
            swipe.setRefreshing(true);
        }
        swipe.setRefreshing(false);
    }
}
