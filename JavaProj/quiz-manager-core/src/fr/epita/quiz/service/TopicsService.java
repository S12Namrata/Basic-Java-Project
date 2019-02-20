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

import fr.epita.quiz.datamodel.Question;
import fr.epita.quiz.datamodel.Topic;
import fr.epita.quiz.utility.Constants;
import fr.epita.quiz.utility.DataConnection;

/**
 * The Class TopicsService.
 *
 * @author namrata
 */
public class TopicsService {
	
	/** The data connection. */
	DataConnection dataConnection;
	
	/** The Constant SELECT_ALL_TOPICS. */
	private static final String SELECT_ALL_TOPICS = "SELECT * FROM TOPICS";
	
	/** The Constant SELECT_TOPIC_BY_ID. */
	private static final String SELECT_TOPIC_BY_ID = "SELECT * FROM TOPICS WHERE ID = ?";
	
	/** The Constant SELECT_TOPIC_BY_QUES_ID. */
	private static final String SELECT_TOPIC_BY_QUES_ID = "SELECT ID, TEXT FROM TOPICS LEFT JOIN QUESTIONS_TOPIC  WHERE TOPICS.ID = QUESTIONS_TOPIC.TOPIC_ID AND QUESTIONS_TOPIC.QUES_ID = ?";
	
	private static final String SELECT_TOPIC_BY_MAX_ID = "SELECT * FROM TOPICS where ID = SELECT MAX(ID) FROM TOPICS ";
	
	/** The Constant SELECT_TOPIC_BY_SEARCH_STRING. */
	private static final String SELECT_TOPIC_BY_SEARCH_STRING = "SELECT * FROM TOPICS WHERE TEXT LIKE ?";
	
	/** The Constant UPDATE_TOPIC. */
	private static final String UPDATE_TOPIC = "UPDATE TOPICS SET TEXT = ? WHERE ID = ?";
	
	/** The Constant INSERT_TOPIC. */
	private static final String INSERT_TOPIC = "INSERT INTO TOPICS(TEXT) VALUES (?)";
	
	/** The Constant DELETE_TOPIC. */
	private static final String DELETE_TOPIC = "DELETE FROM TOPICS WHERE ID = ?";
	
