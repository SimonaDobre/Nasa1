package com.simona.nasa1;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterAMDD extends RecyclerView.Adapter<AdapterAMDD.ClasaViewHolderAMDD> {

    Context context;
    ArrayList<AMDD> sirAster;

    public AdapterAMDD(Context context, ArrayList<AMDD> sirAster) {
        this.context = context;
        this.sirAster = sirAster;
    }

    public class ClasaViewHolderAMDD extends RecyclerView.ViewHolder{
        TextView dataAprop, distanta, procentLovire, procentRatare, energia, oSansaDinCate, energieExplicatii;
        public ClasaViewHolderAMDD(@NonNull View itemView) {
            super(itemView);
            dataAprop = itemView.findViewById(R.id.textViewDataApropierii);
            distanta = itemView.findViewById(R.id.textViewDistantaApropierii);
            procentLovire = itemView.findViewById(R.id.textViewProcentSanseDeLovireApasat);
            procentRatare = itemView.findViewById(R.id.textViewProcSanseRatareApasat);
            energia = itemView.findViewById(R.id.textViewEnergiaDeLovire);
            oSansaDinCate = itemView.findViewById(R.id.textViewOSansaDinCateApasat);
            energieExplicatii = itemView.findViewById(R.id.textView7);
        }
    }


    @NonNull
    @Override
    public ClasaViewHolderAMDD onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new ClasaViewHolderAMDD(LayoutInflater.from(context).inflate(R.layout.rand_amdd, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ClasaViewHolderAMDD holder, int position) {
        AMDD am = sirAster.get(position);
       // Log.i("element SIZE Adapter=", am.getDataApropierii()+"");
       // holder.dataAprop.setText(am.getDataApropierii() + " ... ");
        holder.dataAprop.setText("Data urmatoarei apropieri: " + am.getDataApropierii());

        String dist = String.valueOf(am.getDistantaApropierii());
        String distCuVirgule= "";
        for (int i=0; i<dist.length(); i++){
            if ((dist.length()-i-1)%3 == 0){
                distCuVirgule+= Character.toString(dist.charAt(i)) + ",";
            }
            else {
                distCuVirgule += Character.toString(dist.charAt(i));
            }
        }
        distCuVirgule = distCuVirgule.substring(0, distCuVirgule.length()-1);
        holder.distanta.setText("Distanta la care se apropie: " + distCuVirgule + " km");
        holder.procentLovire.setText("Probabilitatea sa ne loveasca: " + String.valueOf(am.getProcentSanseLovire()) + "%");
        holder.procentRatare.setText("Probabilitatea sa NU ne loveasca: " + String.valueOf(am.getProcentSanseRatare()) + "%");

        String energia = String.valueOf(am.getEnergiaLovirii());
        String energCuVirgule= "";
        for (int i=0; i<energia.length(); i++){
            if ((energia.length()-i-1)%3 == 0){
                energCuVirgule+= Character.toString(energia.charAt(i)) + ",";
            }
            else {
                energCuVirgule += Character.toString(energia.charAt(i));
            }
        }
        energCuVirgule =  energCuVirgule.substring(0,  energCuVirgule.length()-1);
        holder.energia.setText("Energia impactului: " +  energCuVirgule + " megaTone");



        String sanseLovire = String.valueOf(am.getoSansaDInCate());
        String sanseSirul2 = "";
        for (int i=0; i<sanseLovire.length(); i++){
            if ((sanseLovire.length()-i-1)%3 == 0){
                sanseSirul2 += Character.toString(sanseLovire.charAt(i)) + ",";
            }
            else {
                sanseSirul2 += Character.toString(sanseLovire.charAt(i));
            }
        }
        sanseSirul2 = sanseSirul2.substring(0, sanseSirul2.length()-1);
        holder.oSansaDinCate.setText("Exista o sansa din " + sanseSirul2 + " sa ne loveasca");
        holder.oSansaDinCate.setText("Exista o sansa din " + sanseSirul2 + " sa ne loveasca");

        String energiaExpl = String.valueOf(am.getEnergiaLovirii()*1000/15);
        String energExplCuVirgule= "";
        for (int i=0; i<energiaExpl.length(); i++){
            if ((energiaExpl.length()-i-1)%3 == 0){
                energExplCuVirgule+= Character.toString(energiaExpl.charAt(i)) + ",";
            }
            else {
                energExplCuVirgule += Character.toString(energiaExpl.charAt(i));
            }
        }
        energExplCuVirgule =  energExplCuVirgule.substring(0,  energExplCuVirgule.length()-1);

        holder.energieExplicatii.setText("adica de "+ energExplCuVirgule + " mai mare decat bomba de la Hiroshima");

    }

    @Override
    public int getItemCount() {
        return sirAster.size();
    }



}
