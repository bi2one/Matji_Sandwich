package com.matji.sandwich.data;


public class AgreeTerm extends MatjiData {
    private int id;
    private int user_id;
    private boolean agree;
    private String created_at;
    private String updated_at;
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getId() {
        return id;
    }
    
    public void setUserId(int user_id) {
        this.user_id = user_id;
    }
    
    public int getUserId() {
        return user_id;
    }

    public void setAgree(boolean agree) {
        this.agree = agree;
    }

    public boolean isAgree() {
        return agree;
    }

    public void setCreatedAt(String created_at) {
        this.created_at = created_at;
    }

    public String getCreatedAt() {
        return created_at;
    }

    public void setUpdatedAt(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getUpdatedAt() {
        return updated_at;
    }
}