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
	
	public static String toString(Date time, boolean showDate, boolean showTime) {
		String string = "";
		if (showTime) {
			String hours = Integer.toString(time.getHours()); 
			if (hours.length() == 1) hours = '0' + hours;
			String minutes = Integer.toString(time.getMinutes()); 
			if (minutes.length() == 1) minutes = '0' + minutes;
			string += hours + ":" + minutes;
		}
		if (showDate) {
			String date = Integer.toString(time.getDate()); 
				if (date.length() == 1) date = '0' + date;
			String month = Integer.toString(time.getMonth() + 1); 
				if (month.length() == 1) month = '0' + month;
			String year = Integer.toString(time.getYear() + 1900);
			string += " " + date + "." + month + "." + year;
		}
		return string;
	}
	
	public static String toString(Date time) {
		boolean today = isToday(time);
		return toString(time, !today, today);
	}
	
}
