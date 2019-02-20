
package fr.epita.quiz.datamodel;

/**
 * The Class OpenQuestion.
 *
 * @author namrata
 */
public class OpenQuestion extends Question {
	
	/** The answer. */
	private Answer answer;

	/**
	 * Gets the answer.
	 *
	 * @return the answer
	 */
	public Answer getAnswer() {
		return answer;
	}

	/**
	 * Sets the answer.
	 *
	 * @param answer the answer to set
	 */
	public void setAnswer(Answer answer) {
		this.answer = answer;
	}

	@Override
	public String toString() {
		return "OpenQuestion [answer=" + answer + "]";
	}
	
}
