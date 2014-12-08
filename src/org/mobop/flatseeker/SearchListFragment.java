package org.mobop.flatseeker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

import org.mobop.flatseeker.model.Flat;
import org.mobop.flatseeker.model.Model;
import org.mobop.flatseeker.model.Search;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SearchListFragment extends Fragment {
    SparseArray<Search> groups = new SparseArray<Search>();
    SearchExpandableListAdapter searchExpandable;

    static Model model;
    static ActualSearch actualSearch;

    // TODO change to http://stackoverflow.com/questions/10450348/do-fragments-really-need-an-empty-constructor
    public void initSearchListFragment(Model model, ActualSearch actualSearch){
        this.model = model;
        this.actualSearch = actualSearch;
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



        createData();
        ExpandableListView elv = (ExpandableListView) v.findViewById(R.id.listView);
        searchExpandable = new SearchExpandableListAdapter(this,groups,model,actualSearch);
        elv.setAdapter(searchExpandable);
        return v;
    }

    public void createData() {
        groups.clear();
        
//        Group group = new Group("Neuchâtel");
//        groups =
        Collection<Search> searches = model.getSearches();
        for (Search element : searches) {
//            element.children.clear();//TODO should remove
//            for (int i = 0; i < 5; i++) {
//                element.children.add("Sub Item" + i);
//            }

            groups.append(groups.size(), element);
        }
//        groups.append(groups.size(), new Group("proute"));
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
