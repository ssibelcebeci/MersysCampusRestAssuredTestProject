package tests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;

import static io.restassured.RestAssured.given;

public class BaseTest {

    protected RequestSpecification request;
    protected static String token;

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://campus.techno.study";

        token =
                given()
                        .contentType(ContentType.JSON)
                        .body("""
                    {
                      "username": "TS_Staj",
                      "password": "TS_Staj2026"
                    }
                """)
                        .when()
                        .post("/auth/login")
                        .then()
                        .statusCode(200)
                        .extract()
                        .path("access_token");

        request = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("Authorization", "Bearer " + token);
    }
}