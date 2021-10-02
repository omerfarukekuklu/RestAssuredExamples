import fileManager.PayloadManager;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SumValidation {
    @Test
    public void sumOfCourses()
    {
        //Verify if Sum of all Course prices matches with Purchase Amount
        int price, copies, sum = 0;
        JsonPath js=new JsonPath(PayloadManager.getCoursePriceBody());
        int count=	js.getInt("courses.size()");
        for(int i=0;i<count;i++)
        {
            price = js.getInt("courses["+i+"].price");
            copies = js.getInt("courses["+i+"].copies");
            sum += price * copies;

        }
        System.out.println(sum);
        int purchaseAmount =js.getInt("dashboard.purchaseAmount");
        Assert.assertEquals(sum, purchaseAmount);

    }
}
