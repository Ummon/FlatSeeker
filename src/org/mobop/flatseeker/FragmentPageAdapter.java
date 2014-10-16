package org.mobop.flatseeker;

import com.google.android.gms.maps.SupportMapFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class FragmentPageAdapter extends FragmentPagerAdapter {

    public FragmentPageAdapter(FragmentManager fm) {
	super(fm);
    }

    @Override
    public Fragment getItem(int arg0) {

	switch (arg0) {
	case 0:
	    return new EditFragment();
	case 1:
	    return new ListFragment();
	case 2:
	    return SupportMapFragment.newInstance();
//	    return new MapFragment();
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

}
