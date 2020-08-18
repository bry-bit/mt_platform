package com.mt.util;

import redis.clients.jedis.Jedis;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class IDUtil {
    public static String getUUID() {
        String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "").toUpperCase();
        return uuid;
    }
}
