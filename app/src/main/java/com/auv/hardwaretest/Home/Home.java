package com.auv.hardwaretest.Home;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.auv.hardwaretest.BaseActivity;
import com.auv.hardwaretest.R;
import com.auv.hardwaretest.Save_Meal.Save_Meal;
import com.codingending.popuplayout.PopupLayout;

public class Home extends BaseActivity {
    private LinearLayout vending ;
    private Button vending_bt;
    private LinearLayout take_fool ;
    private Button take_fool_bt;
    private LinearLayout Save_meal ;
    private Button Save_meal_bt;
    private String barcode = "";

    @Override
    public int intiLayout() {
        return R.layout.home;
    }

    @Override
    public void initView() {
        //自动售卖
        vending = findViewById(R.id.vending);
        vending_bt = findViewById(R.id.vending_bt);
        vending.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) { // 按下  
                    vending.setBackgroundResource(R.drawable.bt_3);
                    vending_bt.setBackgroundResource(R.drawable.icon11);
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) { // 松开   
                    vending.setBackgroundResource(R.drawable.white);
                    vending_bt.setBackgroundResource(R.drawable.icon1);
                }
                return false;
            }
        });
        vending_bt.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) { // 按下  
                    vending.setBackgroundResource(R.drawable.bt_3);
                    vending_bt.setBackgroundResource(R.drawable.icon11);
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) { // 松开   
                    vending.setBackgroundResource(R.drawable.white);
                    vending_bt.setBackgroundResource(R.drawable.icon1);
                }
                return false;
            }
        });
        vending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toastShort("您点击了自动售卖");
            }
        });
        vending_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toastShort("您点击了自动售卖");
            }
        });

        //取餐
        take_fool = findViewById(R.id.take_fool);
        take_fool_bt = findViewById(R.id.take_fool_bt);
        take_fool.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) { // 按下  
                    take_fool.setBackgroundResource(R.drawable.bt_3);
                    take_fool_bt.setBackgroundResource(R.drawable.icon21);
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) { // 松开   
                    take_fool.setBackgroundResource(R.drawable.white);
                    take_fool_bt.setBackgroundResource(R.drawable.icon2);
                }
                return false;
            }
        });
        take_fool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toastShort("您点击了取餐");
            }
        });
        take_fool_bt.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) { // 按下  
                    take_fool.setBackgroundResource(R.drawable.bt_3);
                    take_fool_bt.setBackgroundResource(R.drawable.icon21);
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) { // 松开   
                    take_fool.setBackgroundResource(R.drawable.white);
                    take_fool_bt.setBackgroundResource(R.drawable.icon2);
                }
                return false;
            }
        });
        take_fool_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toastShort("您点击了取餐");
            }
        });

        //存餐
        Save_meal = findViewById(R.id.Save_meal);
        Save_meal_bt = findViewById(R.id.Save_meal_bt);
        Save_meal.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) { // 按下  
                    Save_meal.setBackgroundResource(R.drawable.bt_3);
                    Save_meal_bt.setBackgroundResource(R.drawable.icon31);
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) { // 松开   
                    Save_meal.setBackgroundResource(R.drawable.white);
                    Save_meal_bt.setBackgroundResource(R.drawable.icon3);
                }
                return false;
            }
        });
        Save_meal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Home.this,Save_Meal.class);
                startActivity(intent);
                toastShort("您点击了存餐");
            }
        });
        Save_meal_bt.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) { // 按下  
                    Save_meal.setBackgroundResource(R.drawable.bt_3);
                    Save_meal_bt.setBackgroundResource(R.drawable.icon31);
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) { // 松开   
                    Save_meal.setBackgroundResource(R.drawable.white);
                    Save_meal_bt.setBackgroundResource(R.drawable.icon3);
                }
                return false;
            }
        });
        Save_meal_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Home.this, Save_Meal.class);
                startActivity(intent);
                toastShort("您点击了存餐");
            }
        });
       // Save_meal();
      //  deviceId.setText(Main_Data.deviceName);
    }

    @Override
    public void initData() {
    //   Save_meal();
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
                Intent intent = new Intent(Home.this,Save_Meal.class);
                intent.putExtra("data",barcode.trim());
                intent.putExtra("state","1");//存餐
                startActivity(intent);
              // toastShort( barcode);
            }
            //  Toast.makeText(MainActivity.this,barcode,Toast.LENGTH_LONG).show();
            barcode = "";
            return true;
        }
        return super.dispatchKeyEvent(event);

    }//监听二维码数据



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        setTheme(R.style.AppTheme_Launcher);
    }


    private void Save_meal() {

        //识别到存餐码弹出存餐通知框
        View parent = View.inflate(Home.this, R.layout.play_save_meal, null);
        final PopupLayout popupLayout = PopupLayout.init(Home.this, parent);
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
                toastShort("open_two");
            }
        });
        CountDownTimer timer = new CountDownTimer(111000l, 1000l) {
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




}
