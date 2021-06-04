# Enjoy with Stock Application :)
Simple REST API application to manage stocks using Spring Boot 2.5

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
`status`: status of the operation such as (`OK`,`NOT_FOUND`,`BAD_REQUEST`,`....`)  
`resut`: in case of success operation depends on service (get one Stock or get list of Stocks) which is used either contains one Stock or return array of Stocks.  
`errors`: in case of error always contains list of `ApiError` Object






