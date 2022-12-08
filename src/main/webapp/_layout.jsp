<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% String pageBody = "/WEB-INF/" + request.getAttribute("pageBody"); %>
<html>
<head>
    <title>Layout</title>
</head>
<body>
    <jsp:include page="WEB-INF/MainComponents/header.jsp"/>

    <jsp:include page="<%=pageBody%>"/>

    <jsp:include page="WEB-INF/MainComponents/footer.jsp"/>
</body>
</html>
