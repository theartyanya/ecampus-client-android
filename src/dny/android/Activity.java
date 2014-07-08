package dny.android;

import android.content.Intent;
import android.os.Bundle;

public class Activity extends android.app.Activity {
	
	public int getStatusBarHeight() { 
    	int result = 0;
    	int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
    	if (resourceId > 0) {
        	result = getResources().getDimensionPixelSize(resourceId);
    	} 
    	return result;
	}
	public int getNavBarHeight() {
		int result = 0;
    	int resourceId = getResources().getIdentifier("navigation_bar_height", "dimen", "android");
    	if (resourceId > 0) {
        	result = getResources().getDimensionPixelSize(resourceId);
    	} 
    	return result;
	}
	
	private static volatile Object globalArg1, globalArg2;
	protected Object arg1, arg2;
	
	public void open(Class aClass, Object arg1, Object arg2) {
		if (globalArg1 != null || globalArg2 != null) return;
		globalArg1 = arg1; globalArg2 = arg2;
		startActivity(new Intent(this, aClass));
	}
	public void open(Class aClass, Object arg1) {
		open(aClass, arg1, null);
	}
	public void open(Class aClass) {
		open(aClass, null, null);
	}

	@Override protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		arg1 = globalArg1; arg2 = globalArg2;
		globalArg1 = null; globalArg2 = null;
	}
	
}
