package com.example.new1;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int SEND_CORD = 1111;
    private RecyclerView recyclerView;
    private MyOpenHelper myOpenHelper;
    private List<RecordBean> recordBeanList = new ArrayList<>();
    private NoteAdapter notepadAdaper;
    private EditText et_search;
    private ImageView btn_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        setContentView(R.layout.activity_main);
        initView();
        //显示数据库中记录到列表控件
        myOpenHelper = new MyOpenHelper(this);
        showData();

        //添加记录按钮，跳转到RecordActivity
        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        /**
         * 点击搜索按钮
         */

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String search = et_search.getText().toString().trim();
                if (TextUtils.isEmpty(search)) {//如果为空，加载全部数据
                    showData();
                } else {

                    //显示数据库中记录到列表控件
                    recordBeanList.clear();
                    recordBeanList.addAll(myOpenHelper.queryRecordByKeyword(search));//模糊查询数据
                    notepadAdaper.notifyDataSetChanged();

                }
            }
        });
    }

    private void showData() {
        //显示数据库中记录到列表控件
        recordBeanList.clear();
        recordBeanList.addAll(myOpenHelper.queryRecord());//查询数据

        notepadAdaper.notifyDataSetChanged();
    }

    //从RecordActivity界面返回后，刷新列表，显示数据
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SEND_CORD && resultCode == RESULT_OK) {
            //查询保存在数据库中的全部数据
            showData();
        }
    }

    private void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.listview);
        et_search = (EditText) findViewById(R.id.et_search);
        btn_search = (ImageView) findViewById(R.id.btn_search);

        notepadAdaper = new NoteAdapter(recordBeanList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(notepadAdaper);

        notepadAdaper.setListener(new NoteAdapter.Listener() {
            @Override
            public void onClick(RecordBean recordBean) {

            }

            @Override
            public void onLongClick(final RecordBean recordBean) {

                AlertDialog alertDialog;
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("删除记录吗?").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //从数据表中删除长按的那条记录。
                        int n = myOpenHelper.deleteRecord(recordBean.getId());
                        if (n > 0) {
                            //从集合中删除长按的那条记录。
                            recordBeanList.remove(recordBean);
                            notepadAdaper.notifyDataSetChanged();//刷新适配器
                            Toast.makeText(MainActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                            dialogInterface.dismiss();
                        }
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                alertDialog = builder.create();//初始化对话框
                alertDialog.show();//对话框显示
            }
        });
    }


}
