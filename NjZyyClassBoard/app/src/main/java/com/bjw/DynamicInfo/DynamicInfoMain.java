package com.bjw.DynamicInfo;

/**
 * 创建人：wxdn
 * 创建时间：2017/10/30
 * 功能描述：
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bjw.Adapter.FragmentAdapter;
import com.bjw.R;
import com.bjw.bean.LabRoomInfo;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.bjw.Common.Connection.getConnection;
import static com.bjw.Common.StaticConfig.AndroidId;
import static com.bjw.Common.StaticConfig.IP;
import static com.bjw.Common.StaticConfig.LabID;
import static com.bjw.Common.StaticConfig.MacAddress;
import static com.bjw.Common.StaticConfig.remoteUrl;

public class DynamicInfoMain extends Fragment {
    static BroadcastReceiverToChangeTime broadcastReceiverToChangeTime;
    ImageView img,underimg1,underimg2,underimg3;
    private List<Fragment> mFragmentList1 = new ArrayList<Fragment>();
    private ViewPager rightmPageVp;
    private FragmentAdapter mFragmentAdapter1;
    LessonFragment lessonFragment = new LessonFragment();
    MessageFragment messageFragment=new MessageFragment();
    TableFragment tableFragment=new TableFragment();
    TextView lessonTv,messageTv,tableTv;
    TextView datetime,weathertime;
    TextView labname,lab_introduct;
    int flag=0;//来标志当前是否获取到实验室信息
    String labintroduct="";//实验室介绍的字符串
    String stringforLabname="";//实验室名字的字符串
    List<String>imageList=new ArrayList<>();//图片地址的集合
    Map<String,Object> map = new HashMap<String,Object>() ;//保存实验室图片与对应的URL的hashMap，用来实现点击切换的功能
    /*************************************************
     *@description： 解析获取到的实验室的信息
     *************************************************/
    public Handler handlerForlabRoomInfo = new Handler() {
        public void handleMessage(Message msg) {
            String response = msg.obj.toString();
            String labroominfo1=null;
            try {
                JSONObject jsonObject = new JSONObject(response.toString());
                labroominfo1 = jsonObject.getString("result");
            } catch (JSONException e) {
                // TODO 自动生成的 catch 块
                e.printStackTrace();
            }
            if(labroominfo1!=null){
                Gson gson = new Gson();
                JsonParser parser = new JsonParser();
                JsonArray Jarray = parser.parse(labroominfo1).getAsJsonArray();
                LabRoomInfo labRoomInfo = null;
                String managers="";
                for(JsonElement obj : Jarray ){
                    labRoomInfo = gson.fromJson(obj , LabRoomInfo.class);
                    labname.setText(labRoomInfo.getLab_name());
                    stringforLabname=labRoomInfo.getLab_name();
//                    不为空的时候将其加入到里面
                    if(labRoomInfo.getManager_name()!=null) {
//                        managers = labRoomInfo.getManager_name() + " " + managers;
                          managers = labRoomInfo.getManager_name();
                    }
                    if(labRoomInfo.getLab_image_url()!=null) {
                        imageList.add(labRoomInfo.getLab_image_url());
                    }
                }
                if(managers.equals("")) {
                    lab_introduct.setText("面积：" + labRoomInfo.getLab_room_area() + "平方米 容量：" + labRoomInfo.getLab_room_capacity() + "人 管理员：当前没有管理员");
                    labintroduct="面积：" + labRoomInfo.getLab_room_area() + "平方米 容量：" + labRoomInfo.getLab_room_capacity() + "人 管理员：当前没有管理员";
                }
                else
                {
                    lab_introduct.setText("面积：" + labRoomInfo.getLab_room_area() + "平方米 容量：" + labRoomInfo.getLab_room_capacity() + "人 管理员：" + managers);
                    labintroduct="面积：" + labRoomInfo.getLab_room_area() + "平方米 容量：" + labRoomInfo.getLab_room_capacity() + "人 管理员：当前没有管理员";
                }
//                加载图片
//                if(imageList.size()>0)
//                {
//                    int a308_1 = R.drawable.a308_1;
//                    Glide.with(getActivity()).load(a308_1).into(img);
//                    int a308_2 = R.drawable.a308_2;
//                    Glide.with(getActivity()).load(a308_2).into(underimg1);
//                    int b105_3 = R.drawable.b105_3;
//                    Glide.with(getActivity()).load(b105_3).into(underimg2);
//                    int b105_4 = R.drawable.b105_4;
//                    Glide.with(getActivity()).load(b105_4).into(underimg3);
//                    map.put("img",R.drawable.a308_1+"") ;
//                    map.put("underimg1",R.drawable.a308_2+"") ;
//                    map.put("underimg2",R.drawable.b105_3+"" ) ;
//                    map.put("underimg3",R.drawable.b105_4+"") ;
//                    Glide.with(getActivity()).load("http://p3.so.qhimgs1.com/bdr/_240_/t01a37ecdefed1d2da3.jpg").into(underimg1);
//                    Glide.with(getActivity()).load("http://p0.so.qhimgs1.com/bdr/_240_/t0194d4d894aaaad6e4.jpg").into(underimg2);
//                    Glide.with(getActivity()).load("http://p0.so.qhmsg.com/bdr/_240_/t013e1bc29ac973da4f.jpg").into(underimg3);
//                    map.put("img","http://p2.so.qhimgs1.com/bdr/_240_/t01b445d5af0f09feae.jpg" ) ;
//                    map.put("underimg1","http://p3.so.qhimgs1.com/bdr/_240_/t01a37ecdefed1d2da3.jpg") ;
//                    map.put("underimg2","http://p0.so.qhimgs1.com/bdr/_240_/t0194d4d894aaaad6e4.jpg" ) ;
//                    map.put("underimg3","http://p0.so.qhmsg.com/bdr/_240_/t013e1bc29ac973da4f.jpg") ;
//                    if(imageList.size()>3) {
//                        Glide.with(getActivity()).load(imageUrl + imageList.get(0)).into(img);
//                        Glide.with(getActivity()).load(imageUrl + imageList.get(1)).into(underimg1);
//                        Glide.with(getActivity()).load(imageUrl + imageList.get(2)).into(underimg2);
//                        Glide.with(getActivity()).load(imageUrl + imageList.get(3)).into(underimg3);
//                    }
//                    else if(imageList.size()==3)
//                    {
//                        Glide.with(getActivity()).load(imageUrl + imageList.get(0)).into(img);
//                        Glide.with(getActivity()).load(imageUrl + imageList.get(1)).into(underimg1);
//                        Glide.with(getActivity()).load(imageUrl + imageList.get(2)).into(underimg2);
//                    }
//                    else if(imageList.size()==2)
//                    {
//                        Glide.with(getActivity()).load(imageUrl + imageList.get(0)).into(img);
//                        Glide.with(getActivity()).load(imageUrl + imageList.get(1)).into(underimg1);
//                    }
//                    else if(imageList.size()==1)
//                    {
//                        Glide.with(getActivity()).load(imageUrl + imageList.get(0)).into(img);
//                    }
//                }
                flag=1;
            }else{
                Toast.makeText(getContext(), "当前无该实验室的信息", Toast.LENGTH_SHORT).show();
            }
        }
    };
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View chatView = inflater.inflate(R.layout.fragment_top_dynamic, container, false);
        return chatView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
    @Override
    public void onStart() {
        super.onStart();
        findViewId();
        //应急使用
        //104
        if(LabID==366) {
        int b104_1 = R.drawable.b104_1;
        Glide.with(getActivity()).load(b104_1).into(img);
        int b104_2 = R.drawable.b104_2;
        Glide.with(getActivity()).load(b104_2).into(underimg1);
        int b104_3 = R.drawable.b104_3;
        Glide.with(getActivity()).load(b104_3).into(underimg2);
        int b104_4 = R.drawable.b104_4;
        Glide.with(getActivity()).load(b104_4).into(underimg3);
        map.put("img",R.drawable.b104_1+"") ;
        map.put("underimg1", R.drawable.b104_2+"") ;
        map.put("underimg2",R.drawable.b104_3+"" ) ;
        map.put("underimg3", R.drawable.b104_4+"") ;
        }
        else if(LabID==367) {
            //105
        int b105_1 = R.drawable.b105_1;
        Glide.with(getActivity()).load(b105_1).into(img);
        int b105_2 = R.drawable.b105_2;
        Glide.with(getActivity()).load(b105_2).into(underimg1);
        int b105_3 = R.drawable.b105_3;
        Glide.with(getActivity()).load(b105_3).into(underimg2);
        int b105_4 = R.drawable.b105_4;
        Glide.with(getActivity()).load(b105_4).into(underimg3);
        map.put("img",R.drawable.b105_1+"") ;
        map.put("underimg1",R.drawable.b105_2+"") ;
        map.put("underimg2",R.drawable.b105_3+"" ) ;
        map.put("underimg3",R.drawable.b105_4+"") ;
        }
        else if(LabID==370) {
            //108
        int b108_1 = R.drawable.b108_1;
        Glide.with(getActivity()).load(b108_1).into(img);
        int b108_2 = R.drawable.b108_2;
        Glide.with(getActivity()).load(b108_2).into(underimg1);
        int b108_3 = R.drawable.b108_3;
        Glide.with(getActivity()).load(b108_3).into(underimg2);
        int b108_4 = R.drawable.b108_4;
        Glide.with(getActivity()).load(b108_4).into(underimg3);
        map.put("img",R.drawable.b108_1+"") ;
        map.put("underimg1",R.drawable.b108_2+"") ;
        map.put("underimg2", b108_3+"" ) ;
        map.put("underimg3",R.drawable.b108_4+"") ;
        }
        else if(LabID==371) {
            //109
        int b109_1 = R.drawable.b109_1;
        Glide.with(getActivity()).load(b109_1).into(img);
        int b109_2 = R.drawable.b109_2;
        Glide.with(getActivity()).load(b109_2).into(underimg1);
        int b109_3 = R.drawable.b109_3;
        Glide.with(getActivity()).load(b109_3).into(underimg2);
        int b109_4 = R.drawable.b109_4;
        Glide.with(getActivity()).load(b109_4).into(underimg3);
        map.put("img",R.drawable.b109_1+"") ;
        map.put("underimg1",R.drawable.b109_2+"") ;
        map.put("underimg2",R.drawable.b109_3+"" ) ;
        map.put("underimg3",R.drawable.b109_4+"") ;
        }
       else if(LabID==372) {
            //110
        int b110_1 = R.drawable.b110_1;
        Glide.with(getActivity()).load(b110_1).into(img);
        int b110_2 = R.drawable.b110_2;
        Glide.with(getActivity()).load(b110_2).into(underimg1);
        int b110_3 = R.drawable.b110_3;
        Glide.with(getActivity()).load(b110_3).into(underimg2);
        int b110_4 = R.drawable.b110_4;
        Glide.with(getActivity()).load(b110_4).into(underimg3);
        map.put("img",R.drawable.b110_1+"") ;
        map.put("underimg1",R.drawable.b110_2+"") ;
        map.put("underimg2",R.drawable.b110_3+"" ) ;
        map.put("underimg3",R.drawable.b110_4+"") ;
        }
        else if(LabID==375) {
            //113
        int b113_1 = R.drawable.b113_1;
        Glide.with(getActivity()).load(b113_1).into(img);
        int b113_2 = R.drawable.b113_2;
        Glide.with(getActivity()).load(b113_2).into(underimg1);
        int b113_3 = R.drawable.b113_3;
        Glide.with(getActivity()).load(b113_3).into(underimg2);
        int b113_4 = R.drawable.b113_4;
        Glide.with(getActivity()).load(b113_4).into(underimg3);
        map.put("img",R.drawable.b113_1+"") ;
        map.put("underimg1",R.drawable.b113_2+"") ;
        map.put("underimg2",R.drawable.b113_3+"" ) ;
        map.put("underimg3",R.drawable.b113_4+"") ;
        }
        else if(LabID==378) {
            //201
        int b201_1 = R.drawable.b201_1;
        Glide.with(getActivity()).load(b201_1).into(img);
        int b201_2 = R.drawable.b201_2;
        Glide.with(getActivity()).load(b201_2).into(underimg1);
        int b201_3 = R.drawable.b201_3;
        Glide.with(getActivity()).load(b201_3).into(underimg2);
        int b201_4 = R.drawable.b201_4;
        Glide.with(getActivity()).load(b201_4).into(underimg3);
        map.put("img",R.drawable.b201_1+"") ;
        map.put("underimg1",R.drawable.b201_2+"") ;
        map.put("underimg2",R.drawable.b201_3+"" ) ;
        map.put("underimg3",R.drawable.b201_4+"") ;
        }
        else if(LabID== 380) {
            //204
        int b204_1 = R.drawable.b204_1;
        Glide.with(getActivity()).load(b204_1).into(img);
        int b204_2 = R.drawable.b204_2;
        Glide.with(getActivity()).load(b204_2).into(underimg1);
        int b204_3 = R.drawable.b204_3;
        Glide.with(getActivity()).load(b204_3).into(underimg2);
        int b204_4 = R.drawable.b204_4;
        Glide.with(getActivity()).load(b204_4).into(underimg3);
        map.put("img",R.drawable.b204_1+"") ;
        map.put("underimg1",R.drawable.b204_2+"") ;
        map.put("underimg2",R.drawable.b204_3+"" ) ;
        map.put("underimg3",R.drawable.b204_4+"") ;
        }
        else if(LabID==383) {
//        //207
        int b207_1 = R.drawable.b207_1;
        Glide.with(getActivity()).load(b207_1).into(img);
        int b207_2 = R.drawable.b207_2;
        Glide.with(getActivity()).load(b207_2).into(underimg1);
        int b207_3 = R.drawable.b207_3;
        Glide.with(getActivity()).load(b207_3).into(underimg2);
        int b207_4 = R.drawable.b207_4;
        Glide.with(getActivity()).load(b207_4).into(underimg3);
        map.put("img",R.drawable.b207_1+"") ;
        map.put("underimg1",R.drawable.b207_2+"") ;
        map.put("underimg2",R.drawable.b207_3+"" ) ;
        map.put("underimg3",R.drawable.b207_4+"") ;
        }
          else  if(LabID==384) {
            //208
            int b208_1 = R.drawable.b208_1;
            Glide.with(getActivity()).load(b208_1).into(img);
            int b208_2 = R.drawable.b208_2;
            Glide.with(getActivity()).load(b208_2).into(underimg1);
            int b208_3 = R.drawable.b208_3;
            Glide.with(getActivity()).load(b208_3).into(underimg2);
            int b208_4 = R.drawable.b208_4;
            Glide.with(getActivity()).load(b208_4).into(underimg3);
            map.put("img", R.drawable.b208_1 + "");
            map.put("underimg1", R.drawable.b208_2 + "");
            map.put("underimg2", R.drawable.b208_3 + "");
            map.put("underimg3", R.drawable.b208_4 + "");
        }
        else  if(LabID==386) {
            //210
            int b210_1 = R.drawable.b210_1;
            Glide.with(getActivity()).load(b210_1).into(img);
            int b210_2 = R.drawable.b210_2;
            Glide.with(getActivity()).load(b210_2).into(underimg1);
            int b210_3 = R.drawable.b210_3;
            Glide.with(getActivity()).load(b210_3).into(underimg2);
            int b210_4 = R.drawable.b210_4;
            Glide.with(getActivity()).load(b210_4).into(underimg3);
            map.put("img", R.drawable.b210_1 + "");
            map.put("underimg1", R.drawable.b210_2 + "");
            map.put("underimg2", R.drawable.b210_3 + "");
            map.put("underimg3", R.drawable.b210_4 + "");
        }
        else  if(LabID==387) {
            //211
            int b211_1 = R.drawable.b211_1;
            Glide.with(getActivity()).load(b211_1).into(img);
            int b211_2 = R.drawable.b211_2;
            Glide.with(getActivity()).load(b211_2).into(underimg1);
            int b211_3 = R.drawable.b211_3;
            Glide.with(getActivity()).load(b211_3).into(underimg2);
            int b211_4 = R.drawable.b211_4;
            Glide.with(getActivity()).load(b211_4).into(underimg3);
            map.put("img", b211_1 + "");
            map.put("underimg1", b211_2 + "");
            map.put("underimg2", b211_3 + "");
            map.put("underimg3", b211_4 + "");
        }
        else  if(LabID==391) {
            //215
            int b215_1 = R.drawable.b215_1;
            Glide.with(getActivity()).load(b215_1).into(img);
            int b215_2 = R.drawable.b215_2;
            Glide.with(getActivity()).load(b215_2).into(underimg1);
            int b215_3 = R.drawable.b215_3;
            Glide.with(getActivity()).load(b215_3).into(underimg2);
            int b215_4 = R.drawable.b215_4;
            Glide.with(getActivity()).load(b215_4).into(underimg3);
            map.put("img", b215_1 + "");
            map.put("underimg1", b215_2 + "");
            map.put("underimg2", b215_3 + "");
            map.put("underimg3", b215_4 + "");
        }
        changeImage();
//        获取一些必要的地址
         IP=getlocalip();
        WifiManager wifi = (WifiManager) getActivity().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        MacAddress = info.getMacAddress();
        AndroidId = Settings.System.getString(getActivity().getContentResolver(), Settings.System.ANDROID_ID);
        //获取实验室的信息,如果之前有数据就直接显示，没有数据就去服务器获取数据
        if(flag==0){
            try {
              URL url = new URL(remoteUrl+"labRoomInfo/labroomDetail/"+LabID);
                getConnection(url,handlerForlabRoomInfo);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        else
        {
            lab_introduct.setText(labintroduct);
            labname.setText(stringforLabname);
//            Glide.with(getActivity()).load("http://p2.so.qhimgs1.com/bdr/_240_/t01b445d5af0f09feae.jpg").into(img);
//            Glide.with(getActivity()).load("http://p3.so.qhimgs1.com/bdr/_240_/t01a37ecdefed1d2da3.jpg").into(underimg1);
//            Glide.with(getActivity()).load("http://p0.so.qhimgs1.com/bdr/_240_/t0194d4d894aaaad6e4.jpg").into(underimg2);
//            Glide.with(getActivity()).load("http://p0.so.qhmsg.com/bdr/_240_/t013e1bc29ac973da4f.jpg").into(underimg3);
//            if(imageList.size()>3) {
//                Glide.with(getActivity()).load(imageUrl + imageList.get(0)).into(img);
//                Glide.with(getActivity()).load(imageUrl + imageList.get(1)).into(underimg1);
//                Glide.with(getActivity()).load(imageUrl + imageList.get(2)).into(underimg2);
//                Glide.with(getActivity()).load(imageUrl + imageList.get(3)).into(underimg3);
//            }
//            else if(imageList.size()==3)
//            {
//                Glide.with(getActivity()).load(imageUrl + imageList.get(0)).into(img);
//                Glide.with(getActivity()).load(imageUrl + imageList.get(1)).into(underimg1);
//                Glide.with(getActivity()).load(imageUrl + imageList.get(2)).into(underimg2);
//            }
//            else if(imageList.size()==2)
//            {
//                Glide.with(getActivity()).load(imageUrl + imageList.get(0)).into(img);
//                Glide.with(getActivity()).load(imageUrl + imageList.get(1)).into(underimg1);
//            }
//            else if(imageList.size()==1)
//            {
//                Glide.with(getActivity()).load(imageUrl + imageList.get(0)).into(img);
//            }
        }
        if(broadcastReceiverToChangeTime==null) {
            registerForChangeTime();
        }
        mFragmentList1.add(lessonFragment);
        mFragmentList1.add(messageFragment);
        mFragmentList1.add(tableFragment);
        mFragmentAdapter1 = new FragmentAdapter(getChildFragmentManager(), mFragmentList1);
        rightmPageVp.setAdapter(mFragmentAdapter1);
        rightmPageVp.setCurrentItem(0);
//        点击事件的切换处理
        lessonTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                mFragmentAdapter1 = new FragmentAdapter(getChildFragmentManager(), mFragmentList1);
//                rightmPageVp.setAdapter(mFragmentAdapter1);
                resetColor();
                lessonTv.setBackgroundColor(Color.parseColor("#0f2740"));
                rightmPageVp.setCurrentItem(0);
            }
        });
        messageTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                mFragmentAdapter1 = new FragmentAdapter(getChildFragmentManager(), mFragmentList1);
