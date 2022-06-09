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

public class AdapterRecyclerPozeMarte extends RecyclerView.Adapter<AdapterRecyclerPozeMarte.ClasViewHolder> {

    Context context;
    ArrayList<PozaMarte> arrayPictures;

    public AdapterRecyclerPozeMarte(Context context, ArrayList<PozaMarte> arrayPictures) {
        this.context = context;
        this.arrayPictures = arrayPictures;
    }

    public static class ClasViewHolder extends RecyclerView.ViewHolder{

        ImageView pic;
        TextView rover, camera, pictureID;

        public ClasViewHolder(@NonNull View itemView) {
            super(itemView);
            pic = itemView.findViewById(R.id.imageView);
            rover = itemView.findViewById(R.id.textViewRover);
            camera = itemView.findViewById(R.id.textViewCamera);
            pictureID = itemView.findViewById(R.id.textViewPictureid);
        }
    }

    @NonNull
    @Override
    public ClasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ClasViewHolder(LayoutInflater.from(context).inflate(R.layout.un_rand, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ClasViewHolder holder, int position) {
        holder.camera.setText("Camera: " + arrayPictures.get(position).getCamera());
        holder.rover.setText("Captured by " + arrayPictures.get(position).getRover());
        holder.pictureID.setText("Picture ID: " + String.valueOf(arrayPictures.get(position).getPictureID()));

        PozaMarte pp = arrayPictures.get(position);
        // incarca poza in recycler
        String imageURL = pp.getPictureURL();
        Picasso.with(context).load(imageURL).into(holder.pic);

    }

    @Override
    public int getItemCount() {
        return arrayPictures.size();
    }


}
