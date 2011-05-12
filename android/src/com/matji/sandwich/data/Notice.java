package com.matji.sandwich.data;

public class Notice extends MatjiData{
	private String start_date;
	private String created_at;
	private String sequence;
	private String updated_at;
	private String subject;
	private String id;
	private String content;
	private String target;
	private String end_date;
	
	public void setStartDate(String start_date) {
		this.start_date = start_date;
	}
	
	public String getStartDate() {
		return start_date;
	}
	
	public void setCreatedAt(String created_at) {
		this.created_at = created_at;
	}
	
	public String getCreatedAt() {
		return created_at;
	}
	
	public void setSequence(String sequence) {
		this.sequence = sequence;
	}
	
	public String getSequence() {
		return sequence;
	}
	
	public void setUpdatedAt(String updated_at) {
		this.updated_at = updated_at;
	}
	
	public String getUpdatedAt() {
		return updated_at;
	}
	
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	public String getSubject() {
		return subject;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getId() {
		return id;
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
	
	public void setEndDate(String end_date) {
		this.end_date = end_date;
	}
	
	public String getEndDate() {
		return end_date;
	}
}