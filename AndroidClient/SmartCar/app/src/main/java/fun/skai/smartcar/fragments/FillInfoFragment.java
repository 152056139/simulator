package fun.skai.smartcar.fragments;


import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Calendar;

import fun.skai.smartcar.R;
import fun.skai.smartcar.activities.MainActivity;
import fun.skai.smartcar.activities.RegisterActivity;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class FillInfoFragment extends Fragment {

    private final static String TAG = "CompleteInfoFragment";
    private static final int UPDATE_FAILED = 0;
    private static final int UPDATE_SUCCESS = 1;

    public FillInfoFragment() {
        // Required empty public constructor
    }

    private EditText mUsernameView;
    private EditText mUserGenderView;
    private EditText mUserBirthdayView;
    private EditText mUserHeightView;
    private EditText mUserWeightView;
    private Button mSubmitView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.i(TAG, "onAttach: CIF attach");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: FillInfoFragment launched");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d(TAG, "onCreateView: execute");
        View view = inflater.inflate(R.layout.fragment_complete_information, container, false);
        Log.d(TAG, "onCreateView: view found");

        mSubmitView = view.findViewById(R.id.fill_info_submit);
        mSubmitView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptUpdate();
            }
        });

        mUsernameView = view.findViewById(R.id.register_name);
        Log.d(TAG, "onCreateView: userView found");
        mUsernameView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO: 2018/5/29 注册时用户名的验证逻辑
            }
        });
        Log.i(TAG, "onCreateView: name is done");

        mUserGenderView = view.findViewById(R.id.register_sex);
        mUserGenderView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showGenderDialog();
            }
        });
        Log.i(TAG, "onCreateView: gender is done");

        mUserBirthdayView = view.findViewById(R.id.register_birthday);
        mUserBirthdayView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBirthdayDialog();
            }
        });
        Log.i(TAG, "onCreateView: birthday is done");

        mUserHeightView = view.findViewById(R.id.register_height);
        mUserHeightView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO: 2018/5/29 身高验证
            }
        });
        Log.i(TAG, "onCreateView: height is done");

        mUserWeightView = view.findViewById(R.id.register_weight);
        mUserWeightView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO: 2018/5/29 体重验证
            }
        });
        Log.i(TAG, "onCreateView: weight is done");

        return view;
    }

    private void showBirthdayDialog() {
        Calendar calendar = Calendar.getInstance();
        final int mYear = calendar.get(Calendar.YEAR);
        int mMonth = calendar.get(Calendar.MONTH);
        final int mDay = calendar.get(Calendar.DAY_OF_MONTH);
        new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                int y = year;
                int m = month;
                int d = dayOfMonth;
                String birthday = String.format("%d-%d-%d", y, m, d);
                mUserBirthdayView.setText(birthday);
            }
        }, mYear, mMonth, mDay).show();
    }

    private void showGenderDialog() {
        final String[] genders = new String[]{"男", "女"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("请选择你的性别：");
        builder.setSingleChoiceItems(genders, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mUserGenderView.setText(genders[which]);
                dialog.dismiss();
            }
        });
        builder.show();
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_FAILED:
                    onUpdateFailed(String.valueOf(msg.obj));
                    break;
                case UPDATE_SUCCESS:
                    onUpdateSuccess(String.valueOf(msg.obj));
                    break;
            }
        }
    };

    private void onUpdateSuccess(String message) {
        Log.d(TAG, "onUpdateSuccess: execute");
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getContext(), MainActivity.class);
        // TODO: 2018/5/30 添加数据
        startActivity(intent);
    }

    private void onUpdateFailed(String message) {
        Log.d(TAG, "onUpdateFailed: execute");
        Toast.makeText(getContext(), message + "请稍后重试", Toast.LENGTH_SHORT).show();
    }

    private void attemptUpdate() {
        RegisterActivity activity = (RegisterActivity) getActivity();
        final String phoneNumber = activity.getPhoneNumber();
        Log.d(TAG, "attemptUpdate: phone = " + phoneNumber);
        final String name = mUsernameView.getText().toString();
        final String gender = "男".equals(mUserGenderView.getText().toString()) ? "1" : "0";
        final String birthday = mUserBirthdayView.getText().toString();
        final String height = mUserHeightView.getText().toString();
        final String weight = mUserWeightView.getText().toString();
        // TODO: 2018/5/30 添加检查空值逻辑
        new Thread(new Runnable() {
            @Override
            public void run() {
                FormBody body = new FormBody.Builder()
                        .add("phone", phoneNumber)
                        .add("name", name)
                        .add("sex", gender)
                        .add("birthday",birthday)
                        .add("height", height)
                        .add("weight", weight)
                        .build();
                Request request = new Request.Builder()
                        .url("http://192.168.1.192:8080/controller/updata_info.php")
                        .post(body)
                        .build();
                OkHttpClient client = new OkHttpClient();
                String result = null;
                try {
                    Response response = client.newCall(request).execute();
                    result = response.body().string();
                    Log.d(TAG, "run: result=" + result);
                    resolveJson(result);
                } catch (IOException e) {
                    e.printStackTrace();
                    // TODO: 2018/5/30 处理更新信息时出现的异常
                }
            }
        }).start();

    }

    private void resolveJson(String result) {
        try{
            String json = result.subSequence(
                    result.indexOf("\"") + 1, result.lastIndexOf("\"")
            ).toString();
            JSONObject jsonObject = new JSONObject(json);
            String status = jsonObject.getString("status");
            Message message = new Message();
            if (status.equals("failed")) {
                message.what = UPDATE_FAILED;
            } else {
                message.what = UPDATE_SUCCESS;
            }
            message.obj = jsonObject.getString("message");
            handler.sendMessage(message);
        } catch (JSONException e) {
            e.printStackTrace();
            // TODO: 2018/5/30 处理解析异常
        }
    }
}
