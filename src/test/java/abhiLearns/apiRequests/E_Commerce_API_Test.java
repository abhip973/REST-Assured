package abhiLearns.apiRequests;

import abhiLearns.pojo.addProductResponse.CreateProductResponse;
import abhiLearns.pojo.createOrder.CreateOrderRequest;
import abhiLearns.pojo.createOrder.Orders;
import abhiLearns.pojo.login.Login;
import abhiLearns.pojo.login.LoginResponse;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class E_Commerce_API_Test {
    public static void main(String[] args) {

        RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").setContentType(ContentType.JSON).build();

        Login login = new Login();
        login.setUserEmail("punj.abhishek1@gmail.com");
        login.setUserPassword("Abhi@123");
        RequestSpecification reqLogin = given().spec(req).body(login);

        LoginResponse loginResponse = reqLogin.when().post("/api/ecom/auth/login").then().extract().response().as(LoginResponse.class);
        String token = loginResponse.getToken();
        String userId = loginResponse.getUserId();

        //Create Product

        RequestSpecification addProductBaseReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
                .addHeader("Authorization", token).build();


        RequestSpecification reqAddProduct = given().spec(addProductBaseReq).param("productName", "Laptop").param("productAddedBy", userId).param("productCategory", "Electronics")
                .param("productSubCategory", "Digital").param("productPrice", "70000").param("productDescription", "i7 Laptop")
                .param("productFor", "Everyone").multiPart("productImage", new File("image.jpeg"));

        CreateProductResponse createProductResponse = reqAddProduct.when().post("/api/ecom/product/add-product").then().extract().response().as(CreateProductResponse.class);
        String productId = createProductResponse.getProductId();

        //Create Order
        RequestSpecification createOrderBaseReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").setContentType(ContentType.JSON).addHeader("Authorization", token).build();

        CreateOrderRequest createOrderRequest = new CreateOrderRequest();
        List<Orders> ordersList = new ArrayList<>();
        ordersList.get(0).setProductOrderId(productId);
        ordersList.get(0).setCountry("India");

        createOrderRequest.setOrders(ordersList);

        RequestSpecification createOrder = createOrderBaseReq.body(createOrderRequest);

        createOrder.when().post("/api/ecom/order/create-order").then().assertThat().statusCode(200);
    }
}
