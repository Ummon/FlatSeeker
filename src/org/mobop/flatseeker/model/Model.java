package org.mobop.flatseeker.model;

import java.util.ArrayList;
import java.util.Collection;

public class Model {
    ArrayList<Search> searches;
    FlatFinder finder;

    public Model(FlatFinder finder) {
        this.searches = new ArrayList<Search>();
        this.finder = finder;
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
}
