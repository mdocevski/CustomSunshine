package com.example.android.sunshine.app.data;

import com.squareup.moshi.JsonQualifier;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by mdocevski on 23.4.17.
 */

@Retention(RUNTIME)
@JsonQualifier
public @interface OpenWeatherDateTime {
}
