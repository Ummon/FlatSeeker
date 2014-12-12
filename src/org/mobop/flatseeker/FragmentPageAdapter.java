package org.mobop.flatseeker;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.widget.Toast;

import org.mobop.flatseeker.model.*;

public class FragmentPageAdapter extends FragmentPagerAdapter {

    EditFragment editFragment;
    SearchListFragment searchListFragment;
    MapFragment mapFragment;
    Model model;
    ActualSearch actualSearch;
    ActionBar.Tab actualTab;

    public static final int EDIT_ID = 0;
    public static final int SEARCH_ID = 1;
    public static final int MAP_ID = 2;

    public FragmentPageAdapter(FragmentManager fm,Model model,ActualSearch actualSearch) {
        super(fm);
        this.model = model;
        this.actualSearch = actualSearch;
        /*model.newSearchTEST("Neuchatel");
        model.newSearchTEST("Lausanne");
        model.newSearchTEST("Zurich");*/
    }

    @Override
    public Fragment getItem(int arg0) {
        
        switch (arg0) {
        case EDIT_ID:
            if(editFragment==null)
                editFragment = new EditFragment();
                editFragment.initEditFragment(model, actualSearch);
            return editFragment;
        case SEARCH_ID:
            if(searchListFragment==null)
                searchListFragment = new SearchListFragment();
                searchListFragment.initSearchListFragment(model, actualSearch);
            return searchListFragment;
        case MAP_ID:
    //	    return SupportMapFragment.newInstance();
            if(mapFragment==null)
                mapFragment = new MapFragment();
                mapFragment.initMapFragment(model, actualSearch);
            return mapFragment;
        default:
            break;
        }
        return null;
    }

    @Override
    public int getCount() {
	// we got 3 fragments list/map/edit
	    return 3;
    }

    public void refresh(ActionBar.Tab tab) {
        actualTab = tab;

        switch (tab.getPosition()) {
            case SEARCH_ID:
                if (searchListFragment != null) {
                    searchListFragment.onResume();
                }
                break;

            case MAP_ID:
                if (mapFragment != null) {
                    mapFragment.refreshPosition();
                }
                break;

            case EDIT_ID:
                if (editFragment != null) {
                    editFragment.onResume();
                }
                break;
        }
    }

    public void setModelAndActualSearch(Model model,ActualSearch actualSearch){
        this.model = model;
        this.actualSearch = actualSearch;
        if(editFragment!=null) {
            editFragment.setModelAndActualSearch(model, actualSearch);
        }
        if(searchListFragment!=null) {
            searchListFragment.setModelAndActualSearch(model, actualSearch);
        }
        if(mapFragment!=null) {
            mapFragment.setModelAndActualSearch(model, actualSearch);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
            searchListFragment.onActivityResult(requestCode,resultCode,data);
    }
}
