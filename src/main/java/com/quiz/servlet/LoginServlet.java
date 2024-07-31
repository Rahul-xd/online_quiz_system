package com.quiz.servlet;

import com.quiz.dao.QuizDAO;
import com.quiz.dao.UserDAO;
import com.quiz.model.Quiz;
import com.quiz.model.User;
import org.mindrot.jbcrypt.BCrypt;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final int MAX_LOGIN_ATTEMPTS = 5;
    private final Map<String, Integer> loginAttempts = new ConcurrentHashMap<>();
    private static final Logger LOGGER = Logger.getLogger(LoginServlet.class.getName());

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userId = request.getParameter("userid");
        String password = request.getParameter("password");

        LOGGER.log(Level.INFO, "Login attempt for user: {0}", userId);

        if (isInvalid(userId) || isInvalid(password)) {
            request.setAttribute("error", "Username and password are required");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }

        if (loginAttempts.getOrDefault(userId, 0) >= MAX_LOGIN_ATTEMPTS) {
            request.setAttribute("error", "Account locked. Please contact support.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }

        UserDAO userDAO = new UserDAO();

        try {
            User user = userDAO.getUserByUserId(userId);
            if (user != null) {
                LOGGER.log(Level.INFO, "User found in database: {0}", userId);
                boolean passwordMatch = BCrypt.checkpw(password, user.getPassword());
                LOGGER.log(Level.INFO, "Password match: {0}", passwordMatch);

                if (passwordMatch) {
                    HttpSession session = request.getSession();
                    session.setAttribute("user", user);
                    loginAttempts.remove(userId);

                    QuizDAO quizDAO = new QuizDAO();
                    List<Quiz> createdQuizzes = quizDAO.getQuizzesByCreator(user.getUserId());
                    session.setAttribute("createdQuizzes", createdQuizzes);

                    response.sendRedirect("index.jsp");
                } else {
                    increaseLoginAttempts(userId);
                    request.setAttribute("error", "Invalid username or password");
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                }
            } else {
                increaseLoginAttempts(userId);
                request.setAttribute("error", "Invalid username or password");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Database error occurred", e);
            request.setAttribute("error", "Database error occurred. Please try again later.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }

    private boolean isInvalid(String value) {
        return value == null || value.trim().isEmpty();
    }

    private void increaseLoginAttempts(String userId) {
        loginAttempts.put(userId, loginAttempts.getOrDefault(userId, 0) + 1);
    }
}
