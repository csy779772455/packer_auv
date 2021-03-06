package com.auv.hardwaretest;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {
    /***封装toast对象**/
    private static Toast toast;
    /***获取TAG的activity名称**/
    protected final String TAG = this.getClass().getSimpleName();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(intiLayout());
        //初始化控件
        initView();
        //设置数据
        initData();

    }

    /**
     * 设置布局
     *
     * @return
     */
    public abstract int intiLayout();

    /**
     * 初始化布局
     */
    public abstract void initView();

    /**
     * 设置数据
     */
    public abstract void initData();

/*    *//**
     * 是否设置标题栏
     *
     * @return
     *//*
    public void setTitle(boolean ishow) {
        isshowtitle=ishow;
    }

    *//**
     * 设置是否显示状态栏
     * @param ishow
     *//*
    public void setState(boolean ishow) {
        isshowstate=ishow;
    }*/

    /**
     * 显示长toast
     * @param msg
     */
    public void toastLong(String msg){
        if (null == toast) {
            toast = new Toast(this);
            toast.setDuration(Toast.LENGTH_LONG);
            toast.setText(msg);
            toast.show();
        } else {
            toast.setText(msg);
            toast.show();
        }
    }

    /**
     * 显示短toast
     * @param msg
     */
    public void toastShort(String msg){
    /*    if (null == toast) {
            toast = new Toast(this);
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setText(msg);
            toast.show();
        } else {
            toast.setText(msg);
            toast.show();
        }*/
    Toast.makeText(this,msg, Toast.LENGTH_SHORT).show();
    }

}
