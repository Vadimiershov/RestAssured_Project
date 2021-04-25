package SelfPractice_Library;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pojo.ArticalForNews;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class NewsAPI {
//    Read the doc of News API Org
//    Send Authorized request to
//    https://newsapi.org/v2/top-headlines?country=us&category=business
//    Print author name and title if source id is not null
//    HINT : Use your POJO Knowledges you learned Today
//https://newsapi.org/v2/top-headlines?country=us&category=business


    @Test
    public  void authorNameAndTitle(){


        baseURI = "https://newsapi.org";
        basePath = "/v2/top-headlines";
        String newsToken = "a1690350d015445eab76901f04fd99f8";


        List<ArticalForNews> listArticles =
        given()
                .queryParam("country","us")
                .queryParam("category","business")
                .log().all()
                .header("X-Api-Key",newsToken)
        .when()
                .get().jsonPath().getList("articles", ArticalForNews.class);
//        then()
//                .statusCode(200)
//                .log().all();

        for (ArticalForNews listArticle : listArticles) {



        }

        //listArticles.forEach(p-> System.out.println(p));

    }




}
