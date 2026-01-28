package tests.US_1_2_3.Country;

import org.testng.annotations.Test;
import tests.BaseTest;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class AddCountry extends BaseTest {

    @Test
    public void addACountry(){
        Map<String, Object> body = new HashMap<>();
        body.put("name", "SibelCountry_" + System.currentTimeMillis());
        body.put("code", "SC" + new Random().nextInt(1000));
        body.put("hasState", true);

        request
                .body(body)
                .when()
                .post("school-service/api/countries")
                .then()
                .statusCode(201);
    }
}
