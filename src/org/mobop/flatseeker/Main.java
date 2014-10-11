package org.mobop.flatseeker;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.app.Notification.Action;
import android.net.wifi.p2p.WifiP2pManager.ActionListener;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class Main extends FragmentActivity implements ActionBar.TabListener {
    ActionBar actionBar;
    ViewPager viewPager;
    FragmentPageAdapter ft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.main);

	viewPager = (ViewPager) findViewById(R.id.pager);
	ft = new FragmentPageAdapter(getSupportFragmentManager());

	actionBar = getActionBar();
	viewPager.setAdapter(ft);
	actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
	actionBar.addTab(actionBar.newTab().setText("Edit").setTabListener(this));
	actionBar.addTab(actionBar.newTab().setText("List").setTabListener(this));
	actionBar.addTab(actionBar.newTab().setText("Map").setTabListener(this));

	viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

	    @Override
	    public void onPageSelected(int arg0) {
		actionBar.setSelectedNavigationItem(arg0);
	    }

	    @Override
	    public void onPageScrolled(int arg0, float arg1, int arg2) {
	    }

	    @Override
	    public void onPageScrollStateChanged(int arg0) {
	    }
	});
    }

    @Override
    public void onTabSelected(Tab tab, FragmentTransaction ft) {
	viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabReselected(Tab tab, FragmentTransaction ft) {
    }

    @Override
    public void onTabUnselected(Tab tab, FragmentTransaction ft) {
    }
}
