package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.util.Locale;


import static java.nio.file.Paths.get;

public class MainActivity extends AppCompatActivity {

    public static Handler handler;
   // public static Handler timeHandle;
    private String receiveMsg = "";
    private BufferedReader in = null;
    private TextView temperatureTv = null;
    private TextView lowTv = null;
    private TextView highTv = null;
    private TextView heartrateTv = null;
    private TextView bloodfatTv = null;
    private TextView timeTv = null;
    private TextView weekTv = null;
    private TextView dateTv = null;
    private TextView driverTv = null;
    private TextView sexTv = null;
    private TextView ageTv = null;
    private TextView weightTv = null;
    public static TextView inputTv;
    public static Button okButton;
    public static Button noButton;
    private static ImageView heart;
    private ImageView setting;
    private ImageView startSpeech;
    private String file = null;
    private String carRate = null;
    private String carTemperature = null;
    private String carOxygen = null;
    private String carHumidity = null;
    private String fileName = "";
    private String lowBlood = "";
    private String highBlood = "";
    private String breathRate = "";
    private String temperature = "";
    private String heartRate = "";

    public int mainCount = 0;
    private int temCount = 0;
    private int temCount1;
    private int temCount2;
    private int temCount3;
    int lowCount = 0;
    int highCount = 0;
    int breathCount = 0;
    int heartCount = 0;


    private TextToSpeech textToSpeech = null;
    private TextToSpeech textToSpeech1;
    private int lowCount1;
    private int lowCount2;
    private int lowCount3;
    private int highCount1;
    private int highCount2;
    private int highCount3;


    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        temperatureTv = (TextView) findViewById(R.id.temperature);
        lowTv = (TextView) findViewById(R.id.lowpress);
        highTv = (TextView) findViewById(R.id.hightpress);
        heartrateTv = (TextView) findViewById(R.id.Heart);
        bloodfatTv = (TextView) findViewById(R.id.bloodfat);
        timeTv = (TextView) findViewById(R.id.textTime);
        weekTv = (TextView) findViewById(R.id.textWeek);
        dateTv = (TextView) findViewById(R.id.textDate);
        driverTv = (TextView) findViewById(R.id.textdriver);
        sexTv = (TextView) findViewById(R.id.textsex);
        ageTv = (TextView) findViewById(R.id.textage);
        weightTv = (TextView) findViewById(R.id.textweight);
        okButton = (Button) findViewById(R.id.okbutton);
        noButton = (Button) findViewById(R.id.nobutton);
        inputTv = (TextView) findViewById(R.id.ai);
        heart = (ImageView) findViewById(R.id.heartImg);
        setting= (ImageView)findViewById(R.id.setting);
        startSpeech = (ImageView) findViewById(R.id.power);

        //开启接收数据service
        startDataIntent();
        //语音播报

            textSpeech1("长安街与心兰路交口有一白色轿车车主突发心脏病导致交通事故，请派出救护车前往");

            textSpeech("检测到疑似突发心脏病是否拨打求助电话,15秒内无应答将自动报警");

        timeButton();



