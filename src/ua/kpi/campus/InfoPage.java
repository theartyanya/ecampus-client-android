package ua.kpi.campus;

import android.content.Context;
import android.widget.RelativeLayout;

public class InfoPage extends Page {
	
	@Override public String getTitle() {
		return getResources().getString(R.string.info_page_title);
	}
	
	public InfoPage(Context context) {
		super(context);
	}
	
}
