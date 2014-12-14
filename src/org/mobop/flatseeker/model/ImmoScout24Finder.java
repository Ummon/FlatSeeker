package org.mobop.flatseeker.model;

import android.util.Log;

import com.google.gson.Gson;

import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

public class ImmoScout24Finder extends FlatFinder {

    static final String CHARSET = "UTF-8";
    static final int TIMEOUT_REQUEST = 6000; // [ms]. (6 s).

    public ImmoScout24Finder() {}

    static class JsonRequestLocation {
        public String term;
        public String ticket;
        public String lang;
        public String[] types;

        public JsonRequestLocation(String term) {
            this.term = term;
            this.ticket = "1";
            this.lang = "fr";
            this.types = new String[] {};
        }
    }

    static class JsonResultLocation {
        public JsonResultLocationItem[] data;
    }

    static class JsonResultLocationItem {
        public String label;
        public String value;
        public String type;
    }

    static class JsonResultList {
        public JsonResultListItem[] parts;
    }

    static class JsonResultListItem {
        public String markup;
    }

    /**
     * Retrieve the location ID from an arbitrary location.
     */
    private int getLocationID(String location) throws IOException {

        final String trimmedLocation = location.trim().toLowerCase();

        String urlStr = "http://www.immoscout24.ch/public/search/getlocations";

        // Build the body of the POST message.
        Gson gson = new Gson();
        String messageBody = gson.toJson(new JsonRequestLocation(trimmedLocation));

        URL url = new URL(urlStr);
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        connection.setRequestMethod("POST");
        connection.setConnectTimeout(TIMEOUT_REQUEST);

        connection.addRequestProperty("Host", "www.immoscout24.ch");
        connection.addRequestProperty("User-Agent", "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:34.0) Gecko/20100101 Firefox/34.0");
        connection.addRequestProperty("Accept", "application/json, text/javascript, */*; q=0.01");
        connection.addRequestProperty("Accept-Language", "en-US,en;q=0.5");
        connection.addRequestProperty("Content-Type", "application/json; charset=utf-8");
        connection.addRequestProperty("Content-Length", Integer.toString(messageBody.getBytes().length));
        connection.addRequestProperty("X-Requested-With", "XMLHttpRequest");
        connection.addRequestProperty("Pragma", "no-cache");
        connection.addRequestProperty("Cache-Control", "no-cache");

        DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
        wr.writeBytes(messageBody);
        wr.flush();
        wr.close();

        // Get the whole body as a string.
        InputStream is = connection.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null)
            sb.append(line);
        bufferedReader.close();
        String jsonStr = sb.toString();

        // Parse the page as JSON.
        JsonResultLocation result = gson.fromJson(jsonStr, JsonResultLocation.class);

