package com.matji.sandwich.http.parser;

import com.matji.sandwich.data.*;
import java.util.ArrayList;

public abstract class MatjiDataParser {
	public abstract ArrayList<MatjiData> getData(String data);
}
