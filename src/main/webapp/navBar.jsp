<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<!doctype html>
<html lang="en">
<head>
  <title>Сайт по продажи автомобилей</title>
</head>
<body>
<div class="container">
  <div class="row">
    <ul class="nav">
      <li class="nav-item">
        <a class="nav-link" href="<%=request.getContextPath()%>/index.do">Объявления</a>
      </li>
      <c:if test="${user != null}">
        <li class="nav-item">
          <a class="nav-link" href="<%=request.getContextPath()%>/usersPosts.do">Мои объявления</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="<%=request.getContextPath()%>/editPost.jsp">Добавить объявление</a>
        </li>
      </c:if>
      <c:if test="${user != null}">
        <li class="nav-item">
          <a class="nav-link" href="<%=request.getContextPath()%>/logout.do"><c:out value="${user.name}"/> | Выйти</a>
        </li>
      </c:if>
      <c:if test="${user == null}">
        <li class="nav-item">
          <a class="nav-link" href="<%=request.getContextPath()%>/login.jsp">Войти</a>
        </li>
      </c:if>
    </ul>
  </div>
</div>