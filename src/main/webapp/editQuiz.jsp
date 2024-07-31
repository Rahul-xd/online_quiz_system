<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Edit Quiz - Quiz Master</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link href="https://fonts.googleapis.com/css2?family=Playfair+Display:wght@400;700&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Lato:wght@300;400;700&display=swap" rel="stylesheet">
    <style>
    /* Edit Quiz Page Styles */
    body {
        font-family: 'Lato', sans-serif;
        color: var(--text-color);
        background-color: var(--background-color);
        margin: 0;
        padding: 0;
    }

    main {
        padding: 2rem 1rem;
        max-width: 800px;
        margin: 0 auto;
    }

    h1 {
        text-align: center;
        color: var(--accent-color);
        font-size: 2.5rem;
        margin-bottom: 2rem;
    }

    .edit-quiz-form {
        background-color: var(--surface-color);
        border: 1px solid var(--primary-color);
        border-radius: 8px;
        padding: 1.5rem;
        box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
    }

    .form-group {
        margin-bottom: 1.5rem;
    }

    label {
        font-weight: bold;
        margin-bottom: 0.5rem;
        display: block;
        color: var(--text-color);
    }

    input[type="text"],
    input[type="number"] {
        width: 100%;
        padding: 0.75rem;
        font-size: 1rem;
        border: 1px solid var(--primary-color);
        border-radius: 4px;
        background-color: var(--background-color);
        transition: box-shadow var(--transition-speed);
    }

    input[type="text"]:focus,
    input[type="number"]:focus {
        box-shadow: 0 0 0 2px var(--accent-color);
    }

    .question-group {
        border: 1px solid var(--primary-color);
        border-radius: 8px;
        padding: 1rem;
        margin-bottom: 1.5rem;
        background-color: var(--background-color);
        position: relative;
    }

    .question-group h3 {
        color: var(--accent-color);
        font-size: 1.5rem;
        margin-bottom: 1rem;
    }

    .options-container {
        display: grid;
        grid-template-columns: 1fr;
        gap: 0.5rem;
        margin-bottom: 1rem;
    }

    .option-group {
        display: flex;
        align-items: center;
    }

    .option-group input {
        flex: 1;
        margin-right: 0.5rem;
    }

    .btn {
        display: inline-block;
        padding: 0.6rem 1.2rem;
        font-size: 1rem;
        border-radius: 4px;
        cursor: pointer;
        transition: background-color var(--transition-speed);
        text-align: center;
        font-weight: 500;
    }

    .btn-primary {
        background-color: var(--primary-color);
        color: var(--text-color);
    }

    .btn-primary:hover {
        background-color: var(--accent-color);
    }

    .btn-secondary {
        background-color: var(--secondary-color);
        color: var(--text-color);
    }

    .btn-secondary:hover {
        background-color: var(--accent-color);
    }

    .btn-danger {
        background-color: var(--error-color);
        color: var(--surface-color);
        border: none;
        border-radius: 50%;
        width: 24px;
        height: 24px;
        display: flex;
        align-items: center;
        justify-content: center;
        cursor: pointer;
        transition: background-color var(--transition-speed);
    }

    .btn-danger:hover {
        background-color: #c0392b;
    }

    .form-actions {
        display: flex;
        justify-content: space-between;
        gap: 1rem;
        margin-top: 2rem;
    }

    .error-message,
    .success-message {
        text-align: center;
        font-weight: bold;
        margin-bottom: 1rem;
    }

    .error-message {
        color: var(--error-color);
    }

    .success-message {
        color: var(--success-color);
    }

    footer {
        background-color: var(--primary-color);
        color: var(--surface-color);
        padding: 1rem 0;
        text-align: center;
    }

    @media (max-width: 768px) {
        main {
            padding: 2rem 1rem;
        }

        .form-actions {
            flex-direction: column;
        }

        .form-actions .btn {
            width: 100%;
        }

        .container {
            padding: 0 1rem;
        }
    }
    </style>
