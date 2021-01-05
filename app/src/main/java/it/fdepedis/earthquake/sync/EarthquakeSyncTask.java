package it.fdepedis.earthquake.sync;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Map;

import it.fdepedis.earthquake.activity.EarthquakeActivity;
import it.fdepedis.earthquake.model.EarthquakeBean;
import it.fdepedis.earthquake.model.FeatureBean;
import it.fdepedis.earthquake.network.GetDataService;
import it.fdepedis.earthquake.network.RetrofitClientInstance;
import it.fdepedis.earthquake.settings.EarthquakePreferences;
import it.fdepedis.earthquake.utils.NotificationUtils;
import it.fdepedis.earthquake.utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EarthquakeSyncTask {

    private static final String LOG_TAG = EarthquakeSyncTask.class.getSimpleName();
    private static List<FeatureBean> featureBeanList;

    synchronized public static void checkEarthquake(final Context context) {

        try {
            boolean notificationsEnabled = EarthquakePreferences.isNotificationsEnabled(context);
            Log.e(LOG_TAG, "notificationsEnabled: " + notificationsEnabled);

            if (notificationsEnabled) {

                Map<String, String> parameters = Utils.getNotificationParamsQuery(context);

                GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
                Call<EarthquakeBean> call = service.getEarthQuakes(parameters);
                call.enqueue(new Callback<EarthquakeBean>() {
                    @Override
                    public void onResponse(Call<EarthquakeBean> call, Response<EarthquakeBean> response) {
                        String result;
                        if (response != null) {
                            Log.e(LOG_TAG, "response: " + response);
                            try {
                                result = response.isSuccessful() ? response.body().toString() : null;

                                JSONObject jsonObject = new JSONObject(new Gson().toJson(response.body()));
                                Log.e(LOG_TAG, "resultCallForPush: " + jsonObject.toString());

                                featureBeanList = response.body().getFeatures();
                                /*Log.e(LOG_TAG, "featureBeanList: " + featureBeanList);
                                Log.e(LOG_TAG, "featureBeanList Mag: " + featureBeanList.get(0).getPropertiesBean().getMag());
                                Log.e(LOG_TAG, "featureBeanList Place: " + featureBeanList.get(0).getPropertiesBean().getPlace());
                                Log.e(LOG_TAG, "featureBeanList Time: " + featureBeanList.get(0).getPropertiesBean().getTime());
                                Log.e(LOG_TAG, "featureBeanList URL: " + featureBeanList.get(0).getPropertiesBean().getUrl());
                                Log.e(LOG_TAG, "featureBeanList Type: " + featureBeanList.get(0).getPropertiesBean().getType());
                                Log.e(LOG_TAG, "featureBeanList Intensity: " + featureBeanList.get(0).getPropertiesBean().getIntensity());*/

                                double currMagNotification = featureBeanList.get(0).getPropertiesBean().getMag();

                                String minMagnitude = EarthquakePreferences.getMinMagnitudePreferences(context);

                                // se viene restituito un valore di magnitudo maggiore o uguale
                                // a quello settato nelle preferences invia una notifica
                                // se abilitata dalle preferences
                                if (currMagNotification >= Double.parseDouble(minMagnitude)) {
                                    Log.e(LOG_TAG, "ATTENZIONE: fai partire notifica ==> currMagNotification: " + currMagNotification + " >= " + "minMagnitude: " + minMagnitude);

                                    NotificationUtils.notifyUserOfNewEarthquakeReport(context, featureBeanList);
                                }
                            } catch (Exception e) {
                                Log.e(LOG_TAG, "Exception: " + e.getLocalizedMessage());
                            }
                        } else {
                            Toast.makeText(context, "Code: " + response.code(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<EarthquakeBean> call, Throwable t) {

                        Toast.makeText(context, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        } catch (Exception e) {
            /* Server probably invalid */
            e.printStackTrace();
        }
    }
}