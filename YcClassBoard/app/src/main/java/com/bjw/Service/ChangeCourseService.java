package com.bjw.Service;

import android.app.Service;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.IBinder;
import android.util.Log;

import com.bjw.Common.DatebaseHelper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.bjw.Common.StaticConfig.databaseVersion;
import static com.bjw.Common.StaticConfig.databasename;
import static com.bjw.Common.StaticConfig.lessonTables;

/*************************************************
 *@date：2017/11/17
 *@author：  zxj
 *@description： 定时器的服务
*************************************************/

public class ChangeCourseService extends Service {
    private TimeThread timeThread;
    List<Date> timeforbegins=new ArrayList<>();
    List<Date> timeforends=new ArrayList<>();
    private int flagToStopThread=0;
//    private List<LessonTable>lessonTableList;
    DatebaseHelper myDBHelper;
     SQLiteDatabase db;
    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
      //将得到的课表时间数据加入到相应的界面上便于
//        lessonTableList=new ArrayList<>();
        myDBHelper = new DatebaseHelper(getBaseContext(), databasename, null, databaseVersion);
        db = myDBHelper.getReadableDatabase();
//        Cursor cursor = db.query("LessonTable", null, null, null, null, null, null);
//        while (cursor.moveToNext())
//        {
//            int lab_room_id = cursor.getInt(cursor.getColumnIndex("lab_room_id"));
//            int course_present_people = cursor.getInt(cursor.getColumnIndex("course_present_people"));
//            int start_class = cursor.getInt(cursor.getColumnIndex("start_class"));
//            int end_class = cursor.getInt(cursor.getColumnIndex("end_class"));
//            int total_hour = cursor.getInt(cursor.getColumnIndex("total_hour"));
//            String course_name = cursor.getString(cursor.getColumnIndex("course_name"));
//            String course_number = cursor.getString(cursor.getColumnIndex("course_number"));
//            String course_image_url = cursor.getString(cursor.getColumnIndex("course_image_url"));
//            String course_teacher_name = cursor.getString(cursor.getColumnIndex("course_teacher_name"));
//            String course_beginning_time = cursor.getString(cursor.getColumnIndex("course_beginning_time"));
//            String course_ending_time = cursor.getString(cursor.getColumnIndex("course_ending_time"));
//            String classes_number = cursor.getString(cursor.getColumnIndex("classes_number"));
//            String course_program = cursor.getString(cursor.getColumnIndex("course_program"));
//            String course_date = cursor.getString(cursor.getColumnIndex("course_date"));
//            lessonTableList.add(new LessonTable(lab_room_id,course_present_people,start_class,end_class,total_hour,course_name,course_number,course_image_url,course_teacher_name,course_beginning_time,course_ending_time,classes_number,course_program,course_date)) ;
//        }
//        cursor.close();




























