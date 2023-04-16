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

        System.out.println("Product Id is:" + productId);

        //Create Order
        RequestSpecification createOrderBaseReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").
                addHeader("Authorization", token).setContentType(ContentType.JSON).build();


        //We are first creating object of Orders class and setting our values in it.

        Orders orders = new Orders();
        orders.setCountry("India");
        orders.setProductOrderId(productId);

        //Now we are creating a list for our set values
        List<Orders> ordersList = new ArrayList<Orders>();
        ordersList.add(orders);

        CreateOrderRequest createOrderRequest = new CreateOrderRequest();

        createOrderRequest.setOrders(ordersList);

//        RequestSpecification createOrder = given().spec(createOrderBaseReq).body(createOrderRequest).log().all();
//        createOrder.when().post("/api/ecom/order/create-order").then().log().all().assertThat().statusCode(201);


        //Delete Product
        RequestSpecification deleteProductBaseReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").
                addHeader("Authorization", token).build();

        RequestSpecification deleteProductReq = given().spec(deleteProductBaseReq).pathParam("productId", productId);
        deleteProductReq.when().delete("/api/ecom/product/delete-product/{productId}").then().log().all();


    }
}
