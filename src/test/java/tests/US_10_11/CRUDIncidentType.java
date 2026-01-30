package tests.US_10_11;

import io.restassured.response.Response;
import org.testng.annotations.Test;
import tests.BaseTest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class CRUDIncidentType extends BaseTest {

    private String incidentID;

    @Test(priority = 2, description = "Verify incident type search returns valid response with correct structure")
    public void testSearchIncidentTypes_Success() {

        String requestBody = """
                {
                  "tenantId": "5e39ade1cb4c066deeb43015"
                }
                """;

        Response response =
                given()
                        .spec(request)
                        .body(requestBody)
                        .when()
                        .post("school-service/api/incident-type/search")
                        .then()
                        .log().all()
                        .extract()
                        .response();

        response.then()
                .statusCode(200);
    }

    @Test(priority = 3, description = "Add Incident Type")
    public void createIncidentType() {
        String createBody = """
                {
                  "id": null,
                  "name": "asdasd",
                  "active": true,
                  "tenantId": "5e39ade1cb4c066deeb43015",
                  "minPoint": 10,
                  "maxPoint": 70,
                  "academicBased": false,
                  "permissions": [
                    "ROLE_USER"
                  ],
                  "notifyWithEmail": false,
                  "notifyWithMessage": false
                }
                """;
        Response response =
                given()
                        .spec(request)
                        .body(createBody)

                        .when()
                        .post("school-service/api/incident-type")

                        .then()
                        .body("id", notNullValue())
                        .body("name", equalTo("asdasd"))
                        .statusCode(201)
                        .extract().response();

        incidentID = response.path("id");
        System.out.println("CREATED incidentID = " + incidentID);
    }

    @Test(priority = 4, description = "Get Incident Type Id")
    public void getIncidentId() {

        Response response =
                given()
                        .spec(request)

                        .when()
                        .get("school-service/api/incident-type/{incidentID}", incidentID)

                        .then()
                        .log().all()
                        .statusCode(200)
                        .extract().response();

        response.body().prettyPrint();
    }

    @Test(priority = 5, description = "Update Incident Type Name")
    public void updateIncidentType() {

        String updateBody = """
                {
                  "id": "%s",
                  "name": "deneyelim",
                  "active": true,
                  "tenantId": "5e39ade1cb4c066deeb43015",
                  "minPoint": 10,
                  "maxPoint": 54,
                  "academicBased": false,
                  "permissions": [
                    "ROLE_ADMIN"
                  ],
                  "notifyWithEmail": false,
                  "notifyWithMessage": false
                }
                """.formatted(incidentID);

        given()
                .spec(request)
                .body(updateBody)

                .when()
                .put("school-service/api/incident-type")

                .then()
                .log().all()
                .statusCode(200);

    }

    @Test(priority = 6, description = "Delete Incident Type")
    public void deleteIncidentId() {

        given()
                .spec(request)

                .when()
                .delete("school-service/api/incident-type/{incidentID}", incidentID)

                .then()
                .log().all()
                .statusCode(200);

    }
}