package org.mobop.flatseeker;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import org.mobop.flatseeker.model.Model;

public class FragmentPageAdapter extends FragmentPagerAdapter {

    private EditFragment editFragment;
    private SearchListFragment searchListFragment;
    private MapFragment mapFragment;
    private Model model;
    
    public FragmentPageAdapter(FragmentManager fm) {
        super(fm);
        model = new Model();
        model.newSearchTEST("Neuchatel");
        model.newSearchTEST("Lausanne");
        model.newSearchTEST("Zurich");
    }

    @Override
    public Fragment getItem(int arg0) {
        
        switch (arg0) {
        case 0:
            if(editFragment==null)
                editFragment = new EditFragment(model);
            return editFragment;
        case 1:
            if(searchListFragment==null)
                searchListFragment = new SearchListFragment(model);
            return searchListFragment;
        case 2:
    //	    return SupportMapFragment.newInstance();
            if(mapFragment==null)
                mapFragment = new MapFragment(model);
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

    public void refresh() {
        if(searchListFragment!=null) 
            searchListFragment.onResume();
        
        if(mapFragment!=null) 
            mapFragment.refreshPosition();
    }
}
