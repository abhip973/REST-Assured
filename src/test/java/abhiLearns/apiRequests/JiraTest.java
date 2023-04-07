package abhiLearns.apiRequests;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class JiraTest {
    @Test
    public void getSessionCookie() {
        RestAssured.baseURI = "http://localhost:8080/";

        SessionFilter session = new SessionFilter();
        String cookie = given().header("Content-Type","application/json").body("{\n" +
                "    \"username\": \"punj.abhishek1\",\n" +
                "    \"password\": \"Rab@1234\"\n" +
                "}").filter(session).when().post("rest/auth/1/session").then().assertThat().statusCode(200).extract().response().asString();

        given().pathParam("key","JAA-4").header("Content-Type","application/json").body("{\n" +
                "    \"body\": \"This is a test comment\",\n" +
                "    \"visibility\": {\n" +
                "        \"type\": \"role\",\n" +
                "        \"value\": \"Administrators\"\n" +
                "    }\n" +
                "}").filter(session).post("rest/api/2/issue/{key}/comment").then().assertThat().statusCode(201);

    }
}
