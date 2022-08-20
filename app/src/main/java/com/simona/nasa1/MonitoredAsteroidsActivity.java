package com.simona.nasa1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.simona.nasa1.monitoredAsteroids.MonitoredAsteroidInterface;
import com.simona.nasa1.monitoredAsteroids.MonitoredAsteroid;

import java.util.ArrayList;

public class MonitoredAsteroidsActivity extends AppCompatActivity implements MonitoredAsteroidInterface, View.OnClickListener {

    TextView info1TV, info2TV, progressBarInfoTV;
    RecyclerView rv;
    MonitoredAsteroidsADAPTER mAdapter;
    ArrayList<MonitoredAsteroid> arrayMonitoredAsteroids;
    ArrayList<String> arrayNextDates;

    EditText searchAsteroidByName;
    Button filterBtn, cancelAllFiltersBtn;
    Spinner diamSpinner, riscSpinner;
    ProgressBar progressBar;

    public static final String NAME_MONITORIZED_ASTEROID = "nasm";
    public static final String DIAMETER = "diam";
    public static final String SPEED = "vit";

    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asteroids_monitored);

        initViews();
        loadMonitoredAsteroidsList();
        searchAsteroidByName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence textulIntrodus, int i, int i1, int i2) {
                mAdapter.getFilter().filter(textulIntrodus);
                diamSpinner.setSelection(0);
                riscSpinner.setSelection(0);
            }
            @Override
            public void afterTextChanged(Editable textulIntrodus) {

            }
        });

    }


    @Override
    public void getDetailsForOneMonitoredAsteroid(int pozi) {
//        Toast.makeText(this, "click on " + arrayMonitoredAsteroids.get(pozi).getName(), Toast.LENGTH_SHORT).show();
        Intent i = new Intent(MonitoredAsteroidsActivity.this, AMDDactivity.class);
        MonitoredAsteroid asteroidPeCareAmApasat = arrayMonitoredAsteroids.get(pozi);
        i.putExtra(NAME_MONITORIZED_ASTEROID, asteroidPeCareAmApasat.getName());
        i.putExtra(DIAMETER, asteroidPeCareAmApasat.getDiameter());
        i.putExtra(SPEED, asteroidPeCareAmApasat.getSpeed());
        startActivity(i);

    }

    @Override
    public void onClick(View view) {
        int clickedID = view.getId();
        hideKeyboard();
        switch (clickedID){
            case R.id.filterBtn:
                String selectedDiameter = diamSpinner.getSelectedItem().toString();
                String selectedProbability = riscSpinner.getSelectedItem().toString();
                if (selectedDiameter.equals("diametru(m)") && selectedProbability.equals("sanseLovire(%)")) {
                    Toast.makeText(MonitoredAsteroidsActivity.this, "Alege un diametru sau o probabilitate! ", Toast.LENGTH_SHORT).show();
                    return;
                }
                mAdapter.filterTheArrayBasedOnSelectedOptionFromSpinners(selectedDiameter, selectedProbability);
                mAdapter.notifyDataSetChanged();
                if (mAdapter.getItemCount() == 0) {
                    info1TV.setVisibility(View.VISIBLE);
                    info1TV.setText("Nici un asteroid nu indeplineste criteriile de cautare. Va rog alegeti alt diametru si/sau alta probabilitate");
                } else {
                    info1TV.setVisibility(View.GONE);
                }
                break;

            case R.id.cancelFiltersBtn:
                searchAsteroidByName.setText(null);

                // display the FULLarray & reset both spinners to default
                diamSpinner.setSelection(0);
                riscSpinner.setSelection(0);
                break;
        }
    }

    void loadMonitoredAsteroidsList(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                arrayMonitoredAsteroids = UtilsGeneral.getMonitoredAsteroids(urlForMonitoredAsteroids());

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        hideKeyboard();
                        refreshAdapter();
                        searchAsteroidByName.setVisibility(View.VISIBLE);
                        searchAsteroidByName.setEnabled(true);
                        info1TV.setVisibility(View.VISIBLE);
                        info2TV.setVisibility(View.VISIBLE);
                        diamSpinner.setVisibility(View.VISIBLE);
                        riscSpinner.setVisibility(View.VISIBLE);
                        filterBtn.setEnabled(true);
                        cancelAllFiltersBtn.setEnabled(true);
                        progressBarInfoTV.setVisibility(View.GONE);
                        progressBar.setVisibility(View.GONE);
                    }
                });
            }
        });
        thread.start();
    }

    private void refreshAdapter() {
        mAdapter = new MonitoredAsteroidsADAPTER(MonitoredAsteroidsActivity.this, arrayMonitoredAsteroids, this);
        rv.setAdapter(mAdapter);
    }

    private String urlForMonitoredAsteroids() {
        String x = "https://ssd-api.jpl.nasa.gov/sentry.api";
        return x;
    }

    void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(searchAsteroidByName.getWindowToken(), 0);
    }

    private void initViews() {
        handler = new Handler(Looper.getMainLooper());

        searchAsteroidByName = findViewById(R.id.inputNameET);
        arrayMonitoredAsteroids = new ArrayList<>();
        arrayNextDates = new ArrayList<>();

        rv = findViewById(R.id.monitoredAsteroidsRV);
        rv.setLayoutManager(new LinearLayoutManager(this));

        info1TV = findViewById(R.id.info5TV);
        info2TV = findViewById(R.id.info4TV);
        progressBarInfoTV = findViewById(R.id.progressBarInfoTV);
        filterBtn = findViewById(R.id.filterBtn);
        cancelAllFiltersBtn = findViewById(R.id.cancelFiltersBtn);
        progressBar = findViewById(R.id.progressBar);

        filterBtn.setOnClickListener(this::onClick);
        cancelAllFiltersBtn.setOnClickListener(this::onClick);
        diamSpinner = findViewById(R.id.spinnerDiameter);
        riscSpinner = findViewById(R.id.spinnerMaxHazard);

    }

}
