package com.bjw.Common;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.os.Environment;
import android.util.Log;

import com.bjw.R;
import com.bjw.bean.AllLabRoomInfo;
import com.bjw.bean.ImageBean;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import jxl.Sheet;
import jxl.Workbook;

/**
 * 创建人：wxdn
 * 创建时间：2018/3/12
 * 功能描述：文件操作类
 */

public class FileOperateUtils {
    /*************************************************
     *@description： 将数据库文件转换为CSV文件
    *************************************************/
    public static void ExportToCSV(Cursor c, String fileName) {
        int rowCount = 0;
        int colCount = 0;
        FileWriter fw;
        BufferedWriter bfw;
        File sdCardDir = Environment.getExternalStorageDirectory();
        File saveFile = new File(sdCardDir, fileName);
        try {
            rowCount = c.getCount();
            colCount = c.getColumnCount();
            fw = new FileWriter(saveFile);
            bfw = new BufferedWriter(fw);
            if (rowCount > 0) {
                c.moveToFirst();
                // 写入表头
                for (int i = 0; i < colCount; i++) {
                    if (i != colCount - 1)
                        bfw.write(c.getColumnName(i) + ',');
                    else
                        bfw.write(c.getColumnName(i));
                }
                // 写好表头后换行
                bfw.newLine();
                // 写入数据
                for (int i = 0; i < rowCount; i++) {
                    c.moveToPosition(i);
                    for (int j = 0; j < colCount; j++) {
                        if (j != colCount - 1) {
                            bfw.write(c.getString(j) + ',');
                        }
                        else {
                            bfw.write(c.getString(j));
                        }
                    }
                    // 写好每条记录后换行
                    bfw.newLine();
                }
            }
            // 将缓存数据写入文件
            bfw.flush();
            // 释放缓存
            bfw.close();
            Log.v("导出数据", "导出完毕！");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            c.close();
        }
    }
    /*************************************************
     *@description： 读取Assert里面的实验室列表Excel文件数据
    *************************************************/
    public static ArrayList<AllLabRoomInfo> readLabListExcel(String xlsName, Context context){
        ArrayList<AllLabRoomInfo>allLabRoomInfoArrayList=new ArrayList<>();
        AssetManager manager = context.getAssets();
        try {
            Workbook workbook = Workbook.getWorkbook(manager.open(xlsName));
            Sheet sheet = workbook.getSheet(0);
            //表格一共有多少行
            int sheetRows = sheet.getRows();
            //将数据添加到集合中
            for (int i = 0; i < sheetRows; i++) {
                AllLabRoomInfo bean = new AllLabRoomInfo();
                //获取列的数据
                bean.setLab_room_id(Integer.parseInt(sheet.getCell(0, i).getContents()));
                bean.setLab_room_local_image(getImageResourceId(sheet.getCell(1, i).getContents()));
                bean.setLab_room_location(sheet.getCell(2, i).getContents());
                allLabRoomInfoArrayList.add(bean);
            }
            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return allLabRoomInfoArrayList;
    }


    /*************************************************
     *@description： 读取Assert里面的实验室图片Excel文件数据
     *************************************************/
    public static ArrayList<ImageBean> readLabImageExcel(String xlsName, Context context){
        ArrayList<ImageBean>imageBeanArrayList=new ArrayList<>();
        AssetManager manager = context.getAssets();
        try {
            Workbook workbook = Workbook.getWorkbook(manager.open(xlsName));
            Sheet sheet = workbook.getSheet(0);
            //表格一共有多少行
            int sheetRows = sheet.getRows();
            //将数据添加到集合中
            for (int i = 0; i < sheetRows; i++) {
                ImageBean bean = new ImageBean();
                //获取列的数据
                bean.setLabID(Integer.parseInt(sheet.getCell(0, i).getContents()));
                bean.setImage1(getImageResourceId(sheet.getCell(1, i).getContents()));
                bean.setImage2(getImageResourceId(sheet.getCell(2, i).getContents()));
                bean.setImage3(getImageResourceId(sheet.getCell(3, i).getContents()));
                bean.setImage4(getImageResourceId(sheet.getCell(4, i).getContents()));
                imageBeanArrayList.add(bean);
            }
            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imageBeanArrayList;
    }
    //利用反射技术获取图片的ID，图片的名称是String类型
    public  static int getImageResourceId(String name) {
        R.drawable drawables=new R.drawable();
        //默认的id
        int resId=0x7f02000b;
        try {
            //根据字符串字段名，取字段//根据资源的ID的变量名获得Field的对象,使用反射机制来实现的
            java.lang.reflect.Field field=R.drawable.class.getField(name);
            //取值
            resId=(Integer)field.get(drawables);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return resId;
    }

}
