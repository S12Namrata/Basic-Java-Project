
package fr.epita.quiz.datamodel;

/**
 * The Class McqQuestion.
 *
 * @author namrata
 */
public class McqQuestion extends Question {
	
	/** The mcq options. */
	private McqOptions mcqOptions;

	/**
	 * Gets the mcq options.
	 *
	 * @return the mcqOptions
	 */
	public McqOptions getMcqOptions() {
		return mcqOptions;
	}

	/**
	 * Sets the mcq options.
	 *
	 * @param mcqOptions the mcqOptions to set
	 */
	public void setMcqOptions(McqOptions mcqOptions) {
		this.mcqOptions = mcqOptions;
	}


	@Override
	public String toString() {
		return "McqQuestion [mcqOptions=" + mcqOptions + "]";
	}
}
