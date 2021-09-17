package com.example.telecomreclamation;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.telecomreclamation.databinding.ActivityMapsBinding;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final float DEFAULT_ZOOM = 15f;
    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private static final String TAG = "MapsActivity";
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1324;
    private Boolean mLocationPermissionGranted = false;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    // widgets
    private EditText mSearchText;
    private ImageView gps;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        mSearchText=findViewById(R.id.input_search);
        gps=findViewById(R.id.ic_gps);
        getLocationPermissions();

        gps.setOnClickListener(x->{
            getDeviceLocation();
        });
        Places.initialize(getApplicationContext(),"AIzaSyDKmE1oMRC0h31-kKPeOCD7BY0-FdLf5WI");

    }


    private void init(){
        mSearchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionID, KeyEvent keyEvent) {
                if (actionID== EditorInfo.IME_ACTION_SEARCH || actionID==EditorInfo.IME_ACTION_DONE || keyEvent.getAction()==keyEvent.ACTION_DOWN
                || keyEvent.getAction()==keyEvent.KEYCODE_ENTER){
                    //execute search method
                    geoLocate();
                    //Initialize place field list
                    List<Place.Field> fieldList= Arrays.asList(Place.Field.ADDRESS,
                            Place.Field.LAT_LNG,Place.Field.NAME);
                    //Create Intent
                    Intent intent=new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY,fieldList)
                            .build(MapsActivity.this);
                    //Start activity result
                    startActivityForResult(intent,100);

                }
                return false;
            }
        });
        hideSoftKeyboard();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK){
            Place place=Autocomplete.getPlaceFromIntent(data);
            mSearchText.setText(place.getAddress());


        }
    }

    private void geoLocate() {
        String searchString=mSearchText.getText().toString();
        Geocoder geocoder=new Geocoder(MapsActivity.this);
        List<Address> addresses=new ArrayList<>();
        try {
            addresses=geocoder.getFromLocationName(searchString,1);

        }catch (IOException e){
            Log.d("MapActivity",e.getMessage());
        }
        if (addresses.size()>0){
            Address address=addresses.get(0);
            Toast.makeText(getApplicationContext(), address.toString(), Toast.LENGTH_SHORT).show();
            moveCamera(new LatLng(address.getLatitude(),address.getLongitude()),DEFAULT_ZOOM,address.getAddressLine(0));
        }
    }

    private void getDeviceLocation() {
        Toast.makeText(getApplicationContext(), "getting the device location", Toast.LENGTH_SHORT).show();
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        try {
            if (mLocationPermissionGranted) {
                Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), "found location", Toast.LENGTH_SHORT).show();
                        Location currentLocation = (Location) task.getResult();
                        moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()), DEFAULT_ZOOM,"my location");

                    } else {
                        Toast.makeText(getApplicationContext(), "couldn't find location", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        } catch (SecurityException e) {
            Log.d(TAG, e.getMessage());
        }
    }

    private void moveCamera(LatLng latLng, float zoom,String title) {
        Toast.makeText(getApplicationContext(), "moving the camera to " + latLng.latitude + ";" + latLng.longitude, Toast.LENGTH_SHORT).show();
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
        if (!title.equals("my location")){
            MarkerOptions options=new MarkerOptions().position(latLng).title(title);
            mMap.addMarker(options);
        }
        hideSoftKeyboard();
    }

    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(MapsActivity.this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Toast.makeText(getApplicationContext(), "Map is ready", Toast.LENGTH_SHORT).show();
        mMap = googleMap;

        if (mLocationPermissionGranted) {
            getDeviceLocation();
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(false);
            mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
            init();
        }
    }

    private void getLocationPermissions(){
        String[] permissions={Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION};
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), FINE_LOCATION)==PackageManager.PERMISSION_GRANTED){
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(),COARSE_LOCATION)==PackageManager.PERMISSION_GRANTED){
                mLocationPermissionGranted=true;
                initMap();
            }else{
                ActivityCompat.requestPermissions(this,permissions,LOCATION_PERMISSION_REQUEST_CODE);
            }
        }else{
            ActivityCompat.requestPermissions(this,permissions,LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mLocationPermissionGranted=false;
        switch (requestCode){
            case LOCATION_PERMISSION_REQUEST_CODE:{
                if (grantResults.length >0){
                    for (int i=0;i<grantResults.length;i++){
                        mLocationPermissionGranted=false;
                        return;
                    }
                }
                mLocationPermissionGranted=true;

                initMap();
            }
        }
    }

    private void hideSoftKeyboard(){
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

}