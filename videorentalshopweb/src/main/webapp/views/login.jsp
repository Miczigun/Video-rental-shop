<%-- 
    Document   : login
    Created on : 7 gru 2023, 13:51:40
    Author     : Michal Lajczak
    Version    : 1.5
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html data-bs-theme="dark">
    <head>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Page</title>
        <script>
            let message = "${error}";
            if (message != ""){
                alert(message)
            }
        </script>
    </head>
    <body>
        <div class="container text-center w-25 mt-3">
            <form method="POST">
                <div class="mb-3">
                    <label for="username" class="form-label">Username</label><br>
                    <input type="text" id="username" name="username" class="form-control"/>
                </div>
                <div class="mb-3">
                    <label for="password" class="form-label">Password</label><br>
                    <input type="password" id="password" name="password" class="form-control"/>
                </div>
                <input type="submit" value="Sign in" class="btn btn-primary"/>
            </form>
            <p class="mt-3">Do not have an account? <a href="/videorentalshopweb/register">Sign up</a></p>
        </div>
    </body>
</html>
