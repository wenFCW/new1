package com.example.new1;
//业务类
public class RecordBean {
    //用来存放每一条记录的对象的。
    private String id;
    private String record;//记录内容
    private String time; //当前时间

    public RecordBean(){
    }

    public RecordBean(String id,String record,String time){
        this.id=id;
        this.record=record;
        this.time=time;
    }

    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id=id;
    }

    public String getRecord(){
        return record;
    }

    public void setRecord(String record){
        this.record=record;
    }

    public String getTime(){
        return time;
    }

    public void setTime(String time){
        this.time=time;
    }


}
