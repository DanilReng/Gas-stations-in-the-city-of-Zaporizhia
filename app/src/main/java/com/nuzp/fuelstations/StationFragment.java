package com.nuzp.fuelstations;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;


public class StationFragment extends Fragment {
    private static final String ARG_PARAM1 = "station";
    private Station station;
    private View view;

    public StationFragment() {
        // Required empty public constructor
    }

    public static StationFragment newInstance(Station param1) {
        StationFragment fragment = new StationFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            station = (Station) getArguments().getSerializable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_station, container, false);

        DatabaseConnector databaseConnector = DatabaseConnector.getInstance(getContext());

        setContent(R.id.station_name, getString(R.string.station) + " " + databaseConnector.getBrandById(station.getBrand_id()) + " №" + station.getStation_id());
        setContent(R.id.phone, station.getPhone());
        setContent(R.id.address, station.getAddress());
        setContent(R.id.description, station.getDescription());

        int resourceId = getResources().getIdentifier("station_" + station.getStation_id(), "drawable", getContext().getPackageName());
        ImageView stationPhoto = view.findViewById(R.id.station_photo);
        stationPhoto.setImageResource(resourceId);
        return view;
    }

    public void setContent(int id, String text) {
        TextView tv = view.findViewById(id);
        tv.setText(text);
    }
}