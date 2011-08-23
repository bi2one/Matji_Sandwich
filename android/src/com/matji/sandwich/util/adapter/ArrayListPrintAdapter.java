package com.matji.sandwich.util.adapter;

import java.util.ArrayList;

public class ArrayListPrintAdapter extends ArrayList {
    public ArrayListPrintAdapter(ArrayList list) {
	super(list);
    }

    public String toSeparatedString(String separator) {
	StringBuilder builder = new StringBuilder("");

	if (size() == 0) {
	    return "";
	}

	builder.append(get(0).toString());

	for (int i = 1; i < size(); i++) {
	    builder.append(separator);
	    builder.append(get(i));
	}

	return builder.toString();
    }

    public String toCommaString() {
	return toSeparatedString(", ");
    }
}