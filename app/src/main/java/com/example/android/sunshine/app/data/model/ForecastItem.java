package com.example.android.sunshine.app.data.model;

import com.example.android.sunshine.app.data.OpenWeatherDateTime;
import com.squareup.moshi.Json;

import org.joda.time.DateTime;

import java.util.List;

/**
 * Created by mdocevski on 23.4.17.
 */

public class ForecastItem {

    @OpenWeatherDateTime
    @Json(name = "dt") public final DateTime dateTime;
    @Json(name = "temp") public final Temperature temperature;
    public final double pressure;
    public final int humidity;
    public final List<Weather> weather;

    public final double speed;
    @Json(name = "deg") public final int degrees;
    public final int clouds;
    public final double rain;

    public ForecastItem(DateTime dateTime, Temperature temperature, double pressure, int humidity, List<Weather> weather, double speed, int degrees, int clouds, double rain) {
        this.dateTime = dateTime;
        this.temperature = temperature;
        this.pressure = pressure;
        this.humidity = humidity;
        this.weather = weather;
        this.speed = speed;
        this.degrees = degrees;
        this.clouds = clouds;
        this.rain = rain;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ForecastItem)) return false;

        ForecastItem that = (ForecastItem) o;

        if (Double.compare(that.pressure, pressure) != 0) return false;
        if (humidity != that.humidity) return false;
        if (Double.compare(that.speed, speed) != 0) return false;
        if (degrees != that.degrees) return false;
        if (clouds != that.clouds) return false;
        if (Double.compare(that.rain, rain) != 0) return false;
        if (dateTime != null ? !dateTime.equals(that.dateTime) : that.dateTime != null) return false;
        if (temperature != null ? !temperature.equals(that.temperature) : that.temperature != null) return false;
        return weather != null ? weather.equals(that.weather) : that.weather == null;

    }

    @Override
    public int hashCode() {
        int result;
        long temp1;
        result = dateTime != null ? dateTime.hashCode() : 0;
        result = 31 * result + (temperature != null ? temperature.hashCode() : 0);
        temp1 = Double.doubleToLongBits(pressure);
        result = 31 * result + (int) (temp1 ^ (temp1 >>> 32));
        result = 31 * result + humidity;
        result = 31 * result + (weather != null ? weather.hashCode() : 0);
        temp1 = Double.doubleToLongBits(speed);
        result = 31 * result + (int) (temp1 ^ (temp1 >>> 32));
        result = 31 * result + degrees;
        result = 31 * result + clouds;
        temp1 = Double.doubleToLongBits(rain);
        result = 31 * result + (int) (temp1 ^ (temp1 >>> 32));
        return result;
    }
}
