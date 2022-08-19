package com.simona.nasa1;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.simona.nasa1.fireball.Fireball;
import com.simona.nasa1.fireball.FireballUTILS;

import java.util.ArrayList;

public class FireballMapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, View.OnHoverListener {

    public GoogleMap myMap;
    ArrayList<LatLng> locationsArray;
    ArrayList<Marker> markersArray;
    ArrayList<Fireball> fireballsArray;
    Handler handler;

    // TUNGUSKA ?
    // GOLFUL MEXIC ?

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fireball_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        initViews();
        displayFireballsLocations();

    }

    void displayFireballsLocations() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                getFireballs();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        displayHitPlaces();
                    }
                });
            }
        });
        thread.start();
    }

    void getFireballs() {
        fireballsArray = FireballUTILS.getFireballArray(urlFireballs());
    }

    void displayHitPlaces() {
        for (int i = 0; i < fireballsArray.size(); i++) {
            Fireball currentFireball = fireballsArray.get(i);
            double currentFireballLatitude = Double.parseDouble(fireballsArray.get(i).getLatNr());
//            Log.i("lati din fireballs=", currentFireballLatitude + "");
            if (fireballsArray.get(i).getLatNS().equals("S")) {
                currentFireballLatitude = -currentFireballLatitude;
            }
            double currentFireballLongitude = Double.parseDouble(fireballsArray.get(i).getLongNr());
            if (fireballsArray.get(i).getLongEW().equals("W")) {
                currentFireballLongitude = -currentFireballLongitude;
            }
            LatLng location = new LatLng(currentFireballLatitude, currentFireballLongitude);
            MarkerOptions mo = new MarkerOptions().position(location);
            if (Double.parseDouble(currentFireball.getEnergy()) < 0.2) {
                mo.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
            } else if (Double.parseDouble(currentFireball.getEnergy()) < 0.5) {
                mo.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
            } else if (Double.parseDouble(currentFireball.getEnergy()) < 1) {
                mo.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
            } else if (Double.parseDouble(currentFireball.getEnergy()) < 3) {
                mo.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
            } else {
                mo.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET));
            }

            Marker mar = myMap.addMarker(mo);
            markersArray.add(mar);

            myMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                // Use default InfoWindow frame
                @Override
                public View getInfoWindow(Marker arg0) {
                    return null;
                }

                // Defines the contents of the InfoWindow
                @Override
                public View getInfoContents(Marker mar) {

                    // Getting view from the layout file info_window_layout
                    View v = getLayoutInflater().inflate(R.layout.fireball_info_window, null);

                    // Getting the position from the marker
                    // LatLng latLng = mar.getPosition();
                    // Getting reference to the TextView to set anything
                    TextView date, energy, lat, lng;
                    date = v.findViewById(R.id.textView8);
                    energy = v.findViewById(R.id.textView9);
                    lat = v.findViewById(R.id.textView10);
                    lng = v.findViewById(R.id.textView11);

                    int i = markersArray.indexOf(mar);
                    Fireball fb = fireballsArray.get(i);
                    date.setText("Data = " + fb.getDate());
                    double hiroshimaMultiplier = Double.parseDouble(fb.getEnergy());
                    energy.setText("Energia= " + fb.getEnergy() + " kiloTone, adica  \n");
                    if (hiroshimaMultiplier < 15){
                        energy.append("de " + String.format("%.2f", 15 / hiroshimaMultiplier) + " ori mai mica decat bomba de la Hiroshima");
                    } else if (hiroshimaMultiplier == 15){
                        energy.append("egala cu bomba de la Hiroshima");
                    } else {
                        energy.append("de " +  String.format("%.2f", hiroshimaMultiplier / 15) + " ori mai mare decat bomba de la Hiroshima");
                    }

                    lat.setText("Latitudine= " + fb.getLatNr() + fb.getLatNS());
                    lng.setText("Longitudine= " + fb.getLongNr() + fb.getLongEW());

                    // Returning the view containing InfoWindow contents
                    return v;
                }
            });

        }
    }

    private void initViews() {
        handler = new Handler(Looper.getMainLooper());
        locationsArray = new ArrayList<>();
        markersArray = new ArrayList<>();
        fireballsArray = new ArrayList<>();
    }


    @Override
    public boolean onMarkerClick(Marker marker) {
        Toast.makeText(this, "click pe " + marker.getTitle(), Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public boolean onHover(View view, MotionEvent motionEvent) {
        return false;
    }


    private String urlFireballs() {
        // this link returns the most recent 20 events
        String last20 = "https://ssd-api.jpl.nasa.gov/fireball.api?limit=20";

        // this link returns all recorded events
        String allEvents = "https://ssd-api.jpl.nasa.gov/fireball.api";
        return allEvents;
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        myMap = googleMap;
        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

    }


}
