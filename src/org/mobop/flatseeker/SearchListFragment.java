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

import org.mobop.flatseeker.model.Flat;
import org.mobop.flatseeker.model.Model;
import org.mobop.flatseeker.model.Search;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SearchListFragment extends Fragment {
    SparseArray<Search> groups = new SparseArray<Search>();
    SearchExpandableListAdapter searchExpandable;

    Model model;
    ActualSearch actualSearch;

    // TODO change to http://stackoverflow.com/questions/10450348/do-fragments-really-need-an-empty-constructor
    public void initSearchListFragment(Model model, ActualSearch actualSearch){
        this.model = model;
        this.actualSearch = actualSearch;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        this.model = getArguments().getParcelable(Model.class.getName());
        this.actualSearch = getArguments().getParcelable(ActualSearch.class.getName());
//        setRetainInstance(true);

        createData();
    }

    public static final SearchListFragment newInstance(Model model, ActualSearch actualSearch)
    {
        SearchListFragment f = new SearchListFragment();
        Bundle bdl = new Bundle(2);
        bdl.putParcelable(Model.class.getName(), model);
        bdl.putParcelable(ActualSearch.class.getName(), actualSearch);
        f.setArguments(bdl);
        return f;
    }

    @Override
    public void onResume(){
        if(searchExpandable==null){return;}

        createData();
        searchExpandable.notifyDataSetChanged();
        super.onResume();
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.list_layout, null);

        ExpandableListView elv = (ExpandableListView) v.findViewById(R.id.listView);
        searchExpandable = new SearchExpandableListAdapter(this,groups,model,actualSearch);
        elv.setAdapter(searchExpandable);
        return v;
    }

    public void createData() {
        groups.clear();
        
        Collection<Search> searches = model.getSearches();
        for (Search element : searches) {
            groups.append(groups.size(), element);
        }
    }

    public void setModelAndActualSearch(Model model,ActualSearch actualSearch){
        this.model = model;
        this.actualSearch = actualSearch;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode){
            case SearchExpandableListAdapter.TAG_NOTE:
                if(data==null){
                    return;
                }
                //TODO Ugly as shit, can be better ?
                // because parcelable send back a copy of our flat, we have to find which flat it is
                // and set the new note to it.
                Flat f = data.getParcelableExtra(NoteActivity.NOTE_MESSAGE);
                List<Search> l = new ArrayList<Search>(model.getSearches());
                Search s = l.get(actualSearch.get());
                List<Flat> searches = new ArrayList<Flat>(s.getResult());
                for(Flat flat : searches){
                    if(flat.equalsWithoutNote(f)){
                        flat.setNote(f.getNote());
                    }
                }
                break;
            default:
                break;
        }
    }
}
