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
import fr.epita.quiz.datamodel.McqOptions;
import fr.epita.quiz.datamodel.McqQuestion;
import fr.epita.quiz.datamodel.OpenQuestion;
import fr.epita.quiz.datamodel.Question;
import fr.epita.quiz.utility.Constants;
import fr.epita.quiz.utility.DataConnection;

/**
 * The Class QuestionsService.
 *
 * @author namrata
 */
public class QuestionsService {
	
	/** The data connection. */
	DataConnection dataConnection;
	
	/** The Constant SELECT_ALL_QUESTIONS. */
	private static final String SELECT_ALL_QUESTIONS = "SELECT * FROM QUESTIONS";
	
	/** The Constant SELECT_QUESTION_BY_ID. */
	private static final String SELECT_QUESTION_BY_ID = "SELECT * FROM QUESTIONS WHERE ID = ?";
	
	/** The Constant SELECT_QUESTION_BY_MAX_ID. */
	private static final String SELECT_QUESTION_BY_MAX_ID = "SELECT * FROM QUESTIONS where ID = SELECT MAX(ID) FROM QUESTIONS ";
	
	/** The Constant UPDATE_QUESTION. */
	private static final String UPDATE_QUESTION = "UPDATE QUESTIONS SET TEXT = ?, TYPE = ?,LEVEL = ? WHERE ID = ?";
	
	/** The Constant INSERT_QUESTION. */
	private static final String INSERT_QUESTION = "INSERT INTO QUESTIONS(TEXT,TYPE,LEVEL) VALUES (?,?,?)";
	
	/** The Constant DELETE_QUESTION. */
	private static final String DELETE_QUESTION = "DELETE FROM QUESTIONS WHERE ID = ?";
	
	/** The Constant SELECT_QUESTION_BY_TOPIC. */
	private static final String SELECT_QUESTION_BY_TOPIC = "SELECT * FROM QUESTIONS JOIN QUESTIONS_TOPIC  WHERE QUESTIONS_TOPIC.QUES_ID = QUESTIONS.ID AND QUESTIONS_TOPIC.TOPIC_ID = ? ORDER BY RAND() LIMIT ?";
	
