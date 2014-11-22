package org.mobop.flatseeker.model;

import java.util.ArrayList;
import java.util.Collection;

public class Model {
    public Model(){
        searches = new ArrayList<Search>();
    }
    
    public Collection<Search> getSearchs() {
        return this.searches;
    }

    public Search newSearch(SearchParams params) {
        Search s = new Search(params);
        this.searches.add(s);
        return s;
    }

    ArrayList<Search> searches;
}
