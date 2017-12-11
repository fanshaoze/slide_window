package Other;

import Other.Database;
import Other.Interval;
import Other.Time;
import Other.User;

import java.util.*;

public class ViolentAlgorithm {
	TreeMap<String, ArrayList<String>> ir;
	public ViolentAlgorithm(){
		ir = new TreeMap<String,ArrayList<String>>();
	}
	/**
	 * 从2013-07-01 00:00:00开始，沿着时间线一次扫描，获得所有时间区间的结果，区间的终止时间－开始时间＝duration
	 * @param db 原始数据库
	 * @param duration
	 * @return 最优区间覆盖的记录条数
	 */

	public int scanTimeline(String timeline, Database db, int duration, int step, String granularity){
		Calendar c1 = Calendar.getInstance();
		Time TIME = new Time();
		//提取timeline中的起始时间和终止时间
		String[] t = timeline.split(",");
		int t1 = Integer.parseInt(t[0]);	//起始时间
		int t2 = Integer.parseInt(t[1]);	//终止时间
		
		int time = t1;//表示扫描时当前的时间结点，从t1开始
		int max = 0; //记录区间覆盖的最多用户数量
		String start,end,interval;
		//Runtime rt = Runtime.getRuntime();
		int memory = 0;
	//	System.out.println("当前内存消耗：" + (rt.totalMemory() - rt.freeMemory())/1024/1024);
		while(time <= (t2- duration)){
//			if (((rt.totalMemory() - rt.freeMemory())/1024/1024) > memory)
//				memory = (int) ((rt.totalMemory() - rt.freeMemory())/1024/1024);
			Iterator<User> it = db.data.iterator();
			User u;
			ArrayList<String> userSet = new ArrayList<String>();
			while(it.hasNext()){
				u = it.next();
				for(Interval eachI:u.intervals){
					if(eachI.getStart()<=time && eachI.getEnd() >= time+duration){
						userSet.add(u.getUser());
					}
				}
			}

			if(userSet.size() >= max) {
				String preInterval;
				ArrayList<String> preSet;
				int preStart;
				if(!ir.isEmpty()) {
					preInterval = ir.lastKey();
					preSet = ir.get(preInterval);
					String[] temp = preInterval.split(",");
					if(granularity.equals("second")){
						preStart = TIME.uniformToSecond(temp[0].substring(1));
					}else if(granularity.equals("minute")){
						preStart = TIME.uniformToMinute(temp[0].substring(1));
					}else {
						preStart = Integer.parseInt(temp[0].substring(1));
					}
				}
				else {
					preInterval = null;
					preSet = null;
					preStart = 0;
				}
				if( (preSet!=null) &&preSet.equals(userSet)){
					ir.remove(preInterval);
					if(granularity.equals("second")) {
						interval = "[" + TIME.transformFromSecond(preStart) + "," 
								+ TIME.transformFromSecond(time+duration) + "]";
					}
					else if(granularity.equals("minute")){
						interval = "[" + TIME.transformFromMinute(preStart) + "," 
								+ TIME.transformFromMinute(time+duration) + "]";
					}
					else {
						interval = "[" + preStart + "," + (time+duration) + "]";
					}
				}
				else {
					if(granularity.equals("second")){
						interval = "[" + TIME.transformFromSecond(time) + "," +
									TIME.transformFromSecond(time+duration) + "]";
					}
					else if(granularity.equals("minute")){
						interval = "[" + TIME.transformFromMinute(time) + "," +
								TIME.transformFromMinute(time+duration) + "]";
					}
					else {
						interval = "[" + time + "," + (time+duration) + "]";
					}
				}
				max = userSet.size();
				ir.put(interval, userSet);
			}
			time += step ;
		}
		Calendar c2 = Calendar.getInstance();
		System.out.println("扫描完成： time:"+ (c2.getTimeInMillis() - c1.getTimeInMillis()));
		return max;
	}

