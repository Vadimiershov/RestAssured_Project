package day5;

import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class MovieAPIKey {

    @DisplayName("Search movies JsonPath practice")
    @Test
    public void testSearch(){

        //http://www.omdbapi.com/?apikey=96055849
        JsonPath jp =
        given()
                .baseUri("http://www.omdbapi.com")
                .log().all()
                .queryParam("apikey","96055849")
                .queryParam("s","Superman")
                .queryParam("type","series").
        when()
                .get().
        then()
                .log().all()
                .statusCode(200)
                //CONTINUE from here save Titles as list
                .extract()
                    .jsonPath()
        ;

        List<String> allTitles = jp.getList("Search.Title",String.class);
        System.out.println("allTitles = " + allTitles);
        int totalResult = jp.getInt("totalResults");
        System.out.println("totalResult = " + totalResult);

        //assert the size of the list is 10
        assertThat(allTitles,hasSize(10));

        //assert first item contains String Superman
        assertThat(allTitles.get(0),containsString("Superman"));

        //assert it has item "Superman and Lois"
        assertThat(allTitles,hasItem("Superman and Lois"));

        //assert it has item "Superman and Lois", "The New Adventures of Superman"
        assertThat(allTitles,hasItems("Superman and Lois", "The New Adventures of Superman"));

        //assert all items contains superman
        assertThat(allTitles, everyItem(containsString("Superman")));
    }

    @DisplayName("Search movies JsonPath practice")
    @Test
    public void testSearch2() {

        //http://www.omdbapi.com/?apikey=96055849

                given()
                        .baseUri("http://www.omdbapi.com")
                        .log().all()
                        .queryParam("apikey", "96055849")
                        .queryParam("s", "Superman")
                        .queryParam("type", "series").
                        when()
                        .get().
                        then()
                        .log().all()
                        .statusCode(200)
                        .body("Search.Title", hasSize(10))
                        .body("Search[0].Title", containsString("Superman"))
                        .body("Search.Title", hasItem("Superman and Lois"))
                        .body("Search.Title", hasItems("Superman and Lois", "The New Adventures of Superman"))
                        .body("Search.Title", everyItem(containsString("Superman")));


    }

}