	/** The Constant SELECT_QUESTION_BY_TOPIC_AND_LEVEL. */
	private static final String SELECT_QUESTION_BY_TOPIC_AND_LEVEL = "SELECT * FROM QUESTIONS JOIN QUESTIONS_TOPIC  WHERE QUESTIONS_TOPIC.QUES_ID = QUESTIONS.ID AND QUESTIONS_TOPIC.TOPIC_ID = ? AND QUESTIONS.LEVEL = ? ORDER BY RAND() LIMIT ?";

	
	/**
	 * Gets the listof questions.
	 *
	 * @return allQuestions
	 */
	public List<Question> getListofQuestions()
	{
		List<Question> allQuestion = new ArrayList<>();
		try 
		{
			dataConnection = DataConnection.getInstance();
			Connection connection = dataConnection.getConnection();
			PreparedStatement sqlQuery = connection.prepareStatement(SELECT_ALL_QUESTIONS);
			populateListOfquestions(allQuestion, sqlQuery);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return allQuestion;
	}
	

	/**
	 * Populate list ofquestions.
	 *
	 * @param allQuestion the all question
	 * @param sqlQuery the SQL query to be executed
	 * @throws SQLException the SQL exception
	 */

	private void populateListOfquestions(List<Question> allQuestion, PreparedStatement sqlQuery) throws SQLException {
		TopicsService topicService;
		AnswersService answerService;
		McqQuestion mcqQuestion;
		OpenQuestion openQuestion;
		ResultSet resultSet = sqlQuery.executeQuery();
		while (resultSet.next()) {
			if(Constants.MCQ.equalsIgnoreCase(resultSet.getString(Constants.TYPE)))
			{
				mcqQuestion = new McqQuestion();
				mcqQuestion.setId(resultSet.getInt(Constants.ID));
				mcqQuestion.setQuesText(resultSet.getString(Constants.TEXT));
				mcqQuestion.setType(resultSet.getString(Constants.TYPE));
				mcqQuestion.setLevel(resultSet.getInt(Constants.LEVEL));
				topicService = new TopicsService();
				mcqQuestion.setListOfTopicsId(topicService.getListOfTopicByQuesId(resultSet.getInt(Constants.ID)));
				answerService = new AnswersService();
				McqOptions mcqOptions = new McqOptions();
				mcqOptions.setOptions(answerService.getAnswerByQuesId(resultSet.getInt(Constants.ID)));
				mcqQuestion.setMcqOptions(mcqOptions);
				allQuestion.add(mcqQuestion);	
			}
			if(Constants.OPEN.equalsIgnoreCase(resultSet.getString(Constants.TYPE)))
			{
				openQuestion = new OpenQuestion();
				openQuestion.setId(resultSet.getInt(Constants.ID));
				openQuestion.setQuesText(resultSet.getString(Constants.TEXT));
				openQuestion.setType(resultSet.getString(Constants.TYPE));
				openQuestion.setLevel(resultSet.getInt(Constants.LEVEL));
				topicService = new TopicsService();
				openQuestion.setListOfTopicsId(topicService.getListOfTopicByQuesId(resultSet.getInt(Constants.ID)));
				answerService = new AnswersService();
				List<Answer> listofAnswers = answerService.getAnswerByQuesId(resultSet.getInt(Constants.ID));
				if(listofAnswers.size() == 1)
				{
					openQuestion.setAnswer(listofAnswers.get(0));
				}
				allQuestion.add(openQuestion);	
			}
							
		}
	}
	
	/**
	 * Gets the question by id.
	 *
	 * @param id the id
	 * @return question
	 * @Param id select question by id
	 */
	public Question getQuestionById(Integer id)
	{
		Question question;
		try 
		{
			dataConnection = DataConnection.getInstance();
			Connection connection = dataConnection.getConnection();
			PreparedStatement sqlQuery = connection.prepareStatement(SELECT_QUESTION_BY_ID);
			sqlQuery.setInt(1, id);
			ResultSet resultSet = sqlQuery.executeQuery();
			while (resultSet.next()) {	
				question = new Question();
				question.setId(resultSet.getInt(Constants.ID));
				question.setQuesText(resultSet.getNString(Constants.TEXT));	
				question.setType(resultSet.getString(Constants.TYPE));
				question.setLevel(resultSet.getInt(Constants.LEVEL));
				return question;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * Gets the question currently inserted.
	 *
	 * @return the question currently inserted
	 */
	public Question getQuestionCurrentlyInserted()
	{
		Question question = new Question();
		try 
		{
			dataConnection = DataConnection.getInstance();
			Connection connection = dataConnection.getConnection();
			PreparedStatement sqlQuery = connection.prepareStatement(SELECT_QUESTION_BY_MAX_ID);
			ResultSet resultSet = sqlQuery.executeQuery();
			while (resultSet.next()) {			
				question.setId(resultSet.getInt(Constants.ID));
				question.setQuesText(resultSet.getNString(Constants.TEXT));	
				question.setType(resultSet.getString(Constants.TYPE));
				question.setLevel(resultSet.getInt(Constants.LEVEL));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return question;
	}
	
	/**
	 * Update question.
	 *
	 * @param id the id
	 * @param updateText the text to be updated
	 * @param type the type of question
	 * @param level the level of the question
	 * @return rowsChanged no of rows altered
	 * @Param id the question id
	 */
	public Integer updateQuestion(Integer id, String updateText, String type, Integer level)
	{
		Integer rowsChanged = 0;
		try 
		{
			dataConnection = DataConnection.getInstance();
			Connection connection = dataConnection.getConnection();
			PreparedStatement sqlQuery = connection.prepareStatement(UPDATE_QUESTION);
			sqlQuery.setString(1,updateText);
			sqlQuery.setString(2,type);
			sqlQuery.setInt(3, level);
			sqlQuery.setInt(4,id);
			rowsChanged = sqlQuery.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return rowsChanged;
	}
	
	/**
	 * Insert question.
	 *
	 * @param text the Question name to insert
	 * @param type the type
	 * @param level the level
	 * @return rowsChanged no of rows altered
	 */
	public Integer insertQuestion(String text, String type, Integer level)
	{
		Integer rowsChanged = 0;
		try 
		{
			dataConnection = DataConnection.getInstance();
			Connection connection = dataConnection.getConnection();
			PreparedStatement sqlQuery = connection.prepareStatement(INSERT_QUESTION);

			sqlQuery.setString(1,text);
			sqlQuery.setString(2,type);
			sqlQuery.setInt(3,level);
			rowsChanged = sqlQuery.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return rowsChanged;
	}
	
	/**
	 * Delete question.
	 *
	 * @param id the Question to delete
	 * @return rowsChanged no of rows altered
	 */
	public Integer deleteQuestion(Integer id)
	{
		Integer rowsChanged = 0;
		try 
		{
			dataConnection = DataConnection.getInstance();
			Connection connection = dataConnection.getConnection();
			PreparedStatement sqlQuery = connection.prepareStatement(DELETE_QUESTION);
			sqlQuery.setInt(1, id);
			rowsChanged = sqlQuery.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return rowsChanged;
	}
	
	/**
	 * Gets the list of questions by topic ID.
	 *
	 * @param topicId the topic id
	 * @param count the count
	 * @return allQuestions the list of questions
	 */
	public List<Question> getListofQuestionsByTopicID(Integer topicId, Integer count)
	{
		List<Question> allQuestion = new ArrayList<>();
		try 
		{
			dataConnection = DataConnection.getInstance();
			Connection connection = dataConnection.getConnection();
			PreparedStatement sqlQuery = connection.prepareStatement(SELECT_QUESTION_BY_TOPIC);
			sqlQuery.setInt(1, topicId);
			if(count == null)
				count = Integer.valueOf(Constants.DEFAULT_COUNT);
			sqlQuery.setInt(2, count);
			populateListOfquestions(allQuestion, sqlQuery);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return allQuestion;
	}
	
	/**
	 * Gets the list of questions by level and topic id.
	 *
	 * @param topicId the topic id
	 * @param count the count
	 * @param level the level
	 * @return allQuestions the list of questions
	 */
	public List<Question> getListofQuestionsByLevelAndTopicId(Integer topicId, Integer count, Integer level)
	{
		List<Question> allQuestion = new ArrayList<>();
		try 
		{
			dataConnection = DataConnection.getInstance();
			Connection connection = dataConnection.getConnection();
			PreparedStatement sqlQuery = connection.prepareStatement(SELECT_QUESTION_BY_TOPIC_AND_LEVEL);
			sqlQuery.setInt(1, topicId);
			sqlQuery.setInt(2, level);
			if(count == null)
				count = Integer.valueOf(Constants.DEFAULT_COUNT);
			sqlQuery.setInt(3, count);
			
			populateListOfquestions(allQuestion, sqlQuery);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return allQuestion;
	}

}
