package com.example.android.sunshine.app.data.model;

import com.example.android.sunshine.app.data.OpenWeatherDateTime;

import org.joda.time.DateTime;

import java.util.List;

/**
 * Created by mdocevski on 23.4.17.
 */

public class ForecastItem {

    @OpenWeatherDateTime
    public final DateTime dt;
    public final Temperature temp;
    public final double pressure;
    public final int humidity;
    public final List<Weather> weather;

    public final double speed;
    public final int deg;
    public final int clouds;
    public final double rain;

    public ForecastItem(DateTime dt, Temperature temp, double pressure, int humidity, List<Weather> weather, double speed, int deg, int clouds, double rain) {
        this.dt = dt;
        this.temp = temp;
        this.pressure = pressure;
        this.humidity = humidity;
        this.weather = weather;
        this.speed = speed;
        this.deg = deg;
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
        if (deg != that.deg) return false;
        if (clouds != that.clouds) return false;
        if (Double.compare(that.rain, rain) != 0) return false;
        if (dt != null ? !dt.equals(that.dt) : that.dt != null) return false;
        if (temp != null ? !temp.equals(that.temp) : that.temp != null) return false;
        return weather != null ? weather.equals(that.weather) : that.weather == null;

    }

    @Override
    public int hashCode() {
        int result;
        long temp1;
        result = dt != null ? dt.hashCode() : 0;
        result = 31 * result + (temp != null ? temp.hashCode() : 0);
        temp1 = Double.doubleToLongBits(pressure);
        result = 31 * result + (int) (temp1 ^ (temp1 >>> 32));
        result = 31 * result + humidity;
        result = 31 * result + (weather != null ? weather.hashCode() : 0);
        temp1 = Double.doubleToLongBits(speed);
        result = 31 * result + (int) (temp1 ^ (temp1 >>> 32));
        result = 31 * result + deg;
        result = 31 * result + clouds;
        temp1 = Double.doubleToLongBits(rain);
        result = 31 * result + (int) (temp1 ^ (temp1 >>> 32));
        return result;
    }
}
