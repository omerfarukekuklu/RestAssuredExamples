import fileManager.JsonManager;
import fileManager.PayloadManager;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;

public class CompexJsonParseExample {
    public static void main(String[] args) {
        JsonPath js = JsonManager.rawToJson(PayloadManager.getCoursePriceBody());

        // Print no of courses
        int count = js.getInt("courses.size()");
        System.out.println("No of courses : " + count);

        // Print purchase amount
        int purchaseAmount = js.getInt("dashboard.purchaseAmount");
        System.out.println("Purchase amount : " + purchaseAmount);

        // Print title of firs course
        String titleOfFirstCourse = js.get("courses[0].title");
        System.out.println(titleOfFirstCourse);

        // Print All course titles and their respective Prices
        String courseTitle;
        int coursePrice;
        for(int i =0; i < count; i++){
            courseTitle = js.get("courses[" + i + "].title");
            coursePrice = js.get("courses[" + i + "].price");
            System.out.println((courseTitle));
            System.out.println((coursePrice));
        }
        // Print no of copies sold by RPA Course
        for(int i=0;i<count;i++)
        {
            courseTitle =js.get("courses["+i+"].title");
            if(courseTitle.equalsIgnoreCase("RPA"))
            {
                int copies=js.get("courses["+i+"].copies");
                System.out.println(copies);
                break;
            }
        }

        //Verify if Sum of all Course prices matches with Purchase Amount
        int sum = 0;
        int copies;
        for(int i=0;i<count;i++)
        {
            coursePrice = js.get("courses[" + i + "].price");
            copies = js.get("courses[" + i + "].copies");
            sum += coursePrice * copies;
        }

        System.out.println("Sum of all course prices: " + sum);
        Assert.assertEquals(purchaseAmount, sum);
    }
}