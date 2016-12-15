package onetown.otop.onetownoneproduct.Activity;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import onetown.otop.onetownoneproduct.Classes.CustomAutoTextViewAdapter;
import onetown.otop.onetownoneproduct.Classes.DistanceCalculator;
import onetown.otop.onetownoneproduct.Classes.LocationStaticDatas;
import onetown.otop.onetownoneproduct.Database.DBHelper;
import onetown.otop.onetownoneproduct.Objects.LocationsData;
import onetown.otop.onetownoneproduct.R;


public class MainActivity extends AppCompatActivity implements OnMapReadyCallback,DistanceCalculator,GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener,LocationListener{

    LatLng latLng;
    FloatingActionButton fab;
    GoogleMap gMap,placesMap;
    Marker myCurrentLocationMarker,placesMarker;
    DBHelper dbHelper;
    AutoCompleteTextView autoCompleteTextView;
    ArrayList<LocationsData> places= new ArrayList<LocationsData>();
    Location loc;
    LocationRequest locationRequest;
    GoogleApiClient client;
    Circle currentLocCircle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper= new DBHelper(this);
        if (isGooglePlayAvailable()) {
            Toast.makeText(this,"Successfully connected to Google Play Services",Toast.LENGTH_LONG).show();
            setContentView(R.layout.activity_main);
            setupGoogleApiClient();
            initMap();

        }
        places=dbHelper.getAllLocationsAndDatas();
        autoCompleteTextView= (AutoCompleteTextView)findViewById(R.id.autocompleteTV_search);
        CustomAutoTextViewAdapter adapter= new CustomAutoTextViewAdapter(this,R.layout.activity_main,R.id.autocomplete_result,places);
        autoCompleteTextView.setAdapter(adapter);
        adapter.setNotifyOnChange(true);

        // added listener for any clicked listview results
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                gMap.clear();
                Log.d("D_onItemClick","Clicked on item: "+places.get(position).toString());
                createMarker(places);
                gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(places.get(position).locationLatitude,places.get(position).locationLongitude),10));
                Log.d("Place Position ",String.valueOf("Latitude: "+places.get(position).locationLatitude+" Longitude: "+places.get(position).locationLongitude));



         /**      // On marker click listener
                gMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        Intent i= new Intent(MainActivity.this,PlaceDetailsActivity.class);
                        i.putExtra("lists",places);
                        startActivity(i);
                        Log.i("Intent",i.toString());
                        Log.i("onMarkerClick","Successfull, Title: "+marker.getTitle());
                        return false;
                    }
                }); */
            }
        });

        // Get Users location when clicked
        fab= (FloatingActionButton)findViewById(R.id.fab_getLocation);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                requestLocationUpdate();

                latLng= new LatLng(loc.getLatitude(),loc.getLongitude());
                Log.d("MainActivity",String.valueOf(loc.getLatitude()+" "+String.valueOf(loc.getLongitude())));

              //  gMap.clear();
                // Zoom Camera to the current location
            /**    currentLocCircle= gMap.addCircle(new CircleOptions()
                                .center(new LatLng(loc.getLatitude(),loc.getLongitude()))
                                .radius(1000)
                                .strokeColor(Color.GREEN)
                                .fillColor(Color.LTGRAY));
                gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,5)); */

            }
        });


    }
    @Override
    protected void onStart() {
        super.onStart();
        client.connect();
        if (dbHelper.checkDatabaseIfEmpty()) {
          new LocationStaticDatas(dbHelper);
        }else {
            Toast.makeText(this,"Database already have values",Toast.LENGTH_LONG).show();
        }

        createLocationRequest();
    }
    // Marker Creation for OTOP Places
    public boolean createMarker(ArrayList<LocationsData>datas) {
        //placesMap= gMap;
        boolean isNotEmpty= true;
        if (isNotEmpty) {
           placesMarker= placesMap.addMarker(new MarkerOptions()
                    .position(new LatLng(datas.get(0).locationLatitude,datas.get(0).locationLongitude))
                    .title(datas.get(0).locationName));
           placesMarker.showInfoWindow();
           placesMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
               @Override
               public boolean onMarkerClick(Marker marker) {
                   Log.i("Places Marker",marker.getTitle()+"\n"+places.toString());

                   Intent i= new Intent(MainActivity.this,PlaceDetailsActivity.class);
                   i.putExtra("lists",places);
                   startActivity(i);
                   Log.i("Intent",i.toString());
                   Log.i("onMarkerClick","Successfull, Title: "+marker.getTitle());
                   Log.i("Getting Item Id",String.valueOf(places.get(0).get_id()));
                   return false;
               }
           });
        }else {
            isNotEmpty=false;
        }
        return isNotEmpty;
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

    @Override
    protected void onStop() {
        super.onStop();
        client.disconnect();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopGettingLocationData();
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
        placesMap=gMap;
        gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(10.8417947,123.057734),5));
        gMap.getUiSettings().setZoomControlsEnabled(true);
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
    private void setupGoogleApiClient() {

        client = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    // Request Location Data
    private void requestLocationUpdate() {
        // Permission COdes
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION},1);
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(client,locationRequest,this);
    }

    // Getting last known Location
    private void getLastKnownLocation() {

        loc=LocationServices.FusedLocationApi.getLastLocation(client);

        if (loc != null) {
            Log.i("LOCATION: ",loc.toString());

            myCurrentLocationMarker= gMap.addMarker(new MarkerOptions()
                    .position(new LatLng(loc.getLatitude(),loc.getLongitude()))
                    .title("My Current Location"));

            currentLocCircle= gMap.addCircle(new CircleOptions()
                    .center(new LatLng(loc.getLatitude(),loc.getLongitude()))
                    .radius(1000)
                    .strokeColor(Color.GREEN)
                    .fillColor(Color.LTGRAY));
           // gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(loc.getLatitude(),loc.getLongitude()),15));

        }
        else {

            AlertDialog.Builder builder= new AlertDialog.Builder(this);
            builder.setTitle("Location Service not Active");
            builder.setMessage("Please enable location service (GPS)");
            builder.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                }
            });
            Dialog dialog=builder.create();
            dialog.setCancelable(false);
            dialog.show();
        }
    }


    private void createLocationRequest() {
        locationRequest= LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(2000);//2 Seconds per update
    }

    private void stopGettingLocationData() {
        LocationServices.FusedLocationApi.removeLocationUpdates(client,this);
    }
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.i("onConnected","Connected!");
        getLastKnownLocation();
    }

    @Override
    public void onConnectionSuspended(int i) {
        client.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        myCurrentLocationMarker.remove();
        currentLocCircle.remove();
        loc= location;
        getLastKnownLocation();
    }
}
