package abhiLearns.apiRequests;

import abhiLearns.files.ReUsableMethods;
import abhiLearns.pojo.getCoursesResponse.GetCoursesResponse;
import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.given;

public class Deserialization {
    public static void main(String[] args) {

//        WebDriver driver = new ChromeDriver();
//        driver.get("https://accounts.google.com/o/oauth2/v2/auth/oauthchooseaccount?scope=https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email&auth_url=https%3A%2F%2Faccounts.google.com%2Fo%2Foauth2%2Fv2%2Fauth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https%3A%2F%2Frahulshettyacademy.com%2FgetCourse.php&service=lso&o2v=2&flowName=GeneralOAuthFlow");

//        driver.findElement(By.xpath("//input[@type='email']")).sendKeys("testUser@gmail.com");
//        driver.findElement(By.xpath("//input[@type='email']")).sendKeys(Keys.ENTER);
//        driver.findElement(By.xpath("//input[@type='password']")).sendKeys("TestPassword");
//        driver.findElement(By.xpath("//input[@type='password']")).sendKeys(Keys.ENTER);
//        String codeUrl = driver.getCurrentUrl();

        String codeUrl = "https://rahulshettyacademy.com/getCourse.php?code=4%2F0AVHEtk7Sk2RkB1XXY4yi6SLA6K83vYEKUsC-hQuN0XbuvUmsyIRD_XqWVYWCbe2RHccHDA&scope=email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+openid&authuser=0&prompt=none";

        String[] codeUrlSplit = codeUrl.split("code=");
        String[] codeSplit = codeUrlSplit[1].split("&");

        System.out.println("Code = " + codeSplit[0]);

        String accessTokenResponse = given().urlEncodingEnabled(false)
                .queryParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
                .queryParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
                .queryParam("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
                .queryParam("grant_type", "authorization_code")
                .queryParam("code", codeSplit[0])
                .when().post("https://www.googleapis.com/oauth2/v4/token").then().log().all().extract().response().asString();

        JsonPath accessTokenJson = ReUsableMethods.rawToJson(accessTokenResponse);
        String accessToken = accessTokenJson.getString("access_token");
        System.out.println("Access Token" + accessToken);

        GetCoursesResponse gc = given().queryParam("access_token", accessToken).expect().defaultParser(Parser.JSON)
                .when().get("https://rahulshettyacademy.com/getCourse.php")
                .then().extract().response().as(GetCoursesResponse.class);

        System.out.println("LinkedIn: " + gc.getCourses().getWebAutomation().get(2).getCourseTitle());

        //Extract price of the courseTitle "SoapUI WebServices Testing"
        int apiListSize = gc.getCourses().getApi().size();
        String price = null;

        for (int i = 0; i < apiListSize; i++) {
            if (gc.getCourses().getApi().get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing")) {
                price = gc.getCourses().getApi().get(i).getPrice();
            }
        }
        System.out.println("The price of SoapUI WebServices Testing is: " + price);

        //Get all Course Title of Web Automation
        int webListSize = gc.getCourses().getWebAutomation().size();
        for (int i = 0; i < webListSize; i++) {
            System.out.println(gc.getCourses().getWebAutomation().get(i).getCourseTitle());
        }

    }
}
