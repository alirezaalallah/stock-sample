# Enjoy with Stock Sample Application! :slightly_smiling_face:
Simple REST API application to manage stocks using [Spring Boot](https://spring.io/)

Below items represent services which are ready to use:

  * Create new Stock
  * Update existing Stock
  * Delete existing Stock
  * Get list of all Stocks
  * Get specific Stock

We will describe each service in detail.

## How to run Stock Application?

You can run REST API application with below command:
>`mvn spring-boot:run`

## Application response format (ApiResponse)
Below Json represent total structure of Stock Application's response:
```
{
  "status": STATUS TEST,
  "result": Stock | array(Stock)
  "errors": array(ApiError)
}
```
`status`: status of the operation such as (`ACCEPT`,`NOT_FOUND`,`BAD_REQUEST`,`....`)  
`resut`: in case of success operation depends on service (get one Stock or get list of Stocks) which is used either contains one Stock or return array of Stocks.  
`errors`: in case of error always contains list of `ApiError` Object

### ApiError format
Below json represent error object:
```
{
  "reason": "INVALID_VALUE",
  "message": "Stock id should be digit",
  "param": "id"
}
```
### Example of success response (Get existing Stock)

```
{
  "status": "ACCEPTED",
  "result": {
    "uniqueId": 1,
    "name": "Ali",
    "currentPrice": 50000.00,
    "lastUpdate": "2021-06-02T00:00:00"
  }
}
```
### Example of fail resonse 
```
{
  "status": "NOT_FOUND",
  "errors": [
    {
      "reason": "STOCK_NOT_FOUND",
      "message": "Stock not found"
    }
  ]
}
```

## List Of REST API which are available:

### Create new Stock
`METHOD`: `POST`  
`URL`: `/api/stocks`

###### Request body sample:  
```
{
  "name": "Adrian",
  "price": 90000
}
```
###### Possible responses:
`ACCEPT`: Create Stock successfully.  
`BAD_REQUEST`: Send invalid request to server (For example: Name of Stock which is required is aleady exist in database or Sending invalid data format,...)  

### Update existing Stock
`METHOD`: `PUT`  
`URL`: `/api/stocks/{id}`

###### Possible responses:
`ACCEPT`: Update Stock successfully.  
`NOT_FOUND`: Stock id which is requested does not exists in database.
`BAD_REQUEST`: Send invalid request to server (For example: Sending invalid data format,...)  

### Delete existing Stock
`METHOD`: `DELETE`  
`URL`: `/api/stocks/{id}`

###### Possible responses:
`ACCEPT`: DELETE Stock successfully.  
`NOT_FOUND`: Stock id which is requested does not exists in database.
`BAD_REQUEST`: Send invalid request to server (For example: Sending invalid data format,...)  


### Get specific Stock
`METHOD`: `GET`  
`URL`: `/api/stocks/{id}`

###### Possible responses:
`ACCEPT`: Get Stock object under `ApiResponse` format successfully.  
`NOT_FOUND`: Stock id which is requested does not exists in database.
`BAD_REQUEST`: Send invalid request to server (For example: Sending invalid data format,...)  


### Get list of all Stocks
`METHOD`: `GET`  
`URL`: `/api/stocks`

###### Possible responses:
`ACCEPT`: Get list of Stock objects under `ApiResponse` format successfully (Could be empty list if there are no Stocks exists in database). 
`BAD_REQUEST`: Reject request by server for some reason.










