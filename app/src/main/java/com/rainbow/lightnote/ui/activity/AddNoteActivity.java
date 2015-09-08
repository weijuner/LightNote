package com.rainbow.lightnote.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.rainbow.lightnote.R;

import me.gujun.android.taggroup.TagGroup;


public class AddNoteActivity extends Activity implements View.OnClickListener {
    private TextView tv_notebook;
    private ImageButton img_btn_tag;
    private ImageButton img_btn_back;
    private ImageButton img_btn_complete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_add_note);
        initView();
        initListener();
    }
    private void initListener() {
        tv_notebook.setOnClickListener(this);
        img_btn_tag.setOnClickListener(this);
        img_btn_back.setOnClickListener(this);
        img_btn_complete.setOnClickListener(this);

    }

    private void initView() {
        tv_notebook = (TextView)findViewById(R.id.tv_notebook);
        img_btn_tag = (ImageButton)findViewById(R.id.img_btn_tag);
        img_btn_back = (ImageButton)findViewById(R.id.img_btn_back);
        img_btn_complete = (ImageButton)findViewById(R.id.img_btn_complete);
    }

    private void showPopupWindow(View view) {

        // 一个自定义的布局，作为显示的内容
        View contentView = LayoutInflater.from(this).inflate(
                R.layout.popup_tag_view, null);
        // 设置好参数之后再show
        TagGroup mTagGroup = (TagGroup) contentView.findViewById(R.id.tag_group);
        mTagGroup.setTags(new String[]{"Tag1", "Tag2", "Tag3"});
         // 设置按钮的点击事件

        final PopupWindow popupWindow = new PopupWindow(contentView,
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);


        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels;  // 屏幕宽度（像素）
        int height = metric.heightPixels;  // 屏幕高度（像素）
        popupWindow.setWidth(width-80);
        popupWindow.setHeight(height-400);//设置弹出框大小

        popupWindow.setTouchable(true);
        popupWindow.setAnimationStyle(R.style.PopupAnimation);
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                Log.i("mengdd", "onTouch : ");

                return false;
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
            }
        });

        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        // 我觉得这里是API的一个bug
        popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_popup_tag));

        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
    }
    private void showNoteBookWindow(View view) {

        // 一个自定义的布局，作为显示的内容
        View contentView = LayoutInflater.from(this).inflate(
                R.layout.popup_note_book, null);
        // 设置好参数之后再show
        // 设置按钮的点击事件

        final PopupWindow popupWindow = new PopupWindow(contentView,
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);


        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels;  // 屏幕宽度（像素）
        int height = metric.heightPixels;  // 屏幕高度（像素）
        popupWindow.setWidth(width-80);
        popupWindow.setHeight(height-400);//设置弹出框大小

        popupWindow.setTouchable(true);
        popupWindow.setAnimationStyle(R.style.PopupAnimation);
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                Log.i("mengdd", "onTouch : ");

                return false;
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
            }
        });

        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        // 我觉得这里是API的一个bug
        popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_popup_tag));

        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_notebook:
                showNoteBookWindow(tv_notebook);
                break;
            case R.id.img_btn_tag:
                showPopupWindow(tv_notebook);
                break;
            case R.id.img_btn_back:
                finish();
                break;
            case R.id.img_btn_complete://完成按钮
                break;
        }
    }
}
