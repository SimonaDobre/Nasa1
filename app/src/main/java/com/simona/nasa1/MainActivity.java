package com.simona.nasa1;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.simona.nasa1.earthPictures.GeneralPicture;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String API_KEY = BuildConfig.API_KEY;
    Button toMarsPictureBtn, asteroidsBtn, toEarthPictureBtn,
            toMonitoredBtn, specificSearchBtn, fireballBtn;
    ImageView pictureOfTheDay;
    TextView explanationTV, titleTV, infoTV; // linkForVideoTV;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        getPictureOfTheDay();

    }

    void getPictureOfTheDay() {
        final GeneralPicture[] potd = new GeneralPicture[1];
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String apiUrl = linkForPictureOfTheDay();
                potd[0] = UtilsGeneral.obtainPictureOfTheDay(apiUrl);

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        titleTV.setText(potd[0].getTitle());
                        explanationTV.setText(potd[0].getExplanation());
                        if (potd[0].getMediaType().equals("image")) {
                            Picasso.with(MainActivity.this).load(potd[0].getPictureURL()).into(pictureOfTheDay);
                            infoTV.setText("Poza zilei " + todayDate());
                        } else {
                            pictureOfTheDay.setVisibility(View.GONE);
//                            linkForVideoTV.setVisibility(View.VISIBLE);
//                            linkForVideoTV.setText("Acesta este situl: " + pz[0].getPictureURL());
                            // linkForVideo.setMovementMethod(LinkMovementMethod.getInstance());
//                            Linkify.addLinks(linkForVideoTV, Linkify.WEB_URLS);
//                            infoTV.setText("Filmul zilei de " + todayDate());
                        }
                    }
                });
            }
        });
        thread.start();
    }

    private String todayDate() {
        Date todayDate = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(todayDate);
    }

    private String linkForPictureOfTheDay() {
        String x = "https://api.nasa.gov/planetary/apod?date="
                + todayDate()
                + "&api_key=" + API_KEY;
        return x;
    }

    @Override
    public void onClick(View view) {
        int clickedID = view.getId();
        switch (clickedID) {

            case R.id.toMarsPicturesBtn:
                startActivity(new Intent(MainActivity.this, MarsPicturesActivity.class));
                break;

            case R.id.toAsteroidsBtn:
                startActivity(new Intent(MainActivity.this, AsteroidsActivity.class));
                break;

            case R.id.toEarthPicturesBtn:
                startActivity(new Intent(MainActivity.this, EarthPicturesActivity.class));
                break;

            case R.id.toMonitoredBtn:
                startActivity(new Intent(MainActivity.this, MonitoredAsteroidsActivity.class));
                break;

            case R.id.toClosestAsteroidsBtn:
                startActivity(new Intent(MainActivity.this, NearestAsteroidsActivity.class));
                break;

            case R.id.fireballBtn:
                startActivity(new Intent(MainActivity.this, FireballMapsActivity.class));
                break;
        }
    }

    private void initViews() {
        handler = new Handler(Looper.getMainLooper());

        toMarsPictureBtn = findViewById(R.id.toMarsPicturesBtn);
        asteroidsBtn = findViewById(R.id.toAsteroidsBtn);
        toEarthPictureBtn = findViewById(R.id.toEarthPicturesBtn);
        toMonitoredBtn = findViewById(R.id.toMonitoredBtn);
        specificSearchBtn = findViewById(R.id.toClosestAsteroidsBtn);
        fireballBtn = findViewById(R.id.fireballBtn);

        toMarsPictureBtn.setOnClickListener(this::onClick);
        asteroidsBtn.setOnClickListener(this::onClick);
        toEarthPictureBtn.setOnClickListener(this::onClick);
        toMonitoredBtn.setOnClickListener(this::onClick);
        specificSearchBtn.setOnClickListener(this::onClick);
        fireballBtn.setOnClickListener(this::onClick);

        pictureOfTheDay = findViewById(R.id.pictureOfTheDayIV);
        explanationTV = findViewById(R.id.explanationTV);
        titleTV = findViewById(R.id.titleTV);
//        linkForVideoTV = findViewById(R.id.textView5);
        infoTV = findViewById(R.id.infoTV);
    }


}
