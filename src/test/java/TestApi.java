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

    @Test
    public void validate_get_health_endpoint() {
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
                log().all().
                assertThat().statusCode(equalTo(200)).
                assertThat().body("status", equalTo(jsonObject.get("status")));
    }

    @Test
    public void validate_post_health_endpoint() {
        given().
            log().all().
        when().
            post("/").
        then().
            log().all().
            assertThat().statusCode(equalTo(201)).
            assertThat().header("Location", String.format("http://localhost:%d/1", RestAssured.port));
    }

    @After
    public void teardown() {
        this.appServer.stop();
    }
}