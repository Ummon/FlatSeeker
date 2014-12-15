package org.mobop.flatseeker.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class SearchParams implements Cloneable, Parcelable, Serializable {
    public String city;
    public int radius; // [m].
    public Range<Integer> price;
    public Range<Double> numberOfRooms;
    public Range<Integer> size;
    public Calendar date;

    /**
     *
     * @param city
     * @param rang [km]
     * @param price
     * @param numberOfRooms
     * @param size [m²]
     */
    public SearchParams(String city, int radius, Range<Integer> price, Range<Double> numberOfRooms, Range<Integer> size, Calendar date) {
        this.city = city;
        this.radius = radius;
        this.price = price;
        this.numberOfRooms = numberOfRooms;
        this.size = size;
    }

    public SearchParams clone() {
        return new SearchParams(this.city, this.radius, this.price.clone(), this.numberOfRooms, this.size, this.date);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.city);
        dest.writeInt(this.radius);
        dest.writeInt(this.price.from);
        dest.writeInt(this.price.to);
        dest.writeDouble(this.numberOfRooms.from);
        dest.writeDouble(this.numberOfRooms.to);
        dest.writeInt(this.size.from);
        dest.writeInt(this.size.to);
        if(this.date != null) {
            dest.writeLong(this.date.getTimeInMillis());
        } else {
            dest.writeLong(0);
        }
    }

    private SearchParams (Parcel in) {
        this.city = in.readString();
        this.radius = in.readInt();
        this.price = new Range<Integer>(in.readInt(),in.readInt());
        this.numberOfRooms = new Range<Double>(in.readDouble(),in.readDouble());
        this.size = new Range<Integer>(in.readInt(),in.readInt());
        this.date = Calendar.getInstance();
        long date = in.readLong();
        if (date>0) {
            this.date.setTimeInMillis(date);
        }else{
//            this.date;
        }
    }

    public static final Parcelable.Creator<SearchParams> CREATOR = new Parcelable.Creator<SearchParams>() {
        public SearchParams createFromParcel(Parcel in) {
            return new SearchParams(in);
        }
        public SearchParams[] newArray(int size) {
            return new SearchParams[size];
        }
    };
}