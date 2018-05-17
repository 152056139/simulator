package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.Handler;
import android.os.Message;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.io.BufferedReader;
import java.util.Locale;

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
    private ImageView outer=null;
    private ImageView middle=null;
    private ImageView lining=null;
    private ImageView heart=null;
    private TextView input=null;
    private ImageView speech=null;
    private TextToSpeech textToSpeech=null;

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startAnimations();


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
                            textSpeech("车速为0");
                            bloodfatTv.setTextColor(Color.parseColor("#ff7f00"));


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

    //方法：开启界面动画
    private void startAnimations(){
        outer=(ImageView)findViewById(R.id.outer);
        Animation operatingAnim = AnimationUtils.loadAnimation(this, R.anim.anim_round_outrotate);
        LinearInterpolator lin = new LinearInterpolator();
        operatingAnim.setInterpolator(lin);
        outer.startAnimation(operatingAnim);

        middle=(ImageView)findViewById(R.id.middle);
        Animation middleoperatingAnim = AnimationUtils.loadAnimation(this, R.anim.anim_round_middle_rotate);
        LinearInterpolator middlelin = new LinearInterpolator();
        middleoperatingAnim.setInterpolator(middlelin);
        middle.startAnimation(middleoperatingAnim);

        lining=(ImageView)findViewById(R.id.lining);
        Animation liningoperatingAnim = AnimationUtils.loadAnimation(this, R.anim.anim_round_lining_rotate);
        LinearInterpolator lininglin = new LinearInterpolator();
        liningoperatingAnim.setInterpolator(lininglin);
        lining.startAnimation(liningoperatingAnim);

        heart=(ImageView)findViewById(R.id.heartImg);
        Animation heartAnim= AnimationUtils.loadAnimation(this,R.anim.anim_scale_heartscale);
        heart.startAnimation(heartAnim);
    }
    //方法：开启发送文件的service
    private void startFileIntent(){
        Intent fileIntent=new Intent(this,SearchFileService.class);
        fileIntent.putExtra("fileName",fileName);
        startService(fileIntent);
    }
    //方法：开启获取数据的service
    private void startDataIntent(){
        Intent dataIntent=new Intent(this,MyService.class);

        startService(dataIntent);
    }
    //方法：点击按钮语音播报文本框中的文本
    private void textSpeech(String speechText){
        input=(TextView)findViewById(R.id.ai);
        speech=(ImageView)findViewById(R.id.power);
        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = textToSpeech.setLanguage(Locale.CHINA);
                    if (result != TextToSpeech.LANG_COUNTRY_AVAILABLE&& result != TextToSpeech.LANG_AVAILABLE){
                        Toast.makeText(getApplicationContext(), "TTS暂时不支持这种语音的朗读！", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

       /* speech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {*/


                String str = speechText;
                String num = "";
                for (int i = 0; i < str.length(); i++) {
                    String isNum = str.substring(i, i + 1);
                    int a = 0;
                    try {
                        a = Integer.parseInt(isNum);//判断是不是可以转换为数字
                        num = num + a;
                    } catch (Exception e) {
                        num =num;
                    }
                }
                String sum=","+num+",";//重新赋值
                str = str .replaceAll(num,sum+"");//替换掉之前的值


                textToSpeech.speak(str,TextToSpeech.QUEUE_ADD, null);
                Log.d("最终得到", str);
            //}
        //});


    }

}

