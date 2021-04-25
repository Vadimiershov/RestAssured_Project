package day2;

import org.junit.jupiter.api.DisplayName;
import test_util.SpartanNoAuthBaseTest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class VerifyingResponseInTheChain extends SpartanTest_PathVariableQueryParam{

    @DisplayName("Verifying the Get /spartans/{id} response directly in the chain")
    @Test
    public void testOneSpartanInOneShot(){

        given()
                .log().all()
                .pathParam("id",16).
        when()
                .get("/spartans/{id}").
        then()
                .log().all() //this will log the response
                .statusCode(200)
                .header("Content-Type",is("application/json") )
                .contentType("application/json")
                .body("id",equalTo(16))
                .body("name",equalTo("Sinclair"))
                .body("gender",equalTo("Male"))
                .body("phone",equalTo(9714460354L))
                ;


    }



}
