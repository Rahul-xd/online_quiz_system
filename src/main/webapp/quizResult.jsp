<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Quiz Result - Quiz Master</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
</head>
<body>
    <header class="header">
        <div class="container">
            <a href="index.jsp" class="logo">Quiz Master</a>
            <nav class="navbar">
                <a href="index.jsp">Home</a>
            </nav>
        </div>
    </header>

    <main class="main-content fade-in">
        <div class="container">
            <div class="quiz-container">
                <h2>Quiz Result</h2>
                <p>Your score: ${score}%</p>
                <p>Correct answers: ${correctAnswers} out of ${totalQuestions}</p>

				<div class="result-details">
					<c:forEach var="question" items="${quiz.questions}" varStatus="status">
					    <div class="result-container">
					        <h3>Question ${status.index + 1}</h3>
					        <p>${question.text}</p>
					        <c:forEach var="option" items="${question.options}" varStatus="optionStatus">
					            <c:if test="${not empty option}">
					                <div class="option-div ${optionStatus.index + 1 == question.correctAnswer ? 'correct-answer' : ''} ${optionStatus.index + 1 == userAnswers[question.id] ? 'user-answer' : ''}">
					                    ${option}
					                    <c:if test="${optionStatus.index + 1 == userAnswers[question.id]}">
					                        (Your Answer)
					                    </c:if>
					                    <c:if test="${optionStatus.index + 1 == question.correctAnswer}">
					                        (Correct Answer)
					                    </c:if>
					                </div>
					            </c:if>
					        </c:forEach>
					    </div>
					</c:forEach>
				</div>

                <a href="index.jsp" class="btn">Back to Dashboard</a>
            </div>
        </div>
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

    <script src="${pageContext.request.contextPath}/js/quiz.js"></script>
</body>
</html>