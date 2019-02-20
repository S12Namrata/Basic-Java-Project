/**
 * 
 */
package fr.epita.quiz.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.epita.quiz.datamodel.Answer;
import fr.epita.quiz.utility.Constants;
import fr.epita.quiz.utility.DataConnection;

/**
 * The Class AnswersService.
 *
 * @author namrata
 */
public class AnswersService {
	
	/** The data connection. */
	DataConnection dataConnection;
	
	/** The Constant SELECT_ALL_ANSWERS. */
	private static final String SELECT_ALL_ANSWERS = "SELECT * FROM ANSWERS";
	
	/** The Constant SELECT_ANSWER_BY_ID. */
	private static final String SELECT_ANSWER_BY_ID = "SELECT * FROM ANSWERS WHERE ID = ?";
	
	/** The Constant SELECT_ANSWER_BY_QUES_ID. */
	private static final String SELECT_ANSWER_BY_QUES_ID = "SELECT ID, TEXT, IS_CORRECT FROM ANSWERS LEFT JOIN QUESTION_ANSWERS WHERE ANSWERS.ID = QUESTION_ANSWERS.ANS_ID AND QUESTION_ANSWERS.QUES_ID = ?";
	
	/** The Constant UPDATE_ANSWER. */
	private static final String UPDATE_ANSWER = "UPDATE ANSWERS SET TEXT = ?, IS_CORRECT = ? WHERE ID = ?";
	
	/** The Constant INSERT_ANSWER. */
	private static final String INSERT_ANSWER = "INSERT INTO ANSWERS(TEXT,IS_CORRECT) VALUES (?,?)";
	
	/** The Constant DELETE_ANSWER. */
	private static final String DELETE_ANSWER = "DELETE FROM ANSWERS WHERE ID = ?";
	
	/** The Constant SELECT_ANSWER_BY_MAX_ID. */
	private static final String SELECT_ANSWER_BY_MAX_ID = "SELECT * FROM ANSWERS where ID = SELECT MAX(ID) FROM ANSWERS ";
	
	
	/**
	 * Gets the list of answers.
	 *
	 * @return allAnswers
	 */
	public List<Answer> getListofAnswers()
	{
		List<Answer> allAnswer = new ArrayList<>();
		try 
		{
			dataConnection = DataConnection.getInstance();
			Connection connection = dataConnection.getConnection();
			PreparedStatement sqlQuery = connection.prepareStatement(SELECT_ALL_ANSWERS);
			ResultSet resultSet = sqlQuery.executeQuery();
			while (resultSet.next()) {
				Answer answer = new Answer();
				
				answer.setId(resultSet.getInt(Constants.ID));
				answer.setText(resultSet.getString(Constants.TEXT));
				answer.setIsCorrect(resultSet.getBoolean(Constants.IS_CORRECT));
				allAnswer.add(answer);		
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return allAnswer;
	}
	
	/**
	 * Gets the answer by id.
	 *
	 * @param id the id
	 * @return answer
	 * @Param id the answer id
	 */
	public Answer getAnswerById(Integer id)
	{
		Answer answer = new Answer();
		try 
		{
			dataConnection = DataConnection.getInstance();
			Connection connection = dataConnection.getConnection();
			PreparedStatement sqlQuery = connection.prepareStatement(SELECT_ANSWER_BY_ID);
			sqlQuery.setInt(1, id);
			ResultSet resultSet = sqlQuery.executeQuery();
			while (resultSet.next()) {			
				answer.setId(resultSet.getInt(Constants.ID));
				answer.setText(resultSet.getString(Constants.TEXT));
				answer.setIsCorrect(resultSet.getBoolean(Constants.IS_CORRECT));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return answer;
	}
	
	/**
	 * Gets the answer by ques id.
	 *
	 * @param id the id
	 * @return answer
	 * @Param id the ques id
	 */
	public List<Answer> getAnswerByQuesId(Integer id)
	{
		List<Answer> allAnswer = new ArrayList<>();
		try 
		{
			dataConnection = DataConnection.getInstance();
			Connection connection = dataConnection.getConnection();
			PreparedStatement sqlQuery = connection.prepareStatement(SELECT_ANSWER_BY_QUES_ID);
			sqlQuery.setInt(1, id);
			ResultSet resultSet = sqlQuery.executeQuery();
			while (resultSet.next()) {	
				
				Answer answer = new Answer();
				answer.setId(resultSet.getInt(Constants.ID));
				answer.setText(resultSet.getString(Constants.TEXT));
				answer.setIsCorrect(resultSet.getBoolean(Constants.IS_CORRECT));
				
				allAnswer.add(answer);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return allAnswer;
	}
	
	/**
	 * Update answer.
	 *
	 * @param id the id
	 * @param updateText the text to be updated
	 * @param isCorrect the flag for correct answer
	 * @return rowsChanged no of rows altered
	 * @Param id the answer id
	 */
	public Integer updateAnswer(Integer id, String updateText, Boolean isCorrect )
	{
		Integer rowsChanged = 0;
		try 
		{
			dataConnection = DataConnection.getInstance();
			Connection connection = dataConnection.getConnection();
			PreparedStatement sqlQuery = connection.prepareStatement(UPDATE_ANSWER);
			sqlQuery.setString(1,updateText);
			sqlQuery.setBoolean(2,isCorrect);
			sqlQuery.setInt(3,id);
			rowsChanged = sqlQuery.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return rowsChanged;
	}
	
	/**
	 * Insert answer.
	 *
	 * @param text the Answer name to insert
	 * @param isCorrect the flag for correct answer
	 * @return rowsChanged no of rows altered
	 */
	public Integer insertAnswer(String text, Boolean isCorrect)
	{
		Integer rowsChanged = 0;
		try 
		{
			dataConnection = DataConnection.getInstance();
			Connection connection = dataConnection.getConnection();
			PreparedStatement sqlQuery = connection.prepareStatement(INSERT_ANSWER);

			sqlQuery.setString(1,text);
			sqlQuery.setBoolean(2,isCorrect);
			rowsChanged = sqlQuery.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return rowsChanged;
	}
	
	/**
	 * Delete answer.
	 *
	 * @param id the Answer to delete
	 * @return rowsChanged no of rows altered
	 */
	public Integer deleteAnswer(Integer id)
	{
		Integer rowsChanged = 0;
		try 
		{
			dataConnection = DataConnection.getInstance();
			Connection connection = dataConnection.getConnection();
			PreparedStatement sqlQuery = connection.prepareStatement(DELETE_ANSWER);
			sqlQuery.setInt(1, id);
			rowsChanged = sqlQuery.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return rowsChanged;
	}
	
	/**
	 * Gets the answer currently inserted.
	 *
	 * @return the answer currently inserted
	 */
	public Answer getAnswerCurrentlyInserted()
	{
		Answer answer = new Answer();
		try 
		{
			dataConnection = DataConnection.getInstance();
			Connection connection = dataConnection.getConnection();
			PreparedStatement sqlQuery = connection.prepareStatement(SELECT_ANSWER_BY_MAX_ID);
			ResultSet resultSet = sqlQuery.executeQuery();
			while (resultSet.next()) {			
				answer.setId(resultSet.getInt(Constants.ID));
				answer.setText(resultSet.getString(Constants.TEXT));
				answer.setIsCorrect(resultSet.getBoolean(Constants.IS_CORRECT));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return answer;
	}
	
}
