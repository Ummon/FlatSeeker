package org.mobop.flatseeker;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.SupportMapFragment;

public class MapFragment extends Fragment {

    MapView mapView;
    GoogleMap googleMap;
    
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        SupportMapFragment mSupportMapFragment = new SupportMapFragment();
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
//        fragmentTransaction.remove(mSupportMapFragment);
        fragmentTransaction.replace(R.id.map, mSupportMapFragment);
        fragmentTransaction.commit();

//        if(mSupportMapFragment!=null){
//
//            googleMap = mSupportMapFragment.getMap();
//            if(googleMap!=null){
//                googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
//                googleMap.getUiSettings().setMyLocationButtonEnabled(false);
//                googleMap.setMyLocationEnabled(false);
//            }
//        }
//        
    	return inflater.inflate(R.layout.map_layout, container, false);
    }
}
