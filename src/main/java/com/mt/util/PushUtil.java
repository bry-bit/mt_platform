package com.mt.util;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class PushUtil {
    public static String push(String requestXml) {
        try {
            URL url = new URL("http://localhost/u8eai/import.asp");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setConnectTimeout(3000000);
            con.setReadTimeout(3000000);
            con.setDoInput(true);
            con.setDoOutput(true);
            con.setAllowUserInteraction(false);
            con.setUseCaches(false);
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
            //发送Request消息
            OutputStream out = con.getOutputStream();
            DataOutputStream dos = new DataOutputStream(out);
            dos.write(requestXml.getBytes("UTF-8"));
            //获取Response消息
            InputStream in = con.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            StringBuilder sb = new StringBuilder();
            String s = null;
            while ((s = br.readLine()) != null) {
                sb.append(s);
            }
            System.out.println("sb:" + sb);
            String responseXml = sb.toString();
            System.out.println("responseXml：" + responseXml);
            return JSONUtil.toJson("0", "", "推送成功！", "");
        } catch (Exception e) {
            e.printStackTrace();
            return JSONUtil.toJson("1", "", "推送失败！", "");
        }
    }
}
