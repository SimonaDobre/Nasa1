package com.simona.nasa1;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.util.ArrayList;

public class AdapterRecyclerAsteroMonitorizati extends RecyclerView.Adapter<AdapterRecyclerAsteroMonitorizati.ClasaViewHolderAsterMonitoriz> implements Filterable {

    Context context;
    ArrayList<AsteroMonitorizat> sirAsterMonitorizati;
    ArrayList<AsteroMonitorizat> sirulFULL;
    InterfataMonitorizati interFmonit;


    public AdapterRecyclerAsteroMonitorizati(Context context, ArrayList<AsteroMonitorizat> sirAsterMonitorizati, InterfataMonitorizati interfataMonitorizati) {
        this.context = context;
        this.sirAsterMonitorizati = sirAsterMonitorizati;
        this.interFmonit = interfataMonitorizati;
        sirulFULL = new ArrayList<>(sirAsterMonitorizati);
    }


    public class ClasaViewHolderAsterMonitoriz extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView nume, viteza, diametru, maxHazard, ultima, urmatoarea, procentLovire, procentRatare, oSansaDinCate;
        InterfataMonitorizati im;

        public ClasaViewHolderAsterMonitoriz(@NonNull View itemView, InterfataMonitorizati im) {
            super(itemView);
            nume = itemView.findViewById(R.id.textViewNumele);
            viteza = itemView.findViewById(R.id.textViewViteza);
            diametru = itemView.findViewById(R.id.textViewDiametruP);
            // maxHazard = itemView.findViewById(R.id.textViewProcentSanseDeLovire);
            ultima = itemView.findViewById(R.id.textViewUltimaObservare);
            urmatoarea = itemView.findViewById(R.id.textViewUrmatoareaData);
            procentLovire = itemView.findViewById(R.id.textViewProcentSanseDeLovire);
            procentRatare = itemView.findViewById(R.id.textViewProcSanseRatare);
            oSansaDinCate = itemView.findViewById(R.id.textViewOSansaDinCate);
            this.im = im;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            im.clickPeUnMonitorizat(getAdapterPosition());
        }
    }


    @NonNull
    @Override
    public ClasaViewHolderAsterMonitoriz onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ClasaViewHolderAsterMonitoriz(LayoutInflater.from(context).inflate(R.layout.rand_astero_monitorizati, parent, false), interFmonit);
    }

    @Override
    public void onBindViewHolder(@NonNull ClasaViewHolderAsterMonitoriz holder, int position) {

        // am pus totul intr-un try/catch, pt ca, daca selectia din spinere se face
        // prea ingusta si nu se gaseste nici un AsteroidMonitorizat care
        // sa se incadreze in limitele de acolo
        // si deci sirulFiltrat din metoda
        // selectieDin3Spinnere(String diam, String probabil, String anul)
        // va avea rezultat nul
        // in metoda onBindViewHolder se poate sa zica ca nu gaseste
        // nici un rezultat in array, deci sa dea nullPointerException
        // cand incearca sa faca citirea elementului curent
        try {
            AsteroMonitorizat am = sirAsterMonitorizati.get(position);
            holder.nume.setText(am.getNume());
            int diametr = am.getDiametru();

            holder.diametru.setText("Diametru: " + diametr + "m");
//        if (diametr <= 3){
//            Log.i("DAAA adaptor= ", am.getNume() + ";" +diametr);
//        }
            int v1 = am.getViteza();
//        String vitez = "";
//        try {
//            vitez = v1.substring(0,4);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

            String vitezaString = String.valueOf(v1);
            String vCuVirgule = "";

            for (int i=0; i<vitezaString.length(); i++){
                if ((vitezaString.length()-i-1)%3 == 0){
                    vCuVirgule+= Character.toString(vitezaString.charAt(i)) + ",";
                }
                else {
                    vCuVirgule += Character.toString(vitezaString.charAt(i));
                }
            }
            vCuVirgule = vCuVirgule.substring(0, vCuVirgule.length()-1);

            holder.viteza.setText("Viteza la intrarea in atmosfera: " + vCuVirgule + " km/h");
            // holder.maxHazard.setText("Max harazd: "  + am.getMaxHazard());
            holder.procentLovire.setText("Sanse sa ne loveasca: " + String.valueOf((am.getProcentProbabilLovire())) + "%");

            holder.procentRatare.setText("Sanse sa ne rateze: " + String.valueOf(am.getProcentProbabilRATEU()) + "%");
            int unaDinCateSanseDeLovire = (int) (am.getoSansaDeLovireDinCate());
            String sanseLovire = String.valueOf(unaDinCateSanseDeLovire);
            String sanseSirul2 = "";
            for (int i = 0; i < sanseLovire.length(); i++) {
                if ((sanseLovire.length() - i - 1) % 3 == 0) {
                    sanseSirul2 += Character.toString(sanseLovire.charAt(i)) + ",";
                } else {
                    sanseSirul2 += Character.toString(sanseLovire.charAt(i));
                }
            }
            sanseSirul2 = sanseSirul2.substring(0, sanseSirul2.length() - 1);
            holder.oSansaDinCate.setText("Exista o sansa din " + sanseSirul2 + " sa ne loveasca");
            holder.ultima.setText("Ultima data cand a fost observat: " + am.getUltimaObs());
            holder.urmatoarea.setText(am.getUrmatoareaObs() + "; \n Click aici pt a vedea urmatoarele date cand se va apropia de Pamant");
          //  holder.urmatoarea.setText(am.getUrmatoareaObs().get(0) + "; \n Click aici pt a vedea urmatoarele date cand se va apropia de Pamant");
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return sirAsterMonitorizati.size();
    }


    @Override
    public Filter getFilter() {
        return rezultatFiltrare;
    }

    Filter rezultatFiltrare = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence textulCautat) {

            // FUNCTIONEAZA PT CAUTARE DUPA UN TEXT INTRODUS IN EDITTEXT:
            ArrayList<AsteroMonitorizat> sirulFiltrat = new ArrayList<>();
            if (textulCautat == null || textulCautat.length() == 0) {
                sirulFiltrat.addAll(sirulFULL);
            } else {
                String stringulCautat = textulCautat.toString().toLowerCase().trim();
                for (AsteroMonitorizat am : sirulFULL) {
                    if (am.getNume().toLowerCase().contains(stringulCautat)) {
                        sirulFiltrat.add(am);
                    }
                }
            }
            FilterResults fr = new FilterResults();
            fr.values = sirulFiltrat;
            return fr;

//            ArrayList<AsteroMonitorizat> sirSortatDupaDiametru = new ArrayList<>();
//            ArrayList<AsteroMonitorizat> sirSortatDupaDiamSiSanse = new ArrayList<>();
//            String selectia = textulCautat.toString();
//
//
//            for (AsteroMonitorizat am: sirulFULL){
//                if (am.getDiametru() >= diamInf && am.getDiametru() < diamSup){
//                    sirSortatDupaDiametru.add(am);
//                }
//            }
//            Log.i("LIMIT DIAM ", "inf=" + diamInf + "; lSup=" + diamSup + "; sirDupaDiam = " + sirSortatDupaDiametru.size());
//
//
//
//
//            if (selectia.equals("sanseLovire(%)")){
//                sirSortatDupaDiamSiSanse.addAll(sirSortatDupaDiametru);
//            }
//            else if (selectia.equals("<0.1")){
//                sanseSup = 0.1;
//            }
//            else if (selectia.equals(">0.5")){
//                sanseInf = 0.5;
//            }
//            else if (selectia.equals("0.1-0.3")){
//                sanseInf = 0.1; sanseSup = 0.3;
//            }
//            else if (selectia.equals("0.3-0.5")){
//                sanseInf = 0.3; sanseSup = 0.5;
//            }
//            for (AsteroMonitorizat am: sirSortatDupaDiametru){
//                if (am.getProcentProbabilLovire() >= sanseInf && am.getProcentProbabilLovire() < sanseSup){
//                    sirSortatDupaDiamSiSanse.add(am);
//                }
//            }
//
//            Log.i("LIMIT SANSE ", "sanseInf=" + sanseInf + "; sanseSup=" + sanseSup + "sirDupaDiamSiSanse=" + sirSortatDupaDiamSiSanse.size());
//          // Log.i("LIMIT 4 ", "inf=" + lInf + "; lSup=" + lSup);
//            FilterResults fr = new FilterResults();
//            fr.values = sirSortatDupaDiametru;
//            return fr;

        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults rezultFiltrare) {
            sirAsterMonitorizati.clear();
            sirAsterMonitorizati.addAll((ArrayList<AsteroMonitorizat>) rezultFiltrare.values);
            notifyDataSetChanged();
        }
    };


    protected void selectieDin3Spinnere(String diam, String probabil){ // , String anul) {
        ArrayList<AsteroMonitorizat> sirFiltrat = new ArrayList<>();
        int diamInf = 0, diamSup = 100000;
        double sanseInf = 0, sanseSup = 100;

        if (!(diam.equals("diametru(m)"))) {
            if (diam.equals("<3")) {
                diamSup = 3;
                // Log.i("LIMIT 1 ", "inf=" + lInf + "; lSup=" + lSup);
            } else if (diam.equals(">501")) {
                diamInf = 501;
            } else  {
                diamInf = Integer.parseInt(diam.substring(0, diam.indexOf("-")));
                diamSup = Integer.parseInt(diam.substring(diam.indexOf("-") + 1));
            }
        }

        if (!(probabil.equals("sanseLovire(%)"))) {
            if (probabil.equals("<0.1")) {
                sanseSup = 0.1;
                // Log.i("LIMIT 1 ", "inf=" + lInf + "; lSup=" + lSup);
            } else if (probabil.equals(">0.5")) {
                sanseInf = 0.5;
            } else  {
                sanseInf = Double.parseDouble(probabil.substring(0, probabil.indexOf("-")));
                sanseSup = Double.parseDouble(probabil.substring(probabil.indexOf("-") + 1));
            }
        }

        for (AsteroMonitorizat am: sirulFULL){
            if (am.getDiametru() >= diamInf && am.getDiametru() <= diamSup
            && am.getProcentProbabilLovire() >= sanseInf && am.getProcentProbabilLovire() <= sanseSup){
                sirFiltrat.add(am);
            }
        }

        sirAsterMonitorizati.clear();
        sirAsterMonitorizati.addAll(sirFiltrat);
        notifyDataSetChanged();

    }

}
