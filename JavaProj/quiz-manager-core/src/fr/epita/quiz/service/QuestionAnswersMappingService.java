/**
 * 
 */
package fr.epita.quiz.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import fr.epita.quiz.utility.DataConnection;

// TODO: Auto-generated Javadoc

/**
 * The Class QuestionAnswersMappingService.
 */
public class QuestionAnswersMappingService {
	
	/** The data connection. */
	DataConnection dataConnection;


	/** The Constant INSERT_QUESTION_ANSWERS_MAPPING. */
	private static final String INSERT_QUESTION_ANSWERS_MAPPING = "INSERT INTO QUESTION_ANSWERS VALUES (?,?);";
	
	/** The Constant DELETE_QUESTION_ANSWERS_MAPPING. */
	private static final String DELETE_QUESTION_ANSWERS_MAPPING = "DELETE FROM QUESTION_ANSWERS WHERE QUES_ID = ?";


	/**
	 * Insert question answer mapping.
	 *
	 * @param quesId the ques id
	 * @param ansId the ans id
	 * @return the integer
	 */
	public Integer insertQuestionAnswerMapping(int quesId, int ansId)
	{
		Integer rowsChanged = 0;
		try 
		{
			dataConnection = DataConnection.getInstance();
			Connection connection = dataConnection.getConnection();
			PreparedStatement sqlQuery = connection.prepareStatement(INSERT_QUESTION_ANSWERS_MAPPING);

			sqlQuery.setInt(1,quesId);
			sqlQuery.setInt(2,ansId);
			rowsChanged = sqlQuery.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return rowsChanged;
	}
	
	
	/**
	 * Delete question answer mapping.
	 *
	 * @param id the id
	 * @return the integer
	 */
	public Integer deleteQuestionAnswerMapping(Integer id)
	{
		Integer rowsChanged = 0;
		try 
		{
			dataConnection = DataConnection.getInstance();
			Connection connection = dataConnection.getConnection();
			PreparedStatement sqlQuery = connection.prepareStatement(DELETE_QUESTION_ANSWERS_MAPPING);
			sqlQuery.setInt(1, id);
			rowsChanged = sqlQuery.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return rowsChanged;
	}
	
}
