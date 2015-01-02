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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ImmoScout24Finder extends FlatFinder {

    static final String CHARSET = "UTF-8";
    static final int TIMEOUT_REQUEST = 6000; // [ms]. (6 s).
    static final int MAX_NUMBER_FLAT_RESULT = 3; // 3 is for testing.
    static final String URL = "www.immoscout24.ch";

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

        String urlStr = String.format("http://%s/public/search/getlocations", URL);

        // Build the body of the POST message.
        Gson gson = new Gson();
        String messageBody = gson.toJson(new JsonRequestLocation(trimmedLocation));

        URL url = new URL(urlStr);
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        connection.setRequestMethod("POST");
        connection.setConnectTimeout(TIMEOUT_REQUEST);

        connection.addRequestProperty("Host", URL);
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

        String urlStr = String.format("http://%s/fr/recherche/?", URL);
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

        connection.addRequestProperty("Host", URL);
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
                urlsResult.add("http://" + URL + element.attr("href"));
            }
        }

        return urlsResult;
    }

    static Pattern addressPattern = Pattern.compile("^\\s*(.*?)(\\d*)\\s*$");
    static Pattern numberPattern = Pattern.compile("^(?:.*?)(\\d+).*$");
    // static Pattern numberPattern = Pattern.compile("^\\s*(\\d+)\\s*$"); // Old version, a bit less powerful.
    static Pattern freeNowPattern = Pattern.compile("^\\s*Immédiatement\\s*");
    static SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

    /**
     * Load the given URL and parse the result as a flat.
     */
    private Flat getFlat(String url) throws IOException {
        Connection connection = Jsoup.connect(url);
        connection.timeout(TIMEOUT_REQUEST);

        connection.header("Host", URL);
        connection.header("User-Agent", "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:34.0) Gecko/20100101 Firefox/34.0");
        connection.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        connection.header("Accept-Language", "en-US,en;q=0.5");

        Document doc = connection.get();

        String addressHtml = doc.select("td.adr > div:nth-child(1)").html();
        Matcher addressMatcher = addressPattern.matcher(addressHtml);
        if (!addressMatcher.matches() || addressMatcher.groupCount() < 2)
            return null;
        String street = addressMatcher.group(1);
        String streetNumber = addressMatcher.group(2);

        String zipHtml = doc.select("span.postal-code:nth-child(1)").html();
        Matcher zipMatcher = numberPattern.matcher(zipHtml);
        String zip = "0";
        if (zipMatcher.matches() && zipMatcher.groupCount() == 1)
            zip = zipMatcher.group(1);

        String city = doc.select("span.locality:nth-child(2)").html().trim();

        String numberOfRoomsHtml = doc.select(".base-data > tbody:nth-child(1) > tr:nth-child(3) > td:nth-child(2)").html();
        Matcher numberOfRoomsMatcher = numberPattern.matcher(numberOfRoomsHtml);
        String numberOfRooms = "0";
        if (numberOfRoomsMatcher.matches() && numberOfRoomsMatcher.groupCount() == 1)
            numberOfRooms = numberOfRoomsMatcher.group(1);

        String sizeHtml = doc.select(".base-data > tbody:nth-child(1) > tr:nth-child(4) > td:nth-child(2)").html();
        Matcher sizeMatcher = numberPattern.matcher(sizeHtml);
        String size = "0";
        if (sizeMatcher.matches() && sizeMatcher.groupCount() == 1)
            size = sizeMatcher.group(1);

        String dateFreeHtml = doc.select(".base-data > tbody:nth-child(1) > tr:nth-child(5) > td:nth-child(2)").html();
        Date dateFree = null;
        if (!freeNowPattern.matcher(dateFreeHtml).matches())
            try {
                dateFree = dateFormat.parse(dateFreeHtml);
            } catch (ParseException e) {
                Log.e(this.getClass().toString(), e.toString());
            }

        String additionalExpensesHtml = doc.select(".prices > span:nth-child(2) > span:nth-child(2)").html().replaceAll("'", "");
        Matcher additionalExpensesMatcher = numberPattern.matcher(additionalExpensesHtml);
        String additionalExpenses = "0";
        if (additionalExpensesMatcher.matches() && additionalExpensesMatcher.groupCount() == 1)
            additionalExpenses = additionalExpensesMatcher.group(1);

        String priceHtml = doc.select(".prices > span:nth-child(1) > span:nth-child(2)").html().replaceAll("'", "");
        Matcher priceMatcher = numberPattern.matcher(priceHtml);
        String price = "0";
        if (priceMatcher.matches() && priceMatcher.groupCount() == 1)
            price = priceMatcher.group(1);

        String floorHtml = doc.select("div.content-section:nth-child(5) > table:nth-child(2) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(2)").html();
        Matcher floorMatcher = numberPattern.matcher(floorHtml);
        String floor = "0";
        if (floorMatcher.matches() && floorMatcher.groupCount() == 1)
            floor = floorMatcher.group(1);

        String contact = doc.select(".ref").html().trim();
        String estateAgent = doc.select(".lister > div:nth-child(1)").html().trim();

        return new Flat(
            Double.parseDouble(numberOfRooms),
            Integer.parseInt(size),
            Integer.parseInt(price),
            Integer.parseInt(additionalExpenses),
            dateFree,
            city,
            street,
            Integer.parseInt(streetNumber),
            Integer.parseInt(floor),
            estateAgent,
            contact
        );
    }

    @Override
    public Collection<Flat> Find(SearchParams params) {
        try {
            int locationID = this.getLocationID(params.city);

            List<String> urls = this.getUrls(params, locationID);

            List<Flat> flatsResult = new ArrayList<Flat>();

            // Here it is possible to parallelize the requests with http://developer.android.com/reference/java/util/concurrent/ExecutorService.html.
            int i = 0;
            for (String url : urls) {
                Flat flat = this.getFlat(url);
                if (flat != null) {
                    flatsResult.add(flat);
                    i++;
                }
                if (i >= MAX_NUMBER_FLAT_RESULT)
                    break;
            }

            return flatsResult;
        } catch (Exception e) {
            Log.e(this.getClass().toString(), e.toString());
            return new ArrayList<Flat>();
        }
    }
}
