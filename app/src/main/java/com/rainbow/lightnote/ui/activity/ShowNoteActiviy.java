package com.rainbow.lightnote.ui.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.rainbow.lightnote.R;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.media.UMImage;

public class ShowNoteActiviy extends Activity implements View.OnClickListener {
    ActionBar actionBar; //声明ActionBar
    private ImageButton img_btn_back;
    private ImageButton img_btn_share;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_note_activiy);
        actionBar = getActionBar(); //得<span></span>到ActionBar
        actionBar.hide(); //隐藏ActionBar
        initView();
        initListener();
    }

    private void initView() {
        img_btn_back = (ImageButton) findViewById(R.id.img_btn_back);
        img_btn_share = (ImageButton) findViewById(R.id.img_btn_share);
    }

    private void initListener() {
        img_btn_back.setOnClickListener(this);
        img_btn_share.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_show_note_activiy, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_btn_back:
                finish();
                break;
            case R.id.img_btn_share:
                final UMSocialService mController = UMServiceFactory.getUMSocialService("com.umeng.share");
                mController.getConfig().setPlatformOrder(
                        SHARE_MEDIA.RENREN
                );
// 设置分享内容
                mController.setShareContent("LightNote share test");
// 设置分享图片, 参数2为图片的url地址
                mController.setShareMedia(new UMImage(this,
                        "http://img5.duitang.com/uploads/item/201406/25/20140625014032_emTsS.thumb.700_0.jpeg"));
                mController.openShare(this,false);
                break;
        }
    }
}
