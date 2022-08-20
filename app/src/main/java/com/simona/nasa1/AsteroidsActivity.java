package com.simona.nasa1;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.simona.nasa1.asteroid.AsteroidADAPTER;
import com.simona.nasa1.asteroid.Asteroid;
import com.simona.nasa1.asteroid.AsteroidsApproachingDatesInterface;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AsteroidsActivity extends AppCompatActivity implements AsteroidsApproachingDatesInterface, View.OnClickListener {

    Button onlyDangerousAsteroidsBtn, allAsteroidsBtn;
    TextView infoTV, loadingDataTV;
    ArrayList<Asteroid> arrayAsteroids;
    ArrayList<Asteroid> arrayAsteroidsFULL;
    RecyclerView rv;
    AsteroidADAPTER mAdapter;
    public static final String NUMELE = "n";
    public static final String DIAMTERUL = "diam";
    public static final String IDUL = "idul";
    Handler handler;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asteroids);

        initViews();
        getArrayAsteroidsFromJson();
    }

    private String URLlink() {
        String x = "https://api.nasa.gov/neo/rest/v1/feed?api_key=" + MainActivity.API_KEY;
        return x;
    }

    @Override
    public void getDetailsOfAnAsteroid(int pozi) {
        Asteroid clickedAsteroid = arrayAsteroids.get(pozi);
//        Toast.makeText(this, "clicked on " + clickedAsteroid.getNameAsteroid(), Toast.LENGTH_SHORT).show();
        Intent i = new Intent(AsteroidsActivity.this, AsteroidAproachingDatesActivity.class);

        i.putExtra(NUMELE, clickedAsteroid.getNameAsteroid());
        i.putExtra(DIAMTERUL, String.valueOf(clickedAsteroid.getDiameterMax()));
        i.putExtra(IDUL, clickedAsteroid.getIdAsteroid());
        startActivity(i);
    }

    @Override
    public void onClick(View view) {
        int clickedID = view.getId();
        switch (clickedID) {
            case R.id.onlyDangerousAsteroidsBtn:
                mAdapter.getFilter().filter("");
                infoTV.setText("Toti asteroizii cotati ca fiind periculosi, care vor trece " +
                        "pe langa Pamant, timp de 7 zile, incepand cu data de " + currentDate() + ":");
                break;

            case R.id.allAsteroidsBtn:
                infoTV.setText("Toti asteroizii care vor trece pe langa Pamant, " +
                        "timp de 7 zile, incepand cu data de " + currentDate());
                arrayAsteroids.clear();
                arrayAsteroids.addAll(arrayAsteroidsFULL);
                refreshAdaptor();
                break;
        }
    }

    void getArrayAsteroidsFromJson() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String urlForAsteroids = URLlink();
                arrayAsteroids = UtilsGeneral.getAsteroidsListFromJson(urlForAsteroids);
                arrayAsteroidsFULL = new ArrayList<>(arrayAsteroids);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        infoTV.setText("Toti asteroizii care vor trece pe langa Pamant," +
                                " timp de 7 zile, incepand cu data de " + currentDate());
                        refreshAdaptor();
                        progressBar.setVisibility(View.GONE);
                        loadingDataTV.setVisibility(View.GONE);
                    }
                });
            }
        });
        thread.start();
    }

    private String currentDate() {
        Date today = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(today);
    }

    private void refreshAdaptor() {
        mAdapter = new AsteroidADAPTER(this, arrayAsteroids, this);
        rv.setAdapter(mAdapter);
    }

    private void initViews() {
        handler = new Handler(Looper.getMainLooper());

        infoTV = findViewById(R.id.infoAsteroidsTV);
        loadingDataTV = findViewById(R.id.textView);
        progressBar = findViewById(R.id.progressBar2);
        onlyDangerousAsteroidsBtn = findViewById(R.id.onlyDangerousAsteroidsBtn);
        allAsteroidsBtn = findViewById(R.id.allAsteroidsBtn);
        onlyDangerousAsteroidsBtn.setOnClickListener(this::onClick);
        allAsteroidsBtn.setOnClickListener(this::onClick);

        arrayAsteroids = new ArrayList<>();

        rv = findViewById(R.id.allAsteroidsRV);
        rv.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new AsteroidADAPTER(this, arrayAsteroids, this);
        rv.setAdapter(mAdapter);
    }

}