        if (result.data.length > 0)
            return Integer.parseInt(result.data[0].value);
        return 0;
    }

    /**
     * Retrieve the list of URLs corresponding to the given criteria.
     */
    private List<String> getUrls(final SearchParams params, final int locationID) throws IOException {

        String urlStr = "http://www.immoscout24.ch/fr/recherche/?";
        urlStr += URLEncodedUtils.format(
                new ArrayList<BasicNameValuePair>() {{
                    add(new BasicNameValuePair("s", "2"));
                    add(new BasicNameValuePair("t", "1"));
                    add(new BasicNameValuePair("l", Integer.toString(locationID)));
                    add(new BasicNameValuePair("r", Integer.toString(params.radius)));
                    add(new BasicNameValuePair("pf", Integer.toString(params.price.from / 100) + "h"));
                    add(new BasicNameValuePair("pt",  Integer.toString(params.price.to / 100) + "h"));
                    add(new BasicNameValuePair("nrf", Double.toString(params.numberOfRooms.from)));
                    add(new BasicNameValuePair("nrt", Double.toString(params.numberOfRooms.to)));
                    add(new BasicNameValuePair("slf", Integer.toString(params.size.from)));
                    add(new BasicNameValuePair("slt", Integer.toString(params.size.to)));
                }},
                CHARSET
        );

        // The body of the POST message (Form data).
        String messageBody = URLEncodedUtils.format(
                new ArrayList<BasicNameValuePair>() {{
                    add(new BasicNameValuePair("renderMode", "1"));
                    add(new BasicNameValuePair("scrollPosP", "0"));
                    add(new BasicNameValuePair("scrollPosA", "0"));
                    add(new BasicNameValuePair("scrollPosQ", ""));
                }},
                CHARSET
        );

        URL url = new URL(urlStr);
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        connection.setRequestMethod("POST");
        connection.setConnectTimeout(TIMEOUT_REQUEST);

        connection.addRequestProperty("Host", "www.immoscout24.ch");
        connection.addRequestProperty("User-Agent", "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:34.0) Gecko/20100101 Firefox/34.0");
        connection.addRequestProperty("Accept", "application/json, text/javascript, */*; q=0.01");
        connection.addRequestProperty("Accept-Language", "en-US,en;q=0.5");
        connection.addRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        connection.addRequestProperty("Content-Length", Integer.toString(messageBody.getBytes().length));
        connection.addRequestProperty("X-Requested-By", "jquery.partLoad");
        connection.addRequestProperty("X-Requested-With", "XMLHttpRequest");
        connection.addRequestProperty("Pragma", "no-cache");
        connection.addRequestProperty("Cache-Control", "no-cache");

        DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
        wr.writeBytes(messageBody);
        wr.flush();
        wr.close();

        // Get the whole body as a string.
        InputStream is = connection.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null)
            sb.append(line);
        bufferedReader.close();
        String jsonStr = sb.toString();

        // Parse the page as JSON.
        Gson gson = new Gson();
        JsonResultList result = gson.fromJson(jsonStr, JsonResultList.class);

        List<String> urlsResult = new ArrayList<String>();

        if (result.parts.length > 0) {
            Document doc = Jsoup.parse(result.parts[0].markup);
            Elements elements = doc.select(".item-title");

            for (Element element : elements) {
                urlsResult.add("http://www.immoscout24.ch" + element.attr("href"));
            }
        }

        return urlsResult;
    }

    /**
     * Load the given URL and parse the result as a flat.
     */
    private Flat getFlat(String url) throws IOException {
        Connection connection = Jsoup.connect(url);
        connection.timeout(TIMEOUT_REQUEST);

        connection.header("Host", "www.immoscout24.ch");
        connection.header("User-Agent", "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:34.0) Gecko/20100101 Firefox/34.0");
        connection.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        connection.header("Accept-Language", "en-US,en;q=0.5");

        Document doc = connection.get();
        Elements address = doc.select("td.adr > div:nth-child(1)");
        Elements zip = doc.select("span.postal-code:nth-child(1)");
        Elements city = doc.select("span.locality:nth-child(2)");
        Elements numberOfRooms = doc.select(".base-data > tbody:nth-child(1) > tr:nth-child(3) > td:nth-child(2)");
        Elements size = doc.select(".base-data > tbody:nth-child(1) > tr:nth-child(4) > td:nth-child(2)");
        Elements dateFree = doc.select(".base-data > tbody:nth-child(1) > tr:nth-child(5) > td:nth-child(2)");
        Elements additionalExpenses = doc.select(".prices > span:nth-child(2) > span:nth-child(2)");
        Elements price = doc.select(".prices > span:nth-child(1) > span:nth-child(2)");
        Elements floor = doc.select("div.content-section:nth-child(5) > table:nth-child(2) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(2)");
        Elements contact = doc.select(".ref");
        Elements estateAgent = doc.select(".lister > div:nth-child(1)");

        // TODO.
        return new Flat(
            4.5,
            100,
            2000,
            220,
            Calendar.getInstance().getTime(),
            "Neuchatel",
            "Battieux",
            18,
            3,
            "Gérance",
            "078 123 43 23"
        );
    }

    @Override
    public Collection<Flat> Find(SearchParams params) {
        try {
            int locationID = this.getLocationID(params.city);

            List<String> urls = this.getUrls(params, locationID);

            List<Flat> flatsResult = new ArrayList<Flat>();

            if (!urls.isEmpty())
                flatsResult.add(this.getFlat(urls.get(0)));

            return flatsResult;
        } catch (Exception e) {
            Log.e(this.getClass().toString(), e.toString());
            return new ArrayList<Flat>();
        }
    }
}
