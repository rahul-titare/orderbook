# orderbook
Orderbook Assignment


1. This application runs on 8081 port as defiend in application.properties
2. The UI for interacting with API calls can be accessed from http://localhost:8081/orderbook.html

REST API

Open Orderbook

1. Method: GET
2. URL: orderbook/open/{instrumentId}
3. Sample response body

   a. {"status":"success"}
   
   b. {"status":"error","errorCode":"ORDER_BOOK_ALREADY_OPENED"}

------------------------------------------------------------------------------------------------

Add Order

1. Method: POST
2. Request Content-Type: application/json
3. URL: orderbook/order/add
4. Sample request Body

    a. {"instrumentId":"ABC", "quantity":43, "price":100, "orderType":"LIMIT"}

5. Sample response body

    a. {"status":"success","data":{"orderId":"cc8f9293-3d6e-4ef7-9cea-aaef70bd01f8"}}

------------------------------------------------------------------------------------------------

Close Orderbook

1. Method: GET
2. URL: orderbook/close/{instrumentId}
3. Sample response body

    a. {"status":"success"}
    
    b. {"status":"error","errorCode":"ORDER_BOOK_ALREADY_CLOSED"}

------------------------------------------------------------------------------------------------

Execute Orderbook

1. Method: POST
2. Request Content-Type: application/json
3. URL: orderbook/execute
4. Sample request body

    a. {"instrumentId":"ABC","quantity":88,"price":100}

5. Sample response body

    a. {"status":"success","data":{"orderBookedExecuted":false}}

------------------------------------------------------------------------------------------------

Get Order details

1. Method: GET
2. URL: orderbook/order/get/{orderId from Add Order API response}
3. Sample response body

{"status":"success","data":{"quantity":43,"executionQuantity":0,"price":22.0,"executionPrice":0.0,"status":null,"type":"LIMIT"}}

------------------------------------------------------------------------------------------------

Get ALL/VALID/INVALID order statics statistics

1. Method: GET
2. URL

  orderbook/stats/{instrumentId}/ALL
  
  orderbook/stats/{instrumentId}/VALID
  
  orderbook/stats/{instrumentId}/INVALID

3. Sample response body

{"status":"success","data":{"instrumentId":"ABC","numOrders":2,"demand":86,"maxOrder":{"orderId":"e64f8a0f-31e1-424e-b8bf-ff466a3858de","quantity":43,"entryDate":"2019-05-08T22:48:15.188","price":22.0,"orderType":"LIMIT"},"minOrder":{"orderId":"e64f8a0f-31e1-424e-b8bf-ff466a3858de","quantity":43,"entryDate":"2019-05-08T22:48:15.188","price":22.0,"orderType":"LIMIT"},"firstOrder":{"orderId":"e64f8a0f-31e1-424e-b8bf-ff466a3858de","quantity":43,"entryDate":"2019-05-08T22:48:15.188","price":22.0,"orderType":"LIMIT"},"lastOrder":{"orderId":"cc8f9293-3d6e-4ef7-9cea-aaef70bd01f8","quantity":43,"entryDate":"2019-05-08T22:48:23.736","price":22.0,"orderType":"LIMIT"},"limitBreakDown":[{"price":22.0,"demand":86}]}}


from master