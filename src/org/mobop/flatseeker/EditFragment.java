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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.mobop.flatseeker.model.Model;
import org.mobop.flatseeker.model.PriceRange;
import org.mobop.flatseeker.model.SearchParams;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class EditFragment extends Fragment{

    GoogleMap map;
    SupportMapFragment mSupportMapFragment;
    Model model;
    
    Button save;
    EditText cityTbx;
    EditText rangeTbx;
    EditText startRangeTbx;
    EditText endRangeTbx;
    EditText roomTbx;
    EditText sizeTbx;
    
    public EditFragment(Model model){
        this.model = model;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.edit_layout, container, false);
        
        cityTbx = (EditText)v.findViewById(R.id.editCityInput);
        rangeTbx = (EditText)v.findViewById(R.id.editRangeInput);
        startRangeTbx = (EditText)v.findViewById(R.id.editPriceStartInput);
        endRangeTbx = (EditText)v.findViewById(R.id.editPriceEndInput);
        roomTbx = (EditText)v.findViewById(R.id.editRoomInput);
        sizeTbx = (EditText)v.findViewById(R.id.editFlatSizeInput);

        save = (Button) v.findViewById(R.id.editSaveButton);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // save the new search in the global model
                model.newSearch(new SearchParams(
                        cityTbx.getText().toString(),
                        Integer.valueOf(rangeTbx.getText().toString()),
                        new PriceRange( Integer.valueOf(startRangeTbx.getText().toString()), 
                                Integer.valueOf(endRangeTbx.getText().toString())),
                        Integer.valueOf(roomTbx.getText().toString()),
                        Integer.valueOf(sizeTbx.getText().toString())));
                Toast.makeText(getActivity(),"tchin tchin", Toast.LENGTH_LONG).show();
            }
        });

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

                    Geocoder geoCoder = new Geocoder(getActivity(), Locale.getDefault());
                    try {
                        List<Address> address = geoCoder.getFromLocationName("Lausanne colline 11", 1);
                        double latitude = address.get(0).getLatitude();
                        double longitude = address.get(0).getLongitude();
                        map.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                new LatLng(latitude, longitude), 16));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    
                    // You can customize the marker image using images bundled with
                    // your app, or dynamically generated bitmaps. 
                    map.addMarker(new MarkerOptions()
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.house))
                            .anchor(0.0f, 1.0f) // Anchors the marker on the bottom left
                            .position(new LatLng(41.889, -87.622)));

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

            };
        }
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.map2, mSupportMapFragment);
        fragmentTransaction.commit();

        return v;
    }
}


