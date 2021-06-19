package com.example.new1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

//数据库
public class MyOpenHelper extends SQLiteOpenHelper {

    public static final String CREATE_TABLE = "create table " + MyConstant.TABLE_NAME
            + "(" + MyConstant.CLOME_ID + " integer primary key autoincrement,"
            + MyConstant.CLOME_RECORD + " text,"
            + MyConstant.CLOME_TIME + " text)";

    //建表语句
    public static final String CREATE_USER = "create table User ("
            + "id integer primary key autoincrement, "
            + "username text, "
            + "userpwd text)";

    //创建数据表对象
    private SQLiteDatabase db;

    //构造方法
    public MyOpenHelper(@Nullable Context context) {
        super(context, MyConstant.DB_NAME, null, 1);//创建数据库
        db = this.getWritableDatabase();//创建可读可写数据表
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //创建数据表
        sqLiteDatabase.execSQL(CREATE_TABLE);
        sqLiteDatabase.execSQL(CREATE_USER);//创建用户表
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    //向数据库添加数据
    public long inserRecord(String record, String time) {
        ContentValues values = new ContentValues();
        values.put(MyConstant.CLOME_RECORD, record);
        values.put(MyConstant.CLOME_TIME, time);
        return db.insert(MyConstant.TABLE_NAME, null, values);
    }

    //更新数据
    public int updateRecord(String id, String record, String time) {
        ContentValues values = new ContentValues();
        values.put(MyConstant.CLOME_RECORD, record);
        values.put(MyConstant.CLOME_TIME, time);
        return db.update(MyConstant.TABLE_NAME, values, "id=?", new String[]{id});
    }

    //删除数据
    public int deleteRecord(String id) {
        return db.delete(MyConstant.TABLE_NAME, "id=?", new String[]{id});
    }

    //查询数据 查询到的所有数据存入该集合
    public List<RecordBean> queryRecord() {
        Cursor cursor = db.query(MyConstant.TABLE_NAME, null, null, null, null,
                null, MyConstant.CLOME_TIME + " desc");
        List<RecordBean> recordBeanList = new ArrayList<>();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                //使用字段名先获取字段编号，再通过字段编号获取字段值
                //其中获得的id是int型，需转换
                String id = String.valueOf(cursor.getInt(cursor.getColumnIndex(MyConstant.CLOME_ID)));
                String record = cursor.getString(cursor.getColumnIndex(MyConstant.CLOME_RECORD));
                String time = cursor.getString(cursor.getColumnIndex(MyConstant.CLOME_TIME));
                RecordBean recordBean = new RecordBean(id, record, time);
                recordBeanList.add(recordBean);
            }
            cursor.close();
        }
        return recordBeanList;
    }

    //模糊查询的数据存入该集合
    public List<RecordBean> queryRecordByKeyword(String keyword) {
        Cursor cursor = db.query(MyConstant.TABLE_NAME, null, MyConstant.CLOME_RECORD + " like ?", new String[]{"%" + keyword + "%"}, null,
                null, MyConstant.CLOME_TIME + " desc");
        List<RecordBean> recordBeanList = new ArrayList<>();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                //使用字段名先获取字段编号，再通过字段编号获取字段值
                //其中获得的id是int型，需转换
                String id = String.valueOf(cursor.getInt(cursor.getColumnIndex(MyConstant.CLOME_ID)));
                String record = cursor.getString(cursor.getColumnIndex(MyConstant.CLOME_RECORD));
                String time = cursor.getString(cursor.getColumnIndex(MyConstant.CLOME_TIME));
                RecordBean recordBean = new RecordBean(id, record, time);
                recordBeanList.add(recordBean);
            }
            cursor.close();
        }
        return recordBeanList;
    }

    /**
     * 将User实例存储到数据库--注册
     */
    public int saveUser(User user) {
        if (user != null) {

            Cursor cursor = db.rawQuery("select * from User where username=?", new String[]{user.getUsername().toString()});
            if (cursor.getCount() > 0) {
                return -1;
            } else {
                try {
                    db.execSQL("insert into User(username,userpwd) values(?,?) ", new String[]{user.getUsername().toString(), user.getUserpwd().toString()});
                } catch (Exception e) {
                    Log.d("错误", e.getMessage().toString());
                }
                return 1;
            }
        } else {
            return 0;
        }
    }

    /**
     * 从数据库读取User信息--登录
     */
    public User login(String pwd, String name) {

        User user = null;
        Cursor cursor = db.rawQuery("select * from User where userpwd=? and username=?", new String[]{pwd, name});

        if (cursor.moveToNext()) {

            user = new User();
            user.setId(cursor.getInt(cursor.getColumnIndex("id")));
            user.setUsername(cursor.getString(cursor.getColumnIndex("username")));
            user.setUserpwd(cursor.getString(cursor.getColumnIndex("userpwd")));

        }


        return user;

    }
}
