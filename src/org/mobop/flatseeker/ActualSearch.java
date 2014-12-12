package org.mobop.flatseeker;

import android.os.Parcel;
import android.os.Parcelable;

public class ActualSearch implements Parcelable {
    int actualSearch = -1;

    public ActualSearch() {
    }

    public int get() {
        return actualSearch;
    }

    public void set(int actualSearch) {
        this.actualSearch = actualSearch;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(actualSearch);
    }

    private ActualSearch(Parcel in) {
        actualSearch = in.readInt();
    }

    public static final Parcelable.Creator<ActualSearch> CREATOR
            = new Parcelable.Creator<ActualSearch>() {
        public ActualSearch createFromParcel(Parcel in) {
            return new ActualSearch(in);
        }

        public ActualSearch[] newArray(int size) {
            return new ActualSearch[size];
        }
    };
}
