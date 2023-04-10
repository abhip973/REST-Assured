package abhiLearns.apiRequests;

import abhiLearns.files.ReUsableMethods;
import io.restassured.path.json.JsonPath;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static io.restassured.RestAssured.given;

public class OAuthTest {
    public static void main(String[] args) {

//        WebDriver driver = new ChromeDriver();
//        driver.get("https://accounts.google.com/o/oauth2/v2/auth/oauthchooseaccount?scope=https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email&auth_url=https%3A%2F%2Faccounts.google.com%2Fo%2Foauth2%2Fv2%2Fauth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https%3A%2F%2Frahulshettyacademy.com%2FgetCourse.php&service=lso&o2v=2&flowName=GeneralOAuthFlow");

//        driver.findElement(By.xpath("//input[@type='email']")).sendKeys("testUser@gmail.com");
//        driver.findElement(By.xpath("//input[@type='email']")).sendKeys(Keys.ENTER);
//        driver.findElement(By.xpath("//input[@type='password']")).sendKeys("TestPassword");
//        driver.findElement(By.xpath("//input[@type='password']")).sendKeys(Keys.ENTER);
//        String codeUrl = driver.getCurrentUrl();

        String codeUrl = "https://rahulshettyacademy.com/getCourse.php?code=4%2F0AVHEtk7uqIXJHXMTIR655USPKZo2mnKxU6lJeLNdaeCXjQRZsbMgGtWnNKt7JFhXjwx1TA&scope=email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+openid&authuser=1&prompt=consent";

        String[] codeUrlSplit = codeUrl.split("code=");
        String[] codeSplit = codeUrlSplit[1].split("&");

        System.out.println("Code = "+codeSplit[0]);

        String accessTokenResponse = given().urlEncodingEnabled(false)
                .queryParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
                .queryParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
                .queryParam("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
                .queryParam("grant_type", "authorization_code")
                .queryParam("code", codeSplit[0])
                .when().post("https://www.googleapis.com/oauth2/v4/token").then().log().all().extract().response().asString();

        JsonPath accessTokenJson = ReUsableMethods.rawToJson(accessTokenResponse);
        String accessToken = accessTokenJson.getString("access_token");
        System.out.println("Access Token"+accessToken);

        String response = given().queryParam("access_token", accessToken).when().get("https://rahulshettyacademy.com/getCourse.php")
                .then().extract().response().asString();
        System.out.println(response);
    }
}
