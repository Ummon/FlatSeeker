package org.mobop.flatseeker.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.*;

public class Flat implements Parcelable, Serializable {
    public final double numberOfRooms;
    public final int size; // [m²].

    public final int price;
    public final int additionalExpenses;

    public final Date freeFrom;

    public final String city;
    public final String street;
    public final int number;
    public final int floor; // 0 is the first floor.

    public final String estateAgent; // Gérance.
    public final String contact; // Mainly a phone number.

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
            int floor,
            String estateAgent,
            String contact
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
        this.estateAgent = estateAgent;
        this.contact = contact;
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
        dest.writeDouble(this.numberOfRooms);
        dest.writeInt(this.size);
        dest.writeInt(this.price);
        dest.writeInt(this.additionalExpenses);
        dest.writeSerializable(this.freeFrom);
        dest.writeString(this.city);
        dest.writeString(this.street);
        dest.writeInt(this.number);
        dest.writeInt(this.floor);
        dest.writeString(this.estateAgent);
        dest.writeString(this.contact);
        dest.writeString(this.note);
    }

    private Flat(Parcel in) {
        this.numberOfRooms = in.readDouble();
        this.size = in.readInt();
        this.price = in.readInt();
        this.additionalExpenses = in.readInt();
        this.freeFrom = (Date)in.readSerializable();
        this.city = in.readString();
        this.street = in.readString();
        this.number = in.readInt();
        this.floor = in.readInt();
        this.estateAgent = in.readString();
        this.contact = in.readString();
        this.note = in.readString();
    }

    public static final Parcelable.Creator<Flat> CREATOR = new Parcelable.Creator<Flat>() {
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
                this.numberOfRooms == that.numberOfRooms &&
                this.size == that.size &&
                this.price == that.price &&
                this.additionalExpenses == that.additionalExpenses &&
                this.freeFrom.equals(that.freeFrom) &&
                this.number == that.number &&
                this.floor == that.floor &&
                this.city.equals(that.city) &&
                this.street.equals(that.street) &&
                this.estateAgent.equals(that.estateAgent) &&
                this.contact.equals(that.contact);
    }

}