	/**
	 * Gets the listof topics.
	 *
	 * @return allTopics
	 */
	public List<Topic> getListofTopics()
	{
		List<Topic> allTopics = new ArrayList<>();
		try 
		{
			dataConnection = DataConnection.getInstance();
			Connection connection = dataConnection.getConnection();
			PreparedStatement sqlQuery = connection.prepareStatement(SELECT_ALL_TOPICS);
			ResultSet resultSet = sqlQuery.executeQuery();
			while (resultSet.next()) {
				Topic topic = new Topic();
				
				topic.setId(resultSet.getInt(Constants.ID));
				topic.setTopicText(resultSet.getNString(Constants.TEXT));
				
				allTopics.add(topic);		
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return allTopics;
	}
	
	/**
	 * Gets the topic by id.
	 *
	 * @param id the id
	 * @return Topic
	 * @Param id select topic by id
	 */
	public Topic getTopicById(Integer id)
	{
		Topic topic = new Topic();
		try 
		{
			dataConnection = DataConnection.getInstance();
			Connection connection = dataConnection.getConnection();
			PreparedStatement sqlQuery = connection.prepareStatement(SELECT_TOPIC_BY_ID);
			sqlQuery.setInt(1, id);
			ResultSet resultSet = sqlQuery.executeQuery();
			while (resultSet.next()) {			
				topic.setId(resultSet.getInt(Constants.ID));
				topic.setTopicText(resultSet.getNString(Constants.TEXT));		
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return topic;
	}
	
	/**
	 * Gets the list of topic by ques id.
	 *
	 * @param id the id
	 * @return List of Topic
	 * @Param id the question id
	 */
	public List<Topic> getListOfTopicByQuesId(Integer id)
	{
		List<Topic> allTopics = new ArrayList<>();
		try 
		{
			dataConnection = DataConnection.getInstance();
			Connection connection = dataConnection.getConnection();
			PreparedStatement sqlQuery = connection.prepareStatement(SELECT_TOPIC_BY_QUES_ID);
			sqlQuery.setInt(1, id);
			ResultSet resultSet = sqlQuery.executeQuery();
			while (resultSet.next()) {
				Topic topic = new Topic();
				
				topic.setId(resultSet.getInt(Constants.ID));
				topic.setTopicText(resultSet.getNString(Constants.TEXT));
				
				allTopics.add(topic);		
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return allTopics;
	}
	
	/**
	 * Gets the topic by search string.
	 *
	 * @param searchString the search string
	 * @return Topic
	 * @Param searchString select topic by searchString
	 */
	public List<Topic> getTopicBySearchString(String searchString)
	{
		List<Topic> allTopics = new ArrayList<>();
		try 
		{
			dataConnection = DataConnection.getInstance();
			Connection connection = dataConnection.getConnection();
			PreparedStatement sqlQuery = connection.prepareStatement(SELECT_TOPIC_BY_SEARCH_STRING);
			sqlQuery.setString(1, "%"+searchString+"%");
			ResultSet resultSet = sqlQuery.executeQuery();
			while (resultSet.next()) {
				Topic topic = new Topic();
				
				topic.setId(resultSet.getInt(Constants.ID));
				topic.setTopicText(resultSet.getNString(Constants.TEXT));
				
				allTopics.add(topic);		
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return allTopics;
	}
	
	/**
	 * Update topic.
	 *
	 * @param id the id
	 * @param updateText the text to be updated
	 * @return rowsChanged no of rows altered
	 * @Param id the topic id
	 */
	public Integer updateTopic(Integer id, String updateText)
	{
		Integer rowsChanged = 0;
		try 
		{
			dataConnection = DataConnection.getInstance();
			Connection connection = dataConnection.getConnection();
			PreparedStatement sqlQuery = connection.prepareStatement(UPDATE_TOPIC);
			sqlQuery.setString(1,updateText);
			sqlQuery.setInt(2,id);
			rowsChanged = sqlQuery.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return rowsChanged;
	}
	
	/**
	 * Insert topic.
	 *
	 * @param text the topic name to insert
	 * @return rowsChanged no of rows altered
	 */
	public Integer insertTopic(String text)
	{
		Integer rowsChanged = 0;
		try 
		{
			dataConnection = DataConnection.getInstance();
			Connection connection = dataConnection.getConnection();
			PreparedStatement sqlQuery = connection.prepareStatement(INSERT_TOPIC);

			sqlQuery.setString(1,text);
			rowsChanged = sqlQuery.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return rowsChanged;
	}

	
	/**
	 * Delete topic.
	 *
	 * @param id the topic to delete
	 * @return rowsChanged no of rows altered
	 */
	public Integer deleteTopic(Integer id)
	{
		Integer rowsChanged = 0;
		try 
		{
			dataConnection = DataConnection.getInstance();
			Connection connection = dataConnection.getConnection();
			PreparedStatement sqlQuery = connection.prepareStatement(DELETE_TOPIC);
			sqlQuery.setInt(1, id);
			rowsChanged = sqlQuery.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return rowsChanged;
	}
	
	public Topic getTopicCurrentlyInserted()
	{
		Topic topic = new Topic();
		try 
		{
			dataConnection = DataConnection.getInstance();
			Connection connection = dataConnection.getConnection();
			PreparedStatement sqlQuery = connection.prepareStatement(SELECT_TOPIC_BY_MAX_ID);
			ResultSet resultSet = sqlQuery.executeQuery();
			while (resultSet.next()) {			
				
				topic.setId(resultSet.getInt(Constants.ID));
				topic.setTopicText(resultSet.getNString(Constants.TEXT));
		
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return topic;
	}
	
}
