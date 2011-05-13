package com.matji.sandwich.data;

public class StoreTag extends MatjiData{
	private int id;
	private int tag_id;
	private int store_id;
	private int count;
	private String create_at;
	private String updated_at;
	private Tag tag;
	private Store store;
	public void setId(int id) {
		this.id = id;
	}
	public int getId() {
		return id;
	}
	public void setTagId(int tag_id) {
		this.tag_id = tag_id;
	}
	public int getTagId() {
		return tag_id;
	}
	public void setStoreId(int store_id) {
		this.store_id = store_id;
	}
	public int getStoreId() {
		return store_id;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getCount() {
		return count;
	}
	public void setCreateAt(String create_at) {
		this.create_at = create_at;
	}
	public String getCreateAt() {
		return create_at;
	}
	public void setUpdatedAt(String updated_at) {
		this.updated_at = updated_at;
	}
	public String getUpdatedAt() {
		return updated_at;
	}
	public void setTag(Tag tag) {
		this.tag = tag;
	}
	public Tag getTag() {
		return tag;
	}
	public void setStore(Store store) {
		this.store = store;
	}
	public Store getStore() {
		return store;
	}

}
