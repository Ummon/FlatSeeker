package org.mobop.flatseeker;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.mobop.flatseeker.model.Model;
import org.mobop.flatseeker.model.Range;
import org.mobop.flatseeker.model.Search;
import org.mobop.flatseeker.model.SearchParams;

import java.util.ArrayList;
import java.util.List;

public class EditFragment extends Fragment{

    static Model model;
    static ActualSearch actualSearch;
    
    Button save;
    EditText cityTbx;
    EditText radiusTbx;
    EditText priceStartTbx;
    EditText priceEndTbx;
    EditText roomStartTbx;
    EditText roomEndTbx;
    EditText sizeStartTbx;
    EditText sizeEndTbx;
    ViewPager viewPager;

    // TODO change to http://stackoverflow.com/questions/10450348/do-fragments-really-need-an-empty-constructor
    public void initEditFragment(Model model,ActualSearch actualSearch){
        this.model = model;
        this.actualSearch = actualSearch;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.edit_layout, container, false);

        viewPager = (ViewPager) getActivity().findViewById(R.id.pager);
        
        cityTbx = (EditText)v.findViewById(R.id.editCityInput);
        radiusTbx = (EditText)v.findViewById(R.id.editRangeInput);
        priceStartTbx = (EditText)v.findViewById(R.id.editPriceStartInput);
        priceEndTbx = (EditText)v.findViewById(R.id.editPriceEndInput);
        roomStartTbx = (EditText)v.findViewById(R.id.editRoomStartInput);
        roomEndTbx = (EditText)v.findViewById(R.id.editRoomEndInput);
        sizeStartTbx = (EditText)v.findViewById(R.id.editSizeStartInput);
        sizeEndTbx = (EditText)v.findViewById(R.id.editSizeEndInput);

        fill();

        save = (Button) v.findViewById(R.id.editSaveButton);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // save the new search in the global model
                SearchParams test = new SearchParams(
                        cityTbx.getText().toString(),
                        Integer.valueOf(radiusTbx.getText().toString()),
                        new Range<Integer>(
                                Integer.valueOf(priceStartTbx.getText().toString()),
                                Integer.valueOf(priceEndTbx.getText().toString())),
                        new Range<Double>(
                                Double.valueOf(roomStartTbx.getText().toString()),
                                Double.valueOf(roomEndTbx.getText().toString())),
                        new Range<Integer>(
                                Integer.valueOf(sizeStartTbx.getText().toString()),
                                Integer.valueOf(sizeEndTbx.getText().toString()))
                );

                model.newSearch(test);


            viewPager.setCurrentItem(FragmentPageAdapter.SEARCH_ID);
            }
        });

        return v;
    }

    public void onResume(){
        super.onResume();
        fill();
    }

    private void fill(){
        if(actualSearch.get()>-1){
            List<Search> l = new ArrayList<Search>(model.getSearches());
            SearchParams s = l.get(actualSearch.get()).getParams();

            cityTbx.setText(s.city);
            radiusTbx.setText(String.valueOf(s.radius));
            priceStartTbx.setText(String.valueOf(s.price.from));
            priceEndTbx.setText(String.valueOf(s.price.to));
            roomStartTbx.setText(String.valueOf(s.numberOfRooms.from));
            roomEndTbx.setText(String.valueOf(s.numberOfRooms.to));
            sizeStartTbx.setText(String.valueOf(s.size.from));
            sizeEndTbx.setText(String.valueOf(s.size.to));
        }else{
//            cityTbx.setText("");
//            radiusTbx.setText("");
//            priceStartTbx.setText("");
//            priceEndTbx.setText("");
//            roomStartTbx.setText("");
//            roomEndTbx.setText("");
//            sizeStartTbx.setText("");
//            sizeEndTbx.setText("");
        }
    }

}