//                rightmPageVp.setAdapter(mFragmentAdapter1);
                resetColor();
                messageTv.setBackgroundColor(Color.parseColor("#0f2740"));
                rightmPageVp.setCurrentItem(1);
            }
        });
        tableTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                mFragmentAdapter1 = new FragmentAdapter(getChildFragmentManager(), mFragmentList1);
//                rightmPageVp.setAdapter(mFragmentAdapter1);
                resetColor();
                tableTv.setBackgroundColor(Color.parseColor("#0f2740"));
                rightmPageVp.setCurrentItem(2);
            }
        });
        rightmPageVp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrollStateChanged(int state) {
            }
            @Override
            public void onPageScrolled(int position, float offset,
                                       int offsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                resetColor();
                switch (position) {
                    case 0:
                        lessonTv.setBackgroundColor(Color.parseColor("#0f2740"));
                        break;
                    case 1:
                        messageTv.setBackgroundColor(Color.parseColor("#0f2740"));
                        break;
                    case 2:
                        tableTv.setBackgroundColor(Color.parseColor("#0f2740"));
                        break;
                    default:
                        break;
                }
            }
        });
    }
    /*************************************************
     *@description： 得到IP地址
    *************************************************/
    private String getlocalip(){
        WifiManager wifiManager = (WifiManager)getActivity().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ipAddress = wifiInfo.getIpAddress();
        if(ipAddress==0)
            return "未连接wifi";
        return ((ipAddress & 0xff)+"."+(ipAddress>>8 & 0xff)+"."
                +(ipAddress>>16 & 0xff)+"."+(ipAddress>>24 & 0xff));
    }
    /*************************************************
     *@description： 控件绑定
    *************************************************/
    public void findViewId()
    {
        rightmPageVp=(ViewPager)getActivity().findViewById(R.id.change_right_tab);
        lessonTv=(TextView) getActivity().findViewById(R.id.lesson);
        messageTv=(TextView) getActivity().findViewById(R.id.message);
        tableTv=(TextView) getActivity().findViewById(R.id.table);
        img=(ImageView)getActivity().findViewById(R.id.mainimg);
        underimg1=(ImageView)getActivity().findViewById(R.id.under_img1);
        underimg2=(ImageView)getActivity().findViewById(R.id.under_img2);
        underimg3=(ImageView)getActivity().findViewById(R.id.under_img3);
        datetime=(TextView)getActivity().findViewById(R.id.datetime);
        weathertime=(TextView)getActivity().findViewById(R.id.weathertime);
        labname=(TextView)getActivity().findViewById(R.id.labname);
        lab_introduct=(TextView)getActivity().findViewById(R.id.lab_introduct);
    }
    /*************************************************
     *@description： 图片的切换点击时间的监听
     *************************************************/
    public void changeImage()
    {
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        underimg1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                  Glide.with(getActivity()).load(Integer.parseInt(map.get("underimg1").toString())).into(img);
                  Glide.with(getActivity()).load(Integer.parseInt(map.get("img").toString())).into(underimg1);
                  String rpforunderimg1=map.get("img").toString();
                  String rpforimg=map.get("underimg1").toString();
                  map.put("img",rpforimg);
                  map.put("underimg1",rpforunderimg1);
            }
        });
        underimg2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Glide.with(getActivity()).load(Integer.parseInt(map.get("underimg2").toString())).into(img);
                Glide.with(getActivity()).load(Integer.parseInt(map.get("img").toString())).into(underimg2);
                String rpforunderimg2=map.get("img").toString();
                String rpforimg=map.get("underimg2").toString();
                map.put("img",rpforimg);
                map.put("underimg2",rpforunderimg2);
            }
        });
        underimg3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Glide.with(getActivity()).load(Integer.parseInt(map.get("underimg3").toString())).into(img);
                Glide.with(getActivity()).load(Integer.parseInt(map.get("img").toString())).into(underimg3);
                String rpforunderimg3=map.get("img").toString();
                String rpforimg=map.get("underimg3").toString();
                map.put("img",rpforimg);
                map.put("underimg3",rpforunderimg3);
            }
        });
    }
    /*************************************************
     *@description： 重新设计背景颜色
    *************************************************/
    public void resetColor()
    {
        messageTv.setBackgroundColor(Color.parseColor("#254161"));
        tableTv.setBackgroundColor(Color.parseColor("#254161"));
        lessonTv.setBackgroundColor(Color.parseColor("#254161"));
    }
    /*************************************************
     *@description： 注册得定时器的广播
     *************************************************/
    public void registerForChangeTime()
    {
        broadcastReceiverToChangeTime=new BroadcastReceiverToChangeTime();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(1);
        filter.addAction("com.ChangeTime");
        getActivity().registerReceiver(broadcastReceiverToChangeTime, filter);
    }
    /*************************************************
     *@description： 定时操作的广播接收器
     *************************************************/
    public class BroadcastReceiverToChangeTime extends BroadcastReceiver
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            String time=intent.getStringExtra("currentTime");
            datetime.setText(time);
        }
    }
    @Override
    public void onPause()
    {
        super.onPause();
    }
    @Override
    public void onStop()
    {
        super.onStop();
        mFragmentList1.clear();
    }
    @Override
    public void onDestroy()
    {
        broadcastReceiverToChangeTime=null;
        super.onDestroy();
    }
}