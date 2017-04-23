package com.example.android.sunshine.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.squareup.moshi.Moshi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.OkHttpClient;
import timber.log.Timber;

/**
 * A placeholder fragment containing a simple view.
 */
public class ForecastFragment extends Fragment {
    private OkHttpClient okHttpClient;
    private Moshi moshi;

    ArrayAdapter<String> forecastAdapter;
    private ForecastFetcher forecastFetcher;

    public ForecastFragment() {
    }

    //region Lifecycle

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        okHttpClient = ((SunshineApplication) getActivity().getApplication()).getOkHttpClient();
        moshi = ((SunshineApplication) getActivity().getApplication()).getMoshi();
        forecastFetcher = new ForecastFetcher(okHttpClient, moshi, "metric", "json");

        // Inform the Activity that we have menu items to add
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Create some dummy data for the ListView.  Here's a sample weekly forecast
        String[] data = {
                "Mon 6/23 - Sunny - 31/17",
                "Tue 6/24 - Foggy - 21/8",
                "Wed 6/25 - Cloudy - 22/17",
                "Thurs 6/26 - Rainy - 18/11",
                "Fri 6/27 - Foggy - 21/10",
                "Sat 6/28 - TRAPPED IN WEATHERSTATION - 23/18",
                "Sun 6/29 - Sunny - 20/7"
        };
        List<String> weekForecast = new ArrayList<>(Arrays.asList(data));


        // Now that we have some dummy forecast data, create an ArrayAdapter.
        // The ArrayAdapter will take data from a source (like our dummy forecast) and
        // use it to populate the ListView it's attached to.
        forecastAdapter =
                new ArrayAdapter<>(
                        getActivity(), // The current context (this activity)
                        R.layout.list_item_forecast, // The name of the layout ID.
                        R.id.list_item_forecast_textview, // The ID of the textview to populate.
                        weekForecast);

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        // Get a reference to the ListView, and attach this adapter to it.
        ListView listView = (ListView) rootView.findViewById(R.id.listview_forecast);
        listView.setAdapter(forecastAdapter);

        // Update the forecast
        fetchForecast();

        return rootView;
    }

    //endregion

    //region Menu
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.forecastfragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh: {
                Timber.d("Refresh button clicked.");
                //Update the forecast
                fetchForecast();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
    //endregion

    /***
     * Fetches 7 days forecast for Skopje.
     */
    private void fetchForecast() {
        forecastFetcher.fetchDailyWeatherForecast("1000", "mk", 7, daysForecasts -> {
            if(getActivity()!=null) {
                getActivity().runOnUiThread(() -> {
                    forecastAdapter.clear();
                    forecastAdapter.addAll(daysForecasts);
                    forecastAdapter.notifyDataSetChanged();
                });
            }
        }, Timber::e);
    }
}
