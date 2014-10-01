package logger;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;

public class DataLogger {
	private static boolean logging = false;
	private static FileWriter fw;
	
	// call DataLogger.startLogging("INS") to generate a file
	// filename: INS_Log_<long value for time>
	// then every time data should be logged
	// save the data as the corresponding (INS/VINS) DataObject
	// then call DataLogger.logData(object)
	
	// once done logging, flush and close
	// ^ subject to change, could be flushing every time data is logged
	public static void startLogging(String prefix) {
		try {
			if (fw == null)
				fw = new FileWriter(prefix + "_Log_" + Calendar.getInstance().getTimeInMillis());
		} catch (IOException e) {

			e.printStackTrace();
			return;
		}
		
		logging = true;
	}
	
	public static void logData(DataObject data) {
		try {
			fw.append(Calendar.getInstance().getTimeInMillis() + " " + data);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void stopLogging() {
		if (!logging)
			return;
		
		try {
			fw.flush();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		fw = null;
	}
	
}
