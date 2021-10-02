package fileManager;

import io.restassured.path.json.JsonPath;

public class JsonManager {
    public static JsonPath rawToJson(String string){
        return new JsonPath(string);
    }
}
