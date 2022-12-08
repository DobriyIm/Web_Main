<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    String userInput = (String) request.getAttribute("userInput");
    String fromFilter = (String)  request.getAttribute("from-filter");
%>


<section>
    <h1>Hello <page></page></h1>
    <% if(userInput != null){ %>
    <p>Text: <%= userInput%></p>
    <% } %>
    <form method="post">
        Enter text: <label><input type="text" name="userInput"/></label>
        <input type="submit" value="Submit"/>
    </form>
    <p>Filter servlet: <a href="filter">link</a></p>
    <p>DemoFilter: <%=fromFilter%></p>
</section>

