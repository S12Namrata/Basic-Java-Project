/**
 * 
 */
package fr.epita.quiz.launcher;

import java.util.List;
import java.util.Scanner;

import fr.epita.quiz.datamodel.Answer;
import fr.epita.quiz.datamodel.Question;
import fr.epita.quiz.datamodel.Topic;
import fr.epita.quiz.service.AnswersService;
import fr.epita.quiz.service.QuestionAnswersMappingService;
import fr.epita.quiz.service.QuestionTopicsMappingService;
import fr.epita.quiz.service.QuestionsService;
import fr.epita.quiz.service.TopicsService;
import fr.epita.quiz.utility.Constants;
import fr.epita.quiz.utility.LogUtility;

/**
 * The Class QuizMaker.
 *
 * @author namrata
 */
public class QuizMaker {
	
	/** The logger. */
	private static String className = "QuizMaker";
	
	/** The log utility. */
	private static LogUtility logUtility = LogUtility.getInstance();
	
	/**
	 * Gets the random quiz.
	 *
	 * @param topicId the topic ID
	 * @param count the no. of questions in quiz
	 * @param level the level of questions
	 * @return list of questions
	 */

	public List<Question> getRandomQuiz(Integer topicId, Integer count, Integer level)
	{
		QuestionsService questionsService = new QuestionsService();
		if(level == null)
		{
			return questionsService.getListofQuestionsByTopicID(topicId, count);
		}
		return questionsService.getListofQuestionsByLevelAndTopicId(topicId, count, level);
	}
	
	
	/**
	 * Insert question.
	 *
	 * @return the string
	 */
	public void insertQuestion()
	{
		System.out.println("**** Details of Question  ****");
		QuestionsService questionsService  =new QuestionsService();

		Scanner scanner = new Scanner(System.in);
		scanner.reset();
		System.out.println(" Enter your question : ");
		String ques = scanner.nextLine();
		scanner.reset();
		System.out.println("Enter type of Question M(mcq)/O(open) : ");
		String typeOfQues = scanner.nextLine();
		scanner.reset();
		
		if(Constants.MCQ.equalsIgnoreCase(typeOfQues))
			typeOfQues = Constants.MCQ;
		
		if(Constants.OPEN.equalsIgnoreCase(typeOfQues))
			typeOfQues = Constants.OPEN;
		
		System.out.println("1 - Easy, 2 - Average, 3 - Difficult ");
		System.out.println("Enter level of the question  : ");
		int levelOfQues = scanner.nextInt();
		scanner.reset();
		
		questionsService.insertQuestion(ques, typeOfQues, levelOfQues);
		logUtility.logDebug(className, "Question Inserted");
		Question question = questionsService.getQuestionCurrentlyInserted();
		
		insertTopic(question);		
		insertAnswer(question);
		
	}


	/**
	 * Insert topic.
	 *
	 * @param question the question
	 */
	private void insertTopic(Question question) 
	{
		System.out.println(" ---- Add topic to the question ---- \n");
		Scanner scanner = new Scanner(System.in);
		scanner.reset();
		QuizLauncher quizLauncher = new QuizLauncher();
		Topic tp = quizLauncher.displayTopicMenu();
		TopicsService topicsService = new TopicsService();
		
		if(tp == null)
		{
			System.out.println("Do You want to add a new Topic (Y/N):");
			String confirmAdd = scanner.nextLine();
			scanner.reset();
			
			if("Y".equalsIgnoreCase(confirmAdd))
			{					
				System.out.println(" Enter Topic name : ");
				String ques = scanner.nextLine();
				scanner.reset();
				topicsService.insertTopic(ques);
				logUtility.logDebug(className, "Topic Inserted");
				tp = topicsService.getTopicCurrentlyInserted();
			}
		}
		
		if(tp != null)
		{
			QuestionTopicsMappingService questionTopicsMappingService = new QuestionTopicsMappingService();
			questionTopicsMappingService.insertQuestionTopicMapping(question.getId(), tp.getId());
			logUtility.logDebug(className, "Question Topic mapping Inserted");
		}
		else
		{
			logUtility.logError(className, "Error in inserting Topic Mapping ");
		}
	}
	
	
	/**
	 * Insert answer.
	 *
	 * @param question the question
	 */
	private void insertAnswer(Question question)
	{
		Scanner scanner = new Scanner(System.in);
		scanner.reset();
		AnswersService answersService = new AnswersService();
		QuestionAnswersMappingService questionAnswersMappingService = new QuestionAnswersMappingService();
		
		if(Constants.MCQ.equalsIgnoreCase(question.getType()))
		{
			for(int i =0; i<4;i++)
			{
				System.out.println("Enter option for the question : ");
				String answerText = scanner.next();
				scanner.reset();
				
				System.out.println("Is this option correct answer (Y/N): ");
				String isCorrect = scanner.next();
				scanner.reset();
				
				boolean isCorrectFlag = false;
				if("Y".equalsIgnoreCase(isCorrect))
					 isCorrectFlag = true;
				
				answersService.insertAnswer(answerText, isCorrectFlag);
				logUtility.logDebug(className, "Answer Inserted");
				Answer answer = answersService.getAnswerCurrentlyInserted();
				
				questionAnswersMappingService .insertQuestionAnswerMapping(question.getId(), answer.getId());
				logUtility.logDebug(className, "Question Answers mapping Inserted");
			}
		}
		else if(Constants.OPEN.equalsIgnoreCase(question.getType()))
		{
			System.out.println("Enter answer for the question : ");
			String answerText = scanner.next();
			scanner.reset();
			
			answersService.insertAnswer(answerText, true);
			logUtility.logDebug(className, "Answer Inserted");
			Answer answer = answersService.getAnswerCurrentlyInserted();
			
			questionAnswersMappingService.insertQuestionAnswerMapping(question.getId(), answer.getId());
			logUtility.logDebug(className, "Question Answers mapping Inserted");
		}
		
		System.out.println("Question Inserted Successfull.......");
	}

	
	
