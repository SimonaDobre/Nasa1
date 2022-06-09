package com.simona.nasa1;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

public class AdaptorAsteroid extends RecyclerView.Adapter<AdaptorAsteroid.ClasaViewHolder> implements Filterable {

    Context context;
    ArrayList<Asteroid> sirAsteroizi;
    ArrayList<Asteroid> sirFULL;
    InterfataAsteroiziPericulosi iap;

    public AdaptorAsteroid(Context context, ArrayList<Asteroid> sirAsteroizi, InterfataAsteroiziPericulosi iap) {
        this.context = context;
        this.sirAsteroizi = sirAsteroizi;
        this.iap = iap;
        sirFULL = new ArrayList<>(sirAsteroizi);
    }


    public static class ClasaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView nume, dMin, dMax, viteza, closestKm, closestDate, isDanger, isSentry;
        InterfataAsteroiziPericulosi interfataAsteroiziPericulosi;

        public ClasaViewHolder(@NonNull View itemView, InterfataAsteroiziPericulosi interfataAsteroiziPericulosi) {
            super(itemView);
            nume = itemView.findViewById(R.id.textViewNume);
            dMin = itemView.findViewById(R.id.textViewMin);
            dMax = itemView.findViewById(R.id.textViewMax);
            viteza = itemView.findViewById(R.id.textViewViteza);
            closestKm = itemView.findViewById(R.id.textViewClosestKM);
            closestDate = itemView.findViewById(R.id.textViewClosestData);
            isDanger = itemView.findViewById(R.id.textViewDangerous);
            isSentry = itemView.findViewById(R.id.textViewSentry);
            this.interfataAsteroiziPericulosi = interfataAsteroiziPericulosi;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            interfataAsteroiziPericulosi.clickPeUnPericulos(getAdapterPosition());
        }
    }


    @NonNull
    @Override
    public ClasaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ClasaViewHolder(LayoutInflater.from(context).inflate(R.layout.rand_asteroid, parent, false), iap);
    }


    @Override
    public void onBindViewHolder(@NonNull ClasaViewHolder holder, int position) {
        Asteroid a = sirAsteroizi.get(position);
        holder.nume.setText("Nume: " + a.getName());
        holder.dMin.setText("Diametru minim: " + String.valueOf(a.getDiametruMin()) + "m");
        holder.dMax.setText("Diametru Maxim: " + String.valueOf(a.getDiametruMax()) + "m");
        holder.viteza.setText("Viteza la intrarea in atmosfera: " + a.getViteza() + " km/h");
        String distanta = (a.getDistantaMinimaLaCareSeVaApropia());
        distanta = distanta.substring(0, distanta.indexOf("."));
        String distantaCuVirgule = "";
        for (int i = 0; i < distanta.length(); i++) {
            if (((distanta.length() - i - 1) % 3) == 0) {
                distantaCuVirgule += Character.toString(distanta.charAt(i)) + ",";
            } else {
                distantaCuVirgule += Character.toString(distanta.charAt(i));
            }
        }
        distantaCuVirgule = distantaCuVirgule.substring(0, distantaCuVirgule.length() - 1);
        holder.closestKm.setText("Dist fata de Pamant: " + distantaCuVirgule + " km");

        holder.closestDate.setText("Data la care va trece la distanta minima: " + a.getClosestApproachDate());

        holder.isDanger.setText("E potential periculos: " + String.valueOf(a.isDangerous()));
        if (a.isDangerous()) {
            holder.isDanger.setTextColor(Color.RED);
        } else {
            holder.isDanger.setTextColor(Color.BLACK);
        }

        holder.isSentry.setText("E monitorizat de Nasa: " + String.valueOf(a.isSentryObject()));

        if (a.isSentryObject()) {
            holder.isSentry.setTextColor(Color.RED);
        } else {
            holder.isSentry.setTextColor(Color.BLACK);
        }

    }

    @Override
    public int getItemCount() {
        return sirAsteroizi.size();
    }


    @Override
    public Filter getFilter() {
        return rezultatFiltrare;
    }

    Filter rezultatFiltrare = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<Asteroid> sirulFiltrat = new ArrayList<>();
            // in sirul sirulFiltrat, pun doar acei asteroizi care au true
            // la ePericulos sau la eMonitorizat
            // nu ma folosesc de charSequence \
            // pt ca metoda se decanseaza la apasarea butonului "doarPericulosi"
            // nu de la un text introdus
            for (Asteroid a : sirFULL) {
                if (a.isDangerous() || a.isSentryObject()) {
                    sirulFiltrat.add(a);
                }
            }
            FilterResults fr = new FilterResults();
            fr.values = sirulFiltrat;
            return fr;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            sirAsteroizi.clear();
            sirAsteroizi.addAll((ArrayList<Asteroid>) filterResults.values);
            notifyDataSetChanged();
        }
    };


//    public Filter afiseazaPeToti() {
//        return filt;
//    }
//
//    Filter filt = new Filter() {
//        @Override
//        protected FilterResults performFiltering(CharSequence charSequence) {
//            ArrayList<Object> sirulDinNou = new ArrayList<>();
//            sirulDinNou.addAll(sirFULL);
//            FilterResults fr = new FilterResults();
//            fr.values = sirulDinNou;
//            return fr;
//        }
//
//        @Override
//        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
//            sirAsteroizi.clear();
//            sirAsteroizi.addAll((ArrayList<Asteroid>) filterResults.values);
//            notifyDataSetChanged();
//        }
//    };


}




