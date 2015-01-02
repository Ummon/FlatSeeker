package org.mobop.flatseeker.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class StubFinder extends FlatFinder {
    public StubFinder() {
    }

    public Collection<Flat> Find(SearchParams params) {
        ArrayList<Flat> flats = new ArrayList<Flat>();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

        try {
            if (params.city.toLowerCase().equals("neuchatel")) {
                flats.add(new Flat(3.0, 67, 1200, 120, format.parse("01/03/2015"), "Neuchâtel", "Faubourg de l'Hopital", 2, 2, "Robert Crot", "021/123 45 78"));
                flats.add(new Flat(3.5, 70, 1350, 120, format.parse("01/04/2015"), "Neuchâtel", "Avenue des Terreaux", 4, 1, "Robert Crot", "021/123 45 78"));
                flats.add(new Flat(5.0, 130, 2100, 120, format.parse("01/02/2015"), "Neuchâtel", "Rue des Battieux", 7, 5, "Robert Crot", "021/123 45 78"));
            } else if (params.city.toLowerCase().equals("lausanne")) {
                flats.add(new Flat(5.0, 170, 2400, 280, format.parse("15/03/2015"), "Lausanne", "Rue St-Roch", 1, 3, "Robert Crot", "021/123 45 78"));
                flats.add(new Flat(2.5, 57, 920, 60, format.parse("01/04/2015"), "Lausanne", "Avenue du Théatre", 56, 3, "Robert Crot", "021/123 45 78"));
                flats.add(new Flat(1.5, 43, 650, 40, format.parse("15/02/2015"), "Lausanne", "Rue Caroline", 6, 2, "Robert Crot", "021/123 45 78"));
                flats.add(new Flat(3.0, 62, 1400, 180, format.parse("01/02/2015"), "Neuchâtel", "Rue Centrale", 24, 1, "Robert Crot", "021/123 45 78"));
            } else if (params.city.toLowerCase().equals("geneve")) {
                flats.add(new Flat(3.5, 67, 1700, 210, format.parse("08/03/2015"), "Genêve", "Rue des Alpes", 3, 4, "Robert Crot", "021/123 45 78"));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return flats;
    }
}
