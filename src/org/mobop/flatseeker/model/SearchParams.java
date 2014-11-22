package org.mobop.flatseeker.model;

public class SearchParams {
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
    
    public String getCity(){return city;}

    String city;
    int range;
    PriceRange priceRange;
    int numberOfRooms;
    int size;
}
