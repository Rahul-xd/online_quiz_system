<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Create Quiz - Quiz Master</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link href="https://fonts.googleapis.com/css2?family=Playfair+Display:wght@400;700&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Lato:wght@300;400;700&display=swap" rel="stylesheet">
</head>
<body class="create-quiz-page">
    <header class="header">
        <div class="container">
            <a href="index.jsp" class="logo">Quiz Master</a>
            <nav class="navbar">
                <a href="index.jsp" class="btn">Dashboard</a>
            </nav>
        </div>
    </header>

    <main class="main-content fade-in">
        <div class="container">
            <h1>Create a New Quiz</h1>
            
            <c:if test="${not empty error}">
                <div class="error-message">${error}</div>
            </c:if>
            
            <c:if test="${not empty success}">
                <div class="success-message">${success}</div>
            </c:if>
            
            <form action="${pageContext.request.contextPath}/createQuiz" method="post" id="quizForm" class="create-quiz-form">
                <div class="form-group quiz-name-group">
                    <label for="quizName">Quiz Name:</label>
                    <input type="text" id="quizName" name="quizName" required>
                </div>
                
                <div id="questions-container">
                    <!-- Questions will be added here dynamically -->
                </div>

                <div class="form-actions">
                    <button type="button" class="btn btn-secondary add-question">Add Question</button>
                    <button type="submit" class="btn btn-primary submit-quiz">Create Quiz</button>
                </div>
                <input type="hidden" id="questionCount" name="questionCount" value="0">
            </form>
        </div>
    </main>
    <script>
        document.getElementById('quizForm').addEventListener('submit', function(event) {
            var questionCount = document.querySelectorAll('.question-group').length;
            if (questionCount < 2) {
                event.preventDefault();
                alert('Quiz must have at least 2 questions');
            }
        });
    </script>
    <script src="${pageContext.request.contextPath}/js/quizCreator.js"></script>
</body>
</html>
