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
            String requestXml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                    "<ufinterface sender=\"001\" receiver=\"u8\" roottag=\"purchaseorder\" docid=\"885992348\" proc=\"add\" codeexchanged=\"N\" exportneedexch=\"N\" paginate=\"0\" display=\"采购订单\" family=\"采购管理\" timestamp=\"0x000000000027B640\">\n" +
                    "\t<purchaseorder>\n" +
                    "\t\t<header>\n" +
                    "\t\t\t<code>0000000001</code>\n" +
                    "\t\t\t<date>2006-10-10</date>\n" +
                    "\t\t\t<vendorcode>0001</vendorcode>\n" +
                    "\t\t\t<deptcode>2001</deptcode>\n" +
                    "\t\t\t<personcode/>\n" +
                    "\t\t\t<purchase_type_code>01</purchase_type_code>\n" +
                    "\t\t\t<operation_type_code>普通采购</operation_type_code>\n" +
                    "\t\t\t<address/>\n" +
                    "\t\t\t<idiscounttaxtype>0</idiscounttaxtype>\n" +
                    "\t\t\t<recsend_type/>\n" +
                    "\t\t\t<currency_name>人民币</currency_name>\n" +
                    "\t\t\t<currency_rate>1</currency_rate>\n" +
                    "\t\t\t<tax_rate>17</tax_rate>\n" +
                    "\t\t\t<paycondition_code/>\n" +
                    "\t\t\t<traffic_money>0</traffic_money>\n" +
                    "\t\t\t<bargain>0</bargain>\n" +
                    "\t\t\t<remark/>\n" +
                    "\t\t\t<period/>\n" +
                    "\t\t\t<maker>demo</maker>\n" +
                    "\t\t\t<define1/>\n" +
                    "\t\t\t<define2/>\n" +
                    "\t\t\t<define3/>\n" +
                    "\t\t\t<define4/>\n" +
                    "\t\t\t<define5/>\n" +
                    "\t\t\t<define6/>\n" +
                    "\t\t\t<define7/>\n" +
                    "\t\t\t<define8/>\n" +
                    "\t\t\t<define9/>\n" +
                    "\t\t\t<define10/>\n" +
                    "\t\t\t<define11/>\n" +
                    "\t\t\t<define12/>\n" +
                    "\t\t\t<define13/>\n" +
                    "\t\t\t<define14/>\n" +
                    "\t\t\t<define15/>\n" +
                    "\t\t\t<define16/>\n" +
                    "\t\t</header>\n" +
                    "\t\t<body>\n" +
                    "\t\t\t<entry>\n" +
                    "\t\t\t\t<inventorycode>GB00270850</inventorycode>\n" +
                    "\t\t\t\t<checkflag>0</checkflag>\n" +
                    "\t\t\t\t<unitcode>0502</unitcode>\n" +
                    "\t\t\t\t<quantity>450</quantity>\n" +
                    "\t\t\t\t<num>9</num>\n" +
                    "\t\t\t\t<quotedprice/>\n" +
                    "\t\t\t\t<price>59.83</price>\n" +
                    "\t\t\t\t<taxprice>70</taxprice>\n" +
                    "\t\t\t\t<money>26923.08</money>\n" +
                    "\t\t\t\t<tax>4576.92</tax>\n" +
                    "\t\t\t\t<sum>31500</sum>\n" +
                    "\t\t\t\t<discount/>\n" +
                    "\t\t\t\t<natprice>59.83</natprice>\n" +
                    "\t\t\t\t<natmoney>26923.08</natmoney>\n" +
                    "\t\t\t\t<assistantunit>0502</assistantunit>\n" +
                    "\t\t\t\t<nattax>4576.92</nattax>\n" +
                    "\t\t\t\t<natsum>31500</natsum>\n" +
                    "\t\t\t\t<natdiscount/>\n" +
                    "\t\t\t\t<taxrate>17</taxrate>\n" +
                    "\t\t\t\t<item_class/>\n" +
                    "\t\t\t\t<item_code/>\n" +
                    "\t\t\t\t<item_name/>\n" +
                    "\t\t\t\t<arrivedate>2006-10-30</arrivedate>\n" +
                    "\t\t\t\t<define22/>\n" +
                    "\t\t\t\t<define23/>\n" +
                    "\t\t\t\t<define24/>\n" +
                    "\t\t\t\t<define25/>\n" +
                    "\t\t\t\t<define26/>\n" +
                    "\t\t\t\t<define27/>\n" +
                    "\t\t\t\t<define28/>\n" +
                    "\t\t\t\t<define29/>\n" +
                    "\t\t\t\t<define30/>\n" +
                    "\t\t\t\t<define31/>\n" +
                    "\t\t\t\t<define32/>\n" +
                    "\t\t\t\t<define33/>\n" +
                    "\t\t\t\t<define34/>\n" +
                    "\t\t\t\t<define35/>\n" +
                    "\t\t\t\t<define36/>\n" +
                    "\t\t\t\t<define37/>\n" +
                    "\t\t\t\t<free1/>\n" +
                    "\t\t\t\t<free2/>\n" +
                    "\t\t\t\t<free3/>\n" +
                    "\t\t\t\t<free4/>\n" +
                    "\t\t\t\t<free5/>\n" +
                    "\t\t\t\t<free6/>\n" +
                    "\t\t\t\t<free7/>\n" +
                    "\t\t\t\t<free8/>\n" +
                    "\t\t\t\t<free9/>\n" +
                    "\t\t\t\t<free10/>\n" +
                    "\t\t\t</entry>\n" +
                    "\t\t</body>\n" +
                    "\t</purchaseorder>\n" +
                    "</ufinterface>\n";
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

