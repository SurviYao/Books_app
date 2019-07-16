package com.example.administrator.books_app;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.example.administrator.books_app.utils.HttpUtil;
import com.example.administrator.books_app.utils.SharedUtil;
import com.example.administrator.books_app.utils.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MyBorrowActivity extends BaseActivity {

    @BindView(R.id.rv_borrow)
    RecyclerView rvBorrow;
    private List<BookJYInfo> infos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_borrow);
        ButterKnife.bind(this);
        LinearLayoutManager manager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        rvBorrow.setLayoutManager(manager);
        rvBorrow.setItemAnimator(new DefaultItemAnimator());
        Map<String, Object> oMap = new HashMap<>();
        oMap.put("number", SharedUtil.getInstance().getString("number"));
        HttpUtil.getInstance()
                .background(handler,
                        Util.MYBORROW,
                        HttpUtil.HTTCLIENT_POST,
                        "MyBorrowServlet", oMap);
    }

    int index = 0;
    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //弹出对话框，提示用户是否归还
            //如果归还，则调用接口，进行提交归还该图书
            index = (int) v.getTag();
            show_Dialog(index);
        }
    };

    public void show_Dialog(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("提示");
        builder.setMessage("是否归还" + infos.get(position).getBookname() + "这本书的图书?");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //调用接口，进行提交归还该图书
                Map<String, Object> oMap = new HashMap<>();
                oMap.put("number", SharedUtil.getInstance().getString("number"));
                oMap.put("bookid", infos.get(position).getBookid());
                HttpUtil.getInstance().background(handler, Util.BORROW,
                        HttpUtil.HTTCLIENT_POST, "BorrowAppServlet", oMap);
            }
        });
        builder.setNegativeButton("取消", null);
        builder.show();
    }

    private MyBorrowAdapter adapter;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case Util.BORROW:
                    int i = (int) msg.obj;
                    if (i == 0) {
                        toast_short("归还失败");
                    } else {
                        toast_short("归还成功");
                        adapter.deleteItem(index);
                    }
                    break;
                case Util.MYBORROW:
                    infos = (List<BookJYInfo>) msg.obj;
                    if (infos != null && infos.size() > 0) {
                        adapter = new MyBorrowAdapter(context, infos, listener);
                        rvBorrow.setAdapter(adapter);
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
