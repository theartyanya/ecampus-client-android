package ua.kpi.campus;

import android.content.Context;
import android.widget.RelativeLayout;

public abstract class Page extends RelativeLayout {
	
	public abstract String getTitle();
	
	public Page(Context context) {
		super(context);
		setLayoutParams(new LayoutParams(
			LayoutParams.MATCH_PARENT,
			LayoutParams.MATCH_PARENT
		));
	}
	
}
