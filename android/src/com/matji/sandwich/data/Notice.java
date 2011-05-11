package com.matji.sandwich.data;

public class Notice extends MatjiData{
	private int id;
	private String subject;
	private String content;
	private String target;
	private String start_date;
	private String end_date;
	private int sequence;
	
	public void setId(int id) {
		this.id = id;
	}
	public int getId() {
		return id;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getSubject() {
		return subject;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getContent() {
		return content;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public String getTarget() {
		return target;
	}
	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}
	public String getStart_date() {
		return start_date;
	}
	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}
	public String getEnd_date() {
		return end_date;
	}
	public void setSequence(int sequence) {
		this.sequence = sequence;
	}
	public int getSequence() {
		return sequence;
	}

}
