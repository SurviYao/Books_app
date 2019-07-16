package com.example.administrator.books_app.utils;

import android.os.Handler;
import android.os.Message;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpUtil {
    public static final int HTTPURLCONNECTION_GET = 1;
    public static final int HTTPURLCONNECTION_PSOT = 2;
    public static final int HTTPCLIENT_GET = 3;
    public static final int HTTCLIENT_POST = 4;
    private static HttpUtil util;
    private String ip = "10.1.92.170";
    private String webName = "Books/";
    private String url = "http://" + ip + ":8080/" + webName;

    public String getUrl() {
        return url;
    }

    public static HttpUtil getInstance() {
        if (util == null) {
            util = new HttpUtil();
        }
        return util;
    }

    /**
     * servlet
     * 请求方式
     * map
     * key=values  &  key=values
     * handle
     */
    public void background(final Handler handler, final int type, final int requestMethod, final String servletName, final Map<String, Object> oMap) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                StringBuilder builder = new StringBuilder(url);
                builder.append(servletName);
                InputStream is = null;
                switch (requestMethod) {
                    case HTTPURLCONNECTION_GET:
                        is = url_get(type, builder, oMap);
                        break;
                    case HTTPURLCONNECTION_PSOT:
                        is = url_post(type, builder, oMap);
                        break;
                    case HTTPCLIENT_GET:
                        is = client_get(type, builder, oMap);
                        break;
                    case HTTCLIENT_POST:
                        is = client_post(type, builder, oMap);
                        break;
                }
                String info = StreamUtil.getInstance()
                        .InputStreamToString(is);
                Object o = JsonUtil.getInstance()
                        .getJson(type, info);
                Message message = handler.obtainMessage();
                message.obj = o;
                message.what = type;
                handler.sendMessage(message);
            }
        }.start();
    }

    private InputStream client_post(int type, StringBuilder builder, Map<String, Object> oMap) {
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(builder.toString());
        List<NameValuePair> oList = new ArrayList<>();
        for (Map.Entry<String, Object> entry : oMap.entrySet()) {
            String key = entry.getKey();
            Object values = entry.getValue();
            NameValuePair pair = new BasicNameValuePair(key, String.valueOf(values));
            oList.add(pair);
        }
        try {
            HttpEntity entity = new UrlEncodedFormEntity(oList, "UTF-8");
            post.setEntity(entity);
            HttpResponse response = client.execute(post);
            int code = response.getStatusLine().getStatusCode();
            if (code == 200) {//404:地址错误  500--505：服务器不正常
                InputStream is = response.getEntity().getContent();
                return is;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private InputStream client_get(int type, StringBuilder builder, Map<String, Object> oMap) {
        builder.append("?");
        for (Map.Entry<String, Object> entry : oMap.entrySet()) {
            String key = entry.getKey();
            Object values = entry.getValue();
            builder
                    .append(key)
                    .append("=")
                    .append(values)
                    .append("&");
        }
        builder.deleteCharAt(builder.length() - 1);
        HttpClient client = new DefaultHttpClient();
        HttpGet get = new HttpGet(builder.toString());
        try {
            HttpResponse response = client.execute(get);
            int code = response.getStatusLine().getStatusCode();
            if (code == 200) {
                InputStream is = response.getEntity().getContent();
                return is;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private InputStream url_post(int type, StringBuilder builder, Map<String, Object> oMap) {
        try {
            URL url = new URL(builder.toString());
            HttpURLConnection huc = (HttpURLConnection)
                    url.openConnection();
            huc.setRequestMethod("POST");
            huc.setReadTimeout(5000);
            huc.setConnectTimeout(5000);
            huc.setDoOutput(true);
            huc.setDoInput(true);
            //拼接的是数据参数
            StringBuilder builder1 = new StringBuilder();
            builder1.append("?");
            for (Map.Entry<String, Object> entry : oMap.entrySet()) {
                String key = entry.getKey();
                Object values = entry.getValue();
                builder1
                        .append(key)
                        .append("=")
                        .append(values)
                        .append("&");
            }
            builder1.deleteCharAt(builder1.length() - 1);
            OutputStream os = huc.getOutputStream();
            os.write(builder1.toString().getBytes());
            if (huc.getResponseCode() == 200) {
                return huc.getInputStream();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private InputStream url_get(int type, StringBuilder builder, Map<String, Object> oMap) {
        builder.append("?");
        for (Map.Entry<String, Object> entry : oMap.entrySet()) {
            String key = entry.getKey();
            Object values = entry.getValue();
            builder
                    .append(key)
                    .append("=")
                    .append(values)
                    .append("&");
        }
        builder.deleteCharAt(builder.length() - 1);
        //get请求的完整http地址
        try {
            URL url = new URL(builder.toString());
            HttpURLConnection huc = (HttpURLConnection)
                    url.openConnection();
            huc.setConnectTimeout(5000);
            huc.setReadTimeout(5000);
            huc.setRequestMethod("GET");
            if (huc.getResponseCode() == 200) {
                InputStream is = huc.getInputStream();
                return is;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
