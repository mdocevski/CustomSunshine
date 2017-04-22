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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import timber.log.Timber;

/**
 * A placeholder fragment containing a simple view.
 */
public class ForecastFragment extends Fragment {
    private OkHttpClient okHttpClient;
    private Moshi moshi;

    ArrayAdapter<String> mForecastAdapter;
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
        mForecastAdapter =
                new ArrayAdapter<>(
                        getActivity(), // The current context (this activity)
                        R.layout.list_item_forecast, // The name of the layout ID.
                        R.id.list_item_forecast_textview, // The ID of the textview to populate.
                        weekForecast);

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        // Get a reference to the ListView, and attach this adapter to it.
        ListView listView = (ListView) rootView.findViewById(R.id.listview_forecast);
        listView.setAdapter(mForecastAdapter);

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
        forecastFetcher.fetchDailyWeatherForecast("1000", "mk", 7, new Callback() {
            public void onFailure(Call call, IOException e) {
                Timber.e(e, "Failed to acquire weather data.");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
//                            moshi.
                Timber.d(response.body().string());
            }
        });
    }
}
