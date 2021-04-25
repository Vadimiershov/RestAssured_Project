package day3;

import io.restassured.http.ContentType;
import pojo.spartan;
import test_util.SpartanNoAuthBaseTest;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import test_util.SpartanNoAuthBaseTest;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class SpartanUpdatingData_test extends SpartanNoAuthBaseTest {

    // you may repeat everything we did previously in post test for providing body
    // we will just look at Map and POJO

    @DisplayName("PUT /spartans/{id} body as Map")
    @Test
    public void testUpdateDataWithMap(){

        Map<String, Object> bodyMap = new LinkedHashMap<>();
        bodyMap.put("name", "Abigale");
        bodyMap.put("gender", "Female");
        bodyMap.put("phone", 1234566789L);

        given()
                .log().all()
                .pathParam("id",105)
                .contentType(ContentType.JSON)
                .body(bodyMap).
        when()
                .put("/spartans/{id}").
        then()
                .statusCode(204)
                ;
}

    @DisplayName("PUT /spartans/{id} body as POJO")
    @Test
    public void testUpdateDataWithPOJO() {

        spartan sp = new spartan("Dena", "Female", 1234567659);

        given()
                .log().all()
                .pathParam("id", 6)
                .contentType(ContentType.JSON)
                .body(sp).
                when()
                .put("/spartans/{id}").
                then()
                .statusCode(204)
        ;

    }

    @DisplayName("PATCH /spartans/{id} body as String")
    @Test
    public void testPartialUpdateDataWithString() {

        String patchBody = " {\"phone\": 1231231299} ";

        given()
                .log().all()
                .contentType(ContentType.JSON)
                .pathParam("id",6)
                .body(patchBody).
        when()
                .patch("/spartans/{id}").
        then()
                .statusCode(204)
                ;
    }


    @DisplayName("Test DELETE /spartans/{id} ")
    @Test
    public void testDeleteOne() {
        given()
                .pathParam("id",105)
                .log().uri().
        when()
                .delete("/spartans/{id}").
        then().statusCode(equalTo(204));
    }

}
