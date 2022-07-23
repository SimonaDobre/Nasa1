package com.simona.nasa1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.simona.nasa1.monitoredAsteroids.MonitoredAsteroidInterface;
import com.simona.nasa1.monitoredAsteroids.MonitoredAsteroid;

import java.util.ArrayList;

public class MonitoredAsteroidsADAPTER extends RecyclerView.Adapter<MonitoredAsteroidsADAPTER.ClassViewHolderMonitorizedAsteroids> implements Filterable {

    Context context;
    ArrayList<MonitoredAsteroid> monitoredAsteroidsList;
    ArrayList<MonitoredAsteroid> monitoredAsterFULL;
    MonitoredAsteroidInterface interfMonit;

    public MonitoredAsteroidsADAPTER(Context context, ArrayList<MonitoredAsteroid> monitoredAsteroidsList, MonitoredAsteroidInterface interfMonit) {
        this.context = context;
        this.monitoredAsteroidsList = monitoredAsteroidsList;
        this.interfMonit = interfMonit;
        monitoredAsterFULL = new ArrayList<>(monitoredAsteroidsList);
    }


    public class ClassViewHolderMonitorizedAsteroids extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView nameTV, speedTV, diameterTV,  lastTV, nextTV, hitPercentTV, missPercentTV, aChanceOutOfHowManyTV;
        MonitoredAsteroidInterface im;

