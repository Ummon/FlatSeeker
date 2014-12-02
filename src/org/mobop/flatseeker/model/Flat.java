package org.mobop.flatseeker.model;

import java.util.*;

public class Flat {
    double numberOfRooms;
    int size; // [m²].

    int price;
    int additionalExpenses;

    Date freeFrom;

    String city;
    String street;
    int number;
    int floor; // 0 is the first floor.

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
}
