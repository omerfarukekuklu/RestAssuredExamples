import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class OauthTest {
    @Test
    public void oAuth2Test(){
        //Get Authorization COde
        String url = "";
        String code = url.split("code=")[1].split("&")[0];
        System.out.println("\nCode: " + code + "\n");

        //Get Access Token
        String accessTokenResponse = given().urlEncodingEnabled(false).queryParams("code", code,
                 "client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com",
                "client_secret", "erZOWM9g3UtwNRj340YYaK_W",
                "redirect_uri", "https://rahulshettyacademy.com/getCourse.php",
                "grant_type", "authorization_code")
                .when().log().all().post("https://www.googleapis.com/oauth2/v4/token").asString();

        JsonPath js = new JsonPath(accessTokenResponse);
        String accessToken = js.getString("access_token");
        System.out.println("\nAccess Token: " + accessToken + "\n");

        // Actual Request - Get courses
        String response = given().queryParam("access_token", accessToken)
                .when().log().all().get("https://rahulshettyacademy.com/getCourse.php").asString();
        System.out.println(response);
    }
}
