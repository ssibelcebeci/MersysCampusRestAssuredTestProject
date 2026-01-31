package tests.US_4_5;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import tests.BaseTest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class CRUDCity extends BaseTest {
    public static String CityID;

    @Test(priority = 1)
    public void loginAndGetToken() {
        String createBody = """
                {
                  "username": "TS_Staj",
                  "password": "TS_Staj2026",
                  "rememberMe": true
                }
                """;

        Response response =
                given()
                        .spec(request)
                        .body(createBody)

                        .when()
                        .post("auth/login")

                        .then()
                        .log().all()
                        .statusCode(200)
                        .extract().response();

        String token = response.path("access_token");
    }

    @Test(priority = 2)
    public void createCity() {

        String createBody = """
                {
                    "name": "Akhisar",
                    "country": {
                        "id": "5cad7e76bc32694aad5298ce"
                    },
                    "state": null,
                    "translateName": []
                }
                """;

        Response response =
                given()
                        .spec(request)
                        .body(createBody)

                        .when()
                        .post("school-service/api/cities")

                        .then()
                        .statusCode(201)
                        .body("id", notNullValue())
                        .body("name", equalTo("Akhisar"))
                        .extract().response();

        CityID = response.jsonPath().getString("id");
    }

    @Test(priority = 3)
    public void updateCity() {
        Assert.assertNotNull(CityID, "City ID is null");
        String updateBody = """
                   {
                   "id" : "%s",
                       "name": "Turgutlu",
                       "country": {
                           "id": "5cad7e76bc32694aad5298ce"
                       },
                       "state": null,
                       "translateName": []
                   }
                """.formatted(CityID);

        given()
                .spec(request)
                .contentType("application/json")
                .body(updateBody)
                .log().all()
                .when()
                .put("/school-service/api/cities")
                .then()
                .log().all()
                .statusCode(200);
    }

    @Test(priority = 4)
    public void deleteCity() {
        request
                .when()
                .delete("school-service/api/cities/" + CityID)
                .then()
                .statusCode(200);
    }
}
