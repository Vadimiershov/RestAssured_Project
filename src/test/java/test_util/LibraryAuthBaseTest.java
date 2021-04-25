package test_util;

import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;

public class LibraryAuthBaseTest {

    public static String LibrarianToken;


    @BeforeAll
    public static void inti(){

        baseURI = "http://library3.cybertekschool.com";
        basePath = "/rest/v1";
        LibrarianToken = getToken("librarian11@library","shpUFwRF");
        //DB Connection info
        String url = ConfigurationReader.getProperty("library1.database.url");
        String username = ConfigurationReader.getProperty("library1.database.username");
        String password = ConfigurationReader.getProperty("library1.database.password");
        DB_Utility.createConnection(url,username,password);

    }

    public static String getToken(String username, String password){

    return     given()
                    .log().all()
                    .contentType(ContentType.URLENC)
                    .formParam("email", username)
                    .formParam("password", password).
                when()
                    .post("/login")
                    .path("token");
    }

    public static Map<String,Object> getRandomBook(){

        Faker faker = new Faker();

        Map<String,Object> myBookMap = new HashMap<>();
        myBookMap.put("name",faker.book().title());
        myBookMap.put("isbn",faker.number().digits(8));
        myBookMap.put("year",faker.number().numberBetween(1850,2020));
        myBookMap.put("author",faker.book().author());
        myBookMap.put("book_category_id",faker.number().numberBetween(0,20));
        myBookMap.put("description",faker.book().genre());

        return myBookMap;
    }

    @AfterAll
    public static void cleanup(){

        RestAssured.reset();
        DB_Utility.destroy();


    }




}
