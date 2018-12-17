package com.donaldark.pfccalculation;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

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
        myRecyclerViewHolder.tv_firstLine.setText(data.get(position).getFirstLine() + " калорий");

        DateTimeFormatter fmt = DateTimeFormat.forPattern("dd.MM.yyyy");

        DateTime dt =fmt.parseDateTime(data.get(position).getDate());


                myRecyclerViewHolder.tv_date.setText(dt.getDayOfMonth() + " " + dt.monthOfYear().getAsText() + " " + dt.getYear() + " года");
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public class MyRecyclerViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_firstLine;
        public TextView tv_date;

        public MyRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_firstLine = (TextView) itemView.findViewById(R.id.growth_text_main);
            tv_date = (TextView) itemView.findViewById(R.id.growth_date);
        }
    }
}
