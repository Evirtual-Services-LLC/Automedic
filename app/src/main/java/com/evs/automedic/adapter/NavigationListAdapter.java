package com.evs.automedic.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.evs.automedic.R;


import java.util.LinkedHashMap;

public class NavigationListAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater layoutInflater = null;
    private LinkedHashMap<String, Integer> data;
    private   ViewHolder holder;;

    public NavigationListAdapter(Context context, LinkedHashMap<String, Integer> map) {
        this.mContext = context;
        this.data = map;
        layoutInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.keySet().toArray()[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView = layoutInflater.inflate(R.layout.list_adapter_item, null);
            holder = new ViewHolder();

            holder.textView_list_item = (TextView) convertView.findViewById(R.id.textView_list_item);
            holder.icon = (ImageView) convertView.findViewById(R.id.imageView_icons);
//            holder.count = (TextView) convertView.findViewById(R.id.count);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.textView_list_item.setText((""+data.keySet().toArray()[ position ]));
        Glide.with(mContext).load(data.get((data.keySet().toArray())[ position ])).dontAnimate().into(holder.icon);
//        icon.setImageResource(icons.get(position));

        return convertView;
    }
    private class ViewHolder{
        TextView textView_list_item;//, count;
        ImageView icon;
    }


}
