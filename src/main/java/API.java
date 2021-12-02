import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.javanet.NetHttpTransport;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.*;


public class API {

    public String getWebPage(String url) throws IOException, IOException {
        HttpRequestFactory requestFactory = new NetHttpTransport().createRequestFactory();
        HttpRequest request = requestFactory.buildGetRequest(new GenericUrl(url));
        String response = request.execute().parseAsString();

        System.out.println(response);
        return response;
    }

    public ArrayList<Map<String, String>> getJson(String response) {

        Map<String, String> values = new HashMap<String, String>();
        ArrayList<Map<String, String>> data = new ArrayList<Map<String, String>>();

        try {
            JSONObject obj = new JSONObject(response);

            String mData = obj.getJSONObject("Time Series (5min)").toString();

            JSONObject objtwo = new JSONObject(mData);

            Iterator<?> keys = objtwo.keys();

            while (keys.hasNext()) {
                String key = (String) keys.next();
                values.put("date", key);
                values.put("open", objtwo.getJSONObject(key).getString("1. open"));
                values.put("high", objtwo.getJSONObject(key).getString("2. high"));
                values.put("low", objtwo.getJSONObject(key).getString("3. low"));
                values.put("close", objtwo.getJSONObject(key).getString("4. close"));
                values.put("volume", objtwo.getJSONObject(key).getString("5. volume"));
                data.add(values);
                values = new HashMap<String, String>();
            }

        } catch (Exception e) {
            System.out.println(e);
        }

        return data;
    }

}

