package com.simona.nasa1;

import androidx.fragment.app.FragmentActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class FireballMapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, View.OnHoverListener {

    public GoogleMap mMap;
    ArrayList<LatLng> sirLocatii;
    ArrayList<Marker> sirMarkeri;
    ArrayList<Fireballs> sirFireballs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fireball_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        sirLocatii = new ArrayList<>();
        sirMarkeri = new ArrayList<>();
        sirFireballs = new ArrayList<>();

        ClasaAsyncFireball clasaAsyncFireball = new ClasaAsyncFireball();
        clasaAsyncFireball.execute(linkulDeAccesat());

      //  mMap.setOnMarkerClickListener(this);

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


    private class ClasaAsyncFireball extends AsyncTask<String, Void, ArrayList<Fireballs>> {

        @Override
        protected ArrayList<Fireballs> doInBackground(String... strings) {
            return UtilsFireball.toateOdata(strings[0]);
        }

        @Override
        protected void onPostExecute(ArrayList<Fireballs> fireballs) {
            sirFireballs = fireballs;
            Log.i("lati SIZE=", fireballs.size() + "");
            for (int i = 0; i < fireballs.size(); i++) {
                Fireballs fbCurent = sirFireballs.get(i);
                double latiObtinuta = Double.parseDouble(fireballs.get(i).getLatNr());
                Log.i("lati din fireballs=", latiObtinuta + "");
                if (fireballs.get(i).getLatNS().equals("S")) {
                    latiObtinuta = -latiObtinuta;
                }
                double longiObtinuta = Double.parseDouble(fireballs.get(i).getLongNr());
                if (fireballs.get(i).getLongEW().equals("W")) {
                    longiObtinuta = -longiObtinuta;
                }
                LatLng locatia = new LatLng(latiObtinuta, longiObtinuta);
                MarkerOptions mo = new MarkerOptions().position(locatia);
                if (Double.parseDouble(fbCurent.getEnergia()) < 0.2){
                    mo.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                }
                else if (Double.parseDouble(fbCurent.getEnergia()) < 0.5){
                    mo.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
                }
                else if (Double.parseDouble(fbCurent.getEnergia()) < 1 ){
                    mo.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
                }
                else if (Double.parseDouble(fbCurent.getEnergia()) < 3 ){
                    mo.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                }
                else {
                    mo.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET));
                }

                Marker mar = mMap.addMarker(mo);
                // in felul asta, zice ca il asociez pe Marker cu un obiect Fireballss
                //  mar.setTag(fireballs.get(i));
                // nu am testat sa vad cum merge
                sirMarkeri.add(mar);


//               mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
//                    @Override
//                    // FOLOSESTE FEREASTRA DEFAULT
//                    public View getInfoWindow(Marker marker) {
//                        return fereastraInformareFireball(marker);
//                    }
//
//                    @Override
//                    // DEFINESTE CONTINUTUL LUI INFOWINDOW
//                    public View getInfoContents(Marker marker) {
//                        return null;
//                    }
//                });

                //  mar.showInfoWindow();

                // sirLocatii.add(locatia);

                mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                    // Use default InfoWindow frame
                    @Override
                    public View getInfoWindow(Marker arg0) {
                        return null;
                    }

                    // Defines the contents of the InfoWindow
                    @Override
                    public View getInfoContents(Marker mar) {

                        // Getting view from the layout file info_window_layout
                        View v = getLayoutInflater().inflate(R.layout.fireball_infoo_window, null);

                        // Getting the position from the marker
                        //LatLng latLng = mar.getPosition();

                        // Getting reference to the TextView to set orice vreau sa setez
                        TextView data, energia, lat, lng;
                        data = v.findViewById(R.id.textView8);
                        energia = v.findViewById(R.id.textView9);
                        lat = v.findViewById(R.id.textView10);
                        lng = v.findViewById(R.id.textView11);
                        //tvLat = (TextView) v.findViewById(R.id.tv_lat);

                        int i = sirMarkeri.indexOf(mar);
                        Fireballs fb = sirFireballs.get(i);
                        data.setText("Data = " + fb.getData());
                        energia.setText("Energia= " + fb.getEnergia() + " kiloTone");
                        lat.setText("Latitudine= " + fb.getLatNr() + fb.getLatNS());
                        lng.setText("Longitudine= " + fb.getLongNr() + fb.getLongEW());
                        // Setting the latitude
                        //  tvLat.setText(duration);

                        // Returning the view containing InfoWindow contents
                        return v;
                    }
                });



            }
        }
    }


    public void testClick(){

    }

    public View fereastraInformareFireball(Marker m) {
        View v = getLayoutInflater().inflate(R.layout.fireball_infoo_window, null);
        TextView data, energia, lat, lng;
        data = v.findViewById(R.id.textView8);
        energia = v.findViewById(R.id.textView9);
        lat = v.findViewById(R.id.textView10);
        lng = v.findViewById(R.id.textView11);
        // iau indexul markerului m si extrag din sirFireballs, acel element
        // care contine datele corespondente ale markerului respectiv
        // metoda mMap.setInfoWindowAdapter ma obliga sa pun ca parametru un
        // Marker. As fi pus un Fireball
        int i = sirMarkeri.indexOf(m);
        Fireballs fb = sirFireballs.get(i);
        data.setText("Data = " + fb.getData());
        energia.setText("Energia= " + fb.getEnergia() + " kiloTone");
        lat.setText("Latitudine= " + fb.getLatNr() + fb.getLatNS());
        lng.setText("Longitudine= " + fb.getLongNr() + fb.getLongEW());
        return v;
    }


    private String linkulDeAccesat() {
        // asta returneaza doar ultimele 20 cele mai recente
        String x = "https://ssd-api.jpl.nasa.gov/fireball.api?limit=20";

        // asta returneaza toate fireballurile cazute vreodata
        String toate = "https://ssd-api.jpl.nasa.gov/fireball.api";

        return toate;
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
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));


    }


}
