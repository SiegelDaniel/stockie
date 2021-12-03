import java.io.IOException;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.javanet.NetHttpTransport;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.*;


public class API {

    /**
        Method to return the result of the API call.
     */

    public String getWebPage(String url) throws IOException, IOException {
        HttpRequestFactory requestFactory = new NetHttpTransport().createRequestFactory();
        HttpRequest request = requestFactory.buildGetRequest(new GenericUrl(url));
        String response = request.execute().parseAsString();

      // System.out.println(response);
        return response;
    }

    /**
        Method to get a structured Arraylist of all entries of the API call
     */

    public ArrayList<Map<String, String>> getJson(String response) {

        // Create a Hashmap to store the individual values
        Map<String, String> values = new HashMap<String, String>();

        // Create ArrayList with the type of hashmap
        ArrayList<Map<String, String>> data = new ArrayList<Map<String, String>>();

        try {
            //Create an JSONObject from the result.
            JSONObject obj = new JSONObject(response);

            // Dive into the structure of the JSON
            String mData = obj.getJSONObject("Time Series (5min)").toString();


            JSONObject objtwo = new JSONObject(mData);

            Iterator<?> keys = objtwo.keys();

            /*
                loop by the API call to save the individual values in the hashmap and then in the Arraylist
             */
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

