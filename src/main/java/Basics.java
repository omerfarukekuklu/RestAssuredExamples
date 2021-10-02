import fileManager.JsonManager;
import fileManager.PayloadManager;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class Basics {
    public static void main(String[] args) {
        //Add place-> Update Place with New Address -> Get Place to validate if New address is present in response
        RestAssured.baseURI = "https://rahulshettyacademy.com";

        //Add Place
        String addPlaceResponse = given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
                .body(PayloadManager.getAddPlaceBody())
                .when().post("maps/api/place/add/json")
                .then().assertThat().statusCode(200).body("scope",equalTo("APP"))
                .header("Server", "Apache/2.4.18 (Ubuntu)").extract().response().asPrettyString();

        System.out.println(addPlaceResponse);
        JsonPath js = JsonManager.rawToJson(addPlaceResponse);
        String placeId = js.get("place_id");
        System.out.println("Place Id: " + placeId);

        //Update Place Address
        String newAddress = "70 Summer walk";
        given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
                .body(PayloadManager.getUpdatePlaceBody(placeId, newAddress))
                .when().put("maps/api/place/update/json")
                .then().assertThat().log().all().statusCode(200).body("msg", equalTo("Address successfully updated"));

        //Get Place and verify whether the address is updated correctly
        String getPlaceResponse = given().log().all().queryParam("key", "qaclick123")
                .queryParam("place_id", placeId)
                .when().get("maps/api/place/get/json")
                .then().log().all().assertThat().statusCode(200).extract().response().asPrettyString();

        JsonPath js1 = JsonManager.rawToJson(getPlaceResponse);
        String updatedAddress = js1.getString("address");
        System.out.println("Updated address :" + updatedAddress);
        Assert.assertEquals(updatedAddress, newAddress, "Adresses do not match");
    }
}