        handler = new Handler() {
            @SuppressLint("SetTextI18n")
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                switch (msg.what) {
                    case 0://文件名
                        fileName = (String) msg.obj;
                        break;
                    case 1://呼吸频率
                        breathRate = (String) msg.obj;
                        bloodfatTv.setText((String) msg.obj);
                        bloodfatTv.setTextColor(Color.parseColor("#bc1717"));
                        break;
                    case 2://车速
                        carRate = (String) msg.obj;
                        if (carRate.equals("0")) {
                            startFileIntent();

                        }
                        break;
                    case 3://车内温度
                        carTemperature = (String) msg.obj;

                        break;
                    case 4://体温
                        temperature = (String) msg.obj;
                        temperatureTv.setText((String) msg.obj);

                        break;
                    case 5://低血压

                        lowBlood = (String) msg.obj;
                        lowTv.setText(lowBlood);

                        break;
                    case 6://高血压
                        highBlood = (String) msg.obj;
                        highTv.setText(highBlood);

                        break;
                    case 7://心率
                        heartRate = (String) msg.obj;
                        heartrateTv.setText(heartRate);
                        heartrateTv.setTextColor(Color.parseColor("#bc1717"));
                        break;
                    case 8://车内含氧量
                        carOxygen = (String) msg.obj;
                        break;
                    case 9://车内湿度
                        carHumidity = (String) msg.obj;
                        break;
                    case 10://当前时间
                        timeTv.setText((String) msg.obj);
                    case 11://当前周
                        weekTv.setText((String) msg.obj);
                    case 12://当前日期
                        dateTv.setText((String) msg.obj);

                }
                //开启界面动画效果
                startAnimations();
                driverTv.setText("郭浩卓");
                sexTv.setText("男");
                ageTv.setText("22");
                weightTv.setText("55");

               /* mainCount++;

                if(mainCount>1) {

                    JSONObject jsonObject = new JSONObject();
                    try {

                        jsonObject.put("lowBlood", lowBlood);
                        jsonObject.put("highBlood", highBlood);
                        jsonObject.put("heartRate", heartRate);
                        jsonObject.put("breathRate", breathRate);
                        jsonObject.put("temperature", temperature);
                        jsonObject.put("carRate", carRate);
                        jsonObject.put("carTemperature", carTemperature);
                        jsonObject.put("carHumidity", carHumidity);
                        jsonObject.put("carOxygen", carOxygen);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    analyzeCondition(jsonObject);

                }*/

            }

        };


    }

    //方法：开启界面动画
    private void startAnimations() {
        ImageView outer = (ImageView) findViewById(R.id.outer);
        Animation operatingAnim = AnimationUtils.loadAnimation(this, R.anim.anim_round_outrotate);
        LinearInterpolator lin = new LinearInterpolator();
        operatingAnim.setInterpolator(lin);
        outer.startAnimation(operatingAnim);

        ImageView middle = (ImageView) findViewById(R.id.middle);
        Animation middleoperatingAnim = AnimationUtils.loadAnimation(this, R.anim.anim_round_middle_rotate);
        LinearInterpolator middlelin = new LinearInterpolator();
        middleoperatingAnim.setInterpolator(middlelin);
        middle.startAnimation(middleoperatingAnim);

        ImageView lining = (ImageView) findViewById(R.id.lining);
        Animation liningoperatingAnim = AnimationUtils.loadAnimation(this, R.anim.anim_round_lining_rotate);
        LinearInterpolator lininglin = new LinearInterpolator();
        liningoperatingAnim.setInterpolator(lininglin);
        lining.startAnimation(liningoperatingAnim);

        ImageView heart = (ImageView) findViewById(R.id.heartImg);
        Animation heartAnim = AnimationUtils.loadAnimation(this, R.anim.anim_scale_heartscale);
        heart.startAnimation(heartAnim);
    }

    //方法：开启发送文件的service
    private void startFileIntent() {
        Intent fileIntent = new Intent(this, SearchFileService.class);
        fileIntent.putExtra("fileName", fileName);
        startService(fileIntent);
    }

    //方法：开启获取数据的service
    private void startDataIntent() {
        Intent dataIntent = new Intent(this, MyService.class);

        startService(dataIntent);
    }

    //方法：语音播报文本
    public void textSpeech(final String text) {


        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = textToSpeech.setLanguage(Locale.CHINESE);
                    if (result != TextToSpeech.LANG_COUNTRY_AVAILABLE && result != TextToSpeech.LANG_AVAILABLE) {
                        Toast.makeText(getApplicationContext(), "TTS暂时不支持这种语音的朗读！", Toast.LENGTH_SHORT).show();
                        Log.d("最终TTS", "nospeak");
                    } else {
                        Log.d("最终TTS", "speaking");


                    }
                }
            }
        });
        startSpeech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textToSpeech.setPitch(1.0f);
                textToSpeech.speak(text, TextToSpeech.QUEUE_ADD, null);
                inputTv.setText(text);
            }
        });


    }

    //方法：分析身体状况

    /**
     *
     * @param jsonObject
     */
    private void analyzeCondition(JSONObject jsonObject) {
        String breathe = "";
        String high = "";
        String low = "";
        String heart = "";
        String carV = "";
        String carTem = "";
        String carHum = "";
        String carOxy = "";
        String tem = "";


        try {
            breathe = jsonObject.getString("breathRate");
            low = jsonObject.getString("lowBlood");
            high = jsonObject.getString("highBlood");
            heart = jsonObject.getString("heartRate");
            tem = jsonObject.getString("temperature");
            carV = jsonObject.getString("carRate");
            carTem = jsonObject.getString("carTemperature");
            carHum = jsonObject.getString("carHumidity");
            carOxy = jsonObject.getString("carOxygen");
            Log.d("cartem", String.valueOf(carTem));
        } catch (JSONException e) {
            e.printStackTrace();
        }
//体温异常*******************************************************
        if (Integer.parseInt(tem) > 38 && Integer.parseInt(tem) <= 40 && Integer.parseInt(carTem) > 45) {//车内温度过高体温偏高
            temCount++;
            Log.d("temCount", String.valueOf(temCount));
            if (temCount == 10) {
                textSpeech("我怀疑你发烧了有需要的话让我帮你拨打求助电话");
                temperatureTv.setTextColor(Color.parseColor("#FF0000"));
                temCount = 0;
            }

        } else if (Integer.parseInt(tem) > 38 && Integer.parseInt(tem) <= 40 && Integer.parseInt(carTem) <= 45) {//车内温度正常体温偏高
            temCount1++;
            if (temCount1 == 10) {
                textSpeech("体温偏高");
                temperatureTv.setTextColor(Color.parseColor("#FF0000"));
                temCount1 = 0;
            }
        } else if (Integer.parseInt(tem) > 37 && Integer.parseInt(tem) <= 38 && Integer.parseInt(carTem) > 45) {//车内温度过高体温微高
            temCount2++;
            if (temCount2 == 10) {
                textSpeech("如果车里热的让你直冒汗而你又不知道怎么使用空调的话，我可以帮你打开空调");
                temperatureTv.setTextColor(Color.parseColor("#FF7F00"));
                temCount2 = 0;
            }
        } else if ((Integer.parseInt(tem) > 37 && Integer.parseInt(tem) <= 38 && Integer.parseInt(carTem) <= 45)) {//车内温度正常体温微高
            temCount3++;
            if (temCount3 == 10) {
                textSpeech("体温偏高如果感到身体不适请停车休息");
                temperatureTv.setTextColor(Color.parseColor("#FF7F00"));
                temCount3 = 0;
            }
        } else if (Integer.parseInt(tem) <= 37) {//正常体温
            temperatureTv.setTextColor(Color.parseColor("#99cc32"));
        }
//血压低压异常**********************************************************
        else if (Integer.parseInt(low) >= 90 && Integer.parseInt(low) <= 109 && Integer.parseInt(carV) >= 150) {//车速快低压微高
            lowCount++;
            if (lowCount == 10) {
                textSpeech("你知道的，车速太快或者血压太高对你来说都不是好事");
                lowTv.setTextColor(Color.parseColor("#ff7f0"));
                lowCount = 0;
            }
        } else if (Integer.parseInt(low) >= 110 && Integer.parseInt(carV) > 150) {//车速快低压很高
            lowCount1++;
            if (lowCount1 == 2) {
                textSpeech("你的低压高的很离谱，请尽快降低车速避免突发情况");
                lowTv.setTextColor(Color.parseColor("#ff000"));
                lowCount1 = 0;
            }
        } else if (Integer.parseInt(low) >= 90 && Integer.parseInt(low) <= 109 && Integer.parseInt(carV) < 150) {//车速正常低压微高
            lowCount2++;
            if (lowCount2 == 2) {
                textSpeech("低压有些偏高");
                lowTv.setTextColor(Color.parseColor("#ff7f0"));
                lowCount2 = 0;
            }
        } else if (Integer.parseInt(low) >= 110 && Integer.parseInt(carV) <= 150) {//车速正常低压很高
            lowCount3++;
            if (lowCount3 == 2) {
                textSpeech("低压严重偏高");
                lowTv.setTextColor(Color.parseColor("#ff0000"));
            }
        } else if (Integer.parseInt(low) <= 85) {
            lowTv.setTextColor(Color.parseColor("#99cc32"));
        }
//血压高压异常********************************************************
        else if (Integer.parseInt(high) >= 140 && Integer.parseInt(high) <= 179 && Integer.parseInt(carV) >= 150) {//车速快高压微高
            highCount++;
            if (highCount == 10) {
                textSpeech("你知道的，车速太快或者血压太高对你来说都不是好事");
                highTv.setTextColor(Color.parseColor("#ff7f0"));
                highCount = 0;
            }
        } else if (Integer.parseInt(high) > 180 && Integer.parseInt(carV) >= 150) {//车速快高压很高
            highCount1++;
            if (highCount1 == 2) {
                textSpeech("你的高压高的很离谱，请尽快降低车速避免突发情况");
                highTv.setTextColor(Color.parseColor("#ff000"));
                highCount1 = 0;
            }
        } else if (Integer.parseInt(high) >= 140 && Integer.parseInt(high) <= 179 && Integer.parseInt(carV) < 150) {//车速正常高压微高
            highCount2++;
            if (highCount2 == 2) {
                textSpeech("高压有些偏高");
                highTv.setTextColor(Color.parseColor("#ff7f0"));
                highCount2 = 0;
            }
        } else if (Integer.parseInt(high) > 180 && Integer.parseInt(carV) >= 150) {//车速正常高压很高
            highCount3++;
            if (highCount3 == 2) {
                textSpeech("高压严重偏高");
                highTv.setTextColor(Color.parseColor("#ff0000"));
            }
        } else if (Integer.parseInt(high) <= 85) {
            highTv.setTextColor(Color.parseColor("#99cc32"));
        }


    }

    //启动求助倒计时
    public void timeButton() {

        heart.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick (View v){
                new Thread(new CountdownTimer()).start();
            }
        });

    }

    //setting语音播报
    public void textSpeech1(final String text) {

        textToSpeech1 = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = textToSpeech1.setLanguage(Locale.CHINESE);
                    if (result != TextToSpeech.LANG_COUNTRY_AVAILABLE && result != TextToSpeech.LANG_AVAILABLE) {
                        Toast.makeText(getApplicationContext(), "TTS暂时不支持这种语音的朗读！", Toast.LENGTH_SHORT).show();
                        Log.d("最终TTS", "nospeak");
                    } else {
                        Log.d("最终TTS", "speaking");


                    }
                }
            }
        });
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textToSpeech1.setPitch(1.0f);
                textToSpeech1.speak(text, TextToSpeech.QUEUE_ADD, null);
                inputTv.setText(text);
            }
        });




    }






}

