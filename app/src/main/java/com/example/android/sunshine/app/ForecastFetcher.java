package com.example.android.sunshine.app;

import android.net.Uri;
import android.support.annotation.Nullable;

import com.squareup.moshi.Moshi;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

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
     * @param callback Action to execute on result.
     */
    public void fetchDailyWeatherForecast(String postalCode, @Nullable String countryCode, int days, Callback callback) {
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
                .enqueue(callback);
    }
}
