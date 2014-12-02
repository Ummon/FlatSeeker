package org.mobop.flatseeker.model;

public class Range<T> implements Cloneable {
    public final T from;
    public final T to;

    public Range(T from, T to) {
        this.from = from;
        this.to = to;
    }

    public Range clone() {
        return new Range<T>(this.from, this.to);
    }
}