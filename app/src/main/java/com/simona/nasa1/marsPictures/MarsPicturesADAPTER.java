package com.simona.nasa1.marsPictures;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.simona.nasa1.R;
import com.simona.nasa1.earthPictures.GeneralPicture;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MarsPicturesADAPTER extends RecyclerView.Adapter<MarsPicturesADAPTER.ClasViewHolder> {

    Context context;
    ArrayList<GeneralPicture> arrayPictures;

    public MarsPicturesADAPTER(Context context, ArrayList<GeneralPicture> arrayPictures) {
        this.context = context;
        this.arrayPictures = arrayPictures;
    }

    public static class ClasViewHolder extends RecyclerView.ViewHolder{

        ImageView pictureIV;
        TextView roverTV, cameraTV, pictureIDtv;

        public ClasViewHolder(@NonNull View itemView) {
            super(itemView);
            pictureIV = itemView.findViewById(R.id.marsPictureIV);
            roverTV = itemView.findViewById(R.id.roverTV);
            cameraTV = itemView.findViewById(R.id.cameraTV);
            pictureIDtv = itemView.findViewById(R.id.pictureIDtv);
        }
    }

    @NonNull
    @Override
    public ClasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ClasViewHolder(LayoutInflater.from(context).inflate(R.layout.row_mars_pictures, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ClasViewHolder holder, int position) {
        holder.cameraTV.setText("Camera: " + arrayPictures.get(position).getCamera());
        holder.roverTV.setText("Captured by " + arrayPictures.get(position).getRover());
        holder.pictureIDtv.setText("Picture ID: " + arrayPictures.get(position).getPictureID());

        GeneralPicture pp = arrayPictures.get(position);
        String imageURL = pp.getPictureURL();
        Picasso.with(context).load(imageURL).into(holder.pictureIV);
    }

    @Override
    public int getItemCount() {
        return arrayPictures.size();
    }

}
