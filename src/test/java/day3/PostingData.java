package day3;
import day1.SpartanNoAuthTest;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import pojo.spartan;
import test_util.SpartanNoAuthBaseTest;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
@DisplayName("Testing adding data to Spartan app multiple way")
public class PostingData extends SpartanNoAuthBaseTest{

    @DisplayName("Post/ spartans with String")
    @Test
    public void testPostDataWithStringBody(){

        /*
        {
            "name" : "Abigale",
            "gender" : "Female",
            "phone" : 1231231230
            }
         */

        String postStrBody = "{\n" +
                "            \"name\" : \"Abigale\",\n" +
                "            \"gender\" : \"Female\",\n" +
                "            \"phone\" : 1231231230\n" +
                "            }";

        given()
                .log().all()
          //      .header("Content-Type","application/json")
                .contentType(ContentType.JSON) //providing header for request
                .body(postStrBody).
        when()
                .post("/spartans").
        then()
                .statusCode(is (201))
                .log().all()
                .contentType(ContentType.JSON) // this is asserting response from the data
                .body("success",is("A Spartan is Born!"))
                .body("data.name",is("Abigale"))
                ;
    }

    @DisplayName("Post/ spartans with External File")
    @Test
    public void testPostDataWithExternalFile() {

        //singleSpartan.json

        File jsonFile = new File("singleSpartan.json");

        given()
                .log().all()
                //      .header("Content-Type","application/json")
                .contentType(ContentType.JSON) //providing header for request
                .body(jsonFile).
                when()
                .post("/spartans").
                then()
                .statusCode(is(201))
                .log().all()
                .contentType(ContentType.JSON) // this is asserting response from the data
                .body("success", is("A Spartan is Born!"))
                .body("data.name", is("Abigale"))
        ;

    }

    @DisplayName("Post/ spartans with Map Object")
    @Test
    public void testPostDataWithMapObjectAsBody() {

        Map<String, Object> bodyMap = new LinkedHashMap<>();
        bodyMap.put("name", "Abigale");
        bodyMap.put("gender", "Female");
        bodyMap.put("phone", 1234566789L);

        given()
                .log().all()
                .contentType(ContentType.JSON) //providing header for request
                .body(bodyMap).
        when()
                .post("/spartans").
        then()
                .statusCode(is (201))
                .log().all()
                .contentType(ContentType.JSON) // this is asserting response from the data
                .body("success",is("A Spartan is Born!"))
                .body("data.name",is("Abigale"))
        ;

    }

    @DisplayName("Post/ spartans with POJO")
    @Test
    public void testPostDataWithPOJOAsBody() {

         spartan sp = new spartan("Abigale","Female",1234567890);
        // turn into below
        /*
        {
            "name" : "Abigale",
            "gender" : "Female",
            "phone" : 1231231230
            }
         */

        given()
                .log().all()
                .contentType(ContentType.JSON) //providing header for request
                .body(sp).
        when()
                .post("/spartans").
        then()
                .statusCode(is (201))
                .log().all()
                .contentType(ContentType.JSON) // this is asserting response from the data
                .body("success",is("A Spartan is Born!"))
                .body("data.name",is("Abigale"))
        ;

        System.out.println("sp = " + sp);


    }

    }
