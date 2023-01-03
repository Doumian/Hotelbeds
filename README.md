# Running Instructions

In order to run the app, the command is:

```mvn spring-boot:run```

This will expose a series of endpoints and start our Monitoring Process.


# Test Instructions

In order to run the tests, the command is:

```mvn test "-Dspring.profiles.active=test"```

Every mvn command should follow this profiling, if we want to prevent starting the Monitoring process.

``` mvn clean install "-Dspring.profiles.active=test"```


# Hacker Detection - Solution

I developed several services for this solution:
- HackerDetectionService
  
      Will present our logic behind identifiying a hacker attempt log record.

- LogService:
  
      Will allow us to generate fully random 100k+ log records in a single logfile with a custom probability for Hacker Attemps into ```logs/tmp/``` for futher manual testing.

      Will also be responsible for reading Logfiles.

- MonitoringService

      Background service that will allow us to monitor logs/monitoring/ directory. Each time a logfile is created or modified, will apply the previous mentioned logic, and log the IPv4 from any hacking attempts.

We used Map data structure since the UseCase for failedLoginAttempts fits perfectly, and performance-wise is ```O(1)``` for our needed operations.

We also used the NIO Java library for IO operations, for significant performance improvement and NIO Java WatchService for the background directory monitoring.

## Usage

In order to test it, generate a random logfile calling our exposed REST API endpoint:

Endpoint that generates a logfile with 100K+ logs following a custom Hacker Attempt Probability. This file will generate at our root project ```logs/tmp/``` directory.

`POST /logs/v1/generate-random-log-file/{hacker-attempt-probability}`

    curl --location --request POST 'http://localhost:8080/logs/v1/generate-random-log-file/1'

When generated, manually move it to our ```logs/monitoring/``` directory for processing it. If any hacker attempts have been made, they will get logged.

Editing the file will trigger the same logic.

# Time Calculation - Solution

I developed a function that returns the number of minutes (rounded down) between two
timestamps time1 and time2 in RFC 1123 format ie: ```26 Dec 2022 17:15:00 GMT``` (RFC 2822 predecessor)

The function will receive 2 strings that will get parsed to ZonedDateTime.

Then we will use ```Duration.between(zoneDate1, zoneDate2).toMinutes()``` to get the amount of minutes between both dates.

Finally, we will be making use of ```Math.floorDiv()``` and ```Math.abs()``` with the previous result, so we can get a positive down-rounded value.

## Usage

In order to test it, call our exposed REST API endpoint with both timestamps:

Endpoint that returns the number of minutes (rounded down) between two RFC 1123 format Strings. (RFC 2822 predecessor)

`POST /time-calculations/v1/minutes-between-timestamps`

````
curl --location --request POST 'http://localhost:8080/time-calculations/v1/minutes-between-timestamps' \
--header 'Content-Type: application/json' \
--data-raw '{
"timeStamp1" : "26 Dec 2022 17:30:00 GMT",
"timeStamp2" : "26 Dec 2022 17:15:00 GMT"
}'
````
