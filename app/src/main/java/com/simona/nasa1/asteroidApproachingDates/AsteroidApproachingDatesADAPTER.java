package com.simona.nasa1.asteroidApproachingDates;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.simona.nasa1.R;
import com.simona.nasa1.nearestAsteroids.NearestAsteroid;

import java.util.ArrayList;

public class AsteroidApproachingDatesADAPTER extends RecyclerView.Adapter<AsteroidApproachingDatesADAPTER.ClassViewHolDerDangerousAsteroid> {

    Context context;
    ArrayList<NearestAsteroid> arrayClosenessDates;

    public AsteroidApproachingDatesADAPTER(Context context, ArrayList<NearestAsteroid> arrayClosenessDates) {
        this.context = context;
        this.arrayClosenessDates = arrayClosenessDates;
    }

    public class ClassViewHolDerDangerousAsteroid extends RecyclerView.ViewHolder {

        TextView dateTV, speedTV, distanceTV; //, nameTV;

        public ClassViewHolDerDangerousAsteroid(@NonNull View itemView) {
            super(itemView);
            dateTV = itemView.findViewById(R.id.date2TV);
            speedTV = itemView.findViewById(R.id.speed2TV);
            distanceTV = itemView.findViewById(R.id.distance2TV);
            //nameTV = itemView.findViewById(R.id.textViewNume);
        }
    }


    @NonNull
    @Override
    public ClassViewHolDerDangerousAsteroid onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ClassViewHolDerDangerousAsteroid(LayoutInflater.from(context).inflate(R.layout.row_aster_approach, parent, false));
    }


    @Override
    public void onBindViewHolder(@NonNull ClassViewHolDerDangerousAsteroid holder, int position) {
        NearestAsteroid acd = arrayClosenessDates.get(position);

        holder.dateTV.setText("Data apropierii de Pamant: " + acd.getDateOfApproach());

        String dist = String.valueOf(acd.getDistance());
        String dist2 = "";
        for (int i = 0; i < dist.length(); i++) {
            if ((dist.length() - i - 1) % 3 == 0) {
                dist2 += dist.charAt(i) + ",";
            } else {
                dist2 += Character.toString(dist.charAt(i));
            }
        }
        dist2 = dist2.substring(0, dist2.length() - 1);

        holder.distanceTV.setText("Distanta fata de Pamant: " + dist2 + " km");

        // the same adapter can be used for the link-ul from NearestAsteroidsActivity
        // check conversion for lunar distance!

        String speed1 = String.valueOf(acd.getSpeed());
        String speed2 = "";
        for (int i = 0; i < speed1.length(); i++) {
            if ((speed1.length() - i - 1) % 3 == 0) {
                speed2 += Character.toString(speed1.charAt(i)) + ",";
            } else {
                speed2 += Character.toString(speed1.charAt(i));
            }
        }

        speed2 = speed2.substring(0, speed2.length() - 1);
        holder.speedTV.setText("Viteza: " + speed2 + " km/h");
    }

    @Override
    public int getItemCount() {
        return arrayClosenessDates.size();
    }


}
