package it.fdepedis.earthquake.sync;

import android.content.Context;
import android.util.Log;
import java.net.URL;
import java.util.Date;
import it.fdepedis.earthquake.settings.EarthquakePreferences;

public class EarthquakeSyncTask {

    private static final String LOG_TAG = EarthquakeSyncTask.class.getSimpleName();

    synchronized public static void checkEarthquake(Context context) {

        try {
            //Log.e(LOG_TAG, "syncQuakeReport: in execution");

            boolean notificationsEnabled = EarthquakePreferences.isNotificationsEnabled(context);
            Log.e(LOG_TAG, "notificationsEnabled: " + notificationsEnabled);

            if (notificationsEnabled) {
/*
                URL quakeReportRequestUrl = Utils.getNotificationURLByTime(context);

                String queryJSONResponse = QueryUtils.makeHttpRequest(quakeReportRequestUrl);

                JSONObject baseJsonResponse = new JSONObject(queryJSONResponse);
                JSONArray earthquakeArray = baseJsonResponse.getJSONArray("features");

                JSONObject currentEarthquake = earthquakeArray.getJSONObject(0);
                JSONObject properties = currentEarthquake.getJSONObject("properties");
                Log.e(LOG_TAG, "properties: " + properties);

                double currMagNotification = properties.getDouble("mag");
                String formatCurrMagNotification = Utils.formatMagnitude(currMagNotification);
                Log.e(LOG_TAG, "formatCurrMagNotification: " + formatCurrMagNotification);

                String currPlace = properties.getString("place");
                Log.e(LOG_TAG, "currPlace: " + currPlace);

                long currTime = properties.getLong("time");
                Date dateObject = new Date(currTime);
                String formatCurrTime = Utils.formatDate(dateObject);
                Log.e(LOG_TAG, "formatCurrTime: " + formatCurrTime);

                String formatCurrHour = Utils.formatTime(dateObject);
                Log.e(LOG_TAG, "formatCurrHour: " + formatCurrHour);

                String url = properties.getString("url");
                Log.e(LOG_TAG, "url: " + url);

                String minMagnitude = EarthquakePreferences.getMinMagnitudePreferences(context);

                // se viene restituito un valore di magnitudo maggiore o uguale
                // a quello settato nelle preferences invia una notifica
                // se abilitata dalle preferences
                if (currMagNotification >= Double.parseDouble(minMagnitude)) {
                    Log.e(LOG_TAG, "ATTENZIONE: fai partire notifica ==> currMagNotification: " + currMagNotification + " >= " + "minMagnitude: " + minMagnitude);

                    NotificationUtils.notifyUserOfNewQuakeReport(context, formatCurrMagNotification, currPlace, formatCurrTime, formatCurrHour, url);
                }*/
            }
        } catch (Exception e) {
            /* Server probably invalid */
            e.printStackTrace();
        }
    }
}