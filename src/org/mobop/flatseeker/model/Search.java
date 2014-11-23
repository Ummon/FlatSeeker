package org.mobop.flatseeker.model;

import java.util.ArrayList;
import java.util.List;

public class Search {
    protected Search(SearchParams params) {
        this.params = params;
    }

    //TODO ONLY FOR TEST
    public Search(String town) {
//        (String city, int range, PriceRange priceRange, int numberOfRooms, int size
        this.params = new SearchParams(town,2,new PriceRange(1,10),2,20);
    }

    public void delete() {

    }
    
//    @Override
//    public boolean equals(Object a){
//        if ( this == a ) return true;
//        if ( !(a instanceof SearchParams) ) return false;
//        return this.params.equals(((Search)a).params);
//    }
    
    public SearchParams getParams(){
        return params;
    }

    public final List<String> children = new ArrayList<String>();
    SearchParams params;
}
