
# Spring Boot, H2, JPA, Rest API

Build Restful API for Manage Customer with phone details using Spring Boot, H2 and JPA.

## Steps to Setup

**1. Clone the application**

```bash
git clone https://github.com/saneera/belong-mobile-service.git
```

**2. Open the project your favourite IDE (Recomended IntelliJ or Eclipse)**

**3. Run the app using gradle**

```bash
./gradlew bootRun
```
The app will start running at <http://localhost:8080>

## Explore Rest APIs

The app defines following CRUD APIs.
 
### Customer

#### Swagger URL : http://localhost:8080/swagger-ui/index.html

| Method | Url                                                         | Decription                                  | 
|--------|-------------------------------------------------------------|---------------------------------------------| 
| POST   | /api/v1/customers                                           | Create Customer                             |
| GET    | /api/v1/customers                                           | Get Customers                               | 
| GET    | /api/v1/customers/{id}                                      | Get Customers By cusromer Id                | 
| PATCH  | /api/v1/customers/{id}/phone-details/{phoneDetailId}/status | Activate Or Decativate Customer Phone Number|
| GET    | /api/v1/customers/{id}/phone-details                        | Get all Phone numbers for customer          |


### Running tests 
```bash
Gradle: ./gradlew clean test
```

### Integration Test
 Integration Test located on src/test/java/karate



### Curl Command

#### Get All Customer

``` bash
curl -X 'GET' \
  'http://localhost:8080/api/v1/customers?page=0&size=5' \
  -H 'accept: */*'
```


#### Add Customer
``` bash
curl -X 'POST' \
  'http://localhost:8080/api/v1/customers' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "firstName": "<first_name>",
  "lastName": "<last_name>",
  "phoneDetails": [   
 {
      "phoneNumber": "<phone_number>",
      "status": "Active"
    }
  ]
}'
```

#### Get Specific Customer
``` bash
curl -X 'GET' \
  'http://localhost:8080/api/v1/customers/1' \
  -H 'accept: */*'
```


#### Update Specific Customer first Name and Last Name
``` bash
curl -X 'PUT' \
  'http://localhost:8080/api/v1/customers/1' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "id": 1,
  "firstName": "<Updated_first_name>",
  "lastName": "<Updated_last_name>"
  
```


#### Activate Phone Number

``` bash
curl -X 'PATCH' \
  'http://localhost:8080/api/v1/customers/1/phone-details/1/status' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "status": "Active"
}'
```

#### Inactivate Phone Number

``` bash
curl -X 'PATCH' \
  'http://localhost:8080/api/v1/customers/1/phone-details/1/status' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "status": "Inactive"
}'
```

#### Get All Phone Numbers for customer

``` bash
curl -X 'GET' \
  'http://localhost:8080/api/v1/customers/1/phone-details?page=0&size=10' \
  -H 'accept: */*'
```

