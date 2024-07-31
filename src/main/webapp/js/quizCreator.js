document.addEventListener('DOMContentLoaded', function() {
    const MAX_QUESTIONS = 50;
    const MIN_OPTIONS = 2;
    const MAX_OPTIONS = 4;
    let questionCounter = 0;
    const questionsContainer = document.getElementById('questions-container');
    const addQuestionBtn = document.querySelector('.add-question');
    const questionCountInput = document.getElementById('questionCount');

    function addQuestion() {
        if (questionCounter >= MAX_QUESTIONS) {
            alert('Maximum number of questions reached');
            return;
        }

        questionCounter++;
        questionCountInput.value = questionCounter;

        const newQuestionGroup = document.createElement('div');
        newQuestionGroup.classList.add('question-group');
        newQuestionGroup.innerHTML = `
            <h3>Question ${questionCounter}</h3>
            <div class="form-group">
                <label for="question${questionCounter}">Question:</label>
                <input type="text" id="question${questionCounter}" name="question${questionCounter}" required>
            </div>
            <div id="options-container-${questionCounter}">
                <div class="form-group">
                    <label for="option${questionCounter}-1">Option 1:</label>
                    <input type="text" id="option${questionCounter}-1" name="option${questionCounter}-1" required>
                </div>
                <div class="form-group">
                    <label for="option${questionCounter}-2">Option 2:</label>
                    <input type="text" id="option${questionCounter}-2" name="option${questionCounter}-2" required>
                </div>
            </div>
			<button type="button" class="add-option-btn" onclick="addOption(${questionCounter})">Add Option</button>
            <div class="form-group">
                <label for="correctAnswer${questionCounter}">Correct Answer (1-${MAX_OPTIONS}):</label>
                <input type="number" id="correctAnswer${questionCounter}" name="correctAnswer${questionCounter}" min="1" max="${MAX_OPTIONS}" required>
            </div>
			<button type="button" class="remove-question" aria-label="Remove Question">&times;</button>
        `;

        questionsContainer.appendChild(newQuestionGroup);

        newQuestionGroup.querySelector('.remove-question').addEventListener('click', function() {
            newQuestionGroup.remove();
            questionCounter--;
            questionCountInput.value = questionCounter;
            updateQuestionNumbers();
        });
    }

    window.addOption = function addOption(questionNumber) {
        const optionsContainer = document.getElementById(`options-container-${questionNumber}`);
        const optionCount = optionsContainer.children.length;

        if (optionCount < MAX_OPTIONS) {
            const newOption = document.createElement('div');
            newOption.classList.add('form-group');
            newOption.innerHTML = `
                <label for="option${questionNumber}-${optionCount + 1}">Option ${optionCount + 1}:</label>
                <input type="text" id="option${questionNumber}-${optionCount + 1}" name="option${questionNumber}-${optionCount + 1}" required>
            `;
            optionsContainer.appendChild(newOption);
        } else {
            alert('Maximum 4 options per question');
        }
    }

    function updateQuestionNumbers() {
        const questionGroups = document.querySelectorAll('.question-group');
        questionGroups.forEach((group, index) => {
            const questionNumber = index + 1;
            group.querySelector('h3').textContent = `Question ${questionNumber}`;
            group.querySelectorAll('input').forEach(input => {
                const nameParts = input.name.split('-');
                nameParts[0] = nameParts[0].replace(/\d+/, questionNumber);
                input.name = nameParts.join('-');
                input.id = input.name;
            });
            group.querySelectorAll('label').forEach(label => {
                label.htmlFor = label.htmlFor.replace(/\d+/, questionNumber);
            });
        });
    }

    addQuestionBtn.addEventListener('click', addQuestion);

    addQuestion();
});
