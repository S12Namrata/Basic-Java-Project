
package fr.epita.quiz.datamodel;

import java.util.List;

/**
 * The Class McqOptions.
 *
 * @author namrata
 */
public class McqOptions {
	
	/** The options. */
	List<Answer> options;

	/**
	 * Gets the options.
	 *
	 * @return the options
	 */
	public List<Answer> getOptions() {
		return options;
	}

	/**
	 * Sets the options.
	 *
	 * @param options the options to set
	 */
	public void setOptions(List<Answer> options) {
		this.options = options;
	}

	@Override
	public String toString() {
		return "McqOptions [options=" + options + "]";
	}
	
	

}
