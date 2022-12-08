<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% String host = request.getContextPath(); %>

<nav class="four">
    <ul>
        <li><a href="<%=host%>/">Index</a></li>
        <li><a href="<%=host%>/filter">Filter</a></li>
        <li><a href="<%=host%>/hello">Hello</a></li>
        <li><a href="<%=host%>/auth">Auth</a></li>
        <li><a href="<%=host%>/f-upload">File Upload</a></li>
    </ul>
</nav>
