package tests.US_10_11;

import io.restassured.response.Response;
import org.testng.annotations.Test;
import tests.BaseTest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class CRUDEducation extends BaseTest {

    private String schoolID;

    @Test(priority = 1, description = "Login Campus")
    public void Login() {
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

    @Test(priority = 2, description = "Create Education Standard")
    public void createEducation() {
        String createBody = """
                {
                  "id": null,
                  "name": "deneme",
                  "description": "asd",
                  "gradeLevelId": "5e909860b0fd8113ea1432b4",
                  "subjectId": "6568e07f5823fc7b2346f5d1",
                  "gradeCategoriesTemplateId": "65bba1cc4e7e60513ec6e748",
                  "gradeCategoryId": "96b4-5c5a",
                  "calculationMode": "MEAN",
                  "numberOfScores": 5,
                  "scoreWeights": [
                    1,
                    1,
                    1,
                    1,
                    1
                  ],
                  "parentStandardCalculationStarategy": "TURN_OFF"
                }
                """;
        Response response =
                given()
                        .spec(request)
                        .body(createBody)

                        .when()
                        .post("school-service/api/education-standard")

                        .then()
                        .body("id", notNullValue())
                        .body("name", equalTo("deneme"))
                        .statusCode(201)
                        .extract().response();

        schoolID = response.path("id");

    }

    @Test(priority = 3, description = "Add Education Standard Name Null")
    public void addEducationStandardNameNull() {

        String body = """
                {
                  "id": null,
                  "name": null,
                  "description": "asd",
                  "gradeLevelId": "5e909860b0fd8113ea1432b4",
                  "subjectId": "6568e07f5823fc7b2346f5d1",
                  "gradeCategoriesTemplateId": "65bba1cc4e7e60513ec6e748",
                  "gradeCategoryId": "96b4-5c5a",
                  "calculationMode": "MEAN",
                  "numberOfScores": 5,
                  "scoreWeights": [1,1,1,1,1],
                  "parentStandardCalculationStarategy": "TURN_OFF"
                }
                """;
        Response response =
                given()
                        .spec(request)
                        .body(body)
                        .when()
                        .post("school-service/api/education-standard");

        response.then()
                .log().all()
                .statusCode(400);
    }

    @Test(priority = 4, description = "Get list Education")
    public void getEducationStandardId() {

        Response response =
                given()

                        .spec(request)

                        .when()
                        .get("school-service/api/education-standard/{schoolID}", schoolID)

                        .then()
                        .log().all()
                        .statusCode(200)
                        .extract().response();

        response.body().prettyPrint();

    }

    @Test(priority = 5, description = "Update Education ")
    public void updateEducation() {

        String updateBody = """
                {
                  "id": "%s",
                  "name": "deneme1555",
                  "description": "asd",
                  "gradeLevelId": "5e909860b0fd8113ea1432b4",
                  "subjectId": "6568e07f5823fc7b2346f5d1",
                  "gradeCategoriesTemplateId": "65bba1cc4e7e60513ec6e748",
                  "gradeCategoryId": "96b4-5c5a",
                  "calculationMode": "MEAN",
                  "numberOfScores": 5,
                  "scoreWeights": [1,1,1,1,1],
                  "parentStandardCalculationStarategy": "TURN_OFF"
                }
                """.formatted(schoolID);

        given()
                .spec(request)
                .body(updateBody)

                .when()
                .put("school-service/api/education-standard")

                .then()
                .log().all()
                .statusCode(200);

    }

    @Test(priority = 6, description = "Delete Education Standard Id")
    public void deleteEducationId() {

        given()
                .spec(request)

                .when()
                .delete("school-service/api/education-standard/{schoolID}", schoolID)

                .then()
                .log().all()
                .statusCode(204);

    }
}
