package com.simona.nasa1;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.simona.nasa1.amdd.AMDD;
import com.simona.nasa1.amdd.AMDDutils;
import com.simona.nasa1.amdd.AMDDadapter;

import java.util.ArrayList;

public class AMDDactivity extends AppCompatActivity {

    ArrayList<AMDD> monitorizedAsteroidDetailsArray;
    TextView nameTV, diameterTV, speedTV, loadingTV;
    ProgressBar progressBar;
    RecyclerView rv;
    AMDDadapter mAdapter;
    String receivedName = "";
    int receivedDiam, receivedSpeed;
    Intent receivedIntent;
    Handler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amdd);

        receivedIntent = getIntent();
        receivedName = receivedIntent.getStringExtra(MonitoredAsteroidsActivity.NAME_MONITORIZED_ASTEROID);
        receivedDiam = receivedIntent.getIntExtra(MonitoredAsteroidsActivity.DIAMETER, 0);
        receivedSpeed = receivedIntent.getIntExtra(MonitoredAsteroidsActivity.SPEED, 0);

        initViews();
        displayMonitorizedAsteroidDetails();

    }

    void displayMonitorizedAsteroidDetails() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                monitorizedAsteroidDetailsArray = AMDDutils.getMonitorizedAsteroidDetails(urlForMonitorizedAsteroidDetails());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter = new AMDDadapter(AMDDactivity.this, monitorizedAsteroidDetailsArray);
                        rv.setAdapter(mAdapter);
                        loadingTV.setVisibility(View.GONE);
                        progressBar.setVisibility(View.GONE);
                    }
                });
            }
        });
        thread.start();
    }

    private String urlForMonitorizedAsteroidDetails() {
        // link:  https://ssd-api.jpl.nasa.gov/sentry.api?des=2000%20SG344 (2000 SG344)
        String firstPart = receivedName.substring(receivedName.indexOf("(") + 1, receivedName.indexOf("(") + 5);
        String lastPart = receivedName.substring(receivedName.lastIndexOf(" ") + 1, receivedName.indexOf(")"));
        String link = "https://ssd-api.jpl.nasa.gov/sentry.api?des="
                + firstPart
                + "%20"
                + lastPart;
        return link;
    }

    private void initViews() {
        monitorizedAsteroidDetailsArray = new ArrayList<>();
        handler = new Handler(Looper.getMainLooper());
        nameTV = findViewById(R.id.clickedNameTV);
        diameterTV = findViewById(R.id.clickedDiameterTV);
        speedTV = findViewById(R.id.clickedSpeedTV);
        loadingTV = findViewById(R.id.textView16);
        progressBar = findViewById(R.id.progressBar7);

        rv = findViewById(R.id.closenessDatesForAmonitoredAsteroidRV);
        rv.setLayoutManager(new LinearLayoutManager(this));

        nameTV.setText(receivedName);
        diameterTV.setText("Diametru= " + receivedDiam + " metri");

        String speedString = String.valueOf(receivedSpeed);
        String speedComma = "";
        for (int i = 0; i < speedString.length(); i++) {
            if ((speedString.length() - i - 1) % 3 == 0) {
                speedComma += Character.toString(speedString.charAt(i)) + ",";
            } else {
                speedComma += Character.toString(speedString.charAt(i));
            }
        }
        speedComma = speedComma.substring(0, speedComma.length() - 1);
        speedTV.setText("Viteza la intrarea in atmosfera= " + speedComma + "km/h");
    }


}
