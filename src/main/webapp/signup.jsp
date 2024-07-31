<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sign Up - Quiz Master</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css?v=1.3">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
</head>
<body>
    <header class="header">
        <div class="container">
            <a href="index.jsp" class="logo">Quiz Master</a>
            <nav class="navbar">
                <a href="index.jsp">Home</a>
                <a href="login.jsp" class="btn btn-secondary">Login</a>
            </nav>
        </div>
    </header>

    <main class="main-content fade-in">
        <div class="container">
            <form action="${pageContext.request.contextPath}/signup" method="post" class="form-container" id="signupForm">
                <h2>Sign Up</h2>
                
                <c:if test="${not empty error}">
                    <div class="error-message">${error}</div>
                </c:if>
                
                <c:if test="${not empty success}">
                    <div class="success-message">${success}</div>
                </c:if>
                
                <div class="form-group">
                    <label for="fname">First Name:</label>
                    <input type="text" id="fname" name="fname" required>
                </div>
                <div class="form-group">
                    <label for="lname">Last Name:</label>
                    <input type="text" id="lname" name="lname" required>
                </div>
                <div class="form-group">
                    <label for="email">Email:</label>
                    <input type="email" id="email" name="email" required>
                </div>
                <div class="form-group">
                    <label for="userid">Username:</label>
                    <input type="text" id="userid" name="userid" required>
                </div>
                <div class="form-group">
                    <label for="password">Password:</label>
                    <input type="password" id="password" name="password" required>
                </div>
                <button type="submit" class="btn btn-primary">Sign Up</button>
                <p class="text-center mt-2">Already have an account? <a href="login.jsp" class="signup-link">Login</a></p>
            </form>
        </div>
    </main>

    <script>
        document.getElementById('signupForm').addEventListener('submit', function(event) {
            var firstName = document.getElementById('fname').value.trim();
            var lastName = document.getElementById('lname').value.trim();
            var email = document.getElementById('email').value.trim();
            var userId = document.getElementById('userid').value.trim();
            var password = document.getElementById('password').value.trim();
            var errorMessage = '';

            if (firstName === '') {
                errorMessage += 'First Name is required. ';
            }

            if (lastName === '') {
                errorMessage += 'Last Name is required. ';
            }

            if (email === '') {
                errorMessage += 'Email is required. ';
            }

            if (userId === '') {
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
    </script>
</body>
</html>
