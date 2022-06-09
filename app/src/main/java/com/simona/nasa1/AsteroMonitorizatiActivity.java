package com.simona.nasa1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class AsteroMonitorizatiActivity extends AppCompatActivity implements InterfataMonitorizati {

    TextView tvInformare;
    RecyclerView rv;
    AdapterRecyclerAsteroMonitorizati mAdapter;
    ArrayList<AsteroMonitorizat> sirAsteroMonitoriz;
    ArrayList<String> urmatoareleDate;
    // ArrayList<AsteroMonitorizat> sirCuUrmatoareaDataDeApropiere;
    // ArrayList<AsteroMonitorizat> sirulFiltrat;
    EditText cautaAsterDupaNume;
    Button filtreaza, cancelAllFiltersBtn;
    Spinner diamSpinner, riscSpinner; //, dataSpinner;

    public static final String NUME_ASTRE_MONIT = "nasm";
    public static final String DIAMETRUL = "diam";
    public static final String VITEZA = "vit";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_astero_monitorizati);

        cautaAsterDupaNume = findViewById(R.id.editTextNume);
        sirAsteroMonitoriz = new ArrayList<>();
        urmatoareleDate = new ArrayList<>();
        // sirCuUrmatoareaDataDeApropiere = new ArrayList<>();
        //  sirulFiltrat = new ArrayList<>();
        rv = findViewById(R.id.listaAsteroMonitorizati);
        rv.setLayoutManager(new LinearLayoutManager(this));

        ClasaAsyncPtMonitorizati clasy = new ClasaAsyncPtMonitorizati();
        clasy.execute(linkPtTotiMonitorizatii());


        cautaAsterDupaNume.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence textulIntrodus, int i, int i1, int i2) {
                mAdapter.getFilter().filter(textulIntrodus);
                // le selectez la zero, pt ca, daca fac selectie dupa spinnere, apoi incep sa scriu
                // un text in editText, face automat o cautare in tot sirulFull si anuleaza cautarea din spinnere
                diamSpinner.setSelection(0);
                riscSpinner.setSelection(0);
            }

            @Override
            public void afterTextChanged(Editable textulIntrodus) {


            }
        });

        init();
        actiuneButon();


    }


    @Override
    public void clickPeUnMonitorizat(int pozi) {
        int pozitiaPeCareAmDatClick = pozi;
        Toast.makeText(this, "dat click pe " + sirAsteroMonitoriz.get(pozi).getNume(), Toast.LENGTH_SHORT).show();

        Intent i = new Intent(AsteroMonitorizatiActivity.this, AMDDactivity.class);
        AsteroMonitorizat asteroidPeCareAmApasat = sirAsteroMonitoriz.get(pozi);
        i.putExtra(NUME_ASTRE_MONIT, asteroidPeCareAmApasat.getNume());
        i.putExtra(DIAMETRUL, asteroidPeCareAmApasat.getDiametru());
        i.putExtra(VITEZA, asteroidPeCareAmApasat.getViteza());
        startActivity(i);

    }


    public class ClasaAsyncPtMonitorizati extends AsyncTask<String, Void, ArrayList<AsteroMonitorizat>> {

        @Override
        protected ArrayList<AsteroMonitorizat> doInBackground(String... strings) {
            ArrayList<AsteroMonitorizat> sirDeAsterObtinut = UtilsPtAsteroMonitorizati.toateLaUnLoc(strings[0]);


            return sirDeAsterObtinut;
        }

        @Override
        protected void onPostExecute(ArrayList<AsteroMonitorizat> asteroMonitorizats) {
//            for (AsteroMonitorizat amCurent: asteroMonitorizats){
//                //  String numeleCurent = amCurent.getNume();
//                String linkPtUrmDateDeApropiere = linkPtObtinereaUrmatoarelorDateDeApropiere(amCurent);
//                Log.i("LINK=", linkPtUrmDateDeApropiere);
//                ClasaAsyncPtObtinereaUrmatoarelorDate clasaAsyncPtObtinereaUrmatoarelorDate = new ClasaAsyncPtObtinereaUrmatoarelorDate();
//                clasaAsyncPtObtinereaUrmatoarelorDate.execute(linkPtUrmDateDeApropiere);
//                amCurent.setUrmatoareaObs(urmatoareleDate);
//                Log.i("LINK size=", urmatoareleDate.size()+"");
//                urmatoareleDate.clear();
//            }
            sirAsteroMonitoriz = asteroMonitorizats;
            refreshAdapter();
            //   selectieSpinnere();
            // Proiectul NASA1 – am un recyclerview care se alimenteaza dintr-un
            // arrayList de obiect obtinut din parsarea unui JSON. Am si un spinner,
            // ca sa pot cere sa afiseze doar anumite obiecte din arrayList-ul meu.
            // Cand selectez ceva dinspinner, apeleaza metoda getFilter.filter(“…”)
            // din Adaptorul recycleru-ului.
            //        Metoda prin care selectez ceva din spinner, trebuie pusa in
            //        clasaAsync din activitatea unde este definit recyclerview-ul,
            //        in metoda onPostExecute. Daca nu fac asta, se va incerca de la bun
            //        inceput sa apeleze metoda getFilter din selectia implicita a spinnerului,
            //        dar in acel moment arrayList-ul inca nu e populat (merge async) si
            //        o sa-midea “null pointer exception”.

        }
    }


    // clasa asta am luat-o din AMDDactivity,
    // acolo se cheama  ClasaAsyncPtDetaliiDate
