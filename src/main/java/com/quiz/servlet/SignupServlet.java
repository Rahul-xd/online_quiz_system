package com.quiz.servlet;

import com.quiz.dao.UserDAO;
import com.quiz.model.User;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mindrot.jbcrypt.BCrypt;

@WebServlet("/signup")
public class SignupServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(SignupServlet.class.getName());

    private static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
    private static final String EMAIL_PATTERN = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String firstName = request.getParameter("fname");
        String lastName = request.getParameter("lname");
        String email = request.getParameter("email");
        String userId = request.getParameter("userid");
        String password = request.getParameter("password");

        // Input validation
        if (isInvalid(firstName) || isInvalid(lastName) || isInvalid(email) || isInvalid(userId) || isInvalid(password)) {
            request.setAttribute("error", "All fields are required");
            request.getRequestDispatcher("signup.jsp").forward(request, response);
            return;
        }

        // Email validation
        if (!email.matches(EMAIL_PATTERN)) {
            request.setAttribute("error", "Invalid email format");
            request.getRequestDispatcher("signup.jsp").forward(request, response);
            return;
        }

        // Password validation
        if (!password.matches(PASSWORD_PATTERN)) {
            request.setAttribute("error", "Password must be at least 8 characters long and include uppercase, lowercase, number, and special character");
            request.getRequestDispatcher("signup.jsp").forward(request, response);
            return;
        }

        UserDAO userDAO = new UserDAO();

        try {
            // Check if the user already exists
            if (userDAO.userExists(userId)) {
                request.setAttribute("error", "Username already exists");
                request.getRequestDispatcher("signup.jsp").forward(request, response);
                return;
            }

            // Log the plain text password before hashing
            LOGGER.log(Level.INFO, "Plain text password before hashing: {0}", password);

            // Hash the password before storing it in the database
            String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(10));

            // Log the hashed password
            LOGGER.log(Level.INFO, "Hashed password: {0}", hashedPassword);

            User newUser = new User(0, firstName, lastName, email, userId, hashedPassword);

            // Logging attempt to create user
            LOGGER.log(Level.INFO, "Attempting to create user: {0}", userId);
            userDAO.addUser(newUser);
            LOGGER.log(Level.INFO, "User created successfully: {0}", userId);

            // Registration successful
            request.setAttribute("success", "Registration successful. Please login.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to register user", e);
            request.setAttribute("error", "Registration failed. Please try again later.");
            request.getRequestDispatcher("signup.jsp").forward(request, response);
        }
    }

    private boolean isInvalid(String value) {
        return value == null || value.trim().isEmpty();
    }
}
