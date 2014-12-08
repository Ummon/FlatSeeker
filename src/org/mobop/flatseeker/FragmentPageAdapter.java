package org.mobop.flatseeker;

import android.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import org.mobop.flatseeker.model.*;

public class FragmentPageAdapter extends FragmentPagerAdapter {

    EditFragment editFragment;
    SearchListFragment searchListFragment;
    MapFragment mapFragment;
    Model model;
    Integer actualSearch = -1;

    public static final int EDIT_ID = 0;
    public static final int SEARCH_ID = 1;
    public static final int MAP_ID = 2;

    public FragmentPageAdapter(FragmentManager fm) {
        super(fm);
        model = new Model(new StubFinder());
        /*model.newSearchTEST("Neuchatel");
        model.newSearchTEST("Lausanne");
        model.newSearchTEST("Zurich");*/
    }

    @Override
    public Fragment getItem(int arg0) {
        
        switch (arg0) {
        case EDIT_ID:
            if(editFragment==null)
                editFragment = new EditFragment(model, actualSearch);
            return editFragment;
        case SEARCH_ID:
            if(searchListFragment==null)
                searchListFragment = new SearchListFragment(model, actualSearch);
            return searchListFragment;
        case MAP_ID:
    //	    return SupportMapFragment.newInstance();
            if(mapFragment==null)
                mapFragment = new MapFragment(model, actualSearch);
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
        if(tab.getPosition()==SEARCH_ID) {
            if (searchListFragment != null) {
                searchListFragment.onResume();
            }
        }

        if(tab.getPosition()==MAP_ID) {
            if (mapFragment != null) {
                mapFragment.refreshPosition();
            }
        }

        if(tab.getPosition()==EDIT_ID) {
            if (editFragment != null) {
                editFragment.onResume();
            }
        }
    }
}
