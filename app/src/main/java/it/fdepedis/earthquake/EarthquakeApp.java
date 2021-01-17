package it.fdepedis.earthquake;

import android.app.Application;
import timber.log.Timber;

public class EarthquakeApp extends Application {
    @Override public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }
}
