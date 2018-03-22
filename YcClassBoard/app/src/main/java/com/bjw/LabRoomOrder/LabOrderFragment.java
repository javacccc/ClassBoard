package com.bjw.LabRoomOrder;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bjw.Adapter.LessonTableListAdapter;
import com.bjw.Common.ScreenSizeUtils;
import com.bjw.LabReport.ChartUtils;
import com.bjw.R;
import com.bjw.bean.LabReport;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.bjw.Common.NetToolUtils.getConnection;
import static com.bjw.Common.StaticConfig.LabID;
import static com.bjw.Common.StaticConfig.remoteUrl;
import static com.bjw.Common.StaticConfig.isLabOrderFragment;

/**
 * 创建人：wxdn
 * 创建时间：2017/11/2
 * 功能描述：
 */

public class LabOrderFragment extends Fragment {
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.et_ordercontent)
    EditText etOrdercontent;
    @BindView(R.id.bt_submit)
    Button btSubmit;
    Unbinder unbinder;
    @BindView(R.id.sp_startclass)
    Spinner spStartclass;
    @BindView(R.id.sp_endclass)
    Spinner spEndclass;
    @BindView(R.id.listview_table1)
    ListView listviewTable1;
    @BindView(R.id.listview_table2)
    ListView listviewTable2;
    @BindView(R.id.listview_table3)
    ListView listviewTable3;
    @BindView(R.id.chart1)
    LineChart chart1;
    @BindView(R.id.chart2)
    LineChart chart2;
    @BindView(R.id.chart3)
    LineChart chart3;
    //三个表格里面的数据
    ArrayList<ArrayList<String>> listFororderTime = new ArrayList<ArrayList<String>>();
    ArrayList<ArrayList<String>> listFororderCount = new ArrayList<ArrayList<String>>();
    ArrayList<ArrayList<String>> listFororderCard = new ArrayList<ArrayList<String>>();
    String startClass = null;
    String endClass = null;
    String username = null;
    String password = null;
    BroadcastReceiverToGetCardForLabOrder broadcastReceiverToGetCardForLabOrder = null;

    private String cardNum = null;
    /*************************************************
     *@description： 解析当前的刷卡用户是否有权限进行相应的预约操作
     *************************************************/
    public Handler handlerForLabCard = new Handler() {
        public void handleMessage(Message msg) {
            String response = msg.obj.toString();
            String code = null;
            String usernametemp = null;
            try {
                JSONObject jsonObject = new JSONObject(response.toString());
                code = jsonObject.getString("code");
                usernametemp = jsonObject.getString("username");
            } catch (JSONException e) {
                // TODO 自动生成的 catch 块
                e.printStackTrace();
            }
            if (code.equals("200")) {
                if (true) {
                    if (usernametemp != null) {
                        username = usernametemp;
                    }
                    LabOrderThread labOrderThread = new LabOrderThread();
                    labOrderThread.start();
                }
            } else {
                Toast.makeText(getContext(), "当前用户验证出错", Toast.LENGTH_SHORT).show();
            }
        }
    };
    /*************************************************
     *@description： 解析当前的登陆用户是否有权限进行相应的预约操作
     *************************************************/
    public Handler handlerForLabUser = new Handler() {
        public void handleMessage(Message msg) {
            String response = msg.obj.toString();
            String code = null;
            String message = null;
            try {
                JSONObject jsonObject = new JSONObject(response.toString());
                code = jsonObject.getString("code");
                message = jsonObject.getString("message");
            } catch (JSONException e) {
                // TODO 自动生成的 catch 块
                e.printStackTrace();
            }
            if (message.equals("验证成功")) {
                if (true) {
                    LabOrderThread labOrderThread = new LabOrderThread();
                    labOrderThread.start();
                }
            } else {
                Toast.makeText(getContext(), "当前用户验证出错", Toast.LENGTH_SHORT).show();
            }
        }
    };
    /*************************************************
     *@description： 解析预约数据传入之后的数据返回
     *************************************************/
    public Handler handlerForLabOrderBack = new Handler() {
        public void handleMessage(Message msg) {
            String response = msg.obj.toString();
            String code = null;
            try {
                JSONObject jsonObject = new JSONObject(response.toString());
                code = jsonObject.getString("code");
            } catch (JSONException e) {
                // TODO 自动生成的 catch 块
                e.printStackTrace();
            }
            if (code.equals("200")) {
                Toast.makeText(getContext(), "预约成功", Toast.LENGTH_SHORT).show();
//                做一些成功之后的操作，比如刷新相应的界面
                ////                if (true) {
//////                    如果返回的数据是表示插入成功，那么就将数据插入到数据库里面然后动态的更新界面
////                    DatebaseHelper myDBHelper;
////                    LessonTable lessonTable5 = new LessonTable(LabID, 36, 9, 10, 42, "节能减排", "TcN5", "图片地址", "李华", "9:59:00", "17:52:00", "测试班级5", "实验5", GetCurrentTime.getCurrentDate());
////                    myDBHelper = new DatebaseHelper(getActivity(), databasename, null, databaseVersion);
////                    final SQLiteDatabase db = myDBHelper.getReadableDatabase();
////                    //将数据写入数据库里面
////                    ContentValues values = new ContentValues();
////                    values.put("lab_room_id", lessonTable5.getLab_room_id());
////                    values.put("course_present_people", lessonTable5.getCourse_present_people());
////                    values.put("start_class", lessonTable5.getStart_class());
////                    values.put("end_class", lessonTable5.getEnd_class());
////                    values.put("total_hour", lessonTable5.getTotal_hour());
////                    values.put("course_name", lessonTable5.getCourse_name());
////                    values.put("course_number", lessonTable5.getCourse_number());
////                    values.put("course_image_url", lessonTable5.getCourse_image_url());
////                    values.put("course_teacher_name", lessonTable5.getCourse_teacher_name());
////                    values.put("course_beginning_time", lessonTable5.getCourse_beginning_time());
////                    values.put("course_ending_time", lessonTable5.getCourse_ending_time());
////                    values.put("classes_number", lessonTable5.getClasses_number());
////                    values.put("course_program", lessonTable5.getCourse_program());
////                    values.put("course_date", lessonTable5.getCourse_date());
////                    db.insert("LessonTable", null, values);
////                    Cursor cursor = db.query("LessonTable", null, null, null, null, null, null);
////                    FileOperateUtils.ExportToCSV(cursor, "实验室课程文件.csv");
////                    cursor.close();
////                    lessonTables.add(lessonTable5);
////                }
            } else if (code.equals("500")) {
                Toast.makeText(getContext(), "当前时间段实验室被占用,请重先选择实验室", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "未知错误", Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View chatView = inflater.inflate(R.layout.fragment_labroom_order, container, false);
        unbinder = ButterKnife.bind(this, chatView);
        return chatView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public void onStart() {
        super.onStart();
        //标记为当前的页面
        isLabOrderFragment=true;
        //卡号广播的注册
        if (broadcastReceiverToGetCardForLabOrder == null) {
            registerForCardForLabOrder();
        }
        //获取下拉框里面的值
        spStartclass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                startClass = spStartclass.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        spEndclass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                endClass = spEndclass.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        //提交的时候需要判断时间当前的用户
        btSubmit.setOnClickListener(submitOnClickListener);
        //初始化三个表格的数据
        InitOrderTimeTable();
        InitOrderCountTable();
        InitOrderCardTable();
        //初始化三个折线图数据
        InitChartData();
    }

    /*************************************************
     *@description： 自定义登陆对话框来进行当前用户身份的验证
     *************************************************/
    private void customDialog() {
        final Dialog dialog = new Dialog(getContext(), R.style.NormalDialogStyle);
        View view = View.inflate(getContext(), R.layout.dialog_selfdefine, null);
        TextView cancel = (TextView) view.findViewById(R.id.cancel);
        TextView confirm = (TextView) view.findViewById(R.id.confirm);
        final EditText etUsername = (EditText) view.findViewById(R.id.et_username);
        final EditText etPassword = (EditText) view.findViewById(R.id.et_password);
        dialog.setContentView(view);
        //使得点击对话框外部不消失对话框
        dialog.setCanceledOnTouchOutside(false);
        //设置对话框的大小
        view.setMinimumHeight((int) (ScreenSizeUtils.getInstance(getContext()).getScreenHeight() * 0.23f));
        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        dialogWindow.setAttributes(lp);
        etUsername.setText("001807");
        etPassword.setText("00107");
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                去服务器端验证当前的用户是否可以进行预约
                if (true) {
                    try {
                        username = etUsername.getText().toString();
                        password = etPassword.getText().toString();
                        URL url = new URL(remoteUrl + "labRoomOrder/labUser/" + username + "/" + password);
                        getConnection(url, handlerForLabUser);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                }
                dialog.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    View.OnClickListener submitOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            customDialog();
        }
    };

    /*************************************************
     *@description： 实验室判冲和实验室预约数据的上传的线程
     *************************************************/
    private class LabOrderThread extends Thread {
        @Override
        public void run() {
            if (true) {
                try {
                    String un = username;
                    String sc = startClass;
                    String ec = endClass;
                    int ss = Integer.parseInt(sc);
                    int ee = Integer.parseInt(ec);
                    URL url = new URL(remoteUrl + "labRoomOrder/labPanchong/" + LabID + "/" + ss + "/" + ee + "/" + un);
                    getConnection(url, handlerForLabOrderBack);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    /*************************************************
     *@description： 刷卡进行相应的实验室预约
     *************************************************/
    /*************************************************
     *@description： 注册得到卡号的广播
     *************************************************/
    public void registerForCardForLabOrder() {
        broadcastReceiverToGetCardForLabOrder = new BroadcastReceiverToGetCardForLabOrder();
        IntentFilter filter1 = new IntentFilter();
        filter1.setPriority(2);
        filter1.addAction("com.getOrderCardnum");
        getActivity().registerReceiver(broadcastReceiverToGetCardForLabOrder, filter1);
    }

    /*************************************************
     *@description： 卡号信息的广播接收器同时处理相关的
     * 读卡逻辑
     *************************************************/
    public class BroadcastReceiverToGetCardForLabOrder extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            cardNum = intent.getStringExtra("cardnumforOrder");
            Toast.makeText(getContext(), cardNum, Toast.LENGTH_SHORT).show();
            CardOrderThread cardOrderThread = new CardOrderThread();
            cardOrderThread.start();
        }
    }

    /*************************************************
     *@description： 刷卡数据用户的判冲的线程
     *************************************************/
    private class CardOrderThread extends Thread {
        @Override
        public void run() {
            if (true) {
                try {
//                    cardNum="069D2B56";
                        URL url = new URL(remoteUrl + "labRoomOrder/labCardOrder/" + cardNum);
                        getConnection(url, handlerForLabCard);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /*************************************************
     *@description： 三个表格的数据的初始化
     *************************************************/
    public void InitOrderTimeTable() {
        listFororderTime.clear();
//        表头
        ArrayList<String> list = new ArrayList<String>();
        list.add("预约人");
        list.add("预约开始节次");
        list.add("预约结束节次");
        list.add("预约时长");
        listFororderTime.add(list);
//        表内容
        for (int i = 0; i < 3; i++) {
//                这就是相应的数据
            ArrayList<String> listforContent = new ArrayList<String>();
            listforContent.add("张华");
            listforContent.add("1");
            listforContent.add("2");
            listforContent.add("2");
            listFororderTime.add(listforContent);
        }
        LessonTableListAdapter adapter = new LessonTableListAdapter(getActivity(), listFororderTime);
        listviewTable1.setAdapter(adapter);
    }

    public void InitOrderCountTable() {
        listFororderCount.clear();
//        表头
        ArrayList<String> list = new ArrayList<String>();
        list.add("预约人");
        list.add("预约次数");
        list.add("预约时长");
        listFororderCount.add(list);
//        表内容
        for (int i = 0; i < 4; i++) {
//                这就是相应的数据
            ArrayList<String> listforContent = new ArrayList<String>();
            listforContent.add("张华");
            listforContent.add("2");
            listforContent.add("5小时");
            listFororderCount.add(listforContent);
        }
        LessonTableListAdapter adapter = new LessonTableListAdapter(getActivity(), listFororderCount);
        listviewTable2.setAdapter(adapter);
    }

    public void InitOrderCardTable() {
        listFororderCard.clear();
//        表头
        ArrayList<String> list = new ArrayList<String>();
        list.add("刷卡人");
        list.add("刷卡身份");
        list.add("刷卡次数");
        list.add("刷卡最后时间");
        listFororderCard.add(list);
//        表内容
        for (int i = 0; i < 10; i++) {
//                这就是相应的数据
            ArrayList<String> listforContent = new ArrayList<String>();
            listforContent.add("李明");
            listforContent.add("学生");
            listforContent.add("5");
            listforContent.add("2018-5-20");
            listFororderCard.add(listforContent);
        }
        LessonTableListAdapter adapter = new LessonTableListAdapter(getActivity(), listFororderCard);
        listviewTable3.setAdapter(adapter);
    }

    /*************************************************
     *@description： 三个折线图的数据初始化
    *************************************************/
    public void InitChartData()
    {
        List<LabReport> labReportList = new ArrayList<>();
        LabReport labReport1 = new LabReport("第一周", 15, 921);
        LabReport labReport2 = new LabReport("第二周", 16, 921);
        LabReport labReport3 = new LabReport("第三周", 17, 921);
        LabReport labReport4 = new LabReport("第四周", 20, 921);
        LabReport labReport5 = new LabReport("第五周", 35, 921);
        LabReport labReport6 = new LabReport("第六周", 18, 921);
        LabReport labReport7 = new LabReport("第七周", 70, 921);
        LabReport labReport8 = new LabReport("第八周", 10, 921);
        LabReport labReport9 = new LabReport("第九周", 50, 921);
        LabReport labReport10 = new LabReport("第十周", 15, 921);
        LabReport labReport11 = new LabReport("第十一周", 45, 921);
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
        ChartUtils.initChart(chart1);
        ChartUtils.initChart(chart2);
        ChartUtils.initChart(chart3);
        List<List<Entry>> listArrayList = new ArrayList<>();
        List<Entry> values = new ArrayList<>();
        List<Entry> values1 = new ArrayList<>();
        int totalnumber = 0;
        for (int i = 0; i < labReportList.size(); i++) {
            values.add(new Entry(i, labReportList.get(i).getWeekStudentNum()));
            totalnumber = totalnumber + labReportList.get(i).getWeekStudentNum();
        }
        listArrayList.add(values);
        int averageNum = totalnumber / labReportList.size();
        for (int j = 0; j < labReportList.size(); j++) {
            values1.add(new Entry(j, averageNum));
        }
        listArrayList.add(values1);
        ChartUtils.notifyDataSetChanged(chart1, listArrayList, ChartUtils.dayValue);
        ChartUtils.notifyDataSetChanged(chart2, listArrayList, ChartUtils.dayValue);
        ChartUtils.notifyDataSetChanged(chart3, listArrayList, ChartUtils.dayValue);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //当退出的时候给当前的页面赋予一个值，视为了在刷卡的时候进行相应的区分
        isLabOrderFragment=false;
        unbinder.unbind();
    }
}