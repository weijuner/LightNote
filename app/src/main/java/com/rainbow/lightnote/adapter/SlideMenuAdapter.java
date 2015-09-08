package com.rainbow.lightnote.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.rainbow.lightnote.R;


/**
 * Created by weijuner on 2015/9/2.
 */
public class SlideMenuAdapter extends BaseAdapter {
    private Context context;
    private String[] listItems;
    private LayoutInflater inflater;
    private int selectItem = 1;
    public SlideMenuAdapter(Context context, String[] listItems) {
        this.context = context;
        this.listItems = listItems;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listItems.length+1;
    }

    @Override
    public Object getItem(int position) {
        return listItems[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
     //   if (convertView == null) {
            if(position == 0){
                convertView = inflater
                        .inflate(R.layout.item_lv_main_img, null);
                viewHolder = new ViewHolder();
                viewHolder.iv = (ImageView) convertView
                        .findViewById(R.id.user_image);
                viewHolder.tv = (TextView) convertView
                        .findViewById(R.id.user_name);
                convertView.setTag(viewHolder);
            }else{
                convertView = inflater
                        .inflate(R.layout.item_menu_lv, null);
                viewHolder = new ViewHolder();
                viewHolder.tv_menu = (TextView) convertView
                        .findViewById(R.id.tv_menu);
                viewHolder.tv_menu.setText(listItems[position-1]);
                if (position == selectItem) {
                    convertView.setBackgroundColor(context.getResources().getColor(R.color.main_color));
                }
                else {
                    convertView.setBackgroundColor(Color.WHITE);
                }
                convertView.setTag(viewHolder);
            }

  //      } else {
   //         viewHolder = (ViewHolder) convertView.getTag();
  //      }
        return convertView;
    }

    public  void setSelectItem(int selectItem) {
        this.selectItem = selectItem;
    }
    static class ViewHolder {
        public ImageView iv;
        public TextView tv;
        public TextView tv_menu;
    }
}
