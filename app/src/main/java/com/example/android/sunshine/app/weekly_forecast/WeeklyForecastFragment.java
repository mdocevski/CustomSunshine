package com.example.android.sunshine.app.weekly_forecast;

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

import com.example.android.sunshine.app.ForecastFetcher;
import com.example.android.sunshine.app.R;
import com.example.android.sunshine.app.SunshineApplication;
import com.squareup.moshi.Moshi;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import timber.log.Timber;

/**
 * A placeholder fragment containing a simple view.
 */
public class WeeklyForecastFragment extends Fragment {
    private OkHttpClient okHttpClient;
    private Moshi moshi;

    ArrayAdapter<String> forecastAdapter;
    private ForecastFetcher forecastFetcher;

    public WeeklyForecastFragment() {
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

        List<String> weekForecast = new ArrayList<>();
        // Now that we have some dummy forecast data, create an ArrayAdapter.
        // The ArrayAdapter will take data from a source (like our dummy forecast) and
        // use it to populate the ListView it's attached to.
        forecastAdapter =
                new ArrayAdapter<>(
                        getActivity(), // The current context (this activity)
                        R.layout.weekly_forecast_list_item, // The name of the layout ID.
                        R.id.list_item_forecast_textview, // The ID of the textview to populate.
                        weekForecast);

        View rootView = inflater.inflate(R.layout.weekly_forecast_fragment, container, false);

        // Get a reference to the ListView, and attach this adapter to it.
        ListView listView = (ListView) rootView.findViewById(R.id.listview_forecast);
        listView.setAdapter(forecastAdapter);
        listView.setOnItemClickListener((parent, view, position, id) -> {
//            Toast.makeText(getActivity(), forecastAdapter.getItem(position), Toast.LENGTH_SHORT).show();
            // Launch
            ((WeeklyForecastActivity) getActivity()).launchDetailActivity(forecastAdapter.getItem(position));
        });

        // Update the forecast
        fetchForecast();

        return rootView;
    }

    //endregion

    //region Menu
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.weekly_forecast_fragment_menu, menu);
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
            if (getActivity() != null) {
                getActivity().runOnUiThread(() -> {
                    forecastAdapter.clear();
                    forecastAdapter.addAll(daysForecasts);
                    forecastAdapter.notifyDataSetChanged();
                });
            }
        }, Timber::e);
    }
}
