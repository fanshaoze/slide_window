package ActivityConflict;

import Other.Time;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


import java.io.IOException;
import java.text.ParseException;
import java.util.*;

/**
 * Created by eason on 2017/6/2.
 */

public class ActivityConflict {

    public Map<ArrayList<String>, Integer> ActivityConflict (
            Map<String, Integer> map1,
            Map<String, Integer> map2,
            Map<String, Integer> map3)
    {

        Map<ArrayList<String>, Integer> plans = new HashMap<>();
        int max = -1;

        for (String inter1:map1.keySet())
        {
            for (String inter2:map2.keySet())
            {
                for (String inter3:map3.keySet())
                {
                    ArrayList<String> intervals = new ArrayList<>();
                    intervals.add(inter1);
                    intervals.add(inter2);
                    intervals.add(inter3);
                    try {
                        int sum = map1.get(inter1) + map2.get(inter2) + map3.get(inter3);
                        if (sum > max){
                            if (!isConflict(intervals)){
//                                System.out.println("不冲突");
                                plans.put(intervals, sum);
                                max = sum;
                            }
                        }
                    }catch (Exception e){
                        System.err.println("catch a Exception!");
                    }
                }
            }
        }
        System.out.println("能参加这三个活动的最大用户数是" + max + "人次");
        Map<ArrayList<String>, Integer> optPlans = new HashMap<>();
        for (ArrayList<String> list: plans.keySet()){
            if (plans.get(list) == max){
                optPlans.put(list, max);

            }
        }
        return optPlans;
    }


    public Map<ArrayList<String>, Integer> TwoActivityConflict (Map<String, Integer> map1, Map<String, Integer> map2)
    {

        Map<ArrayList<String>, Integer> plans = new HashMap<>();
        int max = -1;

        for (String inter1:map1.keySet())
        {
            for (String inter2:map2.keySet())
            {
                ArrayList<String> intervals = new ArrayList<>();
                intervals.add(inter1);
                intervals.add(inter2);
                try {
                    int sum = map1.get(inter1) + map2.get(inter2);
                    if (sum > max){
                        if (!isConflict(intervals)){
//                                System.out.println("不冲突");
                            plans.put(intervals, sum);
                            max = sum;
                        }
                    }
                }catch (Exception e){
                    System.err.println("catch a Exception!");
                }
            }
        }
        System.out.println("能参加这两个活动的最大用户数是" + max + "人次");
        Map<ArrayList<String>, Integer> optPlans = new HashMap<>();
        for (ArrayList<String> list: plans.keySet()){
            if (plans.get(list) == max){
                optPlans.put(list, max);

            }
        }
        return optPlans;
    }

    public Map<ArrayList<String>, Integer> FourActivityConflict (
            Map<String, Integer> map1,
            Map<String, Integer> map2,
            Map<String, Integer> map3,
            Map<String, Integer> map4)
    {

        Map<ArrayList<String>, Integer> plans = new HashMap<>();
        int max = -1;

        for (String inter1:map1.keySet())
        {
            for (String inter2:map2.keySet())
            {
                for (String inter3:map3.keySet())
                {
                    for (String inter4:map4.keySet()){
                        ArrayList<String> intervals = new ArrayList<>();
                        intervals.add(inter1);
                        intervals.add(inter2);
                        intervals.add(inter3);
                        intervals.add(inter4);
                        try {
                            int sum = map1.get(inter1) + map2.get(inter2) + map3.get(inter3) + map4.get(inter4);
                            if (sum > max){
                                if (!isConflict(intervals)){
//                                System.out.println("不冲突");
                                    plans.put(intervals, sum);
                                    max = sum;
                                }
                            }
                        }catch (Exception e){
                            System.err.println("catch a Exception!");
                        }
                    }
                }
            }
        }
        System.out.println("能参加这四个活动的最大用户数是" + max + "人次");
        Map<ArrayList<String>, Integer> optPlans = new HashMap<>();
        for (ArrayList<String> list: plans.keySet()){
            if (plans.get(list) == max){
                optPlans.put(list, max);

            }
        }
        return optPlans;
    }

    public Map<ArrayList<String>, Integer> FiveActivityConflict (
            Map<String, Integer> map1,
            Map<String, Integer> map2,
            Map<String, Integer> map3,
            Map<String, Integer> map4,
            Map<String, Integer> map5)
    {

        Map<ArrayList<String>, Integer> plans = new HashMap<>();
        int max = -1;

        for (String inter1:map1.keySet())
        {
            for (String inter2:map2.keySet())
            {
                for (String inter3:map3.keySet())
                {
                    for (String inter4:map4.keySet())
                    {
                        for (String inter5:map5.keySet())
                        {
                            ArrayList<String> intervals = new ArrayList<>();
                            intervals.add(inter1);
                            intervals.add(inter2);
                            intervals.add(inter3);
                            intervals.add(inter4);
                            intervals.add(inter5);
                            try {
                                int sum = map1.get(inter1) + map2.get(inter2) +
                                        map3.get(inter3) + map4.get(inter4) + map5.get(inter5);
                                if (sum > max){
                                    if (!isConflict(intervals)){
//                                System.out.println("不冲突");
                                        plans.put(intervals, sum);
                                        max = sum;
                                    }
                                }
                            }catch (Exception e){
                                System.err.println("catch a Exception!");
                            }
                        }

                    }
                }
            }
        }
        System.out.println("能参加这五个活动的最大用户数是" + max + "人次");
        Map<ArrayList<String>, Integer> optPlans = new HashMap<>();
        for (ArrayList<String> list: plans.keySet()){
            if (plans.get(list) == max){
                optPlans.put(list, max);

            }
        }
        return optPlans;
    }

