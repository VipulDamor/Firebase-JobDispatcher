package com.eryushion.jobschedulardemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.RetryStrategy;
import com.firebase.jobdispatcher.Trigger;

public class MainActivity extends AppCompatActivity {

    public static final String JObTag = "jobservice";
    FirebaseJobDispatcher firebaseJobDispatcher;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseJobDispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(this));


        findViewById(R.id.btn_startJob).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startJob();
            }
        });

        findViewById(R.id.btn_stopJob).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopJob();
            }
        });

    }

    private void startJob() {
        Job job = firebaseJobDispatcher.newJobBuilder()
                .setService(MyJobService.class)
                .setLifetime(Lifetime.FOREVER)
                .setRecurring(true)
                .setTag(JObTag)
                .setTrigger(Trigger.executionWindow(5, 5))
                .setRetryStrategy(RetryStrategy.DEFAULT_EXPONENTIAL)
                .setReplaceCurrent(false)
                .setConstraints(Constraint.ON_ANY_NETWORK) // TODO: 11/12/18  if you need network
                .build();
        firebaseJobDispatcher.mustSchedule(job);
        Toast.makeText(this, "job started..", Toast.LENGTH_SHORT).show();
    }

    private void stopJob() {
        firebaseJobDispatcher.cancel(JObTag); // todo this will cancel only perticular job
        //firebaseJobDispatcher.cancelAll(); //todo this will cancel all your jobs
        Toast.makeText(this, "job cancelled..", Toast.LENGTH_SHORT).show();
    }
}
