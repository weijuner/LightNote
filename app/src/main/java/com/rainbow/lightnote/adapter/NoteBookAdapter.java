package com.rainbow.lightnote.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.rainbow.lightnote.R;
import com.rainbow.lightnote.engin.NoteManager;
import com.rainbow.lightnote.model.Category;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class NoteBookAdapter extends BaseAdapter {
    private  Context mContext;
    private List<Category> categorys;
    public NoteBookAdapter(Context context,List<Category> categorys) {
        mContext = context;
        this.categorys = categorys;
    }

    @Override
    public int getCount() {
        return categorys.size();
    }

    @Override
    public String getItem(int position) {
        return categorys.get(position).getCategoryname();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_note_book, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tv_note_book = (TextView) convertView.findViewById(R.id.tv_note_book);
            viewHolder.tv_note_book.setText(categorys.get(position).getCategoryname());
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
                                   new NoteManager().deleteNote(position);
                                    notifyDataSetInvalidated();
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
        public TextView tv_note_book;
    }
}