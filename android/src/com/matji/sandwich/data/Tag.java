package com.matji.sandwich.data;

public class Tag extends MatjiData {
	protected int id;
	protected int tag_id;
	protected String created_at;
	protected String updated_at;
	protected int count;	
	protected SimpleTag tag;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getTagId() {
		return tag_id;
	}
	
	public void setTagId(int tag_id) {
		this.tag_id = tag_id;
	}
	
	public String getCreatedAt() {
		return created_at;
	}
	
	public void setCreatedAt(String created_at) {
		this.created_at = created_at;
	}
	
	public String getUpdatedAt() {
		return updated_at;
	}
	
	public void setUpdatedAt(String updated_at) {
		this.updated_at = updated_at;
	}
	
	public int getCount() {
		return count;
	}
	
	public void setCount(int count) {
		this.count = count;
	}
	
	public SimpleTag getTag() {
		return tag;
	}
	
	public void setTag(SimpleTag tag) {
		this.tag = tag;
	}
}
