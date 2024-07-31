package com.quiz.servlet;

import com.quiz.dao.QuestionDAO;
import com.quiz.dao.QuizDAO;
import com.quiz.model.Question;
import com.quiz.model.Quiz;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/editQuiz")
public class EditQuizServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String quizIdParam = request.getParameter("quizId");
        if (quizIdParam == null || quizIdParam.trim().isEmpty()) {
            request.setAttribute("error", "Invalid quiz ID");
            request.getRequestDispatcher("error.jsp").forward(request, response);
            return;
        }

        try {
            int quizId = Integer.parseInt(quizIdParam);
            QuizDAO quizDAO = new QuizDAO();
            Quiz quiz = quizDAO.getQuizById(quizId);

            if (quiz == null) {
                request.setAttribute("error", "Quiz not found");
                request.getRequestDispatcher("error.jsp").forward(request, response);
                return;
            }

            request.setAttribute("quiz", quiz);
            request.getRequestDispatcher("editQuiz.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid quiz ID format");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        } catch (SQLException e) {
            request.setAttribute("error", "Database error occurred. Please try again later.");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String quizIdParam = request.getParameter("quizId");
        String quizName = request.getParameter("quizName");

        if (quizIdParam == null || quizIdParam.trim().isEmpty() || quizName == null || quizName.trim().isEmpty()) {
            request.setAttribute("error", "Quiz ID and name are required");
            request.getRequestDispatcher("error.jsp").forward(request, response);
            return;
        }

        try {
            int quizId = Integer.parseInt(quizIdParam);
            QuizDAO quizDAO = new QuizDAO();
            Quiz quiz = quizDAO.getQuizById(quizId);

            if (quiz == null) {
                request.setAttribute("error", "Quiz not found");
                request.getRequestDispatcher("error.jsp").forward(request, response);
                return;
            }

            quiz.setName(quizName);
            quizDAO.updateQuiz(quiz);

            int questionCount = Integer.parseInt(request.getParameter("questionCount"));
            QuestionDAO questionDAO = new QuestionDAO();

            // Remove existing questions not present in the form
            List<Integer> updatedQuestionIds = new ArrayList<>();
            for (int i = 0; i < questionCount; i++) {
                String questionIdParam = request.getParameter("questionId" + i);
                if (!"new".equals(questionIdParam)) {
                    updatedQuestionIds.add(Integer.parseInt(questionIdParam));
                }
            }
            questionDAO.removeQuestionsNotInList(quizId, updatedQuestionIds);

            // Update or add questions
            for (int i = 0; i < questionCount; i++) {
                String questionText = request.getParameter("questionText" + i);
                List<String> options = new ArrayList<>();
                int optionCount = 0;
                while (request.getParameter("option" + i + "-" + optionCount) != null) {
                    options.add(request.getParameter("option" + i + "-" + optionCount));
                    optionCount++;
                }
                int correctAnswer = Integer.parseInt(request.getParameter("correctAnswer" + i));

                String questionIdParam = request.getParameter("questionId" + i);
                if ("new".equals(questionIdParam)) {
                    Question newQuestion = new Question(0, questionText, options, correctAnswer);
                    questionDAO.addQuestion(newQuestion, quizId);
                } else {
                    int questionId = Integer.parseInt(questionIdParam);
                    Question question = new Question(questionId, questionText, options, correctAnswer);
                    questionDAO.updateQuestion(question);
                }
            }

            request.setAttribute("success", "Quiz updated successfully");
            response.sendRedirect("index.jsp");
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid input format");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        } catch (SQLException e) {
            request.setAttribute("error", "Database error occurred. Please try again later.");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }
}
