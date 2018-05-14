package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.content.ContentValues.TAG;

public class SearchFileService extends Service {
    String filePath;
    public SearchFileService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        GetDataFileThread getDataFileThread = new GetDataFileThread();
        getDataFileThread.start();
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        //获得从Activity中传递过来的数据
        filePath = intent.getStringExtra("fileName");
        return super.onStartCommand(intent, flags, startId);

    }
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private class GetDataFileThread extends Thread {
        private String getFile(String filePath) {
            Log.d("searchservice", "开始查询");
            FileInputStream in = null;
            BufferedReader reader = null;
            StringBuilder content = new StringBuilder();
            String dataFile = null;
            try {
                in = openFileInput(filePath);
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
                Log.d("从文件中查询到", dataFile);
                return dataFile;
            }
        }


        //发送文件****************************************************

        @Override
        public void run() {
            OkHttpClient.Builder builder = new OkHttpClient.Builder()
                    .connectTimeout(15, TimeUnit.SECONDS)
                    .writeTimeout(20, TimeUnit.SECONDS)
                    .readTimeout(20, TimeUnit.SECONDS);

            MediaType MEDIA_TYPE_HTML
                    = MediaType.parse("text/html; charset=utf-8");

            OkHttpClient mOkHttpClient = builder.build();

            MultipartBody.Builder mbody = new MultipartBody.Builder().setType(MultipartBody.FORM);


            @SuppressLint("SdCardPath") File file = new File("/data/data/com.example.myapplication/files/"+filePath);
            if (file.exists()) {//
                Log.i("file:", "exists");
            } else if (!file.exists()) {
                Log.i("file:", "not exists");
            }

            Log.i("imageName:", file.getName());
            mbody.addFormDataPart("file", file.getName(), RequestBody.create(MEDIA_TYPE_HTML, file));
            mbody.addFormDataPart("driver","joke");
            RequestBody requestBody = mbody.build();
            Request request = new Request.Builder()
                    .header("Authorization", "Client-ID " + "...")
                    .url("http://192.168.1.226:8080/controller/upload_file.php")
                    .post(requestBody)
                    .build();

            mOkHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    Log.i("InfoMSG", response.body().string());
                }
            });

stopSelf();
        }
    }
}

