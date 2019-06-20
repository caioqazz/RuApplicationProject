package br.ufop.ruapplicationpassivemvc.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.usuario.ruapplicationpassivemvc.R;
import br.ufop.ruapplicationpassivemvc.util.Constants;

public class DemandView extends FrameLayout {
    public DemandView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void setVisibilityDialog(int type) {
        if (type == View.VISIBLE) {
            (findViewById(R.id.demand_content_container)).setVisibility(View.INVISIBLE);
            (findViewById(R.id.demand_loading_dialog)).setVisibility(View.VISIBLE);
        } else {
            (findViewById(R.id.demand_loading_dialog)).setVisibility(View.INVISIBLE);
            (findViewById(R.id.demand_content_container)).setVisibility(View.VISIBLE);
        }
    }

    public void setPrincipal(int sumPrincipal) {
        ((TextView) findViewById(R.id.demand_textview_normal)).setText(sumPrincipal + " Pessoas");
    }

    public void setVegetariano(int sumVegetariano) {
        ((TextView) findViewById(R.id.demand_textview_vegetariano)).setText(sumVegetariano + " Pessoas");
    }

    public void setTotal(int total) {
        ((TextView) findViewById(R.id.demand_textview_total)).setText(total + " Pessoas");
    }

    public void setDate(String date) {
        ((TextView) findViewById(R.id.demand_date)).setText(date);
    }

    public void setHeader(int type) {


    }

}
