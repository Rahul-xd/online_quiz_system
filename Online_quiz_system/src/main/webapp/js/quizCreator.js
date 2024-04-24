document.getElementById("quizForm").addEventListener("submit", function(event) {
    /*event.preventDefault(); // Prevent form submission*/

    // Get form values
    var formData = new FormData(document.getElementById("quizForm"));

    // Convert form data to object
    var quizData = {};
    formData.forEach(function(value, key) {
        if (key.includes("-")) {
            var parts = key.split("-");
            var questionNum = parts[0].slice(-1);
            var optionNum = parts[1];
            if (!quizData["question" + questionNum]) {
                quizData["question" + questionNum] = {};
            }
            quizData["question" + questionNum]["option" + optionNum] = value;
        } else {
            quizData[key] = value;
        }
    });

    // Log the quiz data (you can handle it in various ways, like sending it to a server)
    console.log(quizData);
});

// Function to add a new question group
var questionCounter = 1;
document.querySelector(".add-question").addEventListener("click", function() {
    questionCounter++;

    var newQuestionGroup = document.createElement("div");
    newQuestionGroup.classList.add("question-group");
    newQuestionGroup.innerHTML = `
        <label for="question${questionCounter}">Question:</label>
        <input type="text" id="question${questionCounter}" name="question${questionCounter}" required>

        <label for="option${questionCounter}-1">Option 1:</label>
        <input type="text" id="option${questionCounter}-1" name="option${questionCounter}-1" required>

        <label for="option${questionCounter}-2">Option 2:</label>
        <input type="text" id="option${questionCounter}-2" name="option${questionCounter}-2" required>

        <label for="option${questionCounter}-3">Option 3:</label>
        <input type="text" id="option${questionCounter}-3" name="option${questionCounter}-3" required>

        <label for="option${questionCounter}-4">Option 4:</label>
        <input type="text" id="option${questionCounter}-4" name="option${questionCounter}-4" required>

        <label for="correctAnswer${questionCounter}">Correct Answer:</label>
        <input type="number" id="correctAnswer${questionCounter}" name="correctAnswer${questionCounter}" min="1" max="4" required>

        <button type="button" class="remove-question">Remove</button>
    `;
    document.querySelector("#quizForm").insertBefore(newQuestionGroup, document.querySelector(".add-question"));
    
    // Add event listener to remove button
    newQuestionGroup.querySelector(".remove-question").addEventListener("click", function() {
        newQuestionGroup.remove();
    });
});
