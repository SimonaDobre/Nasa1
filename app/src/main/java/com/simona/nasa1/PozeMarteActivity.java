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
import java.util.GregorianCalendar;

public class PozeMarteActivity extends AppCompatActivity {

    RecyclerView rv;
    AdapterRecyclerPozeMarte mAdapter;
    ArrayList<PozaMarte> arrayPictures;

    TextView tv1, tv2, infoGenerale, anotimp, temp, pres, vitVantMin, vitVantMax;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poze_marte);

        arrayPictures = new ArrayList<>();

        init();

        ClasaAsyncPtPoze clasy = new ClasaAsyncPtPoze();
        clasy.execute(linkulPtAccesatPtPoze());

        rv = findViewById(R.id.listOfPictures);
        rv.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new AdapterRecyclerPozeMarte(this, arrayPictures);
        rv.setAdapter(mAdapter);

        ClasAsyncPtVreme clasVreme = new ClasAsyncPtVreme();
        clasVreme.execute(linkPtVreme());


    }


    private String dataDeAzi(){
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = simpleDateFormat.format(c);
        return formattedDate;
    }

    private String yesterday(){
       Calendar cal = Calendar.getInstance();
       cal.add(Calendar.DATE, -1);
       DateFormat formatulDatei = new SimpleDateFormat("yyyy-MM-dd");
       String dataIeri = formatulDatei.format(cal.getTime());
       return dataIeri;
    }

    private String alaltaieri(){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -2);
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String alalttaier = sdf.format(cal.getTime());
        return  alalttaier;

    }


    private String acum5Zile(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -5);
        String alaltaieri = dateFormat.format(cal.getTime());
        return alaltaieri;
    }

    private String linkulPtAccesatPtPoze(){
        String dataPtCareCerPozele = yesterday();
        String link = "https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos?earth_date="
                + dataPtCareCerPozele
                + "&api_key=6cx6Qw5nthtvSz47ffTv4EHZAbNBiMSMnu4iyuV1";
        return link;
    }

    public class ClasaAsyncPtPoze extends AsyncTask<String, Void, ArrayList<PozaMarte>>{

        @Override
        protected ArrayList<PozaMarte> doInBackground(String... strings) {
            ArrayList<PozaMarte> sirObtinut = new ArrayList<>();
            sirObtinut = UtilsPtPozeMarte.toateLaUnLocPtPoze(strings[0]);
            return sirObtinut;
        }
        @Override
        protected void onPostExecute(ArrayList<PozaMarte> picturesses) {
            updateDupaAsyncPtPoze(picturesses);

        }
    }

    private void updateDupaAsyncPtPoze(ArrayList<PozaMarte> aa){
        arrayPictures = aa;

        mAdapter = new AdapterRecyclerPozeMarte(this, arrayPictures);
        rv.setAdapter(mAdapter);
    }




    private String linkPtVreme(){
        String x = "https://api.nasa.gov/insight_weather/?api_key=kEfXDqiER0JSBhCQEt86TlQVC3FSkG5sF3X9y46N&feedtype=json&ver=1.0";
        return x;
    }

    public class ClasAsyncPtVreme extends AsyncTask<String, Void, VremeaPeMarte>{

        @Override
        protected VremeaPeMarte doInBackground(String... strings) {
            return UtilsPtVremeMarte.obtineVremeaMarte(strings[0]);
        }

        @Override
        protected void onPostExecute(VremeaPeMarte vpm) {
            infoGenerale.setText("Vremea pe Marte pentru data " + alaltaieri());
            anotimp.setText("Anotimp: " + vpm.getAnotimp());
            temp.setText("Temp minima: " + vpm.getTemperaturaMin() + " grade C; \n" +
                    "Temp max: " + vpm.getTemperaturaMax() + "grade C" );
            if (vpm.getPresiunea() == 0 ){
                pres.setText("Presiunea: nu este disponibila" );
            }
            else {
                pres.setText("Presiunea: " + vpm.getPresiunea());
            }

            if (vpm.getVitezavantMin() == 0){
                vitVantMin.setText("Viteza minima vant: nu sunt date disponibile");
            }
            else {
                double v = vpm.getVitezavantMin();
                v *= 100;
                v = Math.floor(v)/100;
                vitVantMin.setText("Viteza vant minima: " + v + "m/s, adica " + Math.floor(v*3600*100/1000)/100 + " km/h");
            }

            if (vpm.getVitezavantMax() == 0){
                vitVantMax.setText("Viteza maxima vant: nu sunt date disponibile");
            }
            else {
                double v = vpm.getVitezavantMax();
                v *= 100;
                v = Math.floor(v)/100;
                vitVantMax.setText("Viteza vant maxima: " + v + "m/s, adica " + Math.floor(v*3600*100/1000)/100 + " km/h");
            }


        }
    }




    private void init(){

        tv1 = findViewById(R.id.textView);
        tv2 = findViewById(R.id.textView2);

        tv1.setText("Afizez poze pt data: " + yesterday());
      //  tv2.setText(yesterday());

        infoGenerale = findViewById(R.id.textViewInfoGeneraleVreme);
        infoGenerale.setText("Vremea pe Marte in data " + alaltaieri());

        anotimp = findViewById(R.id.textViewAnotimp);
        temp = findViewById(R.id.textViewTemperatura);
        pres = findViewById(R.id.textViewPresiunea);
        vitVantMin = findViewById(R.id.textViewVitezaVantMIn);
        vitVantMax = findViewById(R.id.textViewVitezaVantMax);
    }

}
