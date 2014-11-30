package org.mobop.flatseeker.model;

import java.util.ArrayList;
import java.util.Collection;
import org.mobop.flatseeker.model.web.FlatFinder;
import org.mobop.flatseeker.model.web.HomeGateFinder;

public class Model {
    Search actualSearch;
    ArrayList<Search> searches;
    FlatFinder finder = new HomeGateFinder();

    public Model(){
        this.searches = new ArrayList<Search>();
    }
    
    public Collection<Search> getSearches() {
        return this.searches;
    }

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

    //becarefull if try to add a search which isn't in the list searches
    public void setActualSearch(Search actualSearch){
//        if(searches.contains(searches) || actualSearch == null)
            this.actualSearch = actualSearch;
    }
    
    public Search getActualSearch(){
        return actualSearch;
    }

    protected void removeSearch(Search search) {
        this.searches.remove(search);
    }
}
