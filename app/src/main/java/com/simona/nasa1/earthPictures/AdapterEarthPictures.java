package com.simona.nasa1.earthPictures;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.simona.nasa1.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterEarthPictures extends RecyclerView.Adapter<AdapterEarthPictures.ClasaViewHolder> {

    Context context;
    ArrayList<GeneralPicture> arrayPictures;

    public AdapterEarthPictures(Context context, ArrayList<GeneralPicture> arrayPictures) {
        this.context = context;
        this.arrayPictures = arrayPictures;
    }

    public class ClasaViewHolder extends RecyclerView.ViewHolder{

        ImageView earthPictureIV;
        TextView dateTV, latiLongiTV, captureTV;

        public ClasaViewHolder(@NonNull View itemView) {
            super(itemView);
            earthPictureIV = itemView.findViewById(R.id.earthPictureIV);
            dateTV = itemView.findViewById(R.id.dateTV);
            latiLongiTV = itemView.findViewById(R.id.latiLongiTV);
            captureTV = itemView.findViewById(R.id.captionTV);
        }
    }


    @NonNull
    @Override
    public ClasaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ClasaViewHolder(LayoutInflater.from(context).inflate(R.layout.row_earth, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ClasaViewHolder holder, int position) {
        GeneralPicture pp = arrayPictures.get(position);
        holder.dateTV.setText("Data pozei: " + pp.getDate());
        holder.latiLongiTV.setText("Latitudine=" + pp.getLatitude() + "; Longi=" + pp.getLongitude());
        holder.captureTV.setText(pp.getCaption());
        Picasso.with(context).load(pp.getPictureURL()).into(holder.earthPictureIV);
    }

    @Override
    public int getItemCount() {
        return arrayPictures.size();
    }


}
