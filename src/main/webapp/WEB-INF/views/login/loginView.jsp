<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>ë¡ê·¸ì¸</title>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-/Y6pD6FV/Vv2HJnA6t+vslU6fwYXjCFtcEpHbNJ0lyAFsXTsjBbfaDjzALeQsN6M" crossorigin="anonymous">
    <link href="https://getbootstrap.com/docs/4.0/examples/signin/signin.css" rel="stylesheet" crossorigin="anonymous">
</head>

<body>
<div class="container">
    <form class="form-signin" method="post" action="/login/loginProcess">
        <p class="text-center">
            <img src="/images/nahwasa-profile.png" class="img-thumbnail" style="width: 200px;" alt="ì´ ê¸ì´ ë³´ì¸ë¤ë©´ ìíë¦¬í° ì¤ì  ìëª»íê±°ì!">
        </p>
        <h2 class="form-signin-heading text-center mb-5">nahwasa.com</h2>

        <p>
            <label for="userId" class="sr-only">ìì´ë</label>
            <input type="text" id="userId" name="userId" class="form-control" placeholder="ìì´ë" required="" autofocus="">
        </p>
        <p>
            <label for="userPassword" class="sr-only">ë¹ë°ë²í¸</label>
            <input type="password" id="userPassword" name="userPassword" class="form-control" placeholder="ë¹ë°ë²í¸" required="">
        </p>
        <button class="btn btn-lg btn-primary btn-block" type="submit">ë¡ê·¸ì¸</button>
    </form>

    <form class="form-signin" method="get" action="/login/joinView">
        <button class="btn btn-lg btn-warning btn-block" type="submit">íìê°ìíê¸°</button>
    </form>
</div>
</body>
</html>