package day8;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pojo.Region;
import test_util.DB_Utility;
import test_util.HR_ORDS__API_BaseTest;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static test_util.DB_Utility.*;

public class HR_ORDS_API_BaseTest extends HR_ORDS__API_BaseTest {

    @Test
    public void testHR_ORDS_API(){

        runQuery("SELECT * FROM REGIONS WHERE REGION_ID = 1");
        displayAllData();
        // send request to GET /regions/{region_id} and compare the result with DB result
        //save expected data for region_id of 1 into List
        List<String> firstRowAsExpectedList = getRowDataAsList(1);
        System.out.println("firstRowAsExpectedList = " + firstRowAsExpectedList);

        //send API request to GET /regions/{region_id} with id of 1 , save the result

        Region r1 =
                given().pathParam("region_id",1)
                .get("/regions/{region_id}")
                .jsonPath()
                .getObject("items[0]",Region.class);
        System.out.println("r1 = " + r1);

        //get("/regions/{region_id}",1)
        //compare the result
        assertThat(r1.getRegion_id(), is( Integer.parseInt(firstRowAsExpectedList.get(0)) ));
        assertThat(r1.getRegion_name(),is(firstRowAsExpectedList.get(1)));

    }

    @DisplayName("GET /regions and compare with expected DB Result")
    @Test
    public void testAllRegionsWithDB(){

        runQuery("SELECT REGION_ID, REGION_NAME FROM REGIONS");
        //Saving all rows into List of Map
        List<Map<String,String>> expectedRowMapList = getAllRowAsListOfMap();
        System.out.println("expectedRowMapList = " + expectedRowMapList);

        List<Region> allRegionsPojoLst = get("/regions").jsonPath().getList("items",Region.class);
        System.out.println("allRegionsPojoLst = " + allRegionsPojoLst);

        assertThat(expectedRowMapList.size(), equalTo(allRegionsPojoLst.size()));
        // compare each region id and region name match the expected region id and name
        for (int i = 0; i < expectedRowMapList.size(); i++) {

            assertThat(allRegionsPojoLst.get(i).getRegion_id(),
                    is(Integer.parseInt(expectedRowMapList.get(i).get("REGION_ID") )));

            assertThat(allRegionsPojoLst.get(i).getRegion_name(),
                    is(expectedRowMapList.get(i).get("REGION_NAME")));
        }

    }

}
