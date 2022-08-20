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

import com.simona.nasa1.earthPictures.GeneralPicture;
import com.simona.nasa1.marsPictures.MarsPicturesADAPTER;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class MarsPicturesActivity extends AppCompatActivity {

    RecyclerView rv;
    MarsPicturesADAPTER mAdapter;
    ArrayList<GeneralPicture> arrayMarsPictures;
//    String season, pressure;
//    int minWindSpeed, maxWindSpeed, temperature;

    TextView titleTV, infoGeneralTV, seasonTV, tempTV, presTV, maxWindSpeedTV, loadingPicturesTV;
    Handler handler;

    Calendar cal;
    SimpleDateFormat shortDateFormat;

    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mars_pictures);

        initViews();
        displayMarsPictures();

    }

    void displayMarsPictures() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                arrayMarsPictures = UtilsGeneral.getMarsPicturesArray(urlForMarsPictures());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter = new MarsPicturesADAPTER(MarsPicturesActivity.this, arrayMarsPictures);
                        rv.setAdapter(mAdapter);
                        tempTV.setText("Temperatura: -60 grade C");
                        maxWindSpeedTV.setText("Viteza vantului: 36 km/h");
                        presTV.setText("Presiunea: 750 P");
                        progressBar.setVisibility(View.GONE);
                        loadingPicturesTV.setVisibility(View.GONE);
                    }
                });
            }
        });
        thread.start();
    }

    private String yesterday() {
        cal.add(Calendar.DATE, -3);
        String yesterdayDate = shortDateFormat.format(cal.getTime());
        return yesterdayDate;
    }

    private String aDayBeforeYesterday() {
        cal.add(Calendar.DATE, -1);
        String adby = shortDateFormat.format(cal.getTime());
        return adby;
    }

    private String urlForMarsPictures() {
        String pictureDate = yesterday();
        String link = "https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos?earth_date="
                + pictureDate
                + "&api_key=" + MainActivity.API_KEY;
        return link;
    }

    private void initViews() {
        arrayMarsPictures = new ArrayList<>();
        handler = new Handler(Looper.getMainLooper());

        cal = Calendar.getInstance();
        shortDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        titleTV = findViewById(R.id.infoTV);
        titleTV.setText("Poze MARTE din data: " + yesterday());
        infoGeneralTV = findViewById(R.id.infoWeatherTV);
        infoGeneralTV.setText("Vremea pe Marte in data " + aDayBeforeYesterday());
        loadingPicturesTV = findViewById(R.id.textView2);

        progressBar = findViewById(R.id.progressBar3);

        seasonTV = findViewById(R.id.seasonTV);
        tempTV = findViewById(R.id.temperatureTV);
        presTV = findViewById(R.id.pressureTV);
        maxWindSpeedTV = findViewById(R.id.maxWindSpeedTV);

        rv = findViewById(R.id.listMarsPicturesRV);
        rv.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new MarsPicturesADAPTER(this, arrayMarsPictures);
        rv.setAdapter(mAdapter);
    }


}
