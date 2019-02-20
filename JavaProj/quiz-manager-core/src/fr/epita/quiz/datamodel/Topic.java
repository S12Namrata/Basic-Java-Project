
package fr.epita.quiz.datamodel;

/**
 * The Class Topic.
 *
 * @author namrata
 */
public class Topic {
	
	/** The id. */
	private Integer id;
	
	/** The topic text. */
	private String topicText;
	
	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	
	/**
	 * Gets the topic text.
	 *
	 * @return the topicText
	 */
	public String getTopicText() {
		return topicText;
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
	 * Sets the topic text.
	 *
	 * @param topicText the topicText to set
	 */
	public void setTopicText(String topicText) {
		this.topicText = topicText;
	}

	@Override
	public String toString() {
		return "Topic [id=" + id + ", topicText=" + topicText + "]";
	}
}
