<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>ëìë³´ë</title>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-/Y6pD6FV/Vv2HJnA6t+vslU6fwYXjCFtcEpHbNJ0lyAFsXTsjBbfaDjzALeQsN6M" crossorigin="anonymous">
    <link href="https://getbootstrap.com/docs/4.0/examples/signin/signin.css" rel="stylesheet" crossorigin="anonymous">
</head>

<body>
<div class="container">
    <h3 class="form-signin-heading text-center mb-5">nahwasa.com</h3>

    <h3 class="overview-normalize">ì ì ìì´ë</h3>
    <p>
        ${userId}
    </p>
    <hr/>
    <h3 class="overview-normalize">ì­í </h3>
    <p>
        ${userRoles}
    </p>
    <hr/>
    <h3 class="overview-normalize">ì­í ì ë°ë¥¸ íì´ì§ ì´ë ê¶í íì¸</h3>
    <p>
        <button onclick="location.href='/admin/main'" class="btn btn-sm btn-success">ê´ë¦¬ì ì¤ì  íì´ì§(ê´ë¦¬ìë§)</button>
        <button onclick="location.href='/user/main'" class="btn btn-sm btn-info">ì ì  ì¤ì  íì´ì§(ì ì ë§)</button>
    </p>
    <hr/>
    <form method="post" action="/logout">
        <button class="btn btn-sm btn-danger btn-block" type="submit">ë¡ê·¸ìì</button>
    </form>
</div>
</body>
</html>