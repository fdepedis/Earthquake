package it.fdepedis.earthquake.settings;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.preference.PreferenceManager;

import it.fdepedis.earthquake.R;

public class EarthquakePreferences {

    private static final String LOG_TAG = EarthquakePreferences.class.getSimpleName();

    /**
     * Get Notification Enable in Notification -> Preferences
     * */
    public static boolean isNotificationsEnabled(Context context) {

        String displayNotificationsKey = context.getString(R.string.pref_enable_notifications_key);

        boolean shouldDisplayNotificationsByDefault = context
                .getResources()
                .getBoolean(R.bool.show_notifications_by_default);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);

        boolean shouldDisplayNotifications = sp
                .getBoolean(displayNotificationsKey, shouldDisplayNotificationsByDefault);

        return shouldDisplayNotifications;
    }

    /**
     * Get Min Magnitude preference in General
     * */
    public static String getMinMagnitudePreferences(Context context) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        String minMagnitude = sharedPrefs.getString(
                context.getString(R.string.settings_min_magnitude_key),
                context.getString(R.string.settings_min_magnitude_default));

        return minMagnitude;
    }

    /**
     * Get Min Magnitude Notification in Notification -> Preferences
     * */
    public static String getMinMagNotificationPreferences(Context context) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        String minMagNotification = sharedPrefs.getString(
                context.getString(R.string.settings_min_mag_notify_key),
                context.getString(R.string.settings_min_mag_notify_default));

        return minMagNotification;
    }

    /**
     * Get OrderBy preference in General
     * */
    public static String getOrderByPreferences(Context context) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        String orderBy = sharedPrefs.getString(
                context.getString(R.string.settings_order_by_key),
                context.getString(R.string.settings_order_by_default));

        return orderBy;
    }

    /**
     * Get NumItems preference in General
     * */
    public static String getNumItemsPreferences(Context context) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        String numItems = sharedPrefs.getString(
                context.getString(R.string.settings_num_item_key),
                context.getString(R.string.settings_num_item_default));

        return numItems;
    }

}
