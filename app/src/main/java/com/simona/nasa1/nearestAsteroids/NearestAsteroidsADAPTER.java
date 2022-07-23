package com.simona.nasa1.nearestAsteroids;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.simona.nasa1.R;

import java.util.ArrayList;

public class NearestAsteroidsADAPTER extends RecyclerView.Adapter<NearestAsteroidsADAPTER.ClassViewHolderCloseness> {

    Context context;
    ArrayList<NearestAsteroid> arrayAsteroids;

    public NearestAsteroidsADAPTER(Context context, ArrayList<NearestAsteroid> arrayAsteroids) {
        this.context = context;
        this.arrayAsteroids = arrayAsteroids;
    }

    public class ClassViewHolderCloseness extends RecyclerView.ViewHolder{
        TextView nameTV, dateTV, speedTV, distanceTV;

        public ClassViewHolderCloseness(@NonNull View itemView) {
            super(itemView);
            nameTV = itemView.findViewById(R.id.textView12);
            dateTV = itemView.findViewById(R.id.textView13);
            speedTV = itemView.findViewById(R.id.textView14);
            distanceTV = itemView.findViewById(R.id.textView15);
        }
    }

    @NonNull
    @Override
    public ClassViewHolderCloseness onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ClassViewHolderCloseness(LayoutInflater.from(context).inflate(R.layout.row_aster_close, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ClassViewHolderCloseness holder, int position) {
        NearestAsteroid apa = arrayAsteroids.get(position);

        holder.nameTV.setText(apa.getName());
        holder.dateTV.setText("Data apropierii de Pamant: " + apa.getDateOfApproach());

        String dist = String.valueOf(apa.getDistance());
        String dist2= "";
        for (int i=0; i<dist.length(); i++){
            if ((dist.length()-i-1)%3 == 0){
                dist2+= dist.charAt(i) + ",";
            }
            else {
                dist2 += Character.toString(dist.charAt(i));
            }
        }
        dist2 = dist2.substring(0, dist2.length()-1);

        holder.distanceTV.setText("Distanta fata de Pamant: " + dist2 + " km");

        String speed1 = String.valueOf(apa.getSpeed());
        String speed2= "";
        for (int i=0; i<speed1.length(); i++){
            if ((speed1.length()-i-1)%3 == 0){
                speed2+= speed1.charAt(i) + ",";
            }
            else {
                speed2 += Character.toString(speed1.charAt(i));
            }
        }

        speed2 = speed2.substring(0, speed2.length()-1);
        holder.speedTV.setText("Viteza: " + speed2 + " km/h");
    }

    @Override
    public int getItemCount() {
        return arrayAsteroids.size();
    }


}
