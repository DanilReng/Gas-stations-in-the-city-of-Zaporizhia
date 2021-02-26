package com.nuzp.fuelstations;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class StationsListFragment extends Fragment {
    private static final String ARG_PARAM1 = "brand_id";
    private static final String ARG_PARAM2 = "param2";
    private int brand_id;
    private View view;

    public StationsListFragment() {
        // Required empty public constructor
    }

    public static StationsListFragment newInstance(int param1) {
        StationsListFragment fragment = new StationsListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            brand_id = getArguments().getInt(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_stations_list, container, false);

        DatabaseConnector databaseConnector = DatabaseConnector.getInstance(getContext());
        ArrayList<Station> stations = databaseConnector.getStations(brand_id);

        LinearLayout stationsList = view.findViewById(R.id.stationsList);

        for (Station station : stations) {
            final LinearLayout stationLayout = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.inflate_station_item, null);
            final LinearLayout infoLayout = (LinearLayout) stationLayout.getChildAt(1);

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(25, 35, 25, 35);
            stationLayout.setLayoutParams(layoutParams);

            int resourceId = getResources().getIdentifier("station_" + station.getStation_id(), "drawable", getContext().getPackageName());

            CardView card = (CardView) stationLayout.getChildAt(0);
            card.setPreventCornerOverlap(true);
            ((ImageView) card.getChildAt(0)).setImageResource(resourceId);

            ((TextView) infoLayout.getChildAt(0)).setText(getString(R.string.station) + " №" + station.getStation_id());
            ((TextView) infoLayout.getChildAt(1)).setText(station.getAddress());

            stationLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MainActivity.fm.beginTransaction()
                            .setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out)
                            .replace(R.id.main_fragment_container, StationFragment.newInstance(station))
                            .addToBackStack(null)
                            .commit();
                }
            });
            stationsList.addView(stationLayout);
        }
        return view;
    }
}