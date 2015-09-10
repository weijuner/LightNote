package com.rainbow.lightnote.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.rainbow.lightnote.R;
import com.rainbow.lightnote.engin.NoteManager;
import com.rainbow.lightnote.model.Note;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.media.UMImage;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class CategoryAdapter extends BaseAdapter {
    private  Context mContext;
    private List<Note> notes;
    final UMSocialService mController = UMServiceFactory.getUMSocialService("com.umeng.share");
    public CategoryAdapter(Context context, List<Note> notes) {
        mContext = context;
        this.notes = notes;
        mController.getConfig().setPlatformOrder(
                            SHARE_MEDIA.RENREN
                   );
// 设置分享内容
        mController.setShareContent("LightNote share test");
// 设置分享图片, 参数2为图片的url地址
        mController.setShareMedia(new UMImage(mContext,
                "http://img5.duitang.com/uploads/item/201406/25/20140625014032_emTsS.thumb.700_0.jpeg"));

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
    public View getView(final int position, View convertView, ViewGroup parent) {
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
                    mController.openShare((Activity)mContext, false);
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
        public TextView tv_note_title;
        public TextView tv_note_time;
        public TextView tv_note_category;
    }
}