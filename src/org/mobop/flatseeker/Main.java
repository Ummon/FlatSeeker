package org.mobop.flatseeker;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.mobop.flatseeker.model.Flat;
import org.mobop.flatseeker.model.Model;
import org.mobop.flatseeker.model.ImmoStreetFinder;
import org.mobop.flatseeker.model.Search;

import java.util.ArrayList;
import java.util.List;

import static org.mobop.flatseeker.R.menu.main;

public class Main extends FragmentActivity implements TabListener {
    private ActionBar actionBar;
    private ViewPager viewPager;
    private FragmentPageAdapter fpAdapter;
    private Model model;
    private ActualSearch actualSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        model = StorageManager.loadModel(getApplicationContext());
        actualSearch = new ActualSearch();
        fpAdapter = new FragmentPageAdapter(getSupportFragmentManager(), model, actualSearch);
        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(fpAdapter);

        actionBar = getActionBar();
        assert actionBar != null;
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.addTab(actionBar.newTab().setText("Edit").setTabListener(this));
        actionBar.addTab(actionBar.newTab().setText("List").setTabListener(this));
        actionBar.addTab(actionBar.newTab().setText("Map").setTabListener(this));

        //	GoogleMap googleMap;
        //	googleMap = ((SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map)).getMap();

        viewPager.requestTransparentRegion(viewPager);
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

//        ft.replace(R.id.pager,fpAdapter.getItem(tab.getPosition()));
        fpAdapter.refresh(tab);
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabReselected(Tab tab, FragmentTransaction ft) {
    }

    @Override
    public void onTabUnselected(Tab tab, FragmentTransaction ft) {
    }

    public void onPause() {
        StorageManager.saveModel(model, getApplicationContext());
        model =StorageManager.loadModel(getApplicationContext());
        super.onPause();
    }

    public void onResume() {
        super.onResume();
        model = StorageManager.loadModel(getApplicationContext());
//        actualSearch = new ActualSearch();
        fpAdapter.setModelAndActualSearch(model, actualSearch);
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
                StorageManager.saveModel(model, getApplicationContext());
                break;
            default:
                break;
        }
        fpAdapter.onActivityResult(requestCode, resultCode, data);
    }
}
