package abhiLearns.apiRequests;

import abhiLearns.files.Payload;
import abhiLearns.files.ReUsableMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;

import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;


public class AddPlace {
    public static void main(String[] args) {

        Payload payload = new Payload();
        String newAddress = "70 winter walk, USA";


        RestAssured.baseURI = "https://rahulshettyacademy.com";

        //Add Place
        String response = given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
                .body(payload.AddPlace())
                .when().post("/maps/api/place/add/json").then().assertThat().statusCode(200)
                .body("scope", equalTo("APP")).header("server", "Apache/2.4.41 (Ubuntu)").extract().asString();

        JsonPath js = new JsonPath(response);
        String place_id = js.getString("place_id");
        System.out.println("Extracted place id is: " + place_id);

        //Update Place
        given().queryParam("key", "qaclick123").queryParam("place_id", place_id).header("Content-Type", "application/json")
                .body(payload.UpdatePlace(place_id, newAddress))
                .when().put("/maps/api/place/update/json").then().log().all().assertThat().statusCode(200).body("msg", equalTo("Address successfully updated"));

        //Get Place
        String getResponse = given().queryParam("key", "qaclick123").queryParam("place_id", place_id).when().get("/maps/api/place/get/json")
                .then().assertThat().statusCode(200).extract().asString();
        JsonPath getResponseJson = ReUsableMethods.rawToJson(getResponse);

        String address = getResponseJson.getString("address");
        Assert.assertEquals(address, newAddress);
    }
}
