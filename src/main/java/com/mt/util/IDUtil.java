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

    public static class Random {
        //private static int index = 0;
        static  AtomicInteger index = new AtomicInteger(0);
        static Jedis jedis;
        public static void main(String[] args){
            jedis =new Jedis("127.0.0.1", 6379);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    while(true){
                        String timestamp = String.valueOf(new Date().getTime());
                        String key = timestamp + String.valueOf(new Random().getIndex());
                        System.out.println("1: " + key );
                        String value = getKey(key);

                        if(value!= null){
                            System.out.println("key: " + key);
                            index.set(1000);
                        }else {
                            setKey(key);
                        }

                        Thread.yield();
                        if(index.get() == 1000)
                            break;
                    }
                }
            }).start();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while(true){
                        String timestamp = String.valueOf(new Date().getTime()) ;
                        String key = timestamp + String.valueOf(new Random().getIndex());
                        System.out.println("2: " + key );
                        String value =  getKey(key);
                        if(value!= null){
                            System.out.println("key: " + key);
                            index.set(1000);
                        }else{
                            setKey(key);
                        }

                        Thread.yield();
                        if(index.get() == 1000)
                            break;
                    }
                }
            }).start();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while(true){
                        String timestamp = String.valueOf(new Date().getTime()) ;
                        String key = timestamp + String.valueOf(new Random().getIndex());
                        System.out.println("3: " + key );
                        String value =  getKey(key);
                        if(value!= null){
                            System.out.println("key: " + key);
                            index.set(1000);
                        }else{
                            setKey(key);
                        }

                        Thread.yield();
                        if(index.get() == 1000)
                            break;
                    }
                }
            }).start();
        /*try {
            TimeUnit.MILLISECONDS.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("size: " + map.size());*/

        }
        protected String getIndex(){
            synchronized(Random.class){
                index.getAndIncrement();
                if(index.get() == 1000)
                    index.set(0);
                return String.format("%03d", index.get());
            }

        }
        public static void setKey(String key){
            synchronized(Random.class){
                jedis.set(key, key);
            }
        }
        public static String getKey(String key){
            synchronized(Random.class){
                return jedis.get(key);
            }
        }
    }
}
