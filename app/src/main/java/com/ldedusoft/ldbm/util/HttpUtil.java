package com.ldedusoft.ldbm.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by wangjianwei on 2016/6/22.
 */
public class HttpUtil {

    public  static void sendHttpRequest(final String path,final String xml, final HttpCallbackListener listener){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    byte[] entity = xml.getBytes("UTF-8");
                    HttpURLConnection conn = (HttpURLConnection) new URL(path).openConnection();
                    conn.setConnectTimeout(5000);
                    conn.setRequestMethod("POST");
                    conn.setDoOutput(true);
                    //指定发送的内容类型为xml
                    conn.setRequestProperty("Content-Type", "text/xml; charset=UTF-8");
                    conn.setRequestProperty("Content-Length", String.valueOf(entity.length));
                    OutputStream outStream = conn.getOutputStream();
                    outStream.write(entity);
                    if (conn.getResponseCode() == 200) {
                        InputStream in = conn.getInputStream();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                        StringBuilder response = new StringBuilder();
                        String line;
                        while((line = reader.readLine())!=null){
                            response.append(line);
                        }
                        listener.onFinish(response.toString());
//                        listener.onFinish(StreamTool.streamToString(conn.getInputStream()));
                    } else {
                        listener.onWarning("服务器错误:" + conn.getResponseCode());
                    }
                }catch (Exception e){
                    listener.onError(e);
                }
            }
        }).start();
    }
}
