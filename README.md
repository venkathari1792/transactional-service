# transactional-service
Thread Safe Transactional Service
-----------------------------------------------------------------------------
Project consists of 3 REST API Services
-----------------------------------------------------------------------------

1. Post Transaction
 
End point: /transations

Description : Post a Transaction

Method : POST 

Request Body :
{
"amount": "20.3",
"timestamp": "2021-06-06T01:30:10.012Z"
}
Status Codes :

201 - CREATED
204 - Old Transaction (<60 secs)
400 - Incorrect JSON
422 - Unparsable Data

-----------------------------------------------------------------------------

2. Get Statistics

End point: /statistics

Description : To Fetch Sum, Avg, Max, Min and Count of transactions in last 60 seconds

Method : GET

Response : 

{
    "sum": "0.00",
    "avg": "0.00",
    "max": null,
    "min": null,
    "count": 0
}
-----------------------------------------------------------------------------

3. Delete Transactions

Method : DELETE

Request Body : {}

Status Code 

204 : Transactions Deleted