	public Map<String, Integer> getCandidateMap(String timeline, Database db, int duration, int step, String granularity){
		//用来存放单个活动的所有候选区间和对应的候选用户集合中的用户数量
		Map<String, Integer> candidateMap = new HashMap<>();
		Calendar c1 = Calendar.getInstance();
		Time TIME = new Time();

		//提取timeline中的起始时间和终止时间
		String[] t = timeline.split(",");
		int t1 = Integer.parseInt(t[0]);	//起始时间
		int t2 = Integer.parseInt(t[1]);	//终止时间

		int time = t1;//表示扫描时当前的时间结点，从t1开始
//		int max = 0; //记录区间覆盖的最多用户数量
		String start,end,interval;
		//Runtime rt = Runtime.getRuntime();
		int memory = 0;
		//	System.out.println("当前内存消耗：" + (rt.totalMemory() - rt.freeMemory())/1024/1024);
		while(time <= (t2- duration)){
//			if (((rt.totalMemory() - rt.freeMemory())/1024/1024) > memory)
//				memory = (int) ((rt.totalMemory() - rt.freeMemory())/1024/1024);
			Iterator<User> it = db.data.iterator();
			User u;
			ArrayList<String> userSet = new ArrayList<String>();
			while(it.hasNext()){
				u = it.next();
				for(Interval eachI:u.intervals){
					if(eachI.getStart()<=time && eachI.getEnd() >= time+duration){
						userSet.add(u.getUser());
					}
				}
			}

			if(userSet.size() > 0) {
				String preInterval;
				ArrayList<String> preSet;
				int preStart;
				if(!ir.isEmpty()) {
					preInterval = ir.lastKey();
					preSet = ir.get(preInterval);
					String[] temp = preInterval.split(",");
					if(granularity.equals("second")){
						preStart = TIME.uniformToSecond(temp[0].substring(1));
					}else if(granularity.equals("minute")){
						preStart = TIME.uniformToMinute(temp[0].substring(1));
					}else {
						preStart = Integer.parseInt(temp[0].substring(1));
					}
				}
				else {
					preInterval = null;
					preSet = null;
					preStart = 0;
				}
				if( (preSet!=null) &&preSet.equals(userSet)){
					ir.remove(preInterval);
					if(granularity.equals("second")) {
						interval = "[" + TIME.transformFromSecond(preStart) + ","
								+ TIME.transformFromSecond(time+duration) + "]";
					}
					else if(granularity.equals("minute")){
						interval = "[" + TIME.transformFromMinute(preStart) + ","
								+ TIME.transformFromMinute(time+duration) + "]";
					}
					else {
						interval = "[" + preStart + "," + (time+duration) + "]";
					}
				}
				else {
					if(granularity.equals("second")){
						interval = "[" + TIME.transformFromSecond(time) + "," +
								TIME.transformFromSecond(time+duration) + "]";
					}
					else if(granularity.equals("minute")){
						interval = "[" + TIME.transformFromMinute(time) + "," +
								TIME.transformFromMinute(time+duration) + "]";
					}
					else {
						interval = "[" + time + "," + (time+duration) + "]";
					}
				}
				candidateMap.put(interval, userSet.size());
//				ir.put(interval, userSet);
			}
			time += step ;
		}
		Calendar c2 = Calendar.getInstance();
		System.out.println("扫描完成： time:"+ (c2.getTimeInMillis() - c1.getTimeInMillis()));
		return candidateMap;
	}

	/**
	 * 判断区间是否需要合并。当两个区间的用户集合内容相同，且互相相交时，返回true；否则返回false
	 * @param u1
	 * @param u2
	 * @param s1
	 * @param e1
	 * @param s2
	 * @param e2
	 * @return
	 */
	public boolean mergeCondition(ArrayList<String>u1, ArrayList<String>u2, int s1, int e1,int s2, int e2){
		if(u1.equals(u2) && (s1<= e2 && s2 <= e1))
			return true;
		else return false;
	}
	

}
