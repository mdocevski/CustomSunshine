package com.example.android.sunshine.app.data;

import com.squareup.moshi.FromJson;
import com.squareup.moshi.ToJson;

import org.joda.time.DateTime;

/**
 * Created by mdocevski on 23.4.17.
 */

public class OpenWeatherDateTimeAdapter {
    @ToJson
    long toJson(@OpenWeatherDateTime DateTime dt) {
        return (dt.getMillis()/1000);
    }

    @FromJson @OpenWeatherDateTime DateTime fromJson(long dt) {
        return new DateTime(dt * 1000);
    }
}
