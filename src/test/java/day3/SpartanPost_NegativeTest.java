package day3;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pojo.spartan;
import test_util.SpartanNoAuthBaseTest;
import day1.SpartanNoAuthTest;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
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

public class SpartanPost_NegativeTest extends SpartanNoAuthBaseTest {

    @DisplayName("post request without content type, expect 415")
    @Test
    public void test1(){

        spartan sp = new spartan("B21","Male",1234564560);
            given()
                    .log().all()
                    .body(sp).
            when()
                    .post("/spartans").
            then()
                    .statusCode(415)
                    .log().all()
            ;

    }

    @DisplayName("post request without valid Json, expect 400")
    @Test
    public void test2(){

        given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(" Bad json structure here").
        when()
                .post("/spartans").
        then()
                .log().all()
                .statusCode(400)
                ;

    }

    @DisplayName("post request with valid Json,bad Name -  expect 400 with detailed name error")
    @Test
    public void test3(){

        spartan sp = new spartan("1","Male",1234564560);
        given()
                .log().all()
                .body(sp).
                contentType(ContentType.JSON).
        when()
                .post("/spartans").
        then()
                .statusCode(400)
                .log().all()
                .body("message",is("Invalid Input!"))
                .body("errorCount",is (1))
                .body("errors[0].errorField",is("name"))
                .body("errors[0].rejectedValue",is("1"))
                .body("errors[0].reason",is("name should be at least 2 character and max 15 character"))

        ;


    }

    @DisplayName("post request with bad name, phone, expect 400 with detailed name, phone error message")
    @Test
    public void test4(){

        spartan sp = new spartan("Badyd","Male",123456456012343L);
        given()
                .log().all()
                .body(sp).
                contentType(ContentType.JSON).
                when()
                .post("/spartans").
                then()
                .statusCode(400)
                .log().all()
                .body("message",is("Invalid Input!"))
                .body("errorCount",is (1))
                .body("errors[0].errorField",is("name"))
                .body("errors[0].rejectedValue",is("1"))
                .body("errors[0].reason",is("name should be at least 2 character and max 15 character"))

        ;


    }

    @DisplayName("post request with bad name, phone, expect 400")
    @Test
    public void test5(){




    }

}
