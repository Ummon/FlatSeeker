package org.mobop.flatseeker.model;

public class PriceRange implements Cloneable {
    public final int from;
    public final int to;

    public PriceRange(int from, int to) {
        this.from = from;
        this.to = to;
    }

    public PriceRange clone() {
        return new PriceRange(this.from, this.to);
    }
}