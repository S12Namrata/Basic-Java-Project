package fr.epita.quiz.launcher;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import fr.epita.quiz.datamodel.Answer;
import fr.epita.quiz.datamodel.McqOptions;
import fr.epita.quiz.datamodel.McqQuestion;
import fr.epita.quiz.datamodel.OpenQuestion;
import fr.epita.quiz.datamodel.Question;
import fr.epita.quiz.datamodel.Topic;
import fr.epita.quiz.service.TopicsService;
import fr.epita.quiz.utility.Constants;
import fr.epita.quiz.utility.PDFService;

/**
 * The Class Quiz.
 */
public class QuizLauncher {
	

	/**
	 * Start quiz.
	 */
	public void startQuiz()
	{
		Scanner scanner = new Scanner(System.in);
		String choice = "N";
		do
		{
			choice = "N";
			Topic topic = displayTopicMenu();
			if(topic != null)
			{
				Integer level = displayLevelMenu();
				if( Constants.RANDOMLY == level )
					level = null;
				
				QuizMaker quizMaker = new QuizMaker();
				List<Question> listOfQuestions = quizMaker.getRandomQuiz(topic.getId(), 5, level);
				
				Integer userPref = displayUserPref();
				if(Constants.START_QUIZ == userPref)
				{
					System.out.println("\n---------------------------------------------\n");
					displayQuiz(listOfQuestions);
				}
				else if(Constants.SAVE_AS_PDF == userPref)
				{
					PDFService pdfService = new PDFService();
					pdfService.saveAsPdf(listOfQuestions,topic.getTopicText());
				}
				
				
				
				System.out.println("\n---------------------------------------------");
				System.out.println(" Do you want to take another quiz (Y/N) : ");
				choice = scanner.next();
			}
		}
		while("Y".equalsIgnoreCase(choice));
	}
	
	/**
	 * Display quiz.
	 *
	 * @param listOfQuestions the list of questions
	 * @return the list of user answers
	 */
	private List displayQuiz(List<Question> listOfQuestions)
	{
		Scanner scanner = new Scanner(System.in);
		int correctAnswersCount = 0;
		List userAnswers = new ArrayList<>();
		
		int i = 1, size = listOfQuestions.size();
		if(size > 0)
		{
			for (Question question : listOfQuestions) 
			{

				System.out.println("\n***** Q. " + i +" *****\n"); i++;
				correctAnswersCount = correctAnswersCount + displayQuestions(scanner,userAnswers, question);

			}

			Double percentCorrect = ((correctAnswersCount*100)/(double)size);
			
			System.out.println("\n*****************************************\n");
			System.out.println("\tResult : " + percentCorrect + "%");
			System.out.println("\tCorrect answer: " + correctAnswersCount+"/"+listOfQuestions.size());
			System.out.println("\n*****************************************\n");

			displayResult(userAnswers, listOfQuestions);
		}
		else
			System.out.println("Please choose another topic and different level.");
		return userAnswers;
	}

