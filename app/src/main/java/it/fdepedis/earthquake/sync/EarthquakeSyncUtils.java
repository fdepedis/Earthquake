/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package it.fdepedis.earthquake.sync;

import android.content.Context;
import android.util.Log;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.Driver;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.Trigger;

import java.util.concurrent.TimeUnit;

public class EarthquakeSyncUtils {

    private static final String LOG_TAG = EarthquakeSyncUtils.class.getSimpleName();

    //private static final int SYNC_INTERVAL_PERIODICITY = new Time(System.currentTimeMillis()).getMinutes();
    private static final int SYNC_INTERVAL_PERIODICITY = (int) TimeUnit.HOURS.toSeconds(24);
    private static final int SYNC_INTERVAL_TOLERANCE = (int) TimeUnit.HOURS.toSeconds(1);

    private static boolean sInitialized;

    private static final String QUAKE_REPORT_SYNC_TAG = "quakereport-sync";

    static void scheduleFirebaseJobDispatcherSync(final Context context) {

        Driver driver = new GooglePlayDriver(context);
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(driver);

        Job syncEarthquakeJob = dispatcher.newJobBuilder()
                .setService(EarthquakeFirebaseJobService.class)
                .setTag(QUAKE_REPORT_SYNC_TAG)
                .setConstraints(Constraint.ON_ANY_NETWORK)
                .setLifetime(Lifetime.FOREVER)
                .setRecurring(true)
                //60*60*24,60*60*24+60 => ogni giorno
                .setTrigger(Trigger.executionWindow(
                        //SYNC_INTERVAL_PERIODICITY,
                        (60*60*24),
                        //SYNC_INTERVAL_PERIODICITY + SYNC_INTERVAL_TOLERANCE))
                        (60*60*24) + 60))
                //.setTrigger(Trigger.executionWindow(0, 30))                                           // per i test
                .setReplaceCurrent(true)
                .build();

        Log.e(LOG_TAG, "Notification Trigger.executionWindow(\n" +
                "                        //SYNC_INTERVAL_PERIODICITY,\n" +
                "                        (60*60*24),\n" +
                "                        //SYNC_INTERVAL_PERIODICITY + SYNC_INTERVAL_TOLERANCE))\n" +
                "                        (60*60*24) + 60)): ");

        dispatcher.schedule(syncEarthquakeJob);

        //Log.e(LOG_TAG, "Scheduled");
    }

    synchronized public static void initialize(final Context context) {

        if (sInitialized) return;

        sInitialized = true;

        scheduleFirebaseJobDispatcherSync(context);

    }

}