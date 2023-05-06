package com.lonch.client.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.lonch.client.R;
import com.lonch.client.bean.login.LoginCodebean;
import com.lonch.client.bean.login.LoginPwdbean;
import com.lonch.client.common.Constants;
import com.lonch.client.interfaces.LoginContract;
import com.lonch.client.model.LoginPhoneModel;
import com.lonch.client.utils.HeaderUtils;
import com.lonch.client.utils.NetWorkInfoUtils;
import com.lonch.client.utils.OkHttpUtil;
import com.lonch.client.utils.SpUtils;
import com.lonch.client.utils.StringUtils;
import com.lonch.client.utils.ToastUtils;
import com.lonch.client.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity implements LoginContract.LoginResponseView {

    private TextView btnWangjiPwd;
    private EditText usernameEditText, pwdAndCode;
    private CheckBox cbBtnAgree; //同意用户协议
    private LinearLayout loginProgress;
    private RelativeLayout loginDataPage;
    private LoginPhoneModel loginPhoneModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        usernameEditText = findViewById(R.id.username);
        cbBtnAgree = findViewById(R.id.id_login_check_btn);
        pwdAndCode = findViewById(R.id.password_and_code);
        final Button loginButton = findViewById(R.id.login);
        loginProgress = findViewById(R.id.id_relative_login_progress);
        loginDataPage = findViewById(R.id.id_relative_data);
        btnWangjiPwd = findViewById(R.id.id_login_wangjipwd);

        loginPhoneModel = new LoginPhoneModel(this);

        loginButton.setOnClickListener(v -> {
            hideSoftKeyboard(LoginActivity.this);
            int verify = NetWorkInfoUtils.verify(getApplicationContext());
            if (verify != -1) {
                if (getStatus()) {
                    visiLodin();
                    try {
                        JSONObject allJson = new JSONObject();
                        allJson.put("userName", usernameEditText.getText().toString().trim());
                            allJson.put("password",
                                    HeaderUtils.md5(pwdAndCode.getText().toString().trim()));
                        allJson.put("terminalType", Constants.APP_CLIENT_ID);
                        allJson.put("productId", Constants.PRODUCT_ID);
                        loginPhoneModel.login(allJson.toString(), true);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                ToastUtils.showText(getResources().getString(R.string.page_login_connect_wifi));
            }
        });
    }

    private void visiLodin() {//显示login~
        loginProgress.setVisibility(View.VISIBLE);
        loginDataPage.setVisibility(View.GONE);
    }

    /**
     * 隐藏软键盘(只适用于Activity，不适用于Fragment)
     */
    public void hideSoftKeyboard(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    private boolean getStatus() {
        String phone = usernameEditText.getText().toString().trim();
        String pwdCode = pwdAndCode.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.page_login_phone_not_null), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(pwdCode)) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.page_login_passwd_not_null), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!TextUtils.isEmpty(phone) && !StringUtils.isMobileNO(phone)) {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.phone_number_error), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!cbBtnAgree.isChecked()) {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.login_tips_agree_user_instructions), Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onloginSuccess(LoginPwdbean bean) {
        if (null == bean) {
            return;
        }
        if (null == bean.getServiceResult()) {
            return;
        }
        SpUtils.put("token", bean.getServiceResult().getToken());
        SpUtils.put("curTimeMillis", String.valueOf(System.currentTimeMillis()));

        Toast.makeText(getApplicationContext(), bean.getServiceResult().getMsg() + "", Toast.LENGTH_SHORT).show();
        visiData();
        setIntent();

    }

    @Override
    public void onloginCodeSuccess(LoginCodebean bean) {
        if (null == bean) {
            showErrorToast();
            return;
        }
        if (null == bean.getServiceResult()) {
            showErrorToast();
            return;
        }
        SpUtils.put("token", bean.getServiceResult().getToken());
        SpUtils.put("curTimeMillis", String.valueOf(System.currentTimeMillis()));

        Toast.makeText(getApplicationContext(), bean.getServiceResult().getMsg() + "", Toast.LENGTH_SHORT).show();
        visiData();
        setIntent();
    }

    @Override
    public void onloginFaile(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
        visiData();
    }

    private void showErrorToast() {
        Toast.makeText(LoginActivity.this, R.string.error_tips, Toast.LENGTH_LONG).show();
    }

    private void visiData() {//显示数据
        loginDataPage.setVisibility(View.VISIBLE);
        loginProgress.setVisibility(View.GONE);
    }

    private void setIntent() {
        String brand = android.os.SystemProperties.get("ro.product.brand");
        if (!TextUtils.isEmpty(brand) && brand.equals("SUNMI")) {
            OkHttpUtil.getInstance().reportSunMiSn(Utils.getSN());
        }
        Intent intent = new Intent(this, LoadingActivity.class);
        intent.putExtra("login", true);
        startActivity(intent);
        finish();
    }
}
