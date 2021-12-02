import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.javanet.NetHttpTransport;
import java.io.IOException;
import java.util.Iterator;

import org.json.*;
public class API {


    private ObjectMapper objectMapper = new ObjectMapper();

    public JsonNode getWebPage(String url) throws IOException, IOException {
        HttpRequestFactory requestFactory = new NetHttpTransport().createRequestFactory();
        HttpRequest request = requestFactory.buildGetRequest(new GenericUrl(url));
        String response = request.execute().parseAsString();

        try {
            JSONObject obj = new JSONObject(response);

            String mData = obj.getJSONObject("Time Series (5min)").toString();

            JSONObject objtwo = new JSONObject(mData.trim());

            Iterator<?> keys = objtwo.keys();

            while (keys.hasNext()) {
                String key = (String) keys.next();
                System.out.println(objtwo.getJSONObject(key).getString("3. low"));
                System.out.println("\n");
            }






        } catch (Exception e) {
            //TODO: handle exception
            System.out.println(e);
        }


        //System.out.println(response);
        return objectMapper.readValue(response, JsonNode.class);
    }

}