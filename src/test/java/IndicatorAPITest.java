import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static spark.Spark.stop;

public class IndicatorAPITest {

    @BeforeAll
    static void setUp() {
        Stockie.main(new String[0]);
    }

    @AfterAll
    static void tearDown() {
        //Spark Java stoppen
        stop();
    }

    private HttpResponse<String> getSimpleMovingAverageRequest(String id, String days) throws IOException, InterruptedException{
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/assets/" + id + "/indicators/sma/days/"+days)).GET().build();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    @Test
    public void getSMATest() throws IOException, InterruptedException{
        HttpResponse<String> working = getSimpleMovingAverageRequest("AAPL","20");
        HttpResponse<String> wrongAsset = getSimpleMovingAverageRequest("BBPL","20");
        HttpResponse<String> wrongDays = getSimpleMovingAverageRequest("AAPL","-8");

        assertEquals(200,working.statusCode());
        assertEquals(404,wrongAsset.statusCode());
        assertEquals(404,wrongDays.statusCode());

    }

}
