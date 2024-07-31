let quizDuration;
let currentQuestion = 0;
let reviewMode = false;
let questions;
let prevButton;
let nextButton;
let submitButton;
let reviewButton;
let questionCountSpan;
let timerDisplay;
let quizForm;
let timeLeft;
let timerInterval;


document.addEventListener('DOMContentLoaded', function() {
    console.log("DOM Content Loaded");

    questions = document.querySelectorAll('.question-container');
    prevButton = document.getElementById('prev-button');
    nextButton = document.getElementById('next-button');
    submitButton = document.getElementById('submit-button');
    reviewButton = document.getElementById('review-button');
    questionCountSpan = document.querySelector('.number-of-question');
    timerDisplay = document.getElementById('timer');
    quizForm = document.getElementById('quiz-form');

    if (questions.length > 0) {
        // Quiz-taking logic
        const quizDurationElement = document.getElementById('quizDuration');
        if (!quizDurationElement) {
            console.error("Quiz duration element not found");
            alert("Error: Quiz duration element is missing. Please contact support.");
            return;
        }
        quizDuration = parseInt(quizDurationElement.value) || 30;
        console.log("Quiz Duration:", quizDuration);

        if (!timerDisplay) {
            console.error("Timer display element not found");
            alert("Error: Timer display element is missing. Please contact support.");
            return;
        }

        if (!quizForm) {
            console.error("Quiz form element not found");
            alert("Error: Quiz form element is missing. Please contact support.");
            return;
        }

        timeLeft = quizDuration * 60;
        console.log("Total questions:", questions.length);
        if (questions.length === 0) {
            console.error("No questions found. Check if the quiz data is properly loaded.");
            alert("Error: No questions found for this quiz. Please try again later.");
            return;
        }

        showQuestion(currentQuestion);
        updateTimer();
        timerInterval = setInterval(updateTimer, 1000);

        prevButton.addEventListener('click', showPreviousQuestion);
        nextButton.addEventListener('click', showNextQuestion);
        submitButton.addEventListener('click', submitQuizForm);
        reviewButton.addEventListener('click', toggleReviewMode);
        updateCorrectAnswerInput();
    } else {
        // Result page logic
        console.log("Quiz result page loaded");
		if (document.querySelector('.result-details')) {
		    highlightResults();
		}
    }
});

function updateTimer() {
    if (timeLeft < 0) {
        clearInterval(timerInterval);
        alert("Time's up! Submitting your quiz now.");
        submitQuizForm();
        return;
    }

    const minutes = Math.floor(timeLeft / 60);
    const seconds = timeLeft % 60;
    timerDisplay.textContent = `${minutes}:${seconds < 10 ? '0' : ''}${seconds}`;
    console.log("Timer updated:", minutes, "minutes", seconds, "seconds remaining");
    timeLeft--;
}

function showQuestion(index) {
    questions.forEach(q => q.style.display = 'none');
    questions[index].style.display = 'block';
    questionCountSpan.textContent = `${index + 1} of ${questions.length} questions`;
    console.log("Showing question", index + 1);
    updateNavigationButtons();
}

function updateNavigationButtons() {
    prevButton.style.display = currentQuestion > 0 ? 'inline-block' : 'none';
    nextButton.style.display = currentQuestion < questions.length - 1 ? 'inline-block' : 'none';
    submitButton.style.display = currentQuestion === questions.length - 1 ? 'inline-block' : 'none';
    reviewButton.textContent = reviewMode ? 'Exit Review' : 'Review All';
    console.log("Navigation buttons updated");
}

function showNextQuestion() {
    if (currentQuestion < questions.length - 1) {
        currentQuestion++;
        showQuestion(currentQuestion);
    }
}

function showPreviousQuestion() {
    if (currentQuestion > 0) {
        currentQuestion--;
        showQuestion(currentQuestion);
    }
}

function selectOption(element, questionId, optionIndex) {
    const questionContainer = element.closest('.question-container');
    const options = questionContainer.querySelectorAll('.option-div');
    options.forEach(opt => opt.classList.remove('selected'));
    element.classList.add('selected');
    const answerInput = document.getElementById('answer' + questionId);
    answerInput.value = optionIndex;
    console.log("Selected option", optionIndex, "for question", questionId);
}

function toggleReviewMode() {
    reviewMode = !reviewMode;
    if (reviewMode) {
        showAllQuestions();
    } else {
        hideAllQuestionsExceptCurrent();
    }
    updateNavigationButtons();
}

function showAllQuestions() {
    questions.forEach(q => q.style.display = 'block');
    prevButton.style.display = 'none';
    nextButton.style.display = 'none';
    submitButton.style.display = 'inline-block';
    console.log("Review mode enabled: Showing all questions");
}

function hideAllQuestionsExceptCurrent() {
    questions.forEach((q, index) => {
        q.style.display = index === currentQuestion ? 'block' : 'none';
    });
    updateNavigationButtons();
    console.log("Review mode disabled: Showing current question only");
}

function submitQuizForm() {
    if (confirm('Are you sure you want to submit the quiz?')) {
        clearInterval(timerInterval);
        var form = document.getElementById('quiz-form');
        
        // Collect all answers before submitting
        questions.forEach((question, index) => {
            var selectedOption = question.querySelector('.option-div.selected');
            var answerInput = document.getElementById('answer' + (index + 1));
            if (selectedOption) {
                answerInput.value = selectedOption.getAttribute('data-option-index');
            }
        });

        console.log("Submitting form with answers:", 
            Array.from(form.elements)
                .filter(el => el.name.startsWith('answer'))
                .map(el => `${el.name}: ${el.value}`)
                .join(', ')
        );

        form.submit();
    }
}

function updateCorrectAnswerInput() {
    const questions = document.querySelectorAll('.question-container');
    questions.forEach((question, index) => {
        const options = question.querySelectorAll('.option-div');
        const correctAnswerInput = document.getElementById(`correctAnswer${index + 1}`);
        if (correctAnswerInput) {
            correctAnswerInput.max = options.length;
        }
    });
}

function highlightResults() {
    const results = document.querySelectorAll('.result-container');
    results.forEach(result => {
        result.querySelectorAll('.option-div').forEach((option) => {
            const isCorrect = option.dataset.correct === 'true';
            const isSelected = option.dataset.selected === 'true';
            
            if (isCorrect && isSelected) {
                option.classList.add('correct-answer', 'user-answer');
            } else if (isCorrect) {
                option.classList.add('correct-answer');
            } else if (isSelected) {
                option.classList.add('user-answer');
            }
        });
    });
}

window.showNextQuestion = showNextQuestion;
window.showPreviousQuestion = showPreviousQuestion;
window.selectOption = selectOption;
window.submitQuizForm = submitQuizForm;
window.toggleReviewMode = toggleReviewMode;
