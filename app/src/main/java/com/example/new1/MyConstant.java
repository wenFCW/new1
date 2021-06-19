package com.example.new1;

import java.text.SimpleDateFormat;
import java.util.Date;

//存放常量和获取时间功能。
public class MyConstant {
    public static final String DB_NAME = "RecordDB.db";//库名
    public static final String TABLE_NAME = "recordInfos";//表名
    public static final String CLOME_ID = "id"; //id字段
    public static final String CLOME_RECORD = "record";//记录内容字段
    public static final String CLOME_TIME = "time"; //保存时间字段

    //获取当前日期。
    public static final String getTime(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        return  simpleDateFormat.format(date);
    }

}
