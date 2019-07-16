package com.example.administrator.books_app.utils;

import com.example.administrator.books_app.Book;
import com.example.administrator.books_app.BookJYInfo;
import com.example.administrator.books_app.Student;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtil {
    private static JsonUtil util;

    public static JsonUtil getInstance() {
        if (util == null) {
            util = new JsonUtil();
        }
        return util;
    }

    public Object getJson(int type, String json) {
        Object result = null;
        //是否是json格式的数据进行解析
        if (json.contains("{") || json.contains("[")) {
            switch (type) {
                case Util.LOGIN:
                    result = getStudent(json);
                    break;
                case Util.BORROW:
                case Util.BORROW_BOOK:
                case Util.IS_BORROW:
                case Util.REGISTER:
                case Util.HIS_DELETE:
                    result = register(json);
                    break;
                case Util.BOOKTYPE:
                    result = getBookType(json);
                    break;
                case Util.SEACH:
                case Util.BOOKS:
                    result = getBooks(json);
                    break;
                case Util.HIS_BORROW:
                    result = getBorrows_(json);
                    break;
                case Util.MYBORROW:
                    result = getBorrows(json);
                    break;
            }
        } else {
            result = json;
        }
        return result;
    }

    public List<BookJYInfo> getBorrows(String json) {
        List<BookJYInfo> infos = null;
        try {
            JSONArray array = new JSONArray(json);
            if (array != null) {
                infos = new ArrayList<>();
                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = array.getJSONObject(i);
                    int id = object.getInt("id");
                    int bookid = object.getInt("bookid");
                    String bookname = object.getString("bookname");
                    String bookaddress = object.getString("bookaddress");
                    String image = object.getString("image");
                    String startdate = object.getString("startdate");
                    int state = object.getInt("state");
                    BookJYInfo info = new BookJYInfo(id, bookid,
                            "", startdate, "",
                            state, image, bookname, bookaddress);
                    infos.add(info);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return infos;
    }

    public List<BookJYInfo> getBorrows_(String json) {
        List<BookJYInfo> infos = null;
        try {
            JSONArray array = new JSONArray(json);
            if (array != null) {
                infos = new ArrayList<>();
                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = array.getJSONObject(i);
                    int id = object.getInt("id");
                    int bookid = object.getInt("bookid");
                    String bookname = object.getString("bookname");
                    String bookaddress = object.getString("bookaddress");
                    String image = object.getString("image");
                    String startdate = object.getString("startdate");
                    String enddate = object.getString("enddate");
                    int state = object.getInt("state");
                    BookJYInfo info = new BookJYInfo(id, bookid,
                            "", startdate, enddate,
                            state, image, bookname, bookaddress);
                    infos.add(info);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return infos;
    }

    public List<Book> getBooks(String json) {
        List<Book> books = null;
        try {
            JSONArray array = new JSONArray(json);
            if (array != null) {
                books = new ArrayList<>();
                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = array.getJSONObject(i);
                    int bookid = object.getInt("bookid");
                    String bookname = object.getString("bookname");
                    String bookaddress = object.getString("bookaddress");
                    String bookdate = object.getString("bookdate");
                    String info = object.getString("info");
                    String image = object.getString("image");
                    String type = object.getString("type");
                    String author = object.getString("author");
                    Book book = new Book
                            (bookid, bookname,
                                    type, bookaddress,
                                    bookdate, info, author, image);
                    books.add(book);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return books;
    }

    public List<String> getBookType(String json) {
        List<String> oList = null;
        //[{"type":"编程语言"}，{"type":"音乐"}，{"type":"体育"}，{"type":"美食"}]
        try {
            JSONArray array = new JSONArray(json);
            if (array != null) {
                oList = new ArrayList<>();
                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = array.getJSONObject(i);
                    String type = object.getString("type");
                    oList.add(type);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return oList;
    }

    public int register(String json) {
        int state = 0;
        try {
            JSONObject o = new JSONObject(json);
            state = o.getInt("state");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return state;
    }

    public Student getStudent(String json) {
        //{"id":1,"number":"007","name":"小明"}
        Student student = null;
        try {
            JSONObject object = new JSONObject(json);
            int id = object.getInt("id");
            String number = object.getString("number");
            String name = object.getString("name");
            String psd = object.getString("psd");
            String phone = object.getString("phone");
            student = new Student(id, number, name, psd, phone);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return student;
    }
}
