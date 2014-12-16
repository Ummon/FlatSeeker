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
import android.widget.Toast;

import org.mobop.flatseeker.model.Flat;
import org.mobop.flatseeker.model.Model;
import org.mobop.flatseeker.model.Search;

/**
 * Flatseeker MobOp
 * HES-SO
 * Created by Gregori BURRI, Etienne FRANK on 02.11.2014.
 */
public class SearchExpandableListAdapter extends BaseExpandableListAdapter {

    public final static int TAG_NOTE = 1;

    final SparseArray<Search> groups;
    public LayoutInflater inflater;
    public Activity activity;
    ExpandableListView mExpandableList;

    Model model;
    ActualSearch actualSearch;

    public SearchExpandableListAdapter(Fragment act, SparseArray<Search> groups, Model model, ActualSearch actualSearch) {
        activity = act.getActivity();
        this.groups = groups;
        inflater = activity.getLayoutInflater();
        this.model = model;
        this.actualSearch = actualSearch;
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
        priceTbx.setText(String.valueOf(flat.price));
        TextView roomTbx = (TextView) convertView.findViewById(R.id.searchFlatRoom);
        roomTbx.setText(String.valueOf(flat.numberOfRooms));
        TextView sizeRoom = (TextView) convertView.findViewById(R.id.searchFlatSize);
        sizeRoom.setText(String.valueOf(flat.size));
        TextView number = (TextView) convertView.findViewById(R.id.searchFlatNumber);
        number.setText(String.valueOf(flat.number));
        TextView floor = (TextView) convertView.findViewById(R.id.searchFlatFloor);
        floor.setText(String.valueOf(flat.floor));
        TextView estateAgent = (TextView) convertView.findViewById(R.id.searchFlatEstateAgent);
        estateAgent.setText(String.valueOf(flat.estateAgent));
        TextView contact = (TextView) convertView.findViewById(R.id.searchFlatContact);
        contact.setText(String.valueOf(flat.contact));

//        Toast.makeText(activity,"toto",Toast.LENGTH_SHORT).show();

        //start note activity
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, NoteActivity.class);
                intent.putExtra(NoteActivity.NOTE_FLAT, (android.os.Parcelable) flat);
                activity.startActivityForResult(intent, TAG_NOTE);
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

        if (groupPosition == actualSearch.get()) {
            actualSearch.set(-1);
        }
    }

    @Override
    public void onGroupExpanded(int groupPosition) {
        super.onGroupExpanded(groupPosition);

        if (actualSearch.get() != -1) {
            mExpandableList.collapseGroup(actualSearch.get());
        }
        actualSearch.set(groupPosition);
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