package dny.android.util;

import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.text.*;

public class ListenerAdapter implements 
	OnClickListener,
	TextWatcher
{

	private final Runnable runnable;
	
	@Override public void onClick(View p1) {
		if (runnable == null) return;
		runnable.run();
	}

	@Override public void beforeTextChanged(CharSequence p1, int p2, int p3, int p4) {
		
	}

	@Override public void onTextChanged(CharSequence p1, int p2, int p3, int p4) {
		if (runnable == null) return;
		runnable.run();
	}

	@Override public void afterTextChanged(Editable p1) {
		
	}

	public ListenerAdapter(Runnable runnable) {
		this.runnable = runnable;
	}
	
}
