import fileManager.payloadManager;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class Basics {
    public static void main(String[] args) {
        RestAssured.baseURI = "https://rahulshettyacademy.com";
        given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
                .body(payloadManager.getAddPlaceBody())
                .when().post("maps/api/place/add/json")
                .then().log().all().assertThat().statusCode(200).body("scope",equalTo("APP"))
                .header("Server", "Apache/2.4.18 (Ubuntu)");
    }
}
