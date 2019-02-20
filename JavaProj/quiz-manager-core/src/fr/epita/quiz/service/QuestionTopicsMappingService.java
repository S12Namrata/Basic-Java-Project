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
 * The Class QuestionTopicsMappingService.
 *
 * @author namrata
 */
public class QuestionTopicsMappingService {
	
	/** The data connection. */
	DataConnection dataConnection;
	
	/** The Constant INSERT_QUESTION_TOPIC_MAPPING. */
	private static final String INSERT_QUESTION_TOPIC_MAPPING = "INSERT INTO QUESTIONS_TOPIC VALUES (?,?);";
	
	/** The Constant DELETE_QUESTION_TOPIC_MAPPING. */
	private static final String DELETE_QUESTION_TOPIC_MAPPING = "DELETE FROM QUESTIONS_TOPIC WHERE QUES_ID = ?";

	
	/**
	 * Insert question topic mapping.
	 *
	 * @param quesId the ques id
	 * @param topicId the topic id
	 * @return the integer
	 */
	public Integer insertQuestionTopicMapping(int quesId, int topicId)
	{
		Integer rowsChanged = 0;
		try 
		{
			dataConnection = DataConnection.getInstance();
			Connection connection = dataConnection.getConnection();
			PreparedStatement sqlQuery = connection.prepareStatement(INSERT_QUESTION_TOPIC_MAPPING);

			sqlQuery.setInt(1,quesId);
			sqlQuery.setInt(2,topicId);
			rowsChanged = sqlQuery.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return rowsChanged;
	}
	
	/**
	 * Delete question topic mapping.
	 *
	 * @param id the id
	 * @return the integer
	 */
	public Integer deleteQuestionTopicMapping(Integer id)
	{
		Integer rowsChanged = 0;
		try 
		{
			dataConnection = DataConnection.getInstance();
			Connection connection = dataConnection.getConnection();
			PreparedStatement sqlQuery = connection.prepareStatement(DELETE_QUESTION_TOPIC_MAPPING);
			sqlQuery.setInt(1, id);
			rowsChanged = sqlQuery.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return rowsChanged;
	}

}
