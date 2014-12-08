package org.mobop.flatseeker;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

    Model model;
    Integer actualSearch;
    
    Button save;
    EditText cityTbx;
    EditText radiusTbx;
    EditText priceStartTbx;
    EditText priceEndTbx;
    EditText roomStartTbx;
    EditText roomEndTbx;
    EditText sizeStartTbx;
    EditText sizeEndTbx;

    public EditFragment(Model model,Integer actualSearch){
        this.model = model;
        this.actualSearch = actualSearch;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.edit_layout, container, false);
        
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
            model.newSearch(new SearchParams(
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
                ));
            }
        });

        return v;
    }

    public void onResume(){
        super.onResume();
        Toast.makeText(getActivity(),String.valueOf(actualSearch),Toast.LENGTH_SHORT).show();
    }

    private void fill(){
        if(actualSearch>-1){
            List<Search> l = new ArrayList<Search>(model.getSearches());
            SearchParams s = l.get(actualSearch).getParams();

            cityTbx.setText(s.city);
            radiusTbx.setText(s.radius);
            priceStartTbx.setText(s.price.from);
            priceEndTbx.setText(s.price.to);
            roomStartTbx.setText(String.valueOf(s.numberOfRooms.from));
            roomEndTbx.setText(String.valueOf(s.numberOfRooms.to));
            sizeStartTbx.setText(s.size.from);
            sizeEndTbx.setText(s.size.to);
        }else{
            cityTbx.setText("");
            radiusTbx.setText("");
            priceStartTbx.setText("");
            priceEndTbx.setText("");
            roomStartTbx.setText("");
            roomEndTbx.setText("");
            sizeStartTbx.setText("");
            sizeEndTbx.setText("");
        }
    }

}


