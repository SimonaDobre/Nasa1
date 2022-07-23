package com.simona.nasa1.amdd;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.simona.nasa1.R;

import java.util.ArrayList;

public class AMDDadapter extends RecyclerView.Adapter<AMDDadapter.ClassViewHolderMonitoredAseroidDetails> {

    Context context;
    ArrayList<AMDD> asteroidDetailsArray;

    public AMDDadapter(Context context, ArrayList<AMDD> asteroidDetailsArray) {
        this.context = context;
        this.asteroidDetailsArray = asteroidDetailsArray;
    }

    public class ClassViewHolderMonitoredAseroidDetails extends RecyclerView.ViewHolder{
        TextView dateOfClosenessTV, distanceTV, hitPercentTV, missPercentTV, energyTV, aChanceOfHowManyTV, energyExplanationTV;
        public ClassViewHolderMonitoredAseroidDetails(@NonNull View itemView) {
            super(itemView);
            dateOfClosenessTV = itemView.findViewById(R.id.dateOfClosenessTV);
            distanceTV = itemView.findViewById(R.id.distanceTV);
            hitPercentTV = itemView.findViewById(R.id.hitPercentTV);
            missPercentTV = itemView.findViewById(R.id.missedChancesPercentTV);
            energyTV = itemView.findViewById(R.id.hitEnergyTV);
            aChanceOfHowManyTV = itemView.findViewById(R.id.oneChanceOfHowManyTV);
            energyExplanationTV = itemView.findViewById(R.id.textView7);
        }
    }


    @NonNull
    @Override
    public ClassViewHolderMonitoredAseroidDetails onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ClassViewHolderMonitoredAseroidDetails(LayoutInflater.from(context).inflate(R.layout.row_amdd, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ClassViewHolderMonitoredAseroidDetails holder, int position) {
        AMDD am = asteroidDetailsArray.get(position);
        holder.dateOfClosenessTV.setText("Data urmatoarei apropieri: " + am.getDate());

        String dist = String.valueOf(am.getDistance());
        String distComma= "";
        for (int i=0; i<dist.length(); i++){
            if ((dist.length()-i-1)%3 == 0){
                distComma+= Character.toString(dist.charAt(i)) + ",";
            }
            else {
                distComma += Character.toString(dist.charAt(i));
            }
        }
        distComma = distComma.substring(0, distComma.length()-1);
        holder.distanceTV.setText("Distanta la care se apropie: " + distComma + " km");
        holder.hitPercentTV.setText("Probabilitatea sa ne loveasca: " + String.valueOf(am.getHitChancePercent()) + "%");
        holder.missPercentTV.setText("Probabilitatea sa NU ne loveasca: " + String.valueOf(am.getMissChancePercent()) + "%");

        String energy = String.valueOf(am.getHitEnergy());
        String energyComma= "";
        for (int i=0; i<energy.length(); i++){
            if ((energy.length()-i-1)%3 == 0){
                energyComma+= Character.toString(energy.charAt(i)) + ",";
            }
            else {
                energyComma += Character.toString(energy.charAt(i));
            }
        }
        energyComma =  energyComma.substring(0,  energyComma.length()-1);
        holder.energyTV.setText("Energia impactului: " +  energyComma + " megaTone");

        String hitChances = String.valueOf(am.getaChanceOutOfHowMany());
        String hitChances2 = "";
        for (int i=0; i<hitChances.length(); i++){
            if ((hitChances.length()-i-1)%3 == 0){
                hitChances2 += Character.toString(hitChances.charAt(i)) + ",";
            }
            else {
                hitChances2 += Character.toString(hitChances.charAt(i));
            }
        }
        hitChances2 = hitChances2.substring(0, hitChances2.length()-1);
        holder.aChanceOfHowManyTV.setText("Exista o sansa din " + hitChances2 + " sa ne loveasca");

        String energiaExpl = String.valueOf(am.getHitEnergy()*1000/15);
        String energExplComma= "";
        for (int i=0; i<energiaExpl.length(); i++){
            if ((energiaExpl.length()-i-1)%3 == 0){
                energExplComma+= energiaExpl.charAt(i) + ",";
            }
            else {
                energExplComma += Character.toString(energiaExpl.charAt(i));
            }
        }
        energExplComma =  energExplComma.substring(0,  energExplComma.length()-1);
        holder.energyExplanationTV.setText("adica de "+ energExplComma + " mai mare decat bomba de la Hiroshima");

    }

    @Override
    public int getItemCount() {
        return asteroidDetailsArray.size();
    }



}
