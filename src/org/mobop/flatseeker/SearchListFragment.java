package org.mobop.flatseeker;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

import org.mobop.flatseeker.model.Model;
import org.mobop.flatseeker.model.Search;

import java.util.Collection;

public class SearchListFragment extends Fragment {
    SparseArray<Group> groups = new SparseArray<Group>();
    SearchExpandableListAdapter searchExpandable;
    private Model model;
    
    public SearchListFragment(Model model){
        this.model = model;
    }

    @Override
    public void onResume(){
        if(searchExpandable==null){return;}
        
        //TODO only on can be selected and then adapt it to the map view
        createData();
        searchExpandable.notifyDataSetChanged();
        super.onResume();

        Toast.makeText(getActivity(), "rolala", Toast.LENGTH_LONG).show();
        
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.list_layout, null);
        createData();
        ExpandableListView elv = (ExpandableListView) v.findViewById(R.id.listView);
        searchExpandable = new SearchExpandableListAdapter(this,groups);
        elv.setAdapter(searchExpandable);
        return v;
    }

    public void createData() {
        groups.clear();
//        Group group = new Group("Neuchâtel");
        Collection<Search> searches = model.getSearchs();
        for (Search element : searches) {
            Group group = new Group(element.getParams().getCity());
//            for (int i = 0; i < 5; i++) {
//                group.children.add("Sub Item" + i);
//            }
            groups.append(groups.size(), group);
        }
        groups.append(groups.size(), new Group("proute"));
//        for (int j = 0; j < 5; j++) {
//            Group group = new Group("Test " + j);
//            for (int i = 0; i < 5; i++) {
//                group.children.add("Sub Item" + i);
//            }
//            groups.append(j, group);
//        }
    }
}