//    public class ClasaAsyncPtObtinereaUrmatoarelorDate extends AsyncTask<String, Void, ArrayList<String>> {
//
//        @Override
//        protected ArrayList<String> doInBackground(String... strings) {
//            ArrayList<String> dateleUrmatoare = UtilsPtUrmatoareleDateDeApropiere.toateLaUnLoc(strings[0]);
//            return dateleUrmatoare;
//        }
//
//        @Override
//        protected void onPostExecute(ArrayList<String> urmDateDeAprop) {
//            urmatoareleDate.clear();
//            urmatoareleDate.addAll(urmDateDeAprop);
//        }
//    }


    private void refreshAdapter() {
        mAdapter = new AdapterRecyclerAsteroMonitorizati(AsteroMonitorizatiActivity.this, sirAsteroMonitoriz, this);
        rv.setAdapter(mAdapter);
    }

    private String linkPtTotiMonitorizatii() {
        String x = "https://ssd-api.jpl.nasa.gov/sentry.api";
        return x;
    }


    // metoda asta am luat-o din AMDDactivity,
    // acolo se cheama  private String linkPtDetaliiDATEasteroid() {
    private String linkPtObtinereaUrmatoarelorDateDeApropiere(AsteroMonitorizat amCurent) {
        // linkul arata asa:
        // https://ssd-api.jpl.nasa.gov/sentry.api?des=2000%20SG344 (2000 SG344)
        // adica se formeaza din primul cuvant, apoi %20, apoi ultimul cuvant
        // si le extrag din numePrimit
        String primul = amCurent.getNume().substring(amCurent.getNume().indexOf("(") + 1, amCurent.getNume().indexOf("(") + 5);
        String doilea = amCurent.getNume().substring(amCurent.getNume().lastIndexOf(" ") + 1, amCurent.getNume().indexOf(")"));
        String link = "https://ssd-api.jpl.nasa.gov/sentry.api?des="
                + primul
                + "%20"
                + doilea;

        // astea au fost pentru test, ca sa vad ca merge
        //  String link = "https://ssd-api.jpl.nasa.gov/sentry.api?des=2000%20SG344";
        // link = "https://ssd-api.jpl.nasa.gov/sentry.api?des=2018%20VP1";
        return link;
    }


    private void actiuneButon() {

        filtreaza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String diamAles = diamSpinner.getSelectedItem().toString();
                String probabAles = riscSpinner.getSelectedItem().toString();
                // String anulAles = dataSpinner.getSelectedItem().toString();
                mAdapter.selectieDin3Spinnere(diamAles, probabAles); //, anulAles);
                mAdapter.notifyDataSetChanged();
                if (mAdapter.getItemCount() == 0){
                    tvInformare.setVisibility(View.VISIBLE);
                    tvInformare.setText("Nici un asteroid nu indeplineste criteriile de cautare. Va rog alegeti alt diametru si/sau alta probabilitate");
                }
                else {
                    tvInformare.setVisibility(View.GONE);
                }
            }
        });

        cancelAllFiltersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cautaAsterDupaNume.setText(null);
                // butonul functioneaza doar datorita acestei linii,
                // pt ca in onCreate exista cautaAsterDupaNume.addtextChangeListener
                // care verifica in permanenta daca e scris ceva sau nu
                // in acel editText
                // daca nu as avea editText, as putea pune linia asta de cod
                // ca sa imi afiseze din nou sirul complet:
                //  mAdapter.selectieDin3Spinnere(diamSpinner.getSelectedItem().toString(), riscSpinner.getSelectedItem().toString());

                diamSpinner.setSelection(0);
                riscSpinner.setSelection(0);
                 // liniile astea le-am pus, pt ca, din moment ce in editText va fi null
                // iar sirul afisat va fi sirulFull
                // am zis sa fie si selectiile spinnerelor aduse la zero
                // ca sa nu fie confuzii pt utilizator


                //   dataSpinner.setSelection(0);
            }
        });
    }

    private void init() {
        tvInformare = findViewById(R.id.textView18);
        filtreaza = findViewById(R.id.button5);
        cancelAllFiltersBtn = findViewById(R.id.button6);
        diamSpinner = findViewById(R.id.spinnerDiametru);
        riscSpinner = findViewById(R.id.spinnerMaxHazard);
        // dataSpinner = findViewById(R.id.spinnerCeaMaiApropiataData);
    }

}
