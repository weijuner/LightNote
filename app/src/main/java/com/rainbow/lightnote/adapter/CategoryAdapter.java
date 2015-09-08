package com.rainbow.lightnote.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.rainbow.lightnote.R;
import com.rainbow.lightnote.model.Note;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class CategoryAdapter extends BaseAdapter {
    private  Context mContext;
    private List<Note> notes;
    public CategoryAdapter(Context context, List<Note> notes) {
        mContext = context;
        this.notes = notes;
    }

    @Override
    public int getCount() {
        return notes.size();
    }

    @Override
    public String getItem(int position) {
        return notes.get(position).getTitle();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_category_lv, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tv_note_title = (TextView) convertView.findViewById(R.id.tv_note_title);
            viewHolder.tv_note_title.setText(notes.get(position).getTitle());
            viewHolder.tv_note_time = (TextView) convertView.findViewById(R.id.tv_note_time);
            viewHolder.tv_note_time.setText(notes.get(position).getTime());
            viewHolder.tv_note_category = (TextView) convertView.findViewById(R.id.tv_note_category);
            viewHolder.tv_note_category.setText(notes.get(position).getCategory());
            convertView.findViewById(R.id.btn_share_menu).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, "Star", Toast.LENGTH_SHORT).show();
                }
            });

            convertView.findViewById(R.id.btn_trash_menu).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new SweetAlertDialog(mContext, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Are you sure?")
                            .setContentText("删除这么珍贵的东西!")
                            .setConfirmText("Yes,delete it!")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog
                                            .setTitleText("Deleted!")
                                            .setContentText("坏记忆跑掉啦!")
                                            .setConfirmText("OK")
                                            .setConfirmClickListener(null)
                                            .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                                }
                            })
                            .show();
                }
            });
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        return convertView;
    }

    private static class ViewHolder {
        public TextView tv_note_title;
        public TextView tv_note_time;
        public TextView tv_note_category;
    }
}