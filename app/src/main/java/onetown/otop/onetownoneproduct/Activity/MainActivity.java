package onetown.otop.onetownoneproduct.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Location;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import onetown.otop.onetownoneproduct.Classes.CustomAutoTextViewAdapter;
import onetown.otop.onetownoneproduct.Classes.DistanceCalculator;
import onetown.otop.onetownoneproduct.Database.DBHelper;
import onetown.otop.onetownoneproduct.Classes.LocationTracker;
import onetown.otop.onetownoneproduct.Objects.LocationsData;
import onetown.otop.onetownoneproduct.R;


public class MainActivity extends AppCompatActivity implements OnMapReadyCallback,DistanceCalculator{
    Location loc;
    LatLng latLng;
    LocationTracker tracker;
    FloatingActionButton fab;
    GoogleMap gMap;
    double radiusValue;
    Marker myCurrentLocationMarker;
    DBHelper dbHelper;
    AutoCompleteTextView autoCompleteTextView;
    ArrayList<LocationsData> places= new ArrayList<LocationsData>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper= new DBHelper(this);
        tracker= new LocationTracker(this,gMap);
        if (isGooglePlayAvailable()) {
            Toast.makeText(this,"Successfully connected to Google Play Services",Toast.LENGTH_LONG).show();
            setContentView(R.layout.activity_main);
            initMap();
        }

        places=dbHelper.getAllLocationsAndDatas();

        autoCompleteTextView= (AutoCompleteTextView)findViewById(R.id.autocompleteTV_search);
        CustomAutoTextViewAdapter adapter= new CustomAutoTextViewAdapter(this,R.layout.activity_main,R.id.autocomplete_result,places);
        autoCompleteTextView.setAdapter(adapter);

        // Get Users location when clicked
        fab= (FloatingActionButton)findViewById(R.id.fab_getLocation);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loc= tracker.getUsersLocationByCriteria(gMap,radiusValue);
                latLng= new LatLng(loc.getLatitude(),loc.getLongitude());
                Log.d("MainActivity",String.valueOf(loc.getLatitude()+" "+String.valueOf(loc.getLongitude())));

                gMap.clear();
                // Zoom Camera to the current location
                myCurrentLocationMarker= gMap.addMarker(new MarkerOptions()
                                                        .position(latLng)
                                                        .title("My Current Location")
                                                        .snippet("Current location sample snippet"));
                myCurrentLocationMarker.showInfoWindow();
                gMap.addCircle(new CircleOptions()
                                .center(new LatLng(loc.getLatitude(),loc.getLongitude()))
                                .radius(50000)
                                .strokeColor(Color.GREEN)
                                .fillColor(Color.LTGRAY));
                gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,13));
                // On marker click listener
                gMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        Intent i= new Intent(MainActivity.this,PlaceDetailsActivity.class);
                        Log.d("Arraylist Values size",String.valueOf(places.size()));
                        i.putExtra("lists",places);
                        startActivity(i);
                        Log.i("Intent",i.toString());
                        Log.i("onMarkerClick","Successfull, Title: "+marker.getTitle());
                        return false;
                    }
                });

            calculateDistanceOfTwoPoint(loc);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        if (dbHelper.checkDatabaseIfEmpty()) {
            addLocations();
        }else {
            Toast.makeText(this,"Database already have values",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    // Check if Google Play Services is Available
    public boolean isGooglePlayAvailable() {
        GoogleApiAvailability api= GoogleApiAvailability.getInstance();
        int isAvailable= api.isGooglePlayServicesAvailable(this);

        if (isAvailable == ConnectionResult.SUCCESS) {
            return true;
        }else if (api.isUserResolvableError(isAvailable)){
            Dialog dialog= api.getErrorDialog(this, isAvailable, 0);
            dialog.show();
        }else {
            Toast.makeText(this,"Can't connect to Play Services",Toast.LENGTH_LONG).show();
        }
        return false;
    }

    //Initialize Map
    public void initMap() {
        GoogleMapOptions opt= new GoogleMapOptions().liteMode(true);
        MapFragment mapFragment= (MapFragment) getFragmentManager().findFragmentById(R.id.fragment_maps);
        mapFragment.getMapAsync(this);
        mapFragment.newInstance(opt);
    }

    // Interface methods
    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap= googleMap;
        gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(10.8417947,123.057734),5));
        gMap.getUiSettings().setZoomControlsEnabled(true);
    }

    // Creation of markers
    public Marker createMarkerInSearch(LatLng latLng,List<Address> addresses,int i) {
        return gMap.addMarker(new MarkerOptions()
        .position(latLng)
        .title(toString(addresses,i)));

    }

    public String toString(List<Address> list,int i) {
        return String.valueOf(list.get(i).getLocality())+" "+
                String.valueOf(list.get(i).getAdminArea())+" "+
                        String.valueOf(list.get(i).getFeatureName());
    }

    public void addLocations() {
                dbHelper.addNewLocation(new LocationsData("Davao City","Banana Chips",7.253501,125.1708687));
                dbHelper.addNewLocation(new LocationsData("Davao Del Sur","Processed Mango",6.4653752,124.2872263));
                dbHelper.addNewLocation(new LocationsData("Davao Del Norte","Banana Chips",7.4455742,125.036961));
                dbHelper.addNewLocation(new LocationsData("Davao Oriental","Coconut-based Products",7.1382151,125.1483339));
    }

    @Override
    public void calculateDistanceOfTwoPoint(Location source) {
        Location destinationLoc= new Location("");
        destinationLoc.setLatitude(7.253501);
        destinationLoc.setLongitude(125.1708687);

     /**   Marker destMarker= gMap.addMarker(new MarkerOptions()
                                        .title("Destination")
                                        .position(new LatLng(destinationLoc.getLatitude(),destinationLoc.getLongitude())));
        destMarker.showInfoWindow();

        loc.distanceTo(destinationLoc);

        if (loc.distanceTo(destinationLoc) >= 50000) {
            Toast.makeText(this,"Destination is farther than the radius of 100 meters!",Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(this,"Destination is nearer the radius of 100 meters!",Toast.LENGTH_LONG).show();
        } */
    }
}
