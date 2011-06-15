package com.matji.sandwich;

import android.os.Bundle;
<<<<<<< HEAD
=======
import android.view.View;
import android.widget.Button;

>>>>>>> e71624aacaf456a4885c664beb0095e175f3625d
import com.matji.sandwich.base.BaseMapActivity;

public class MapToolActivity extends BaseMapActivity {
	@Override
	protected void onCreate(Bundle icicle) {
		// TODO Auto-generated method stub
		super.onCreate(icicle);
		setContentView(R.layout.main);
	}

	protected boolean isRouteDisplayed() {
		return true;
	}
	
	@Override
	protected String titleBarText() {
		return "MapToolActivity";
	}
	
	@Override
	protected boolean setTitleBarButton(Button button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void onTitleBarItemClicked(View view) {
		// TODO Auto-generated method stub
		
	}
}
