package com.donaldark.pfccalculation;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyRecyclerViewHolder> {

    Context context;
    List<ResultData> data;

    public MyRecyclerViewAdapter(Context context, List<ResultData> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public MyRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(context).inflate(R.layout.fragment_result_list_item, parent, false);
        MyRecyclerViewHolder vHolder = new MyRecyclerViewHolder(v);
        return vHolder;
    }

    public void setData(List<ResultData> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull MyRecyclerViewHolder myRecyclerViewHolder, int position) {
        myRecyclerViewHolder.tv_firstLine.setText(data.get(position).getFirstLine());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public class MyRecyclerViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_firstLine;

        public MyRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_firstLine = (TextView) itemView.findViewById(R.id.growth_text_main);
        }
    }
}
