expedia
=======

Expedia Coding Problem

The prerequisites to run this application are:

1. Maven Build Tool
2. Apache Tomcat 7.x
3. Proxy Free Internet Connection

To run the application follow the following steps

1. Update settings.xml with your tomcat manager user and password
2. Modify pom.xml for server configuration url (port number might be different)
3. Start Tomcat using startup.bat
4. mvn compile
5. mvn test
6. mvn package
7. mvn tomcat7:deploy -s settings.xml

The web application is accessible at localhost:8080/weather


The problem given was equivalent to writing a Client for a REST Service. The client invokes an API and process the response
in JSON format. It builds a JSON object out of the response of service invoked.
