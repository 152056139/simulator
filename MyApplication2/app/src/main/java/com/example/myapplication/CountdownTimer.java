package com.example.myapplication;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class CountdownTimer implements Runnable {
    MainActivity mainActivity=new MainActivity();
    public Button timeBtn1=mainActivity.okButton;
    public Button timeBtn2=mainActivity.noButton;
    public TextView ai=mainActivity.inputTv;
    public  Handler handler=new Handler();
    public int T=15;
    @Override
    public void run() {
        while(T>0){
            handler.post(new Runnable() {
                @SuppressLint("SetTextI18n")
                @Override
                public void run() {

                    ai.setVisibility(View.INVISIBLE);

                    timeBtn1.setVisibility(View.VISIBLE);
                    timeBtn2.setVisibility(View.VISIBLE);
                    timeBtn1.setClickable(false);
                    timeBtn1.setText("求助  "+"("+T+"s)");
                }
            });
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            T--;
        }

        //倒计时结束
        handler.post(new Runnable() {
            @Override
            public void run() {
                timeBtn1.setClickable(true);
                timeBtn1.setVisibility(View.INVISIBLE);
                timeBtn2.setVisibility(View.INVISIBLE);
                ai.setVisibility(View.VISIBLE);
                ai.setText("正在拨打求助电话。。。");
                mainActivity.mainCount=1;
            }

        });
        T=15;

    }
}
