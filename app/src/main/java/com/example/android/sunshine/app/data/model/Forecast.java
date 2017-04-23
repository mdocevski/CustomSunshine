package com.example.android.sunshine.app.data.model;

import com.squareup.moshi.Json;

import java.util.List;

/**
 * Created by mdocevski on 23.4.17.
 */

public class Forecast {
    public final City city;
    @Json(name = "cod") public final String code;
    public final double message;
    @Json(name = "cnt") public final int count;
    public final List<ForecastItem> list;

    public Forecast(City city, String code, double message, int count, List<ForecastItem> list) {
        this.city = city;
        this.code = code;
        this.message = message;
        this.count = count;
        this.list = list;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Forecast)) return false;

        Forecast forecast = (Forecast) o;

        if (Double.compare(forecast.message, message) != 0) return false;
        if (count != forecast.count) return false;
        if (city != null ? !city.equals(forecast.city) : forecast.city != null) return false;
        if (code != null ? !code.equals(forecast.code) : forecast.code != null) return false;
        return list != null ? list.equals(forecast.list) : forecast.list == null;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = city != null ? city.hashCode() : 0;
        result = 31 * result + (code != null ? code.hashCode() : 0);
        temp = Double.doubleToLongBits(message);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + count;
        result = 31 * result + (list != null ? list.hashCode() : 0);
        return result;
    }
}
