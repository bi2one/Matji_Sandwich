package com.matji.sandwich.http.request;

import java.util.ArrayList;

import android.content.Context;

import com.matji.sandwich.data.Alarm;
import com.matji.sandwich.data.AlarmSetting.AlarmSettingType;
import com.matji.sandwich.data.MatjiData;
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
    
    public void actionRead(ArrayList<MatjiData> alarms) {
    	parser = new AlarmParser(context);
    	httpMethod = HttpMethod.HTTP_POST;
    	action = "read";
    	
    	String ids = "";
    	for (MatjiData alarm : alarms) {
    		ids += ((Alarm) alarm).getId() + ",";
    	}
    	
    	postHashtable.clear();
    	postHashtable.put("alarm_id", ids);
    }
}