package abhiLearns.apiRequests;

import abhiLearns.files.Payload;
import abhiLearns.files.ReUsableMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class DynamicJsonDataProvider {

    @Test(dataProvider = "booksDetails")
    public void addBooksToLib(String isbn) {
        RestAssured.baseURI = "http://216.10.245.166";

        String response = given().header("Content-Type", "application/json").body(Payload.addBookToLibrary(isbn)).when()
                .post("/Library/Addbook.php").then().assertThat().statusCode(200).extract().response().asString();

        JsonPath js = ReUsableMethods.rawToJson(response);
        System.out.println(js.getString("ID"));

        given().header("Content-Type", "application/json").body("{\n" +
                "    \"ID\": \"" + isbn + "22" + "\"\n" +
                "}").when().post("/Library/DeleteBook.php").then().assertThat().statusCode(200);
    }


    @DataProvider(name = "booksDetails")
    public Object[][] booksDetails() {
        return new Object[][]{{"asbahd"}, {"eiqe"}, {"qwewof"}, {"aomqd"}};
    }
}
