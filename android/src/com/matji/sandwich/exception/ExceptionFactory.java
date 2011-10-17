package com.matji.sandwich.exception;



public class ExceptionFactory {
    public static final int STATUS_OK = 200;
    public static final int UNKNOWN_ERROR = 0;;
    public static final int ID_NOT_EXIST= 1;
    public static final int PASSWORD_INCORRECT= 2;
    public static final int CLIENT_INFO_INCORRECT = 3;
    public static final int GRANT_CODE_SESSION_EXPIRED = 4;
    public static final int EMAIL_NOT_EXIST = 5;
    public static final int DUPLICATED_NICKNAME = 6;
    public static final int DUPLICATED_EMAIL = 7;
    public static final int INVALID_LENGTH = 8;
    public static final int NOT_VERIFIED_EMAIL = 9;
    public static final int DUPLICATED_USER_ID = 10;  
    public static final int DUPLICATED_STORE_FOOD = 11;
    public static final int INVALID_PARAMETERS_REQUESTED = 12;
    public static final int ALREADY_LIKED_POST = 13;
    public static final int ALREADY_LIKED_STORE = 14;
    public static final int ALREADY_LIKED_STORE_FOOD = 15;
    public static final int NEVER_LIKED_POST = 16;
    public static final int NEVER_LIKED_STORE = 17;
    public static final int NEVER_LIKED_STORE_FOOD = 18;
    public static final int ALREADY_BOOKMARKED_STORE = 19;
    public static final int NEVER_BOOKMARKED_STORE = 20;
    public static final int CANNOT_LIKE_YOUR_POST = 21;
    public static final int NON_AUTHENTICATION = 22;
    public static final int CANNOT_FOLLOW_YOURSELF = 23;
    public static final int ALREADY_FOLLOWING = 24;
    public static final int NEVER_FOLLOWING = 25;
    public static final int INVALID_EMAIL_FORM = 26;

    public static MatjiException create(int code) {
	MatjiException result = null;

	switch(code) {
    case ID_NOT_EXIST:
        result = new InvalidPasswordMatjiException();
        break;
    case PASSWORD_INCORRECT:
        result = new InvalidPasswordMatjiException();
        break;
    case DUPLICATED_STORE_FOOD:
        result = new DuplicatedStoreFoodMatjiException();
        break;
    case DUPLICATED_NICKNAME:
        result = new DuplicatedNicknameMatjiException();
        break;
    case DUPLICATED_EMAIL:
        result = new DuplicatedEmailMatjiException();
        break;
    case NOT_VERIFIED_EMAIL:
        result = new NotVerifiedEmailMatjiException();
        break;
    case INVALID_EMAIL_FORM:
        result = new InvalidEmailFormMatjiException();
	}
		
	return result;
    }
	
}
