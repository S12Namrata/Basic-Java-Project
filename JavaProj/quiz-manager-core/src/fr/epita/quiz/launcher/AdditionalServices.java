package fr.epita.quiz.launcher;

import java.util.Scanner;

public class AdditionalServices {
	
	
public void displayMainMenu()
{

	Scanner scanner = new Scanner(System.in);
	int option;
	QuizMaker quizMaker = new QuizMaker();

	do
	{
		System.out.println("\n---------------------------------------------");
		System.out.println("\t********* Menu *********");
		System.out.println("---------------------------------------------");
		
		System.out.println(" 1. * Take Quiz *");
		System.out.println(" 2. Make Quiz");
		System.out.println(" 3. Update Question");
		System.out.println(" 4. Delete Question");
		System.out.println(" 5. Exit Application");
		System.out.println("\n Select level from menu: ");
		option = scanner.nextInt();
		scanner.reset();
		
		switch(option)
		{
			case 1: QuizLauncher quizLancher = new QuizLauncher();
					quizLancher.startQuiz();
			break;	
			case 2: quizMaker.insertQuestion();
			break;
			case 3: quizMaker.updateQuestion();
			break;
			case 4: quizMaker.deleteQuestion();
			break;
			default: 
			break;
			
		}
	}
	while(option != 5);
	
}

}
