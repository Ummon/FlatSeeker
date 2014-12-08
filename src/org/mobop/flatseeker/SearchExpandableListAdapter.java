package org.mobop.flatseeker;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckedTextView;
import android.widget.ExpandableListView;
import android.widget.TextView;

import org.mobop.flatseeker.model.Flat;
import org.mobop.flatseeker.model.Model;
import org.mobop.flatseeker.model.Search;

/**
 * Flatseeker MobOp
 * HES-SO
 * Created by Gregori BURRI, Etienne FRANK on 02.11.2014.
 */
public class SearchExpandableListAdapter extends BaseExpandableListAdapter {

    public final static String EXTRA_MESSAGE = "org.mobop.flatseeker.MESSAGE";
    public final static int TAG_NOTE = 1;
    private final SparseArray<Search> groups;
    public LayoutInflater inflater;
    public Activity activity;
    private static int searchSelected = -1;
    private ExpandableListView mExpandableList;
    private Model model;
    
    public SearchExpandableListAdapter(Fragment act, SparseArray<Search> groups, Model model) {
        activity = act.getActivity();
        this.groups = groups;
        inflater = activity.getLayoutInflater();
        this.model = model;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return groups.get(groupPosition).getFlat(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final Flat flat = (Flat) getChild(groupPosition, childPosition);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.listrow_flat, null);
        }

        TextView streetTbx = (TextView) convertView.findViewById(R.id.search_row);
        streetTbx.setText(flat.street);
        TextView priceTbx = (TextView) convertView.findViewById(R.id.searchFlatPrice);
        priceTbx.setText(flat.street);
        TextView roomTbx = (TextView) convertView.findViewById(R.id.searchFlatRoom);
        roomTbx.setText(flat.street);
        TextView sizeRoom = (TextView) convertView.findViewById(R.id.searchFlatSize);
        sizeRoom.setText(flat.street);

        //start note activity
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, NoteActivity.class);
                intent.putExtra(EXTRA_MESSAGE, flat);
                activity.startActivityForResult(intent,TAG_NOTE);
//                Toast.makeText(activity, children,
//                        Toast.LENGTH_SHORT).show();
            }
        });
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return groups.get(groupPosition).getResult().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groups.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return groups.size();
    }

    @Override
    public void onGroupCollapsed(int groupPosition) {
        super.onGroupCollapsed(groupPosition);
        
        if(groupPosition==searchSelected) {
            searchSelected = -1;
            // TODO
            //model.setActualSearch(null);
        }
    }

    @Override
    public void onGroupExpanded(int groupPosition) {
        super.onGroupExpanded(groupPosition);
        
        if (searchSelected != -1) {
            mExpandableList.collapseGroup(searchSelected);
        }
        // TODO.
        // model.setActualSearch(groups.get(groupPosition));
        searchSelected=groupPosition;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.listrow_search, null);
        }
        Search group = (Search) getGroup(groupPosition);
        ((CheckedTextView) convertView).setText(group.getParams().city);
        ((CheckedTextView) convertView).setChecked(isExpanded);
        mExpandableList = (ExpandableListView) activity.findViewById(R.id.listView);
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}