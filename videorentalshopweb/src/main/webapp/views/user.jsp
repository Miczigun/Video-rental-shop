<%-- 
    Document   : user
    Created on : 9 gru 2023, 18:05:38
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
        <title>Profile Page</title>
    </head>
    <body>
        <nav class="navbar navbar-dark" style="background-color: #008080;">
            <div class="container-fluid">
                <a class="navbar-brand" href="/videorentalshopweb">VRS</a>
                <div class="d-flex justify-content-end">
                    <a class="navbar-brand mr-auto" href="/videorentalshopweb/menu">Movies</a>
                    <a class="navbar-brand mr-auto" href="/videorentalshopweb/user">Profile</a>
                    <a class="navbar-brand mr-auto" href="/videorentalshopweb/login">Logout</a>
                </div>
            </div>
        </nav>
        <div class="container mt-3">
            <div class="container text-center">
                <p>User: ${user.username}</p>
                <p>Money: ${user.money}</p>
                <p>Premium: ${user.premium}</p>
            </div>
            <div class="container text-center w-25">
            <form method="POST">
                <label for="amount" class="form-label"></label>
                <input type="number" id="amount" class="form-control my-3" name="amount"/>
                <input type="submit" value="Top up an account" class="btn btn-primary mb-5"/>
            </form>
            </div>
            <h1 class="text-center">Your Movies</h1>
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
