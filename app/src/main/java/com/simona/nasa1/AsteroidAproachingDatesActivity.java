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

import com.simona.nasa1.asteroidApproachingDates.AsteroidApproachingDatesADAPTER;
import com.simona.nasa1.nearestAsteroids.NearestAsteroid;

import java.util.ArrayList;

public class AsteroidAproachingDatesActivity extends AppCompatActivity {

    TextView nameTV, diameterTV, infoTV, loadingTV;
    ProgressBar progressBar;
    RecyclerView rv;
    AsteroidApproachingDatesADAPTER myAdapter;
    String receivedID, receivedName, receivedDiameter;
    Intent j;
    Handler handler;
    ArrayList<NearestAsteroid> listClosenessDates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asteroid_approaching_dates);

        initViews();
        getAsteroidDetailsFromJson();

    }

    private String urlForAsteroidDetails() {
        return "https://www.neowsapp.com/rest/v1/neo/" + receivedID;
    }

    void getAsteroidDetailsFromJson() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                listClosenessDates = UtilsGeneral.getNearestAsteroidsArray(urlForAsteroidDetails());

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        myAdapter = new AsteroidApproachingDatesADAPTER(AsteroidAproachingDatesActivity.this, listClosenessDates);
                        rv.setAdapter(myAdapter);
                        progressBar.setVisibility(View.GONE);
                        loadingTV.setVisibility(View.GONE);
                    }
                });
            }
        });
        thread.start();
    }


    private void initViews() {
        handler = new Handler(Looper.getMainLooper());

        nameTV = findViewById(R.id.name1TV);
        diameterTV = findViewById(R.id.diameter1TV);
        infoTV = findViewById(R.id.info6TV);
        loadingTV = findViewById(R.id.textView6);
        progressBar = findViewById(R.id.progressBar6);

        j = getIntent();
        receivedName = j.getStringExtra(AsteroidsActivity.NUMELE);
        receivedDiameter = j.getStringExtra(AsteroidsActivity.DIAMTERUL);
        receivedID = j.getStringExtra(AsteroidsActivity.IDUL);

        nameTV.setText("Asteroid \n" + receivedName);
        diameterTV.setText("Max diameter: " + receivedDiameter + "m");
        infoTV.setText("All dates when it passed / will pass the Earth:");

        rv = findViewById(R.id.datesOfClosenessRV);
        rv.setLayoutManager(new LinearLayoutManager(this));
    }

}
