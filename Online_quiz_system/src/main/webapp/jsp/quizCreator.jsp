<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.sql.*" %>
<%@
page import = "p1.Dbutil"
 %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<style>
h1{
font-size:30px;
margin:auto;
}</style>
</head>
<body>
<% 
	String quizName = request.getParameter("quizName");
	try {
    Dbutil.connect();
    /* out.println("connection established"); */
    String insertQuery = "INSERT INTO quizzes VALUES (?,?)";
    ResultSet rs = Dbutil.st.executeQuery("select * from quizzes");
    /* out.println("statement executed"); */
    int sum=0;
    while(rs.next()){
    	sum+=rs.getInt(1);
    }
   	Dbutil.pst = Dbutil.con.prepareStatement(insertQuery);
   	Dbutil.pst.setInt(1, sum);
    Dbutil.pst.setString(2, quizName);
    Dbutil.pst.executeUpdate();
    // Redirect to another page with the generated quiz ID
    if (sum != 0) {
        /* response.sendRedirect("generateQuiz.jsp?quizId=" + sum); */
        %>
        <h1>Quiz Id is :<%=sum %> </h1>
        <% 
    }
    else {
        // Handle error
        out.println("Error occurred while creating quiz.");
    }
	Dbutil.st.close();
	Dbutil.con.close();
	}
	catch (Exception e) {
		e.printStackTrace();
	}
%>
</body>
</html>