    public Map<ArrayList<String>, Integer> SixActivityConflict (
            Map<String, Integer> map1, Map<String, Integer> map2, Map<String, Integer> map3,
            Map<String, Integer> map4, Map<String, Integer> map5, Map<String, Integer> map6)
    {

        Map<ArrayList<String>, Integer> plans = new HashMap<>();
        int max = -1;

        for (String inter1:map1.keySet())
        {
            for (String inter2:map2.keySet())
            {
                for (String inter3:map3.keySet())
                {
                    for (String inter4:map4.keySet())
                    {
                        for (String inter5:map5.keySet())
                        {
                            for (String inter6:map6.keySet()){
                                ArrayList<String> intervals = new ArrayList<>();
                                intervals.add(inter1);intervals.add(inter2);intervals.add(inter3);
                                intervals.add(inter4);intervals.add(inter5);intervals.add(inter6);
                                try {
                                    int sum = map1.get(inter1) + map2.get(inter2) + map3.get(inter3)
                                            + map4.get(inter4) + map5.get(inter5) + map6.get(inter6);
                                    if (sum > max){
                                        if (!isConflict(intervals)){
//                                System.out.println("不冲突");
                                            plans.put(intervals, sum);
                                            max = sum;
                                        }
                                    }
                                }catch (Exception e){
                                    System.err.println("catch a Exception!");
                                }
                            }
                        }

                    }
                }
            }
        }
        System.out.println("能参加这六个活动的最大用户数是" + max + "人次");
        Map<ArrayList<String>, Integer> optPlans = new HashMap<>();
        for (ArrayList<String> list: plans.keySet()){
            if (plans.get(list) == max){
                optPlans.put(list, max);

            }
        }
        return optPlans;
    }







    /**
     * 判断时间区间是否冲突
     * @param intervals
     * @return
     * @throws Exception
     */
    public boolean isConflict(ArrayList<String> intervals) throws Exception{
        Time TIME = new Time();
        ArrayList<String> sortedIntervals = sortAllIntervals(intervals);
        boolean isConflict = true;

        for (int i = 1; i < sortedIntervals.size(); i++) {
            /**
             * 后一个区间的开始时间
             */
            String secInter = sortedIntervals.get(i);
            String[] secInterArray = secInter.split(",");
            String secInterStart = secInterArray[0].substring(1);
//            System.out.println(secInterStart);

            /**
             * 前一个区间的结束时间
             */
            String firInter = sortedIntervals.get(i - 1);
            String[] firInterArray = firInter.split(",");
//            System.out.println(firInterArray[1]);
            String firInterEnd = firInterArray[1].substring(0, firInterArray[1].length() - 1);
//            System.out.println(firInterEnd);

            long secStartTime = TIME.uniformTime(secInterStart);

            long firEndTime = TIME.uniformTime(firInterEnd);

            if (secStartTime < firEndTime){
                break;
            }else {
                if (i == sortedIntervals.size() - 1)
                    isConflict = false;
            }
        }
        return isConflict;
    }

    /**
     *
     * @param intervals
     * @return 排完序后的数组
     * @throws Exception
     */
    public ArrayList<String> sortAllIntervals(ArrayList<String> intervals) throws Exception{
        Time TIME = new Time();
        Collections.sort(intervals, new Comparator<Object>() {
            @Override
            public int compare(Object o1, Object o2) {
                // TODO Auto-generated query stub
                String inter1 = (String) o1;
                String inter2 = (String) o2;
                long time1 = 0;
                String[] timeArray1 = inter1.split(",");
                String start1 = timeArray1[0].substring(1);
                try {
                    time1 = TIME.uniformTime(start1);
                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                long time2 = 0;
                String[] timeArray2 = inter2.split(",");
                String start2 = timeArray2[0].substring(1);
                try {
                    time2 = TIME.uniformTime(start2);
                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                return (time1 > time2) ? 1 :(time1 == time2) ? 0 : -1 ;
            }

        });
        return intervals;
    }


    public void outputPlans(Map<ArrayList<String>, Integer> plans, Calendar c, String outputFile) throws Exception{
        File f = new File(outputFile);
        FileWriter fw = new FileWriter(f);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write("最优区间和对应的活动最多参与总人次如下所示：\n");

        for (ArrayList<String> list: plans.keySet()) {
            String line = "";
            for (int i = 0; i < list.size(); i++) {
                line += list.get(i) + ",";
            }
            line += plans.get(list) + "\n";
            bw.write(line);
        }
        Calendar ca = Calendar.getInstance();
        long cost = ca.getTimeInMillis() - c.getTimeInMillis();
        bw.write("算法时间花费: " + Integer.toString((int) cost) + " ms\n");
        System.out.println("time:" + Integer.toString((int) cost));
        //bw.write("算法内存消耗：" + (rt.totalMemory() - rt.freeMemory())/1024/1024 + "MB");
        bw.close();
    }

}

