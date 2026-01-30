package tests.US_4_5;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import tests.BaseTest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class ExamManaging extends BaseTest {
    public static String ExamID;
    public static String invalidExamID="697b876fc137c05a387fe8e0";
    @Test(priority = 1)
    public void createExam() {

        String createBody= """
        {
            "id": null,
                      "name": "Java Core101",
                      "type": "EXAM",
                      "translateName": [],
                      "school": "695c16bdc138c05a387fe36f",
                      "gradeLevel": {
                          "id": "5e909860b0fd8113ea1432b4"
                      },
                      "academicPeriod": "695c1c03f620a8876fd6e521",
                      "active": true,
                      "description": "",
                      "note": "",
                      "agreementText": "",
                      "sendSMS": false,
                      "sms": "",
                      "sendEmailEnabled": false,
                      "emailMessage": {
                          "subject": "",
                          "content": ""
                      },
                      "registrationStartDate": "2026-01-21T00:00:00.000Z",
                      "registrationEndDate": "2026-01-30T00:00:00.000Z",
                      "paid": false,
                      "price": 0,
                      "bankAccount": null,
                      "sendEmailToRegistrar": false,
                      "registrarEmails": [],
                      "showDescFirst": false,
                      "showNoteFirst": false,
                      "noteEnabled": false,
                      "descEnabled": false,
                      "agreementEnabled": false,
                      "formTemplateId": null,
                      "documents": []
        }
                """;

        Response response =
                given()
                        .spec(request)
                        .body(createBody)

                        .when()
                        .post("school-service/api/exams")

                        .then()
                        .statusCode(201)
                        .body("id", notNullValue())
                        .body("name", equalTo("Java Core101"))
                        .extract().response();

        ExamID = response.jsonPath().getString("id");
    }
    @Test(priority = 2, description = "Creating exam by name box left blank")
    public void negativeExamFilling() {

        String createBody= """
        {
           "id": null,
                      "name": null,
                      "type": "EXAM",
                      "translateName": [],
                      "school": "695c16bdc138c05a387fe36f",
                      "gradeLevel": {
                          "id": "5e909860b0fd8113ea1432b4"
                      },
                      "academicPeriod": "695c1c03f620a8876fd6e521",
                      "active": true,
                      "description": "",
                      "note": "",
                      "agreementText": "",
                      "sendSMS": false,
                      "sms": "",
                      "sendEmailEnabled": false,
                      "emailMessage": {
                          "subject": "",
                          "content": ""
                      },
                      "registrationStartDate": "2026-01-21T00:00:00.000Z",
                      "registrationEndDate": "2026-01-30T00:00:00.000Z",
                      "paid": false,
                      "price": 0,
                      "bankAccount": null,
                      "sendEmailToRegistrar": false,
                      "registrarEmails": [],
                      "showDescFirst": false,
                      "showNoteFirst": false,
                      "noteEnabled": false,
                      "descEnabled": false,
                      "agreementEnabled": false,
                      "formTemplateId": null,
                      "documents": []
        }
                """;

        Response response =
                given()
                        .spec(request)
                        .body(createBody)

                        .when()
                        .post("school-service/api/exams")

                        .then()
                        .statusCode(400)
                        .body("message", containsString("NAME_CANNOT_EMPTY_OR_NULL"))
                        .extract().response();
    }
    @Test(priority = 3, dependsOnMethods = {"createExam"})
    public void getExamById() {
        given()
                .spec(request)
                .when()
                .get("school-service/api/exams/{examId}", ExamID)
                .then()
                .log().all()
                .statusCode(200);

    }
    @Test(priority = 3)
    public void updateExam() {
        Assert.assertNotNull(ExamID, "Exam ID is null");
        String updateBody = """
                   {
                    "id": "%s",
                      "name": "Java Core102",
                      "type": "EXAM",
                      "translateName": [],
                      "school": "695c16bdc138c05a387fe36f",
                      "gradeLevel": {
                          "id": "5e909860b0fd8113ea1432b4"
                      },
                      "academicPeriod": "695c1c03f620a8876fd6e521",
                      "active": true,
                      "description": "",
                      "note": "",
                      "agreementText": "",
                      "sendSMS": false,
                      "sms": "",
                      "sendEmailEnabled": false,
                      "emailMessage": {
                          "subject": "",
                          "content": ""
                      },
                      "registrationStartDate": "2026-01-21T00:00:00.000Z",
                      "registrationEndDate": "2026-01-30T00:00:00.000Z",
                      "paid": false,
                      "price": 0,
                      "bankAccount": null,
                      "sendEmailToRegistrar": false,
                      "registrarEmails": [],
                      "showDescFirst": false,
                      "showNoteFirst": false,
                      "noteEnabled": false,
                      "descEnabled": false,
                      "agreementEnabled": false,
                      "formTemplateId": null,
                      "documents": [],
                      "schoolId": "695c16bdc138c05a387fe36f"
                   }
                """.formatted(ExamID);

        given()
                .spec(request)
                .contentType("application/json")
                .body(updateBody)
                .log().all()
                .when()
                .put("/school-service/api/exams" )
                .then()
                .log().all()
                .statusCode(200);
    }

   @Test(priority = 5)
    public void updateExamWithInvalidId() {
        Assert.assertNotNull(invalidExamID, "Invalid Exam ID is null");
        String updateBody = """
                   {
                 {
                     "id": "697b876fc137c05a387fe8e0",
                     "name": "Java Core103",
                     "type": "EXAM",
                     "translateName": [],
                     "school": "695c16bdc138c05a387fe36f",
                     "gradeLevel": {
                         "id": "5e909860b0fd8113ea1432b4"
                     },
                     "academicPeriod": "695c1c03f620a8876fd6e521",
                     "active": true,
                     "description": "",
                     "note": "",
                     "agreementText": "",
                     "sendSMS": false,
                     "sms": "",
                     "sendEmailEnabled": false,
                     "emailMessage": {
                         "subject": "",
                         "content": ""
                     },
                     "registrationStartDate": "2026-01-21T00:00:00.000Z",
                     "registrationEndDate": "2026-01-30T00:00:00.000Z",
                     "paid": false,
                     "price": 0,
                     "bankAccount": null,
                     "sendEmailToRegistrar": false,
                     "registrarEmails": [],
                     "showDescFirst": false,
                     "showNoteFirst": false,
                     "noteEnabled": false,
                     "descEnabled": false,
                     "agreementEnabled": false,
                     "formTemplateId": null,
                     "documents": [],
                     "schoolId": "695c16bdc138c05a387fe36f"
                 }
                   }
                """.formatted(invalidExamID);

        given()
                .spec(request)
                .contentType("application/json")
                .body(updateBody)
                .when()
                .put("/school-service/api/exams/{InvalidExamId}", invalidExamID )
                .then()
                .statusCode(404);
    }
    @Test(priority = 6)
    public void deleteExam() {
        request
                .when()
                .delete("school-service/api/exams/" + ExamID)
                .then()
                .statusCode(204);
    }
    @Test(priority = 7)
    public void deleteExamWithInvalidID() {
        request
                .when()
                .delete("school-service/api/exams/{InvalidExamId}", invalidExamID)
                .then()
                .statusCode(404);
    }

}
