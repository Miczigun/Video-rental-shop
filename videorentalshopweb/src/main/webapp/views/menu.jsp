<%-- 
    Document   : menu
    Created on : 7 gru 2023, 15:09:28
    Author     : Miczi
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="pl.polsl.model.ModelLogic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html data-bs-theme="dark">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
        <title>Menu Page</title>
    </head>
    <body>
        <div class="container">
            <table class="table table-bordered">
                  <thead>
                    <tr>
                      <th>Title</th>
                      <th>Genre</th>
                      <th>Year</th>
                      <th>Price</th>
                    </tr>
                  </thead>
                  <tbody>
                  <c:forEach var="item" items="${movies}">
                      <tr>
                          <td>
                              ${item.title}
                          </td>
                          <td>
                              ${item.genre}
                          </td>
                          <td>
                              ${item.year}
                          </td>
                          <td>
                              ${item.price}
                          </td>
                      </tr>
                  </c:forEach>
                  </tbody>
            </table>
        </div>           
    </body>
</html>
