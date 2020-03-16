package com.auv.hardwaretest.Save_Meal;

import android.graphics.Color;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.auv.hardwaretest.R;

import java.util.List;

public class Batch_Adapter extends RecyclerView.Adapter <Batch_Adapter.MyViewHolder> {
    private List<Save_Bill_bean> fruitList;
    private OnItemClickLitener   mOnItemClickLitener;
    public Batch_Adapter(List<Save_Bill_bean> fruitList){
        this.fruitList=fruitList;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        private LinearLayout save_item;
        private TextView serial_number;//流水号
        private TextView ood;//订单号
        private TextView cuisine;//菜品
        private TextView phone;//手机尾号
        private TextView stall;//档口
        private TextView time;//时间
        private TextView cell;//单元格
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            serial_number = itemView.findViewById(R.id.serial_number);
            ood = itemView.findViewById(R.id.ood);
            cuisine = itemView.findViewById(R.id.cuisine);
            phone = itemView.findViewById(R.id.phone);
            stall = itemView.findViewById(R.id.stall);
            time = itemView.findViewById(R.id.time);
            cell = itemView.findViewById(R.id.cell);
            save_item = itemView.findViewById(R.id.save_item);
        }
    }

    @NonNull
    @Override
    public Batch_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.batch_save_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Batch_Adapter.MyViewHolder holder, int position) {
        //设置显示的文字
        holder.serial_number.setText("流水号"+fruitList.get(position).getSerial_number());
        holder.ood.setText("订单号："+fruitList.get(position).getOod());
        holder.cuisine.setText("菜品："+fruitList.get(position).getCuisine());
        holder.phone.setText("手机尾号："+fruitList.get(position).getPhone());
        holder.stall.setText("档口："+fruitList.get(position).getStall());
        holder.time.setText("时间："+fruitList.get(position).getTime());
        holder.cell.setText("单元格："+fruitList.get(position).getCell());
        if (mOnItemClickLitener != null) {
            holder.save_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickLitener.onItemClick(view, position);
                }
            });
        }
        if (fruitList.get(position).getState().equals("1")){
            holder.cell.setVisibility(View.VISIBLE);
        }else if (fruitList.get(position).getState().equals("2")){
            holder.cell.setVisibility(View.GONE);
        }

        if (fruitList.get(position).getSave_item().equals("1")){
            holder.save_item.setBackgroundResource(R.drawable.button1_style);
            holder.serial_number.setTextColor(Color.parseColor("#ffffff"));
            holder.ood.setTextColor(Color.parseColor("#ffffff"));
            holder.cuisine.setTextColor(Color.parseColor("#ffffff"));
            holder.phone.setTextColor(Color.parseColor("#ffffff"));
            holder.stall.setTextColor(Color.parseColor("#ffffff"));
            holder.time.setTextColor(Color.parseColor("#ffffff"));
            holder.cell.setTextColor(Color.parseColor("#ffffff"));

        }else if (fruitList.get(position).getSave_item().equals("2")){
            holder.save_item.setBackgroundResource(R.drawable.box);
            holder.serial_number.setTextColor(Color.parseColor("#000000"));
            holder.ood.setTextColor(Color.parseColor("#000000"));
            holder.cuisine.setTextColor(Color.parseColor("#000000"));
            holder.phone.setTextColor(Color.parseColor("#000000"));
            holder.stall.setTextColor(Color.parseColor("#000000"));
            holder.time.setTextColor(Color.parseColor("#000000"));
            holder.cell.setTextColor(Color.parseColor("#000000"));

        }


    }

    @Override
    public int getItemCount() {
        //设置显示的item数量为fruitList列表的元素的数量
        return fruitList.size();
    }
    //设置回调接口
    public interface OnItemClickLitener{
        void onItemClick(View view, int position);
    }

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener){
        this.mOnItemClickLitener = mOnItemClickLitener;
    }
}

