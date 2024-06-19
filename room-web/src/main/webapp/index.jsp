<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <title>Planning Room | Home</title>
    </head>
    <body>
        <jsp:useBean id="index" class="xyz.jplan.room.view.Welcome" />
        <h2>Welcome | Under construction...</h2>
        <p>Welcome</p> <%= index.getHello() %>
    </body>
</html>