</head>
<body>
    <header class="header">
        <div class="container">
            <a href="index.jsp" class="logo">Quiz Master</a>
            <nav class="navbar">
                <a href="index.jsp" class="btn btn-secondary">Dashboard</a>
            </nav>
        </div>
    </header>

    <main class="container">
        <h1>Edit Quiz</h1>
        
        <c:if test="${not empty error}">
            <div class="error-message">${error}</div>
        </c:if>
        
        <c:if test="${not empty success}">
            <div class="success-message">${success}</div>
        </c:if>
        
        <form action="${pageContext.request.contextPath}/editQuiz" method="post" id="editQuizForm" class="edit-quiz-form">
            <input type="hidden" name="quizId" value="${quiz.id}">
            <div class="form-group">
                <label for="quizName">Quiz Name:</label>
                <input type="text" id="quizName" name="quizName" value="${quiz.name}" required>
            </div>
            
            <div id="questions-container">
                <c:forEach var="question" items="${quiz.questions}" varStatus="status">
                    <div class="question-group" data-question-id="${question.id}">
                        <h3>Question ${status.index + 1}</h3>
                        <input type="hidden" name="questionId${status.index}" value="${question.id}">
                        <div class="form-group">
                            <label for="questionText${status.index}">Question:</label>
                            <input type="text" id="questionText${status.index}" name="questionText${status.index}" value="${question.text}" required>
                        </div>
                        <div class="options-container">
                            <c:forEach var="option" items="${question.options}" varStatus="optionStatus">
                                <div class="form-group option-group">
                                    <label for="option${status.index}-${optionStatus.index}">Option ${optionStatus.index + 1}:</label>
                                    <input type="text" id="option${status.index}-${optionStatus.index}" name="option${status.index}-${optionStatus.index}" value="${option}" required>
                                    <button type="button" class="btn btn-danger remove-option" aria-label="Remove Option">&times;</button>
                                </div>
                            </c:forEach>
                        </div>
                        <button type="button" class="btn btn-secondary add-option-btn">Add Option</button>
                        <div class="form-group">
                            <label for="correctAnswer${status.index}">Correct Answer:</label>
                            <input type="number" id="correctAnswer${status.index}" name="correctAnswer${status.index}" value="${question.correctAnswer}" min="1" max="4" required>
                        </div>
                        <button type="button" class="btn btn-danger remove-question" aria-label="Remove Question">&times;</button>
                    </div>
                </c:forEach>
            </div>

            <div class="form-actions">
                <button type="button" class="btn btn-secondary add-question">Add Question</button>
                <button type="submit" class="btn btn-primary update-quiz">Update Quiz</button>
            </div>
            <input type="hidden" id="questionCount" name="questionCount" value="${quiz.questions.size()}">
        </form>
    </main>

    <footer class="footer">
        <div class="container">
            <p>&copy; 2024 Quiz Master. All rights reserved.</p>
        </div>
    </footer>

    <script>
        document.addEventListener('DOMContentLoaded', function() {
            const form = document.getElementById('editQuizForm');
            const questionsContainer = document.getElementById('questions-container');
            const addQuestionBtn = document.querySelector('.add-question');
            const questionCountInput = document.getElementById('questionCount');

            // Add new question
            addQuestionBtn.addEventListener('click', function() {
                const questionGroups = document.querySelectorAll('.question-group');
                const newQuestionIndex = questionGroups.length;
                const newQuestionHtml = 
                    '<div class="question-group" data-question-id="new">' +
                    '<h3>Question ' + (newQuestionIndex + 1) + '</h3>' +
                    '<input type="hidden" name="questionId' + newQuestionIndex + '" value="new">' +
                    '<div class="form-group">' +
                    '<label for="questionText' + newQuestionIndex + '">Question:</label>' +
                    '<input type="text" id="questionText' + newQuestionIndex + '" name="questionText' + newQuestionIndex + '" required>' +
                    '</div>' +
                    '<div class="options-container">' +
                    '<div class="form-group option-group">' +
                    '<label for="option' + newQuestionIndex + '-0">Option 1:</label>' +
                    '<input type="text" id="option' + newQuestionIndex + '-0" name="option' + newQuestionIndex + '-0" required>' +
                    '<button type="button" class="btn btn-danger remove-option">Remove Option</button>' +
                    '</div>' +
                    '<div class="form-group option-group">' +
                    '<label for="option' + newQuestionIndex + '-1">Option 2:</label>' +
                    '<input type="text" id="option' + newQuestionIndex + '-1" name="option' + newQuestionIndex + '-1" required>' +
                    '<button type="button" class="btn btn-danger remove-option">Remove Option</button>' +
                    '</div>' +
                    '</div>' +
                    '<button type="button" class="btn btn-secondary add-option-btn">Add Option</button>' +
                    '<div class="form-group">' +
                    '<label for="correctAnswer' + newQuestionIndex + '">Correct Answer:</label>' +
                    '<input type="number" id="correctAnswer' + newQuestionIndex + '" name="correctAnswer' + newQuestionIndex + '" min="1" max="2" required>' +
                    '</div>' +
                    '<button type="button" class="btn btn-danger remove-question">Remove Question</button>' +
                    '</div>';
                questionsContainer.insertAdjacentHTML('beforeend', newQuestionHtml);
                updateQuestionCount();
            });

            // Remove question
            questionsContainer.addEventListener('click', function(e) {
                if (e.target.classList.contains('remove-question')) {
                    const questionGroup = e.target.closest('.question-group');
                    questionGroup.remove();
                    updateQuestionCount();
                    updateQuestionNumbers();
                }
            });

            // Add option
            questionsContainer.addEventListener('click', function(e) {
                if (e.target.classList.contains('add-option-btn')) {
                    const optionsContainer = e.target.previousElementSibling;
                    const optionGroups = optionsContainer.querySelectorAll('.option-group');
                    if (optionGroups.length < 4) {
                        const newOptionIndex = optionGroups.length;
                        const questionIndex = e.target.closest('.question-group').querySelector('input[name^="questionId"]').name.match(/\d+/)[0];
                        const newOptionHtml = 
                            '<div class="form-group option-group">' +
                            '<label for="option' + questionIndex + '-' + newOptionIndex + '">Option ' + (newOptionIndex + 1) + ':</label>' +
                            '<input type="text" id="option' + questionIndex + '-' + newOptionIndex + '" name="option' + questionIndex + '-' + newOptionIndex + '" required>' +
                            '<button type="button" class="btn btn-danger remove-option">Remove Option</button>' +
                            '</div>';
                        optionsContainer.insertAdjacentHTML('beforeend', newOptionHtml);
                        updateCorrectAnswerMax(e.target.closest('.question-group'));
                    } else {
                        alert('Maximum 4 options allowed per question.');
                    }
                }
            });

            // Remove option
            questionsContainer.addEventListener('click', function(e) {
                if (e.target.classList.contains('remove-option')) {
                    const optionGroup = e.target.closest('.option-group');
                    const optionsContainer = optionGroup.parentElement;
                    if (optionsContainer.querySelectorAll('.option-group').length > 2) {
                        optionGroup.remove();
                        updateOptionNumbers(optionsContainer);
                        updateCorrectAnswerMax(e.target.closest('.question-group'));
                    } else {
                        alert('Minimum 2 options required per question.');
                    }
                }
            });

            // Update question count
            function updateQuestionCount() {
                const questionGroups = document.querySelectorAll('.question-group');
                questionCountInput.value = questionGroups.length;
            }

            // Update question numbers
            function updateQuestionNumbers() {
                const questionGroups = document.querySelectorAll('.question-group');
                questionGroups.forEach((group, index) => {
                    group.querySelector('h3').textContent = 'Question ' + (index + 1);
                });
            }

            // Update option numbers
            function updateOptionNumbers(optionsContainer) {
                const optionGroups = optionsContainer.querySelectorAll('.option-group');
                optionGroups.forEach((group, index) => {
                    const label = group.querySelector('label');
                    label.textContent = 'Option ' + (index + 1) + ':';
                    const input = group.querySelector('input');
                    input.name = input.name.replace(/option\d+-\d+/, 'option' + input.name.match(/\d+/)[0] + '-' + index);
                });
            }

            // Update correct answer max value
            function updateCorrectAnswerMax(questionGroup) {
                const correctAnswerInput = questionGroup.querySelector('input[name^="correctAnswer"]');
                const optionCount = questionGroup.querySelectorAll('.option-group').length;
                correctAnswerInput.max = optionCount;
            }

            // Form submission
            form.addEventListener('submit', function(e) {
                e.preventDefault();
                const questionGroups = document.querySelectorAll('.question-group');
                if (questionGroups.length < 2) {
                    alert('Quiz must have at least 2 questions');
                    return;
                }
                this.submit();
            });
        });
    </script>
</body>
</html>
