<%@ page import="models.Post" %>
<%@ page import="models.User" %>
<%@ page import="repositories.CarMarkRepository" %>
<%@ page import="repositories.CarBodyRepository" %>
<%@ page import="repositories.UserRepository" %>
<%@ page import="repositories.PostRepository" %>
<%@ page import="repositories.PostRepository" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
<%
    String id = request.getParameter("id");
    Post post = Post.of("", "", false, null, null, (User) request.getSession().getAttribute("user"));
    if (id != null) {
        post = PostRepository.instOf().findPostById(Integer.valueOf(id));
    }
%>
<div class="container">
    <jsp:include page="/navBar.jsp"/>
</div>
<div class="container pt-3">
    <div class="row">
        <div class="card" style="width: 100%">
            <div class="card-header">
                <% if (id == null) { %>
                Новое объявление.
                <% } else { %>
                Редактирование объявления.
                <% } %>
            </div>
            <div class="card-body">
                <form action="<%=request.getContextPath()%>/editPost.do?id=<%=post.getId()%>" method="post">
                    <div class="form-group">
                        <label>Название</label>
                        <input required type="text" class="form-control" name="name" value="<%=post.getName()%>"
                               placeholder="Укажите название объявления">
                    </div>
                    <div class="form-group">
                        <label>Описание</label>
                        <textarea required type="text" class="form-control" name="description"
                                  placeholder="Опишите свое объявление"><%=post.getDescription()%></textarea>
                    </div>
                    <div class="form-group">
                        <label>Выберете марку автомобиля</label>
                        <select class="form-control" name="mark">
                            <c:forEach items="${CarMarkRepository.instOf().findAllCarMark()}" var="mark">
                                <option value="${mark.id}"><c:out value="${mark.name}" /></option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="form-group">
                        <label>Выберете марку автомобиля</label>
                        <select class="form-control" name="body">
                            <c:forEach items="${CarBodyRepository.instOf().findAllCarBody()}" var="body">
                                <option value="${body.id}"><c:out value="${body.name}" /></option>
                            </c:forEach>
                        </select>
                    </div>
                    <button type="submit" class="btn btn-primary">Сохранить</button>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>
