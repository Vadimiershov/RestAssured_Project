package day2;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import test_util.SpartanNoAuthBaseTest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class BreakingBad_getAllCharacterByName {


    @BeforeAll
    public static void init(){

        baseURI = "https://www.breakingbadapi.com";
        basePath = "/api";

    }


    @AfterAll
    public static void cleanUp(){

        RestAssured.reset();

    }

    @DisplayName("Get All Character By Name")
    @Test
    public void getFilterCharacterName(){

                given()
                    .log().uri()
                    .queryParam("name","Walter").
                when()
                    .get("/characters")
                    .prettyPeek().
                then()
                    .statusCode(200)
                    .contentType("application/json")
                ;
    }

    @DisplayName("Test Get /characters/{char_id}")
    @Test
    public void test1Character(){

        given()
                .pathParam("char_id",1)
                .log().uri().
        when()
                .get("/characters/{char_id}").
        then()
                .log().all()
                .statusCode(200)
                ;
    }

    @DisplayName("Test GET /episodes/{episode_id}")
    @Test
    public void test1Episode(){

        given()
                .pathParam("episode_id",60)
                .log().all().
        when()
                .get("/episodes/{episode_id}").
        then()
                .log().all()
                .statusCode(200)
                .contentType(ContentType.JSON)
                ;
    }


}
