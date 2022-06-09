package com.simona.nasa1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;

public class AMDDactivity extends AppCompatActivity {

    TextView nume, diametru, viteza;
    RecyclerView rv;
    AdapterAMDD mAdapter;
   // ArrayList<AsterMonitDetaliiDATE> sirAster;
    String numePrimit = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aster_monit_d_e_t_a_l_i_i);

       // sirAster = new ArrayList<>();
        rv = findViewById(R.id.dateleApropieriiUnuiAsterMonitoriz);
      //  mAdapter = new AdapterAsterMonitorizDetaliiDATE(this, sirAster);
        rv.setLayoutManager(new LinearLayoutManager(this));
      //  rv.setAdapter(mAdapter);

        init();
        Intent iPrimit = getIntent();
        numePrimit = iPrimit.getStringExtra(AsteroMonitorizatiActivity.NUME_ASTRE_MONIT);
        int diamPrimit = iPrimit.getIntExtra(AsteroMonitorizatiActivity.DIAMETRUL,0);
        int vitezaPrimita = iPrimit.getIntExtra(AsteroMonitorizatiActivity.VITEZA,0);

        nume.setText(numePrimit);
        diametru.setText("Diametru= " + diamPrimit + " metri");

        String vitezaString = String.valueOf(vitezaPrimita);
        String vitezaCuVirgule = "";
        for (int i = 0; i < vitezaString.length(); i++) {
            if ((vitezaString.length() - i - 1) % 3 == 0) {
                vitezaCuVirgule += Character.toString(vitezaString.charAt(i)) + ",";
            } else {
                vitezaCuVirgule+= Character.toString(vitezaString.charAt(i));
            }
        }
        vitezaCuVirgule = vitezaCuVirgule.substring(0, vitezaCuVirgule.length() - 1);

        viteza.setText("Viteza la intrarea in atmosfera= " + vitezaCuVirgule + "km/h");

        ClasaAsyncPtDetaliiDate clasy = new ClasaAsyncPtDetaliiDate();
        clasy.execute(linkPtDetaliiDATEasteroid());

    }


    private String linkPtDetaliiDATEasteroid() {
        // linkul arata asa:
        // https://ssd-api.jpl.nasa.gov/sentry.api?des=2000%20SG344 (2000 SG344)
        // adica se formeaza din primul cuvant, apoi %20, apoi ultimul cuvant
        // si le extrag din numePrimit
        String primul = numePrimit.substring(numePrimit.indexOf("(")+1, numePrimit.indexOf("(")+5);
        String doilea = numePrimit.substring(numePrimit.lastIndexOf(" ") + 1, numePrimit.indexOf(")"));
        String link = "https://ssd-api.jpl.nasa.gov/sentry.api?des="
                + primul
                + "%20"
                + doilea;

        // astea au fost pentru test, ca sa vad ca merge
      //  String link = "https://ssd-api.jpl.nasa.gov/sentry.api?des=2000%20SG344";
       // link = "https://ssd-api.jpl.nasa.gov/sentry.api?des=2018%20VP1";
        return link;
    }

    public class ClasaAsyncPtDetaliiDate extends AsyncTask<String, Void, ArrayList<AMDD>> {

        @Override
        protected ArrayList<AMDD> doInBackground(String... strings) {
            ArrayList<AMDD> sir = UtilsPtAMDD.toateLaUnLoc(strings[0]);
            return sir;
        }

        @Override
        protected void onPostExecute(ArrayList<AMDD> amdd) {
            for (int i=0; i<amdd.size(); i++){
                Log.i("element " + i + "= ",amdd.get(i) .getDistantaApropierii()+ "");
            }

            mAdapter = new AdapterAMDD(AMDDactivity.this, amdd);
            rv.setAdapter(mAdapter);
        }
    }

    private void init() {
        nume = findViewById(R.id.textViewNumeleApasat);
        diametru = findViewById(R.id.textViewDiametruApasat);
        viteza = findViewById(R.id.textViewVitezaApasat);
    }


}
