package com.example.administrator.books_app;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.example.administrator.books_app.utils.HttpUtil;
import com.example.administrator.books_app.utils.SharedUtil;
import com.example.administrator.books_app.utils.Util;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.et_number)
    EditText etNumber;
    @BindView(R.id.et_psd)
    EditText etPsd;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.tv_code)
    TextView tvCode;
    @BindView(R.id.cb_psd)
    CheckBox cbPsd;
    @BindView(R.id.cb_auto)
    CheckBox cbAuto;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.btn_register)
    Button btnRegister;
    private boolean auto, psd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        tvCode.setText(getRandom());
        auto = SharedUtil.getInstance().getBoolean("auto");
        if (auto) {
            //跳入主界面，并销毁登录界面
            startActivity(new Intent(context,MainActivity.class));
            finish();
        } else {
            psd = SharedUtil.getInstance().getBoolean("psd");
            if (psd) {
                String number = SharedUtil.getInstance().
                        getString("number");
                String password = SharedUtil.getInstance().
                        getString("password");
                etNumber.setText(number);
                etPsd.setText(password);
                cbPsd.setChecked(true);
            }
        }
    }

    @OnCheckedChanged({R.id.cb_psd, R.id.cb_auto})
    public void check(CompoundButton btn, boolean isChecked) {
        switch (btn.getId()) {
            case R.id.cb_auto:
                auto = isChecked;
                break;
            case R.id.cb_psd:
                psd = isChecked;
                break;
        }
    }

    @OnClick({R.id.btn_login, R.id.btn_register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                String number = etNumber.getText().toString();
                String password = etPsd.getText().toString();
                String code = etCode.getText().toString();
                boolean bol = isEmpty(new String[]{number, password, code},
                        new String[]{"学号", "密码", "验证码"});
                if (!bol) {
                    if (!code.equals(tvCode.getText().toString())) {
                        toast_short("验证码不一致，请重新输入");
                    } else {
                        //进行登录操作，通过http协议调用接口
                        Map<String, Object> oMap = new HashMap<>();
                        oMap.put("number", number);
                        oMap.put("psd", password);
                        HttpUtil.getInstance().background(handler,Util.LOGIN,HttpUtil.HTTCLIENT_POST,"LoginAppServlet",oMap);
                    }
                }
                break;
            case R.id.btn_register:
                startActivity(
                        new Intent
                                (context,
                                        RegisterActivity.class));
                break;
        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case Util.LOGIN:
                    Student student= (Student) msg.obj;
                    if (student!=null){
                        SharedUtil.getInstance()
                                .setInt("id",student.getId())
                                .setString("number",student.getNumber())
                                .setString("name",student.getName())
                                .setString("password",student.getPsd())
                                .setString("phone",student.getPhone())
                                .setBoolean("auto",auto)
                                .setBoolean("psd",psd);
                        toast_short("登录成功");
                        //关闭当前界面，跳转至主界面
                        startActivity(new Intent(context,MainActivity.class));
                        finish();
                    }else {
                        toast_short("登录失败，请检查学号，密码或者网络错误");
                    }
                    break;
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.bind(this).unbind();
    }
}
