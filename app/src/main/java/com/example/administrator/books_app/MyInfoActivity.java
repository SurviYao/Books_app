package com.example.administrator.books_app;

import android.os.Bundle;
import android.widget.TextView;

import com.example.administrator.books_app.utils.SharedUtil;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MyInfoActivity extends AppCompatActivity {

    @BindView(R.id.tv_number)
    TextView tvNumber;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_phone)
    TextView tvPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);
        ButterKnife.bind(this);
        tvName.setText(SharedUtil.getInstance().getString("name"));
        tvNumber.setText(SharedUtil.getInstance().getString("number"));
        tvPhone.setText(SharedUtil.getInstance().getString("phone"));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.bind(this).unbind();
    }
}
