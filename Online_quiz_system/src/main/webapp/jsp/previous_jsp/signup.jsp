<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Sign Up</title>
<link rel="stylesheet" href="../css/login.css">
</head>
<body>
    <div class="main">
        <header class="header">
            <a href="home.jsp" class="logo">Quiz.</a>
        </header>
    </div>
    <div class="wrapper">
        <form action="">
            <h1>SIGNUP</h1>
            <div class="input-box">
                <input type="text" placeholder="Username" required>
                <i class='bx bxs-user'></i>
            </div>
            <div class="input-box">
                <input type="email" placeholder="Mail" required>
                <i class='bx bxl-gmail'></i>
            </div>
            <div class="input-box">
                <input type="password" placeholder="Password" required>
                <i class='bx bxs-lock-alt' ></i>
            </div>
            <button type="submit" class="btn">Sign up</button>
            <div class="register-link">
                <p>Have have an acount?<a
                href="login.jsp">Login
                </a></p>
            </div>
        </form>
    </div>
</body>
</html>