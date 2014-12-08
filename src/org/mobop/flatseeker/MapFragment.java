package org.mobop.flatseeker;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import org.mobop.flatseeker.model.Model;
import org.mobop.flatseeker.model.Search;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MapFragment extends Fragment {

    static Model model;
    static ActualSearch actualSeach;

    GoogleMap map;
    SupportMapFragment mSupportMapFragment;


    // TODO change to http://stackoverflow.com/questions/10450348/do-fragments-really-need-an-empty-constructor
    public void initMapFragment(Model model, ActualSearch actualSearch){
        this.model = model;
        this.actualSeach = actualSearch;
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if(mSupportMapFragment==null) {
            mSupportMapFragment = new SupportMapFragment() {
                @Override
                public void onActivityCreated(Bundle savedInstanceState) {
                    super.onActivityCreated(savedInstanceState);
                    map = mSupportMapFragment.getMap();
                    map.setMyLocationEnabled(true);
                    map.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
                        @Override
                        public void onMapLongClick(LatLng latLng) {
                            Toast.makeText(getActivity().getApplicationContext(), "bitch", Toast.LENGTH_LONG).show();
                        }
                    });

                   refreshPosition();
                }

            };
        }

        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.map, mSupportMapFragment);
        fragmentTransaction.commit();
        
    	return inflater.inflate(R.layout.map_layout, container, false);
    }
    
    public void refreshPosition(){

        if(actualSeach.get()!=-1) {

            Geocoder geoCoder = new Geocoder(getActivity(), Locale.getDefault());
            List<Search> l = new ArrayList<Search>(model.getSearches());

            // TODO
            try {
                List<Address> address = geoCoder.getFromLocationName(l.get(actualSeach.get()).getParams().city, 10);
                double latitude = address.get(0).getLatitude();
                double longitude = address.get(0).getLongitude();
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(
                        new LatLng(latitude, longitude), 16));
            } catch (IOException e) {
                e.printStackTrace();
            }

            // You can customize the marker image using images bundled with
            // your app, or dynamically generated bitmaps. 
//                        map.addMarker(new MarkerOptions()
//                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.house))
//                                .anchor(0.0f, 1.0f) // Anchors the marker on the bottom left
//                                .position(new LatLng(41.889, -87.622)));

//                LatLng sydney = new LatLng(-33.867, 151.206);
//
//                map.setMyLocationEnabled(true);
//                map.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 13));
//
//                map.addMarker(new MarkerOptions()
//                        .title("Sydney")
//                        .snippet("The most populous city in Australia.")
//                        .position(sydney));
        }
    }
}
