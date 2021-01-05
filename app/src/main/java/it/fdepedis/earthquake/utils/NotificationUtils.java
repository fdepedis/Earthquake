package it.fdepedis.earthquake.utils;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import java.util.Date;
import java.util.List;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import it.fdepedis.earthquake.R;
import it.fdepedis.earthquake.activity.DetailEarthquakeActivity;
import it.fdepedis.earthquake.model.FeatureBean;

public class NotificationUtils {

    private static final String LOG_TAG = NotificationUtils.class.getSimpleName();

    /*
     * This notification ID can be used to access our notification after we've displayed it. This
     * can be handy when we need to cancel the notification, or perhaps update it. This number is
     * arbitrary and can be set to whatever you like. 3004 is in no way significant.
     */
    private static final int EARTHQUAKE_NOTIFICATION_ID = 3004;

    public static void notifyUserOfNewEarthquakeReport(Context context, List<FeatureBean> featureBeanList) {

        double currMagNotification = featureBeanList.get(0).getPropertiesBean().getMag();
        String formatCurrMagNotification = Utils.formatMagnitude(currMagNotification);
        Log.e(LOG_TAG, "formatCurrMagNotification: " + formatCurrMagNotification);

        String currPlace = featureBeanList.get(0).getPropertiesBean().getPlace();
        Log.e(LOG_TAG, "currPlace: " + currPlace);

        long currTime = featureBeanList.get(0).getPropertiesBean().getTime();
        Date dateObject = new Date(currTime);
        String formatCurrTime = Utils.formatDate(dateObject);
        Log.e(LOG_TAG, "formatCurrTime: " + formatCurrTime);

        String formatCurrHour = Utils.formatTime(dateObject);
        Log.e(LOG_TAG, "formatCurrHour: " + formatCurrHour);

        String url = featureBeanList.get(0).getPropertiesBean().getUrl();
        Log.e(LOG_TAG, "url: " + url);

        Resources resources = context.getResources();
        int largeArtResourceId = R.mipmap.ic_launcher;
        int smallArtResourceId = R.drawable.ic_stat_album;

        Bitmap largeIcon = BitmapFactory.decodeResource(
                resources,
                largeArtResourceId);

        String notificationTitle = context.getString(R.string.app_name);
        String notificationText = "Location: " + currPlace +
                "\n" + "Magnitude: " + formatCurrMagNotification +
                "\n" + "Time: " + formatCurrTime + " - " + formatCurrHour;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            Log.e(LOG_TAG, "notifications???");

            CharSequence name = context.getString(R.string.app_name);
            String description = context.getString(R.string.app_name);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(String.valueOf(EARTHQUAKE_NOTIFICATION_ID), name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);

            Uri earthquakeUri = Uri.parse(url);
            Intent websiteIntent = new Intent(Intent.ACTION_VIEW, earthquakeUri);

            // Create an explicit intent for an Activity in your app
            websiteIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, websiteIntent, 0);

            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, String.valueOf(EARTHQUAKE_NOTIFICATION_ID))
                    .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                    .setSmallIcon(smallArtResourceId)
                    .setLargeIcon(largeIcon)
                    .setContentTitle(notificationTitle)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(notificationText))
                    .setContentText(notificationText)
                    .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000}) //Vibration
                    //.setLights(Color.RED, 3000, 3000) //LED
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);

            Intent detailIntent = new Intent(context, DetailEarthquakeActivity.class);
            FeatureBean featureBean = featureBeanList.get(0);
            //detailIntent.setData(featureBeanList);
            /*Intent intent = new Intent(this, DetailEarthquakeActivity.class);*/
            detailIntent.putExtra("position", featureBean);
            //startActivity(intent);

            /*
            TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(context);
            taskStackBuilder.addNextIntentWithParentStack(detailIntentForToday);
            PendingIntent resultPendingIntent = taskStackBuilder
                    .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

            notificationBuilder.setContentIntent(resultPendingIntent);
            */

            NotificationManagerCompat notification = NotificationManagerCompat.from(context);

            notification.notify(EARTHQUAKE_NOTIFICATION_ID, notificationBuilder.build());

            /*
             * Since we just showed a notification, save the current time. That way, we can check
             * next time the weather is refreshed if we should show another notification.
             */
            //QuakeReportPreferences.saveLastNotificationTime(context, System.currentTimeMillis());
        }

    }

}
