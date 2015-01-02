package org.mobop.flatseeker;

import android.content.Intent;
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

    Model model;
    ActualSearch actualSearch;
    ExpandableListView elv;

    public void initSearchListFragment(Model model, ActualSearch actualSearch) {
        this.model = model;
        this.actualSearch = actualSearch;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.model = getArguments().getParcelable(Model.class.getName());
        this.actualSearch = getArguments().getParcelable(ActualSearch.class.getName());

        createData();
    }

    public static final SearchListFragment newInstance(Model model, ActualSearch actualSearch) {
        SearchListFragment f = new SearchListFragment();
        Bundle bdl = new Bundle(2);
        bdl.putParcelable(Model.class.getName(), model);
        bdl.putParcelable(ActualSearch.class.getName(), actualSearch);
        f.setArguments(bdl);
        return f;
    }

    @Override
    public void onResume() {
        createData();

        searchExpandable.notifyDataSetChanged();

        super.onResume();

        for(int i=0;i<model.getSearches().size();i++){
            elv.collapseGroup(i);
        }
        actualSearch.set(-1);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.list_layout, null);

        elv = (ExpandableListView) v.findViewById(R.id.listView);
        searchExpandable = new SearchExpandableListAdapter(this, groups, model, actualSearch);
        elv.setAdapter(searchExpandable);

        return v;
    }

    public void createData() {
        groups.clear();

        Collection<Search> searches = model.getSearches();
        for (Search element : searches) {
            groups.append(groups.size(), element);
        }

//        if (searchExpandable != null) {
//            elv.setAdapter(searchExpandable);
//            searchExpandable.notifyDataSetChanged();
//        }
    }

    public void setModelAndActualSearch(Model model, ActualSearch actualSearch) {
        this.model = model;
        this.actualSearch = actualSearch;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SearchExpandableListAdapter.TAG_NOTE) {
            if (data == null) {
                return;
            }
            createData();
        }
    }
}
