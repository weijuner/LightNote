package com.rainbow.lightnote.ui.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.rainbow.lightnote.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by SYSTEM on 2015/9/6.
 */
public class PersonalInfoActivity extends Activity{

    private TextView personalInfo_textview,setting_textview;
    private CircleImageView profile_circleimageview;
    private PopupWindow popWindow;
    private View popwindowView;
    private Button popWin_button_selected, popWin_button_cancle,
            popWin_button_takephoto;
    private String PATH= Environment.getExternalStorageDirectory().getAbsolutePath();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_person_info);
        initView();
    }

    public void initView()
    {
        personalInfo_textview=(TextView)findViewById(R.id.person_info);
        setting_textview= (TextView) findViewById(R.id.person_set);
        profile_circleimageview= (CircleImageView) findViewById(R.id.profile_image);

        personalInfo_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        setting_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        profile_circleimageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!popWindow.isShowing()) {
                    popWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
                }
            }
        });
        popwindowView=getLayoutInflater().inflate(R.layout.popup_choose_img,null,false);
        popWindow=new PopupWindow(popwindowView, ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
        popWindow.setBackgroundDrawable(new BitmapDrawable());
        popWindow.setTouchInterceptor(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                Log.i("mengdd", "onTouch : ");

                return false;
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
            }
        });
        popWindow.setOutsideTouchable(true);

        popWin_button_selected = (Button) popwindowView
                .findViewById(R.id.popwindow_changeimage);
        popWin_button_cancle = (Button) popwindowView
                .findViewById(R.id.popwindow_cancle);
        popWin_button_takephoto = (Button) popwindowView
                .findViewById(R.id.popwindow_takephoto);
        popWin_button_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popWindow.dismiss();
            }
        });
        popWin_button_takephoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popWindow.dismiss();
                getImageFromCamear();
            }
        });
        popWin_button_selected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popWindow.dismiss();
                getImageFromAlbum();
            }
        });
    }

    private int REQUEST_CODE_PICK_IMAGE=1;
    private int CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE=2;

    protected void getImageFromAlbum() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");//
        startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);
    }
    protected void getImageFromCamear()
    {
        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE);
    }



    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) { //
            return;
        }
        Bitmap bm = null;

        ContentResolver resolver = getContentResolver();

        if (requestCode == REQUEST_CODE_PICK_IMAGE) {
            try {
                Uri originalUri = data.getData(); //
                bm = MediaStore.Images.Media.getBitmap(resolver, originalUri); //
                String[] proj = { MediaStore.Images.Media.DATA };
                Cursor cursor = managedQuery(originalUri, proj, null, null,
                        null);
                int column_index = cursor
                        .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                String path = cursor.getString(column_index);
                Bitmap bt=convertToBitmap(path,100,120);

                profile_circleimageview.setImageDrawable(new BitmapDrawable(bt));
                saveBitmap(bt);

            } catch (IOException e) {
            }
        }

    }

    public void saveBitmap(Bitmap bm) {
        File f = new File(PATH+"/LightNote/", "user");

        try {
            FileOutputStream out = new FileOutputStream(f);
            bm.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.flush();
            out.close();
          //  Log.i(TAG, "�Ѿ�����");
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }



    public Bitmap convertToBitmap(String path,int w,int h) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        opts.inPreferredConfig = Bitmap.Config.ARGB_8888;
        BitmapFactory.decodeFile(path, opts);
        int width = opts.outWidth;
        int height = opts.outHeight;
        float scaleWidth = 0.f, scaleHeight = 0.f;
        if (width > w || height > h) {
            scaleWidth = ((float) width) / w;
            scaleHeight = ((float) height) / h;
        }
        opts.inJustDecodeBounds = false;
        float scale = Math.max(scaleWidth, scaleHeight);
        opts.inSampleSize = (int)scale;
        WeakReference<Bitmap> weak = new WeakReference<Bitmap>(BitmapFactory.decodeFile(path, opts));
        return Bitmap.createScaledBitmap(weak.get(), w, h, true);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK)
        {
            if(popWindow.isShowing())
            {
                popWindow.dismiss();
            }else
            {
                this.finish();
            }
        }
        return false;
    }
}
