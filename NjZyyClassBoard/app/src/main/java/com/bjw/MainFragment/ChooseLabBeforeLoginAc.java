package com.bjw.MainFragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.bjw.R;
import com.bjw.bean.AllLabRoomInfo;
import com.bjw.bean.VersionBean;
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
import static com.bjw.Common.StaticConfig.LabID;
import static com.bjw.Common.StaticConfig.remoteUrl;

/**
 * 创建人：wxdn
 * 创建时间：2017/12/9
 * 功能描述：
 */

public class ChooseLabBeforeLoginAc extends Activity{
        private GridView gview;
        private List<Map<String, Object>> data_list;
        private SimpleAdapter sim_adapter;
        String localVersionName;
        int localVersionCode;
        List<AllLabRoomInfo>allLabRoomInfoList=new ArrayList<>();
    /*************************************************
     *@description： 去服务器端获取相应的版本与本地的版本作比较
     * 数据包括，版本号，版本名称，该版本所对应的apk文件的路径
    *************************************************/
        public Handler handlerForGetVersion = new Handler() {
        public void handleMessage(Message msg) {
            String response = msg.obj.toString();
            String VersionBeans = null;
            try {
                JSONObject jsonObject = new JSONObject(response.toString());
                VersionBeans = jsonObject.getString("result");
            } catch (JSONException e) {
                // TODO 自动生成的 catch 块
                e.printStackTrace();
            }
            if (VersionBeans != null) {
                Gson gson = new Gson();
                JsonParser parser = new JsonParser();
                JsonArray Jarray = parser.parse(VersionBeans).getAsJsonArray();
                VersionBean versionBean;
                for (JsonElement obj : Jarray) {
                    versionBean = gson.fromJson(obj, VersionBean.class);
                    //取出版本号然后与相应的本地版本进行比较
                    int versionCode=versionBean.getVersionCode();
                    if(versionCode==localVersionCode)
                    {
                        Toast.makeText(getBaseContext(), "当前已是最新版本", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        //进行相应的更新操作
//                        开启下载的线程
                        String apkPath = versionBean.getApkPath();
                        //开启一个下载的服务。
                    }
                }
            }
        }
    };
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_choose_labroom);
//            检验版本更新
            CheckVersionUpdate();
            gview = (GridView) findViewById(R.id.gview);
            data_list = new ArrayList<Map<String, Object>>();
            initDataForNJ();
            String [] from ={"image","text"};
            int [] to = {R.id.image,R.id.text};
            sim_adapter = new SimpleAdapter(this, data_list, R.layout.grideview_item, from, to);
            gview.setAdapter(sim_adapter);
            //点击事件的处理
            gview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> arg0, View arg1, int idx, long arg3) {
                    // TODO Auto-generated method stub
                    LabID=allLabRoomInfoList.get(idx).getLab_room_id();
                    //将第一次进入时的LabId进行保存
                    SharedPreferences mSharedPreferences = getSharedPreferences("NJLABID", Context.MODE_PRIVATE);
                    final SharedPreferences.Editor editor = mSharedPreferences.edit();
                    editor.putInt("labId",LabID);
                    editor.commit();
                    Intent intent=new Intent(getBaseContext(),LoadingActivity.class);
                    startActivity(intent);
                }
            });
        }
        /*************************************************
         *@description： 初始化实验室数据与界面
        *************************************************/
