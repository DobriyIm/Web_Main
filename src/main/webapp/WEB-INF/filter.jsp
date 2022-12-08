<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% String[] randData = (String[]) request.getAttribute("randData");    %>


<p>list:</p>
<ul>
    <% for(String str: randData) { %>
        <li><%=str%></li>
    <%  }   %>
</ul>

