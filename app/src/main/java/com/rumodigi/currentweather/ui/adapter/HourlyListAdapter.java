package com.rumodigi.currentweather.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.rumodigi.currentweather.R;
import com.rumodigi.domain.models.HourlyForecastModel;
import com.rumodigi.domain.models.HourlyModel;

import java.util.ArrayList;
import java.util.List;

public class HourlyListAdapter extends RecyclerView.Adapter<HourlyListAdapter.MyViewHolder> {
    private HourlyModel hourlyModel;
    private List<HourlyForecastModel> data;

    public HourlyListAdapter(){
        data = new ArrayList<>();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ConstraintLayout layout = (ConstraintLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.houry_data, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(layout);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.summary.setText(data.get(position).getSummary());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(HourlyModel hourlyModel){
        this.hourlyModel = hourlyModel;
        data.clear();
        data.addAll(this.hourlyModel.getData());
        this.notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView summary;
        MyViewHolder(View v) {
            super(v);
            summary = v.findViewById(R.id.summaryDetails);
        }
    }
}
