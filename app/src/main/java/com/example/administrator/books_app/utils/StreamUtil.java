package com.example.administrator.books_app.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * 流文件工具类
 */
public class StreamUtil {
    private static StreamUtil util;

    //静态：被定义为静态的变量，方法，在当前类没有创建对象之前，内存已经分配了
    //而且所占用的内存会一直存在，直到当前应用完全退出后
    //被java的垃圾回收机制自动回收

    /**
     * 获得当前类对象
     * @return
     */
    public static StreamUtil getInstance() {
        if (util == null) {
            util = new StreamUtil();
        }
        return util;
    }


    /**
     * 字节转换成字符串
     * @param is
     * @return
     */
    public String InputStreamToString(InputStream is) {
            byte[] bytes = new byte[1024];
        int count = -1;
        StringBuilder builder = new StringBuilder();
        try {
            while ((count = is.read(bytes)) != -1) {
                String info = new String(bytes, 0, count);
                builder.append(info);
            }
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return builder.toString();
    }

    /**
     * 图片下载
     * 流的输入输出
     */
    public boolean DownLoadBitmap(InputStream is, File file) {
        boolean isDownLoad = false;
        //先将服务端传过来的图片流转换成位图
        Bitmap bitmap = BitmapFactory.decodeStream(is);
        //然后根据文件路径，创建输出流
        try {
            if (!file.exists()) {
                if (file.isDirectory()) {
                    file.mkdirs();
                }
                if (file.isFile()) {
                    file.createNewFile();
                }
            } else {
                FileOutputStream fos = new FileOutputStream(file);
                isDownLoad = bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                fos.close();
                is.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isDownLoad;
    }
}
