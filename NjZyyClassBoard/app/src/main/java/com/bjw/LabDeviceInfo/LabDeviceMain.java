package com.bjw.LabDeviceInfo;

/**
 * 创建人：wxdn
 * 创建时间：2017/10/30
 * 功能描述：
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bjw.R;
import com.bjw.bean.LabDeviceMainBean;
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
import java.util.List;

import static com.bjw.Common.Connection.getConnection;
import static com.bjw.Common.StaticConfig.IsRealDataForDevice;
import static com.bjw.Common.StaticConfig.LabID;
import static com.bjw.Common.StaticConfig.TestVideoPath;
import static com.bjw.Common.StaticConfig.TestVideoPath1;
import static com.bjw.Common.StaticConfig.TestVideoPath2;
import static com.bjw.Common.StaticConfig.TestVideoPath3;
import static com.bjw.Common.StaticConfig.remoteUrl;

public class LabDeviceMain extends Fragment {
    BroadcastReceiverToForceMore broadcastReceiverToForceMore;
    private LinearLayout mLinear;
    public static List<LabDeviceMainBean>labDeviceMainBeanList=new ArrayList<>();//保存实验室信息的List
    public static int flag=0;
    public static boolean isGetFromInternet=false;//第一次是从网络上面获取，第二次就不是了
    public static boolean isBackFromChild =false;//用来判断是否是从第二季页面跳转回来的
    /*************************************************
     *@description： 处理获取的实验室设备信息
    *************************************************/
    Handler handlerForlabRoomDeviceInfo = new Handler() {
        public void handleMessage(Message msg) {
            String response = msg.obj.toString();
            String labroomdevicesinfo=null;
            try {
                JSONObject jsonObject = new JSONObject(response.toString());
                labroomdevicesinfo = jsonObject.getString("result");
            } catch (JSONException e) {
                // TODO 自动生成的 catch 块
                e.printStackTrace();
            }
            if(labroomdevicesinfo!=null) {
                Gson gson = new Gson();
                JsonParser parser = new JsonParser();
                JsonArray Jarray = parser.parse(labroomdevicesinfo).getAsJsonArray();
                LabDeviceMainBean labDeviceMainBean = null;
                for (JsonElement obj : Jarray) {
                    labDeviceMainBean = gson.fromJson(obj, LabDeviceMainBean.class);
                    labDeviceMainBeanList.add(labDeviceMainBean);
                }
                addView();
                isGetFromInternet=false;
            }else{
                Toast.makeText(getContext(), "当前实验室没有设备", Toast.LENGTH_SHORT).show();
            }
        }
    };
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState){
        super.onCreateView(inflater, container, savedInstanceState);
        View chatView = inflater.inflate(R.layout.fragment_top_second, container,false);
        return chatView;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
    }
    public void onStart()
    {
        super.onStart();
        findViewId();
        registerForForceMore();
        initData();
    }
    private void findViewId()
    {
        mLinear = (LinearLayout) getActivity().findViewById(R.id.linear);
    }
    /*************************************************
     *@description： 处理数据
    *************************************************/
    private void initData() {
        if(labDeviceMainBeanList.size()==0) {
            //如果没有数据去服务器端获取数据
            /*************************************************
             *@description： 真实数据的获取
             *************************************************/
            if (IsRealDataForDevice) {
                    try {
                        URL url = new URL(remoteUrl + "labRoomInfo/listLabdevice/" + LabID);
                        getConnection(url, handlerForlabRoomDeviceInfo);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    if (!isBackFromChild) {
                        addView();
                    } else {
                        isBackFromChild = false;
                    }
            }
            else {
                /*************************************************
                 *@description： 使用假数据
                 *************************************************/
                    LabDeviceMainBean labDeviceMainBean1 = new LabDeviceMainBean(LabID, 1, "气动设备", "http://f.hiphotos.baidu.com/baike/c0%3Dbaike60%2C5%2C5%2C60%2C20%3Bt%3Dgif/sign=a7c7151f552c11dfcadcb771024e09b5/810a19d8bc3eb135f6e46547a41ea8d3fd1f442f.jpg", "厂家一", "非常非常非常唱的简介非常非常非常唱的简介非常非常非常唱的简介非常非常非常唱的简介非常非常非常唱的简介非常非常非常唱的简介", TestVideoPath1);
                    LabDeviceMainBean labDeviceMainBean2 = new LabDeviceMainBean(LabID, 2, "微电脑插拔力试验机", "http://www.xmkeli.cn/imageRepository/2f74b761-be67-470c-a724-77a65643b4a3.jpg", "厂家二", "非常非常非常唱的简介非常非常非常唱的简介非常非常非常唱的简介非常非常非常唱的简介非常非常非常唱的简介非常非常非常唱的简介", TestVideoPath2);
                    LabDeviceMainBean labDeviceMainBean3 = new LabDeviceMainBean(LabID, 3, "设备三", "http://file.youboy.com/d/153/89/78/3/137143s.jpg", "厂家三", "非常非常非常唱的简介非常非常非常唱的简介非常非常非常唱的简介非常非常非常唱的简介非常非常非常唱的简介非常非常非常唱的简介", TestVideoPath3);
                    LabDeviceMainBean labDeviceMainBean4 = new LabDeviceMainBean(LabID, 4, "设备四", "http://file1.youboy.com/a/86/4/36/7/10385167.jpg", "厂家四", "非常非常非常唱的简介非常非常非常唱的简介非常非常非常唱的简介非常非常非常唱的简介非常非常非常唱的简介非常非常非常唱的简介", TestVideoPath);
                    LabDeviceMainBean labDeviceMainBean5 = new LabDeviceMainBean(LabID, 5, "设备五", "http://img2.imgtn.bdimg.com/it/u=1463976287,3744399650&fm=214&gp=0.jpg", "厂家五", "非常非常非常唱的简介非常非常非常唱的简介非常非常非常唱的简介非常非常非常唱的简介非常非常非常唱的简介非常非常非常唱的简介", TestVideoPath);
                    LabDeviceMainBean labDeviceMainBean6 = new LabDeviceMainBean(LabID, 6, "设备六", "http://img2.imgtn.bdimg.com/it/u=2565928167,1042035775&fm=214&gp=0.jpg", "厂家六", "非常非常非常唱的简介非常非常非常唱的简介非常非常非常唱的简介非常非常非常唱的简介非常非常非常唱的简介非常非常非常唱的简介", TestVideoPath);
                    LabDeviceMainBean labDeviceMainBean7 = new LabDeviceMainBean(LabID, 7, "设备七", "http://img.gtimg.c-ps.net/info/big/2015/5/13/20155130922005868.jpg", "厂家七", "非常非常非常唱的简介非常非常非常唱的简介非常非常非常唱的简介非常非常非常唱的简介非常非常非常唱的简介非常非常非常唱的简介", TestVideoPath);
                    LabDeviceMainBean labDeviceMainBean8 = new LabDeviceMainBean(LabID, 8, "设备八", "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1515216031825&di=427d8329048edee4599c5b188cb7abc1&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimgad%2Fpic%2Fitem%2Fb90e7bec54e736d1a770573490504fc2d56269af.jpg", "厂家八", "非常非常非常唱的简介非常非常非常唱的简介非常非常非常唱的简介非常非常非常唱的简介非常非常非常唱的简介非常非常非常唱的简介", TestVideoPath);
                    LabDeviceMainBean labDeviceMainBean9 = new LabDeviceMainBean(LabID, 9, "设备九", "http://file5.youboy.com/a/45/44/96/1/7755371.jpg", "厂家九", "非常非常非常唱的简介非常非常非常唱的简介非常非常非常唱的简介非常非常非常唱的简介非常非常非常唱的简介非常非常非常唱的简介", TestVideoPath);
                    labDeviceMainBeanList.add(labDeviceMainBean1);
                    labDeviceMainBeanList.add(labDeviceMainBean2);
                    labDeviceMainBeanList.add(labDeviceMainBean3);
                    labDeviceMainBeanList.add(labDeviceMainBean4);
                    labDeviceMainBeanList.add(labDeviceMainBean5);
                    labDeviceMainBeanList.add(labDeviceMainBean6);
                    labDeviceMainBeanList.add(labDeviceMainBean7);
                    labDeviceMainBeanList.add(labDeviceMainBean8);
                    labDeviceMainBeanList.add(labDeviceMainBean9);
                }
            //数据拿到之后去根据数据去动态添加View
            if (flag == 0) {
                addView();
            } else {
                flag = 0;
            }
        }
        //设备数据不为空，则直接添加相应的界面，不用去服务器端获取了
        else
        {
            //数据拿到之后去根据数据去动态添加View
            if (flag == 0) {
                addView();
            } else {
                flag = 0;
            }
        }
    }
 /*************************************************
  *@description： 添加相应的先横向的View，后纵向的VIew添加
 *************************************************/
    private void addView() {
         View viewHorizal;
         LinearLayout linearLayout;
        LinearLayout linearLayoutforTemp = null;
        //ivList集合有几个元素就添加几个
        for (int i = 0; i < labDeviceMainBeanList.size(); i++) {
            //引入水平的 Horizal
            if(i%6==0) {
                viewHorizal = View.inflate(getContext(), R.layout.second_horizal_item, null);
                linearLayout=(LinearLayout)viewHorizal.findViewById(R.id.second_h_item);
                linearLayoutforTemp=linearLayout;
                View view = View.inflate(getContext(), R.layout.second_item, null);
                //找到里面需要动态改变的控件
                ImageView ivLogo1 = (ImageView) view.findViewById(R.id.iv_logo);
                Button bt_operate = (Button) view.findViewById(R.id.bt_operate);
                Button bt_video = (Button) view.findViewById(R.id.bt_video);
                //将图片载入到相应的ImageView界面
                if(labDeviceMainBeanList.get(i).getLab_device_image_url()!=null) {
                    Glide.with(getActivity()).load(labDeviceMainBeanList.get(i).getLab_device_image_url()).into(ivLogo1);}
                //设置每个View里面的相关操作是手册和相关的视频的点击事件
                final int finalI1 = i;
                bt_operate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(getActivity(),LabDeviceOperate.class);
                        intent.putExtra("LabDeviceId",labDeviceMainBeanList.get(finalI1).getLab_device_id());
                        intent.putExtra("LabDeviceName",labDeviceMainBeanList.get(finalI1).getLab_device_name());
                        intent.putExtra("LabDeviceImageUrl",labDeviceMainBeanList.get(finalI1).getLab_device_image_url());
                        intent.putExtra("LabDeviceSupplier",labDeviceMainBeanList.get(finalI1).getLab_device_supplier());
                        intent.putExtra("LabDeviceIntroduct",labDeviceMainBeanList.get(finalI1).getLab_device_introduct());
                        intent.putExtra("LabDeviceVideoUrl",labDeviceMainBeanList.get(finalI1).getLab_device_video_url());
                        startActivity(intent);
                    }
                });
                bt_video.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //跳转到播放的页面
                        Intent intent=new Intent(getActivity(),LabDeviceVideo.class);
                        intent.putExtra("LabDeviceId",labDeviceMainBeanList.get(finalI1).getLab_device_id());
                        intent.putExtra("LabDeviceName",labDeviceMainBeanList.get(finalI1).getLab_device_name());
                        intent.putExtra("LabDeviceImageUrl",labDeviceMainBeanList.get(finalI1).getLab_device_image_url());
                        intent.putExtra("LabDeviceSupplier",labDeviceMainBeanList.get(finalI1).getLab_device_supplier());
                        intent.putExtra("LabDeviceIntroduct",labDeviceMainBeanList.get(finalI1).getLab_device_introduct());
                        intent.putExtra("LabDeviceVideoUrl",labDeviceMainBeanList.get(finalI1).getLab_device_video_url());
                        startActivity(intent);
                    }
                });
                linearLayout.addView(view);
                mLinear.addView(linearLayout);
            }
            else
            {
                View view = View.inflate(getContext(), R.layout.second_item, null);
                //将图片载入到相应的ImageView界面
                ImageView ivLogo1 = (ImageView) view.findViewById(R.id.iv_logo);
                Button bt_operate = (Button) view.findViewById(R.id.bt_operate);
                Button bt_video = (Button) view.findViewById(R.id.bt_video);
                if(labDeviceMainBeanList.get(i).getLab_device_image_url()!=null) {
                    Glide.with(getActivity()).load(labDeviceMainBeanList.get(i).getLab_device_image_url()).into(ivLogo1);}
                //设置每个View里面的相关操作是手册和相关的视频的点击事件
                final int finalI = i;//由于OnClick里面拿不到i,所以需要重新赋值给一个final对象
                bt_operate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(getActivity(),LabDeviceOperate.class);
                        intent.putExtra("LabDeviceId",labDeviceMainBeanList.get(finalI).getLab_device_id());
                        intent.putExtra("LabDeviceName",labDeviceMainBeanList.get(finalI).getLab_device_name());
                        intent.putExtra("LabDeviceImageUrl",labDeviceMainBeanList.get(finalI).getLab_device_image_url());
                        intent.putExtra("LabDeviceSupplier",labDeviceMainBeanList.get(finalI).getLab_device_supplier());
                        intent.putExtra("LabDeviceIntroduct",labDeviceMainBeanList.get(finalI).getLab_device_introduct());
                        intent.putExtra("LabDeviceVideoUrl",labDeviceMainBeanList.get(finalI).getLab_device_video_url());
                        startActivity(intent);
                    }
                });
                bt_video.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //跳转到播放的页面
                        Intent intent=new Intent(getActivity(),LabDeviceVideo.class);
                        //将相应的数据传递到下一个界面
                        intent.putExtra("LabDeviceId",labDeviceMainBeanList.get(finalI).getLab_device_id());
                        intent.putExtra("LabDeviceName",labDeviceMainBeanList.get(finalI).getLab_device_name());
                        intent.putExtra("LabDeviceImageUrl",labDeviceMainBeanList.get(finalI).getLab_device_image_url());
                        intent.putExtra("LabDeviceSupplier",labDeviceMainBeanList.get(finalI).getLab_device_supplier());
                        intent.putExtra("LabDeviceIntroduct",labDeviceMainBeanList.get(finalI).getLab_device_introduct());
                        intent.putExtra("LabDeviceVideoUrl",labDeviceMainBeanList.get(finalI).getLab_device_video_url());
                        startActivity(intent);
                    }
                });
                linearLayoutforTemp.addView(view);
            }
        }
    }
    /*************************************************
     *@description： 注册接收返回的广播是为了防止再次动态创建布局
     *************************************************/
    public void registerForForceMore()
    {
        broadcastReceiverToForceMore=new BroadcastReceiverToForceMore();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(6);
        filter.addAction("com.BackForNotCreateMore");
        getActivity().registerReceiver(broadcastReceiverToForceMore, filter);
    }
    /*************************************************
     *@description： 定时接收返回的广播接收器
     *************************************************/
    public class BroadcastReceiverToForceMore extends BroadcastReceiver
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            flag=1;
            isBackFromChild=true;
        }

    }
    @Override
    public void onResume()
    {
        super.onResume();
//        flag=1;
    }
    @Override
    public void onPause()
    {
        super.onPause();
//        flag=1;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
