package dny.android.util;

import android.view.View.OnClickListener;
import android.view.*;

public class Listener implements 
	OnClickListener
{

	private final Runnable runnable;
	
	@Override public void onClick(View p1) {
		if (runnable == null) return;
		runnable.run();
	}
	
	public Listener(Runnable runnable) {
		this.runnable = runnable;
	}
	
}
