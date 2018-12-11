package com.eryushion.jobschedulardemo;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.widget.Toast;

import com.firebase.jobdispatcher.JobService;


/**
 * Created by eryusdev on 11/12/18.
 */

public class MyJobService extends JobService {


    BackGround backGround;

    @SuppressLint("StaticFieldLeak")
    @Override
    public boolean onStartJob(final com.firebase.jobdispatcher.JobParameters job) {

        backGround = new BackGround() {
            @Override
            protected void onPostExecute(String s) {
                Toast.makeText(getApplicationContext(), "i am from post Execute....", Toast.LENGTH_LONG).show();
                jobFinished(job, false);

            }
        };
        backGround.execute();
        return true;
        // TODO: 11/12/18  if your job is short then you can return false other wise return true
    }

    @Override
    public boolean onStopJob(com.firebase.jobdispatcher.JobParameters job) {
        return true;
        //todo to reschedule the failed job you can return true if the job is failed you can retry from here
    }


    @SuppressLint("StaticFieldLeak")
    public class BackGround extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            return "Yay...  i am From Background....";
        }
    }
}
