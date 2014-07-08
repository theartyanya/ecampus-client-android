package dny.time;

import java.util.Date;
import java.util.Calendar;

public class Time {

	final static Calendar calendar = Calendar.getInstance();
	
	public static boolean isToday(Date time) {
		Date now = calendar.getTime();
		return 
			time.getYear() == now.getYear() &&
			time.getMonth() == now.getMonth() &&
			time.getDate() == now.getDate();
	}
	
	public static String toString(Date time, boolean today) {
		String string = time.getHours() + ":" + time.getMinutes();
		if (!today) {
			string += " " + time.getDate() + "." + time.getMonth() + "." + time.getYear();
		}
		return string;
	}
	
	public static String toString(Date time) {
		return toString(time, isToday(time));
	}
	
}
