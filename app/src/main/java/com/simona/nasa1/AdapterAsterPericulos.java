package com.simona.nasa1;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AdapterAsterPericulos extends RecyclerView.Adapter<AdapterAsterPericulos.ClasViewHolDerAsterPericulos>{

    Context context;
    ArrayList<AsterPericulosApropieri> sirApropieri;


    public AdapterAsterPericulos(Context context, ArrayList<AsterPericulosApropieri> sirApropieri) {
        this.context = context;
        this.sirApropieri = sirApropieri;
    }

    public class ClasViewHolDerAsterPericulos extends RecyclerView.ViewHolder{

        TextView data, viteza, distanta;
        public ClasViewHolDerAsterPericulos(@NonNull View itemView) {
            super(itemView);
            data = itemView.findViewById(R.id.textViewDataP);
            viteza = itemView.findViewById(R.id.textViewVitezaP);
            distanta = itemView.findViewById(R.id.textViewDistantaP);
            //nume = itemView.findViewById(R.id.textViewNume);
        }
    }



    @NonNull
    @Override
    public ClasViewHolDerAsterPericulos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ClasViewHolDerAsterPericulos(LayoutInflater.from(context).inflate(R.layout.rand_aster_periculos, parent, false));
    }


//    private String dataDeAzi(){
//        Date c = Calendar.getInstance().getTime();
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        String formattedDate = simpleDateFormat.format(c);
//        return formattedDate;
//    }


    @Override
    public void onBindViewHolder(@NonNull ClasViewHolDerAsterPericulos holder, int position) {
        AsterPericulosApropieri apa = sirApropieri.get(position);
        Log.i("PPPP APA = ", apa.getNume());
//        if (apa.getNume().equals(null)){
//            holder.nume.setVisibility(View.GONE);
//        }
//        else {
//            holder.nume.setText(apa.getNume());
//        }

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

      //  String dataCurenta = dataDeAzi();
      //  String dataApropieriiDoarData = apa.getDataApropierii().substring(0,10);

        holder.distanta.setText("Distanta fata de Pamant: " + dist2 + " km");

        // atunci cand folosesc acest adaptor pt a afisa rezultatele din
        // link-ul din activitatea AsterCeiMaiApropiatiActivity
        // nu stiu sa convertesc corect distantele obtinute de acolo
        // e ceva cu distanta lunara dau nu am inteles


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
        return sirApropieri.size();
    }



}
