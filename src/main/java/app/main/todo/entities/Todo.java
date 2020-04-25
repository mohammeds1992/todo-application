package app.main.todo.entities;

import app.main.todo.csv.utils.CSVReaderUtil;

public class Todo {

	private Integer id;

	private String text;

	private boolean completed;

	private String due;

	private Integer priority;

	private String category;

	public Todo(String text, String due, Integer priority, String category, boolean completed, String csvFilePath) {
				   
		this.id = CSVReaderUtil.getNextId(csvFilePath);
		this.text = text;
		this.due = due == null ? "" : due;
		this.priority = priority;
		this.category = category == null ? "" : category;
		this.completed = completed;

	}

	public Todo(Integer id, String text, boolean completed, String due, Integer priority, String category) {
		super();
		this.id = id;
		this.text = text;
		this.completed = completed;
		this.due = due;
		this.priority = priority;
		this.category = category;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

	public String getDue() {
		return due;
	}

	public void setDue(String due) {
		this.due = due;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	@Override
	public String toString() {
		return id + "," + text + "," + completed + "," + due + "," + priority + "," + category;
	}
}