package abhiLearns.files;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.Random;
import java.util.random.RandomGenerator;

public class Payload {
    public String AddPlace() {
        return "{\n" +
                "    \"location\": {\n" +
                "        \"lat\": -38.383494,\n" +
                "        \"lng\": 33.427362\n" +
                "    },\n" +
                "    \"accuracy\": 50,\n" +
                "    \"name\": \"Abhi house\",\n" +
                "    \"phone_number\": \"(+91) 983 893 3937\",\n" +
                "    \"address\": \"29, side layout, cohen 09\",\n" +
                "    \"types\": [\n" +
                "        \"shoe park\",\n" +
                "        \"shop\"\n" +
                "    ],\n" +
                "    \"website\": \"http://google.com\",\n" +
                "    \"language\": \"French-IN\"\n" +
                "}";
    }

    public String UpdatePlace(String placeId, String address) {
        return "{\n" +
                "    \"place_id\": \"" + placeId + "\",\n" +
                "    \"address\": \"" + address + "\",\n" +
                "    \"key\": \"qaclick123\"\n" +
                "}";
    }

    public static String coursePrice() {
        return "{\n" +
                "  \"dashboard\": {\n" +
                "    \"purchaseAmount\": 910,\n" +
                "    \"website\": \"rahulshettyacademy.com\"\n" +
                "  },\n" +
                "  \"courses\": [\n" +
                "    {\n" +
                "      \"title\": \"Selenium Python\",\n" +
                "      \"price\": 50,\n" +
                "      \"copies\": 6\n" +
                "    },\n" +
                "    {\n" +
                "      \"title\": \"Cypress\",\n" +
                "      \"price\": 40,\n" +
                "      \"copies\": 4\n" +
                "    },\n" +
                "    {\n" +
                "      \"title\": \"RPA\",\n" +
                "      \"price\": 45,\n" +
                "      \"copies\": 10\n" +
                "    }\n" +
                "  ]\n" +
                "}";
    }

    public static String addBookToLibrary(String isbn) {

        String payload = "{\n" +
                "    \"name\": \"Learn Selenium Automation with Java\",\n" +
                "    \"isbn\": \"" + isbn + "\",\n" +
                "    \"aisle\": \"22\",\n" +
                "    \"author\": \"John foe\"\n" +
                "}";
        return payload;
    }
}
