<%-- 
    Document   : index
    Created on : Sep 19, 2024, 3:17:11 PM
    Author     : ihsan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.3/font/bootstrap-icons.min.css">
        <style>
            @import url('https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900&display=swap');
            body {
                font-family: "Poppins", sans-serif;
                height: 100%;
            }
        </style>
    </head>
    <body>
        <%-- Menyertakan sidebar --%>
        <%@ include file="sidebar.jsp" %>

        <div id="page-content-wrapper">
            <h1>Hello World!</h1>
            <p>Welcome to the main content area!</p>
        </div>
    </body>
</html>
