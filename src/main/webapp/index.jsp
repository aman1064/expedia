<%-- 
    Document   : index
    Created on : 25 May, 2014, 2:56:07 PM
    Author     : Aman
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <form action="getWeatherInfo" method="get">
            <input type="text" name="zip" id="zip"/>
            <input type="submit" value="Get Weather"/>
        </form>
        
    </body>
</html>
