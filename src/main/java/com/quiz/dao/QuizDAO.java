package com.quiz.dao;

import com.quiz.model.Quiz;
import com.quiz.model.Question;
import com.quiz.util.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class QuizDAO {
	
	private static final Logger LOGGER = Logger.getLogger(QuizDAO.class.getName());
    public int createQuiz(Quiz quiz) throws SQLException {
        String sql = "INSERT INTO quizzes (name, creator_id) VALUES (?, ?)";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            conn.setAutoCommit(false);
            pstmt.setString(1, quiz.getName());
            pstmt.setString(2, quiz.getCreatorId());
            pstmt.executeUpdate();
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    conn.commit();
                    return generatedKeys.getInt(1);
                } else {
                    conn.rollback();
                    throw new SQLException("Creating quiz failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Database error. Please contact the administrator.", e);
        }
    }

    public Quiz getQuizById(int quizId) throws SQLException {
        String sql = "SELECT * FROM quizzes WHERE id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, quizId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Quiz quiz = new Quiz(
                        rs.getInt("id"),
                        rs.getString("name"),
                        null,  // We'll fetch questions separately
                        rs.getString("creator_id")
                    );

                    // Fetch questions for this quiz
                    List<Question> questions = getQuestionsForQuiz(quizId);
                    quiz.setQuestions(questions);

                    return quiz;
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Database error. Please contact the administrator.", e);
        }
        throw new SQLException("Quiz not found");
    }

    private List<Question> getQuestionsForQuiz(int quizId) throws SQLException {
        String sql = "SELECT * FROM questions WHERE quiz_id = ?";
        List<Question> questions = new ArrayList<>();
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, quizId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    List<String> options = new ArrayList<>();
                    for (int i = 1; i <= 4; i++) {
                        options.add(rs.getString("option" + i));
                    }
                    questions.add(new Question(
                        rs.getInt("id"),
                        rs.getString("text"),
                        options,
                        rs.getInt("correct_answer")
                    ));
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Database error. Please contact the administrator.", e);
        }
        return questions;
    }

    public void saveQuizResult(String userId, int quizId, double score) throws SQLException {
        String sql = "INSERT INTO quiz_results (user_id, quiz_id, score) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, userId);
            pstmt.setInt(2, quizId);
            pstmt.setDouble(3, score);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Saving quiz result failed, no rows affected.");
            }
            LOGGER.info("Quiz result saved successfully. User ID: " + userId + ", Quiz ID: " + quizId + ", Score: " + score);
        } catch (SQLException e) {
            LOGGER.severe("Error saving quiz result: " + e.getMessage());
            throw new SQLException("Database error. Please contact the administrator.", e);
        }
    }

    public List<Quiz> getQuizzesForUser(String userId) throws SQLException {
        List<Quiz> quizzes = new ArrayList<>();
        String sql = "SELECT * FROM quizzes WHERE creator_id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Quiz quiz = new Quiz(
                        rs.getInt("id"),
                        rs.getString("name"),
                        null, // We're not loading questions here for performance reasons
                        rs.getString("creator_id")
                    );
                    quizzes.add(quiz);
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Database error. Please contact the administrator.", e);
        }
        return quizzes;
    }

    public void deleteQuiz(int quizId) throws SQLException {
        String sql = "DELETE FROM quizzes WHERE id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, quizId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Database error. Please contact the administrator.", e);
        }
    }

    public List<Quiz> getQuizzesByCreator(String creatorId) throws SQLException {
        List<Quiz> quizzes = new ArrayList<>();
        String sql = "SELECT * FROM quizzes WHERE creator_id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, creatorId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Quiz quiz = new Quiz(
                        rs.getInt("id"),
                        rs.getString("name"),
                        null,  // We're not loading questions here for performance reasons
                        rs.getString("creator_id")
                    );
                    quizzes.add(quiz);
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Database error. Please contact the administrator.", e);
        }
        return quizzes;
    }

    public void updateQuiz(Quiz quiz) throws SQLException {
        String sql = "UPDATE quizzes SET name = ? WHERE id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, quiz.getName());
            pstmt.setInt(2, quiz.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Database error. Please contact the administrator.", e);
        }
    }
}
