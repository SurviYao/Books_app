package com.example.administrator.books_app;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

/**
 * 基类
 */
public class BaseActivity extends Activity {
    public MyApp app;
    public BaseActivity context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (app==null){
            app= (MyApp) getApplication();
        }
        context=this;
        addActivity();
    }

    public boolean isEmpty(String[] values,String[] info){
        boolean is=false;
        for (int i = 0; i <values.length ; i++) {
            if (TextUtils.isEmpty(values[i])){
                toast_short(info[i]+"不能为空");
                is=true;
                break;
            }
        }
        return is;
    }

    public void toast_long(String text){
        Toast.makeText(context,text,Toast.LENGTH_LONG).show();
    }

    public void toast_short(String text){
        Toast.makeText(context,text,Toast.LENGTH_SHORT).show();
    }

    public String getRandom(){
        //随机产生四位数的随机数
        //[0,1] *9000-->[0,9000]+1000--->[1000,10000]    [1000,10000]
        String num=String.valueOf
                ((int)(Math.random()*9000+1000));
        return num;
    }

    /**
     * 将打开的activity界面的父类对象添加至集合
     */
    public void addActivity(){
        app.addActivity(context);
    }

    /**
     * 移除指定的界面
     */
    public void removeActivity(){
        app.removeActivity(context);
    }

    /**
     * 释放所有界面内存
     */
    public void removeAllActivity(){
        app.removeAllActivity();
    }
}
