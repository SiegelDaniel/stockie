import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static spark.Spark.stop;

public class AssetAPITest {


    @BeforeAll
    static void setUp() {
        Stockie.main(new String[0]);
    }

    @AfterAll
    static void tearDown() {
        //Spark Java stoppen
        stop();
    }

    private HttpResponse<String> getDescriptionRequest(String id) throws IOException, InterruptedException{
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/assets/" + id + "/descr")).GET().build();
        return client.send(request, BodyHandlers.ofString());
    }

    private HttpResponse<String> getDaysRequest(String id, String days) throws IOException, InterruptedException{
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/assets/" + id + "/price/days/" + days)).GET().build();
        return client.send(request, BodyHandlers.ofString());
    }

    private HttpResponse<String> getAllRequest(String id) throws IOException, InterruptedException{
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/assets/" + id +"/price/all")).GET().build();
        return client.send(request, BodyHandlers.ofString());
    }

    private HttpResponse<String> getLastRequest(String id) throws IOException, InterruptedException{
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/assets/"+id+"/price/last")).GET().build();
        return client.send(request, BodyHandlers.ofString());
    }

    @Test
    public void getDescriptionTest() throws IOException, InterruptedException{

        //Normalfall, nicht vorhandener String, String der SQL beeinflussen koennte
        HttpResponse<String> normal = getDescriptionRequest("AAPL");
        HttpResponse<String> wrongID = getDescriptionRequest("BBPL");

        System.out.println(normal.toString());
        assertEquals(200, normal.statusCode());
        assertEquals(404, wrongID.statusCode());
    }

    @Test
    public void getDaysTest() throws IOException, InterruptedException{

        //Normalfall, nicht vorhandenes Asset, Tangshan die die SQL-Abfrage knacken könnte
        HttpResponse<String> working = getDaysRequest("AAPL","1");
        HttpResponse<String> wrongID = getDaysRequest("BBPL", "2");
        HttpResponse<String> tooManyDays = getDaysRequest("AAPL","11012022");

        assertEquals(404,wrongID.statusCode());
        assertEquals(200,working.statusCode());

        assertEquals(404,tooManyDays.statusCode());
    }

    @Test
    public void getLastTest() throws IOException, InterruptedException{

        HttpResponse<String> working = getLastRequest("AAPL");
        assertEquals(200,working.statusCode());

        HttpResponse<String> wrongAsset = getLastRequest("BBPL");
        assertEquals(404, wrongAsset.statusCode());
    }

    @Test
    public void getAllTest() throws IOException, InterruptedException{

        HttpResponse<String> working = getAllRequest("AAPL");
        HttpResponse<String> wrongAsset = getAllRequest("BBPL");

        assertEquals(200, working.statusCode());
        assertEquals(404, wrongAsset.statusCode());
    }


}
