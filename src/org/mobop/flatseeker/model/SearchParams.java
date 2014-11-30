package org.mobop.flatseeker.model;

public class SearchParams implements Cloneable {
    public String city;
    public int range;
    public PriceRange priceRange;
    public int numberOfRooms;
    public int size;

    /**
     *
     * @param city
     * @param rang [km]
     * @param priceRange
     * @param numberOfRooms
     * @param size [m²]
     */
    public SearchParams(String city, int range, PriceRange priceRange, int numberOfRooms, int size) {
        this.city = city;
        this.range = range;
        this.priceRange = priceRange;
        this.numberOfRooms = numberOfRooms;
        this.size = size;
    }

    public SearchParams clone() {
        return new SearchParams(this.city, this.range, this.priceRange.clone(), this.numberOfRooms, this.size);
    }

//    @Override
//    public boolean equals (Object a){
//        if ( this == a ) return true;
//        if ( !(a instanceof SearchParams) ) return false;
//        
//        SearchParams b = (SearchParams)a;
//        return city.equals(b.city )&& range == b.range && priceRange.from==b.priceRange.from &&
//                priceRange.to==b.priceRange.to && numberOfRooms==b.numberOfRooms && size==b.size;
//    }
}