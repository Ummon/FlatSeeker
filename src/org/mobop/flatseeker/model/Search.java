package org.mobop.flatseeker.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * To create a new search use 'Model.newSearch(..)'.
 */
public class Search {
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
        result = new ArrayList<Flat>(model.finder.Find(params)); // a supp avant de commit
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
}
