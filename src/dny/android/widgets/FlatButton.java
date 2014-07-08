package dny.android.widgets;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.widget.ImageButton;

import dny.android.util.Listener;

public class FlatButton extends ImageButton {
	
	private static int exitFadeDuration = 125;
	private static int pressedColor = 0xffffffff;
	private static int notSoPressedColor = 0x88ffffff;
	
	public FlatButton(Bitmap bitmap, Runnable clickAction, Context context) {
		super(context);
		setImageBitmap(bitmap);
		setOnClickListener(new Listener(clickAction));
		
		backgroundSetting: {
			StateListDrawable background = new StateListDrawable();
			background.setExitFadeDuration(exitFadeDuration);
			background.addState(
				new int[] {android.R.attr.state_pressed},
				new ColorDrawable(pressedColor)
			);
			background.addState(
				new int[] {android.R.attr.state_focused},
				new ColorDrawable(notSoPressedColor)
			);
			setBackgroundDrawable(background);
		}
		
	}
	
}
