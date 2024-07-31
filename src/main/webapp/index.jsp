<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Online Quiz System</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css?ver=1.1">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
</head>
<body>
    <header class="header">
        <div class="container">
            <a href="index.jsp" class="logo">Quiz Master</a>
            <nav class="navbar">
                <a href="#home" class="active">Home</a>
                <a href="#contact">Contact</a>
                <c:choose>
                    <c:when test="${empty sessionScope.user}">
                        <a href="login.jsp" class="btn">Login</a>
                        <a href="signup.jsp" class="btn">Sign Up</a>
                    </c:when>
                    <c:otherwise>
                        <a href="logout" class="btn">Logout</a>
                    </c:otherwise>
                </c:choose>
            </nav>
        </div>
    </header>

    <main class="main-content">
        <c:choose>
            <c:when test="${empty sessionScope.user}">
                <section id="home" class="hero">
                    <div class="hero-content">
                        <h1>Welcome to Quiz Master</h1>
                        <p>Test your knowledge, create quizzes, and challenge others!</p>
                        <a href="login.jsp" class="btn btn-large">Get Started</a>
                    </div>
                </section>
            </c:when>
            <c:otherwise>
                <section id="dashboard" class="dashboard fade-in">
                    <div class="container">
                        <h2>Welcome, ${sessionScope.user.firstName}!</h2>
                        <div class="dashboard-actions">
                            <a href="createQuiz.jsp" class="btn">Create Quiz</a>
                            <form action="${pageContext.request.contextPath}/takeQuiz" method="get" class="quiz-id-input">
                                <input type="text" name="quizId" placeholder="Enter Quiz ID" required>
                                <button type="submit" class="btn">Take Quiz</button>
                            </form>
                        </div>
                        <c:if test="${not empty sessionScope.createdQuizzes}">
                            <h3>Your Created Quizzes</h3>
                            <table>
                                <thead>
                                    <tr>
                                        <th>Quiz ID</th>
                                        <th>Quiz Name</th>
                                        <th>Action</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="quiz" items="${sessionScope.createdQuizzes}">
                                        <tr>
                                            <td>${quiz.id}</td>
                                            <td>${quiz.name}</td>
                                            <td>
                                                <a href="takeQuiz?quizId=${quiz.id}" class="btn btn-small">Take Quiz</a>
                                                <a href="editQuiz?quizId=${quiz.id}" class="btn btn-small">Edit</a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </c:if>
                        <c:if test="${empty sessionScope.createdQuizzes}">
                            <p>You haven't created any quizzes yet.</p>
                        </c:if>
                    </div>
                </section>
            </c:otherwise>
        </c:choose>
    </main>

    <footer class="footer">
        <div class="container">
            <div class="social-icons">
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
