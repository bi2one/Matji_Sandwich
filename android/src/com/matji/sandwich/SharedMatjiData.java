package com.matji.sandwich;

import java.util.Stack;

import android.util.Log;

import com.matji.sandwich.data.MatjiData;

public class SharedMatjiData {
	private static volatile SharedMatjiData sharedMatjiData;
	private static Stack<MatjiData> stack;
	
	private SharedMatjiData() { }
	public static SharedMatjiData getInstance() {
		if (sharedMatjiData == null) {
			synchronized(SharedMatjiData.class) {
				if (sharedMatjiData == null) {
					sharedMatjiData = new SharedMatjiData();
					stack = new Stack<MatjiData>();
				}
			}
		}
		
		return sharedMatjiData;
	}
	
	public void push(MatjiData data) {
		Log.d("Matji", "PUSH" + data.toString());
		stack.push(data);
	}
	
	public MatjiData pop() {
		return stack.pop();
	}
	
	public MatjiData top() {
		MatjiData data = stack.lastElement();
		Log.d("Matji", "TOP" + data.toString());
		

		return stack.lastElement();
	}
}