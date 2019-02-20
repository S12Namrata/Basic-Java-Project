
package fr.epita.quiz.datamodel;

import java.util.List;

/**
 * The Class Question.
 *
 * @author namrata
 */
public class Question {

	/** The id. */
	private Integer id;
	
	/** The ques text. */
	private String quesText;
	
	/** The type. */
	private String type;
	
	/** The list of topics. */
	private List<Topic> listOfTopicsId;
	
	/** The level. */
	private Integer level;
	
	/**
	 * Gets the list of topics id.
	 *
	 * @return the listOfTopicsId
	 */
	public List<Topic> getListOfTopicsId() {
		return listOfTopicsId;
	}
	
	/**
	 * Sets the list of topics.
	 *
	 * @param listOfTopicsId the listOfTopicsId to set
	 */
	public void setListOfTopicsId(List<Topic> listOfTopicsId) {
		this.listOfTopicsId = listOfTopicsId;
	}
	
	/**
	 * Sets the level.
	 *
	 * @param level the level to set
	 */
	public void setLevel(Integer level) {
		this.level = level;
	}
	
	/**
	 * Gets the level.
	 *
	 * @return the level
	 */
	public Integer getLevel() {
		return level;
	}
	
	/**
	 * Gets the ques text.
	 *
	 * @return the quesText
	 */
	public String getQuesText() {
		return quesText;
	}
	
	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	
	/**
	 * List of topics.
	 *
	 * @return the topic
	 */
	public List<Topic> listOfTopicsId() {
		return listOfTopicsId;
	}
	
	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Integer getId() {
		return id;
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
	 * Sets the ques text.
	 *
	 * @param quesText the quesText to set
	 */
	public void setQuesText(String quesText) {
		this.quesText = quesText;
	}
	
	/**
	 * Sets the type.
	 *
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	/** 
	 * @return string of question object 
	 */
	@Override
	public String toString() {
		return "Question [id=" + id + ", quesText=" + quesText + ", type=" + type + ", listOfTopicsId=" + listOfTopicsId
				+ ", level=" + level + "]";
	}
	
	
}
