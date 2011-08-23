package com.matji.sandwich.widget;

import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.util.AttributeSet;
import android.util.Log;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;

import com.matji.sandwich.R;
import com.matji.sandwich.util.MatjiConstants;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.io.File;

public class AlbumView extends LinearLayout {
    private static final int BACKGROUND_REFERENCE = R.color.matji_light_pink;
    private static final int FRAME_WIDTH_REFERENCE = R.dimen.album_view_frame_min_width;
    private static final int FRAME_HEIGHT_REFERENCE = R.dimen.album_view_frame_min_height;
    private static final int PADDING_REFERENCE = R.dimen.album_view_padding;
    private static final int BASIC_ROW = 3;
    private static final int BASIC_COLUMN = 4;
    private Context context;
    private LayoutParams rowParam;
    private LayoutParams frameParam;
    private ArrayList<ArrayList<AlbumImageView>> albumImages;
    private int currentRow;
    private int currentCol;
    private int rowSize;
    private int colSize;
    
    public AlbumView(Context context) {
	super(context);
	init(context);
    }

    public AlbumView(Context context, AttributeSet attrs) {
	super(context, attrs);
	init(context);
    }

    private void init(Context context) {
	this.context = context;
	albumImages = new ArrayList<ArrayList<AlbumImageView>>();
	setPadding(0, (int)MatjiConstants.dimen(PADDING_REFERENCE), 0, 0);
	setSize(3, 4);
	currentRow = 0;
	currentCol = 0;
    }

    public void setSize(int row, int column) {
	rowSize = row;
	colSize = column;
	this.context = context;
	detachAllViewsFromParent();
	albumImages.clear();
	rowParam = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
	frameParam = new LayoutParams((int)MatjiConstants.dimen(FRAME_WIDTH_REFERENCE),
				      (int)MatjiConstants.dimen(FRAME_HEIGHT_REFERENCE));

	setOrientation(VERTICAL);
	setBackgroundColor(MatjiConstants.color(BACKGROUND_REFERENCE));
	
	for (int i = 0; i < row; i++) {
	    LinearLayout rowView = new LinearLayout(context);
	    ArrayList<AlbumImageView> rowImages = new ArrayList<AlbumImageView>();
	    rowView.setGravity(Gravity.CENTER_HORIZONTAL);
	    addView(rowView, rowParam);
	    for (int j = 0; j < column; j++) {
		AlbumImageView frame = new AlbumImageView(context);
		rowImages.add(frame);
		frame.setGravity(Gravity.CENTER);
		rowView.addView(frame, frameParam);
	    }
	    albumImages.add(rowImages);
	}
    }

    public AlbumImageView getImageView(int row, int column) {
	return albumImages.get(row).get(column);
    }

    public AlbumImageView getNextImageView() {
	int targetCol = currentCol++;
	int targetRow = currentRow;
	if (currentCol == colSize) {
	    currentCol = 0;
	    currentRow++;
	}
	return getImageView(targetRow, targetCol);
    }

    public boolean pushImage(File file) {
	if (!isContains(file)) {
	    getNextImageView().setImage(file);
	    return true;
	}
	return false;
    }

    public ArrayList<File> getFiles() {
	ArrayList<File> files = new ArrayList<File>();

	for (int i = 0; i < rowSize; i++) {
	    ArrayList<AlbumImageView> rowImages = albumImages.get(i);
	    for (int j = 0; j < colSize; j++) {
		if (i == currentRow && j == currentCol)
		    return files;
		else {
		    AlbumImageView frame = rowImages.get(j);
		    files.add(frame.getFile());
		}
	    }
	}
	
	return files;
    }

    public boolean isContains(File targetFile) {
	ArrayList<File> files = getFiles();
	
	for (File file : files) {
	    if (isFileNameEquals(file, targetFile))
		return true;
	}
	return false;
    }

    private boolean isFileNameEquals(File file1, File file2) {
	return file1.getAbsolutePath().equals(file2.getAbsolutePath());
    }
}