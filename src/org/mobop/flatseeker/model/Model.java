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
    
    //only for test purpose
    public Search newSearchTEST(String town){
        Search s = new Search(town);
        this.searches.add(s);
        return s;
    }
    
    
    //becarefull if try to add a search which isn't in the list searches
    public void setActualSearch(Search actualSearch){
//        if(searches.contains(searches) || actualSearch == null)
            this.actualSearch = actualSearch;
    }
    
    public Search getActualSearch(){
        return actualSearch;
    }

    Search actualSearch;
    ArrayList<Search> searches;
}
