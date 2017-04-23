package com.example.android.sunshine.app;

import android.net.Uri;
import android.support.annotation.Nullable;

import com.example.android.sunshine.app.data.model.Forecast;
import com.example.android.sunshine.app.data.model.ForecastItem;
import com.example.android.sunshine.app.util.Action1;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import org.joda.time.DateTime;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.example.android.sunshine.app.util.StringUtils.isNullOrEmpty;

/***
 * Used to fetch weather from openWeather api. Requires API Keys for api.openweathermap.org to be present in the BuildConfig.
 */
class ForecastFetcher {
    private static final String UNITS_PARAMETER = "units";
    private static final String COUNT_PARAMETER = "cnt";
    private static final String MODE_PARAMETER = "mode";
    private static final String APP_ID = "appId";
    private OkHttpClient okHttpClient;
    private Moshi moshi;
    private String units;
    private String mode;

    /***
     * @param okHttpClient Http client
     * @param moshi Json serializer/deserializer
     * @param units metric/imperial (default is kelvin)
     * @param mode json/xml (default is json)
     */
    public ForecastFetcher(OkHttpClient okHttpClient, Moshi moshi, String units, String mode) {
        this.okHttpClient = okHttpClient;
        this.moshi = moshi;
        this.units = units;
        this.mode = mode;
    }

    /***
     * Method
     * @param postalCode City postal code
     * @param countryCode Country code. If left out default is us.
     * @param days 1-7 days
     * @param onSuccess Action to do on successful execution. Works with a list of forecasts.
     * @param onError Action to do on failing to retrieve forecasts.
     */
    public void fetchDailyWeatherForecast(String postalCode, @Nullable String countryCode, int days, Action1<String[]> onSuccess, Action1<Throwable> onError) {
        Uri forecastUri = new Uri.Builder()
                .scheme("http")
                .path("api.openweathermap.org/data/2.5/forecast/daily")
                .appendQueryParameter("zip", postalCode + (isNullOrEmpty(countryCode) ? "" : "," + countryCode))
                .appendQueryParameter(UNITS_PARAMETER, units)
                .appendQueryParameter(COUNT_PARAMETER, String.valueOf(days))
                .appendQueryParameter(MODE_PARAMETER, mode)
                .appendQueryParameter(APP_ID, BuildConfig.OPEN_WEATHER_MAP_API_KEY)
                .build();

        okHttpClient.newCall(
                new Request.Builder()
                        .get()
                        .url(forecastUri.toString())
                        .build())
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        onError.execute(e);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (!response.isSuccessful()) {
                            onError.execute(new IOException("Unexpected code " + response));
                        }
                        String forecastJsonStr = response.body().string();
                        JsonAdapter<Forecast> forecastJsonAdapter  = moshi.adapter(Forecast.class);
                        Forecast forecast = forecastJsonAdapter.fromJson(forecastJsonStr);

                        onSuccess.execute(getWeatherDataFromJson(forecast));
                    }
                });
    }

    /* The date/time conversion code is going to be moved outside the asynctask later,
    * so for convenience we're breaking it out into its own method now.
    */
    private String getReadableDateString(DateTime dateTime) {
        return dateTime.toString("EEE MMM dd");
    }

    /**
     * Prepare the weather high/lows for presentation.
     */
    private String formatHighLows(double high, double low) {
        // For presentation, assume the user doesn't care about tenths of a degree.
        long roundedHigh = Math.round(high);
        long roundedLow = Math.round(low);

        return roundedHigh + "/" + roundedLow;
    }

    /**
     * Take the String representing the complete forecast in JSON Format and
     * pull out the data we need to construct the Strings needed for the wireframes.
     * <p>
     * Fortunately parsing is easy:  constructor takes the JSON string and converts it
     * into an Object hierarchy for us.
     */
    private String[] getWeatherDataFromJson(Forecast forecast) {
        String[] resultStrs = new String[forecast.list.size()];
        int i = 0;
        for (ForecastItem forecastItem :
                forecast.list) {
            // For now, using the format "Day, description, hi/low"
            String day;
            String description;
            String highAndLow;

            day = getReadableDateString(forecastItem.dt);
            description = forecastItem.weather.get(0).description;
            highAndLow = formatHighLows(forecastItem.temp.max, forecastItem.temp.min);

            resultStrs[i] = day + " - " + description + " - " + highAndLow;
            i++;
        }

        return resultStrs;

    }
}
