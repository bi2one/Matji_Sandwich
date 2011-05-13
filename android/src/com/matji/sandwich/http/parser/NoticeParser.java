package com.matji.sandwich.http.parser;

import java.util.ArrayList;

import android.util.Log;

import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.Notice;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.exception.JSONMatjiException;
import com.matji.sandwich.json.MatjiJSONArray;
import com.matji.sandwich.json.MatjiJSONObject;

import org.json.JSONException;

public class NoticeParser extends MatjiDataParser{
	public ArrayList<MatjiData> getRawData(String data) throws MatjiException {
		ArrayList<MatjiData> noticeList = new ArrayList<MatjiData>();
		MatjiJSONArray jsonArray;
		try {
			jsonArray = new MatjiJSONArray(data);
			MatjiJSONObject element;
			for (int i = 0 ; i < jsonArray.length(); i++) {
				element = jsonArray.getMatjiJSONObject(i);
				Notice notice = new Notice();
				notice.setStartDate(element.getString("start_date"));
				notice.setCreatedAt(element.getString("created_at"));
				notice.setSequence(element.getString("sequence"));
				notice.setUpdatedAt(element.getString("updated_at"));
				notice.setSubject(element.getString("subject"));
				notice.setId(element.getString("id"));
				notice.setContent(element.getString("content"));
				notice.setTarget(element.getString("target"));
				notice.setEndDate(element.getString("end_date"));
				noticeList.add(notice);
			}
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Log.d("Matji", "NoticeParser: Parsing success");

		return noticeList;
	}

	public ArrayList<MatjiData> getData(String data) throws MatjiException {
		String validData = validateData(data);
		return getRawData(validData);
	}
}