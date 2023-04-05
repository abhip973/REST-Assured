package abhiLearns.apiRequests;

import abhiLearns.files.Payload;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;

public class ComplexJsonParse {
    public static void main(String[] args) {
        JsonPath js = new JsonPath(Payload.coursePrice());

        //Print number of courses
        int courseSize = js.getInt("courses.size()");
        System.out.println("Total number of courses: " + courseSize);

        //Print purchase amount
        System.out.println("Total purchase amount: " + js.getInt("dashboard.purchaseAmount"));

        //Print title of first course
        String firstTitle = js.getString("courses.get(0).title");
        System.out.println("Title of first course: " + firstTitle);

        int purchaseAmountNet = 0;

        for (int i = 0; i < courseSize; i++) {
            //Print All course titles and their respective Prices
            System.out.print("Course Title: " + js.getString("courses.get(" + i + ").title"));
            System.out.println(" Course Price: " + js.getInt("courses.get(" + i + ").price"));

            //Print number of copies sold by RPA course
            if (js.getString("courses.get(" + i + ").title").equalsIgnoreCase("RPA")) {
                System.out.println("Number of copies sold of RPA are: " + js.getInt("courses.get(" + i + ").copies"));
            }

            //Verify if Sum of all Course prices matches with Purchase Amount
            purchaseAmountNet = purchaseAmountNet + (js.getInt("courses.get(" + i + ").copies") * js.getInt("courses.get(" + i + ").price"));

        }
        System.out.println("Total purchase Amount: " + purchaseAmountNet);
        int purchaseAmount = js.getInt("dashboard.purchaseAmount");
        Assert.assertEquals(purchaseAmount, purchaseAmountNet);

    }
}
