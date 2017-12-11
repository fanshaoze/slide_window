package CreateTimeRangeTestCase;

import java.io.*;

import Other.*;

/**
 * Created by eason on 2017/2/28.
 */
public class CreateTimeRangeData {
    public String CreateTimeRangeData(String file, Database db , int duration, String granularity,String file2, String tmin, String tmax) throws Exception{
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
        int minTIME = TIME.uniformToSecond(tmin);
        int maxTIME = TIME.uniformToSecond(tmax);


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

            if ((minTIME <= start) && (end <= maxTIME)){
                validRecord = user + "-->" + "[" + startTime + "," + endTime + "]" + "\n";
                bw.write(validRecord);
            }


            if(((end-start)>= duration )&&(minTIME <= start)&&(end <= maxTIME))
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
//        timeline = Integer.toString(min) + "," + Integer.toString(max);
        timeline = TIME.transformFromSecond(min) + "," + TIME.transformFromSecond(max);
        System.out.println("共读入"+  count + "记录");
        System.out.println("有效用户: " + db.getSize());
        bw.close();
        br.close();
        return timeline;
    }

    public static void main(String[] args) throws Exception{
        Database db = new Database();
        int duration = 60;
        int step = 1;
        String granularity = "second";

        String file1 = "/Users/supreme/Desktop/ViolentAlgorithm/data/TimeRange/NewReadInterval";
        String file2 = "/Users/supreme/Desktop/ViolentAlgorithm/data/TimeRange/5day/5daydata";
        String tmin = "2013-07-01 00:00:00";
        String tmax = "2013-07-05 23:59:59";
        CreateTimeRangeData ctrd = new CreateTimeRangeData();
        String timeline = ctrd.CreateTimeRangeData(file1, db, duration,granularity,file2,tmin,tmax);
        System.out.println(timeline);

    }


}
