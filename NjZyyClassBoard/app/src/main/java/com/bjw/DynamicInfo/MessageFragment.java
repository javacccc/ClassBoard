package com.bjw.DynamicInfo;

/*************************************************
 *@date：2017/11/17
 *@author： zxj
 *@description： 通知的碎片
*************************************************/

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.bjw.R;
import com.bjw.bean.LabMessage;
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
public class MessageFragment extends Fragment {
    ListView listView;
    public static List <LabMessage>labMessageList=new ArrayList<>();
    /*************************************************
     *@description： 获取消息数据
    *************************************************/
    public Handler handlerForGetLabMesInfo = new Handler() {
        public void handleMessage(Message msg) {
            String response = msg.obj.toString();
            String labMessages=null;
            try {
                JSONObject jsonObject = new JSONObject(response.toString());
                labMessages = jsonObject.getString("result");
            } catch (JSONException e) {
                // TODO 自动生成的 catch 块
                e.printStackTrace();
            }
            if(labMessages!=null){
                Gson gson = new Gson();
                JsonParser parser = new JsonParser();
                JsonArray Jarray = parser.parse(labMessages).getAsJsonArray();
                LabMessage labMessage = null;
                for(JsonElement obj : Jarray ) {
                    labMessage = gson.fromJson(obj, LabMessage.class);
                    labMessageList.add(labMessage);
                }
                List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
                for (int i = 0; i < labMessageList.size(); i++) {
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("siple_tv1", "通知"+(i+1)+"：") ;
                    map.put("siple_tv2", labMessageList.get(i).getTitle());
                    list.add(map);
                }
                String[] from = {"siple_tv1", "siple_tv2",};
                int[] to = {R.id.messagename, R.id.messagecontent};
                SimpleAdapter simpleAdapter1 = new SimpleAdapter(getActivity(), list, R.layout.activity_right_message_list_item, from, to);
                listView.setAdapter(simpleAdapter1);
            }else{
                Toast.makeText(getContext(), "当前无该实验室的信息", Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState){
        super.onCreateView(inflater, container, savedInstanceState);
        View chatView = inflater.inflate(R.layout.fragment_right_message, container,false);
        return chatView;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
    }
    public void onStart() {
        super.onStart();
        findById();
        //获取消息的信息
        if(false) {
            try {
                URL url = new URL(remoteUrl + "labRoomInfo/labroomInfo/" + LabID);
                getConnection(url, handlerForGetLabMesInfo);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        else {
            if (labMessageList.size() == 0) {
                LabMessage labMessage1 = new LabMessage(LabID, "当前实验室", "测试发布公告一", "击事件做的业务逻辑委派给我们自己定义事件监听器统一管理和操作");
                LabMessage labMessage2 = new LabMessage(LabID, "当前实验室", "测试发布公告二", "具体要传入哪些数据呢？这不是我说了算，也不是你说了算，而是根据里面逻辑具");
                LabMessage labMessage3 = new LabMessage(LabID, "当前实验室", "测试发布公告三", "置布局id的view后者是无效有冲突．所以，我们也很容易想到一点就是初始化我们的这个封装的对话框应该有两种方式分别是文本类型和自定义view界面类型，实际上也就引出了我们这");
                LabMessage labMessage4 = new LabMessage(LabID, "当前实验室", "测试发布公告四", "首先需要拿到EditText对象，所以需要传出一个自定义布局的view对象，所以我就提供一个setter和getter方法留给外部用户使用，这里为了后面的使用方便我就把这个view对象使用回调方法将其回调出去．");
                LabMessage labMessage5 = new LabMessage(LabID, "当前实验室", "测试发布公告五", "接口用于方便用户后期的操作，这里有时候不要，看实际情况．仔细想想我们这里需要吗？实际上是需要就是我们在使用自定义view的对象，因为我们有时候需");
                labMessageList.add(labMessage1);
                labMessageList.add(labMessage2);
                labMessageList.add(labMessage3);
                labMessageList.add(labMessage4);
                labMessageList.add(labMessage5);
            }
                List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
                for (int i = 0; i < labMessageList.size(); i++) {
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("siple_tv1", "通知" + (i + 1) + "：");
                    map.put("siple_tv2", labMessageList.get(i).getTitle());
                    list.add(map);
                }
                String[] from = {"siple_tv1", "siple_tv2",};
                int[] to = {R.id.messagename, R.id.messagecontent};
                SimpleAdapter simpleAdapter1 = new SimpleAdapter(getActivity(), list, R.layout.activity_right_message_list_item, from, to);
                listView.setAdapter(simpleAdapter1);
            }
            listView.setOnItemClickListener(LvListener);
    }
    /*************************************************
     *@description： 数据绑定
    *************************************************/
    public void findById()
    {
        listView=(ListView)getActivity().findViewById(R.id.listviewformessage);
    }
    /*************************************************
     *@description： ListView的监听器
    *************************************************/
    AdapterView.OnItemClickListener LvListener=(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l)
        {
            //新建弹窗显示详细的信息
            AlertDialog dialog = new AlertDialog.Builder(getActivity()).
                    setTitle(labMessageList.get(i).getTitle()).
                    setPositiveButton("确定",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).setMessage(labMessageList.get(i).getContent()).create();
            dialog.show();
        }

    });
}
