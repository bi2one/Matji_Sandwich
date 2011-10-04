package com.matji.sandwich.widget;

import android.view.Gravity;
import android.view.View;
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
import java.util.HashMap;
import java.io.File;

public class AlbumView extends LinearLayout implements View.OnClickListener {
    private static final int BACKGROUND_REFERENCE = R.color.album_view_bg;
    private static final int FRAME_WIDTH_REFERENCE = R.dimen.album_view_frame_width;
    private static final int FRAME_HEIGHT_REFERENCE = R.dimen.album_view_frame_height;
    private static final int PADDING_REFERENCE = R.dimen.album_view_padding;
    private static final int FRAME_MARGIN_RIGHT_REFERENCE = R.dimen.album_view_frame_marginRight;
    private static final int BASIC_ROW = 2;
    private static final int BASIC_COLUMN = 4;
    private Context context;
    private LayoutParams rowParam;
    private LayoutParams frameParam;
    private LayoutParams frameParamLast;
    private ArrayList<ArrayList<AlbumImageView>> albumImages;
    private HashMap<File, Bitmap> thumbnailPool;
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
	thumbnailPool = new HashMap<File, Bitmap>();
	setPadding(0, (int)MatjiConstants.dimen(PADDING_REFERENCE), 0, 0);

	currentRow = 0;
	currentCol = 0;
	rowParam = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
	frameParam = new LayoutParams((int)MatjiConstants.dimen(FRAME_WIDTH_REFERENCE),
				      (int)MatjiConstants.dimen(FRAME_HEIGHT_REFERENCE));
	frameParam.setMargins(0, 0, (int)MatjiConstants.dimen(FRAME_MARGIN_RIGHT_REFERENCE), 0);
	
	frameParamLast = new LayoutParams((int)MatjiConstants.dimen(FRAME_WIDTH_REFERENCE),
					  (int)MatjiConstants.dimen(FRAME_HEIGHT_REFERENCE));
	frameParamLast.setMargins(0, 0, 0, 0);
	
	setSize(BASIC_ROW, BASIC_COLUMN);
    }

    public void setSize(int row, int column) {
	rowSize = row;
	colSize = column;
	this.context = context;
	detachAllViewsFromParent();
	albumImages.clear();
	
	setOrientation(VERTICAL);
	setBackgroundColor(MatjiConstants.color(BACKGROUND_REFERENCE));
	
	for (int i = 0; i < row; i++) {
	    LinearLayout rowView = new LinearLayout(context);
	    ArrayList<AlbumImageView> rowImages = new ArrayList<AlbumImageView>();
	    rowView.setPadding((int)MatjiConstants.dimen(PADDING_REFERENCE), 0,
			       (int)MatjiConstants.dimen(PADDING_REFERENCE),
			       (int)MatjiConstants.dimen(PADDING_REFERENCE));
	    rowView.setGravity(Gravity.CENTER_HORIZONTAL);
	    addView(rowView, rowParam);
	    for (int j = 0; j < column; j++) {
		AlbumImageView frame = new AlbumImageView(context);
		frame.setOnClickListener(this);
		frame.setTag(new IndexTuple(i, j));
		
		rowImages.add(frame);
		if (j != column - 1) {
		    rowView.addView(frame, frameParam);
		} else {
		    rowView.addView(frame, frameParamLast);
		}
	    }
	    albumImages.add(rowImages);
	}
    }

    public void removeImage(int row, int column) {
	ArrayList<AlbumImageView> images = albumImages.get(row);
	AlbumImageView image = images.get(column);
	
	if (!image.isEmpty()) {
	    image.removeImage();
	    notifyDataSetChanged();
	}
    }

    public void notifyDataSetChanged() {
	ArrayList<File> files = getFiles();
	int fileIndex = 0;
	AlbumImageView image;
	
	for (int i = 0; i < rowSize; i++) {
	    for (int j = 0; j < colSize; j++) {
		image = albumImages.get(i).get(j);
		if (fileIndex < files.size()) {
		    File file = files.get(fileIndex++);
		    image.setImage(file, thumbnailPool.get(file));
		} else {
		    image.removeImage();
		    break;
		}
	    }
	}

	currentRow = files.size() / colSize;
	currentCol = files.size() % colSize;
    }

    public boolean isFull() {
	return currentRow == rowSize;
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
	    AlbumImageView imageView = getNextImageView();
	    imageView.setImage(file);
	    thumbnailPool.put(file, imageView.getThumbnail());
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
		    if (!frame.isEmpty())
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

    public void onClick(View v) {
	IndexTuple tuple = (IndexTuple)v.getTag();
	removeImage(tuple.head(), tuple.tail());
    }

    private class IndexTuple {
	private int i;
	private int j;
	
	public IndexTuple(int i, int j) {
	    this.i = i;
	    this.j = j;
	}

	public int head() {
	    return i;
	}

	public int tail() {
	    return j;
	}
    }
}