package com.example.administrator.books_app;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.administrator.books_app.utils.HttpUtil;
import com.example.administrator.books_app.utils.Util;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity {

    @BindView(R.id.et_number)
    EditText etNumber;
    @BindView(R.id.et_psd)
    EditText etPsd;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.btn_register)
    Button btnRegister;
    @BindView(R.id.btn_reset)
    Button btnReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_register, R.id.btn_reset})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_register:
                String number=etNumber.getText().toString();
                String psd=etPsd.getText().toString();
                String name=etName.getText().toString();
                String phone=etPhone.getText().toString();
                boolean bol=isEmpty
                        (new String[]{number,psd,name,phone},
                                new String[]{"学号","密码","姓名","手机号"});
                if (!bol){
                    Map<String,Object> oMap=new HashMap<>();
                    oMap.put("number",number);
                    oMap.put("psd",psd);
                    oMap.put("name",name);
                    oMap.put("phone",phone);
                    HttpUtil.getInstance().background(
                            handler,Util.REGISTER,
                            HttpUtil.HTTCLIENT_POST,
                            "RegisterAppServlet",oMap);
                }
                break;
            case R.id.btn_reset:
                etName.setText("");
                etNumber.setText("");
                etPhone.setText("");
                etPsd.setText("");
                break;
        }
    }
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case Util.REGISTER:
                    int state= (int) msg.obj;
                    if (state==0){
                        toast_short("注册失败");
                    }else if (state==1){
                        toast_short("学号或者手机号已被注册");
                    }else if (state==2){
                        toast_short("注册成功");
                        finish();
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
