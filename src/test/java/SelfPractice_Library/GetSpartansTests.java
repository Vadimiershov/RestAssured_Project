package SelfPractice_Library;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import test_util.LibraryAuthBaseTest;
import test_util.SpartanNoAuthBaseTest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class GetSpartansTests extends SpartanNoAuthBaseTest {

    @DisplayName("Get/api/hello")
    @Test
    public void getHello(){

        Response r1 =
        given()
                .log().all().
         when()
                .get("/hello")
                .prettyPeek()
        ;
        //asserting response "Hello from Sparta"
        assertThat(r1.asString(),is("Hello from Sparta"));

    }

    @DisplayName("Get/spartans")
    @Test
    public void getListSpartans(){

        Response response = RestAssured.get(baseURI+basePath+"/spartans");

        System.out.println("response.statusCode() = " + response.statusCode());

        response.then().body("name",hasItem("Iryna"));

        assertTrue(response.body().asString().contains("Iryna"));

        System.out.println("response.body().asString() = " + response.body().asString());

        //getting first id in the array specifying [0] and assigning to the variable
        int id1 = response.path("id[0]");

        System.out.println("id1 = " + id1);

        //extract aa first names and print them
        List<String> names = response.path("name");
        System.out.println(names);
        //get number of all Spartans
        System.out.println(names.size());

        //extract all phone numbers and print them
        List<Object> phoneNums = response.path("phone");
        for (Object phoneNumber: phoneNums) {
            System.out.println(phoneNumber);
        }

    }

    @DisplayName("Get spartan By ID, using path method to retrieve info")
    @Test
    public void getSpartanById(){

Response response =
                given()
                    .accept(ContentType.JSON)
                    .pathParam("id",105).
                when()
                    .get("/spartans/{id}")
//                then()
//                    .statusCode(200)
//                    .contentType(ContentType.JSON)
//                    .body("name",equalTo("Iryna"))
//                    .log().all()
                ;
        assertThat(response.statusCode(),is(200));
        assertThat(response.contentType(),is("application/json"));
        assertTrue(response.body().asString().contains("Female"));
        assertTrue(response.body().asString().contains("Iry"));
        assertFalse(response.body().asString().contains("Male"));
        System.out.println("response.prettyPrint() = " + response.prettyPrint());

        //path methods

        int ID = response.path("id");
        System.out.println("ID = " + ID);

        //read value with JsonPath

        JsonPath jsonData = response.jsonPath();

        int IDJSON = jsonData.getInt("id");

        System.out.println("IDJSON = " + IDJSON);

    }

    @DisplayName("Get spartan By query parameters")
    @Test
    public void getSpartanByQuery(){

        Response response =
                given()
                    .contentType(ContentType.JSON).
                when()
                    .queryParam("gender","Female")
                    .queryParam("nameContains","Iry")
                    .get("/spartans/search")
                ;

        assertThat(response.statusCode(),is(200));
        assertThat(response.contentType(),is("application/json"));
        assertTrue(response.body().asString().contains("Female"));
        assertTrue(response.body().asString().contains("Iry"));
        assertFalse(response.body().asString().contains("Male"));
        System.out.println("response.prettyPrint() = " + response.prettyPrint());

    }

    @DisplayName("Get spartan By query parameters")
    @Test
    public void getSpartanByQueryWithMap() {

        Map<String, String> spartanQuery = new HashMap<>();
        spartanQuery.put("gender", "Female");
        spartanQuery.put("nameContains","Iryna");

        Response response =
                given()
                        .contentType(ContentType.JSON).
                        when()
                        .queryParams(spartanQuery)
                        .get("/spartans/search")
                ;

        assertThat(response.statusCode(),is(200));
        assertEquals(response.contentType(),"application/json");
        assertTrue(response.body().asString().contains("Female"));
        assertTrue(response.body().asString().contains("Iryna"));
        assertFalse(response.body().asString().contains("Male"));
        System.out.println("response.prettyPrint() = " + response.prettyPrint());

        System.out.println("response.path(\"content\") = " + response.path("content"));





    }


}
