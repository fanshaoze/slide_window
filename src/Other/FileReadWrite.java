package Other;

import Other.Database;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Set;

public class FileReadWrite {
	 public String fileRead(String file, Database db , int duration, String granularity) throws IOException{
			Time TIME = new Time();
			int min = Integer.MAX_VALUE,max =-1;
			int start= 0,end = 0;
			String timeline="";
			File f = new File(file);
			FileReader fr = new FileReader(f);
			BufferedReader br = new BufferedReader(fr);
			String line = "";
			String preUser = "";
			int count = 0;
			while ( (line = br.readLine()) != null){
				String[] context = line.split("-->");
				String user = context[0];
				String[] time = context[1].split(",");
				if(granularity.equals("second")){
					start = TIME.uniformToSecond(time[0].substring(1));	
					end = TIME.uniformToSecond(time[1].substring(0, time[1].length()-1));
				}
				else if(granularity.equals("minute")) {
					start = TIME.uniformToMinute(time[0].substring(1));	
					end = TIME.uniformToMinute(time[1].substring(0, time[1].length()-1));
				}
				else if(granularity.equals("null")){
					start = Integer.parseInt(time[0].substring(1));
					end = Integer.parseInt(time[1].substring(0, time[1].length()-1));
				}
				if( (end-start)>= duration )
				{
					if(!user.equals(preUser)){
						preUser = user;
						User u = new User(user,new Interval(start,end));
						db.insert(u);
					}
					else{
						db.insertI(new Interval(start,end));
					}
					if( start < min ) min = start;
					if( end > max ) max = end;
				}		
				++count;
			}
			timeline = Integer.toString(min) + "," + Integer.toString(max);
			System.out.println("共读入"+  count + "记录");
			System.out.println("有效用户: " + db.data.size());
		    System.out.println("时间线是" + timeline);
			return timeline;
	 }

	public void fileWrite(String file, ViolentAlgorithm va, int max, Calendar c, Runtime rt) throws IOException{
		File f = new File(file);
		FileWriter fw = new FileWriter(f);
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write("最优区间覆盖的记录数量为" + Integer.toString(max) + "\n");
		bw.write("最优区间如下所示：\n");
		Set<String> intervalSet = va.ir.keySet();
		for(String interval:intervalSet) {
			ArrayList<String> recordSet = va.ir.get(interval);
			if(max == recordSet.size()) {
				String s = "";
				s += interval + "-->";
				int count = 1;
				for(String each: recordSet) {
					if(count != 1) s += ",";
					s += each;
					count++;
				}
				s += "\n";
				bw.write(s);
			}
		}
		Calendar ca = Calendar.getInstance();
		long cost = ca.getTimeInMillis() - c.getTimeInMillis();
		bw.write("算法时间花费: " + Integer.toString((int) cost) + " ms\n");
		System.out.println("time:" + Integer.toString((int) cost));
		//bw.write("算法内存消耗：" + (rt.totalMemory() - rt.freeMemory())/1024/1024 + "MB");
		bw.close();
	}

}

