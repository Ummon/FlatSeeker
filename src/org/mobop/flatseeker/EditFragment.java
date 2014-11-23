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
import org.mobop.flatseeker.model.PriceRange;
import org.mobop.flatseeker.model.SearchParams;

public class EditFragment extends Fragment{

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

       
        return v;
    }
}


