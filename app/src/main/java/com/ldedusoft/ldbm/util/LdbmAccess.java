package com.ldedusoft.ldbm.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by wangjianwei on 2016/6/22.
 */
public class LdbmAccess {
    public static String gerAddress() throws Exception,
            IOException {
        String path ="http://ldbm.ld-edusoft.com/webservers/WebService.asmx" ;
        String xml = readSoap();
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
        if(conn.getResponseCode() == 200){
            parseSOAP(conn.getInputStream());
            System.out.println("发送成功");
        }else{
            System.out.println("发送失败");
        }
        return null;
    }

    private static String parseSOAP(InputStream inputStream) throws Exception {

        String xml = StreamTool.streamToString(inputStream);
        System.out.println(xml);
        return null;
    }

    private static String readSoap() throws Exception {

        String xml = "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
                "  <soap:Header>" +
                "    <MySoapHeader xmlns=\"LDBM4S\">" +
                "      <UserName>admin</UserName>" +
                "      <PassWord>zwj6756</PassWord>" +
                "    </MySoapHeader>" +
                "  </soap:Header>" +
                "  <soap:Body>" +
                "    <Login xmlns=\"LDBM4S\">" +
                "      <UName>李涛</UName>" +
                "      <Pwd>123456</Pwd>" +
                "    </Login>" +
                "  </soap:Body>" +
                "</soap:Envelope>";
        return xml;
    }



}
