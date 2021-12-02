import java.io.IOException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.javanet.NetHttpTransport;

import java.util.ArrayList;
import java.util.Iterator;
import org.json.*;


public class API {

    ArrayList<String> open = new ArrayList<String>();
    ArrayList<String> high = new ArrayList<String>();
    ArrayList<String> low = new ArrayList<String>();
    ArrayList<String> close = new ArrayList<String>();
    ArrayList<String> volume = new ArrayList<String>();

    public String getWebPage(String url) throws IOException, IOException {
        HttpRequestFactory requestFactory = new NetHttpTransport().createRequestFactory();
        HttpRequest request = requestFactory.buildGetRequest(new GenericUrl(url));
        String response = request.execute().parseAsString();

        System.out.println(response);
        return response;
    }

    public ArrayList<String> getJson(String response, String value) {

        ArrayList<String> values = new ArrayList<String>();

        try {
            JSONObject obj = new JSONObject(response);

            String mData = obj.getJSONObject("Time Series (5min)").toString();

            JSONObject objtwo = new JSONObject(mData);

            Iterator<?> keys = objtwo.keys();



            while (keys.hasNext()) {
                String key = (String) keys.next();
                System.out.println(objtwo.getJSONObject(key).getString("3. low"));
                values.add(objtwo.getJSONObject(key).getString(value));
                 System.out.println("\n");
            }



        } catch (Exception e) {
            //TODO: handle exception
            System.out.println(e);
        }

       // System.out.println(values.toString());

        return values;
    }

    public void setAllArrayLists(String response){

        this.open = getJson(response, "1. open");
        this.high = getJson(response, "2. high");
        this.low = getJson(response, "3. low");
        this.close = getJson(response, "4. close");
        this.volume = getJson(response, "5. volume");

    }

    public ArrayList<String> getOpen() {
        return this.open;
    }

    public ArrayList<String> getHigh() {
        return this.high;
    }

    public ArrayList<String> getLow() {
        return this.low;
    }

    public ArrayList<String> getClose() {
        return this.close;
    }

    public ArrayList<String> getVolume() {
        return this.volume;
    }

}

