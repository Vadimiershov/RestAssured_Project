package SelfPractice_Library;


import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.*;

public class GoalCountTask {

    @BeforeAll
    public static void init(){

        baseURI = "http://jsonmock.hackerrank.com/api";
        basePath = "/football_matches";

    }

    @AfterAll
    public static void tearDown(){

        reset();

    }

    @DisplayName("Get all goals of a team in a year")
    @Test
    public void getAllGoals(){

        Integer totalGoals = 0;
        int count1 = 1;
        int count2 = 1;
        List<Integer> totalGoalsTeam1 = new ArrayList<>();
        List<Integer> totalGoalsTeam2 = new ArrayList<>();

        int totalPages =
                given()
                        .queryParam("year",2012)
                        .queryParam("team1","Arsenal").
                when()
                        .get().
                then()
                        .extract().jsonPath().getInt("total_pages")
                        ;

        System.out.println("totalPages = " + totalPages);

        while (count1<=totalPages) {

            JsonPath jp =

                    given()
                            .log().all()
                            .queryParam("year", 2012)
                            .queryParam("team1", "Arsenal")
                            .queryParam("page", count1)
                            .contentType(ContentType.JSON).
                    when()
                            .get("").
                    then()
                            .extract().jsonPath()
                            ;

            totalGoalsTeam1.addAll(jp.getList("data.team1goals",Integer.class));
            count1++;
        }
        System.out.println("totalGoalsTeam1 = " + totalGoalsTeam1);
        for (Integer eachGoal: totalGoalsTeam1) {
            totalGoals += eachGoal;
        }


        while (count2<=totalPages) {

            JsonPath jp =

                    given()
                            .log().all()
                            .queryParam("year", 2012)
                            .queryParam("team2", "Arsenal")
                            .queryParam("page", count2)
                            .contentType(ContentType.JSON).
                            when()
                            .get("").
                            then()
                            .log().body()
                            .extract().jsonPath();

            totalGoalsTeam2.addAll(jp.getList("data.team2goals",Integer.class));
            count2++;

        }

        for (Integer eachGoal: totalGoalsTeam2) {
            totalGoals += eachGoal;
        }

        System.out.println("totalGoals = " + totalGoals);
        System.out.println("totalGoalsTeam2 = " + totalGoalsTeam2);
    }

//---------------------------------------------------------------------------

    @DisplayName("Getting total number of games of a team in a given year")
    @Test
    public void test(){


        JsonPath jsonPathA = given()
                .queryParam("year" , 2012)
                .queryParam("team1", "Arsenal").
                        when()
                .get().
                        then()
                .extract()
                .jsonPath()
                ;

        int numberOfPagesA = jsonPathA.getInt("total_pages");
        int totalObjectsA = jsonPathA.getInt("per_page");

        int countNumberOfGoals = 0;
        for (int j = 1; j <= numberOfPagesA; j++) {
            for (int i = 0; i < totalObjectsA; i++) {
                countNumberOfGoals +=   jsonPathA.getInt("data["+i+"].team1goals");
            }
        }

        JsonPath jsonPathB = given()
                .queryParam("year" , 2012)
                .queryParam("team2", "Arsenal").
                        when()
                .get().
                        then()
                .extract()
                .jsonPath()
                ;

        int numberOfPagesB = jsonPathB.getInt("total_pages");
        int totalObjectsB = jsonPathB.getInt("per_page");


        for (int j = 1; j <= numberOfPagesB; j++) {
            for (int i = 0; i <totalObjectsB; i++) {
                countNumberOfGoals +=   jsonPathB.getInt("data["+i+"].team2goals");
            }
        }


        System.out.println("countNumberOfGoals = " + countNumberOfGoals);



    }



}
