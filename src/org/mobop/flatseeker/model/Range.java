package org.mobop.flatseeker.model;

import java.io.Serializable;

public class Range<T> implements Cloneable, Serializable {
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