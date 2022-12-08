<%@ page import="web.basics.entities.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  String contextPath = request.getContextPath();
  String authError = (String) request.getAttribute("authError");
  User user = (User) request.getAttribute("authUser");
%>


<section>
  <% if(user == null) { %>

    <form method="post" action="">
      <div><label>Login: <input type="text" name="userLogin"/></label></div>
      <div><label>Password: <input type="password" name="userPass"/></label></div>
      <input type="hidden" name="form-id" value="nav-auth-form"/>
      <input type="submit" value="Log in"/>
      <br/>
      <a href="<%=contextPath%>/register">Sign Up</a>
    </form>

    <% if(authError != null) { %>
    <span class="auth-error"><%=authError%></span>
    <% } %>

  <% } else { %>

    <p>User name: <%=user.getName()%></p>
    <a href="?logout=true">Log out</a>

  <% } %>

</section>

