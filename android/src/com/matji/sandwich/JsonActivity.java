package com.matji.sandwich;

import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.Alarm;
import com.matji.sandwich.http.parser.AlarmParser;
import com.matji.sandwich.exception.MatjiException;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class JsonActivity extends Activity{
	String string = "{\"result\":[{\"sent_user\":{\"sequence\":-100000002,\"created_at\":\"2010-04-04T15:56:42Z\",\"intro\":\"\\uc800\\ub294 \\uc640\\uc774\\uc950\\ub9c8\\uc2a4\\ud130\\uc785\\ub2c8\\ub2e4\",\"title\":\"\\ub179\\ucc28\\uccad\\ub144\\uc758 \\uc810\\uc2ec\\uc2dd\\uc0ac\",\"updated_at\":\"2011-04-26T19:21:19Z\",\"nick\":\"\\ub179\\ucc28\\uccad\\ub144\",\"following_count\":40,\"follower_count\":40,\"tag_count\":33,\"id\":100000002,\"userid\":\"ygmaster\",\"followed\":false,\"post_count\":212,\"following\":false,\"store_count\":1},\"id\":1,\"received_user_id\":100000170,\"received_user\":{\"sequence\":-100000170,\"created_at\":\"2010-05-19T01:25:45Z\",\"intro\":\"\\uc548\\uc591 \\uc8fc\\ubcc0 \\ub9db\\uc9d1 \\uc815\\ubcf4\\ub97c.. \\uadf8\\uac83\\uc774 \\uc54c\\uace0 \\uc2f6\\ub2e4\",\"title\":\"\\uc548\\uc591\\ub9db\\uc9d1\\uae30\\ud589 \\u3132\\u3132\",\"updated_at\":\"2011-04-20T03:27:16Z\",\"nick\":\"BrandNew\",\"following_count\":13,\"follower_count\":13,\"tag_count\":80,\"id\":100000170,\"userid\":\"dalong10\",\"followed\":false,\"post_count\":104,\"following\":false,\"store_count\":7},\"sent_user_id\":100000002,\"alarm_type\":\"FriendRequest\"}],\"code\":200}";
	ArrayList<MatjiData> datas;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		AlarmParser parser = new AlarmParser();
		try {
			datas = parser.getData(string);
		} catch (MatjiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		Log.d("saf",((AttachFile)datas.get(0)).getFilename()); //attach_file
		Log.d("saf",""+((Alarm)datas.get(0)).getSent_user_id()); //alarm
//		Log.d("saf",""+((Comment)datas.get(0)).getId()); //comment => post_id
//		Log.d("saf",""+((Following)datas.get(0)).getFollowing_user_id()); //following
//		Log.d("saf",""+((Food)datas.get(0)).getLike_count()); //food => store_id,배열 안의 배열처리문제
//		Log.d("saf",""+((Message)datas.get(0)).getId()); //message
//		Log.d("saf",""+((Notice)datas.get(0)).getId()); //notice 
//		Log.d("saf",((Post)datas.get(0)).getPost()); //post
//		Log.d("asdfasdf",((Store)datas.get(0)).getAddress()); //store
//		Log.d("saf",""+((Tag)datas.get(0)).getId()); //tag
//		Log.d("saf",User)datas.get(0)).getUserid()); //user
	}
		
}