	/**
	 * Delete question.
	 *
	 * @return the string
	 */
	public String deleteQuestion()
	{
		QuestionsService questionsService  =new QuestionsService();
		QuestionTopicsMappingService questionTopicsMappingService = new QuestionTopicsMappingService();
		QuestionAnswersMappingService questionAnswersMappingService = new QuestionAnswersMappingService();
		Scanner scanner = new Scanner(System.in);
		
		System.out.println(" Enter your question id to be deleted: ");
		String quesId = scanner.next();
		scanner.reset();
		
		if(quesId != null)
		{
			Question question = questionsService.getQuestionById(Integer.valueOf(quesId));
			if(question != null)
			{
				System.out.println("Question :" + question.getId() + " " + question.getQuesText());
				System.out.println("Do You want to Delete (Y/N):");
				String confirmUpdate = scanner.next();
				scanner.reset();
				
				if("Y".equalsIgnoreCase(confirmUpdate))
				{
					questionTopicsMappingService.deleteQuestionTopicMapping(Integer.valueOf(quesId));
					logUtility.logDebug(className, "Question Topics mapping Deleted");
					questionAnswersMappingService.deleteQuestionAnswerMapping(Integer.valueOf(quesId));
					logUtility.logDebug(className, "Question Answers mapping Deleted");
					questionsService.deleteQuestion(Integer.valueOf(quesId));
					logUtility.logDebug(className, "Question Deleted");
					System.out.println("Question Deleted succesfully");
				}
				else
					return Constants.ERROR;
			}
			else
			{
				System.out.println("Question with Id : "+quesId +" not Found");
				return Constants.ERROR;
			}
		}
		else
		{
			logUtility.logError(className, " question ID is null");
			return Constants.ERROR;
		}
		scanner.reset();
		
		return Constants.SUCCESS;
	}
	
	/**
	 * Update question.
	 *
	 * @return the string
	 */
	public String updateQuestion()
	{
		QuestionsService questionsService  =new QuestionsService();
		Scanner scanner = new Scanner(System.in);
		scanner.reset();
		System.out.println(" Enter your question id to be updated: ");
		String quesId = scanner.nextLine();
		scanner.reset();
		
		if(quesId != null)
		{
			Question question = questionsService.getQuestionById(Integer.valueOf(quesId));
			if(question != null)
			{
				System.out.println("Question :" + question.getId() + " " + question.getQuesText());
				System.out.println("Do You want to Update (Y/N):");
				String confirmUpdate = scanner.nextLine();
				scanner.reset();
				
				if("Y".equalsIgnoreCase(confirmUpdate))
				{
					System.out.println(" Enter Question: ");
					String questext = scanner.nextLine();
					scanner.reset();
					
					System.out.println("Enter type of Question M(mcq)/O(open) : ");
					String typeOfQues = scanner.nextLine();
					scanner.reset();
					
					if(Constants.MCQ.equalsIgnoreCase(typeOfQues))
						typeOfQues = Constants.MCQ;
					
					if(Constants.OPEN.equalsIgnoreCase(typeOfQues))
						typeOfQues = Constants.OPEN;
					
					System.out.println("1 - Easy, 2 - Average, 3 - Difficult ");
					System.out.println("Enter level of the question  : ");
					int levelOfQues = scanner.nextInt();
					scanner.reset();
					
					questionsService.updateQuestion(Integer.valueOf(quesId), questext, typeOfQues, levelOfQues);
					System.out.println("Question with Id : "+quesId +" Updated succesfully");
				}
				else
					return Constants.ERROR;
			}
			else
			{
				System.out.println("Question with Id : "+quesId +" not Found");
				return Constants.ERROR;
			}
				
		}
		else
			logUtility.logError(className, " question ID is null");
		
		return Constants.SUCCESS;
	}
}