	/**
	 * Display questions.
	 *
	 * @param scanner the scanner
	 * @param userAnswers the user answers
	 * @param question the question
	 * @return the number of correct answer
	 */
	private int displayQuestions(Scanner scanner,List userAnswers, Question question) {
		
		String answer;
		int correctAnswer=0;
		System.out.println(question.getQuesText());
		if(question instanceof McqQuestion)
		{
			McqQuestion mcqQuestion = (McqQuestion) question;
			McqOptions mcqOptions = mcqQuestion.getMcqOptions();
			List<Answer> listOfAnswers = mcqOptions.getOptions();
			int mcq = 97;
			System.out.println("");
			for(Answer ans : listOfAnswers)
			{
				System.out.println((char)mcq + ". " + ans.getText());
				mcq++;
			}

			System.out.println("\n Enter your answer : ");
			answer = scanner.next();
			userAnswers.add(answer);

			if("a".equalsIgnoreCase(answer) && listOfAnswers.get(0).getIsCorrect() 
				|| "b".equalsIgnoreCase(answer) && listOfAnswers.get(1).getIsCorrect()
				|| "c".equalsIgnoreCase(answer) && listOfAnswers.get(2).getIsCorrect()
				|| "d".equalsIgnoreCase(answer) && listOfAnswers.get(3).getIsCorrect())
			{
				
				correctAnswer++;
			}


		}
		else if (question instanceof OpenQuestion)
		{
			OpenQuestion openQuestion = (OpenQuestion) question;
			System.out.println("\n Enter your answer : ");
			answer = scanner.next();
			userAnswers.add(answer);

			if(openQuestion.getAnswer().getText().equalsIgnoreCase(answer))
				correctAnswer++;
		}
		return correctAnswer;
	}
	
	
	/**
	 * Display result.
	 *
	 * @param userAnswers the user answers
	 * @param listOfQuestions the list of questions
	 */
	private void displayResult(List userAnswers, List<Question> listOfQuestions)
	{
		
		System.out.println("\n---------------------------------------------");
		System.out.println("\t Solution to the quiz : ");
		System.out.println("---------------------------------------------");
		
		for(int i = 1; i<= listOfQuestions.size() ; i++)
		{
			Question question = listOfQuestions.get(i-1);
			String userAnswer = (String) userAnswers.get(i-1);
			
			System.out.println("\n***** Q. " + i +" *****\n");
			System.out.println(question.getQuesText());
			if(question instanceof McqQuestion)
			{
				McqQuestion mcqQuestion = (McqQuestion) question;
				McqOptions mcqOptions = mcqQuestion.getMcqOptions();
				List<Answer> listOfAnswers = mcqOptions.getOptions();
				int mcq = 97;
				System.out.println("");
				for(Answer ans : listOfAnswers)
				{
					System.out.println((char)mcq + ". " + ans.getText());
					mcq++;
				}
				
				if("a".equalsIgnoreCase(userAnswer))
				{
					userAnswer = userAnswer + ". "+ listOfAnswers.get(0).getText();
				}	
				if("b".equalsIgnoreCase(userAnswer))
				{
					userAnswer = userAnswer + ". "+ listOfAnswers.get(1).getText();
				}	
				if("c".equalsIgnoreCase(userAnswer))
				{
					userAnswer = userAnswer + ". "+ listOfAnswers.get(2).getText();
				}	
				if("d".equalsIgnoreCase(userAnswer))
				{
					userAnswer = userAnswer + ". "+ listOfAnswers.get(3).getText();
				}	
				
				System.out.println("\nYour Answer : " + userAnswer);
				
				mcq = 97;
				for(Answer ans : listOfAnswers)
				{
					if(ans.getIsCorrect())
						System.out.println("--> Answer : "+(char)mcq + ". " + ans.getText());
					mcq++;
				}
			}
			else if (question instanceof OpenQuestion)
			{
				OpenQuestion openQuestion = (OpenQuestion) question;
				System.out.println("\nYour Answer : " + userAnswer);
				System.out.println("--> Answer : " + openQuestion.getAnswer().getText());
			}
		}
	}
	
	
	/**
	 * Display topic menu.
	 *
	 * @return the topic
	 */
	public Topic displayTopicMenu()
	{
		Scanner scanner = new Scanner(System.in);
		int option;
		System.out.println("\n---------------------------------------------");
		System.out.println("\t Select Topic");
		System.out.println("---------------------------------------------");
		TopicsService topicService = new TopicsService();
		List<Topic> listOfTopics = topicService.getListofTopics();
		
		int i;
		for(i = 1; i<= listOfTopics.size(); i++)
		{
			Topic tpc = listOfTopics.get(i-1);
			System.out.println(i+". "+ tpc.getTopicText());
		}
		System.out.println(i+". EXIT ");
		
		do
		{
			System.out.println("\n Select you Topic from menu : ");
			option = scanner.nextInt();
		}
		while(option < 1 || option > (listOfTopics.size()+1));

		if(option == listOfTopics.size()+1)
			return null;
		
		return listOfTopics.get(option-1);
	}
	
	/**
	 * Display level menu.
	 *
	 * @return the level
	 */
	private int displayLevelMenu()
	{
		Scanner scanner = new Scanner(System.in);
		int option;
		System.out.println("\n---------------------------------------------");
		System.out.println("\t Select difficulty level");
		System.out.println("---------------------------------------------");
		
		System.out.println(" 1. Easy");
		System.out.println(" 2. Average");
		System.out.println(" 3. Difficult");
		System.out.println(" 4. Exit");

		do
		{
			System.out.println("\n Select level from menu: ");
			option = scanner.nextInt();
		}
		while(option > 4 || option < 1);
		
		return option;
	}
	
	/**
	 * Display user preference.
	 *
	 * @return the option
	 */
	private int displayUserPref()
	{
		Scanner scanner = new Scanner(System.in);
		int option;
		System.out.println("\n---------------------------------------------");
		System.out.println("\t Do You want to ");
		System.out.println("---------------------------------------------");
		
		System.out.println(" 1. Start Quiz");
		System.out.println(" 2. Save Quiz as PDF");

		do
		{
			System.out.println("\n Select your preference: ");
			option = scanner.nextInt();
		}
		while(option > 2 || option < 1);
		
		return option;
	}
	
	

}
