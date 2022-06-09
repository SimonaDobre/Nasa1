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

public class AdapterAsterAPROAPE extends RecyclerView.Adapter<AdapterAsterAPROAPE.ClasaViewHolderAproape> {

    Context context;
    ArrayList<AsterPericulosApropieri> sirAster;

    public AdapterAsterAPROAPE(Context context, ArrayList<AsterPericulosApropieri> sirAster) {
        this.context = context;
        this.sirAster = sirAster;
    }

    public class ClasaViewHolderAproape extends RecyclerView.ViewHolder{
        TextView nume, data, viteza, distanta;

        public ClasaViewHolderAproape(@NonNull View itemView) {
            super(itemView);
            nume = itemView.findViewById(R.id.textView12);
            data = itemView.findViewById(R.id.textView13);
            viteza = itemView.findViewById(R.id.textView14);
            distanta = itemView.findViewById(R.id.textView15);
        }
    }



    @NonNull
    @Override
    public ClasaViewHolderAproape onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ClasaViewHolderAproape(LayoutInflater.from(context).inflate(R.layout.rand_aster_aproape, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ClasaViewHolderAproape holder, int position) {
        AsterPericulosApropieri apa = sirAster.get(position);

        holder.nume.setText(apa.getNume());
       // Log.i("PPPP APA = ", apa.getNume());
        holder.data.setText("Data apropierii de Pamant: " + apa.getDataApropierii());

        String dist = String.valueOf(apa.getDistanta());
        String dist2= "";
        for (int i=0; i<dist.length(); i++){
            if ((dist.length()-i-1)%3 == 0){
                dist2+= Character.toString(dist.charAt(i)) + ",";
            }
            else {
                dist2 += Character.toString(dist.charAt(i));
            }
        }
        dist2 = dist2.substring(0, dist2.length()-1);

        holder.distanta.setText("Distanta fata de Pamant: " + dist2 + " km");

        String vit = String.valueOf(apa.getViteza());
        String vit2= "";
        for (int i=0; i<vit.length(); i++){
            if ((vit.length()-i-1)%3 == 0){
                vit2+= Character.toString(vit.charAt(i)) + ",";
            }
            else {
                vit2 += Character.toString(vit.charAt(i));
            }
        }

        vit2 = vit2.substring(0, vit2.length()-1);
        holder.viteza.setText("Viteza: " + vit2 + " km/h");
    }

    @Override
    public int getItemCount() {
        return sirAster.size();
    }


}
