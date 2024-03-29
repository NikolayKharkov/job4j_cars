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

    <title>Сайт по продажи автомобилей</title>
</head>
<body>
<div class="container">
    <jsp:include page="navBar.jsp"/>
    <div class="container pt-3">
        <div class="row">
            <div class="card" style="width: 100%">
                <div class="card-header">
                    Объявления
                </div>
                <div class="card-body">
                    <table class="table">
                        <thead>
                        <tr>
                            <th scope="col">Название</th>
                            <th scope="col">Марка</th>
                            <th scope="col">Кузов</th>
                            <th scope="col">Фото</th>
                            <th scope="col">Описание</th>
                            <th scope="col">Владелец</th>
                            <th scope="col">Статус</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${posts}" var="post">
                            <tr>
                                <td><c:out value="${post.name}"/></td>
                                <td><c:out value="${post.carMark.name}"/></td>
                                <td><c:out value="${post.carBody.name}"/></td>
                                <td>
                                    <img src="<c:url value='/photoDownload?id=${post.id}'/>" width="100px" height="100px"/>
                                </td>
                                <td><c:out value="${post.description}"/></td>
                                <td><c:out value="${post.user.email}"/></td>
                                <td>
                                    <c:if test="${post.sold == false}">
                                        <a class="btn btn-success" disabled>В продаже</a>
                                    </c:if>
                                    <c:if test="${post.sold == true}">
                                        <a class="btn btn-secondary" disabled>Продано</a>
                                    </c:if>
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
