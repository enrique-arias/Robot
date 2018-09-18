Notes

Spring boot application

To build: mvn clean install

To run it: mvn spring-boot:run

I have decided for a POST request (it was not specified) with a format: /oilCleaner

For more information about the endpoint it creates a small swagger doc (when running the app) at: http://localhost:8080/swagger-ui.html

With more time I would have added:
-   Logger
-   Test to the request and response (json formats)
-   More information to swagger (format and examples)