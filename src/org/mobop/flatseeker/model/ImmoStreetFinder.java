package org.mobop.flatseeker.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class ImmoStreetFinder extends FlatFinder {

    static final String charset = "UTF-8";

    enum AreaType {
        Commune, // AreaTypeId: "Comm" -> (AreaId, AreaIdAgregate) // d824b5d7-2071-4a07-a51a-44e34a2c362e
        Localite, // AreaTypeId: "Loca" -> (LocationId, InterfaceLocationID) // f5812b35-e952-4e3e-a3f1-b0d678616826
        Region, //  AreaTypeId: "Regi" -> (AreaId, AreaIdAgregate)
        District, // AreaTypeId: "Dist" -> (AreaId, AreaIdAgregate) // 987305db-8784-4bcf-acec-065cc52170a3
    }

    static class Location {
        public final AreaType areaType;
        public final String id;
        public Location(AreaType areaType, String id) {
            this.areaType = areaType;
            this.id = id;
        }
        public Location(String areaType, String id) {
            this.areaType =
                areaType.equals("Loca") ? AreaType.Localite :
                areaType.equals("Regi") ? AreaType.Region :
                areaType.equals("Dist") ? AreaType.District :
                                          AreaType.Commune;
            this.id = id;
        }
        public String toString() {
            return String.format("(%s, %s)", this.areaType, this.id);
        }

        public String getKeyArea1() {
            return this.areaType == AreaType.Localite ? "LocationId" : "AreaId";
        }

        public String getKeyArea2() {
            return this.areaType == AreaType.Localite ? "InterfaceLocationID" : "AreaIdAgregate";
        }
    }

    private Location getLocation(String location) throws IOException {

        String trimmedLocation = location.trim();

        String url = "http://www.immostreet.ch/fr/SearchEngine/AutoCompleteLocationsKendo/00000000-0000-0000-0000-000000000000/40";
        Connection connection = Jsoup.connect(url);

        //connection.header();
        connection.data(
                "countryId", "446931c9-7efe-440d-b674-24b8659087a7", // Switzerland.
                "text", trimmedLocation,
                "filter[logic]", "and",
                "filter[filters][0][value]", trimmedLocation,
                "filter[filters][0][operator]", "startswith",
                "filter[filters][0][field]", "Text",
                "filter[filters][0][ignoreCase]", "true"
        );

        Document doc = connection.get();
        String jsonStr = doc.getElementById("jsonData").html();

        Log.d(this.getClass().toString(), jsonStr);

        JSONArray jsonResults = (JSONArray) JSONValue.parse(jsonStr);
        if (jsonResults.isEmpty())
            return null;

        JSONObject firstResult = (JSONObject)jsonResults.get(0);

        String id = (String)firstResult.get("Value");
        String type = (String)firstResult.get("AreaTypeId");

        return new Location(type, id);
    }

    @Override
    public Collection<Flat> Find(SearchParams params) {
        try {
            // URLEncoder.encode(param1, charset

            String url = "http://www.immostreet.ch/fr/SearchEngine/Louer/Suisse/Appartement";
            Connection connection = Jsoup.connect(url);

            Location loc = this.getLocation(params.city);
            Log.d(this.getClass().toString(), loc.toString());

            connection.data(
                    loc.getKeyArea1(), loc.id,
                    loc.getKeyArea2(), loc.id,
                    "PropertySubTypeGroupID", "1,2",
                    "CurrencyID", "CHF",
                    "NumberOfRoomsMin", params.numberOfRooms.from.toString(),
                    "SurfaceLivibleMin", params.size.from.toString(),
                    "MonthlyRentMin", params.price.from.toString(),
                    "MonthlyRentMax", params.price.to.toString()
                    // AvailableFromMin, ...
            );

            Document doc = connection.get();

            Log.d(this.getClass().toString(), doc.toString());

            return new ArrayList<Flat>();
        } catch (Exception e) {
            Log.e(this.getClass().toString(), e.toString());
            return new ArrayList<Flat>();
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    private ImmoStreetFinder(Parcel in) {
    }

    public static final Parcelable.Creator<ImmoStreetFinder> CREATOR = new Parcelable.Creator<ImmoStreetFinder>() {
        public ImmoStreetFinder createFromParcel(Parcel in) {
            return new ImmoStreetFinder(in);
        }

        public ImmoStreetFinder[] newArray(int size) {
            return new ImmoStreetFinder[size];
        }
    };
}