//        public void initData()
//        {
//            AllLabRoomInfo allLabRoomInfo1=new AllLabRoomInfo(895,R.drawable.a305_1,"A305");
//            AllLabRoomInfo allLabRoomInfo2=new AllLabRoomInfo(899,R.drawable.a308_1,"A308");
//            AllLabRoomInfo allLabRoomInfo3=new AllLabRoomInfo(900,R.drawable.a309_1,"A309");
//            AllLabRoomInfo allLabRoomInfo4=new AllLabRoomInfo(904,R.drawable.a312_1,"A312");
//            AllLabRoomInfo allLabRoomInfo5=new AllLabRoomInfo(902,R.drawable.a313_1,"A311/A313");
//            AllLabRoomInfo allLabRoomInfo6=new AllLabRoomInfo(905,R.drawable.a314_1,"A314");
//            AllLabRoomInfo allLabRoomInfo7=new AllLabRoomInfo(906,R.drawable.a315_1,"A315");
//            AllLabRoomInfo allLabRoomInfo8=new AllLabRoomInfo(918,R.drawable.a410e_1,"A410西");
//            AllLabRoomInfo allLabRoomInfo9=new AllLabRoomInfo(919,R.drawable.a410w_1,"A410东");
//            AllLabRoomInfo allLabRoomInfo10=new AllLabRoomInfo(921,R.drawable.a412_1,"A412");
//            allLabRoomInfoList.add(allLabRoomInfo1);
//            allLabRoomInfoList.add(allLabRoomInfo2);
//            allLabRoomInfoList.add(allLabRoomInfo3);
//            allLabRoomInfoList.add(allLabRoomInfo4);
//            allLabRoomInfoList.add(allLabRoomInfo5);
//            allLabRoomInfoList.add(allLabRoomInfo6);
//            allLabRoomInfoList.add(allLabRoomInfo7);
//            allLabRoomInfoList.add(allLabRoomInfo8);
//            allLabRoomInfoList.add(allLabRoomInfo9);
//            allLabRoomInfoList.add(allLabRoomInfo10);
//            data_list = new ArrayList<Map<String, Object>>();
//            for(int i=0;i<allLabRoomInfoList.size();i++){
//                Map<String, Object> map = new HashMap<String, Object>();
//                map.put("image", allLabRoomInfoList.get(i).getLab_room_local_image());
//                map.put("text", allLabRoomInfoList.get(i).getLab_room_location());
//                data_list.add(map);
//            }
//        }
    public void initDataForNJ()
    {
        AllLabRoomInfo allLabRoomInfo1=new AllLabRoomInfo(366,R.drawable.b104_1,"B3104");
        AllLabRoomInfo allLabRoomInfo2=new AllLabRoomInfo(367,R.drawable.b105_1,"B3105");
        AllLabRoomInfo allLabRoomInfo3=new AllLabRoomInfo(370,R.drawable.b108_1,"B3108");
        AllLabRoomInfo allLabRoomInfo4=new AllLabRoomInfo(371,R.drawable.b109_1,"B3109");
        AllLabRoomInfo allLabRoomInfo5=new AllLabRoomInfo(372,R.drawable.b110_1,"B3110");
        AllLabRoomInfo allLabRoomInfo6=new AllLabRoomInfo(375,R.drawable.b113_1,"B3113");
        AllLabRoomInfo allLabRoomInfo7=new AllLabRoomInfo(378,R.drawable.b201_1,"B3201");
        AllLabRoomInfo allLabRoomInfo8=new AllLabRoomInfo(380,R.drawable.b204_1,"B3204");
        AllLabRoomInfo allLabRoomInfo9=new AllLabRoomInfo(383,R.drawable.b207_1,"B3207");
        AllLabRoomInfo allLabRoomInfo10=new AllLabRoomInfo(384,R.drawable.b208_1,"B3208");
        AllLabRoomInfo allLabRoomInfo11=new AllLabRoomInfo(386,R.drawable.b201_1,"B3210");
        AllLabRoomInfo allLabRoomInfo12=new AllLabRoomInfo(387,R.drawable.b211_1,"B3211");
        AllLabRoomInfo allLabRoomInfo13=new AllLabRoomInfo(391,R.drawable.b215_1,"B3215");
        allLabRoomInfoList.add(allLabRoomInfo1);
        allLabRoomInfoList.add(allLabRoomInfo2);
        allLabRoomInfoList.add(allLabRoomInfo3);
        allLabRoomInfoList.add(allLabRoomInfo4);
        allLabRoomInfoList.add(allLabRoomInfo5);
        allLabRoomInfoList.add(allLabRoomInfo6);
        allLabRoomInfoList.add(allLabRoomInfo7);
        allLabRoomInfoList.add(allLabRoomInfo8);
        allLabRoomInfoList.add(allLabRoomInfo9);
        allLabRoomInfoList.add(allLabRoomInfo10);
        allLabRoomInfoList.add(allLabRoomInfo11);
        allLabRoomInfoList.add(allLabRoomInfo12);
        allLabRoomInfoList.add(allLabRoomInfo13);
        data_list = new ArrayList<Map<String, Object>>();
        for(int i=0;i<allLabRoomInfoList.size();i++){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("image", allLabRoomInfoList.get(i).getLab_room_local_image());
            map.put("text", allLabRoomInfoList.get(i).getLab_room_location());
            data_list.add(map);
        }
    }
        /*************************************************
         *@description： 检验版本更新
        *************************************************/
        public void CheckVersionUpdate()
        {
            //获取本地的版本
            PackageManager pm = getBaseContext().getPackageManager();
            PackageInfo pi = null;
            try {
                pi = pm.getPackageInfo(getBaseContext().getPackageName(), 0);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            localVersionName = pi.versionName;
            localVersionCode = pi.versionCode;
            ApplicationInfo appInfo = pi.applicationInfo;
            String appName = pm.getApplicationLabel(appInfo).toString();
            String packname = appInfo.packageName;
            String version = pi.versionName;
            /*************************************************
             *@description： 检测用于版本的更新
             *************************************************/
            //去服务器端获取版本号
            if(false)
            {
                URL url = null;
                try {
                    url = new URL(remoteUrl + "version/");
                    getConnection(url, handlerForGetVersion);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
            if(false)
            {
                final VersionBean versionBean=new VersionBean(2,"新版","地址");
                int versionCode=versionBean.getVersionCode();
                if(versionCode==localVersionCode)
                {
                    Toast.makeText(getBaseContext(), "当前已是最新版本", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    AlertDialog dialog = new AlertDialog.Builder(this).setTitle("提示")
                            .setNegativeButton("取消", null).setPositiveButton("确定",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            //处理确认按钮的点击事件
                                            String apkPath=versionBean.getApkPath();
                                            //开启一个下载的服务。

                                        }
                                    }).setMessage("当前有新版本，是否去下载").create();
                    dialog.show();
                }
            }
        }
    /*************************************************
     *@description： 当数据更新的时候回到选择实验室的界面后
     * 查看里面是否有数据SharePreference就直接进入里面
     *************************************************/
    @Override
    protected void onResume() {
        SharedPreferences  sp = getSharedPreferences("NJLABID", Context.MODE_PRIVATE);
        LabID= sp.getInt("labId",0);
        if(LabID!=0)
        {
            Intent intent10=new Intent(getBaseContext(),LoadingActivity.class);
            startActivity(intent10);
        }
        super.onResume();
    }
    @Override
    public void onDestroy()
    {
        super.onDestroy();
    }
}
