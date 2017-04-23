package com.example.android.sunshine.app.data.model;

/**
 * Created by mdocevski on 23.4.17.
 */

public class City {
    public final int id;
    public final String name;
    public final Coordinate coord;
    public final String country;
    public final long population;

    public City(int id, String name, Coordinate coord, String country, long population) {
        this.id = id;
        this.name = name;
        this.coord = coord;
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
        if (coord != null ? !coord.equals(city.coord) : city.coord != null) return false;
        return country != null ? country.equals(city.country) : city.country == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (coord != null ? coord.hashCode() : 0);
        result = 31 * result + (country != null ? country.hashCode() : 0);
        result = 31 * result + (int) (population ^ (population >>> 32));
        return result;
    }
}
