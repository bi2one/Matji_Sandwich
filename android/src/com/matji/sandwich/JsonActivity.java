//이 곳은 JSON 테스트를 하는곳입니다.

package com.matji.sandwich;

import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.Food;
import com.matji.sandwich.http.parser.FoodParser;
import com.matji.sandwich.exception.MatjiException;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class JsonActivity extends Activity{
    String string = "{\"result\":[{\"like_count\":1,\"blind\":false,\"id\":23,\"store_id\":12296,\"like\":false,\"user_id\":100044410,\"food_id\":23,\"food\":{\"like_count\":0,\"name\":\"TwoChai\",\"sequence\":-23,\"created_at\":\"2011-05-06T17:09:57Z\",\"updated_at\":\"2011-05-06T17:09:57Z\",\"id\":23}},{\"like_count\":1,\"blind\":false,\"id\":4,\"store_id\":12296,\"like\":false,\"user_id\":100000170,\"food_id\":5,\"food\":{\"like_count\":0,\"name\":\"WanChai\",\"sequence\":-5,\"created_at\":\"2011-04-20T03:32:06Z\",\"updated_at\":\"2011-04-20T03:32:06Z\",\"id\":5}}],\"code\":200}";
    ArrayList<MatjiData> datas;
	
    public void onCreate(Bundle savedInstanceState){
	super.onCreate(savedInstanceState);
	setContentView(R.layout.main);
	FoodParser parser = new FoodParser();
	try {
	    datas = parser.getData(string);
	} catch (MatjiException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	//		Log.d(\"saf\",((AttachFile)datas.get(0)).getFilename()); //attach_file
	//		Log.d(\"saf\",\"\"+((Alarm)datas.get(0)).getSent_user_id()); //alarm
	//		Log.d(\"saf\",\"\"+((Comment)datas.get(0)).getId()); //comment => post_id
	//		Log.d(\"saf\",\"\"+((Following)datas.get(0)).getFollowing_user_id()); //following
	Log.d("saf",""+((Food)datas.get(0)).getName()); //food
	//		Log.d(\"saf\",\"\"+((Message)datas.get(0)).getId()); //message
	//		Log.d(\"saf\",\"\"+((Notice)datas.get(0)).getId()); //notice 
	//		Log.d(\"saf\",((Post)datas.get(0)).getPost()); //post
	//		Log.d(\"asdfasdf\",((Store)datas.get(0)).getAddress()); //store
	//		Log.d(\"saf\",\"\"+((Tag)datas.get(0)).getId()); //tag
	//		Log.d(\"saf\",User)datas.get(0)).getUserid()); //user
    }
		
}
