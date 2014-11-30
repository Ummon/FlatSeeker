package org.mobop.flatseeker;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import org.mobop.flatseeker.model.Model;
import org.mobop.flatseeker.model.Search;

import java.util.Collection;

public class SearchListFragment extends Fragment {
    SparseArray<Search> groups = new SparseArray<Search>();
    SearchExpandableListAdapter searchExpandable;
    private Model model;
    
    public SearchListFragment(Model model){
        this.model = model;
    }

    @Override
    public void onResume(){
        if(searchExpandable==null){return;}
        
        //TODO then adapt it to the map view
        createData();
        searchExpandable.notifyDataSetChanged();
        super.onResume();

//        Toast.makeText(getActivity(), "rolala", Toast.LENGTH_LONG).show();
        
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.list_layout, null);
        createData();
        ExpandableListView elv = (ExpandableListView) v.findViewById(R.id.listView);
        searchExpandable = new SearchExpandableListAdapter(this,groups,model);
        elv.setAdapter(searchExpandable);
        return v;
    }

    public void createData() {
        groups.clear();
        
//        Group group = new Group("Neuchâtel");
        Collection<Search> searches = model.getSearches();
        /*for (Search element : searches) {
            element.children.clear();//TODO should remove
            for (int i = 0; i < 5; i++) {
                element.children.add("Sub Item" + i);
            }
            groups.append(groups.size(), element);
        }*/
//        groups.append(groups.size(), new Group("proute"));
    }
}
