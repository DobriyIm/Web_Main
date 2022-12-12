<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String imgMess = (String) request.getAttribute("imgMess");
    String fileError = (String) request.getAttribute("fileError");
%>

<%if(imgMess != null) { %>
    <label><%=imgMess%></label>
<% } %>

<%if(fileError != null) { %>
    <label>Error: <%=fileError%></label>
<% } %>

<form method="post" enctype="multipart/form-data">
    <input type="file" name="file" id="file"/>
    <label for="file">Choose a file</label>
    <br/>
    <input type="submit" value="Add file"/>
</form>