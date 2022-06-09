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

public class AsterPericulosActivity extends AppCompatActivity {

    TextView numele, diamwtrul;
    String idulPrimit = "";
    RecyclerView rv;
    AdapterAsterPericulos mAdapter;
   // ArrayList<AsterPericulosApropieri> sirApropi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aster_periculos);

        init();
        Intent j = getIntent();
        String numePrimit = j.getStringExtra(AsteroiziActivity.NUMELE);
        Log.i("PPPP NUME PRIMIT = ", numePrimit);
        String diamPrimit = j.getStringExtra(AsteroiziActivity.DIAMTERUL);
         idulPrimit = j.getStringExtra(AsteroiziActivity.IDUL);
         Log.i("PPPP IDUL PRIMIT = ", idulPrimit);
         numele.setText(numePrimit);
         diamwtrul.setText("Diametrul maxim: " + diamPrimit + "m");

//        sirApropi = new ArrayList<>();
//        sirApropi.add(new AsterPericulosApropieri("qqq", "sss", "zz"));
//        sirApropi.add(new AsterPericulosApropieri("vvq", "sss", "zz"));
//        sirApropi.add(new AsterPericulosApropieri("bbbq", "sss", "zz"));

         rv = findViewById(R.id.listaDateApropiereP);
         rv.setLayoutManager(new LinearLayoutManager(this));
      //  mAdapter = new AdapterAsterPericulos(this, sirApropi);
      //  rv.setAdapter(mAdapter);

        ClasaAsyncPtPericulosi clasy = new ClasaAsyncPtPericulosi();
        clasy.execute(linkulPtAcces());

    }

    private String linkulPtAcces(){
        String ll = "https://www.neowsapp.com/rest/v1/neo/" + idulPrimit;
        return ll;
    }

    public class ClasaAsyncPtPericulosi extends AsyncTask<String, Void, ArrayList<AsterPericulosApropieri>>{

        @Override
        protected ArrayList<AsterPericulosApropieri> doInBackground(String... strings) {
            ArrayList<AsterPericulosApropieri> sirObtinutDinUtils = UtilsPtAsteroPERICULOSI.toateLaUnLoc(strings[0]);
            return sirObtinutDinUtils;
        }

        @Override
        protected void onPostExecute(ArrayList<AsterPericulosApropieri> asterPericulosi) {
            mAdapter = new AdapterAsterPericulos(AsterPericulosActivity.this, asterPericulosi);
            rv.setAdapter(mAdapter);
        }
    }


    private void init(){
        numele = findViewById(R.id.textViewNumeP);
        diamwtrul = findViewById(R.id.textViewDiametruP);
    }

}
