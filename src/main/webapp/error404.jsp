<%-- Update both files for consistency --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>404 - Page Not Found - Quiz Master</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
</head>
<body>
    <header class="header">
        <div class="container">
            <a href="index.jsp" class="logo">Quiz Master</a>
        </div>
    </header>

    <main class="main-content fade-in">
        <div class="container">
            <div class="error-container">
                <h1>404 - Page Not Found</h1>
                <p>The page you are looking for does not exist.</p>
                <a href="index.jsp" class="btn">Go to Home</a>
            </div>
        </div>
    </main>

    <footer class="footer">
        <div class="container">
            <div class="social-media-links">
                <a href="https://www.instagram.com/yourprofile" class="social-icon" target="_blank"><i class="fab fa-instagram"></i></a>
                <a href="https://www.facebook.com/yourprofile" class="social-icon" target="_blank"><i class="fab fa-facebook"></i></a>
                <a href="https://www.twitter.com/yourprofile" class="social-icon" target="_blank"><i class="fab fa-twitter"></i></a>
                <a href="https://www.linkedin.com/yourprofile" class="social-icon" target="_blank"><i class="fab fa-linkedin"></i></a>
            </div>
            <p class="copyright">&copy; 2024 Quiz Master. All rights reserved.</p>
        </div>
    </footer>
</body>
</html>