package it.fdepedis.earthquake.settings;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

import androidx.appcompat.app.AppCompatActivity;
import it.fdepedis.earthquake.R;
import it.fdepedis.earthquake.activity.EarthquakeActivity;

public class SettingsActivity extends AppCompatActivity {

    private static final String LOG_TAG = SettingsActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
    }

    public static class EarthquakePreferenceFragment extends PreferenceFragment
            implements Preference.OnPreferenceChangeListener {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.settings_main);

            // Given the key of a minMagnitude preferences, we can use PreferenceFragment's findPreference()
            // method to get the Preference object and setup the preference
            Preference minMagnitude = findPreference(getString(R.string.settings_min_magnitude_key));
            bindPreferenceSummaryToValue(minMagnitude);

            Preference orderBy = findPreference(getString(R.string.settings_order_by_key));
            bindPreferenceSummaryToValue(orderBy);

            Preference numItems = findPreference(getString(R.string.settings_num_item_key));
            bindPreferenceSummaryToValue(numItems);

            Preference minMagNotify = findPreference(getString(R.string.settings_min_mag_notify_key));
            bindPreferenceSummaryToValue(minMagNotify);
        }

        @Override
        public boolean onPreferenceChange(Preference preference, Object value){

            String stringValue = value.toString();
            // Se il parametro "preference" è una lista trova l'indice del valore
            // e salvalo nelle preference
            if (preference instanceof ListPreference) {
                ListPreference listPreference = (ListPreference) preference;
                int prefIndex = listPreference.findIndexOfValue(stringValue);
                if (prefIndex >= 0) {
                    CharSequence[] labels = listPreference.getEntries();
                    preference.setSummary(labels[prefIndex]);
                }
            } else if (preference instanceof CheckBoxPreference) {
                preference.setSummary(stringValue);
            } else {
                preference.setSummary(stringValue);
            }
            return true;
        }

        private void bindPreferenceSummaryToValue(Preference preference) {
            preference.setOnPreferenceChangeListener(this);
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(preference.getContext());
            String preferenceString = preferences.getString(preference.getKey(), "");
            onPreferenceChange(preference, preferenceString);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, EarthquakeActivity.class);
        startActivity(intent);
        super.onBackPressed();
    }

}