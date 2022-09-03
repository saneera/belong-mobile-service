Feature: Customer Test
  Background:
    * def customerUrl = baseUrl + 'api/v1/customers'

  Scenario: Get Customers
    Given url customerUrl
    When method GET
    Then status 200 o/
    And match response contains
    """
    {
     "firstName":"Isaiah",
     "lastName":"Benson",
     "id":1,
     "phoneDetails":'#notnull'
    }
    """




  Scenario: Get Customer by id
    Given url customerUrl+'/3'
    When method GET
    Then status 200
    And match response contains
    """
   {
    "id": 3,
    "firstName": "Kit",
    "lastName": "Poole",
    "phoneDetails": [
        {
            "id": 7,
            "customerId": 3,
            "phoneNumber": "0491570159",
            "status": "INACTIVE"
        },
        {
            "id": 8,
            "customerId": 3,
            "phoneNumber": "0491570152",
            "status": "ACTIVE"
        },
        {
            "id": 9,
            "customerId": 3,
            "phoneNumber": "0491570153",
            "status": "INACTIVE"
        }
     ]
    }
    """

  Scenario: Get Customer by id not found
    Given url customerUrl+'/1000'
    When method GET
    Then status 404
    And match response contains
    """
   {
       "statusCode":404,
       "message":"Not found Customer with id = 1000",
       "description":"uri=/api/v1/customers/1000"
    }
    """

  Scenario: active Phone number
    Given url customerUrl+'/3/phone-numbers/9/status'
    And request
    """
    {
      "status" : "ACTIVE"
    }
    """
    When method PATCH
    Then status 200
    And match response contains
    """
   {
        "id": 9,
        "customerId": 3,
        "phoneNumber": "0491570153",
        "status": "ACTIVE"
    }
    """

  Scenario: active Phone number not found
    Given url customerUrl+'/3/phone-numbers/10/status'
    And request
    """
    {
      "status" : "ACTIVE"
    }
    """
    When method PATCH
    Then status 404
    And match response contains
    """
   {
       "statusCode":404,
       "message":"Not found Phone with id = 10",
       "description":"uri=/api/v1/customers/3/phone-numbers/10/status"
    }
    """


  Scenario: Save Customer
    Given url customerUrl
    And request
    """
    {
     "firstName":"Saneera",
     "lastName":"Yapa",
     "phoneDetails": [
     {
            "phoneNumber": "0425429308",
            "status": "ACTIVE"
       }
     ]
    }
    """
    When method POST
    Then status 200
    And match response contains
    """
   {
      "id":12,
     "firstName":"Saneera",
     "lastName":"Yapa",
     "phoneDetails":[
        {
           "id":20,
           "phoneNumber":"0425429308",
           "customerId":12,
           "status":"ACTIVE"
        }
      ]
    }
    """

