package com.example.android.sunshine.app;

import android.app.Application;
import android.util.Log;

import com.example.android.sunshine.app.data.OpenWeatherDateTimeAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.picasso.Picasso;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import timber.log.Timber;

/**
 * Created by mdocevski on 20.4.17.
 */

public class SunshineApplication extends Application {
    private OkHttpClient okHttpClient;
    private Moshi moshi;
    private Picasso picasso;


    @Override
    public void onCreate() {
        super.onCreate();
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        moshi = new Moshi.Builder()
                // add the converter factories here
                .add(new OpenWeatherDateTimeAdapter())
                .build();
        okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();
        picasso = Picasso.with(getApplicationContext());
//                new Picasso.Builder(getApplicationContext())
//                .build();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
            Timber.plant(new CrashReportingTree());
        }
    }

    public OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }

    public Moshi getMoshi() {
        return moshi;
    }

    public Picasso getPicasso() {
        return picasso;
    }

    //TODO NOT LEAVE LIKE THIS
    @SuppressWarnings("all")
    private class CrashReportingTree extends Timber.Tree {
        @Override protected void log(int priority, String tag, String message, Throwable t) {
            if (priority == Log.VERBOSE || priority == Log.DEBUG) {
                return;
            }
//            FakeCrashLibrary.log(priority, tag, message);
//
//            if (t != null) {
//                if (priority == Log.ERROR) {
//                    FakeCrashLibrary.logError(t);
//                } else if (priority == Log.WARN) {
//                    FakeCrashLibrary.logWarning(t);
//                }
//            }
        }
    }
}
