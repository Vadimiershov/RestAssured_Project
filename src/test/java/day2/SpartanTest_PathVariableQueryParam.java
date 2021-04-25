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

@DisplayName("Spartan Test with path variable and query param")
public class SpartanTest_PathVariableQueryParam extends SpartanNoAuthBaseTest {

    @Test
    public void getOneSpartan(){
        //1 style
        get("/spartans/16").prettyPeek();

        //I want to provide 16 as path variable|parameter
        //I want to provide accept header
        //2 style
    Response r1 =
        given()
                .pathParam("spartan_id",16)
                .header("Accept","application/json").
        when()
                .get("/spartans/{spartan_id}")
                .prettyPeek()
        ;

    Response r2 =
        given()
                //this is same as .header("Accept","application/json").
                .accept("application/json").
                //This is alternative way of providing
                //path variable and value directly in get method
        when()
                .get("/spartans/{spartan_id}",16)
                .prettyPeek()
                ;

    }

    @DisplayName("logging the request")
    @Test
    public void getSpartanWithLog(){

        Response response =
                given()
                        .log().all()
                        .accept("application/json")
                        .pathParam("id",16).
                when()
                    .get("/spartans/{id}")
                    .prettyPeek()
                ;

        assertThat(response.statusCode(),equalTo(200));
        assertThat(response.contentType(),is("application/json"));
        assertThat(response.path("name"),is("Sinclair"));




    }




}
