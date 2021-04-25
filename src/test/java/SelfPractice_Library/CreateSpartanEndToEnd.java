package SelfPractice_Library;

import com.github.javafaker.Faker;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import test_util.SpartanNoAuthBaseTest;

import javax.sound.midi.Soundbank;
import javax.xml.ws.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;


public class CreateSpartanEndToEnd extends SpartanNoAuthBaseTest {

    public static List<String> listOfID;
    public static int totalSpartans;

    @DisplayName("Get a list of all Spartans api/spartans")
    @Test
    public void getListOfSpartans(){

        JsonPath jp =

        when()
                .get("/spartans").
        then()
                .statusCode(200).
                extract().jsonPath()
                ;

        listOfID = jp.getList("id");
        totalSpartans = listOfID.size();
        System.out.println("totalSpartans = " + totalSpartans);
        System.out.println("listOfID = " + listOfID);

    }

    @DisplayName("Add a new Spartan /spartans")
    @Test
    public void addSpartan(){

        Faker faker = new Faker();

        Map<String,Object> bodySpartan = new HashMap<>();
        bodySpartan.put("gender",faker.demographic().sex());
        bodySpartan.put("name",faker.name().firstName());
        bodySpartan.put("phone",faker.number().numberBetween(5000000000L,5000500050L));

        given()
                .contentType(ContentType.JSON).
                given().body(bodySpartan).
                log().all().
        when()
                .post("/spartans").
        then()
                .statusCode(201)
                .body("success",is("A Spartan is Born!"))
                .body("data.name",is(bodySpartan.get("name")))
        ;

    }

    @DisplayName("Delete a spartan /api/spartans/{id}")
    @Test
    public void deleteSpartan(){

        given()
                .contentType(ContentType.JSON)
                .pathParam("id",listOfID.get(totalSpartans-1)).
        when()
                .delete("spartans/{id}").
        then()
                .statusCode(204)
                .log().body();
    }


}
