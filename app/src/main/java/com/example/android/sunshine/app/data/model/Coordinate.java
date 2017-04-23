package com.example.android.sunshine.app.data.model;

import com.squareup.moshi.Json;

/**
 * Created by mdocevski on 23.4.17.
 */

public class Coordinate {
    @Json(name="lon") public final double longitude;
    @Json(name="lat") public final double latitude;

    public Coordinate(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Coordinate)) return false;

        Coordinate coordinate = (Coordinate) o;

        if (Double.compare(coordinate.longitude, longitude) != 0) return false;
        return Double.compare(coordinate.latitude, latitude) == 0;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(longitude);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(latitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
