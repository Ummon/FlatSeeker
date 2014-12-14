package org.mobop.flatseeker.model;

import android.util.Log;

import com.google.gson.Gson;

import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collection;

public class ImmoStreetFinder extends FlatFinder {

    static final String CHARSET = "UTF-8";
    static final int TIMEOUT_REQUEST = 10000; // [ms]. (10 s).

    public ImmoStreetFinder() {}

    enum AreaType {
        Commune, // AreaTypeId: "Comm" -> (AreaId, AreaIdAgregate)
        Localite, // AreaTypeId: "Loca" -> (LocationId, InterfaceLocationID)
        Region, //  AreaTypeId: "Regi" -> (AreaId, AreaIdAgregate)
        District, // AreaTypeId: "Dist" -> (AreaId, AreaIdAgregate)
    }

    static class JsonItem {
        public String Text;
        public String Value;
        public String AreaTypeId;
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

    /**
     * Retrieve the location ID from an arbitrary location.
     */
    private Location getLocation(String location) throws IOException {

        final String trimmedLocation = location.trim();

        String urlStr = "http://www.immostreet.ch/fr/SearchEngine/AutoCompleteLocationsKendo/00000000-0000-0000-0000-000000000000/40?";
        urlStr += URLEncodedUtils.format(
                new ArrayList<BasicNameValuePair>() {{
                        add(new BasicNameValuePair("user", "test"));
                        add(new BasicNameValuePair("countryId", "446931c9-7efe-440d-b674-24b8659087a7")); // Switzerland.
                        add(new BasicNameValuePair("text", trimmedLocation));
                        add(new BasicNameValuePair("filter[logic]", "and"));
                        add(new BasicNameValuePair("filter[filters][0][value]", trimmedLocation));
                        add(new BasicNameValuePair("filter[filters][0][operator]", "startswith"));
                        add(new BasicNameValuePair("filter[filters][0][field]", "Text"));
                        add(new BasicNameValuePair("filter[filters][0][ignoreCase]", "true"));
                }},
                CHARSET
        );

        URL url = new URL(urlStr);
        URLConnection connection = url.openConnection();
        connection.setAllowUserInteraction(false);

        connection.addRequestProperty("Host", "www.immostreet.ch");
        connection.addRequestProperty("User-Agent", "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:34.0) Gecko/20100101 Firefox/34.0");
        connection.addRequestProperty("Accept", "*/*");
        connection.addRequestProperty("Accept-Language", "en-US,en;q=0.5");
        connection.addRequestProperty("Accept-Encoding", "gzip, deflate");
        connection.addRequestProperty("X-Requested-With", "XMLHttpRequest");

        // Get the whole body as a string.
        InputStream is = connection.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null)
            sb.append(line);
        String jsonStr = sb.toString();

        // Parse the page as JSON.
        Gson gson = new Gson();
        JsonItem[] items = gson.fromJson(jsonStr, JsonItem[].class);
        if (items.length == 0)
            return null;

        return new Location(items[1].AreaTypeId, items[0].Value);
    }

    @Override
    public Collection<Flat> Find(SearchParams params) {
        try {
            Location loc = this.getLocation(params.city);

            if (loc == null)
                return new ArrayList<Flat>();

            String url = "http://www.immostreet.ch/fr/SearchEngine/Louer/Suisse/Appartement";
            Connection connection = Jsoup.connect(url);
            connection.timeout(TIMEOUT_REQUEST);

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

            connection.header("Host", "www.immostreet.ch");
            connection.header("User-Agent", "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:34.0) Gecko/20100101 Firefox/34.0");
            connection.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
            connection.header("Accept-Language", "en-US,en;q=0.5");
            connection.header("Accept-Encoding", "gzip, deflate");
            // connection.header("Referer", "http://www.immostreet.ch/fr/SearchEngine/Louer/Suisse/Appartement?AreaId=d824b5d7-2071-4a07-a51a-44e34a2c362e&AreaIdAgregate=d824b5d7-2071-4a07-a51a-44e34a2c362e&PropertySubTypeGroupID=2&CurrencyID=CHF&NumberOfRoomsMin=3&SurfaceLivibleMin=60&MonthlyRentMin=1000&MonthlyRentMax=2000&AvailableFromMin=01.05.2015&SearchCriteriaImmoId=b8188145-da3b-2217-7301-088a6c30d307");
            // connection.header("Connection", "keep-alive");

            Document doc = connection.get();

            // TO BE FINISHED....

            return new ArrayList<Flat>();
        } catch (Exception e) {
            Log.e(this.getClass().toString(), e.toString());
            return new ArrayList<Flat>();
        }
    }
}
