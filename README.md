# Springboot DDD Kickoff Project
This Project showcases a simple Spring Boot application following Domain-Driven Design (DDD) principles and can be used to as an orientation or kickoff your own DDD project. 
This Application demonstrates how to structure a Spring Boot application using DDD concepts, including entities, value objects, repositories, services, and controllers.
Can be used as a starting point for building more complex applications, and it provides a clear separation of concerns between different layers of the application.

It provides a RESTful API to manage staff members, note that there is no persistence layer implemented, so all data is stored in memory and will be lost when the application is stopped. 
# StaffMember API
This service provides endpoints to manage staff members, including retrieval, creation, and encrypted identities.

---

## Running the Service
0. Prerequisites:
Java 21 and Gradle Version 9.0.0 (for local development) or Docker (for containerized deployment)

1. Clone the repository
```bash
git clone https://github.com/niklasbecht/springboot-ddd-starter.git
```

### Option 1: Run Locally with Spring Boot
2. Start the application (Spring Boot):

```bash
./gradlew bootRun
```

Service will be available at:  
http://localhost:8080

### Option 2: Run with Docker
2. Build the Docker image:
```bash
docker build -t staff-member-service .
```

3. Run the container:
```bash
docker run -d -p 8080:8080 --name staff-member-app staff-member-service
```

Service will be available at:  
http://localhost:8080

To stop the container:
```bash
docker stop staff-member-app && docker rm staff-member-app
```

---
# API 
The full functionality of the service can be explored via the **Swagger UI**.

Please visit 
[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
to explore and use the functionalities of this service.

Retrieve the api definition via [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs).



## Example cURL Commands

## 1. Health Check
```bash
curl -X GET "http://localhost:8080/" -H "Accept: application/json"
```

### Get all staff members
```bash
curl -X GET "http://localhost:8080/staffmembers" -H "Accept: application/json"
```
### Get all staff members by skill
```bash
curl -X GET "http://localhost:8080/staffmembers?skill=DESIGNPATTERNS" -H "Accept: application/json"
```
### Get all staff members encrypted
```bash
curl -X GET "http://localhost:8080/staffmembers?encrypted=true" -H "Accept: application/json"
```

### Add a New Staff Member
```bash
curl -X POST "http://localhost:8080/staffmembers" \
-H "Content-Type: application/json" \
-d '{
  "name": "AliceWonder",
  "identity": {
    "firstName": "Alice",
    "lastName": "Wonder"
  },
  "birthday": "1990-05-15",
  "skills": ["JAVA","DESIGNPATTERNS"]
}'
```

## Additional Nodes
- Skills must be one of the predefined enums values [JAVA, KOTLIN, ENGINEERING , DESIGNPATTERNS , DDD].
- included test are performed automatically when pushed to repository.
- please node that added staff members are stored inmemory are lost on server shutdown.
---
## Further Improvements
- only user vavr Collections instead of java collections i.e no more Option of Optional
- add a cipher key to the get encrypted endpoints to allow different encryption
- CaesarCypher: add support negative keys
- maybe use enums in dto and let spring validate the input
- investigate further on potential vulnerability see https://www.mend.io/vulnerability-database/CVE-2025-48924?utm_source=JetBrains 
- introduce more interfaces i.e StaffMemberController
