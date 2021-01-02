package it.fdepedis.earthquake.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.util.Log;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import androidx.core.content.ContextCompat;
import it.fdepedis.earthquake.R;
import it.fdepedis.earthquake.settings.EarthquakePreferences;

public class Utils {

    private static final String LOG_TAG = Utils.class.getName();

    //public static Context context;

    public static Map getDefaultParamsQuery(Context context) {
        Map<String, String> parameters = new HashMap<>();

        String minMagPrefs = EarthquakePreferences.getMinMagnitudePreferences(context);
        String orderByPrefs = EarthquakePreferences.getOrderByPreferences(context);
        String numItemPrefs = EarthquakePreferences.getNumItemsPreferences(context);

        Log.e(LOG_TAG, "minMagPrefs: " + minMagPrefs);
        Log.e(LOG_TAG, "orderByPrefs: " + orderByPrefs);
        Log.e(LOG_TAG, "numItemPrefs: " + numItemPrefs);

        parameters.put("format", "geojson");
        parameters.put("orderby", orderByPrefs);
        parameters.put("minmag", minMagPrefs);
        parameters.put("limit", numItemPrefs);

        return parameters;
    }

    /*public static URL getNotificationURLByTime(Context context) {

        String minMagNotification = EarthquakePreferences.getMinMagNotificationPreferences(context);

        // Costruisci una URL che abbia un solo elemento recente e con
        // una magnitudine di notifica indicata nelle preferences
        Uri uriBuilder = Uri.parse(USGS_REQUEST_URL).buildUpon()
                .appendQueryParameter("format", "geojson")
                .appendQueryParameter("limit", LIMIT)
                .appendQueryParameter("minmag", minMagNotification)
                .appendQueryParameter("orderby", "time")
                .build();

        try{
            URL quakeReportNotificationQueryUrlByTime = new URL(uriBuilder.toString());
            Log.e(LOG_TAG, "quakeReportNotificationQueryUrlByTime: " + quakeReportNotificationQueryUrlByTime );
            return quakeReportNotificationQueryUrlByTime;
        } catch (MalformedURLException e){
            e.printStackTrace();
            return null;
        }
    }*/

    public static String formatMagnitude(double magnitude) {
        DecimalFormat magnitudeFormat = new DecimalFormat("0.0");
        return magnitudeFormat.format(magnitude);
    }

    public static String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(dateObject);
    }

    public static String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

        return timeFormat.format(dateObject);
    }

    public static String formatTsunami(Context context, int tsunami) {
        String tsunamiLabel;
        switch (tsunami) {
            case 0:
                tsunamiLabel = context.getResources().getString(R.string.formatTsunamiValue_0);
                break;
            case 1:
                tsunamiLabel = context.getResources().getString(R.string.formatTsunamiValue_1);
                break;
            default:
                tsunamiLabel = context.getResources().getString(R.string.formatTsunamiValue_NA);
                break;
        }
        return  tsunamiLabel;
    }

    public static int getAlertColor(Context context, String alert) {
        int alertColorResourceId;
        switch (alert) {
            case "green":
                alertColorResourceId = R.color.greenAlert;
                break;
            case "yellow":
                alertColorResourceId = R.color.yellowAlert;
                break;
            case "orange":
                alertColorResourceId = R.color.orangeAlert;
                break;
            case "red":
                alertColorResourceId = R.color.redAlert;
                break;
            default:
                alertColorResourceId = R.color.colorDefault;
                break;
        }
        return ContextCompat.getColor(context, alertColorResourceId);
    }

    public static int getMagnitudeColor(Context context, double magnitude) {
        int magnitudeColorResourceId;
        int magnitudeFloor = (int) Math.floor(magnitude);
        switch (magnitudeFloor) {
            case 0:
            case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            default:
                magnitudeColorResourceId = R.color.magnitude10plus;
                break;
        }
        return ContextCompat.getColor(context, magnitudeColorResourceId);
    }

    public static String capitalize(String str) {
        if(str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    public static boolean isValid (String value){
        return value != null && !value.isEmpty();
    }
}
