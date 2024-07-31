<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Take Quiz - Quiz Master</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
</head>
<body>
    <main class="main-content fade-in">
        <div class="container">
            <div class="quiz-container fade-in">
                <h2>${quiz.name}</h2>

                <!-- Error message display -->
                <c:if test="${not empty error}">
                    <div class="error-message">${error}</div>
                </c:if>

                <div class="timer-header">
                    <div class="number-of-count">
                        <span class="number-of-question">1 of ${quiz.questions.size()} questions</span>
                    </div>
                    <div id="timer"></div>
                </div>

                <form id="quiz-form" action="${pageContext.request.contextPath}/takeQuiz" method="post">
                    <input type="hidden" name="quizId" value="${quiz.id}">
                    <input type="hidden" id="quizDuration" name="quizDuration" value="${quizDuration}">
                    <c:forEach var="question" items="${quiz.questions}" varStatus="status">
                        <div class="question-container" style="display: ${status.index == 0 ? 'block' : 'none'};">
                            <h3>Question ${status.index + 1}</h3>
                            <p>${question.text}</p>
                            <c:forEach var="option" items="${question.options}" varStatus="optionStatus">
                                <c:if test="${not empty option}">
                                    <div class="option-div" onclick="selectOption(this, ${question.id}, ${optionStatus.index + 1})" data-option-index="${optionStatus.index + 1}">
                                        ${option}
                                    </div>
                                </c:if>
                            </c:forEach>
                            <input type="hidden" id="answer${question.id}" name="answer${question.id}" value="">
                        </div>
                    </c:forEach>
                </form>

                <!-- Navigation buttons -->
                <div id="navigation-buttons">
                    <button type="button" id="prev-button" class="btn btn-secondary" >Previous</button>
                    <button type="button" id="next-button" class="btn btn-primary" >Next</button>
                    <button type="button" id="submit-button" class="btn btn-primary" >Submit Quiz</button>
                    <button type="button" id="review-button" class="btn btn-secondary" >Review All</button>
                </div>
            </div>
        </div>
    </main>
    <script src="${pageContext.request.contextPath}/js/quiz.js"></script>
    <script>
        console.log("takeQuiz.jsp loaded");
        window.onload = function() {
            console.log("Window loaded");
            console.log("Number of question containers:", document.querySelectorAll('.question-container').length);
        };
    </script>
</body>
</html>
