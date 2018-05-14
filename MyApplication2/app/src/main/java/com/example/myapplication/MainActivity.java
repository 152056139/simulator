package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;


import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.ProtocolException;
import java.net.Socket;
import java.net.SocketException;
import java.net.URL;
import java.net.UnknownHostException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    public static Handler handler;
    private String receiveMsg = "";
    private BufferedReader in = null;
    private TextView weightTv = null;
    private TextView ageTv = null;
    private TextView sexTv=null;
    private TextView tempratureTv=null;
    private TextView lowTv=null;
    private TextView highTv=null;
    private TextView heartrateTv=null;
    private TextView bloodfatTv=null;
    private String file=null;
    private String carRate=null;
    private String carTemperature=null;
    private String carOxygen=null;
    private String carHumidity=null;
    private String fileName="";

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //开启数据服务

        startDataIntent();


        weightTv = (TextView) findViewById(R.id.textweight);
        ageTv = (TextView) findViewById(R.id.textage);
        sexTv=(TextView)findViewById(R.id.textsex);
        tempratureTv=(TextView)findViewById(R.id.temperature);
        lowTv=(TextView)findViewById(R.id.lowpress);
        highTv=(TextView)findViewById(R.id.hightpress);
        heartrateTv=(TextView)findViewById(R.id.Heart);
        bloodfatTv=(TextView)findViewById(R.id.bloodfat);



        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                switch (msg.what) {
                    case 0:
                        fileName=(String)msg.obj;
                        break;
                    case 1:

                        break;
                    case 2:
                       carRate=(String) msg.obj;
                        bloodfatTv.setText((String) msg.obj);//用完还给 1
                       if(carRate.equals("0"))
                       {
                            startFileIntent();

                        }
                        break;
                    case 3:
                        carTemperature=(String) msg.obj;
                        break;
                    case 4:
                        tempratureTv.setText((String) msg.obj);
                        break;
                    case 5:
                        lowTv.setText((String) msg.obj);
                        break;
                    case 6:
                        highTv.setText((String) msg.obj);
                        break;
                    case 7:
                        heartrateTv.setText((String) msg.obj);
                        break;
                    case 8:
                        carOxygen=(String) msg.obj;
                        break;
                    case 9:
                        carHumidity=(String) msg.obj;
                        break;

                }
            }
        };





    }
    private void startFileIntent(){
        Intent fileIntent=new Intent(this,SearchFileService.class);
        fileIntent.putExtra("fileName",fileName);
        startService(fileIntent);
    }
    private void startDataIntent(){
        Intent dataIntent=new Intent(this,MyService.class);

        startService(dataIntent);
    }

}

