package com.auv.hardwaretest;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.auv.annotation.PlatformEnum;
import com.auv.hardwareservice.ConnectService;
import com.auv.hardwareservice.HardwareService;

import com.auv.hardwaretest.Main_Data.Main_Data;
import com.auv.hardwaretest.Utility.Utilss;
import com.auv.model.AUVBoardCellInit;
import com.auv.model.AUVErrorCode;
import com.auv.model.AUVErrorRecover;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity {


    public  String  controlCell = "1";

    private EditText  editText ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.text);
    }

    public void content(View view){
        //实例化连接服务类
        ConnectService connectService = ConnectService.getInstance(PlatformEnum.CAN_BOARD,MainActivity.this);
        //初始化 主柜数量(BoardNo)  与  该主柜所拥有的格子数量(CellCount)
        List<AUVBoardCellInit>  boardInfoList = new ArrayList<>();
        for(int i = 1; i<2;i++){
            AUVBoardCellInit  boardInfo = new AUVBoardCellInit();
            boardInfo.setBoardNo(i);
            boardInfo.setCellCount(32);
            boardInfoList.add(boardInfo);
        }
        //启动控制板
        connectService.startCouplingTransaction("/dev/ttyS4",boardInfoList);
        //监听执行结果
        connectService.setOnHardwareEventListener(new HardwareService.ResultListener() {
            @Override
            public void feedbackDoorClosedListener(int cellNo) {
                showToast(cellNo+"号已关门");
            }

            @Override
            public void onException(AUVErrorCode auvErrorCode) {
                showToast(auvErrorCode.toString());
            }

            @Override
            public void resultListener(int cellNo, boolean success) {
                String info =  success?"成功":"失败";
                showToast(cellNo+"号执行"+info);
            }

            @Override
            public void onExceptionRecover(AUVErrorRecover auvErrorRecover) {
                showToast(auvErrorRecover.toString());
            }

        });
        showToast("建立连接成功");
    }



    public void openDoor(View view){
        controlCell = editText.getText().toString();

        sendData("DOOR_OPEN",controlCell);

    }
    public void openLight(View view){
        controlCell = editText.getText().toString();
        sendData("LIGHT_OPEN",controlCell);

    }
    public void closeLight(View view){
        controlCell = editText.getText().toString();
        sendData("LIGHT_CLOSE",controlCell);

    }
    public void openDis(View view){
        controlCell = editText.getText().toString();
        sendData("DISINFECT_START",controlCell);

    }
    public void closeDis(View view){
        controlCell = editText.getText().toString();
        sendData("DISINFECT_STOP",controlCell);

    }
    public void openHeating(View view){
        controlCell = editText.getText().toString();
        sendData("WARM_START",controlCell);

    }
    public void closeHeating(View view)
    {
        controlCell = editText.getText().toString();
        sendData("WARM_STOP",controlCell);
    }

    public void  sendData(String command, String cellNo){
        String method = "device.cell.op";
        String timestamp = Utilss.getDateTime();
        String bizData = "{\"cellNo\":\""+cellNo+"\",\"op\":\""+command+"\",\"deviceId\":\""+ Main_Data.deviceName+"\"}";
        String version = "1.0";
        String digest = "";
        String url ="http://openapi.58auv.com/gateway.do";
        String param = "?appId="+Main_Data.appId+
                        "&method="+method+
                        "&timestamp="+timestamp+
                        "&bizData="+bizData+
                        "&version="+version;
        //生成验证信息
        digest = Utilss.encryption(param,Main_Data.secretKey);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String finalDigest = digest;
        //拼接验证信息
        Map<String,String> map = new HashMap<>();
        map.put("appId",Main_Data.appId);
        map.put("method",method);
        map.put("timestamp",timestamp);
        map.put("bizData",bizData);
        map.put("version",version);
        map.put("digest",digest);

        HttpOkHttp.getInstance().requestPost(url , map, new HttpOkHttp.OkHttpCallBack<String>() {
            @Override
            public void requestSuccess(String s) {
                Log.d("121212",s);
            }

            @Override
            public void requestFailure(String message) {
                Log.d("121212",message);
            }
        },String.class);
       /* Map<String,Object> map = new HashMap<>();
                    map.put("appId",appId);
                    map.put("method",method);
                    map.put("timestamp",timestamp);
                    map.put("bizData",bizData);
                    map.put("version",version);
                    map.put("digest", finalDigest);

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                OkHttpRequestUtils.doPost(url,map,map);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
*/

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
                Toast.makeText(getBaseContext(), message,time).show();
            }catch (Exception e){
                e.printStackTrace();
            }

        });
    }



}
