package org.mobop.flatseeker.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.ParcelableSpan;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * To create a new search use 'Model.newSearch(..)'.
 */
public class Search implements Parcelable {
    Model model;
    SearchParams params;
    ArrayList<Flat> result = new ArrayList<Flat>();

    protected Search(Model model, SearchParams params) {
        this.model = model;
        this.setParams(params);
    }

    /**
     * Delete the search. The associated blacklisted flats will stay as blacklisted.
     */
    public void delete() {
        this.model.removeSearch(this);
    }
    
//    @Override
//    public boolean equals(Object a){
//        if ( this == a ) return true;
//        if ( !(a instanceof SearchParams) ) return false;
//        return this.params.equals(((Search)a).params);
//    }

    public SearchParams getParams() {
        return params.clone();
    }

    // Take time.
    public void setParams(SearchParams params) {
        if (params == null || params == this.params)
            return;

        this.params = params;
        result = new ArrayList<Flat>(model.finder.Find(params)); // TODO a supp avant de commit
        this.update();
    }

    public Collection<Flat> getResult() {
        return this.result;
    }

    public Flat getFlat(int index) {
        return this.result.get(index);
    }

    /**
     * Update synchronously the result. May take a little time.
     * Automatically called for new search and when updating params with 'setParams(..)'.
     */
    public void update() {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(params,flags);
//        dest.writeParcelable(model,flags);
        dest.writeInt(result.size());
        for(Flat f : result){
            dest.writeParcelable(f,flags);
        }
    }

    private Search (Parcel in) {
        params = in.readParcelable(SearchParams.class.getClassLoader());
//        model = in.readParcelable(Model.class.getClassLoader());
        int size = in.readInt();
        for(int i=0;i<size;i++){
            result.add(in.<Flat>readParcelable(Flat.class.getClassLoader()));
        }
    }

    public static final Parcelable.Creator<Search> CREATOR
            = new Parcelable.Creator<Search>() {
        public Search createFromParcel(Parcel in) {
            return new Search(in);
        }

        public Search[] newArray(int size) {
            return new Search[size];
        }
    };

    // set only use when we parcelable. Model have Search -> Search have model -> infity
    public void setModel(Model model){
        if(this.model==null){
            this.model = model;
        }
    }
}
