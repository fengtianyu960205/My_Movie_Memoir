package com.example.my_movie_memoir.UI.Maps;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.example.my_movie_memoir.MainActivity;
import com.example.my_movie_memoir.R;
import com.example.my_movie_memoir.sp.SharedPreferenceUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private MapsViewModel mapsViewModel;
    private GetPersonAddressViewModel personAddressViewModel;
    private GetCinemaAddressViewModel getCinemaAddressViewModel;
    private Context context;
    private Activity activity;
    private String personAddress;
    private ArrayList<String> addresses = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        context = getApplicationContext();
        activity = (MainActivity)getParent();
        SharedPreferenceUtil sp = SharedPreferenceUtil.getInstance(context);
        int perId = sp.getInt("perId");
        personAddressViewModel = new ViewModelProvider(this).get(GetPersonAddressViewModel.class);
        mapsViewModel = new ViewModelProvider(this).get(MapsViewModel.class);
        getCinemaAddressViewModel = new ViewModelProvider(this).get(GetCinemaAddressViewModel.class);


        personAddressViewModel.getPersonAddressTask(perId);

        personAddressViewModel.getAddress().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String address) {
                addresses.add(address);

            }
        });

        getCinemaAddressViewModel.getCinemaAddressTask();

        getCinemaAddressViewModel.getAddress().observe(this, new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(ArrayList<String> strings) {
                for (String temp : strings){
                    addresses.add(temp);
                }
                // put all the address to maps view model
                mapsViewModel.getAddCinemaProcessing(addresses);
            }
        });

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.animateCamera(CameraUpdateFactory.zoomTo(10));
        mapsViewModel.getLiveDataAddList().observe(this, new Observer<List<LatLng>>() {
            @Override
            public void onChanged(List<LatLng> latLngs) {
                int i = 0;
                for (LatLng l : latLngs) {
                    if (l != null)
                        if (i == 0){
                            mMap.addMarker(new MarkerOptions()
                                    .position(l)
                                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                                    .title(addresses.get(i))
                                    .snippet("Latitude: " + l.latitude + ", Longitude: " + l.longitude));
                            i++;
                        }
                        else{
                            mMap.addMarker(new MarkerOptions()
                                .position(l)
                                .title(addresses.get(i))
                                .snippet("Latitude: " + l.latitude + ", Longitude: " + l.longitude));
                            i++;
                        }
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(l));
                    Log.d("mapActivity",l.latitude + " " + l.longitude);
                }
            }
        });
    }
}
