package com.example.myapplication;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;


import android.annotation.SuppressLint;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;


import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;


public class MyService extends Service {


    private Socket clientSocket = null;
    private BufferedReader in = null;
    private String receiveMsg = "";
    private String data=null;
    private long startTime=new Date().getTime();
    private String fileName= String.valueOf(startTime);

    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("oncreateoncreate", "=============================");
        SocketAcceptThread socketAcceptThread = new SocketAcceptThread();
        // 开启 Socket 监听线程
        socketAcceptThread.start();

    }

    @Override
    public void onStart(Intent intent, int startId) {
        Log.d("service", "socket service start");


    }

    @Override
    public void onDestroy() {
        Log.d("service", "socket service destroy!");


    }


    private class SocketAcceptThread extends Thread {
       //解析json数据
        private String value(String name) {//获取json串中所需信息（方法）
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(receiveMsg);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String a = null;
            try {
                a = jsonObject.get(name).toString();
            } catch (JSONException e) {
                e.printStackTrace();
            }Log.d("message", "++++++:" + jsonObject);
            return a;




        }
       //处理数据类型
        private String valueType(String value) {//处理数据类型（方法）
            double c = Double.parseDouble(value);
            int d = (int) c;
            String result = String.valueOf(d);
            return result;
        }
       //向主线程发送数据
        private void returnMessage(int what, String result) {//向主程序发送数据（方法）
            Message message = MainActivity.handler.obtainMessage();
            message.what = what;
            message.obj = result;
            MainActivity.handler.sendMessage(message);
            Log.d("====================", "=============================");
        }
        //将收到的数据存入文件中
        private void setInFile(String fileName,String data) {
            FileOutputStream out = null;
            BufferedWriter writer = null;

            try {
                out = openFileOutput(fileName, Context.MODE_APPEND);
                writer = new BufferedWriter(new OutputStreamWriter(out));
                writer.write(data);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (writer != null) {
                        writer.close();
                    }

                    } catch (IOException e) {
                      e.printStackTrace();
                }
            } Log.d("statu", "已存入"+fileName);
        }
        //从文件中读取数据
        private String getDataFile(){
            FileInputStream in = null;
            BufferedReader reader = null;
            StringBuilder content = new StringBuilder();
            String dataFile = null;
            try {
                in = openFileInput("data");
                reader = new BufferedReader(new InputStreamReader(in));
                String line = "";
                while ((line = reader.readLine()) != null) {
                    content.append(line);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                dataFile = content.toString();
                System.out.print("文件中读取到的数据" + dataFile);
            }return  dataFile;
        }
        //获取当前时间
        private JSONObject getTime(){
            Date nowDate = new Date();
            JSONObject jsonObject=new JSONObject();
            @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat=new SimpleDateFormat("HH:mm");
            @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat1=new SimpleDateFormat("E");
            @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat2=new SimpleDateFormat("MM月dd日");
            String nowTime=simpleDateFormat.format(nowDate);
            String nowWeek=simpleDateFormat1.format(nowDate);
            String dateNow=simpleDateFormat2.format(nowDate);
            try {
                jsonObject.put("nowTime",nowTime);
                jsonObject.put("nowWeek",nowWeek);
                jsonObject.put("dateNow",dateNow);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return jsonObject;


        }

        public void run() {
            Log.d("service", "socket service -----------------------");
            try {
                // 实例化ServerSocket对象并设置端口号为 12580
                clientSocket = new Socket("192.168.1.191", 12580);
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), "UTF-8"));

                while (true) {
                    if ((receiveMsg = in.readLine()) != null) {
                        Log.d("message", "receiveMsg:" + receiveMsg);
                        String value = null;


                        //解析接收到的jsonobject
//呼吸频率（调用方法）******************************************************
                        value = value("breathe_rate");
                        //处理数字类型
                        String weightValue = valueType(value);
                        //向主线程发送数据
                        returnMessage(1, weightValue);

//车速（调用方法）******************************************************
                        value = value("car_rate");
                        //处理数字类型
                        String ageValue = valueType(value);
                        if(ageValue.equals("0")){
                            fileName=String.valueOf(new Date().getTime());
                        }
                        //向主线程发送数据
                        returnMessage(2, ageValue);

//车温（调用方法）******************************************************
                        value = value("car_temperature");
                        //处理数字类型
                        String sexValue = valueType(value);
                        //向主线程发送数据
                        returnMessage(3, sexValue);

//体温（调用方法）******************************************************
                        value = value("body_temperature");
                        //处理数字类型
                        String temValue = valueType(value);
                        //向主线程发送数据
                        returnMessage(4, temValue);

//低压（调用方法）******************************************************
                        value = value("low_pressure");
                        //处理数字类型
                        String lowValue = valueType(value);
                        //向主线程发送数据
                        returnMessage(5, lowValue);

//高压（调用方法）******************************************************
                        value = value("high_pressure");
                        //处理数字类型
                        String highValue = valueType(value);
                        //向主线程发送数据
                        returnMessage(6, highValue);

//心率（调用方法）******************************************************
                        value = value("heart_rate");
                        //处理数字类型↓
                        String heartValue = valueType(value);
                        //向主线程发送数据↓
                        returnMessage(7, heartValue);
//含氧量（调用方法）******************************************************
                        value = value("oxygen");
                        //处理数字类型↓
                        String oxygenValue = valueType(value);
                        //向主线程发送数据↓
                        returnMessage(8, oxygenValue);
//湿度（调用方法）******************************************************
                        value = value("humidity");
                        //处理数字类型↓
                        String humidityValue = valueType(value);
                        //向主线程发送数据↓
                        returnMessage(9, humidityValue);
//总结数据存入文件中*****************************************************
                        String carId="冀G84555";
                        data=String.valueOf(new Date().getTime())+" "+weightValue+" "+ageValue+" "+sexValue+" "+temValue+" "+lowValue+" "+highValue+" "+heartValue+" "+oxygenValue+" "+humidityValue+" "+carId+"\n";
                        setInFile(fileName,data);
                        returnMessage(0, fileName);
//获取当前时间**********************************************************
                        JSONObject jsonObject=getTime();
                        String nowTime="";
                        String nowWeek="";
                        String dateNow="";
                        try {
                             nowTime= (String) jsonObject.get("nowTime");
                             nowWeek=(String)jsonObject.get("nowWeek");
                             dateNow=(String)jsonObject.get("dateNow");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        returnMessage(10,nowTime);
                        returnMessage(11,nowWeek);
                        returnMessage(12,dateNow);
                    }
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }





           /* data=getDataFile();
            Message message = MainActivity.handler1.obtainMessage();
            message.what = 10;
            message.obj = data;
            MainActivity.handler1.sendMessage(message);*/

        }

    }
}