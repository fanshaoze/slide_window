package Other;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Time {
	/**
	 * 将相对于2013-07-01 00:00:00的时间转化回具体的"yyyy-MM-dd HH:mm:ss"格式的时间
	 * @param time 相对时间
	 * @return 绝对时间
	 */
	public String transformFromSecond(int time){
		int day,hour,minute,second;
		String s = "2013-07-";
		day = time / (24 * 3600);
		time = time - day * (24 * 3600);
		
		hour = time / 3600;
		time = time - hour * 3600;
		
		minute = time / 60 ;
		second = time - minute * 60;
		 
		if(day+1 < 10)
			s += "0" + Integer.toString(day+1);
		else
			s += Integer.toString(day+1);
		s += " ";
		if(hour < 10)
			s += "0" + Integer.toString(hour);
		else s += Integer.toString(hour);
		if(minute < 10)
			s += ":0" + Integer.toString(minute);
		else s += ":" + Integer.toString(minute);
		if(second < 10)
			s += ":0" + Integer.toString(second);
		else s += ":" + Integer.toString(second);
		return s;
	}
	public String transformFromMinute(int time){
		int day,hour,minute,second;
		String s = "2013-07-";
		day = time / (24 * 60);
		time = time - day * (24 * 60);
		
		hour = time / 60;
		minute = time - hour * 60;
		
		 
		if(day+1 < 10)
			s += "0" + Integer.toString(day+1);
		else
			s += Integer.toString(day+1);
		s += " ";
		if(hour < 10)
			s += "0" + Integer.toString(hour);
		else s += Integer.toString(hour);
		if(minute < 10)
			s += ":0" + Integer.toString(minute);
		else s += ":" + Integer.toString(minute);
		
		s += ":00";
		return s;
	}
	
	/**
	 * 将时间转化为相对于2013-07-01 00:00:00的时间，时间粒度为秒钟
	 * @param time  绝对时间
	 * @return	相对时间（秒）
	 */
	public int uniformToSecond(String time) {
		int t = 0;
		String[] temp = time.split(" ");
		String day = temp[0];
		String hms = temp[1];
		t += (Integer.parseInt(day.split("-")[2])-1) * 24 * 3600;
		String[] temp1 = hms.split(":");
		int hour = Integer.parseInt(temp1[0]);
		int minute = Integer.parseInt(temp1[1]);
		int second = Integer.parseInt(temp1[2]);
		t += hour * 3600 + minute * 60 + second;
		return t;
		 
	}
	
	/**
	 * 将时间转化为相对于2013-07-01 00:00:00的时间，时间粒度为分钟
	 * @param time 绝对时间
	 * @return 相对时间(分）
	 */
	public int uniformToMinute(String time){
		int t = 0;
		String[] temp = time.split(" ");
		String day = temp[0];
		String hms = temp[1];
		t += (Integer.parseInt(day.split("-")[2])-1) * 24 * 60;
		String[] temp1 = hms.split(":");
		int hour = Integer.parseInt(temp1[0]);
		int minute = Integer.parseInt(temp1[1]);
		int second = Integer.parseInt(temp1[2]);
		t += hour * 60 + minute;
		return t;
	}

	/**
	 * 时间标准化,
	 * @param s
	 * @return long型时间
	 * @throws ParseException
     */
	public long uniformTime(String s) throws ParseException {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		long TIME = ((Date)sdf.parse(s)).getTime();

		return TIME;

	}
	
}
