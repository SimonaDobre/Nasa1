package com.simona.nasa1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.util.Linkify;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    Button catrePozaMarsBtn,  asteroiziBtn, catrePozeCuPamantulBtn, monitorizatiBtn, cautaPersonalizat, fireballBtn;
    ImageView pictureOfTheDay;
    TextView explicatia, titlu, linkPtVideo, info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        actiuneButoane();

        ClasaAyncPozaZilei clasaAyncPozaZilei = new ClasaAyncPozaZilei();
        clasaAyncPozaZilei.execute(linkulAccesatPtPozaZilet());

    }


    private String dataDeAzi(){
        Date dataDeAzi = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String azi = dateFormat.format(dataDeAzi);
        return azi;
    }

    private String linkulAccesatPtPozaZilet(){
        String x = "https://api.nasa.gov/planetary/apod?date="
                + dataDeAzi()
                + "&api_key=kEfXDqiER0JSBhCQEt86TlQVC3FSkG5sF3X9y46N";
        return x;
    }


    public class ClasaAyncPozaZilei extends AsyncTask<String, Void, PozaZilei>{

        @Override
        protected PozaZilei doInBackground(String... strings) {
            PozaZilei poz = UtilsPtPrimaPagina.toateLaUnLoc(strings[0]);
            return poz;
        }

        @Override
        protected void onPostExecute(PozaZilei pozaZilei) {
            titlu.setText(pozaZilei.getTitle());
            explicatia.setText(pozaZilei.getExplanation());
            if (pozaZilei.getMediaType().equals("image")){
                Picasso.with(MainActivity.this).load(pozaZilei.getPictureURL()).into(pictureOfTheDay);
                info.setText("Poza zilei de " + dataDeAzi());
            }
            else {
                pictureOfTheDay.setVisibility(View.GONE);
                linkPtVideo.setVisibility(View.VISIBLE);
                linkPtVideo.setText("Acesta este situl: " + pozaZilei.getPictureURL());
               // linkPtVideo.setMovementMethod(LinkMovementMethod.getInstance());

                Linkify.addLinks(linkPtVideo, Linkify.WEB_URLS);

                info.setText("Filmul zilei de " + dataDeAzi());
            }
        }
    }

    private void actiuneButoane(){
        catrePozaMarsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(MainActivity.this, PozeMarteActivity.class));
                Toast.makeText(MainActivity.this, "In curs de actualizare.", Toast.LENGTH_SHORT).show();
            }
        });


        asteroiziBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AsteroiziActivity.class));
            }
        });

        catrePozeCuPamantulBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, PozeCuPamantulActivity.class));
            }
        });

        monitorizatiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(MainActivity.this, AsteroMonitorizatiActivity.class));
                Toast.makeText(MainActivity.this, "In curs de actualizare.", Toast.LENGTH_SHORT).show();
            }
        });

        cautaPersonalizat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AsterCeiMaiApropiatiActivity.class ));
            }
        });

        fireballBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, FireballMapsActivity.class ));
            }
        });
    }


    private void init(){
        catrePozaMarsBtn = findViewById(R.id.button);
        pictureOfTheDay = findViewById(R.id.imageView2);
        explicatia = findViewById(R.id.textView3);
        titlu = findViewById(R.id.textView4);
        linkPtVideo = findViewById(R.id.textView5);
        info = findViewById(R.id.textView);
        asteroiziBtn = findViewById(R.id.button3);
        catrePozeCuPamantulBtn = findViewById(R.id.button4);
        monitorizatiBtn = findViewById(R.id.buttonMonitorizati);
        cautaPersonalizat = findViewById(R.id.buttonCautaPersonalizat);
        fireballBtn = findViewById(R.id.buttonFireball);
    }




}
