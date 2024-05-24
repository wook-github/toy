<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>íìê°ì</title>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-/Y6pD6FV/Vv2HJnA6t+vslU6fwYXjCFtcEpHbNJ0lyAFsXTsjBbfaDjzALeQsN6M" crossorigin="anonymous">
    <link href="https://getbootstrap.com/docs/4.0/examples/signin/signin.css" rel="stylesheet" crossorigin="anonymous">
</head>

<body>
<div class="container">
    <form class="form-signin" id='join_form'>
        <h2 class="form-signin-heading text-center mb-5">íìê°ì!</h2>

        <p>
            <label for="userId" class="sr-only">ìì´ë</label>
            <input type="text" id="userId" name="userId" class="form-control" placeholder="ìì´ë" required="" autofocus="">
        </p>
        <p>
            <label for="userPassword" class="sr-only">ë¹ë°ë²í¸</label>
            <input type="password" id="userPassword" name="userPassword" class="form-control" placeholder="ë¹ë°ë²í¸" required="">
        </p>
        <p>
            <label for="userName" class="sr-only">ì´ë¦</label>
            <input type="text" id="userName" name="userName" class="form-control" placeholder="ì´ë¦" required="">
        </p>
        <button class="btn btn-lg btn-primary btn-block" type="submit">íìê°ì</button>
    </form>

    <script>
        const form = document.getElementById('join_form');

        form.addEventListener('submit', e => {
            e.preventDefault();

            const data = new FormData(form);
            const param = JSON.stringify(Object.fromEntries(data));

            fetch('/login/joinUser', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: param,
            })
            .then(response => {
            	debugger;
                if (response.status == 200) {
                    window.location.href = '/login/loginView';
                    alert("íìê°ì ì±ê³µ")
                } else {
                    alert("íìê°ì ì¤í¨")
                }
            })
            .catch(error => console.log(error))
        });
    </script>
</div>
</body>
</html>