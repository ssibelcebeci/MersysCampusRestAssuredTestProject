package tests.US_06_07.Students;

import io.restassured.response.Response;
import org.testng.annotations.Test;
import tests.BaseTest;

import static io.restassured.RestAssured.given;

public class AddRemoveStudentGroup extends BaseTest {

    private String groupId;
    private String studentId;

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
    public void createNewGroup() {
        String createBody = """
                {
                  "id": null,
                  "schoolId": "695c16bdc138c05a387fe36f",
                  "name": "Test Class",
                  "description": "Test",
                  "active": true,
                  "publicGroup": true,
                  "showToStudent": false
                }
                """;

        Response response =
                given()
                        .spec(request)
                        .body(createBody)

                        .when()
                        .post("school-service/api/student-group")

                        .then()
                        .log().all()
                        .statusCode(201)
                        .extract().response();

        groupId = response.path("id");
    }

    @Test(priority = 3)
    public void getFirstStudentId() {
        String createBody = """
                {
                  "schoolId": "695c16bdc138c05a387fe36f",
                  "academicPeriodId": "695c1c03f620a8876fd6e521",
                  "gradeLevelId": "5e909860b0fd8113ea1432b4",
                  "classId": "ALL",
                  "studentIds": [],
                  "exceptIds": [],
                  "gender": null,
                  "status": null,
                  "departmentId": null,
                  "schoolClassIds": [],
                  "pageIndex": 0,
                  "pageSize": 10
                }
                """;

        Response response =
                given()
                        .queryParam("page", 0)
                        .queryParam("size", 10)
                        .spec(request)
                        .body(createBody)

                        .when()
                        .post("school-service/api/classes/students/pageable")

                        .then()
                        .log().all()
                        .statusCode(200)
                        .extract().response();

        studentId = response.path("content[0].id");
    }

    @Test(priority = 4)
    public void addStudentToGroup() {
        String createBody = String.format("[\"%s\"]", studentId);

        given()
                .queryParam("page", 0)
                .queryParam("size", 10)
                .pathParam("id", groupId)
                .spec(request)
                .body(createBody)

                .when()
                .post("/school-service/api/student-group/{id}/add-students")

                .then()
                .log().all()
                .statusCode(200)
                .extract().response();
    }

    @Test(priority = 5)
    public void deleteAddedStudent() {
        String createBody = String.format("[\"%s\"]", studentId);

        given()
                .queryParam("page", 0)
                .queryParam("size", 10)
                .pathParam("id", groupId)
                .spec(request)
                .body(createBody)

                .when()
                .post("/school-service/api/student-group/{id}/add-students")

                .then()
                .log().all()
                .statusCode(200)
                .extract().response();
    }

    @Test(priority = 6)
    public void deleteCreatedGroup() {

        given()
                .pathParam("id", groupId)
                .spec(request)

                .when()
                .delete("/school-service/api/student-group/{id}")

                .then()
                .log().all()
                .statusCode(200)
                .extract().response();
    }
}
