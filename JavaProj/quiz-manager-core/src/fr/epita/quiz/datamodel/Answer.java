
package fr.epita.quiz.datamodel;

/**
 * The Class Answer.
 *
 * @author namrata
 */
public class Answer {

	/** The id. */
	private	Integer id;
	
	/** The text. */
	private String text;
	
	/** The is correct. */
	private Boolean isCorrect;
	
	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * Gets the text.
	 *
	 * @return the text
	 */
	public String getText() {
		return text;
	}
	
	/**
	 * Gets the checks if is correct.
	 *
	 * @return the isCorrect
	 */
	public Boolean getIsCorrect() {
		return isCorrect;
	}
	
	/**
	 * Sets the id.
	 *
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * Sets the text.
	 *
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}
	
	/**
	 * Sets the checks if is correct.
	 *
	 * @param isCorrect the isCorrect to set
	 */
	public void setIsCorrect(Boolean isCorrect) {
		this.isCorrect = isCorrect;
	}

	@Override
	public String toString() {
		return "Answer [id=" + id + ", text=" + text + ", isCorrect=" + isCorrect + "]";
	}
	
	
}
