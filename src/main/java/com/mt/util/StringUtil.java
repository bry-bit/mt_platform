package com.mt.util;

import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class StringUtil {
    public static final String EMPTY_STRING = "";
    private final static String[] hexDigits = {"0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "A", "B", "C", "D", "E", "F"};
    public static String str;
    public static String[] chars = new String[]{"a", "b", "c", "d", "e", "f",
            "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
            "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z"};

    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0) {
            n = 256 + n;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }

    public static String byteArrayToHexString(byte[] b) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            resultSb.append(byteToHexString(b[i]));
        }
        return resultSb.toString();
    }

    public static String MD5Encode(String origin) {
        String resultString = null;
        try {
            resultString = new String(origin);
            MessageDigest md = MessageDigest.getInstance("MD5");
            resultString = byteArrayToHexString(md.digest(resultString
                    .getBytes()));
        } catch (Exception ex) {
        }
        return resultString;
    }

    //16位
    public static String getOrderIdByUUId() {
        int machineId = 1;//最大支持1-9个集群机器部署
        int hashCodeV = UUID.randomUUID().toString().hashCode();
        if (hashCodeV < 0) {//有可能是负数
            hashCodeV = -hashCodeV;
        }
        // 0 代表前面补充0
        // 4 代表长度为4
        // d 代表参数为正数型
        return machineId + String.format("%015d", hashCodeV);
    }

    //8位--时间戳形式
    //       + "" + (int) Math.random() * 10
    public static String getUUIdByTime() {
        //生成订单号
        Date day = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        String str = df.format(day);
        return str;
    }

    //8位--时间戳形式
    //       + "" + (int) Math.random() * 10
    public static String getUUIdByMonth() {
        //生成订单号
        Date day = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyyMM");
        String str = df.format(day);
        return str;
    }


    public static String getImageUUIdByTime() {
        //生成订单号
        Date day = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        String str = df.format(day);
        return str;

    }


    //8位
    public static String generateShortUuid() {
        StringBuffer shortBuffer = new StringBuffer();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        for (int i = 0; i < 8; i++) {
            String str = uuid.substring(i * 4, i * 4 + 4);
            int x = Integer.parseInt(str, 16);
            shortBuffer.append(chars[x % 0x3E]);
        }
        return shortBuffer.toString();

    }

    // 提取数字
    public static List<Integer> findNum(String str) {
        String a = str.replaceAll("[^0-9]", " ");
        String[] s = a.split(" ");
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < s.length; i++) {
            if (s[i].equals("")) {
                continue;
            }
            System.out.println(Integer.parseInt(s[i]));
            list.add(Integer.parseInt(s[i]));
        }
        return list;
    }

    //提取文字
    public static String findFont(String str) {
        String a = str.replaceAll("[^\\u4E00-\\u9FA5]", "").trim();
        return a;
    }


    /**
     * 不够位数的在前面补0，保留num的长度位数字
     *
     * @param code
     * @return
     */
    public static String autoGenericCode(String code, int num) {
        String result = "";
        // 保留num的位数
        // 0 代表前面补充0
        // num 代表长度为4
        // d 代表参数为正数型
        result = String.format("%0" + num + "d", Integer.parseInt(code));
        return result;

    }

    public static Integer chageNumber(String s) {
        String substring = s.substring(0, s.indexOf("."));
        Integer integer = Integer.valueOf(substring);
        return integer;
    }

    public static String geshiDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String format = simpleDateFormat.format(new Date());
        return format;

    }

}



