package org.mobop.flatseeker.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

public class Model implements Parcelable, Serializable {
    ArrayList<Search> searches;
    FlatFinder finder;

    public Model(FlatFinder finder) {
        this.searches = new ArrayList<Search>();
        this.finder = finder;
    }

    private Model(Parcel in) {
        searches = new ArrayList<Search>();
        int size = in.readInt();
        for (int i = 0; i < size; i++) {
            Search s = in.readParcelable(Search.class.getClassLoader());
            s.setModel(this);
            searches.add(s);
        }
        this.finder = new ImmoScout24Finder(); // Default finder.
    }

    public Collection<Search> getSearches() {
        return this.searches;
    }

    // Take time.
    public Search newSearch(SearchParams params) {
        Search s = new Search(this, params);
        this.searches.add(s);
        return s;
    }

    public Collection<Flat> getBlacklistedFlats() {
        return null;
    }

    public void clearBlacklistedFlatsList() {

    }

    protected void removeSearch(Search search) {
        this.searches.remove(search);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.searches.size());
        for (Search s : this.searches)
            dest.writeParcelable(s, flags);
    }

    public static final Parcelable.Creator<Model> CREATOR
            = new Parcelable.Creator<Model>() {
        public Model createFromParcel(Parcel in) {
            return new Model(in);
        }

        public Model[] newArray(int size) {
            return new Model[size];
        }
    };

    public void setFinder(FlatFinder finder) {
        this.finder = finder;
    }
}
