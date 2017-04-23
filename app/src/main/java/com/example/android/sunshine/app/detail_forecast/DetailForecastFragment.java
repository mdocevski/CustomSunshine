package com.example.android.sunshine.app.detail_forecast;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.sunshine.app.R;

import static com.example.android.sunshine.app.util.StringUtils.isNullOrEmpty;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailForecastFragment extends Fragment {


    private String forecastText;

    public DetailForecastFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        forecastText = getActivity().getIntent().getStringExtra(Intent.EXTRA_TEXT);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.detail_forecast_fragment, container, false);
        if(!isNullOrEmpty(forecastText)){
            ((TextView) rootView.findViewById(R.id.forecast_text)).setText(forecastText);
        }
        return rootView;
    }

}
