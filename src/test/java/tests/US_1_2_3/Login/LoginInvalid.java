package tests.US_1_2_3.Login;

import io.restassured.response.Response;
import org.testng.annotations.Test;
import tests.BaseTest;

import static io.restassured.RestAssured.given;

public class LoginInvalid extends BaseTest {

    @Test
    public void loginNegative(){
        String createBody= """
                {
                  "username": "TS",
                  "password": "TS_Staj2026",
                  "rememberMe": true
                }
                """;

        Response response=
                given()
                        .spec(request)
                        .body(createBody)
                        .when()
                        .post("auth/login")
                        .then()
                        .log().all()
                        .statusCode(401)
                        .extract().response();

        System.out.println(response.body());
    }
}
