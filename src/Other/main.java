package Other;

import Other.Database;
import Other.FileReadWrite;

import java.io.IOException;
import java.util.Calendar;

public class main {
	public static void main(String args[]) throws MyException, IOException{
		
		FileReadWrite frw = new FileReadWrite();
		Database db = new Database();
		int duration = 540;
		int step = 1;
		String granularity = "second";
//		duration
//		String inputFile =	"/Users/hou/Documents/data/duration/540second50000";
//		String outputFile = "/Users/hou/Documents/data/VA/duration/(4)second50000-"+ duration ;
		//String inputFile = "/Users/hou/Documents/data/gran/3600second10000";
		//String outputFile = "/Users/hou/Documents/data/VA/granularity/answerForSecond10000-"+duration+"(5)";
//		granularity
//		String inputFile = "/Users/hou/Documents/data/gran/60(60)minute10000";
//		String outputFile = "/Users/hou/Documents/data/VA/granularity/answerFor60Minute10000-" +duration +"(test)";
//		int range = 10;
//		String inputFile = "/Users/hou/Documents/data/range/300second"+ range +"Day20000";
//		String outputFile = "/Users/hou/Documents/data/VA/range/answerFor"+range+"Day(5)";
		
		//int scale = 40000;
		//int gran = 5;
//		int userNum = 50;
//		String inputFile = "/Users/hou/Documents/data/simulate/" + userNum + "/Sorted" + step + "minute";
		String inputFile = "/Users/supreme/Desktop/ViolentAlgorithm/data/duration/60seconds10000";
		String outputFile = "/Users/supreme/Desktop/ViolentAlgorithm/data/duration/9min/queryResult";
//		String outputFile = "/Users/hou/Documents/data/simulate/"  + userNum + "/" +"VAanswerFor" + step + "minute";
		
		Runtime rt = Runtime.getRuntime();
	
		String timeline = frw.fileRead(inputFile, db ,duration,granularity);
		Calendar c1 = Calendar.getInstance();
		

		ViolentAlgorithm va = new ViolentAlgorithm();
		System.out.println("计时开始");
		int max = va.scanTimeline(timeline, db, duration, step, granularity);
		System.out.println("计时结束");
		frw.fileWrite(outputFile, va, max,c1 , rt);
//		System.out.println("算法总消耗内存:"+ (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));

	}
	
	
	
}
