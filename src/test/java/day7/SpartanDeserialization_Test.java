package day7;

import day4.LibraryAppTest;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pojo.SpartanPOJOwithID;
import test_util.SpartanNoAuthBaseTest;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class SpartanDeserialization_Test extends SpartanNoAuthBaseTest {

    // Serialization : Java object to JSON or any other type of text
    // Deserialization : Json(text) to Java

    @DisplayName("Get /spartans/{id}")
    @Test
    public void testGetOneData(){

        given()
                .pathParam("id",106).
        when()
                .get("/spartans/{id}").
        then()
                .statusCode(200)
                .log().body()
                ;

        //Send same request, store the result into SpartanPojo object
        Response response =
                given()
                .pathParam("id",106).
                when()
                .get("/spartans/{id}");

        SpartanPOJOwithID sp = response.as(SpartanPOJOwithID.class);
        System.out.println("sp = " + sp);

// 2 way for deserialization without path
        JsonPath jp = response.jsonPath();
        SpartanPOJOwithID sp1 = jp.getObject("",SpartanPOJOwithID.class);
        System.out.println("sp1 = " + sp1);
    }

    @DisplayName("GET /spartans/search")
    @Test
    public void testSearch(){
        //save first object with type Spartan pojo
    Response response =
        given()
                .queryParam("nameContains","Iry")
                .queryParam("gender","Female").
        when()
                .get("/spartans/search")
                .prettyPeek()
            ;
    //response as will not work here because we need to provide path to get
        //to the json object we want content[0]
        JsonPath jp = response.jsonPath();
        SpartanPOJOwithID sp = jp.getObject("content[0]",SpartanPOJOwithID.class);
        System.out.println("sp = " + sp);

        //this how we can do in one chain

        SpartanPOJOwithID sp1 = given()
                                    .log().uri()
                                    .queryParam("nameContains","Iry")
                                    .queryParam("gender","Female").
                        when()
                                    .get("/spartans/search")
                                    .jsonPath()
                                    .getObject("content[0]",SpartanPOJOwithID.class);
        System.out.println("sp1 = " + sp1);

    }
    //to get a list of objects
    @DisplayName("GET /spartans/search and save as List<SpartansPOJO>")
    @Test
    public void testSearchSaveList(){

        List<SpartanPOJOwithID> list =
                given()
                        .queryParam("nameContains","Iry")
                        .queryParam("gender","Female").
                when()
                        .get("spartans/search")
                        .jsonPath()
                        .getList("content",SpartanPOJOwithID.class)
                        ;
        // something.class return type class to specify what kind of Item you want to have in your list
        System.out.println("list = " + list);
        list.forEach(blabla -> System.out.println(blabla));

    }


}
