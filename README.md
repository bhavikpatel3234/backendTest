# backendTest
Monitoring server API
1) Start monitor
URL : http://localhost:8080/monitor/start
Request-type : POST
Request-header : 
		Content-Type : application/json 
Request-body : 
{
	"timeInterval": 5, // Provide Time in seconds
	"url": "https://api.test.paysafe.com/accountmanagement/monitor"
}
Response :
Monitor has been started.

2) Stop monitor
URL : http://localhost:8080/monitor/stop
Request-type : GET 
Response :
Monitor has been stopped.

3) Overview
URL : http://localhost:8080/overview
Request-type : GET 
Response : 
{
    "statusCode": 200,
    "logs": [
        {
            "createDateTime": "2019-12-08T23:32:50.231",
            "httpStatus": 200,
            "message": "https://api.test.paysafe.com/accountmanagement/monitor"
        },
        {
            "createDateTime": "2019-12-08T23:32:55.096",
            "httpStatus": 200,
            "message": "https://api.test.paysafe.com/accountmanagement/monitor"
        }
    ]
}