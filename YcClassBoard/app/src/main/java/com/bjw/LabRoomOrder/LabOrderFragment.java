package com.bjw.LabRoomOrder;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.bjw.Adapter.LessonTableListAdapter;
import com.bjw.Common.ScreenSizeUtils;
import com.bjw.LabReport.ChartUtils;
import com.bjw.R;
import com.bjw.bean.LabReport;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;
import java.util.List;

import static com.bjw.Common.StaticConfig.lessonTables;

/**
 * 创建人：wxdn
 * 创建时间：2017/11/2
 * 功能描述：
 */

public class LabOrderFragment extends Fragment {
    LineChart mLineChart1,mLineChart2,mLineChart3;
    Button bt_submit;
    private ListView listView,listView1,listView2;
    private ArrayList<String> list1;
    ArrayList<ArrayList<String>> lists = new ArrayList<ArrayList<String>>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View chatView = inflater.inflate(R.layout.fragment_labroom_order, container, false);
        return chatView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public void onStart() {
        super.onStart();
//        btTest.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                DatebaseHelper myDBHelper;
//                LessonTable lessonTable5 = new LessonTable(LabID, 36, 9, 10, 42, "节能减排", "TcN5", "图片地址", "李华", "9:59:00", "17:52:00", "测试班级5", "实验5", GetCurrentTime.getCurrentDate());
//                myDBHelper = new DatebaseHelper(getActivity(), databasename, null, databaseVersion);
//                final SQLiteDatabase db = myDBHelper.getReadableDatabase();
//                //将数据写入数据库里面
//                    ContentValues values = new ContentValues();
//                    values.put("lab_room_id", lessonTable5.getLab_room_id());
//                    values.put("course_present_people", lessonTable5.getCourse_present_people());
//                    values.put("start_class", lessonTable5.getStart_class());
//                    values.put("end_class", lessonTable5.getEnd_class());
//                    values.put("total_hour", lessonTable5.getTotal_hour());
//                    values.put("course_name", lessonTable5.getCourse_name());
//                    values.put("course_number", lessonTable5.getCourse_number());
//                    values.put("course_image_url", lessonTable5.getCourse_image_url());
//                    values.put("course_teacher_name", lessonTable5.getCourse_teacher_name());
//                    values.put("course_beginning_time", lessonTable5.getCourse_beginning_time());
//                    values.put("course_ending_time", lessonTable5.getCourse_ending_time());
//                    values.put("classes_number",lessonTable5.getClasses_number());
//                    values.put("course_program",lessonTable5.getCourse_program());
//                    values.put("course_date", lessonTable5.getCourse_date());
//                    db.insert("LessonTable", null, values);
//                Cursor cursor = db.query("LessonTable", null, null, null, null, null, null);
//                FileOperateUtils.ExportToCSV(cursor, "实验室课程文件.csv");
//                cursor.close();
//                lessonTables.add(lessonTable5);
//            }
//        });
        findViewId();
        lists.clear();
        ArrayList<String> list = new ArrayList<String>();
        list.add("课程");
        list.add("教师");
        list.add("上课时间");
        list.add("上课班级");
        list.add("实验项目");
        list.add("应到人数");
        //上述是第一行
        lists.add(list);
        for (int i = 0; i < lessonTables.size(); i++) {
//                这就是相应的数据
            list1 = new ArrayList<>();
            list1.add(lessonTables.get(i).getCourse_name());
            list1.add(lessonTables.get(i).getCourse_teacher_name());
            list1.add(lessonTables.get(i).getCourse_beginning_time() + "-" + lessonTables.get(i).getCourse_ending_time());
            list1.add(lessonTables.get(i).getClasses_number());
            list1.add(lessonTables.get(i).getCourse_program());
            list1.add("" + lessonTables.get(i).getCourse_present_people());
            lists.add(list1);
        }
        LessonTableListAdapter adapter = new LessonTableListAdapter(getActivity(), lists);
        listView.setAdapter(adapter);
        listView1.setAdapter(adapter);
        listView2.setAdapter(adapter);




        List<LabReport> labReportList =new ArrayList<>();
        LabReport labReport1 =new LabReport("第一周",15,921);
        LabReport labReport2 =new LabReport("第二周",16,921);
        LabReport labReport3 =new LabReport("第三周",17,921);
        LabReport labReport4 =new LabReport("第四周",20,921);
        LabReport labReport5 =new LabReport("第五周",35,921);
        LabReport labReport6 =new LabReport("第六周",18,921);
        LabReport labReport7 =new LabReport("第七周",70,921);
        LabReport labReport8 =new LabReport("第八周",10,921);
        LabReport labReport9 =new LabReport("第九周",50,921);
        LabReport labReport10 =new LabReport("第十周",15,921);
        LabReport labReport11 =new LabReport("第十一周",45,921);
        labReportList.add(labReport1);
        labReportList.add(labReport2);
        labReportList.add(labReport3);
        labReportList.add(labReport4);
        labReportList.add(labReport5);
        labReportList.add(labReport6);
        labReportList.add(labReport7);
        labReportList.add(labReport8);
        labReportList.add(labReport9);
        labReportList.add(labReport10);
        labReportList.add(labReport11);
        ChartUtils.initChart(mLineChart1);
        ChartUtils.initChart(mLineChart2);
        ChartUtils.initChart(mLineChart3);
        List<List<Entry>> listArrayList = new ArrayList<>();
        List<Entry> values = new ArrayList<>();
        List<Entry> values1 = new ArrayList<>();
        int totalnumber=0;
        for(int i = 0; i< labReportList.size(); i++)
        {
            values.add(new Entry(i, labReportList.get(i).getWeekStudentNum()));
            totalnumber=totalnumber+ labReportList.get(i).getWeekStudentNum();
        }
        listArrayList.add(values);
        int averageNum=totalnumber/ labReportList.size();
        for(int j = 0; j< labReportList.size(); j++)
        {
            values1.add(new Entry(j,averageNum));
        }
        listArrayList.add(values1);
        ChartUtils.notifyDataSetChanged(mLineChart1, listArrayList, ChartUtils.dayValue);
        ChartUtils.notifyDataSetChanged(mLineChart2, listArrayList, ChartUtils.dayValue);
        ChartUtils.notifyDataSetChanged(mLineChart3, listArrayList, ChartUtils.dayValue);

        bt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customDialog();
            }
        });

    }

    /*************************************************
     *@description： 控件绑定
     *************************************************/
    private void findViewId() {
        listView = (ListView) getActivity().findViewById(R.id.listview1);
        listView1=(ListView) getActivity().findViewById(R.id.listview2);
        listView2=(ListView) getActivity().findViewById(R.id.listview3);
        mLineChart1 = (LineChart) getActivity().findViewById(R.id.chart1);
        mLineChart2 = (LineChart) getActivity().findViewById(R.id.chart2);
        mLineChart3 = (LineChart) getActivity().findViewById(R.id.chart3);
        bt_submit=(Button)getActivity().findViewById(R.id.bt_submit);
    }


    /*************************************************
     *@description： 自定义对话框
    *************************************************/
    private void customDialog() {
        final Dialog dialog = new Dialog(getContext(), R.style.NormalDialogStyle);
        View view = View.inflate(getContext(), R.layout.dialog_selfdefine, null);
        TextView cancel = (TextView) view.findViewById(R.id.cancel);
        TextView confirm = (TextView) view.findViewById(R.id.confirm);
        dialog.setContentView(view);
        //使得点击对话框外部不消失对话框
        dialog.setCanceledOnTouchOutside(false);
        //设置对话框的大小
        view.setMinimumHeight((int) (ScreenSizeUtils.getInstance(getContext()).getScreenHeight() * 0.23f));
        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
//        lp.width = (int) (ScreenSizeUtils.getInstance(getContext()).getScreenWidth() * 0.75f);
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        dialogWindow.setAttributes(lp);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        unbinder.unbind();
    }
}