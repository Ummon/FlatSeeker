package org.mobop.flatseeker;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

public class SearchListFragment extends Fragment {
    SparseArray<Group> groups = new SparseArray<Group>();
    
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.list_layout, null);
        createData();
        ExpandableListView elv = (ExpandableListView) v.findViewById(R.id.listView);
        elv.setAdapter(new SearchExpandableListAdapter(this,groups));
        return v;
    }

    public void createData() {

//        Group group = new Group("Neuchâtel");
        for (int j = 0; j < 5; j++) {
            Group group = new Group("Test " + j);
            for (int i = 0; i < 5; i++) {
                group.children.add("Sub Item" + i);
            }
            groups.append(j, group);
        }
    }
}
