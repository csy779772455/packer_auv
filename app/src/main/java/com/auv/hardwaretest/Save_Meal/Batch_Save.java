package com.auv.hardwaretest.Save_Meal;

import android.graphics.Color;
import android.graphics.Rect;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.auv.hardwaretest.BaseActivity;
import com.auv.hardwaretest.R;

import java.util.ArrayList;
import java.util.List;

public class Batch_Save extends BaseActivity {
    private LinearLayout back ;
    private TextView title_text;
    private RecyclerView mRecycler;
    private  String state="1";
    private Button check_all,batch;//全选，批量
    private  TextView to_save,existing;//待存，已存
    private Batch_Adapter batch_adapter;
    private List<Save_Bill_bean> to_saveList = new ArrayList<>();
    private List<Save_Bill_bean> existingList = new ArrayList<>();
    @Override
    public int intiLayout() {
        return R.layout.batch_save;
    }

    @Override
    public void initView() {
        bindId();
        for (int i =0;i<16;i++){
            to_saveList.add(new Save_Bill_bean("2","#0127","34431311324","套餐A","2688","餐餐乐","2020-3-2 午餐","2","2"));
        }
        for (int i =0;i<16;i++){
            existingList.add(new Save_Bill_bean("2","#0127","34431311324","套餐A","2688","餐餐乐","2020-3-2 午餐","2","1"));
        }
        batch_adapter = new Batch_Adapter(to_saveList);
        //定义布局管理器为Grid管理器，设置一行放3个
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(Batch_Save.this, 3);
        //设置布局管理器为线性布局管理器
        mRecycler.setLayoutManager(layoutManager);
        batch_adapter.setOnItemClickLitener(new Batch_Adapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {

                    if (to_saveList.get(position).getSave_item().equals("1")){
                        to_saveList.get(position).setSave_item("2");
                        batch_adapter.notifyDataSetChanged();
                    }else if (to_saveList.get(position).getSave_item().equals("2")){
                        to_saveList.get(position).setSave_item("1");
                        batch_adapter.notifyDataSetChanged();
                    }
              // Toast.makeText(Batch_Save.this,"这是条目"+position,Toast.LENGTH_LONG).show();
            }
        });
        //设置适配器
        mRecycler.setAdapter(batch_adapter);

        //设置分隔线
         mRecycler.addItemDecoration(new MyDecoration());

    }

    @Override
    public void initData() { }
   private  void  bindId(){
    check_all = findViewById(R.id.check_all);
    batch = findViewById(R.id.batch);
    to_save = findViewById(R.id.to_save);
    existing = findViewById(R.id.existing);
    mRecycler = findViewById(R.id.recycler);
    back = findViewById(R.id.back);
    to_save.setTextColor(Color.parseColor("#81D4FA"));
    title_text =findViewById(R.id.title_text);
    title_text.setText("批量存餐");
    mRecycler = findViewById(R.id.recycler);
    back.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
        }
    });
    to_save.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            state = "1";
            existing.setTextColor(Color.parseColor("#000000"));
            to_save.setTextColor(Color.parseColor("#81D4FA"));
            batch.setText("批量存餐");
            batch_adapter = new Batch_Adapter(to_saveList);
            mRecycler.setAdapter(batch_adapter);
            batch_adapter.setOnItemClickLitener(new Batch_Adapter.OnItemClickLitener() {
                @Override
                public void onItemClick(View view, int position) {

                    if (to_saveList.get(position).getSave_item().equals("1")){
                        to_saveList.get(position).setSave_item("2");
                        batch_adapter.notifyDataSetChanged();
                    }else if (to_saveList.get(position).getSave_item().equals("2")){
                        to_saveList.get(position).setSave_item("1");
                        batch_adapter.notifyDataSetChanged();
                    }
                    // Toast.makeText(Batch_Save.this,"这是条目"+position,Toast.LENGTH_LONG).show();
                }
            });
        }
    });
    existing.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            state = "2";
            existing.setTextColor(Color.parseColor("#81D4FA"));
            to_save.setTextColor(Color.parseColor("#000000"));
            batch.setText("重新存餐");
            batch_adapter = new Batch_Adapter(existingList);
            mRecycler.setAdapter(batch_adapter);
            batch_adapter.setOnItemClickLitener(new Batch_Adapter.OnItemClickLitener() {
                @Override
                public void onItemClick(View view, int position) {
                    if (existingList.get(position).getSave_item().equals("1")){
                        existingList.get(position).setSave_item("2");
                        batch_adapter.notifyDataSetChanged();
                    }else if (existingList.get(position).getSave_item().equals("2")){
                        existingList.get(position).setSave_item("1");
                        batch_adapter.notifyDataSetChanged();

                    }


                    // Toast.makeText(Batch_Save.this,"这是条目"+position,Toast.LENGTH_LONG).show();
                }
            });
        }
    });
    check_all .setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (state.equals("1")){
                for (int i = 0;i<to_saveList.size();i++){
                    if (to_saveList.get(i).getSave_item().equals("1")){
                        to_saveList.get(i).setSave_item("2");

                    }else  to_saveList.get(i).setSave_item("1");


                }


            }else if (state.equals("2")){
                for (int i = 0;i<to_saveList.size();i++){
                    if (existingList.get(i).getSave_item().equals("1")){
                        existingList.get(i).setSave_item("2");
                    }else  existingList.get(i).setSave_item("1");
                }
            }
            batch_adapter.notifyDataSetChanged();
        }
    });
    batch.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    });
}

}
class MyDecoration extends RecyclerView.ItemDecoration{
    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        //outRect.set()中的参数分别对应左、上、右、下的间隔
        outRect.set(8,8,8,8);
    }
}