package com.example.android.sunshine.app.data.model;

import java.util.List;

/**
 * Created by mdocevski on 23.4.17.
 */

public class Forecast {
    public final City city;
    public final String cod;
    public final double message;
    public final int cnt;
    public final List<ForecastItem> list;

    public Forecast(City city, String cod, double message, int cnt, List<ForecastItem> list) {
        this.city = city;
        this.cod = cod;
        this.message = message;
        this.cnt = cnt;
        this.list = list;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Forecast)) return false;

        Forecast forecast = (Forecast) o;

        if (Double.compare(forecast.message, message) != 0) return false;
        if (cnt != forecast.cnt) return false;
        if (city != null ? !city.equals(forecast.city) : forecast.city != null) return false;
        if (cod != null ? !cod.equals(forecast.cod) : forecast.cod != null) return false;
        return list != null ? list.equals(forecast.list) : forecast.list == null;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = city != null ? city.hashCode() : 0;
        result = 31 * result + (cod != null ? cod.hashCode() : 0);
        temp = Double.doubleToLongBits(message);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + cnt;
        result = 31 * result + (list != null ? list.hashCode() : 0);
        return result;
    }
}
