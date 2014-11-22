package org.mobop.flatseeker.model;

public class Search {
    protected Search(SearchParams params) {
        this.params = params;
    }

    public void delete() {

    }
    
    public SearchParams getParams(){
        return params;
    }

    SearchParams params;
}
