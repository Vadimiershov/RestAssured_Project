package day6;

import static io.restassured.RestAssured.*;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import test_util.HR_ORDS__API_BaseTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class HR_ORDS_API_Practice extends HR_ORDS__API_BaseTest {

    //http://75.101.202.237:1000/ords/hr/api/regions
    //base_uri = 75.101.202.237:1000
    //base_path = ords/hr/api
    // resources = /regions

    @DisplayName("GET /regoins test")
    @Test
    public void testAllRegions(){

        //get("/regions").prettyPeek();
        //log the request uri
        //send GET / regions request
        //log the response
        //test staus code 200
        //test the count is 4
        //also test the size of items json array is 4
        given()
                .log().uri().
        when()
                .get("/regions").
        then()
                .log().all()
                .statusCode(200)
                .body("count", equalTo(4))
                .body("items", hasSize(4))
                ;
        //this is other style we learned on day 1
    }

    @DisplayName("GET /regoins test day 1 style")
    @Test
    public void testAllRegions2() {

        Response response =
                given()
                        .log().uri().
                when()
                        .get("/regions")
                        //.prettyPeek();
                        ;
        assertThat(response.statusCode(),is(200));

        assertThat(response.path("count"),equalTo(4));
        assertThat(response.path("items"),hasSize(4));
    }
}
