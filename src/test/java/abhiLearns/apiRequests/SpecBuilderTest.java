package abhiLearns.apiRequests;

import abhiLearns.pojo.addPlaceRequest.AddPlaceRequest;
import abhiLearns.pojo.addPlaceRequest.Location;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class SpecBuilderTest {
    public static void main(String[] args) {
        RestAssured.baseURI = "https://rahulshettyacademy.com";
        AddPlaceRequest placeRequest = new AddPlaceRequest();
        placeRequest.setAccuracy(50);
        placeRequest.setName("Frontline house");
        placeRequest.setAddress("29, side layout, cohen 09");
        placeRequest.setLanguage("French-IN");
        placeRequest.setWebsite("http://google.com");
        placeRequest.setPhone_number("(+91) 983 893 3937");

        Location location = new Location();
        location.setLat(-38.383494);
        location.setLng(33.427362);

        List<String> types = new ArrayList<String>();
        types.add("shoe park");
        types.add("shop");
        placeRequest.setTypes(types);
        placeRequest.setLocation(location);

         RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addQueryParam("key","qaclick123")
                .setContentType(ContentType.JSON).build();

         ResponseSpecification res = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();

        String resp = given().spec(req).body(placeRequest).log().all()
                .when().post("/maps/api/place/add/json").then().spec(res).extract().response().asString();

        System.out.println(resp);
    }
}
