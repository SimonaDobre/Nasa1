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

import com.simona.nasa1.earthPictures.AdapterEarthPictures;
import com.simona.nasa1.earthPictures.GeneralPicture;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class EarthPicturesActivity extends AppCompatActivity {

    TextView infoTV, loadingPicturesTV;
    ProgressBar progressBar;
    RecyclerView rv;
    AdapterEarthPictures mAdapter;
    ArrayList<GeneralPicture> arrayEarthPictures;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earth_pictures);

        initViews();
        loadEarthPictures();

    }

    private void loadEarthPictures() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                arrayEarthPictures = UtilsGeneral.getEarthPicturesArray(urlToEarthPictures());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        infoTV.setText("Poze cu Terra din data de " + dateOfPictures() + ":");
                        mAdapter = new AdapterEarthPictures(EarthPicturesActivity.this, arrayEarthPictures);
                        rv.setAdapter(mAdapter);
                        progressBar.setVisibility(View.GONE);
                        loadingPicturesTV.setVisibility(View.GONE);
                    }
                });
            }
        });
        thread.start();
    }

    private void initViews() {
        handler = new Handler(Looper.getMainLooper());
        infoTV = findViewById(R.id.info3TV);
        loadingPicturesTV = findViewById(R.id.textView3);
        progressBar = findViewById(R.id.progressBar4);

        arrayEarthPictures = new ArrayList<>();

        rv = findViewById(R.id.earthPicturesRV);
        mAdapter = new AdapterEarthPictures(this, arrayEarthPictures);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(mAdapter);
    }

    private String dateOfPictures() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -20);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateOfPics = dateFormat.format(cal.getTime());
        return dateOfPics;
    }

    private String urlToEarthPictures() {
        String url = "https://api.nasa.gov/EPIC/api/natural/date/"
                + dateOfPictures()
                + "?api_key=" + MainActivity.API_KEY;
        return url;
    }


}
