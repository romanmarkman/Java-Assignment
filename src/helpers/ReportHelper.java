package helpers;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public final class ReportHelper {
	
	public static String getCurrentDate() {
		
		String timeStamp = new SimpleDateFormat("MM / dd / yyyy").format(Calendar.getInstance().getTime());
		return timeStamp;
	}
}
