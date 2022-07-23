package com.simona.nasa1;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.simona.nasa1.nearestAsteroids.NearestAsteroid;
import com.simona.nasa1.nearestAsteroids.NearestAsteroidsADAPTER;
import com.simona.nasa1.nearestAsteroids.NearestAsteroidsUTILS;

import java.util.ArrayList;

public class NearestAsteroidsActivity extends AppCompatActivity {

    TextView tv, loadingTV;
    ProgressBar progressBar;
    RecyclerView rv;
    ArrayList<NearestAsteroid> arrayNearestAsteroids;
    NearestAsteroidsADAPTER mAdapter;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearest_asteroids);

        initViews();
        loadNearestAsteroids();

    }

    void loadNearestAsteroids() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                arrayNearestAsteroids = NearestAsteroidsUTILS.getDatesOfClosnessApproach(url());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        refreshAdapter();
                        progressBar.setVisibility(View.GONE);
                        loadingTV.setVisibility(View.GONE);
                    }
                });
            }
        });
        thread.start();
    }

    private String url() {
        String x = "https://ssd-api.jpl.nasa.gov/cad.api";
        return x;
    }

    private void refreshAdapter() {
        mAdapter = new NearestAsteroidsADAPTER(this, arrayNearestAsteroids);
        rv.setAdapter(mAdapter);
    }

    void initViews() {
        arrayNearestAsteroids = new ArrayList<>();
        handler = new Handler(Looper.getMainLooper());

        tv = findViewById(R.id.info2TV);
        tv.setText("Asteroizi care vor trece foarte aproape de Pamant, in urmatoarea perioada:");
        loadingTV = findViewById(R.id.textView4);
        progressBar = findViewById(R.id.progressBar5);

        rv = findViewById(R.id.listClosenessRV);
        rv.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new NearestAsteroidsADAPTER(this, arrayNearestAsteroids);
        rv.setAdapter(mAdapter);

    }

}
