package com.mt.controller.standard;

import org.springframework.stereotype.Controller;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

@Controller
public class test {
    public static void main(String[] args) {
        try {
            URL url = new URL("http://192.168.1.44/u8eai/import.asp");
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
            String requestXml = "<ufinterface sender='006' receiver='u8' roottag='purchaseorder' proc='query' dynamicdate='08/17/2020'>" +
//                    "<department>" +
//                    "<field display='部门编码' name='cDepCode' operation='=' value='001' logic=''/>" +
//                    "</department>" +
                    "</ufinterface>";
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
        } catch (ProtocolException ex) {
            ex.printStackTrace();
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}

