package com.simona.nasa1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdaptorRecylerPozePamant extends RecyclerView.Adapter<AdaptorRecylerPozePamant.ClasaViewHolder> {

    Context context;
    ArrayList<PozaPamant> sirPoze;

    public AdaptorRecylerPozePamant(Context context, ArrayList<PozaPamant> sirPoze) {
        this.context = context;
        this.sirPoze = sirPoze;
    }

    public class ClasaViewHolder extends RecyclerView.ViewHolder{

        ImageView pozaPamant;
        TextView data, latiLongi, captura;

        public ClasaViewHolder(@NonNull View itemView) {
            super(itemView);
            pozaPamant = itemView.findViewById(R.id.imageView3);
            data = itemView.findViewById(R.id.textViewData);
            latiLongi = itemView.findViewById(R.id.textViewlatiLongi);
            captura = itemView.findViewById(R.id.textViewCaption);
        }
    }


    @NonNull
    @Override
    public ClasaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ClasaViewHolder(LayoutInflater.from(context).inflate(R.layout.rand_pamant, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ClasaViewHolder holder, int position) {
        PozaPamant pp = sirPoze.get(position);
        holder.data.setText("Data pozei: " + pp.getData());
        holder.latiLongi.setText("Latitudine=" + pp.getLati() + "; Longi=" + pp.getLongi());
        holder.captura.setText(pp.getCaption());
        Picasso.with(context).load(pp.getNumePoza()).into(holder.pozaPamant);
    }

    @Override
    public int getItemCount() {
        return sirPoze.size();
    }


}
