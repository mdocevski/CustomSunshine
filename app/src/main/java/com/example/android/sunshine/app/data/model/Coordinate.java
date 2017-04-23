package com.example.android.sunshine.app.data.model;

/**
 * Created by mdocevski on 23.4.17.
 */

public class Coordinate {
    public final double lon;
    public final double lat;

    public Coordinate(double lon, double lat) {
        this.lon = lon;
        this.lat = lat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Coordinate)) return false;

        Coordinate coordinate = (Coordinate) o;

        if (Double.compare(coordinate.lon, lon) != 0) return false;
        return Double.compare(coordinate.lat, lat) == 0;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(lon);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(lat);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
