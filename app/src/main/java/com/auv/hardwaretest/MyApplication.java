package com.auv.hardwaretest;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import com.auv.alink.devicesdk.app.AUVIotApplication;
import com.auv.hardwaretest.Main_Data.Main_Data;

import static com.aliyun.alink.linksdk.tools.ThreadTools.runOnUiThread;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        String  deviceInfo = "{" +
                "'productKey':'"+ Main_Data.productKey+"'," +
                "'deviceName':'"+Main_Data.deviceName+"'," +
                "'deviceSecret':'"+Main_Data.deviceSecret+"'" +
                "}";
        //连接阿里云服务器
        AUVIotApplication.getInstance(MyApplication.this, deviceInfo, (success, msg) -> {
            String info =  success?"成功":"失败";
         //   showToast("连接"+info+";"+msg);
        });
    }

    /**
     * 提示框
     * @param message  提示信息
     */
    public void showToast(final String message) {
        Log.d("showToast() ", " message = " + message);
        runOnUiThread(() -> {
            int  time = Toast.LENGTH_SHORT;
            try{
                Toast.makeText(getApplicationContext(), message,time).show();
            }catch (Exception e){
                e.printStackTrace();
            }

        });
    }
}
/*    第三方吐司使用
原生：Toasty.normal(context, "normal").show();
普通：Toasty.info(context, "info").show();
成功：Toasty.success(context, "success").show();
警告：Toasty.warning(context, "warning").show();
失败：Toasty.error(context, "error").show();
自定义：Toasty.custom(context, "cutstom", getResources().getDrawable(R.mipmap.ic_launcher),getResources().getColor(R.color.color1) , getResources().getColor(R.color.color2), Toast.LENGTH_SHORT, true, true).show();
*
*
* */