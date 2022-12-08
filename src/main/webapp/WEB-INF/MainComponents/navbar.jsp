<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% String host = request.getContextPath(); %>

<nav class="four">
    <ul>
        <li><a href="<%=host%>/">Index.jsp</a></li>
        <li><a href="<%=host%>/filter">Filter.jsp</a></li>
        <li><a href="<%=host%>/hello">Hello.jsp</a></li>
        <li><a href="<%=host%>/auth">Auth.jsp</a></li>
    </ul>
</nav>
