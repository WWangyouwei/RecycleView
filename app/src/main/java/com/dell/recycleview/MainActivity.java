package com.dell.recycleview;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity{

    private RecyclerView mRv;
    private String url = "http://result.eolinker.com/iYXEPGn4e9c6dafce6e5cdd23287d2bb136ee7e9194d3e9?uri=one";
    private CheckBox mCheckbox;
    private MyAdapter myAdapter;
    private Bean.DataBean bean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        mRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        x.http().get(new RequestParams(url), new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                Bean bean = new Gson().fromJson(result, Bean.class);
                myAdapter = new MyAdapter(MainActivity.this, bean.getData());
                mRv.setAdapter(myAdapter);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    private void initView() {
        mRv = (RecyclerView) findViewById(R.id.my_recycler_view);
        mCheckbox = (CheckBox) findViewById(R.id.quanbu);
        bean = new Bean.DataBean();

        mCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCheckbox.isChecked()){
                    mCheckbox.setChecked(true);
                    myAdapter.selectedAll();


                }else{
                    mCheckbox.setChecked(false);
                    myAdapter.cancleAll();
                }

                boolean b = mCheckbox.isChecked();
            }
        });

    }

    public void setCb(boolean bool) {
        mCheckbox.setChecked(bool);
    }
}
