package day3;

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

public class SpartanJsonPath_Test extends SpartanNoAuthTest {

    @Test
    public void testOne(){

        Response response = given()
                                .log().all()
                                .pathParam("id","6").
                            when()
                                .get("/spartans/{id}").
                            prettyPeek();

        //using path method to extract data

        int myId = response.path("id");
        System.out.println("myId = " + myId);

        //Few meaning of JsonPath:
            //1. just like xpath -- it is used to provide location of certain data
            //2. JsonPath as a class coming from RestAssured to provide reusable methods to extract data
            //3. jsonPath() method of Response object to get JsonPath object

        JsonPath jp = response.jsonPath() ; //number 2 for variable type, number 3 for method

        myId = jp.getInt("id");

        System.out.println("jp.getInt(\"id\") " + myId);

        long phoneNum = jp.getLong("phone");
        System.out.println("phoneNum = " + phoneNum);

        String myName = jp.getString("name");
        System.out.println("myName = " + myName);
        //getting json object from the map, ask for empty and get full body
        System.out.println("jp.getMap(\"\") = " + jp.getMap(""));

        Map<String, Object> resultJsonInMap = jp.getMap("");
        System.out.println("resultJsonInMap = " + resultJsonInMap);

    }
    @DisplayName("Extract data from GET /spartans ")
    @Test
    public void testGetAllSpartans(){

//        Response response = get("/spartans");
//        JsonPath jp = response.jsonPath();

        JsonPath jp = get("/spartans").jsonPath() ;

        //print first ID in the json array response

        int first_id = jp.getInt("id[0]");
        System.out.println("first_id = " + first_id);

        //print second json object name in the json array response
        String my_Name = jp.getString("name[0]");
        System.out.println("my_Name = " + my_Name);

        System.out.println("jp.getString(\"[0]\") = " + jp.getString("[0]"));

        System.out.println("jp.getMap(\"0\") = " + jp.getMap("[0]"));

        System.out.println("jp.getInt(\"[0].id\") = " + jp.getInt("[0].id"));

    }

    @DisplayName("Extract data from GET")
    @Test
    public void testGetSearchSpartans(){

        JsonPath jp =
        given()
                .queryParam("nameContains","Iry")
                .queryParam("gender","Female")
                .log().all().
        when()
                .get("/spartans/search")
                .prettyPeek()
                .jsonPath()
        ;

        // find out first guy id, second guy name
        // content[0].id

        System.out.println("jp.getInt(\"content[0].id\") = " + jp.getInt("content[0].id"));
        System.out.println("jp.getString(\"content[0].name\") = " + jp.getString("content[1].name"));

        //store first json object into a map
        Map<String, Object> firstJsonInMap = jp.getMap("content[0]");

        System.out.println("firstJsonInMap = " + firstJsonInMap);


    }

    @DisplayName("Saving json array fields into LIst")
    @Test
    public void testSavingJsonArrayFieldsIntoList(){

        JsonPath jp =
                given()
                        .queryParam("nameContains","Iry")
                        .queryParam("gender","Female")
                        .log().all().
                        when()
                        .get("/spartans/search")
                        .prettyPeek()
                        .jsonPath()
                ;

        //save all the id into the list

        System.out.println("jp.getList(\"content.id\") = "
                + jp.getList("content.id"));

        System.out.println("jp.getList(\"content.name\") = "
                + jp.getList("content.name"));

        System.out.println("jp.getList(\"content.phone\") = "
                + jp.getList("content.phone"));
        //overloaded getList method
        List<Integer> allIDs = jp.getList("content.id");
        List<Integer> allIDs2 = jp.getList("content.id", Integer.class ) ;

        List<String> allNames = jp.getList("content.name") ;
        List<String> allNames2 = jp.getList("content.name",String.class);

        List<Long> allPhones = jp.getList("content.phone");
        List<Long> allPhones2 = jp.getList("content.phone",Long.class);

    }

    @DisplayName("Get List Practice for GET/ spartans")
    @Test
    public void testGetLIstOutOfAllSpartans(){

        JsonPath jp = get("/spartans").jsonPath();
        //save the list into List object and assert the size

        List<Integer> allIDs = jp.getList("id");
        //System.out.println(allIDs.size());
        assertThat(allIDs,hasSize(129));

        List<Integer> allNames = jp.getList("name");
        assertThat(allNames,hasSize(129));

        List<Integer> allPhones = jp.getList("phone");
        assertThat(allPhones,hasSize(129));

    }



}
