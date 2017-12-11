package CreateTimeRangeTestCase;

import Other.Database;
import Other.Interval;
import Other.Time;
import Other.User;

import java.io.*;

/**
 * Created by eason on 2017/2/28.
 */
public class Spilt50000 {
    public Spilt50000(String file, Database db, String granularity, String file2) throws Exception{
        Time TIME = new Time();
        int min = Integer.MAX_VALUE,max =-1;
        int start= 0,end = 0;
        String timeline= "";
        File f = new File(file);
        FileReader fr = new FileReader(f);
        BufferedReader br = new BufferedReader(fr);
        String line = "";
        String preUser = "";
        int count = 0;
//        int minTIME = TIME.uniformToSecond(tmin);
//        int maxTIME = TIME.uniformToSecond(tmax);


        /**
         * 把在minTIME和maxTIME之间的数据写入文件中
         */

        File f2 = new File(file2);
        FileWriter fw = new FileWriter(f2);
        BufferedWriter bw = new BufferedWriter(fw);
        String validRecord = "";

        //
        while ( (line = br.readLine()) != null){
            String[] context = line.split("-->");
            String user = context[0];
            String[] time = context[1].split(",");

            String startTime = time[0].substring(1);
            String endTime = time[1].substring(0, time[1].length()-1);
            if(granularity.equals("second")){
                start = TIME.uniformToSecond(startTime);
                end = TIME.uniformToSecond(endTime);
            }
            else if(granularity.equals("minute")) {
                start = TIME.uniformToMinute(time[0].substring(1));
                end = TIME.uniformToMinute(time[1].substring(0, time[1].length()-1));
            }
            else if(granularity.equals("null")){
                start = Integer.parseInt(time[0].substring(1));
                end = Integer.parseInt(time[1].substring(0, time[1].length()-1));
            }

            if (count < 50000){
                validRecord = user + "-->" + "[" + startTime + "," + endTime + "]" + "\n";
                bw.write(validRecord);
                ++count;
            }
        }
//        timeline = Integer.toString(min) + "," + Integer.toString(max);
//        timeline = TIME.transformFromSecond(min) + "," + TIME.transformFromSecond(max);
        System.out.println("共读入"+  count + "记录");
//        System.out.println("有效用户: " + db.getSize());
        bw.close();
        br.close();
    }

    public static String verify(String file, Database db , int duration, String granularity) throws IOException{
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
        timeline = TIME.transformFromSecond(min) + "," + TIME.transformFromSecond(max);
        System.out.println("共读入"+  count + "记录");
        System.out.println("有效用户: " + db.getSize());
        return timeline;
    }

    public static void main(String[] args) throws Exception{
        Database db = new Database();
        int duration = 60;
        int step = 1;
        String granularity = "second";

//        String file1 = "/Users/supreme/Desktop/ViolentAlgorithm/data/TimeRange/30day/30daydata";
//        String file2 = "/Users/supreme/Desktop/ViolentAlgorithm/data/TimeRange/30day/30dayspilt50000";
//        Spilt50000 spilt50000 = new Spilt50000(file1, db, granularity, file2);

        String file2 = "/Users/supreme/Desktop/ViolentAlgorithm/data/scale/40000/60seconds40000";
        String timeline = verify(file2, db, duration, granularity);
        System.out.println(timeline);



    }


}
