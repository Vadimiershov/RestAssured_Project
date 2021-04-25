package day7;

import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pojo.Country;
import pojo.Department;
import pojo.Employees;
import pojo.Region;
import test_util.HR_ORDS__API_BaseTest;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class HR_ORDS_API_DeserializationTest extends HR_ORDS__API_BaseTest {

    //Send request to /regions and save the result into List<Region>
    //assert the list has size 4
    @DisplayName("GET /regions")
    @Test
    public void testGetAllRegionAndSaveToListOfPOJO(){

        List<Region> allRegions =
                        get("/regions")
                        .jsonPath()
                        .getList("items",Region.class);

        System.out.println("allRegions = " + allRegions);

    }

    @DisplayName("GET /Countries")
    @Test
    public void testAllCountries() {

//        Country c1 = new Country("AR","Argentina",1);
//        System.out.println("c1 = " + c1);


        //save 3rd country as Country POJO

        Country thirdCountry = get("/countries")
                .jsonPath().getObject("items[0]",Country.class);

        System.out.println("thirdCountry = " + thirdCountry);
        //Save all countries as List<POJO>
        List<Country> allCountries = get("/countries")
                .jsonPath().getList("items",Country.class)
                ;
        allCountries.forEach(p -> System.out.println(p));

        Country c2 = get("/countries").jsonPath().getObject("items[0]",Country.class);
        System.out.println("c2 = " + c2);

    }


    // We want to create pojo that represent Employee data
    // We only care about employee_id , first_name , last_name , salary, department_id
    // we want to save the json data as pojo and we want to ignore any other fields other than specified above

    //http://75.101.202.237:1000/ords/hr/api/employees
    @DisplayName("GET /employees")
    @Test
    public void testAllEmployees(){
        //get the first employee pojo

        Employees e1 =
                get("/employees").jsonPath()
                .getObject("items[0]",Employees.class);
        System.out.println("e1 = " + e1);

    }

    //Till this moment we have been naming our pojo class fields names
    //to match exact name from json field
    //and yet there will be situation that the json field name
    //does not really work for java naming convention
    //we want to be able to name the POJO field anything we want
    //create pojo class called Departments with below fields
    //departmentId, name, manager_id, location_id

    @DisplayName("GET /departments")
    @Test
    public void testAllDepartments(){

        Department d1 = get("/departments").jsonPath()
                .getObject("items[1]",Department.class);
        System.out.println("d1 = " + d1);
    }


}
