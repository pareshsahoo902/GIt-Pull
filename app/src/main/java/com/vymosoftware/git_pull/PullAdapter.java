package com.vymosoftware.git_pull;

import android.content.Context;
import android.graphics.Color;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vymosoftware.git_pull.API.PullModel;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class PullAdapter extends RecyclerView.Adapter<PullAdapter.PullViewHolder> {

    private List<PullModel> pullModelList;
    private Context mContext;
    public static final SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");



    public PullAdapter(List<PullModel> pullModelList, Context mContext) {
        this.pullModelList = pullModelList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public PullViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PullViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.pull_items,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull PullViewHolder holder, int position) {

        String title = pullModelList.get(position).getTitle();
        String state = pullModelList.get(position).getState();
        String date = pullModelList.get(position).getCreatedAt();

        holder.title.setText(title);
        holder.status.setText(state);
        holder.created.setText(getDays(date));
        holder.pr_number.setText("#"+String.valueOf(pullModelList.get(position).getId()));
        if (state.equals("open")){
            holder.status_color.setBackgroundColor(Color.GREEN);
        }
        else {
            holder.status_color.setBackgroundColor(Color.RED);
        }


    }

    @Override
    public int getItemCount() {
        return pullModelList.size();
    }

    public class PullViewHolder extends RecyclerView.ViewHolder{

        public TextView title , pr_number, status, created;
        public ImageView status_color;

        public PullViewHolder(@NonNull View itemView) {
            super(itemView);

            title =itemView.findViewById(R.id.pulltitletext);
            pr_number =itemView.findViewById(R.id.prnumbertext);
            status =itemView.findViewById(R.id.pull_statustext);
            created =itemView.findViewById(R.id.created_text);

            status_color =itemView.findViewById(R.id.pullcolor);
        }
    }

    public String getDays(String date){

            String convTime = null;

            String prefix = "";
            String suffix = "Ago";

            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                Date pasTime = dateFormat.parse(date);

                Date nowTime = new Date();

                long dateDiff = nowTime.getTime() - pasTime.getTime();

                long second = TimeUnit.MILLISECONDS.toSeconds(dateDiff);
                long minute = TimeUnit.MILLISECONDS.toMinutes(dateDiff);
                long hour   = TimeUnit.MILLISECONDS.toHours(dateDiff);
                long day  = TimeUnit.MILLISECONDS.toDays(dateDiff);

                if (second < 60) {
                    convTime = second + " Seconds " + suffix;
                } else if (minute < 60) {
                    convTime = minute + " Minutes "+suffix;
                } else if (hour < 24) {
                    convTime = hour + " Hours "+suffix;
                } else if (day >= 7) {
                    if (day > 360) {
                        convTime = (day / 360) + " Years " + suffix;
                    } else if (day > 30) {
                        convTime = (day / 30) + " Months " + suffix;
                    } else {
                        convTime = (day / 7) + " Week " + suffix;
                    }
                } else if (day < 7) {
                    convTime = day+" Days "+suffix;
                }

            } catch (ParseException e) {
                e.printStackTrace();
                Log.e("ConvTimeE", e.getMessage());
            }

         return convTime;
    }
}
