package org.mobop.flatseeker.model;

public class SearchParams implements Cloneable {
    public String city;
    public int radius; // [m].
    public Range<Integer> price;
    public Range<Double> numberOfRooms;
    public Range<Integer> size;

    /**
     *
     * @param city
     * @param rang [km]
     * @param price
     * @param numberOfRooms
     * @param size [m²]
     */
    public SearchParams(String city, int radius, Range<Integer> price, Range<Double> numberOfRooms, Range<Integer> size) {
        this.city = city;
        this.radius = radius;
        this.price = price;
        this.numberOfRooms = numberOfRooms;
        this.size = size;
    }

    public SearchParams clone() {
        return new SearchParams(this.city, this.radius, this.price.clone(), this.numberOfRooms, this.size);
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