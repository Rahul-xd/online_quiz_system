<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@
page import = "java.sql.*"
 %>
 <%@
page import = "p1.Dbutil"
 %>
<!-- <!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

</body>
</html> -->

<%
String userid = request.getParameter("userid");
session.setAttribute("userid", userid);
String password = request.getParameter("password");
try {
    // Call the connect method to establish the connection
    Dbutil.connect();
    ResultSet rs = Dbutil.st.executeQuery("select * from users where userid='" + userid + "' and password='" + password + "'");
    if(rs.next()){
    if (rs.getString("password").equals(password) && rs.getString("userid").equals(userid)) {
        response.sendRedirect("../html/dashboard.html");
    } else {
        out.println("Invalid password or username.");
    }
    }
    else{
    	out.println("Invalid password or username.");
    	}
	Dbutil.st.close();
	Dbutil.con.close();
}
catch (Exception e) {
	e.printStackTrace();
}
%>