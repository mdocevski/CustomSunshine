package com.example.android.sunshine.app.data.model;

import com.squareup.moshi.Json;

/**
 * Created by mdocevski on 23.4.17.
 */

public class City {
    public final int id;
    public final String name;
    @Json(name="coord") public final Coordinate coordinate;
    public final String country;
    public final long population;

    public City(int id, String name, Coordinate coordinate, String country, long population) {
        this.id = id;
        this.name = name;
        this.coordinate = coordinate;
        this.country = country;
        this.population = population;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof City)) return false;

        City city = (City) o;

        if (id != city.id) return false;
        if (population != city.population) return false;
        if (name != null ? !name.equals(city.name) : city.name != null) return false;
        if (coordinate != null ? !coordinate.equals(city.coordinate) : city.coordinate != null) return false;
        return country != null ? country.equals(city.country) : city.country == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (coordinate != null ? coordinate.hashCode() : 0);
        result = 31 * result + (country != null ? country.hashCode() : 0);
        result = 31 * result + (int) (population ^ (population >>> 32));
        return result;
    }
}
