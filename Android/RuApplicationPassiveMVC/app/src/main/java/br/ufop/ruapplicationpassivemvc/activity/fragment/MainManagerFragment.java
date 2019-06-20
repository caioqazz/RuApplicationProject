package br.ufop.ruapplicationpassivemvc.activity.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.usuario.ruapplicationpassivemvc.R;
import br.ufop.ruapplicationpassivemvc.model.entity.Day;
import br.ufop.ruapplicationpassivemvc.util.SectionsPageAdapter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainManagerFragment extends Fragment {


    private ViewPager mViewPager;

    public MainManagerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Principal");

        // Set up the ViewPager with the sections adapter.
        mViewPager = view.findViewById(R.id.main_manager_container);

        setupViewPager(mViewPager);

        TabLayout tabLayout = getActivity().findViewById(R.id.main_manager_tabs);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.sun);
        tabLayout.getTabAt(1).setIcon(R.drawable.night);
    }

    private void setupViewPager(ViewPager viewPager) {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getChildFragmentManager());
        Day day = new Day();


        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        day.setDate(df.format(date));

        Bundle bundleLunch = new Bundle();
        bundleLunch.putSerializable("day", day);
        bundleLunch.putInt("type", 0);

        Bundle bundleDinner = new Bundle();
        bundleDinner.putSerializable("day", day);
        bundleDinner.putInt("type", 1);

        Fragment lunch = new DemandFragment();
        lunch.setArguments(bundleLunch);


        Fragment dinner = new DemandFragment();
        dinner.setArguments(bundleDinner);

        adapter.addFragment(lunch, "");
        adapter.addFragment(dinner, "");
        viewPager.setAdapter(adapter);
    }
}
