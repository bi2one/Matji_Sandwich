package com.matji.sandwich.http.request;

import android.content.Context;

import com.matji.sandwich.data.AlarmSetting.AlarmSettingType;
import com.matji.sandwich.http.parser.AlarmParser;
import com.matji.sandwich.http.parser.AlarmSettingParser;

public class AlarmHttpRequest extends HttpRequest {
    
    public AlarmHttpRequest(Context context) {
    	super(context);
    	parser = new AlarmParser(context);
    	controller = "alarms";
    }

    public void actionList(int page, int limit) {
    	parser = new AlarmParser(context);
    	httpMethod = HttpMethod.HTTP_GET;
    	action = "list";
    	
    	getHashtable.clear();
    	getHashtable.put("page", page + "");
    	getHashtable.put("limit", limit + "");
    }
    
    public void actionUpdateAlarmPermit(AlarmSettingType type, boolean bool) {
        parser = new AlarmSettingParser(context);
        httpMethod = HttpMethod.HTTP_GET;
        action = "update_alarm_permit";

        String model = "";
        switch (type) {
        case COMMENT:
            model = "comment";
            break;
        case FOLLOWING:
            model = "following";
            break;
        case LIKEPOST:
            model = "likepost";
            break;
        case MESSAGE:
            model = "message";
            break;
        }
        
        getHashtable.clear();
        getHashtable.put(model, bool + "");
    }
}