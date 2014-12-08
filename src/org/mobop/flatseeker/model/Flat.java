package org.mobop.flatseeker.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.*;

public class Flat implements Parcelable {
    public final double numberOfRooms;
    public final int size; // [m²].

    public final int price;
    public final int additionalExpenses;

    public final Date freeFrom;

    public final String city;
    public final String street;
    public final int number;
    public final int floor; // 0 is the first floor.

    String note;

    protected Flat(
            double numberOfRooms,
            int size,
            int price,
            int additionalExpenses,
            Date freeFrom,
            String city,
            String street,
            int number,
            int floor
            ) {
        this.numberOfRooms = numberOfRooms;
        this.size = size;
        this.price = price;
        this.additionalExpenses = additionalExpenses;
        this.freeFrom = freeFrom;
        this.city = city;
        this.street = street;
        this.number = number;
        this.floor = floor;
    }

    public String getNote() {
        return this.note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void blacklist() {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(numberOfRooms);
        dest.writeInt(size);
        dest.writeInt(price);
        dest.writeInt(additionalExpenses);
        dest.writeSerializable(freeFrom);
        dest.writeString(city);
        dest.writeString(street);
        dest.writeInt(number);
        dest.writeInt(floor);
        dest.writeString(note);
    }

    private Flat (Parcel in) {
        numberOfRooms = in.readDouble();
        size = in.readInt();
        price = in.readInt();
        additionalExpenses = in.readInt();
        freeFrom = (Date)in.readSerializable();
        city = in.readString();
        street = in.readString();
        number = in.readInt();
        floor = in.readInt();
        note = in.readString();
    }

    public static final Parcelable.Creator<Flat> CREATOR
            = new Parcelable.Creator<Flat>() {
        public Flat createFromParcel(Parcel in) {
            return new Flat(in);
        }

        public Flat[] newArray(int size) {
            return new Flat[size];
        }
    };


    public boolean equalsWithoutNote(Object aThat) {
        if (this == aThat) return true;
        if (!(aThat instanceof Flat)) return false;

        Flat that = (Flat)aThat;
        return
            ( this.numberOfRooms == that.numberOfRooms ) &&
                ( this.size == that.size ) &&
                ( this.price == that.price ) &&
                ( this.additionalExpenses == that.additionalExpenses ) &&
                ( this.freeFrom.equals(that.freeFrom) ) &&
                ( this.number == that.number ) &&
                ( this.floor == that.floor ) &&
                ( this.city.equals(that.city) ) &&
                ( this.street.equals(that.street) )
            ;
    }

}
