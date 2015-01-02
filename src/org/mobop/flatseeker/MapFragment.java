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

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import org.mobop.flatseeker.model.Flat;
import org.mobop.flatseeker.model.Model;
import org.mobop.flatseeker.model.Search;
import org.mobop.flatseeker.model.SearchParams;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MapFragment extends Fragment {

    Model model;
    ActualSearch actualSearch;

    GoogleMap map;
    SupportMapFragment mSupportMapFragment;


    public void initMapFragment(Model model, ActualSearch actualSearch) {
        this.model = model;
        this.actualSearch = actualSearch;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.model = getArguments().getParcelable(Model.class.getName());
        this.actualSearch = getArguments().getParcelable(ActualSearch.class.getName());
    }

    public static final MapFragment newInstance(Model model, ActualSearch actualSearch) {
        MapFragment f = new MapFragment();
        Bundle bdl = new Bundle(2);
        bdl.putParcelable(Model.class.getName(), model);
        bdl.putParcelable(ActualSearch.class.getName(), actualSearch);
        f.setArguments(bdl);
        return f;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mSupportMapFragment == null) {
            mSupportMapFragment = new SupportMapFragment() {
                @Override
                public void onActivityCreated(Bundle savedInstanceState) {
                    super.onActivityCreated(savedInstanceState);
                    map = mSupportMapFragment.getMap();
                    map.setMyLocationEnabled(true);
                    map.setInfoWindowAdapter(new PopupAdapter(inflater));
                    refreshPosition();
                }

            };
        }

        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.map, mSupportMapFragment);
        fragmentTransaction.commit();

        return inflater.inflate(R.layout.map_layout, container, false);
    }

    public void setModelAndActualSearch(Model model, ActualSearch actualSearch) {
        this.model = model;
        this.actualSearch = actualSearch;
    }

    public void refreshPosition() {

        if (actualSearch.get() != -1) {

            Geocoder geoCoder = new Geocoder(getActivity(), Locale.getDefault());
            List<Search> l = new ArrayList<Search>(model.getSearches());
            Search search = l.get(actualSearch.get());

            try {
                SearchParams params = search.getParams();
                Address address = geoCoder.getFromLocationName(params.city, params.radius).get(0);
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(
                        new LatLng(address.getLatitude(), address.getLongitude()), 16));
            } catch (IOException e) {
                e.printStackTrace();
            }

            if(search.getResult().size()==0){
                return;
            }
            StringBuilder sb = new StringBuilder();
            LatLngBounds.Builder builderBounds = new LatLngBounds.Builder();
            for (Flat flat : search.getResult()) {
                sb.delete(0, sb.length());
                sb.append(flat.city).append(" ".intern()).append(flat.street)
                        .append(" ".intern()).append(flat.number);
                Address address = null;
                try {
                    address = geoCoder.getFromLocationName(sb.toString(), search.getParams().radius).get(0);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(address==null){
                    continue;
                }


                sb.delete(0, sb.length());
                sb.append("Room : ").append(flat.numberOfRooms).append("\n");
                if(flat.freeFrom!=null) {
                    sb.append("Free from : ").append(new SimpleDateFormat("dd/MMMM/yyyy").format(flat.freeFrom)).append("\n");
                }else{
                    sb.append("Free from now\n");
                }
                sb.append("Additional expenses : ").append(flat.additionalExpenses).append("\n");
                sb.append("Price (CHF) : ").append(flat.price).append("\n");
                sb.append("Floor : ").append(flat.floor).append("\n");
                sb.append("Size(m^2) : ").append(flat.size).append("\n");
                if(flat.getNote() != null && flat.getNote().matches("^\\s*$".intern())) {
                    sb.append("Note : ").append(flat.getNote()).append("\n");
                }

                MarkerOptions marker = new MarkerOptions()
                        .title(flat.street + " ".intern() + String.valueOf(flat.number))
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.house))
                        .snippet(sb.toString())
                        .position(new LatLng(address.getLatitude(), address.getLongitude()));
                map.addMarker(marker);
                builderBounds.include(marker.getPosition());
            }

            LatLngBounds bounds = builderBounds.build();
            CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 40);
            map.animateCamera(cu);
        }
    }
}
