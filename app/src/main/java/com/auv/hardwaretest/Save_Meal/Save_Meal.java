package com.auv.hardwaretest.Save_Meal;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.StrictMode;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.auv.annotation.PlatformEnum;
import com.auv.hardwareservice.ConnectService;
import com.auv.hardwareservice.HardwareService;
import com.auv.hardwaretest.BaseActivity;
import com.auv.hardwaretest.HttpOkHttp;
import com.auv.hardwaretest.Main_Data.Main_Data;
import com.auv.hardwaretest.R;
import com.auv.hardwaretest.Utility.Utilss;
import com.auv.hardwaretest.keyboard.KeyboardType;
import com.auv.hardwaretest.keyboard.OnNumberKeyboardListener;
import com.auv.hardwaretest.keyboard.XNumberKeyboardView;
import com.auv.model.AUVBoardCellInit;
import com.auv.model.AUVErrorCode;
import com.auv.model.AUVErrorRecover;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.codingending.popuplayout.PopupLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import es.dmoral.toasty.Toasty;

public class Save_Meal extends BaseActivity implements OnNumberKeyboardListener {
    private String barcode = "";
    private ImageView scan;
    private String state="";
    private String num= "";
    private EditText number ;
    private TextView text ;
   private XNumberKeyboardView keyboardView;
    @Override
    public int intiLayout() {
        return R.layout.save_meal;
    }

    @Override
    public void initView() {

        text = findViewById(R.id.text);
        text.setText("");
        number = findViewById(R.id.number);
        scan = findViewById(R.id.scan);
        Glide.with(Save_Meal.this).load(R.drawable.timg).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(scan);
        keyboardView = (XNumberKeyboardView) findViewById(R.id.view_keyboard);
        keyboardView.setOnNumberKeyboardListener(Save_Meal.this);
        keyboardView.setKeyboardType(KeyboardType.number);
        keyboardView.setSpecialKeyBackground(new ColorDrawable(ContextCompat.getColor(Save_Meal.this, R.color.colorKeyBlank)));
    }

    public void back(View view) {
        finish();
    }
    public void save(View view){
        if (number.length()>0){
            if (number.getText().toString().trim().equals("123123123")){
                sendData("DOOR_OPEN", "4");
            }else Toasty.error(Save_Meal.this, "存餐码错误").show();
        }else {
            Toasty.warning(Save_Meal.this, "请输入存餐码数字").show();
        }





    }

    @Override
    public void initData() {
        //实例化连接服务类
        ConnectService connectService = ConnectService.getInstance(PlatformEnum.CAN_BOARD,Save_Meal.this);
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
                toastShort(cellNo+"号已关门");
            }

            @Override
            public void onException(AUVErrorCode auvErrorCode) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        toastShort(auvErrorCode.toString());
                    }
                });

            }

            @Override
            public void resultListener(int cellNo, boolean success) {
                String info =  success?"成功":"失败";
              runOnUiThread(new Runnable() {
                  @Override
                  public void run() {
                      if (info.equals("成功")){
                          Toasty.success(Save_Meal.this, cellNo+"号柜门"+info).show();

                      }else if (info.equals("失败")){
                          Toasty.warning(Save_Meal.this, cellNo+"号柜门"+info).show();
                      }


                  }
              });
            }

            @Override
            public void onExceptionRecover(AUVErrorRecover auvErrorRecover) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        toastShort(auvErrorRecover.toString());
                    }
                });

            }

        });
            if (getIntent().getStringExtra("data")!=null){
                num = getIntent().getStringExtra("data");
                Log.d("numnum",num);
            }
           if (num.equals("123123123")){
              sendData("DOOR_OPEN", "4");
               Save_meal();
               Log.d("numnum",num);
         }


    }//初始化数据

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            Log.e("123123", "dispatchKeyEvent: " + event.toString());
            char pressedKey = (char) event.getUnicodeChar();
            barcode += pressedKey;
        }
        if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
            //  toastShort(barcode);
            Log.i("123123", barcode);
            if (barcode.trim().equals("123123123")) {
                sendData("DOOR_OPEN", "4");
              //  sendData("LIGHT_CLOSE", "4");

                Save_meal();
            }
            //  Toast.makeText(MainActivity.this,barcode,Toast.LENGTH_LONG).show();
            barcode = "";
            return true;
        }
        return super.dispatchKeyEvent(event);

    }//监听二维码数据


    private void Save_meal() {

        //识别到存餐码弹出存餐通知框
        View parent = View.inflate(Save_Meal.this, R.layout.play_save_meal, null);
        final PopupLayout popupLayout = PopupLayout.init(Save_Meal.this, parent);
        popupLayout.setHeight(400, true);
        popupLayout.setWidth(550, true);
        popupLayout.show(PopupLayout.POSITION_CENTER);//居中显示

        ImageView cancel = parent.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupLayout.dismiss();
            }
        });
        TextView time = parent.findViewById(R.id.time);
        Button open_two = parent.findViewById(R.id.open_two);

        open_two.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) { // 按下  
                    open_two.setBackgroundResource(R.drawable.btn00);
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) { // 松开   
                    open_two.setBackgroundResource(R.drawable.layer);
                }
                return false;
            }
        });
        open_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendData("DOOR_OPEN", "2");

              //  toastShort("open_two");
            }
        });
        CountDownTimer timer = new CountDownTimer(11000l, 1000l) {
            @Override
            public void onTick(long l) {
                time.setText(l / 1000 + "");
            }

            @Override
            public void onFinish() {
                popupLayout.dismiss();
            }
        }.start();//倒计时器
        popupLayout.show();


    } //识别到存餐码弹出存餐通知框

    public void sendData(String command, String cellNo) {

        String method = "device.cell.op";
        String timestamp = Utilss.getDateTime();
        String bizData = "{\"cellNo\":\"" + cellNo + "\",\"op\":\"" + command + "\",\"deviceId\":\"" + Main_Data.deviceName + "\"}";
        String version = "1.0";
        String digest = "";
        String url = "http://openapi.58auv.com/gateway.do";
        String param = "?appId=" + Main_Data.appId +
                "&method=" + method +
                "&timestamp=" + timestamp +
                "&bizData=" + bizData +
                "&version=" + version;
        //生成验证信息
        digest = Utilss.encryption(param, Main_Data.secretKey);
        //拼接验证信息
        param += "&digest=" + digest;
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        //showToast(Utilss.post(url+param,""));
        Map<String, String> map = new HashMap<>();
        map.put("appId", Main_Data.appId);
        map.put("method", method);
        map.put("timestamp", timestamp);
        map.put("bizData", bizData);
        map.put("version", version);
        map.put("digest", digest);

        HttpOkHttp.getInstance().requestPost(url, map, new HttpOkHttp.OkHttpCallBack<String>() {
            @Override
            public void requestSuccess(String s) {
                Log.d("121212", s);
            }

            @Override
            public void requestFailure(String message) {
                Log.d("121212", message);
            }
        }, String.class);
    }//对于智能柜的操作

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
    }

    @Override
    public void onNumberKey(int keyCode, String insert) {
        // 右下角按键的点击事件，删除一位输入的文字
        if (keyCode == XNumberKeyboardView.KEYCODE_BOTTOM_RIGHT) {
            int start = number.length() - 1;
            if (start >= 0) {
                number.getText().delete(start, start + 1);
            }
        }
        // 左下角按键和数字按键的点击事件，输入文字
        else {
            number.append(insert);
        }
    }
}
