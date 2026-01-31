package tests.US_4_5;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import tests.BaseTest;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.given;

public class ExamManaging extends BaseTest {

    private String examId;
    private final String invalidExamId = "695c1c03f620a8876fd6e599";

    @Test(priority = 1)
    public void createExam() {
        Map<String, Object> examBody = new HashMap<>();
        examBody.put("id", null);
        examBody.put("name", "Java Core 101 - Sibel");
        examBody.put("type", "EXAM");
        examBody.put("school", "695c16bdc138c05a387fe36f");
        examBody.put("academicPeriod", "695c1c03f620a8876fd6e521");
        examBody.put("active", true);

        Map<String, String> gradeLevel = new HashMap<>();
        gradeLevel.put("id", "5e909860b0fd8113ea1432b4");
        examBody.put("gradeLevel", gradeLevel);

        Response response =
                given()
                        .spec(request)
                        .body(examBody)
                        .when()
                        .post("/school-service/api/exams")
                        .then()
                        .statusCode(201)
                        .body("id", notNullValue())
                        .body("name", equalTo("Java Core 101 - Sibel"))
                        .extract().response();

        examId = response.path("id");
    }

    @Test(priority = 2)
    public void createExamNegative() {
        Map<String, Object> examBody = new HashMap<>();
        examBody.put("name", null);
        examBody.put("type", "EXAM");
        examBody.put("academicPeriod", "695c1c03f620a8876fd6e521");

        given()
                .spec(request)
                .body(examBody)
                .when()
                .post("/school-service/api/exams")
                .then()
                .statusCode(400);
    }

    @Test(priority = 3, dependsOnMethods = "createExam")
    public void updateExam() {
        Map<String, Object> updateBody = new HashMap<>();
        updateBody.put("id", examId);
        updateBody.put("name", "Java Core 102 - Updated");
        updateBody.put("type", "EXAM");
        updateBody.put("school", "695c16bdc138c05a387fe36f");
        updateBody.put("academicPeriod", "695c1c03f620a8876fd6e521");

        Map<String, String> gradeLevel = new HashMap<>();
        gradeLevel.put("id", "5e909860b0fd8113ea1432b4");
        updateBody.put("gradeLevel", gradeLevel);

        given()
                .spec(request)
                .body(updateBody)
                .when()
                .put("/school-service/api/exams")
                .then()
                .statusCode(200)
                .body("name", equalTo("Java Core 102 - Updated"));
    }

    @Test(priority = 4)
    public void updateExamNegative() {
        Map<String, Object> updateBody = new HashMap<>();
        updateBody.put("id", invalidExamId);
        updateBody.put("name", "Invalid Update");

        try {
            given()
                    .spec(request)
                    .body(updateBody)
                    .when()
                    .put("/school-service/api/exams")
                    .then()
                    .statusCode(400);
        } catch (Exception e) {
            Assert.assertTrue(e.getMessage().contains("400"), "Expected status code 400 not found: " + e.getMessage());
        }
    }

    @Test(priority = 5, dependsOnMethods = "createExam")
    public void deleteExam() {
        given()
                .spec(request)
                .when()
                .delete("/school-service/api/exams/" + examId)
                .then()
                .statusCode(204);
    }

    @Test(priority = 6)
    public void deleteExamNegative() {
        try {
            given()
                    .spec(request)
                    .when()
                    .delete("/school-service/api/exams/" + invalidExamId)
                    .then()
                    .statusCode(500);
        } catch (Exception e) {
            Assert.assertTrue(e.getMessage().contains("500"), "Expected status code 500 not found: " + e.getMessage());
        }
    }
}