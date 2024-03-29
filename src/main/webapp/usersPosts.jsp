<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<!doctype html>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
            integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
            integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
            integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <title>Сайт по продажи автомобилей</title>
</head>
<body>
<div class="container">
    <jsp:include page="navBar.jsp"/>
    <div class="container pt-3">
        <div class="row">
            <div class="card" style="width: 100%">
                <div class="card-header">
                     Мои объявления
                </div>
                <div class="card-body">
                    <table class="table">
                        <thead>
                        <tr>
                            <th scope="col">Название</th>
                            <th scope="col">Фото</th>
                            <th scope="col">Сменить статус</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${posts}" var="post">
                            <tr>
                                <td>
                                    <a href='<c:url value="/editPost.jsp?id=${post.id}"/>'>
                                        <i class="fa fa-edit mr-3"></i>
                                    </a>
                                    <c:out value="${post.name}"/>
                                </td>
                                <td>
                                    <img src="<c:url value='/photoDownload?id=${post.id}'/>" width="100px" height="100px"/>
                                </td>
                                <td>
                                    <c:if test="${post.sold == false}">
                                        <a class="btn btn-success" href="<c:url value='/updatePostStatus?id=${post.id}'/>">
                                            На "продано"</a>
                                    </c:if>
                                    <c:if test="${post.sold == true}">
                                        <a class="btn btn-secondary" href="<c:url value='/updatePostStatus?id=${post.id}'/>">
                                            На "продаже"</a>
                                    </c:if>
                                </td>
                                <td><a class="btn btn-primary" href="<c:url value='/photoUpload?id=${post.id}'/>">
                                    Добавить фото</a>
                                </td>
                                <td><a class="btn btn-danger" href="<c:url value='/deletePost?id=${post.id}'/>">
                                    Удалить объявление</a>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>