package org.mobop.flatseeker;

import android.app.ActionBar;
import android.content.Context;
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
    Context context;

    public static final int EDIT_ID = 0;
    public static final int SEARCH_ID = 1;
    public static final int MAP_ID = 2;

    public FragmentPageAdapter(FragmentManager fm,Context context, Model model, ActualSearch actualSearch) {
        super(fm);
        this.model = model;
        this.context = context;
        this.actualSearch = actualSearch;
    }

    @Override
    public Fragment getItem(int arg0) {

        switch (arg0) {
            case EDIT_ID:
                if (editFragment == null)
                    editFragment = EditFragment.newInstance(model, actualSearch);
                return editFragment;
            case SEARCH_ID:
                if (searchListFragment == null)
                    searchListFragment = SearchListFragment.newInstance(model, actualSearch);
                return searchListFragment;
            case MAP_ID:
                if (mapFragment == null)
                    mapFragment = MapFragment.newInstance(model, actualSearch);
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
                    if(actualSearch.get()==-1) {
                        Toast.makeText(context,  "Example values with Neuchatel", Toast.LENGTH_LONG).show();
                    }
                }
                break;
        }
    }

    public void setModelAndActualSearch(Model model, ActualSearch actualSearch) {
        this.model = model;
        this.actualSearch = actualSearch;
        if (editFragment != null) {
            editFragment.setModelAndActualSearch(model, actualSearch);
        }
        if (searchListFragment != null) {
            searchListFragment.setModelAndActualSearch(model, actualSearch);
        }
        if (mapFragment != null) {
            mapFragment.setModelAndActualSearch(model, actualSearch);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        searchListFragment.onActivityResult(requestCode, resultCode, data);
    }
}
