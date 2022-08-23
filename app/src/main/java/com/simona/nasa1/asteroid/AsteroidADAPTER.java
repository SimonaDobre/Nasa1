package com.simona.nasa1.asteroid;

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

import com.simona.nasa1.R;

import java.util.ArrayList;

public class AsteroidADAPTER extends RecyclerView.Adapter<AsteroidADAPTER.ClassViewHolder> implements Filterable {

    Context context;
    ArrayList<Asteroid> arrayAsteroids;
    ArrayList<Asteroid> arrayFULL;
    AsteroidsApproachingDatesInterface iad;

    public AsteroidADAPTER(Context context, ArrayList<Asteroid> arrayAsteroids, AsteroidsApproachingDatesInterface iad) {
        this.context = context;
        this.arrayAsteroids = arrayAsteroids;
        this.iad = iad;
        arrayFULL = new ArrayList<>(arrayAsteroids);
    }

    public static class ClassViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView nameTV, diamMinTV, diamMaxTV, speedTV, closestKmTV, closestDateTV, isDangerousTV, isSentryTV;
        AsteroidsApproachingDatesInterface interfaceAsteroidDetails;

        public ClassViewHolder(@NonNull View itemView, AsteroidsApproachingDatesInterface interfataAsteroidDetails) {
            super(itemView);
            nameTV = itemView.findViewById(R.id.nameTV);
            diamMinTV = itemView.findViewById(R.id.minDiameterTV);
            diamMaxTV = itemView.findViewById(R.id.maxDiameterTV);
            speedTV = itemView.findViewById(R.id.speedTV);
            closestKmTV = itemView.findViewById(R.id.closestKmTV);
            closestDateTV = itemView.findViewById(R.id.closestDataTV);
            isDangerousTV = itemView.findViewById(R.id.dangerousTV);
            isSentryTV = itemView.findViewById(R.id.sentryTV);
            this.interfaceAsteroidDetails = interfataAsteroidDetails;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            interfaceAsteroidDetails.getDetailsOfAnAsteroid(getAdapterPosition());
        }
    }


    @NonNull
    @Override
    public ClassViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ClassViewHolder(LayoutInflater.from(context).inflate(R.layout.row_asteroid, parent, false), iad);
    }

    @Override
    public void onBindViewHolder(@NonNull ClassViewHolder holder, int position) {
        Asteroid a = arrayAsteroids.get(position);
        holder.nameTV.setText("Name: " + a.getNameAsteroid() + ". ");
        holder.diamMinTV.setText("Min diameter: " + a.getDiameterMin() + "m.");
        holder.diamMaxTV.setText("Max diameter: " + a.getDiameterMax() + "m.");
        holder.speedTV.setText("Speed when enters into the atmosphere: " + a.getSpeed() + " km/h.");
        String distance = (a.getClosestApproachDistance());
        distance = distance.substring(0, distance.indexOf("."));
        String distantaDouble = "";
        for (int i = 0; i < distance.length(); i++) {
            if (((distance.length() - i - 1) % 3) == 0) {
                distantaDouble += Character.toString(distance.charAt(i)) + ",";
            } else {
                distantaDouble += Character.toString(distance.charAt(i));
            }
        }
        distantaDouble = distantaDouble.substring(0, distantaDouble.length() - 1);
        holder.closestKmTV.setText("Distance to Earth: " + distantaDouble + " km.");

        holder.closestDateTV.setText("Date when the asteroid will pass closest to the Earth : " + a.getClosestApproachDate() + ".");

        holder.isDangerousTV.setText("Is it potentially dangerous? " + a.isDangerous() + ".");
        if (a.isDangerous()) {
            holder.isDangerousTV.setTextColor(Color.RED);
        } else {
            holder.isDangerousTV.setTextColor(Color.BLACK);
        }

        holder.isSentryTV.setText("Is it monitored by Nasa? " + a.isSentryObject() + ".");
        if (a.isSentryObject()) {
            holder.isSentryTV.setTextColor(Color.RED);
        } else {
            holder.isSentryTV.setTextColor(Color.BLACK);
        }
    }

    @Override
    public int getItemCount() {
        return arrayAsteroids.size();
    }

    @Override
    public Filter getFilter() {
        return resultAfterFilter;
    }

    Filter resultAfterFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<Asteroid> filteredArray = new ArrayList<>();
            // in filteredArray, there will be added only the asteroids which
            // contains true value, in dangerous or sentryObject fields;
            // charSequence is useless here, as the filtering is performed
            // only on click "doarPericulosi", not onChange the input of and editText
            for (Asteroid a : arrayFULL) {
                if (a.isDangerous() || a.isSentryObject()) {
                    filteredArray.add(a);
                }
            }
            FilterResults fr = new FilterResults();
            fr.values = filteredArray;
            return fr;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            arrayAsteroids.clear();
            arrayAsteroids.addAll((ArrayList<Asteroid>) filterResults.values);
            notifyDataSetChanged();
        }
    };


}




