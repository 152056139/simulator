package fun.skai.smartcar;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    //private UserLoginTask mAuthTask = null;

    // UI 相关
    private EditText mPhoneNumView;
    private EditText mPasswordView;
    private Button mLoginButton;
    private TextView mRegisterLink;
    private View mLoginFormView;
    private View mLoginProgressView;

//    @BindView(R.id.input_phonenum)
//    EditText mPhoneNumView;
//    @BindView(R.id.input_password)
//    EditText mPasswordView;
//    @BindView(R.id.login_button)
//    Button mLoginButton;
//    @BindView(R.id.signup_link)
//    TextView mSignupLink;
//    @BindView(R.id.login_form)
//    View mLoginFormView;
//    @BindView(R.id.login_progress)
//    View mProgressView;

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
//        mSignupLink.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
////                startActivity(intent);
////                finish();
////                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
//            }
//        });
//        mLoginButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //attemptLogin();
//            }
//        });
        mPhoneNumView = findViewById(R.id.input_phonenum);
        mPhoneNumView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String phoneNum = mPhoneNumView.getText().toString();
                    verifyPhoneNum(phoneNum);
                }
            }
        });
        mPasswordView = findViewById(R.id.input_password);
        mPasswordView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String password = mPasswordView.getText().toString();
                    verifyPassword(password);
                }
            }
        });

    }

    /**
     * 验证手机号是否合法
     * @param phonenum 手机号
     * @return true 合法 false 不合法
     */
    private boolean verifyPhoneNum(String phonenum) {
        if (TextUtils.isEmpty(phonenum)) {
            mPhoneNumView.setError(getString(R.string.error_phonenum_field_required));
            return false;
        } else if (!isPhonenumValid(phonenum)) {
            mPhoneNumView.setError(getString(R.string.error_invalid_phone_number));
            return false;
        }
        return true;
    }

    /**
     * 验证密码是否合法
     * @param password 密码
     * @return true 合法 false 不合法
     */
    private boolean verifyPassword(String password) {
        if (TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.error_password_field_required));
            return false;
        } else if (!isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            return false;
        }
        return true;
    }

    /**
     * 手机号是否有效
     * @param phone 手机号
     * @return true 手机号有效 false 手机号无效
     */
    private boolean isPhonenumValid(String phone) {
        String regExp = "^((13[0-9])|(18[0-9])|(17[135-8])|(14[579])|(15[0-35-9]))\\d{8}$";
        Pattern pattern = Pattern.compile(regExp);
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }

    /**
     * 密码是否有效
     * @param password
     * @return
     */
    private boolean isPasswordValid(String password) {
        String regExp = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,18}$";
        Pattern pattern = Pattern.compile(regExp);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

//    private void attemptLogin() {
//        Log.d(TAG, "attemptLogin: execute");
//
//        if (mAuthTask != null) {
//            return;
//        }
//
//        // 重置错误
//        mPhoneNumView.setError(null);
//        mPasswordView.setError(null);
//
//        // 尝试登陆的时候存储手机号和密码
//        String email = mPhoneNumView.getText().toString();
//        String password = mPasswordView.getText().toString();
//
//        boolean cancel;
//        View focusView = null;
//
//        if (!validateInfoInput()) {
//            onLoginFailed();
//            return;
//        }
//
//        mLoginButton.setEnabled(false);
//        //ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,R.)
//    }

    private void onLoginFailed() {

    }

    private boolean validateInfoInput() {
        return false;
    }





//    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {
//        private final String mEmail;
//        private final String mPassword;
//
//        public UserLoginTask(String mEmail, String mPassword) {
//            this.mEmail = mEmail;
//            this.mPassword = mPassword;
//        }
//
//        @Override
//        protected Boolean doInBackground(Void... voids) {
//            // TODO: 2018/5/11 添加身份认证代码
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Boolean aBoolean) {
//            super.onPostExecute(aBoolean);
//        }
//    }

}
