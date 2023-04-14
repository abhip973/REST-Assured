package abhiLearns.apiRequests;

import abhiLearns.files.Payload;
import abhiLearns.files.ReUsableMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class DynamicJson {

    @Test
    public void addBook() {
        RestAssured.baseURI = "http://216.10.245.166";
        String generatedString = RandomStringUtils.randomAlphabetic(5);

        String response = given().header("Content-Type", "application/json").body(Payload.addBookToLibrary(generatedString)).when()
                .post("/Library/Addbook.php").then().assertThat().statusCode(200).extract().response().asString();

        JsonPath js = ReUsableMethods.rawToJson(response);

        System.out.println(js.getString("ID"));
    }
}