        DateFormat df = new SimpleDateFormat("HH:mm:ss");
        for(int i=0;i<lessonTables.size();i++)
        {
            try {
                Date dateforbegin=df.parse(lessonTables.get(i).getCourse_beginning_time());
                Date dateforend=df.parse(lessonTables.get(i).getCourse_ending_time());
                timeforbegins.add(dateforbegin);
                timeforends.add(dateforend);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        this.timeThread = new TimeThread();
        this.timeThread.start();
        return START_STICKY;
    }
    /*************************************************
     *@description： 定时属性课程界面的操作的线程
     *************************************************/
    private class TimeThread extends Thread {
        @Override
        public void run() {
            while (flagToStopThread==0) {

//                Cursor cursor = db.query("LessonTable", null, null, null, null, null, null);
//                lessonTableList.clear();
//                timeforbegins.clear();
//                timeforends.clear();
//                while (cursor.moveToNext())
//                {
//                    int lab_room_id = cursor.getInt(cursor.getColumnIndex("lab_room_id"));
//                    int course_present_people = cursor.getInt(cursor.getColumnIndex("course_present_people"));
//                    int start_class = cursor.getInt(cursor.getColumnIndex("start_class"));
//                    int end_class = cursor.getInt(cursor.getColumnIndex("end_class"));
//                    int total_hour = cursor.getInt(cursor.getColumnIndex("total_hour"));
//                    String course_name = cursor.getString(cursor.getColumnIndex("course_name"));
//                    String course_number = cursor.getString(cursor.getColumnIndex("course_number"));
//                    String course_image_url = cursor.getString(cursor.getColumnIndex("course_image_url"));
//                    String course_teacher_name = cursor.getString(cursor.getColumnIndex("course_teacher_name"));
//                    String course_beginning_time = cursor.getString(cursor.getColumnIndex("course_beginning_time"));
//                    String course_ending_time = cursor.getString(cursor.getColumnIndex("course_ending_time"));
//                    String classes_number = cursor.getString(cursor.getColumnIndex("classes_number"));
//                    String course_program = cursor.getString(cursor.getColumnIndex("course_program"));
//                    String course_date = cursor.getString(cursor.getColumnIndex("course_date"));
//                    lessonTableList.add(new LessonTable(lab_room_id,course_present_people,start_class,end_class,total_hour,course_name,course_number,course_image_url,course_teacher_name,course_beginning_time,course_ending_time,classes_number,course_program,course_date)) ;
//                }
//                cursor.close();
//                DateFormat df1 = new SimpleDateFormat("HH:mm:ss");
//                for(int i=0;i<lessonTableList.size();i++)
//                {
//                    try {
//                        Date dateforbegin=df1.parse(lessonTableList.get(i).getCourse_beginning_time());
//                        Date dateforend=df1.parse(lessonTableList.get(i).getCourse_ending_time());
//                        timeforbegins.add(dateforbegin);
//                        timeforends.add(dateforend);
//                    } catch (ParseException e) {
//                        e.printStackTrace();
//                    }
//                }
//
//
//
//
//
//





                Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);
                String minute1=" ";
                String second1="";
                if(minute<10) {
                    minute1="0"+minute;
                }
                else
                {
                    minute1=""+minute;
                }
                int second = c.get(Calendar.SECOND);
                if(second<10) {
                    second1="0"+second;
                }
                else
                {
                    second1=""+second;
                }
                //当前的时间与课表的时间进行比较，然后动态刷新数据
                String currentTime=hour+":"+minute1+":"+second1;
                DateFormat df = new SimpleDateFormat("HH:mm:ss");
                try {
                    Date currentTimeforDate = df.parse(currentTime);
                    int flag=0;
                    Log.e("zxj","当前的课程数据"+lessonTables.size()+"");


                timeforbegins.clear();
                timeforends.clear();
                    DateFormat df1 = new SimpleDateFormat("HH:mm:ss");
                    for(int i=0;i<lessonTables.size();i++)
                    {
                        try {
                            Date dateforbegin=df1.parse(lessonTables.get(i).getCourse_beginning_time());
                            Date dateforend=df1.parse(lessonTables.get(i).getCourse_ending_time());
                            timeforbegins.add(dateforbegin);
                            timeforends.add(dateforend);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }



                    for(int i=0;i<lessonTables.size();i++)
                    {
                        if (timeforbegins.get(i).getTime()<currentTimeforDate.getTime()&&currentTimeforDate.getTime()<timeforends.get(i).getTime()) {
                            Intent intent = new Intent();
                            String j=i+"";
                            intent.putExtra("Time",j);
                            intent.setAction("com.Time");
                            sendBroadcast(intent);
                            flag=1;
                            break;
                        }
                    }
                    if(flag==0)
                    {
                        Intent intent = new Intent();
                        intent.putExtra("Time","1314520");
                        intent.setAction("com.Time");
                        sendBroadcast(intent);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    @Override
    public void onDestroy() {
        flagToStopThread=1;
//        Log.i("zxj", "销毁定时器操作");
        super.onDestroy();
    }
}
