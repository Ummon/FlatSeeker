package org.mobop.flatseeker.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Collection;

public class HomeGateFinder extends FlatFinder {

    @Override
    public Collection<Flat> Find(SearchParams params) {


        return new ArrayList<Flat>();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    private HomeGateFinder (Parcel in) {
    }

    public static final Parcelable.Creator<HomeGateFinder> CREATOR
            = new Parcelable.Creator<HomeGateFinder>() {
        public HomeGateFinder createFromParcel(Parcel in) {
            return new HomeGateFinder(in);
        }

        public HomeGateFinder[] newArray(int size) {
            return new HomeGateFinder[size];
        }
    };
}
