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

public class SpartanTest_QueryParam extends SpartanTest_PathVariableQueryParam{

    @DisplayName("Get /spartans/ search Endpoint")
    @Test
    public void testSearch(){

        Response response =
                given()
                    .log().all()
                    .queryParam("nameContains","M")
                    .queryParam("gender","Male").
                when()
                   .get("/spartans/search")
                    .prettyPeek();
                //print the total element field value from the response
        System.out.println("response.path(\"totalElement\") = "
                + response.path("totalElement"));


    }


}
