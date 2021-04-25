package day1;

import org.hamcrest.Matchers;
import io.restassured.RestAssured.*;
import io.restassured.matcher.RestAssuredMatchers.*;
import org.hamcrest.Matchers.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class HamcrestMatchersIntro {

    @DisplayName("Number Assertion")
    @Test
    public void simpleTest1() {

        //assert 10 equal to 5+5
        assertThat(10, is(5 + 5));
        assertThat(10, equalTo(10));


        //All matchers has 2 overloaded version
        //first that accept actual value
        //second that accept another matchers
        //below examples is method is accepting another matchers equal to make it readable
        assertThat(5 + 5, is(equalTo(10)));
        //negative assertion 5+5 is not 11
        assertThat(5 + 5, not(11));
        assertThat(5 + 5, is(not(11)));

        //number comparison
        assertThat(5 + 5, is(greaterThan(9)));
    }


    @DisplayName("Matchers related to Strings")
    @Test
    public void stringMatchers(){

        String msg = "B21 is learning Hamcrest";

        //checking for equality is same as number above
        assertThat(msg,is("B21 is learning Hamcrest"));
        assertThat(msg,equalTo("B21 is learning Hamcrest"));
        assertThat(msg,is(equalTo("B21 is learning Hamcrest")));

        //check if this msg start with B21
        assertThat(msg, startsWith("B21"));
        // now do it in case insensitive manner
        assertThat(msg,startsWithIgnoringCase("b21"));
        //check if the msg end with rest
        assertThat(msg, endsWith("rest"));

        //check if msg contains String learning
        assertThat(msg,containsStringIgnoringCase("learning"));

        String str = "    ";
        assertThat(str,blankString());

        assertThat(str.trim(),emptyString());

    }

    @DisplayName("Hamcrest Support for Collection")
    @Test
    public void testCollection(){

        List<Integer> lst = Arrays.asList(1,4,7,3,44,88,99,44);
        //checking the size of this list
        assertThat(lst,hasSize(8));
        //check if this list hasItem 7
        assertThat(lst,hasItem(7));

        assertThat(lst,hasItems(4,7,3));

        assertThat(lst,hasItem(greaterThan(0)));

    }



}
