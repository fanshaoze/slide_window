package ActivityConflict;

import Other.*;

import java.io.*;
import java.util.*;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by eason on 2017/6/2.
 */
public class ReprocessRawFile {
    public void countDateRecordNumber(String fileName)throws Exception{
        File file = new File(fileName);
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);

        String line = "";
//        int count = 0;

        //统计一个月内每天的记录条数.
        Map<String, Integer> map = new HashMap<>();

        while ( (line = br.readLine()) != null) {
            String[] context = line.split("-->");
            String user = context[0];
            String[] time = context[1].split(",");

            String start = time[0].substring(1);
            String startDate = start.substring(0, 10);
            String end = time[1].substring(0, time[1].length() - 1);
//            String endDate = end.substring(0, 10);

            if (map.containsKey(startDate)){
                int value = map.get(startDate)+1;
                map.put(startDate, value);
            }else {
                map.put(startDate, 1);
            }

        }
        for (String key:map.keySet()) {
            System.out.println(key + "-->" + map.get(key));

        }
    }

    public String fileRead(String file, Database db , int duration, String granularity, String queryRange) throws IOException {
        Time TIME = new Time();
        int min = Integer.MAX_VALUE,max =-1;
        int start= 0,end = 0;
        int range_start = 0, range_end = 0;
        String timeline="";
        File f = new File(file);
        FileReader fr = new FileReader(f);
        BufferedReader br = new BufferedReader(fr);
        String line = "";
        String preUser = "";
        int validRecordCount = 0;

        //对查询范围做一个字符串分割处理.
        String[] rangeTime = queryRange.split(",");
        String rangeStart = rangeTime[0].substring(1);
        String rangeEnd = rangeTime[1].substring(0, rangeTime[1].length() - 1);
        System.out.println("查询的时间范围:" + rangeStart + "," + rangeEnd);


        while ( (line = br.readLine()) != null){
            String[] context = line.split("-->");
            String user = context[0];
            String[] time = context[1].split(",");
            if(granularity.equals("second")){
                start = TIME.uniformToSecond(time[0].substring(1));
                end = TIME.uniformToSecond(time[1].substring(0, time[1].length()-1));
                range_start = TIME.uniformToSecond(rangeStart);
                range_end = TIME.uniformToSecond(rangeEnd);
            }
            else if(granularity.equals("minute")) {
                start = TIME.uniformToMinute(time[0].substring(1));
                end = TIME.uniformToMinute(time[1].substring(0, time[1].length()-1));
                range_start = TIME.uniformToMinute(rangeStart);
                range_end = TIME.uniformToMinute(rangeEnd);
            }
            else if(granularity.equals("null")){
                start = Integer.parseInt(time[0].substring(1));
                end = Integer.parseInt(time[1].substring(0, time[1].length()-1));
                range_start = Integer.parseInt(rangeStart);
                range_end = Integer.parseInt(rangeEnd);
            }

            //该时间区间在查询范围内,属于完全有效时间区间
            if ((range_start <= start) && (end <= range_end))
            {
                if((end-start) >= duration)
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
                    ++validRecordCount;
                }
            }
            else if ((start < range_start) && (range_start < end)){
                start = range_start;
                if((end-start)>= duration)
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
                    ++validRecordCount;
                }
            }else if ((start < range_end) && (range_end < end)){
                end = range_end;
                if((end-start)>= duration)
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
                    ++validRecordCount;
                }
            }
        }
        timeline = Integer.toString(min) + "," + Integer.toString(max);
        System.out.println("共读入"+  validRecordCount + "记录");
        System.out.println("有效用户: " + db.getSize());
        System.out.println("时间线是" + timeline);
        return timeline;
    }

    public void outPutCandidateMap(String file, Calendar c, Runtime rt, Map<String, Integer> candidateMap) throws IOException{
        File f = new File(file);
        FileWriter fw = new FileWriter(f);
        BufferedWriter bw = new BufferedWriter(fw);
//		bw.write("最优区间覆盖的记录数量为" + Integer.toString(max) + "\n");
        bw.write("最优区间如下所示：\n");
        Set<String> intervalSet = candidateMap.keySet();
        for(String interval:intervalSet) {
            int userNum = candidateMap.get(interval);
            String s = "";
            s += interval + "-->" + userNum;
            int count = 1;
            s += "\n";
            bw.write(s);
        }
        Calendar ca = Calendar.getInstance();
        long cost = ca.getTimeInMillis() - c.getTimeInMillis();
        bw.write("算法时间花费: " + Integer.toString((int) cost) + " ms\n");
        System.out.println("time:" + Integer.toString((int) cost));
        //bw.write("算法内存消耗：" + (rt.totalMemory() - rt.freeMemory())/1024/1024 + "MB");
        bw.close();
    }



    /*
    public void splitRawFile(
            String fileName,
            String queryRange,
            Database db,
            long duration,
            Map<Integer, User> markUserMap,
            ArrayList<String> allTimeArray,
            Set<String> allStartTimeSet,
            Set<String> allUsers)throws Exception
    {
        Time TIME = new Time();
        long min = Integer.MAX_VALUE,max =-1;
        long startTIME = 0;
        long endTIME = 0;

        File file = new File(fileName);
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);

        String line = "";
        String preUser = "";
        int validRecordCount = 0;
        int markUserFigure = 0;
        Map<String, Boolean> markStartEndMap = new HashMap<>();

        //对查询范围做一个字符串分割处理.
        String[] rangeTime = queryRange.split(",");
        String rangeStart = rangeTime[0].substring(1);
        String rangeEnd = rangeTime[1].substring(0, rangeTime[1].length() - 1);
        System.out.println("查询的时间范围:" + rangeStart + "," + rangeEnd);
        long rangeStartTIME = TIME.uniformTime(rangeStart);
        long rangeEndTIME = TIME.uniformTime(rangeEnd);

        //开始遍历
        while ( (line = br.readLine()) != null) {
            String[] context = line.split("-->");
            String user = context[0];
            String[] time = context[1].split(",");

            String start = time[0].substring(1);
            String end = time[1].substring(0, time[1].length() - 1);



            //该时间区间在查询范围内,属于完全有效时间区间
            if ((rangeStartTIME <= startTIME) && (endTIME <= rangeEndTIME))
            {
                if ((endTIME - startTIME) >= duration)
                {
                    //1.设置开始时间 和 结束时间
                    markStartEndMap.put(start, true);
                    if (!markStartEndMap.containsKey(end)) {
                        markStartEndMap.put(end, false);
                    }

                    //2.将有效区间插入到数据库中.
                    if(!user.equals(preUser))
                    {
                        preUser = user;
                        User u = new User(user,new Interval(start,end));
                        db.insert(u);

                        //所有用户名
                        allUsers.add(user);
                        markUserMap.put(markUserFigure, u);
                        ++markUserFigure;
                    }
                    else{
                        db.insertI(new Interval(start,end));
                    }
                    if( startTIME < min ) min = startTIME;
                    if( endTIME > max ) max = endTIME;

                    //3.有效记录条数加一
                    ++validRecordCount;

                }


            }
            //需要重新切割区间
            else if ((startTIME < rangeStartTIME) && (rangeStartTIME < endTIME))
            {
                if ((endTIME - rangeStartTIME) >= duration)
                {
                    start = rangeStart;
                    //1.设置开始时间 和 结束时间
                    markStartEndMap.put(start, true);
                    if (!markStartEndMap.containsKey(end)) {
                        markStartEndMap.put(end, false);
                    }

                    //2.将有效区间插入到数据库中.
                    if (!user.equals(preUser)) {
                        preUser = user;
                        User u = new User(user, new Interval(start, end));
                        db.insert(u);

                        //所有用户名
                        allUsers.add(user);
                        markUserMap.put(markUserFigure, u);
                        ++markUserFigure;
                    } else {
                        db.insertI(new Interval(start, end));
                    }
                    if (startTIME < min) min = startTIME;
                    if (endTIME > max) max = endTIME;

                    //3.有效记录条数加一
                    ++validRecordCount;
                }

            }
            else if (startTIME < rangeEndTIME && rangeEndTIME < endTIME)
            {
                if ((rangeEndTIME - startTIME) >= duration)
                {
                    end = rangeEnd;
                    //1.设置开始时间 和 结束时间
                    markStartEndMap.put(start, true);
                    if (!markStartEndMap.containsKey(end)) {
                        markStartEndMap.put(end, false);
                    }

                    //2.将有效区间插入到数据库中.
                    if (!user.equals(preUser)) {
                        preUser = user;
                        User u = new User(user, new Interval(start, end));
                        db.insert(u);

                        //所有用户名
                        allUsers.add(user);
                        markUserMap.put(markUserFigure, u);
                        ++markUserFigure;
                    } else {
                        db.insertI(new Interval(start, end));
                    }
                    if (startTIME < min) min = startTIME;
                    if (endTIME > max) max = endTIME;

                    //3.有效记录条数加一
                    ++validRecordCount;
                }

            }
        }

//         把 markStartEndMap 里 value 值为 true 的 key (即所有开始时间)加到 allStartTimeArray;

        for (String t : markStartEndMap.keySet()) {
            allTimeArray.add(t);
            if (markStartEndMap.get(t)) {
                allStartTimeSet.add(t);
            }
        }
        System.out.println("共读入"+ validRecordCount + "记录");
        System.out.println("有效用户: " + db.data.size());
    }
    */

}
