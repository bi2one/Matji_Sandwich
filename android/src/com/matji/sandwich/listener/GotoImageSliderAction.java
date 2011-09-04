package com.matji.sandwich.listener;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

import com.matji.sandwich.ImageSliderActivity;
import com.matji.sandwich.data.AttachFile;

/**
 * 클릭 시 {@link ImageSliderActivity}로 이동하도록 하는 클릭 리스너.
 * 
 * @author mozziluv
 *
 */
public class GotoImageSliderAction implements OnClickListener {
	
	private Context context;
	private ArrayList<AttachFile> filesList;

	/**
	 * 기본 생성자
	 * 
	 * @param context
	 * @param files {@link ImageSliderActivity}에서 사용할 {@link AttachFile}의 {@link ArrayList}
	 */
	public GotoImageSliderAction(Context context, ArrayList<AttachFile> files) {
		this.context = context;
		this.filesList = files;
	}

	/**
	 * 해당 리스너의 {@link AttachFile} 리스트를 파라미터로 전달받은 리스트로 변경한다.
	 * 
	 * @param files 변경 할 {@link AttachFile} 리스트
	 */
	public void setAttachFiles(ArrayList<AttachFile> files) {
		this.filesList = files;
	}
	
	/**
	 * 현재 저장 된 {@link AttachFile} 리스트의 {@link ImageSliderActivity}로 이동한다.
	 */
	@Override
	public void onClick(View v) {
		// 클릭 되는 뷰의 tag엔 position 값이 들어 있어야 한다.
		int position = Integer.parseInt((String) v.getTag());
		callImageViewer(position);
	}

	/**
	 * 해당하는 position의 이미지를 가장 먼저 보여주는 {@link ImageSliderActivity}로 이동한다.
	 * 
	 * @param position 가장 처음 보여 줄 Image의 files 내의 포지션 position.
	 */
	public void callImageViewer(int position){
		AttachFile[] attachFiles = filesList.toArray(new AttachFile[]{});

		Intent viewerIntent = new Intent(context, ImageSliderActivity.class);
		viewerIntent.putExtra(ImageSliderActivity.ATTACH_FILES, attachFiles);
		viewerIntent.putExtra(ImageSliderActivity.POSITION, position);
		context.startActivity(viewerIntent);
	}
}