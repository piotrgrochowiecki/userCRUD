<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>userCRUD - Register</title>

    <!-- Custom fonts for this template-->
    <link href="../theme/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
    <link
            href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
            rel="stylesheet">

    <!-- Custom styles for this template-->
    <link href="../theme/css/sb-admin-2.min.css" rel="stylesheet">

</head>

<body class="bg-gradient-primary">

<div class="container-fluid">

    <!-- Page Heading -->
    <div class="d-sm-flex align-items-center justify-content-between mb-4">
        <h1 class="h3 mb-0 text-gray-800">UsersCRUD</h1>
        <a href="<c:url value="/users/list"/>" class="d-none d-sm-inline-block btn btn-sm btn-primary shadow-sm">
            <i class="fas fa-download fa-sm text-white-50"></i> Users list</a>
    </div>
    <div class="card shadow mb-4">
        <div class="card-header py-3">
            <h6 class="m-0 font-weight-bold text-primary">Edycja użytkownika</h6>
        </div>
        <div class="card-body">
            <form method="post" action="/user/edit">
                <input type="hidden" name="id" value="${user.id}"/>
                <div class="form-group">
                    <label for="userName">Nazwa</label>
                    <input value="${user.userName}" name="userName" type="text" class="form-control" id="userName" placeholder="Nazwa użytkownika">
                </div>
                <div class="form-group">
                    <label for="userEmail">Email</label>
                    <input value="${user.email}" name="email" type="email" class="form-control" id="userEmail" placeholder="Email użytkownika">
                </div>
                <div class="form-group">
                    <label for="userPassword">Hasło</label>
                    <input name="userPassword" type="password" class="form-control" id="userPassword" placeholder="Hasło użytkownika">
                </div>

                <button type="submit" class="btn btn-primary">Edytuj</button>
            </form>

        </div>
    </div>
</div>
<%@ include file="/footer.jsp" %>

<!-- Bootstrap core JavaScript-->
<script src="../theme/vendor/jquery/jquery.min.js"></script>
<script src="../theme/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

<!-- Core plugin JavaScript-->
<script src="../theme/vendor/jquery-easing/jquery.easing.min.js"></script>

<!-- Custom scripts for all pages-->
<script src="../theme/js/sb-admin-2.min.js"></script>

</body>
</html>