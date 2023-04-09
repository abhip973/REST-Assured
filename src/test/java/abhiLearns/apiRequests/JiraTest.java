package abhiLearns.apiRequests;

import abhiLearns.files.ReUsableMethods;
import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.RestAssured.given;

public class JiraTest {
    @Test
    public void getSessionCookie() {
        RestAssured.baseURI = "http://localhost:8080/";

        SessionFilter session = new SessionFilter();
        String cookie = given().relaxedHTTPSValidation().header("Content-Type", "application/json").body("{\n" +
                "    \"username\": \"punj.abhishek1\",\n" +
                "    \"password\": \"Rab@1234\"\n" +
                "}").filter(session).when().post("rest/auth/1/session").then().assertThat().statusCode(200).extract().response().asString();

        //Add Comment
        String response = given().pathParam("key", "JAA-4").header("Content-Type", "application/json").body("{\n" +
                "    \"body\": \"This is a test comment\",\n" +
                "    \"visibility\": {\n" +
                "        \"type\": \"role\",\n" +
                "        \"value\": \"Administrators\"\n" +
                "    }\n" +
                "}").filter(session).post("rest/api/2/issue/{key}/comment").then().assertThat().statusCode(201).extract().response().asString();

        JsonPath js = ReUsableMethods.rawToJson(response);

        String id = js.getString("id");
        System.out.println("Extracted Id" + id);

        //Add attachment
        given().pathParam("key", "JAA-4").header("X-Atlassian-Token", "no-check")
                .header("Content-Type", "multipart/form-data").filter(session)
                .multiPart("file", new File("jira.txt")).when().post("/rest/api/2/issue/{key}/attachments")
                .then().assertThat().statusCode(200);

        //Get Issue
        String issueDetails = given().pathParam("key", "JAA-4").queryParam("fields", "comment").filter(session).when().get("/rest/api/2/issue/{key}").then().assertThat()
                .statusCode(200).assertThat().extract().response().asString();

        System.out.println("Issue Details are: " + issueDetails);

        //Verify if comment is added by matching the extracted id
        JsonPath js1 = ReUsableMethods.rawToJson(issueDetails);
        int size = js1.getInt("fields.comment.comments.size()");
        System.out.println(size);
        String commentExtracted = "";

        for (int i = 0; i < size; i++) {
            String extractedId = js1.get("fields.comment.comments[" + i + "].id").toString();
            System.out.println(extractedId);
            if (extractedId.equalsIgnoreCase(id)) {
                commentExtracted = js1.getString("fields.comment.comments[" + i + "].body");
                System.out.println("Extracted Comment: " + commentExtracted);
            }
        }
        Assert.assertEquals(commentExtracted, "This is a test comment");
    }
}
