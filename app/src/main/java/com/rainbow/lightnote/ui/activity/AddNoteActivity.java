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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.rainbow.lightnote.R;
import com.rainbow.lightnote.adapter.NoteBookAdapter;
import com.rainbow.lightnote.engin.CategoryManager;
import com.rainbow.lightnote.engin.NoteManager;
import com.rainbow.lightnote.model.Lable;
import com.rainbow.lightnote.model.Note;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import me.gujun.android.taggroup.TagGroup;


public class AddNoteActivity extends Activity implements View.OnClickListener {
    private TextView tv_catogary;
    private ImageButton img_btn_tag;
    private ImageButton img_btn_back;
    private ImageButton img_btn_complete;

    private EditText et_notetitle;
    private EditText et_notecontent;
    private Note note;
    private List<Lable> lables = new ArrayList();

    private TagGroup mTagGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_add_note);
        initData();
        initView();
        initListener();
    }

    private void initData() {
        note = new Note();
    }

    private void initListener() {
        tv_catogary.setOnClickListener(this);
        img_btn_tag.setOnClickListener(this);
        img_btn_back.setOnClickListener(this);
        img_btn_complete.setOnClickListener(this);

    }

    private void initView() {
        tv_catogary = (TextView)findViewById(R.id.tv_catogary);
        img_btn_tag = (ImageButton)findViewById(R.id.img_btn_tag);
        img_btn_back = (ImageButton)findViewById(R.id.img_btn_back);
        img_btn_complete = (ImageButton)findViewById(R.id.img_btn_complete);
        et_notetitle = (EditText) findViewById(R.id.et_notetitle);
        et_notecontent = (EditText) findViewById(R.id.et_notecontent);
    }

    private void showPopupWindow(View view) {

        // 一个自定义的布局，作为显示的内容
        View contentView = LayoutInflater.from(this).inflate(
                R.layout.popup_tag_view, null);
        // 设置好参数之后再show

        mTagGroup = (TagGroup) contentView.findViewById(R.id.tag_group);
        if(null != note.getLables())
            mTagGroup.setTags(note.getLableArray());
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
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {

                String tags[] = mTagGroup.getTags();
                for (int i = 0;i<tags.length;i++){
                    Lable lable = new Lable(tags[i]);
                    lables.add(lable);
                }
                note.setLables(lables);
            }
        });
    }
    private void showNoteBookWindow(View view) {

        // 一个自定义的布局，作为显示的内容
        View contentView = LayoutInflater.from(this).inflate(
                R.layout.popup_note_book, null);
        ImageButton img_btn_addbook = (ImageButton) contentView.findViewById(R.id.img_btn_addbook);
        ListView lv_notebook = (ListView) contentView.findViewById(R.id.lv_notebook);
        lv_notebook.setAdapter(new NoteBookAdapter(this,new CategoryManager().getCategorys()));
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
            case R.id.tv_catogary:
                showNoteBookWindow(tv_catogary);
                break;
            case R.id.img_btn_tag:
                showPopupWindow(tv_catogary);
                break;
            case R.id.img_btn_back:
                finish();
                break;
            case R.id.img_btn_complete://完成按钮

                SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd");
                Date curDate = new Date(System.currentTimeMillis());//获取当前时间
                String str = formatter.format(curDate);
                note.setTime(str);
                note.setTitle(et_notetitle.getText().toString());
                note.setContent(et_notecontent.getText().toString());
                note.setCategory(tv_catogary.getText().toString());
                NoteManager nm = new NoteManager();
                nm.addNote(note);
                finish();
                break;
        }
    }
}
