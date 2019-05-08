# orderbook
Orderbook Assignment


1. This application runs on 8081 port as defiend in application.properties
2. The UI for interacting with API calls can be accessed from http://localhost:8081/orderbook.html

REST API

1. Open Orderbook
Method: GET
URL: orderbook/open/{instrumentId}
Sample response body
1. {"status":"success"}
2. {"status":"error","errorCode":"ORDER_BOOK_ALREADY_OPENED"}

------------------------------------------------------------------------------------------------

2. Add Order
METHOD: POST
Request Content-Type: application/json
URL: orderbook/order/add
Sample request Body
{"instrumentId":"ABC", "quantity":43, "price":100, "orderType":"LIMIT"}

Sample response body
{"status":"success","data":{"orderId":"cc8f9293-3d6e-4ef7-9cea-aaef70bd01f8"}}

------------------------------------------------------------------------------------------------

3. Close Orderbook
Method: GET
URL: orderbook/close/{instrumentId}
Sample response body
1. {"status":"success"}
2. {"status":"error","errorCode":"ORDER_BOOK_ALREADY_CLOSED"}

------------------------------------------------------------------------------------------------

4. Execute Orderbook
METHOD: POST
Request Content-Type: application/json
URL: orderbook/execute
Sample request body
{"instrumentId":"ABC","quantity":88,"price":100}

Sample response body
{"status":"success","data":{"orderBookedExecuted":false}}

------------------------------------------------------------------------------------------------

5. Get Order details
Method: GET
URL: orderbook/order/get/{orderId from Add Order API response}
Sample response body
{"status":"success","data":{"quantity":43,"executionQuantity":0,"price":22.0,"executionPrice":0.0,"status":null,"type":"LIMIT"}}

------------------------------------------------------------------------------------------------

6. Get ALL/VALID/INVALID order statics statistics
Method: GET
URL: orderbook/stats/{instrumentId}/ALL
URL: orderbook/stats/{instrumentId}/VALID
URL: orderbook/stats/{instrumentId}/INVALID
Sample response body
{"status":"success","data":{"instrumentId":"ABC","numOrders":2,"demand":86,"maxOrder":{"orderId":"e64f8a0f-31e1-424e-b8bf-ff466a3858de","quantity":43,"entryDate":"2019-05-08T22:48:15.188","price":22.0,"orderType":"LIMIT"},"minOrder":{"orderId":"e64f8a0f-31e1-424e-b8bf-ff466a3858de","quantity":43,"entryDate":"2019-05-08T22:48:15.188","price":22.0,"orderType":"LIMIT"},"firstOrder":{"orderId":"e64f8a0f-31e1-424e-b8bf-ff466a3858de","quantity":43,"entryDate":"2019-05-08T22:48:15.188","price":22.0,"orderType":"LIMIT"},"lastOrder":{"orderId":"cc8f9293-3d6e-4ef7-9cea-aaef70bd01f8","quantity":43,"entryDate":"2019-05-08T22:48:23.736","price":22.0,"orderType":"LIMIT"},"limitBreakDown":[{"price":22.0,"demand":86}]}}
