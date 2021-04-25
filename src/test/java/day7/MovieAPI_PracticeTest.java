package day7;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pojo.Movie;
import pojo.Rating;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class MovieAPI_PracticeTest {

    //http://www.omdbapi.com/?apikey=96055849&t=Superman&plot=full&type=series
    //save the result of your request

    //save the response into Movie POJO, title Str, year int, Released str, Language
    //ignore any unknown properties
    //match the json fields to pojo fields

    @DisplayName("GET /http://www.omdbapi.com/?apikey=96055849&t=Superman&plot=full&type=series")
    @Test
    public void testMovieToPOJO(){

        Movie m1 = given()
                .queryParam("apikey","96055849")
                .queryParam("t","Avenger")
                .baseUri("http://www.omdbapi.com/").
            when()
                .get()
                //.jsonPath().getObject("",Movie.class)
                .as(Movie.class)
                ;

        System.out.println("m1 = " + m1);

    }

    @DisplayName("GET Search for avenger and save Ratings field into List<Raring>")
    @Test
    public void testMovieRatingToPojo(){

    List<Rating> allRatings = given()
                                .queryParam("apikey","96055849")
                                .queryParam("t","Avenger")
                                .baseUri("http://www.omdbapi.com/").
                            when()
                                .get()
                                .jsonPath()
                                .getList("Ratings",Rating.class)
            ;
        System.out.println("allRatings = " + allRatings);


    }


}
