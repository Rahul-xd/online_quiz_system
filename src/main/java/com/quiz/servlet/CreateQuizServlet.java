package com.quiz.servlet;

import com.quiz.model.User;
import com.quiz.model.Quiz;
import com.quiz.model.Question;
import com.quiz.dao.QuizDAO;
import com.quiz.dao.QuestionDAO;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/createQuiz")
public class CreateQuizServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final int MIN_QUESTIONS = 2;
    private static final Logger LOGGER = Logger.getLogger(CreateQuizServlet.class.getName());

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        
        if (user == null) {
            LOGGER.log(Level.WARNING, "User session is null, redirecting to login page.");
            response.sendRedirect("login.jsp");
            return;
        }

        String quizName = request.getParameter("quizName");
        String questionCountStr = request.getParameter("questionCount");

        // Debugging logs
        LOGGER.log(Level.INFO, "Quiz Name: {0}", quizName);
        LOGGER.log(Level.INFO, "Question Count: {0}", questionCountStr);

        // Input validation
        if (quizName == null || quizName.trim().isEmpty() || questionCountStr == null || questionCountStr.trim().isEmpty()) {
            LOGGER.log(Level.WARNING, "Quiz name or question count is empty.");
            request.setAttribute("error", "Quiz name and question count are required");
            request.getRequestDispatcher("createQuiz.jsp").forward(request, response);
            return;
        }

        int questionCount;
        try {
            questionCount = Integer.parseInt(questionCountStr);
            if (questionCount <= 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            LOGGER.log(Level.WARNING, "Invalid question count format: {0}", questionCountStr);
            request.setAttribute("error", "Invalid question count");
            request.getRequestDispatcher("createQuiz.jsp").forward(request, response);
            return;
        }

        // Check for minimum number of questions
        if (questionCount < MIN_QUESTIONS) {
            LOGGER.log(Level.WARNING, "Question count is less than minimum required: {0}", questionCount);
            request.setAttribute("error", "Quiz must have at least " + MIN_QUESTIONS + " questions");
            request.getRequestDispatcher("createQuiz.jsp").forward(request, response);
            return;
        }

        QuizDAO quizDAO = new QuizDAO();
        QuestionDAO questionDAO = new QuestionDAO();

        try {
            // Create quiz
            Quiz newQuiz = new Quiz(0, quizName, new ArrayList<>(), user.getUserId());
            int quizId = quizDAO.createQuiz(newQuiz);
            LOGGER.log(Level.INFO, "Created quiz with ID: {0}", quizId);

            // Add questions
            for (int i = 1; i <= questionCount; i++) {
                String questionText = request.getParameter("question" + i);
                List<String> options = new ArrayList<>();
                for (int j = 1; j <= 4; j++) {
                    String option = request.getParameter("option" + i + "-" + j);
                    if (option != null && !option.trim().isEmpty()) {
                        options.add(option);
                    }
                }
                if (options.size() < 2 || options.size() > 4) {
                    LOGGER.log(Level.WARNING, "Invalid number of options for question {0}", i);
                    request.setAttribute("error", "Each question must have between 2 and 4 options");
                    request.getRequestDispatcher("createQuiz.jsp").forward(request, response);
                    return;
                }
                int correctAnswer = Integer.parseInt(request.getParameter("correctAnswer" + i));
                if (correctAnswer < 1 || correctAnswer > options.size()) {
                    LOGGER.log(Level.WARNING, "Invalid correct answer for question {0}", i);
                    request.setAttribute("error", "Correct answer must be between 1 and " + options.size());
                    request.getRequestDispatcher("createQuiz.jsp").forward(request, response);
                    return;
                }

                LOGGER.log(Level.INFO, "Question {0} - Text: {1}, Correct Answer: {2}", new Object[]{i, questionText, correctAnswer});
                for (int j = 0; j < options.size(); j++) {
                    LOGGER.log(Level.INFO, "Option {0}: {1}", new Object[]{j + 1, options.get(j)});
                }

                Question question = new Question(0, questionText, options, correctAnswer);
                questionDAO.addQuestion(question, quizId);
                LOGGER.log(Level.INFO, "Added question {0} to quiz {1}", new Object[]{i, quizId});
            }

            request.setAttribute("success", "Quiz created successfully. Quiz ID: " + quizId);
            response.sendRedirect("index.jsp");
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "SQL Exception while creating quiz", e);
            request.setAttribute("error", "Failed to create quiz. Please try again.");
            request.getRequestDispatcher("createQuiz.jsp").forward(request, response);
        }
    }
}
