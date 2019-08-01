# ubnt-test-task

## Tested under:
```
  Server version: Apache Tomcat/7.0.42
  Server built:   Jul 2 2013 08:57:41
  Server number:  7.0.42.0
  OS Name:        Windows 10
  OS Version:     10.0
  Architecture:   amd64
  JVM Version:    1.8.0_92-b14
  JVM Vendor:     Oracle Corporation
````

## How to run under Apache Tomcat
- untar **/dist/reddit-stream-reader-SNAPSHOT-bundle.tar** into Apache tomcat webapps directory. 
- Start tomcat. 
- Application URL: http://127.0.0.1:8080/ubnt-test-task/ - index page with some example links. 
- Api base path http://127.0.0.1:8080/ubnt-test-task/api/

**OR**

- copy **/dist/ubnt-test-task.war** into Apache tomcat webapps directory.
- Start tomcat
- Application URL: http://127.0.0.1:8080/ubnt-test-task/ - index page with some example links. 
- Api base path http://127.0.0.1:8080/ubnt-test-task/api/

## How to run with embedded Jetty http server

- java -jar **/dist/reddit-stream-reader-SNAPSHOT-jar-with-dependencies.jar**
- api base URL: http://127.0.0.1:8081/api/

# API

**Activity**
----
  Returns reddit activity - how much submissions and comments have been posted in a given time range

* **URL**

  /activity:timeRange:eventType

* **Method:**

  `GET`
  
*  **URL Params**

   **Required:**
 
   `timeRange=[MIN, MIN_5, HOUR, DAY, ALL_TIME]`
   `eventType=[SUBMISSION, COMMENT]`

* **Success Response:**

  * **Code:** 200 <br />
    **Content:** `{"status":"ok","data":2280}`
 
* **Error Response:**

  * **Code:** 400 BAD REQUEST <br />
    **Content:** `{"status":"error","errorCode":1,"errorMessage":"getActivity.arg1 Valid timeRange is  MIN, MIN_5, HOUR, DAY, ALL_TIME\n"}`
  
**top100**
----
  Returns  returns the top 100 most active subreddits. (submissions + comments)

* **URL**

  /top100:timeRange

* **Method:**

  `GET`
  
*  **URL Params**

   **Required:**
 
   `timeRange=[MIN, MIN_5, HOUR, DAY, ALL_TIME]`

* **Success Response:**

  * **Code:** 200 <br />
    **Content:** `{"status":"ok","data":[{"subredditId":"t5_229sen","activity":275},{"subredditId":"t5_2qh1i","activity":86},{"subredditId":"t5_2qjpg","activity":71},{"subredditId":"t5_2rjli","activity":56},{"subredditId":"t5_2zmfe","activity":31}]}`
 
* **Error Response:**

  * **Code:** 400 BAD REQUEST <br />
    **Content:** `{"status":"error","errorCode":1,"errorMessage":"getActivity.arg1 Valid timeRange is  MIN, MIN_5, HOUR, DAY, ALL_TIME\n"}`

**mostActive**
----
  Returns  returns most active subreddits - both submission andcomment

* **URL**

  /mostActive:timeRange:eventType

* **Method:**

  `GET`
  
*  **URL Params**

   **Required:**
 
   `timeRange=[MIN, MIN_5, HOUR, DAY, ALL_TIME]`
   `eventType=[SUBMISSION, COMMENT]`

* **Success Response:**

  * **Code:** 200 <br />
    **Content:** `{"status":"ok","data":[{"subredditId":"t5_229sen","activity":275},{"subredditId":"t5_2qh1i","activity":86},{"subredditId":"t5_2qjpg","activity":71},{"subredditId":"t5_2rjli","activity":56},{"subredditId":"t5_2zmfe","activity":31}]}`
 
* **Error Response:**

  * **Code:** 400 BAD REQUEST <br />
    **Content:** `{"status":"error","errorCode":1,"errorMessage":"getActivity.arg1 Valid timeRange is  MIN, MIN_5, HOUR, DAY, ALL_TIME\n"}`
 
