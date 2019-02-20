package fr.epita.quiz.utility;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import fr.epita.quiz.datamodel.Answer;
import fr.epita.quiz.datamodel.McqOptions;
import fr.epita.quiz.datamodel.McqQuestion;
import fr.epita.quiz.datamodel.Question;

/**
 * The Class PDFService.
 */
public class PDFService {
	
	/**
	 * Save as pdf.
	 *
	 * @param listOfQuestions the list of questions
	 * @param topic the topic
	 */
	public void saveAsPdf(List<Question> listOfQuestions, String topic)
	{
		Document document = new Document();
		try
		{
			String fileName = Constants.QUIZ + "_" + topic+Constants.PDF_EXTENSION;
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(fileName));
			document.open();

			Font bodyFont = FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL);
			Paragraph title = new Paragraph("QUIZ", FontFactory.getFont(FontFactory.HELVETICA, 18, Font.BOLD));
			title.setSpacingAfter(20.0f);
			Chapter chapter = new Chapter(title, 1);
			chapter.setNumberDepth(0);

			document.add(chapter);
			writeToPdf(listOfQuestions, document, bodyFont);
			
			document.close();
			writer.close();
		}
		catch (DocumentException e)
		{
			e.printStackTrace();
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		

	}

	/**
	 * Write to pdf.
	 *
	 * @param listOfQuestions the list of questions
	 * @param document the document
	 * @param bodyFont the body font
	 * @throws DocumentException the document exception
	 */
	private void writeToPdf(List<Question> listOfQuestions, Document document, Font bodyFont) throws DocumentException 
	{
		int i=1,size = listOfQuestions.size();
		if(size > 0)
		{
			for (Question question : listOfQuestions) 
			{
				Paragraph quesPara = new Paragraph(i++ +") "+question.getQuesText(), bodyFont);
				quesPara.setSpacingBefore(10.0f);
				document.add(new Paragraph(quesPara));
				if(question instanceof McqQuestion)
				{
					McqQuestion mcqQuestion = (McqQuestion) question;
					McqOptions mcqOptions = mcqQuestion.getMcqOptions();
					List<Answer> listOfAnswers = mcqOptions.getOptions();

					com.itextpdf.text.List ansList = new com.itextpdf.text.List(false,true);
					ansList.setLowercase(true);

					for(Answer ans : listOfAnswers)
					{

						ansList.add(new ListItem(ans.getText(),bodyFont));

					}
					document.add(ansList);

				}
				Paragraph userAnswer = new Paragraph("Answer :  __________________",bodyFont);
				userAnswer.setSpacingBefore(5.0f);
				document.add(userAnswer);

			}
		}
		else
			System.out.println("Please choose another topic and different level.");

	}


}
