package com.quiz.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import com.quiz.model.Question;
import com.quiz.model.User;
import com.quiz.model.Quiz;
import com.quiz.dao.QuizDAO;
import java.sql.SQLException;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@WebServlet("/takeQuiz")
public class TakeQuizServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(TakeQuizServlet.class.getName());
    private QuizDAO quizDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        quizDAO = new QuizDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String quizIdParam = request.getParameter("quizId");
        LOGGER.info("Received quizId: " + quizIdParam);

        if (quizIdParam == null || quizIdParam.trim().isEmpty()) {
            request.setAttribute("error", "Invalid quiz ID");
            request.getRequestDispatcher("takeQuiz.jsp").forward(request, response);
            return;
        }

        try {
            int quizId = Integer.parseInt(quizIdParam);
            Quiz quiz = quizDAO.getQuizById(quizId);

            if (quiz == null) {
                request.setAttribute("error", "Quiz not found");
                request.getRequestDispatcher("takeQuiz.jsp").forward(request, response);
                return;
            }

            request.setAttribute("quizDuration", 30); // Set quiz duration in minutes
            request.setAttribute("quiz", quiz);
            request.getRequestDispatcher("takeQuiz.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid quiz ID format");
            request.getRequestDispatcher("takeQuiz.jsp").forward(request, response);
        } catch (SQLException e) {
            request.setAttribute("error", "Database error occurred. Please try again later.");
            request.getRequestDispatcher("takeQuiz.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOGGER.info("Received quiz submission");
        
        // Log all request parameters
        Map<String, String[]> parameterMap = request.getParameterMap();
        for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
            LOGGER.info("Parameter: " + entry.getKey() + " = " + String.join(", ", entry.getValue()));
        }
        
        String quizIdParam = request.getParameter("quizId");

        if (quizIdParam == null || quizIdParam.trim().isEmpty()) {
            request.setAttribute("error", "Quiz ID is required");
            request.getRequestDispatcher("takeQuiz.jsp").forward(request, response);
            return;
        }

        int quizId;
        try {
            quizId = Integer.parseInt(quizIdParam);
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid quiz ID format");
            request.getRequestDispatcher("takeQuiz.jsp").forward(request, response);
            return;
        }

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        if (session.getAttribute("submittedQuiz" + quizId) != null) {
            request.setAttribute("error", "You have already submitted this quiz");
            request.getRequestDispatcher("takeQuiz.jsp").forward(request, response);
            return;
        }

        try {
            Quiz quiz = quizDAO.getQuizById(quizId);
            if (quiz == null) {
                request.setAttribute("error", "Quiz not found");
                request.getRequestDispatcher("takeQuiz.jsp").forward(request, response);
                return;
            }

            List<Question> questions = quiz.getQuestions();
            int totalQuestions = questions.size();
            int correctAnswers = 0;
            
            Map<Integer, Integer> userAnswers = new HashMap<>();

            for (Question question : questions) {
                String userAnswer = request.getParameter("answer" + question.getId());
                LOGGER.info("Question ID: " + question.getId() + ", User Answer: " + userAnswer);
                if (userAnswer != null && !userAnswer.trim().isEmpty()) {
                    try {
                        int answer = Integer.parseInt(userAnswer);
                        userAnswers.put(question.getId(), answer);
                        if (answer == question.getCorrectAnswer()) {
                            correctAnswers++;
                        }
                    } catch (NumberFormatException e) {
                        LOGGER.warning("Invalid answer format for question ID: " + question.getId());
                    }
                }
            }

            double score = (double) correctAnswers / totalQuestions * 100;
            LOGGER.info("Score calculated: " + score + "%, Correct answers: " + correctAnswers + " out of " + totalQuestions);
            
            // Set attributes for the request
            request.setAttribute("quiz", quiz);
            request.setAttribute("userAnswers", userAnswers);
            request.setAttribute("score", score);
            request.setAttribute("totalQuestions", totalQuestions);
            request.setAttribute("correctAnswers", correctAnswers);

            // Save the quiz result
            quizDAO.saveQuizResult(user.getUserId(), quizId, score);

            LOGGER.info("Quiz submission received. Quiz ID: " + quizId + ", User ID: " + user.getUserId());
            LOGGER.info("Total questions: " + totalQuestions + ", Correct answers: " + correctAnswers);
            LOGGER.info("User answers: " + userAnswers);

            request.getRequestDispatcher("quizResult.jsp").forward(request, response);
        } catch (SQLException e) {
            request.setAttribute("error", "Database error occurred. Please try again later.");
            request.getRequestDispatcher("takeQuiz.jsp").forward(request, response);
        }
    }
}
