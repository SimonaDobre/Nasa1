package com.simona.nasa1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class PozeCuPamantulActivity extends AppCompatActivity {

    TextView tv;
    RecyclerView rv;
    AdaptorRecylerPozePamant mAdapter;
    ArrayList<PozaPamant> sirPoze;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poze_cu_pamantul);

        tv = findViewById(R.id.textView6);
        sirPoze = new ArrayList<>();
        rv = findViewById(R.id.listaPozePamant);
        mAdapter = new AdaptorRecylerPozePamant(this, sirPoze);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(mAdapter);

        ClasaAsyncPozePamant clasy = new ClasaAsyncPozePamant();
        clasy.execute(linkulDeAccesatPtToatePozele());

    }


    public class ClasaAsyncPozePamant extends AsyncTask<String, Void, ArrayList<PozaPamant>>{

        @Override
        protected ArrayList<PozaPamant> doInBackground(String... strings) {
            ArrayList<PozaPamant> sirr = UtilsPtPozeCuPamantul.toateLaUnLoc(strings[0]);
            return sirr;
        }


        @Override
        protected void onPostExecute(ArrayList<PozaPamant> pozaPamants) {
            tv.setText("poze Pamant din data de " + dataPozelor());
            mAdapter = new AdaptorRecylerPozePamant(PozeCuPamantulActivity.this, pozaPamants );
            rv.setAdapter(mAdapter);
        }
    }



    private String dataPozelor(){
       // Date dataAzi = Calendar.getInstance().getTime();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -6);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date dataMinusCinciZile = cal.getTime();
        String azi = dateFormat.format(dataMinusCinciZile);
        return azi;
    }

    private String linkulDeAccesatPtToatePozele(){
        String linkk = "https://api.nasa.gov/EPIC/api/natural/date/"
                + dataPozelor()
                + "?api_key=kEfXDqiER0JSBhCQEt86TlQVC3FSkG5sF3X9y46N";
        return linkk;
    }


    private String linkulPtFiecarePoza(String numelePozei){
        String x = "https://api.nasa.gov/EPIC/archive/natural/"
                + dataPozelor()
                + "/png/"
                + numelePozei
                + ".png?api_key=kEfXDqiER0JSBhCQEt86TlQVC3FSkG5sF3X9y46N";
        return x;
    }


}
