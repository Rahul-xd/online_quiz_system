<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login - Quiz Master</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css?v=1.3">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
</head>
<body>
    <header class="header">
        <div class="container">
            <a href="index.jsp" class="logo">Quiz Master</a>
            <nav class="navbar">
                <a href="index.jsp">Home</a>
                <a href="signup.jsp" class="btn btn-secondary">Sign Up</a>
            </nav>
        </div>
    </header>

    <main class="main-content fade-in">
        <div class="container">
            <form action="${pageContext.request.contextPath}/login" method="post" class="form-container" id="loginForm">
                <h2>Login</h2>
                
                <c:if test="${not empty error}">
                    <div class="error-message">${error}</div>
                </c:if>
                
                <c:if test="${not empty success}">
                    <div class="success-message">${success}</div>
                </c:if>
                
                <div class="form-group">
                    <label for="userid">Username:</label>
                    <input type="text" id="userid" name="userid" required>
                </div>
                <div class="form-group">
                    <label for="password">Password:</label>
                    <div class="password-input-wrapper">
                        <input type="password" id="password" name="password" required>
                        <i class="fas fa-eye" id="togglePassword"></i>
                    </div>
                </div>
                <button type="submit" class="btn btn-primary">Login</button>
                <p class="text-center mt-2">
                    Don't have an account? <a href="signup.jsp" class="signup-link">Sign Up</a>
                </p>
                <p class="text-center mt-1">
                    <a href="forgot-password.jsp" class="forgot-password-link">Forgot Password?</a>
                </p>
            </form>
        </div>
    </main>

    <script>
        document.getElementById('loginForm').addEventListener('submit', function(event) {
            var userid = document.getElementById('userid').value.trim();
            var password = document.getElementById('password').value.trim();
            var errorMessage = '';

            if (userid === '') {
                errorMessage += 'Username is required. ';
            }

            if (password === '') {
                errorMessage += 'Password is required. ';
            }

            if (errorMessage !== '') {
                event.preventDefault();
                var errorDiv = document.createElement('div');
                errorDiv.className = 'error-message';
                errorDiv.textContent = errorMessage;
                this.insertBefore(errorDiv, this.firstChild);
            }
        });

        // Toggle password visibility
        const togglePassword = document.querySelector('#togglePassword');
        const password = document.querySelector('#password');

        togglePassword.addEventListener('click', function (e) {
            const type = password.getAttribute('type') === 'password' ? 'text' : 'password';
            password.setAttribute('type', type);
            this.classList.toggle('fa-eye-slash');
        });
    </script>
</body>
</html>