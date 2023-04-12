package abhiLearns.apiRequests;

import abhiLearns.pojo.addPlaceRequest.AddPlaceRequest;
import abhiLearns.pojo.addPlaceRequest.Location;
import io.restassured.RestAssured;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.*;

public class Serialization {
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

        String resp = given().queryParam("key","qaclick123").body(placeRequest).log().all()
                .when().post("/maps/api/place/add/json").then().assertThat().statusCode(200).extract().response().asString();

        System.out.println(resp);
    }
}
