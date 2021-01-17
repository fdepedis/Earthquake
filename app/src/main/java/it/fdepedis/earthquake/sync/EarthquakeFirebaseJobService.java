package it.fdepedis.earthquake.sync;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

import timber.log.Timber;

public class EarthquakeFirebaseJobService extends JobService {

    private static final String LOG_TAG = EarthquakeFirebaseJobService.class.getSimpleName();

    private AsyncTask<Void, Void, Void> mFetchEarthquakeTask;

    @SuppressLint("StaticFieldLeak")
    @Override
    public boolean onStartJob(final JobParameters jobParameters) {

        mFetchEarthquakeTask = new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... voids) {
                Timber.e("doInBackground: FirebaseJobService in execution");
                Context context = getApplicationContext();
                EarthquakeSyncTask.checkEarthquake(context);

                jobFinished(jobParameters, false);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                jobFinished(jobParameters, false);
                Timber.e("onPostExecute: FirebaseJobService in execution");
            }
        };

        mFetchEarthquakeTask.execute();
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        if (mFetchEarthquakeTask != null) {
            Timber.e("onStopJob: FirebaseJobService finish");
            mFetchEarthquakeTask.cancel(true);
        }
        return true;
    }
}
