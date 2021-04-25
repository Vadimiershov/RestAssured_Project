package SelfPractice_Library;

import com.github.javafaker.Faker;
import day4.LibraryAppTest;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class AddUserLibrary extends LibraryAppTest {

    @DisplayName("Add User to System /add_user")
    @Test
    public void AddUserToSystem(){

        Faker faker = new Faker();

        DateTimeFormatter df=DateTimeFormatter.ofPattern("yyyy-MM-dd");

        Map<String,Object> userBody= new HashMap<>();
        userBody.put("full_name", faker.name().fullName());
        userBody.put("email", faker.internet().emailAddress());
        userBody.put("password", faker.animal().name());
        userBody.put("user_group_id", 2);
        userBody.put("status", "active");
        userBody.put("start_date", df.format(LocalDate.now()));
        userBody.put("end_date", df.format(LocalDate.now().plusYears(5)));
        userBody.put("address", faker.address().fullAddress());

        JsonPath jp =
        given()
                .contentType(ContentType.JSON)
                .log().all()
                .body(userBody)
                .header("x-library-token", LibrarianToken).
        when()
                .post("/add_user")
                .jsonPath()
                .prettyPeek();

        assertThat(jp.get("full_name"),is(userBody.get("full_name")));



    }



}
