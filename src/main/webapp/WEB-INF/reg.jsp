<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String regError = (String) request.getAttribute("regError");
    String regOk = (String) request.getAttribute("regOk");
    String userLogin = (String) request.getAttribute("userLogin");
    String userName = (String) request.getAttribute("userName");
%>
<form class="reg-user-form" method="post">
    <% if(regError != null){%>
    <span class="error"><%=regError%></span>
    <% } else if(regOk != null) { %>
    <span class="successful"><%=regOk%></span>
    <% } %>
    <div>
        <label for="user-login">Login</label>
        <input type="text" id="user-login" name="userLogin" <%if(userLogin != null){%> value="<%=userLogin%>" <% } %>/>
    </div>
    <div>
        <label for="password">Password</label>
        <input type="password" id="password" name="userPassword"/>
    </div>
    <div>
        <label for="confirmPassword">Password</label>
        <input type="password" id="confirmPassword" name="confirmPassword"/>
    </div>
    <div>
        <label for="name">Name</label>
        <input type="text" id="name" name="userName" <%if(userName != null){%> value="<%=userName%>" <% } %>/>
    </div>
    <input type="submit" value="Submit"/>
</form>
