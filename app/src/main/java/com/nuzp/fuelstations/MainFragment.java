package com.nuzp.fuelstations;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class MainFragment extends Fragment {
    private View view;
    private final int BUTTON_MARGIN = 15;

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main, container, false);
        DatabaseConnector databaseConnector = DatabaseConnector.getInstance(getContext());

        ArrayList<Brand> brands = databaseConnector.getBrands();
        GridLayout brandsList = view.findViewById(R.id.brandsList);

        for (Brand brand : brands) {
            final LinearLayout brandLayout = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.inflate_brand, null);

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(BUTTON_MARGIN, BUTTON_MARGIN, BUTTON_MARGIN, BUTTON_MARGIN);
            brandLayout.setLayoutParams(layoutParams);

            int resourceId = getResources().getIdentifier(brand.getName().toLowerCase(), "drawable", getContext().getPackageName());

            ((ImageView) brandLayout.getChildAt(0)).setImageResource(resourceId);
            ((TextView) brandLayout.getChildAt(1)).setText(brand.getName());

            brandLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MainActivity.fm.beginTransaction()
                            .setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out)
                            .replace(R.id.main_fragment_container, StationsListFragment.newInstance(brand.getBrand_id()))
                            .addToBackStack(null)
                            .commit();
                }
            });

            brandsList.addView(brandLayout);
        }
        return view;
    }
}