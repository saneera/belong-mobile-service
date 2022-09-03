
# Spring Boot, H2, JPA, Rest API

Build Restful CRUD API for a Customer Management using Spring Boot, H2 and JPA.

## Steps to Setup

**1. Clone the application**

```bash
git clone https://github.com/saneera/belong-mobile-service.git
```

**2. Open the project your favourite IDE (Recomended IntelliJ or Eclipse)**

**3. Run the app using gradle**

```bash
./gradlew spring-boot:run
```
The app will start running at <http://localhost:8080>

## Explore Rest APIs

The app defines following CRUD APIs.

### Customer

| Method | Url                                                         | Decription                                   | Sample Valid Request Body | 
|--------|-------------------------------------------------------------|----------------------------------------------| --------------------------- |
| POST   | /api/v1/customers                                           | Create Customer                              | [JSON](#signup) |
| GET    | /api/v1/customers                                           | Get Customers                                | [JSON](#signin) |
| GET    | /api/v1/customers/{id}                                      | Get Customers By cusromer Id                 | [JSON](#signin) |
| PATCH  | /api/v1/customers/{id}/phone-details/{phoneDetailId}/status | Activate Or Decativate Customer Phone Number | [JSON](#signin) |

