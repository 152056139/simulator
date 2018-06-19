package fun.skai.smartcar.fragments;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import fun.skai.smartcar.R;
import fun.skai.smartcar.activities.RegisterActivity;
import fun.skai.smartcar.util.VerifyInformation;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment{

    private static final int REGISTER_FAILED = 0;
    private static final int REGISTER_SUCCESS = 1;
    private final String TAG = "RegisterFragment";

    private EditText mPhoneView;
    private EditText mPasswordView;
    private EditText mRePasswordView;
    private Button mRegisterButton;

    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        // 初始化控件并设置监听器

        mPhoneView = view.findViewById(R.id.et_f_register_phonenum);
        mPhoneView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                // FIXME: 2018/5/25 便于测试注释了
//                String phoneNumber = mPhoneView.getText().toString().trim();
//                if (TextUtils.isEmpty(phoneNumber)) {
//                    mPhoneView.setError(getString(R.string.error_phonenum_field_required));
//                    mRegisterButton.setEnabled(false);
//                } else {
//                    if (VerifyInformation.isPhoneNumberValid(phoneNumber)) {
//                        mRegisterButton.setEnabled(true);
//                    } else {
//                        mPhoneView.setError(getString(R.string.error_invalid_phone_number));
//                        mPhoneView.requestFocus();
//                        mRegisterButton.setEnabled(false);
//                    }
//                }
            }
        });
        mPasswordView = view.findViewById(R.id.et_f_register_password);
        mPasswordView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                // FIXME: 2018/5/25 测试完请取消注释
//                String password = mPasswordView.getText().toString().trim();
//                if (TextUtils.isEmpty(password)) {
//                    mPasswordView.setError(getString(R.string.error_password_field_required));
//                    mRegisterButton.setEnabled(false);
//                } else {
//                    if (VerifyInformation.isPasswordValid(password)) {
//                        mRegisterButton.setEnabled(true);
//                    } else {
//                        mPasswordView.setError(getString(R.string.error_invalid_password));
//                        mPasswordView.requestFocus();
//                        mRegisterButton.setEnabled(false);
//                    }
//                }
            }
        });
        mRePasswordView = view.findViewById(R.id.et_f_register_repassword);
        mRePasswordView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                // FIXME: 2018/5/25 测试完取消注释
                String password = mPasswordView.getText().toString().trim();
                String rePassword = mRePasswordView.getText().toString().trim();
                if (TextUtils.isEmpty(rePassword)) {
                    mRePasswordView.setError(getString(R.string.error_repassword_field_required));
                    mRegisterButton.setEnabled(false);
                } else {
                    if (VerifyInformation.isPasswordConfirm(password, rePassword)) {
                        mRegisterButton.setEnabled(true);
                    } else {
                        mRePasswordView.setError(getString(R.string.error_invalid_repassword));
                        mRePasswordView.requestFocus();
                        mRegisterButton.setEnabled(false);
                    }
                }
            }
        });
        mRegisterButton = view.findViewById(R.id.bt_f_register_register);
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mRegisterButton.isClickable()) {
                    attemptRegister();
                } else {
                    Toast.makeText(
                            getContext(),
                            getText(R.string.error_unfinished_form),
                            Toast.LENGTH_LONG
                    ).show();
                }
            }
        });

        return view;
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case REGISTER_SUCCESS:
                    onRegisterSuccess((String) msg.obj);
                    break;
                case REGISTER_FAILED:
                    onRegisterFailed((String) msg.obj);
                    break;
            }
        }
    };

    private boolean attemptRegister() {
        String phoneNumber = mPhoneView.getText().toString().trim();
        String password = mPasswordView.getText().toString().trim();
        authentication(phoneNumber, password);
        return false;
    }

    private boolean authentication(final String phoneNumber, final String password) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                FormBody body = new FormBody.Builder()
                        .add("phone", phoneNumber)
                        .add("password", password)
                        .build();
                Request request = new Request.Builder()
                        .url("http://192.168.1.192:8080/controller/register.php")
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
                    // TODO: 2018/5/26 处理注册时连接失败的异常
                    Log.e(TAG, "run: Failed to connect to server");
                }
            }
        }).start();
        return false;
    }

    private void resolveJson(String result) {
        try {
            String json = result.subSequence(
                    result.indexOf("\"") + 1, result.lastIndexOf("\"")
            ).toString();
            Log.d(TAG, "resolveJson: json = " + json);
            JSONObject jsonObject = new JSONObject(json);
            String status = jsonObject.getString("status");
            Message message = new Message();
            if (status.equals("failed")) {
                message.what = REGISTER_FAILED;
            } else if (status.equals("success")) {
                message.what = REGISTER_SUCCESS;
            }
            message.obj = jsonObject.getString("message");
            handler.sendMessage(message);
        } catch (JSONException e) {
            // TODO: 2018/5/26 处理解析异常 
            e.printStackTrace();
        }
    }

    private void onRegisterSuccess(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
        RegisterActivity activity = (RegisterActivity) getActivity();
        if (activity != null) {
            activity.setPhoneNumber(mPhoneView.getText().toString());
            activity.replaceFragment(new RegistrationSuccessFragment(), false);
        }
    }

    private void onRegisterFailed(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
        RegisterActivity activity = (RegisterActivity) getActivity();
        if (activity != null) {
            activity.replaceFragment(new RegistrationFailedFragment(), true);
        }
    }
}
