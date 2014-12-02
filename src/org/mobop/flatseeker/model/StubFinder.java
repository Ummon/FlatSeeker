package org.mobop.flatseeker.model;

import java.util.*;

public class StubFinder extends FlatFinder {
    public Collection<Flat> Find(SearchParams params) {
        ArrayList<Flat> flats = new ArrayList<Flat>();
        GregorianCalendar.getInstance().set(2015, Calendar.JANUARY, 1);

        if (params.city.toLowerCase() == "neuchatel") {
            flats.add(new Flat(
                    3.0,
                    67,
                    1200,
                    120,
                    GregorianCalendar.getInstance().getTime(),
                    "Neuchâtel",
                    "Faubourg de l'Hopital",
                    14,
                    2
            ));
        }

        return flats;
    }
}
