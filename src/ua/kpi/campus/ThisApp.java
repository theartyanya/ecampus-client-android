package ua.kpi.campus;

import android.app.Application;
import android.os.Handler;
import android.widget.Toast;

public class ThisApp extends Application {
	
	private static ThisApp instance;
	
	public static float density;
	
	private static Handler handler = new Handler();
	public static void showToast(final String s) {
		handler.post(new Runnable() {@Override public void run() {
			Toast.makeText(instance, s, Toast.LENGTH_LONG).show();
		}});
	}
	
	@Override public void onCreate() {
		super.onCreate();
		instance = this;
		density = getResources().getDisplayMetrics().density;
	}
	
}
