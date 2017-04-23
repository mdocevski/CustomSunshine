package com.example.android.sunshine.app.data.model;

import com.squareup.moshi.Json;

/**
 * Created by mdocevski on 23.4.17.
 */

public class Temperature {
    public final double day;
    public final double min;
    public final double max;
    public final double night;
    @Json(name = "eve") public final double evening;
    @Json(name = "morn") public final double morning;

    public Temperature(double day, double min, double max, double night, double evening, double morning) {
        this.day = day;
        this.min = min;
        this.max = max;
        this.night = night;
        this.evening = evening;
        this.morning = morning;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Temperature)) return false;

        Temperature that = (Temperature) o;

        if (Double.compare(that.day, day) != 0) return false;
        if (Double.compare(that.min, min) != 0) return false;
        if (Double.compare(that.max, max) != 0) return false;
        if (Double.compare(that.night, night) != 0) return false;
        if (Double.compare(that.evening, evening) != 0) return false;
        return Double.compare(that.morning, morning) == 0;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(day);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(min);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(max);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(night);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(evening);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(morning);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
