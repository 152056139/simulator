package fun.skai.smartcar.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import fun.skai.smartcar.R;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private static final int LOGIN_FAILED = 0;
    private static final int LOGIN_SUCCESS = 1;

    // UI 相关
    private EditText mPhoneNumView;
    private EditText mPasswordView;
    private Button mLoginButton;
    private TextView mRegisterLink;
    private TextView mResetPasswordLink;
    private View mLoginFormView;
    private View mLoginProgressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: " + getTaskId());
        setContentView(R.layout.activity_login);
        Log.i(TAG, "onCreate: init View");
        initView();
    }

    /**
     * 初始化控件
     */
    protected void initView() {
        mLoginFormView = findViewById(R.id.login_form);
        mLoginProgressView = findViewById(R.id.login_progress);

        // 注册
        mRegisterLink = findViewById(R.id.signup_link);
        mRegisterLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
        // 重置密码
        mResetPasswordLink = findViewById(R.id.reset_password_link);
        mResetPasswordLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 2018/5/19 修改代码传到重置密码页
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });

        // 验证手机号
        mPhoneNumView = findViewById(R.id.input_phonenum);
        mPhoneNumView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                // FIXME: 2018/5/14 便于测试先注释了
                // TODO: 2018/5/25 分离判断和视图更新，待测试
//                String phoneNumber = mPhoneNumView.getText().toString().trim();
//                if (TextUtils.isEmpty(phoneNumber)) {
//                    mPhoneNumView.setError(getString(R.string.error_phonenum_field_required));
//                } else {
//                    if (VerifyInformation.isPhoneNumberValid(phoneNumber)) {
//                        mLoginButton.setEnabled(true);
//                    } else {
//                        mPhoneNumView.setError(getString(R.string.error_invalid_phone_number));
//                        mLoginButton.setEnabled(false);
//                        mPhoneNumView.requestFocus();
//                    }
//                }
            }
        });

        // 验证密码
        mPasswordView = findViewById(R.id.input_password);
        mPasswordView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                // FIXME: 2018/5/14 便于测试想注释了
                // TODO: 2018/5/25 分离判断和更新视图
//                String password = mPasswordView.getText().toString().trim();
//                if (TextUtils.isEmpty(password)) {
//                    mPasswordView.setError(getString(R.string.error_password_field_required));
//                } else {
//                    if (VerifyInformation.isPasswordValid(password)) {
//                        mLoginButton.setEnabled(true);
//                    } else {
//                        mPasswordView.setError(getString(R.string.error_invalid_password));
//                        mLoginButton.setEnabled(false);
//                        mPasswordView.requestFocus();
//                    }
//                }
            }
        });
        // 尝试登录
        mLoginButton = findViewById(R.id.login_button);
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin();
            }
        });
    }

    /**
     * 尝试登录
     */
    private void attemptLogin() {
        String phonenum = mPhoneNumView.getText().toString().trim();
        String password = mPasswordView.getText().toString().trim();
        showProgress(true);
        authentication(phonenum, password);
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case LOGIN_FAILED:
                    onLoginFailed((String) msg.obj);
                    break;
                case LOGIN_SUCCESS:
                    onLoginSuccess((String) msg.obj);
                    break;
            }
        }
    };

    /**
     * 登录成功
     *
     * @param message 服务器返回的成功信息
     */
    private void onLoginSuccess(String message) {
        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();
        showProgress(false);
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * 登录失败
     *
     * @param message 服务器返回的失败信息
     */
    private void onLoginFailed(String message) {
        showProgress(false);
        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();
    }

    /**
     * 解析服务器返回的字符串
     *
     * @param result 服务器响应的字符串
     */
    private void resolveJson(String result) {
        try {
            // 从
            String json = result.subSequence(
                    result.indexOf("\"") + 1, result.lastIndexOf("\"")).toString();
            Log.d(TAG, "resolveJson: json=" + json);
            JSONObject jsonObject = new JSONObject(json);
            String status = jsonObject.getString("status");
            Message message = new Message();
            if (status.equals("failed")) {
                message.what = LOGIN_FAILED;
            } else if (status.equals("success")) {
                message.what = LOGIN_SUCCESS;
            }
            message.obj = jsonObject.getString("message");
            handler.sendMessage(message);
        } catch (JSONException e) {
            // TODO: 2018/5/15 处理解析异常
            e.printStackTrace();
        }
    }

    /**
     * 开启子线程，进行身份验证
     *
     * @param phone    手机号
     * @param password 密码
     */
    private void authentication(final String phone, final String password) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                FormBody body = new FormBody.Builder()
                        .add("phone", phone)
                        .add("password", password)
                        .build();
                Request request = new Request.Builder()
                        .url("http://192.168.1.192:8080/controller/login.php")
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
                    // TODO: 2018/5/14 处理登录异常
                    Log.e(TAG, "run: Failed to connect to server");
                }
            }
        }).start();
    }


    /**
     * 显示进度
     *
     * @param show 是否显示
     */
    private void showProgress(boolean show) {
        mLoginProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
    }
}