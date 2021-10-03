import fileManager.JsonManager;
import fileManager.PayloadManager;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.*;

public class LibraryApiTests {

    @BeforeTest
    public void initialize(){
        RestAssured.baseURI = "http://216.10.245.166";
    }

    @Test(dataProvider = "BooksData")
    public void testAddBook(String isbn, String aisle){
        String response = given().log().all().header("Content-Type", "application/json")
                .body(PayloadManager.getAddBookBody(isbn, aisle))
                .when().post("Library/Addbook.php")
                .then().log().all().assertThat().statusCode(200)
                .extract().response().asPrettyString();
        JsonPath js = JsonManager.rawToJson(response);
        String id = js.getString("ID");
        System.out.println(id);
    }

    @DataProvider(name = "BooksData")
    public Object[][] getData(){
        return  new Object[][]{{"asasf", "9363"}, {"uwetee","4853"}, {"yuhuasf", "8796"}};
    }

    @Test
    public void testAddBookWithExternalJsonFile(){
        String body = "";
        try {
            body = new String(Files.readAllBytes(Paths.get("C:\\Users\\Ömer Faruk\\IdeaProjects\\RestAssuredExamples\\src\\main\\resources\\addBook.json")));
        }
        catch (Exception e){
            e.printStackTrace();
        }
        String response = given().log().all().header("Content-Type", "application/json")
                .body(body)
                .when().post("Library/Addbook.php")
                .then().log().all().assertThat().statusCode(200)
                .extract().response().asPrettyString();
        JsonPath js = JsonManager.rawToJson(response);
        String id = js.getString("ID");
        System.out.println(id);
    }
}
