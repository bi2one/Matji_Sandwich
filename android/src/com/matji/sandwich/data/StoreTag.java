package com.matji.sandwich.data;

public class StoreTag extends MatjiData{
	private int id;
	private int tag_id;
	private int store_id;
	private int count;
	private int sequence;
	private Store store;
	private Tag tag;
	
	public void setId(int id) {
		this.id = id;
	}
	public int getId() {
		return id;
	}
	public void setTag_id(int tag_id) {
		this.tag_id = tag_id;
	}
	public int getTag_id() {
		return tag_id;
	}
	public void setStore_id(int store_id) {
		this.store_id = store_id;
	}
	public int getStore_id() {
		return store_id;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getCount() {
		return count;
	}
	public void setSequence(int sequence) {
		this.sequence = sequence;
	}
	public int getSequence() {
		return sequence;
	}
	public void setStore(Store store) {
		this.store = store;
	}
	public Store getStore() {
		return store;
	}
	public void setTag(Tag tag) {
		this.tag = tag;
	}
	public Tag getTag() {
		return tag;
	}
	
}
