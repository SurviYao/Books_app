package com.example.administrator.books_app;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.administrator.books_app.utils.HttpUtil;
import com.example.administrator.books_app.utils.SharedUtil;
import com.example.administrator.books_app.utils.Util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @BindView(R.id.spinner)
    Spinner spinner;
    @BindView(R.id.et_seach)
    EditText etSeach;
    @BindView(R.id.btn_seach)
    Button btnSeach;
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.tv_myborrow)
    TextView tvMyborrow;
    @BindView(R.id.tv_his)
    TextView tvHis;
    @BindView(R.id.tv_myinfo)
    TextView tvMyinfo;
    @BindView(R.id.tv_exit)
    TextView tvExit;
    private List<String> oList;
    private List<Book> books;
    private BookAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        HttpUtil.getInstance().background(handler,
                Util.BOOKTYPE, HttpUtil.HTTPCLIENT_GET,
                "BookTypeAppServlet", new HashMap<>());

        GridLayoutManager manager = new GridLayoutManager
                (context, 2,
                        GridLayoutManager.VERTICAL,
                        false);
        rvList.setLayoutManager(manager);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getType_http(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @OnClick(R.id.btn_seach)
    public void onClick() {
        String info = etSeach.getText().toString();
        boolean bol = isEmpty(new String[]{info}, new String[]{"关键字"});
        if (!bol) {
            Map<String, Object> oMap = new HashMap<>();
            oMap.put("key", info);
            HttpUtil.getInstance().background(
                    handler,
                    Util.SEACH,
                    HttpUtil.HTTCLIENT_POST,
                    "SeachBookAppServlet", oMap);
        }
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int index = (int) v.getTag();
            Intent intent = new Intent(context, BookDetailsActivity.class);
            intent.putExtra("book", books.get(index));
            startActivity(intent);
        }
    };

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case Util.SEACH:
                case Util.BOOKS:
                    books = (List<Book>) msg.obj;
                    if (books != null && books.size() > 0) {
                        adapter = new BookAdapter(context, books, listener);
                        rvList.setAdapter(adapter);
                    }
                    break;
                case Util.BOOKTYPE:
                    oList = (List<String>) msg.obj;
                    if (oList != null && oList.size() > 0) {
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                                (context,
                                        android.R.layout.simple_spinner_dropdown_item,
                                        oList);
                        spinner.setAdapter(adapter);
                    }
                    getType_http(0);
                    break;
            }
        }
    };

    public void getType_http(int index) {
        Map<String, Object> oMap = new HashMap<>();
        oMap.put("type", oList.get(index));
        HttpUtil.getInstance().background(
                handler, Util.BOOKS,
                HttpUtil.HTTCLIENT_POST,
                "BooksAppServlet", oMap);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.bind(this).unbind();
    }

    @OnClick({R.id.tv_myborrow, R.id.tv_his, R.id.tv_myinfo, R.id.tv_exit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_myborrow:
                //我的借阅
                startActivity(new Intent(context, MyBorrowActivity.class));
                break;
            case R.id.tv_his:
                //历史记录
                startActivity(new Intent(context, HisBookInfoActivity.class));
                break;
            case R.id.tv_myinfo:
                //个人信息
                startActivity(new Intent(context, MyInfoActivity.class));
                break;
            case R.id.tv_exit:
                //注销
                SharedUtil.getInstance()
                        .setBoolean("auto", false);
                removeAllActivity();
                startActivity(new Intent(context, LoginActivity.class));
                break;
        }
    }
}
