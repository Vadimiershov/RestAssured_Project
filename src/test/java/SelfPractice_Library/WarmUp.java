package SelfPractice_Library;

import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import java.util.ArrayList;
import java.util.List;
import static io.restassured.RestAssured.*;

public class WarmUp {

    @BeforeAll
    public static void init(){

        baseURI = "https://swapi.dev";
        basePath ="/api";
    }

    @AfterAll
    public static void cleanup(){
        reset();
    }

    @DisplayName("Find out average hight of all people")
    @Test
    public void getAverageHigh() {
//retrieving first page that include 10 people
        //retrieving all pages with all people

        List<Integer> allHeight = get("/people")
                .jsonPath()
                .getList("results.height", Integer.class);

        int total = 0;
        for (Integer each : allHeight) {
            total += each;
        }
        int average = total / (allHeight.size());
        System.out.println("average = " + average);
    }
        // Above code will only retrieve first page that include 10 people
// but we have more than 10 people in star war
// we can get total count of people in first response count field
// the decide how many page we have to go through by sending more request
// then loop through the rest of the pages to add all heights to the list
// and calculate the average from final list
// in order to go to next page we can use
// page query parameter to decide which page we want to see
// HERE IS THE STEPS :
// Create an empty Integer empty list
//  Send GET /people -->>
        // capture the total count using jsonPath
        // save first page heights into the list
//  Loop : from page 2 till last page
        // get the list of height integer using jsonPath
        // add this to the big list

    @DisplayName("Get all heihts from all the pages and find average")
    @Test
    public void testGetAllPagesAverageHeight(){

        List<Integer> allHeight = new ArrayList<>();

        //send initial request, find total count and decide how many pages exist

        JsonPath jp =
                get("/people")
                .jsonPath();

        int peopleCount = jp.getInt("count");
        //if there is remainder we want to add 1, if there is not keep it as is
        int pageCount = (peopleCount%10==0) ? peopleCount / 10 : (peopleCount/10)+1;

        List<Integer> firstPageHeights = jp.getList("results.height");
        allHeight.addAll(firstPageHeights);
        System.out.println("firstPageHeights = " + firstPageHeights);

        for (int pageNum = 2; pageNum <=pageCount; pageNum++) {
            //GET /people?page = yourPageNumberGoesHere
            List<Integer> heightsOnThisPage = get("/people?page="+pageNum)
                    .jsonPath()
                    .getList("results.height");
            allHeight.addAll(heightsOnThisPage) ;
            System.out.println("heightsOnThisPage = " + heightsOnThisPage);
        }





    }




}
