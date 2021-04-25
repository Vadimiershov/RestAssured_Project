package day4;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import pojo.spartan;
import spartan_util.SpartanUtil;
import test_util.SpartanNoAuthBaseTest;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class SpartanRandomPOST_Test extends SpartanNoAuthBaseTest{

    @DisplayName("POST /spartans with random data")
    @Test
    public void addOneRandomSpartanTest(){

        Map<String , Object> randomBody = SpartanUtil.getRandomSpartanMap();

        given()
                .log().body()
                .contentType(ContentType.JSON)
                .body(randomBody).
        when()
                .post("/spartans").
        then()
                .log().all()
                .statusCode(201)
                .body("data.name",is(randomBody.get("name")))
                .body("data.gender",is(randomBody.get("gender")))
                .body("data.phone",is(randomBody.get("phone")))
                ;
    }

    @DisplayName("POST /spartans with random Spartan POJO")
    @Test
    public void addOneRandomSpartanPOJOTest(){

        spartan randomPOJO = SpartanUtil.getRandomSpartanPOJO();
        given()
                .log().body()
                .contentType(ContentType.JSON)
                .body(randomPOJO).
                when()
                .post("/spartans").
                then()
                .log().all()
                .statusCode(201)
                .body("data.name",is(randomPOJO.getName()))
                .body("data.gender",is(randomPOJO.getGender()))
                .body("data.phone",is(randomPOJO.getPhone()))
        ;
    }

    @DisplayName("POST /spartans and send /GET /spartans/{id} to verify")
    @Test
    public void testAddOneDataThenGetOneDataToVerify(){

        //send post request, save the id that generated
        //check status 201
        //send get request using the id you saved from above
        //check status code is 200 and body match for exactly what you send
        spartan randomPOJO = SpartanUtil.getRandomSpartanPOJO();
        Response response =
                given()
                    .log().body()
                    .contentType(ContentType.JSON)
                    .body(randomPOJO).
                when()
                    .post("/spartans").prettyPeek();

        //int newID = response.path("data.id");
        int newId = response.jsonPath().getInt("data.id");
        System.out.println("newId = " + newId);
        assertThat(response.statusCode(),is(201));

        given()
                .pathParam("id",newId)
                .log().uri().
        when()
                .get("/spartans/{id}").
        then()
                .statusCode(200)
                .log().body()
                .body("id",is(newId))
                .body("name",is(randomPOJO.getName()))
                .body("gender",is(randomPOJO.getGender()))
                .body("phone",is(randomPOJO.getPhone()))
                ;
    }

    @DisplayName("POST /spartans and send /GET /spartans/{id} to verify2")
    @Test
    public void testAddOneDataThenGetOneDataToVerifyInTheChain(){

        //send post request verify 201 and then extract the id in the same method chain
        spartan randomPOJO = SpartanUtil.getRandomSpartanPOJO();

        int newID =
                given()
                        .log().body()
                        .contentType(ContentType.JSON)
                        .body(randomPOJO).
                when()
                        .post("/spartans").
                then()
                        .log().body()
                        .statusCode(201)
                        .extract()
   //                     .path("data.id")
                .jsonPath().getInt("data.id")
                ;
        System.out.println("newID = " + newID);


    }

    @DisplayName("POST /spartans and send /GET /spartans/{id} to verify3")
    @Test
    public void testAddOneDataThenExtractHeader(){

        spartan randomPOJO = SpartanUtil.getRandomSpartanPOJO();
        // check status 201 and extract location header
        String location_header =
                given()
                        .log().body()
                        .contentType(ContentType.JSON)
                        .body(randomPOJO).
                when()
                        .post("/spartans").
                then()
                        .statusCode(201)
                        .extract().header("Location");

        System.out.println("location_header = " + location_header);

        //sending the request using above url we extracted

        get(location_header).prettyPeek();

    }

}














