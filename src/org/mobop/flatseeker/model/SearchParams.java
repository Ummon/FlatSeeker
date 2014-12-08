package org.mobop.flatseeker.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class SearchParams implements Cloneable, Parcelable {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(city);
        dest.writeInt(radius);
        dest.writeInt(price.from);
        dest.writeInt(price.to);
        dest.writeDouble(numberOfRooms.from);
        dest.writeDouble(numberOfRooms.to);
        dest.writeInt(size.from);
        dest.writeInt(size.to);
    }

    private SearchParams (Parcel in) {
        city = in.readString();
        radius = in.readInt();
        price = new Range<Integer>(in.readInt(),in.readInt());
        numberOfRooms = new Range<Double>(in.readDouble(),in.readDouble());
        size = new Range<Integer>(in.readInt(),in.readInt());
    }

    public static final Parcelable.Creator<SearchParams> CREATOR
            = new Parcelable.Creator<SearchParams>() {
        public SearchParams createFromParcel(Parcel in) {
            return new SearchParams(in);
        }
        public SearchParams[] newArray(int size) {
            return new SearchParams[size];
        }
    };
}