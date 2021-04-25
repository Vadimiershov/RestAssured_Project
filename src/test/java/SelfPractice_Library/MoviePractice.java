package SelfPractice_Library;

import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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

public class MoviePractice {

    @DisplayName("GET movie with Json object")
    @Test
    public void testGetMovieInfoJson(){

        JsonPath jp =
                given()
                    .queryParam("apikey","96055849")
                    .queryParam("t","Superman").
                when()
                    .get("http://www.omdbapi.com/")
                    .prettyPeek()
                    .jsonPath()
                ;

        String title = jp.getString("Title");
        System.out.println("title = " + title);

        int year = jp.getInt("Year");
        System.out.println("year = " + year);

        String IMDB_rating = jp.getString("Ratings[0].Value");
        System.out.println("IMDB_rating = " + IMDB_rating);

        String RatingIndex2Source = jp.getString("Ratings[1].Source");
        System.out.println("RatingIndex2Source = " + RatingIndex2Source);

    }

    @DisplayName("GET movie with responce")
    @Test
    public void testGetMovieInfoThruResponce() {

        Response response =
                given()
                        .queryParam("apikey", "96055849")
                        .queryParam("t", "Superman").
                        when()
                        .get("http://www.omdbapi.com/")
                        .prettyPeek();
        String year = response.path("Year");
        System.out.println("year = " + year);

        String rating1Source = response.path("Ratings[1].Source");
        System.out.println("rating1Source = " + rating1Source);
    }

    @DisplayName("Get movie info")
    @Test
    public void testGetMovieInfo(){

        JsonPath jp = get("http://www.omdbapi.com/?s=Flash&type=series&apiKey=96055849").jsonPath();

        jp.prettyPrint();

        String object3Title = jp.getString("Search[2].Title");
        assertThat(object3Title,is("Flash Gordon: A Modern Space Opera"));
        System.out.println("object3Title = " + object3Title);

        String object3Year = jp.getString("Search[2].Year");
        assertThat(object3Year,is("2007–2008"));
        System.out.println("object3Year = " + object3Year);

        String object3imdbID = jp.getString("Search[2].imdbID");
        assertThat(object3imdbID,is("tt0959086"));
        System.out.println("object3imdbID = " + object3imdbID);
        //adding all imdbID to the list from response
        List<String> allImdbID = jp.getList("Search.imdbID",String.class);
        System.out.println("allImdbID = " + allImdbID);

        //eventually save all movie titles from all the pages into List<String>
        //from each and every pages
        List<String> allTitles = jp.getList("Search.Title");
        int totalResult = jp.getInt("totalResults");
        int iter = totalResult /10;

        if(iter>1){
            for (int i = 2; i <=iter+1; i++) {
                jp =
                        given()
                                .queryParam("s", "Flash")
                                .queryParam("type", "series")
                                .queryParam("page", i)
                                .queryParam("apiKey", 96055849).
                                when()
                                .get("http://www.omdbapi.com")
                                .jsonPath();
                allTitles.addAll(jp.getList("Search.Title"));
            }
        }
        System.out.println("allTitles = " + allTitles);
        System.out.println(allTitles.size());


    }
}
