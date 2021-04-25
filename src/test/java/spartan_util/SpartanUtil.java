package spartan_util;

import com.github.javafaker.Faker;
import pojo.spartan;
import java.util.LinkedHashMap;
import java.util.Map;

public class SpartanUtil {

    private static Faker faker = new Faker();

    public static Map<String,Object> getRandomSpartanMap(){

        Map<String, Object> bodyMap = new LinkedHashMap<>();
        bodyMap.put("name", faker.name().firstName());
        bodyMap.put("gender", faker.demographic().sex());
        bodyMap.put("phone", faker.number().numberBetween( 5000000000L ,10000000000L ));

        return bodyMap;

    }

    public static spartan getRandomSpartanPOJO(){

        spartan sp = new spartan();
        sp.setName( faker.name().firstName() );
        sp.setGender( faker.demographic().sex() );
        sp.setPhone( faker.number().numberBetween( 5000000000L ,10000000000L ) );

        return sp;

    }


}
