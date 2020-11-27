package com.evs.automedic.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.evs.automedic.R;
import com.evs.automedic.model.NotifcationModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyViewHolder> {
    private Context context;
    private OnClickListener listener;
    private ArrayList<NotifcationModel> list;

    public HistoryAdapter(Context context) {
        this.context = context;
        this.list = new ArrayList<>();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.frag_history_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
//        if (list != null) {
//            holder.tv_notification_title.setText(list.get(position).getMessage());
//            String date = list.get(position).getCreated();
//            String[] datesplit = date.split(" ");
//            String newsplitDate = datesplit[0].trim();
//            String newSplitDateafter = datesplit[1].trim();
//            String newDateFormat = Global.parseDate(newsplitDate, ymdFormat, EEEddMMMyyyy);
//            String[] newadatesplit = newDateFormat.split("-");
//            String dateNumber = newadatesplit[0].trim();
//            String dateMonth = newadatesplit[1].trim();
//            holder.tv_notification_date.setText("Date :" + " " + newDateFormat);
//        }
    }

    public void update(List<NotifcationModel> category) {
        this.list.clear();
        this.list.addAll(category);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public void setOnClickListener(OnClickListener listener) {
        this.listener = listener;
    }

    public interface OnClickListener {
        void onClick(HistoryAdapter adapter, int position);
    }

    public NotifcationModel get(int position) {
        return list.get(position);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private OnClickListener listener;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (listener != null) {
                listener.onClick(getAdapterPosition());
            }

        }

        public void setOnClickListener(OnClickListener listener) {
            this.listener = listener;
        }

        public interface OnClickListener {
            void onClick(int position);
        }
    }


}
