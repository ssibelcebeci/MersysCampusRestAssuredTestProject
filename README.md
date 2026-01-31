# API Test Automation – Rest Assured (User Story Based)

This repository contains an **API Test Automation project** developed using **Java & Rest Assured**, structured strictly according to **User Stories (US)** and their **Acceptance Criteria**.

The project validates a campus/school management system by covering authentication, setup operations, CRUD scenarios, performance checks, and negative test cases.

---

## 🎯 Project Overview

* User Story–driven test design
* Modular test structure per User Story group
* Token-based authentication
* CRUD & negative scenario coverage
* Performance validations (response time)

---

## 🛠 Tech Stack

* **Java**
* **Rest Assured**
* **TestNG**
* **Maven**
* **JSON**
* **Git & GitHub**

---

## 🌍 Environment

* **Base URL:**
  `https://campus.techno.study`

* **API Path:**
  `/school-service/api/...`

All secured endpoints require a valid **Bearer Token** obtained from the login API.

---

## 📁 Project Structure

```
src
 └── test
     └── java
         └── tests
             ├── US_1_2_3
             │   ├── Login.java
             │   ├── CreateCountry.java
             │   └── CRUDState.java
             │
             ├── US_4_5
             │   ├── CRUDCity.java
             │   └── ExamManaging.java
             │
             ├── US_06_07
             │   └── Students
             │       └── AddRemoveStudentGroup.java
             │
             ├── US_10_11
             │   ├── CRUDEducation.java
             │   └── CRUDIncidentType.java
             │
             └── BaseTest.java
```

Each folder represents a **logical grouping of User Stories**, ensuring traceability between requirements and automated tests.

---

## Authentication (US_1)

**Login.java**

* Invalid credentials → **HTTP 400**
* Valid credentials → **HTTP 200**
* Access token is extracted and reused across all test classes

---

## Setup Operations

### Country & State (US_2, US_3)

* Create country with state support
* Perform full CRUD on states
* Validate response body fields
* List operations must complete within **1000 ms**

---

## City Management (US_4)

**CRUDCity.java**

* Create, list, update, and delete cities
* City list response time ≤ **1000 ms**
* City detail response time ≤ **200 ms**
* Status codes validated for each operation

---

## Exam Management (US_5)

**ExamManaging.java**

* Create, edit, list, and delete exams
* Mandatory field validations
* Invalid ID handling → **404**
* Missing required fields → **400**

---

## Student Groups (US_6, US_7)

**AddRemoveStudentGroup.java**

* Create student groups
* Add students to groups
* Remove students from groups
* Validate invalid group/student ID scenarios
* Successful operations → **200 / 201**

---

## Education Standards (US_10)

**CRUDEducation.java**

* Full CRUD operations
* School-based filtering
* Delete operation returns **204**
* Invalid delete attempts return proper error messages

---

## Incident Types (US_11)

**CRUDIncidentType.java**

* Create, update, list, and delete incident types
* Validate min/max negative score rules
* Invalid ID handling → **400**

---

## Performance Criteria

The following performance constraints are enforced:

* List endpoints: **≤ 1000 ms**
* Detail endpoints: **≤ 200 ms**

Tests fail if response times exceed defined limits.

---

## Testing Principles Applied

* User Story–based test coverage
* Positive & negative scenario testing
* Reusable base configuration
* Clear separation of concerns
* Maintainable and scalable test design

---

## Collaborators

* **Sibel**
* **Gürhan**
* **Ramazan**
* **İbrahim**

This project was developed collaboratively as part of an API testing practice, with shared responsibility across analysis, test design, and implementation.


---

## Notes

This project was created for **learning, practice, and portfolio purposes**, following real world API testing standards.
