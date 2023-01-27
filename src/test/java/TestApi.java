import io.restassured.RestAssured;
import io.github.anthonygauthier.AppServer;
import org.json.JSONObject;
import org.junit.*;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class TestApi {
    private AppServer appServer = new AppServer();

    @Before
    public void setup() {
        RestAssured.baseURI = "http://localhost/v1";
        RestAssured.port = 3000;
        this.appServer.start(); // This line is only necessary because we're testing the Java app in this repo
                                // If this test suite was to test an app outside the Java project then we'd omit this
    }

    @Test public void
    validate_health_endpoint() {
        // Set up the JSON object we're expecting
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status", "healthy");

        given().
            log().all(). // This allows us to output the request in the console for debugging purposes
            header("X-Test", "1").
            header("Authorization", "Bearer 123").
        when().
            get("/").
        then().
            assertThat().
                log().all().statusCode(200). // This outputs the body of the response given a status code 200
                body("status", equalTo(jsonObject.get("status")));
    }

    @After
    public void teardown() {
        this.appServer.stop();
    }
}