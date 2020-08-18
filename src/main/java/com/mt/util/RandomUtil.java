package com.mt.util;

import com.mt.mapper.standard.Pur_EnquiryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

@Controller
public class RandomUtil {


    JSONUtil jsonUtil = new JSONUtil();
    @Resource
    Pur_EnquiryMapper pur_enquiryMapper;

    @Autowired
    //静态变量存储最大值
    private static final AtomicInteger atomicNum = new AtomicInteger();
    //初始化分组编号
    private final int INIT_GROUP_NUM = 0;

    /**
     * @Author  javaloveiphone
     * @Description :初始化设置分组编号最大值
     * @throws Exception
     * void
     */
    @PostConstruct
    public void initMaxNum(){
        try{
            int maxGroupNum = Integer.valueOf(pur_enquiryMapper.max_orderno());
            System.out.println("maxGroupNum="+maxGroupNum);
            if(maxGroupNum<INIT_GROUP_NUM){
                maxGroupNum = INIT_GROUP_NUM;
            }
            atomicNum.set(maxGroupNum);
        }catch(Exception e){
            System.out.println("初始化获取分组编号最大值异常");
        }
    }
    /**
     * @Author  javaloveiphone
     * @Description :获取最新分组编号
     * @return
     * int
     * 注：此方法并没有使用synchronized进行同步，因为共享的编号自增操作是原子操作，线程安全的
     */
    public String getNewAutoNum(){
        //线程安全的原子操作，所以此方法无需同步
        int newNum = atomicNum.incrementAndGet();

        NumberFormat formatter = NumberFormat.getNumberInstance();
        formatter.setMinimumIntegerDigits(6);
        formatter.setGroupingUsed(false);
        String s = formatter.format(newNum);
        String m = "";
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        m="MT"+format.format(new Date())+s;

        //数字长度为5位，长度不够数字前面补0
       // String newStrNum = String.format("%06d", newNum);
        System.out.println("newStrNum="+m);
        return m;
    }

}
