Feature: Customer Test

  Background:
    * def customerUrl = baseUrl + 'api/v1/customers'

  Scenario: Get Customers Pagination
    Given url customerUrl
    And param page = 0
    And param size = 5
    When method GET
    Then status 200

    And match response contains
    """
    [
       {
          "id":1,
          "firstName":"Isaiah",
          "lastName":"Benson",
          "phoneDetails":[
             {
                "id":1,
                "customerId":1,
                "phoneNumber":"0491570159",
                "status":"Inactive"
             },
             {
                "id":2,
                "customerId":1,
                "phoneNumber":"0491570152",
                "status":"Active"
             },
             {
                "id":3,
                "customerId":1,
                "phoneNumber":"0491570153",
                "status":"Inactive"
             }
          ]
       },
       {
          "id":2,
          "firstName":"Tommy",
          "lastName":"Guzman",
          "phoneDetails":[
             {
                "id":4,
                "customerId":2,
                "phoneNumber":"0491570159",
                "status":"Inactive"
             },
             {
                "id":5,
                "customerId":2,
                "phoneNumber":"0491570152",
                "status":"Active"
             },
             {
                "id":6,
                "customerId":2,
                "phoneNumber":"0491570153",
                "status":"Inactive"
             }
          ]
       },
       {
          "id":3,
          "firstName":"Kit",
          "lastName":"Poole",
          "phoneDetails":[
             {
                "id":7,
                "customerId":3,
                "phoneNumber":"0491570159",
                "status":"Inactive"
             },
             {
                "id":8,
                "customerId":3,
                "phoneNumber":"0491570152",
                "status":"Active"
             },
             {
                "id":9,
                "customerId":3,
                "phoneNumber":"0491570153",
                "status":"Inactive"
             }
          ]
       },
       {
          "id":4,
          "firstName":"Obadiah",
          "lastName":"Reyes",
          "phoneDetails":[
             {
                "id":10,
                "customerId":4,
                "phoneNumber":"0491570113",
                "status":"Active"
             },
             {
                "id":11,
                "customerId":4,
                "phoneNumber":"0491570114",
                "status":"Active"
             }
          ]
       },
       {
          "id":5,
          "firstName":"Orson",
          "lastName":"Holmes",
          "phoneDetails":[
             {
                "id":12,
                "customerId":5,
                "phoneNumber":"0491570115",
                "status":"Active"
             }
          ]
       }
    ]
    """

  Scenario: Get Customers  Pagination (2nd Page)
    Given url customerUrl
    And param page = 1
    And param size = 5
    When method GET
    Then status 200
    And match response contains
    """
    [
      {
        "id": 6,
        "firstName": "Dennis",
        "lastName": "Bacchus",
        "phoneDetails": [
          {
            "id": 13,
            "customerId": 6,
            "phoneNumber": "0491570109",
            "status": "Active"
          },
          {
            "id": 14,
            "customerId": 6,
            "phoneNumber": "0491570110",
            "status": "Active"
          }
        ]
      },
      {
        "id": 7,
        "firstName": "Miles",
        "lastName": "Heptinstall",
        "phoneDetails": [
          {
            "id": 15,
            "customerId": 7,
            "phoneNumber": "0491570106",
            "status": "Active"
          }
        ]
      },
      {
        "id": 8,
        "firstName": "Samuel",
        "lastName": "White",
        "phoneDetails": [
          {
            "id": 16,
            "customerId": 8,
            "phoneNumber": "0491570107",
            "status": "Active"
          }
        ]
      },
      {
        "id": 9,
        "firstName": "Barrett",
        "lastName": "Tucker",
        "phoneDetails": [
          {
            "id": 17,
            "customerId": 9,
            "phoneNumber": "0491570103",
            "status": "Inactive"
          }
        ]
      },
      {
        "id": 10,
        "firstName": "Osmond",
        "lastName": "Norris",
        "phoneDetails": [
          {
            "id": 18,
            "customerId": 10,
            "phoneNumber": "0491570104",
            "status": "Active"
          }
        ]
      }
    ]
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
            "status": "Inactive"
        },
        {
            "id": 8,
            "customerId": 3,
            "phoneNumber": "0491570152",
            "status": "Active"
        },
        {
            "id": 9,
            "customerId": 3,
            "phoneNumber": "0491570153",
            "status": "Inactive"
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
    Given url customerUrl+'/3/phone-details/9/status'
    And request
    """
    {
      "status" : "Active"
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
        "status": "Active"
    }
    """

  Scenario: active Phone number not found
    Given url customerUrl+'/3/phone-details/10/status'
    And request
    """
    {
      "status" : "Active"
    }
    """
    When method PATCH
    Then status 404
    And match response contains
    """
   {
       "statusCode":404,
       "message":"Not found Phone with id = 10",
       "description":"uri=/api/v1/customers/3/phone-details/10/status"
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
            "status": "Active"
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
           "status":"Active"
        }
      ]
    }
    """

