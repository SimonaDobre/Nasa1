package com.simona.nasa1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class AsterCeiMaiApropiatiActivity extends AppCompatActivity {

    TextView tv;
    RecyclerView rv;
   // ArrayList<AsterPericulosApropieri> sir;
    AdapterAsterAPROAPE mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aster_cei_mai_apropiati);

      //  sir = new ArrayList<>();

        tv = findViewById(R.id.textView17);
        tv.setText("Asteroizi care vor trece foarte aproape de Pamant, in urmatoarea perioada:");
        rv = findViewById(R.id.listaApropieri);
        rv.setLayoutManager(new LinearLayoutManager(this));

        ClasAsyncPtSearch clasAsyncPtSearch = new ClasAsyncPtSearch();
        clasAsyncPtSearch.execute(linkulPtAccesare());

    }


    private String linkulPtAccesare() {
        String x = "https://ssd-api.jpl.nasa.gov/cad.api";
        return x;
    }






    public class ClasAsyncPtSearch extends AsyncTask<String, Void, ArrayList<AsterPericulosApropieri>>{

        @Override
        protected ArrayList<AsterPericulosApropieri> doInBackground(String... strings) {
            ArrayList<AsterPericulosApropieri> sirObtinut = UtilsCautaAster.toateLaUnLoc(strings[0]);
            return sirObtinut;
        }

        @Override
        protected void onPostExecute(ArrayList<AsterPericulosApropieri> asterPericulosApropieris) {
            alocareAdaptor(asterPericulosApropieris);
        }
    }

    private void alocareAdaptor(ArrayList<AsterPericulosApropieri> sirAprop){
        mAdapter = new AdapterAsterAPROAPE(this, sirAprop);
        rv.setAdapter(mAdapter);
    }


}
