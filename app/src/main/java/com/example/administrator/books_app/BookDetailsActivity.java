package com.example.administrator.books_app;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.books_app.utils.HttpUtil;
import com.example.administrator.books_app.utils.SharedUtil;
import com.example.administrator.books_app.utils.Util;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BookDetailsActivity extends BaseActivity {

    @BindView(R.id.iv_book)
    ImageView ivBook;
    @BindView(R.id.tv_bookname)
    TextView tvBookname;
    @BindView(R.id.tv_author)
    TextView tvAuthor;
    @BindView(R.id.tv_bookdate)
    TextView tvBookdate;
    @BindView(R.id.tv_bookaddress)
    TextView tvBookaddress;
    @BindView(R.id.tv_booktype)
    TextView tvBooktype;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.btn_borrow)
    Button btnBorrow;
    private Book book;
    private int state=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);
        ButterKnife.bind(this);
        book= (Book) getIntent().getSerializableExtra("book");
        if (book!=null){
            ImageLoader.getInstance().displayImage(HttpUtil
                            .getInstance().getUrl()
                            +"img/"+book.getImage(),
                    ivBook,MyApp.options);
            tvBookname.setText("书籍名称:"+book.getBookname());
            tvAuthor.setText("作者:"+book.getAuthor());
            tvBookdate.setText("出版日期:"+book.getBookdate());
            tvBookaddress.setText("出版社:"+book.getBookaddress());
            tvBooktype.setText("分类:"+book.getType());
            tvContent.setText("内容简介:\n"+"\u3000\u3000"+book.getInfo());

            Map<String,Object> oMap=new HashMap<>();
            oMap.put("number",SharedUtil.getInstance()
                    .getString("number"));
            oMap.put("bookid",book.getBookid());
            HttpUtil.getInstance()
                    .background(handler,
                            Util.IS_BORROW,
                            HttpUtil.HTTCLIENT_POST,
                            "IsBorrowBookServet",oMap);

        }
    }

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case Util.BORROW_BOOK:
                    int i= (int) msg.obj;
                    if (i==0){
                        toast_short("借阅失败");
                    }else if (i==1){
                        toast_short("借阅成功");
                        finish();
                    }
                    break;
                case Util.IS_BORROW:
                    state= (int) msg.obj;
                    if (state==0){
                        btnBorrow.setText("借阅");
                        btnBorrow.setBackgroundResource(R.color.main);
                    }else if (state==1){
                        btnBorrow.setText("借阅中");
                        btnBorrow.setBackgroundResource(R.color.red);
                    }
                    break;
            }
        }
    };

    @OnClick(R.id.btn_borrow)
    public void onClick() {
        switch (state){
            case 0:
                //书籍未借
                Map<String,Object> oMap=new HashMap<>();
                oMap.put("number",SharedUtil.getInstance().getString("number"));
                oMap.put("bookid",book.getBookid());
                HttpUtil.getInstance()
                        .background(handler,
                                Util.BORROW_BOOK,
                                HttpUtil.HTTCLIENT_POST,
                                "BorrowBookServet",oMap);
                break;
            case 1:
                //已借阅
                //弹出对话框，提示用户当前书籍已被借阅
                toast_short("当前书籍已借阅，无需同时借阅同一本书");
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.bind(this).unbind();
    }
}
