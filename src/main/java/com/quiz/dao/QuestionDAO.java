package com.quiz.dao;

import com.quiz.model.Question;
import com.quiz.util.DatabaseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.List;

public class QuestionDAO {
    private static final Logger logger = LoggerFactory.getLogger(QuestionDAO.class);

    public void addQuestion(Question question, int quizId) throws SQLException {
        validateQuestion(question);

        String sql = "INSERT INTO questions (quiz_id, text, option1, option2, option3, option4, correct_answer) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, quizId);
            stmt.setString(2, question.getText());
            List<String> options = question.getOptions();
            stmt.setString(3, options.size() > 0 ? options.get(0) : "");
            stmt.setString(4, options.size() > 1 ? options.get(1) : "");
            stmt.setString(5, options.size() > 2 ? options.get(2) : "");
            stmt.setString(6, options.size() > 3 ? options.get(3) : "");
            stmt.setInt(7, question.getCorrectAnswer());
            stmt.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error adding question", e);
            throw e;
        }
    }

    public void updateQuestion(Question question) throws SQLException {
        validateQuestion(question);

        String sql = "UPDATE questions SET text = ?, option1 = ?, option2 = ?, option3 = ?, option4 = ?, correct_answer = ? WHERE id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, question.getText());
            pstmt.setString(2, question.getOptions().get(0));
            pstmt.setString(3, question.getOptions().get(1));
            pstmt.setString(4, question.getOptions().get(2));
            pstmt.setString(5, question.getOptions().get(3));
            pstmt.setInt(6, question.getCorrectAnswer());
            pstmt.setInt(7, question.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error updating question", e);
            throw e;
        }
    }

    public void removeQuestionsNotInList(int quizId, List<Integer> questionIds) throws SQLException {
        String query = "DELETE FROM questions WHERE quiz_id = ? AND id NOT IN (" + questionIds.toString().replaceAll("[\\[\\]]", "") + ")";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, quizId);
            stmt.executeUpdate();
        }
    }

    private void validateQuestion(Question question) {
        if (question.getText() == null || question.getText().trim().isEmpty()) {
            throw new IllegalArgumentException("Question text cannot be null or empty");
        }
        List<String> options = question.getOptions();
        if (options.size() < 2 || options.size() > 4) {
            throw new IllegalArgumentException("Question must have between 2 and 4 options");
        }
        for (String option : options) {
            if (option == null || option.trim().isEmpty()) {
                throw new IllegalArgumentException("Options cannot be null or empty");
            }
        }
        if (question.getCorrectAnswer() < 1 || question.getCorrectAnswer() > options.size()) {
            throw new IllegalArgumentException("Correct answer must be between 1 and " + options.size());
        }
    }
}
