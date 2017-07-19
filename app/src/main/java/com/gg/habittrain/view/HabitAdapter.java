package com.gg.habittrain.view;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gg.habittrain.R;
import com.gg.habittrain.data.HabitData;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/1 0001.
 */

public class HabitAdapter extends BaseAdapter {
    private List<Map<String, String>> list;
    private Context context;
    private LayoutInflater mInflater = null;
    public HabitAdapter(Context context, List<Map<String, String>> list){
        this.context=context;
        this.list=list;
        this.mInflater = LayoutInflater.from(context);
    }

    public void setData(List<Map<String, String>> list){
        this.list=list;
    }

    public List<Map<String, String>> getData(){
        return list;
    }
    @Override
    public int getCount() {
        return list==null?0:list.size();
    }

    @Override
    public Object getItem(int position) {
        return list==null?null:list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return list==null?0:position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.list_item, null);
            holder.txtTitle = (TextView) convertView.findViewById(R.id.txt_title);
            holder.txtTime = (TextView) convertView.findViewById(R.id.txt_time);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (position % 2 == 0) {
            convertView.setBackgroundColor(Color.parseColor("#B3FFFFFF"));
        } else {
            convertView.setBackgroundColor(Color.parseColor("#B3FAFAFA"));
        }
        //{"id":"b576d878f7244df2933f8b97de5debda","endtime":"11:00","starttime":"09:00","page":1,"name":"语文课","serialnumber":"11","week":1,"rows":10},
        String timeZ=list.get(position).get("time");
        String titleZ=list.get(position).get("title");
        String contentZ=list.get(position).get("cuecontent");
        Log.e("ClassesAdapter","timeZ="+timeZ+",titleZ="+titleZ+",contentZ="+contentZ);
        holder.txtTitle.setText(titleZ);
        holder.txtTime.setText(timeZ);
        return convertView;
    }

    static class ViewHolder {
        public TextView txtTitle;
        public TextView txtTime;
    }
}
