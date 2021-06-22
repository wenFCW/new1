package com.example.new1;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class RecordActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mNoteBack;
    private TextView mNoteName;
    private EditText mNoteConten;
    private ImageView mDelete;
    private ImageView mNoteSave;
    private MyOpenHelper myOpenHelper;
    private String id;

    @Override
    protected void onCreate(@Nullable Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.hide();
        }
        setContentView(R.layout.record);
        initView();
        initListener();
        //打开数据库、数据表
        myOpenHelper = new MyOpenHelper(this);
        //判断是添加记录还是修改记录
        Intent intent =getIntent();
        id = intent.getStringExtra("id");
        if(id!=null){
            //修改记录
            mNoteName.setText("修改记录");
            mNoteConten.setText(intent.getStringExtra("record"));
        }else{
            //添加记录
            mNoteName.setText("添加记录");
        }


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.note_back:
                finish();
                break;
            case R.id.delete:
                mNoteConten.setText("");
                break;
            case R.id.note_save:
                String noteContent = mNoteConten.getText().toString().trim();
                String time = MyConstant.getTime();
                //判断是新添加保存还是修改后保存
                if(id!=null){
                    //修改后保存
                    if(noteContent.length()>0){
                        int i =myOpenHelper.updateRecord(id,noteContent,time);
                        if(i>0){
                            Toast.makeText(this,"修改成功",Toast.LENGTH_SHORT).show();
                            setResult(RESULT_OK);
                            finish();//结束当前界面
                        }
                    }
                }else{
                    //新添加保存
                    if(noteContent.length()>0){
                        long n = myOpenHelper.inserRecord(noteContent,time);
                        if(n>0){
                            Toast.makeText(this,"添加成功",Toast.LENGTH_SHORT).show();
                            setResult(RESULT_OK);
                            finish();
                        }
                    }
                }
                break;
        }

    }
    private  void initListener(){
        mNoteBack.setOnClickListener(this);
        mDelete.setOnClickListener(this);
        mNoteSave.setOnClickListener(this);
    }

    private void initView(){
        mNoteBack = findViewById(R.id.note_back);
        mNoteName = findViewById(R.id.note_name);
        mNoteConten = findViewById(R.id.note_content);
        mDelete = findViewById(R.id.delete);
        mNoteSave = findViewById(R.id.note_save);
    }
}
