package com.mt.util;

import com.alibaba.fastjson.JSONObject;

public class JSONUtil {
    public static String toJson(String code, Object data, String msg, Object count) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", code);
        jsonObject.put("msg", msg);
        jsonObject.put("data", data);
        jsonObject.put("count", count);
        return jsonObject.toJSONString();
    }
}
