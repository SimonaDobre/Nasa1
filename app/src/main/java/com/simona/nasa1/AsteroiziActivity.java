package com.simona.nasa1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AsteroiziActivity extends AppCompatActivity implements InterfataAsteroiziPericulosi {

    Button doarPericulosi, toti;
    TextView tv;
    ArrayList<Asteroid> sirAsteroizi;
    ArrayList<Asteroid> sirFULL;
    RecyclerView rv;
    AdaptorAsteroid mAdapter;
    public static final String NUMELE = "n";
    public static final String DIAMTERUL = "diam";
    public static final String IDUL = "idul";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asteroizi);

        init();
        actiuneBUton();
        sirAsteroizi = new ArrayList<>();

      //  sirAsteroizi.add(new Asteroid("aaa", 3, 5, true, "mmm", "33", "89", false ));
        rv = findViewById(R.id.totiAsteroizii);
        rv.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new AdaptorAsteroid(this, sirAsteroizi, this);
        rv.setAdapter(mAdapter);

        ClasaAsyncAsteroizi clasy = new ClasaAsyncAsteroizi();
        clasy.execute(linkulDeAccesat());

    }

    private String linkulDeAccesat(){
        String x = "https://api.nasa.gov/neo/rest/v1/feed?api_key=kEfXDqiER0JSBhCQEt86TlQVC3FSkG5sF3X9y46N";
        return x;
    }

    @Override
    public void clickPeUnPericulos(int pozi) {
        Asteroid a = sirAsteroizi.get(pozi);
        Toast.makeText(this, "clicked pe " + a.getName(), Toast.LENGTH_SHORT).show();

       // Log.i("LINKUL TRIMIS=", a.getLinkulCatreEl());
        Intent i = new Intent(AsteroiziActivity.this, AsterPericulosActivity.class);

        i.putExtra(NUMELE, a.getName());
        i.putExtra(DIAMTERUL, String. valueOf(a.getDiametruMax()));
        i.putExtra(IDUL, a.getIdul());
        startActivity(i);
    }


    public class ClasaAsyncAsteroizi extends AsyncTask<String, Void, ArrayList<Asteroid>>{

        @Override
        protected ArrayList<Asteroid> doInBackground(String... strings) {
            ArrayList<Asteroid> sirAster = UtilsPtAsteroizi.toateLaUnLoc(strings[0]);
            return sirAster;
        }

        @Override
        protected void onPostExecute(ArrayList<Asteroid> asteroids) {
            sirAsteroizi = asteroids;
            // am scris egalitatea asta, ca sa am pe ce da click
            // altfel imi zice ca sirul sirAsteroizi e gol

            sirFULL = new ArrayList<>(asteroids);
            tv.setText("Toti asteroizii care vor trece pe langa Pamant, timp de 7 zile, incepand cu data de " + dataCurenta());
            dupaExecutiaClaseiAsyncPtAsteroizi(asteroids);
        }
    }

    private void dupaExecutiaClaseiAsyncPtAsteroizi(ArrayList<Asteroid> sirObtinut){
        mAdapter = new AdaptorAsteroid(AsteroiziActivity.this, sirObtinut, this);
        rv.setAdapter(mAdapter);
    }

    private String dataCurenta(){
        Date dataDeAzi = Calendar.getInstance().getTime();
        DateFormat formatulDatei = new SimpleDateFormat("yyyy-MM-dd");
        String azi = formatulDatei.format(dataDeAzi);
        return azi;
    }


    private void actiuneBUton(){
        doarPericulosi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAdapter.getFilter().filter("");
                tv.setText("Toti asteroizii cotati ca fiind periculosi, care vor trece pe langa Pamant, timp de 7 zile, incepand cu data de " + dataCurenta());
            }
        });

        toti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv.setText("Toti asteroizii care vor trece pe langa Pamant, timp de 7 zile, incepand cu data de " + dataCurenta());
                sirAsteroizi.clear();
                sirAsteroizi.addAll(sirFULL);
                // AICI NU MERGE  !!!!
                 refreshAdaptor();
              //  mAdapter.afiseazaPeToti();
            }
        });
    }

    private void refreshAdaptor(){
        mAdapter = new AdaptorAsteroid(this, sirAsteroizi, this);
        rv.setAdapter(mAdapter);
    }

    private void init(){
        tv = findViewById(R.id.textViewasteroizii);
        doarPericulosi = findViewById(R.id.buttonDoarPericulosi);
        toti = findViewById(R.id.buttonToti);
    }

}
