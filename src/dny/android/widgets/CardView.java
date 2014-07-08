package dny.android.widgets;

import android.content.Context;
import android.widget.RelativeLayout;
import android.view.View;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;

public class CardView extends RelativeLayout {
	
	private final int padding = (int)(ua.kpi.campus.Campus.density * 8);
	private final int shadowSize = (int)(ua.kpi.campus.Campus.density * 2);

	private final RelativeLayout body;
	@Override public void addView(View child) {
		body.addView(child);
	}
	@Override public void setBackgroundColor(int color) {
		body.setBackgroundColor(color);
	}
	@Override public void setBackgroundDrawable(Drawable background) {
		body.setBackgroundDrawable(background);
	}
	@Override public void setBackgroundResource(int resid) {
		body.setBackgroundResource(resid);
	}
	@Override public void setBackground(Drawable background) {
		body.setBackground(background);
	}
	
	public CardView(Context context) {
		super(context);
		
		frameSetting: {
			
			final RelativeLayout frame = new RelativeLayout(context);
			frame.setLayoutParams(new LayoutParams(
				LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT
			));
			frame.setPadding(shadowSize, shadowSize, shadowSize, shadowSize);
			
			shadowSetting: {
				GradientDrawable shadow = new GradientDrawable();
				shadow.setCornerRadius(shadowSize);
				shadow.setColor(0x33000000);
				frame.setBackground(shadow);
			}
			
			bodySetting: {
				body = new RelativeLayout(context);
				body.setLayoutParams(new LayoutParams(
					LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT
				));
				frame.addView(body);
			}
			
			super.addView(frame);
			
		}
		
		setPadding(padding, padding, padding, padding);
		
	}
	
}