        public ClassViewHolderMonitorizedAsteroids(@NonNull View itemView, MonitoredAsteroidInterface im) {
            super(itemView);
            nameTV = itemView.findViewById(R.id.name3TV);
            speedTV = itemView.findViewById(R.id.speedTV);
            diameterTV = itemView.findViewById(R.id.diameter1TV);
            lastTV = itemView.findViewById(R.id.lastObservationTV);
            nextTV = itemView.findViewById(R.id.nextDateTV);
            hitPercentTV = itemView.findViewById(R.id.chancesToHitPercentTV);
            missPercentTV = itemView.findViewById(R.id.chancesToMisspercentTV);
            aChanceOutOfHowManyTV = itemView.findViewById(R.id.oneChanceOfTV);
            this.im = im;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            im.getDetailsForOneMonitoredAsteroid(getAdapterPosition());
        }
    }

    @NonNull
    @Override
    public ClassViewHolderMonitorizedAsteroids onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ClassViewHolderMonitorizedAsteroids(LayoutInflater.from(context).inflate(R.layout.row_aster_monitored,
                parent, false), interfMonit);
    }

    @Override
    public void onBindViewHolder(@NonNull ClassViewHolderMonitorizedAsteroids holder, int position) {
        // use try/catch!
        // if the spinner selected options are too "narrow"
        // and there will be no MonitoredAsteroid to meet the selected criteria
        // then, the filteredArray from the method getSelectedOptionFromSpinners() will be null,
        // so, the method onBindViewHolder will throw a NullPointerException
        // when trying to get the current element of the array
        try {
            MonitoredAsteroid am = monitoredAsteroidsList.get(position);
            holder.nameTV.setText(am.getName());
            int diametr = am.getDiameter();
            holder.diameterTV.setText("Diametru: " + diametr + "m");

            int v1 = am.getSpeed();
            String speed1 = String.valueOf(v1);
            String speed2 = "";
            for (int i=0; i<speed1.length(); i++){
                if ((speed1.length()-i-1)%3 == 0){
                    speed2+= Character.toString(speed1.charAt(i)) + ",";
                }
                else {
                    speed2 += Character.toString(speed1.charAt(i));
                }
            }
            speed2 = speed2.substring(0, speed2.length()-1);

            holder.speedTV.setText("Viteza la intrarea in atmosfera: " + speed2 + " km/h.");
            holder.hitPercentTV.setText("Sanse sa ne loveasca: " + (am.getHitProbabilityPercent()) + "%.");
            holder.missPercentTV.setText("Sanse sa ne rateze: " + (am.getMissProbabilityPercent()) + "%.");
            int oneChanceOfHowMany = (int) (am.getaChanceOutOfHowMany());
            String hitChances1 = String.valueOf(oneChanceOfHowMany);
            String hitChances2 = "";
            for (int i = 0; i < hitChances1.length(); i++) {
                if ((hitChances1.length() - i - 1) % 3 == 0) {
                    hitChances2 += Character.toString(hitChances1.charAt(i)) + ",";
                } else {
                    hitChances2 += Character.toString(hitChances1.charAt(i));
                }
            }
            hitChances2 = hitChances2.substring(0, hitChances2.length() - 1);
            holder.aChanceOutOfHowManyTV.setText("Exista o sansa din " + hitChances2 + " sa ne loveasca.");
            holder.lastTV.setText("Ultima data cand a fost observat: " + am.getLastObservationDate());
            holder.nextTV.setText("\n Click aici pt a vedea urmatoarele date cand se va apropia de Pamant.");
          //  holder.next.setText(am.getUrmatoareaObs().get(0) + "; \n Click aici pt a vedea urmatoarele date cand se va apropia de Pamant");
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return monitoredAsteroidsList.size();
    }


    @Override
    public Filter getFilter() {
        return resultAfterFilter;
    }

    Filter resultAfterFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence searchedText) {
            // performs search by the searchedText got from inputEditText
            ArrayList<MonitoredAsteroid> filteredArray = new ArrayList<>();
            if (searchedText == null || searchedText.length() == 0) {
                filteredArray.addAll(monitoredAsterFULL);
            } else {
                String searchedString = searchedText.toString().toLowerCase().trim();
                for (MonitoredAsteroid am : monitoredAsterFULL) {
                    if (am.getName().toLowerCase().contains(searchedString)) {
                        filteredArray.add(am);
                    }
                }
            }
            FilterResults fr = new FilterResults();
            fr.values = filteredArray;
            return fr;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults rezultFiltrare) {
            monitoredAsteroidsList.clear();
            monitoredAsteroidsList.addAll((ArrayList<MonitoredAsteroid>) rezultFiltrare.values);
            notifyDataSetChanged();
        }
    };

    // performs the array's filter, based on the selected options of the spinners
    protected void filterTheArrayBasedOnSelectedOptionFromSpinners(String diam, String probability){
        ArrayList<MonitoredAsteroid> filteredArrayBySpinnersOptions = new ArrayList<>();
        int diamInf = 0, diamSup = 100000;
        double chancesInf = 0, sanseSup = 100;

        if (!(diam.equals("diametru(m)"))) {
            if (diam.equals("<3")) {
                diamSup = 3;
            } else if (diam.equals(">501")) {
                diamInf = 501;
            } else  {
                diamInf = Integer.parseInt(diam.substring(0, diam.indexOf("-")));
                diamSup = Integer.parseInt(diam.substring(diam.indexOf("-") + 1));
            }
        }

        if (!(probability.equals("sanseLovire(%)"))) {
            if (probability.equals("<0.1")) {
                sanseSup = 0.1;
            } else if (probability.equals(">0.5")) {
                chancesInf = 0.5;
            } else  {
                chancesInf = Double.parseDouble(probability.substring(0, probability.indexOf("-")));
                sanseSup = Double.parseDouble(probability.substring(probability.indexOf("-") + 1));
            }
        }

        for (MonitoredAsteroid am: monitoredAsterFULL){
            if (am.getDiameter() >= diamInf && am.getDiameter() <= diamSup
            && am.getHitProbabilityPercent() >= chancesInf && am.getHitProbabilityPercent() <= sanseSup){
                filteredArrayBySpinnersOptions.add(am);
            }
        }
        monitoredAsteroidsList.clear();
        monitoredAsteroidsList.addAll(filteredArrayBySpinnersOptions);
        notifyDataSetChanged();
    }


}
