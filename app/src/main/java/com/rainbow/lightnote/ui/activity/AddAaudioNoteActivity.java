package com.rainbow.lightnote.ui.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.rainbow.lightnote.R;

import java.io.File;

import me.gujun.android.taggroup.TagGroup;

/**
 * Created by SYSTEM on 2015/9/8.
 */
public class AddAaudioNoteActivity extends Activity implements View.OnClickListener {

    private TextView tv_notebook;
    private ImageButton img_btn_tag;
    private ImageButton img_btn_back;
    private ImageButton img_btn_complete;
    private Button btn_add_audio_note;
    private String PATH=Environment.getExternalStorageDirectory().getAbsolutePath();
    private String APPPATH=PATH+"/LightNote/";
    private  MediaRecorder mMediaRecorder01;
    private boolean ISRECORDING=false;
    private ImageView img_recording_res;
    private ImageButton record_img_forward,record_img_play,record_img_next,record_img_exit;
    private PopupWindow record_playing_popwindow;
    private View view;
    private  MediaPlayer mediaPlayer=new MediaPlayer();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_add_audio_note);
        initView();
        initListener();
    }
    private void initListener() {
        tv_notebook.setOnClickListener(this);
        img_btn_tag.setOnClickListener(this);
        img_btn_back.setOnClickListener(this);
        img_btn_complete.setOnClickListener(this);
        btn_add_audio_note.setOnClickListener(this);
        img_recording_res.setOnClickListener(this);



    }

    private void initView() {
        tv_notebook = (TextView)findViewById(R.id.tv_notebook);
        img_btn_tag = (ImageButton)findViewById(R.id.img_btn_tag);
        img_btn_back= (ImageButton) findViewById(R.id.img_btn_back);
        img_btn_complete= (ImageButton) findViewById(R.id.img_btn_complete);
        btn_add_audio_note= (Button) findViewById(R.id.add_audio_note_button);
        img_recording_res=(ImageView)findViewById(R.id.img_recording_res);
        view =getLayoutInflater().inflate(R.layout.view_play_recording,null,false);
        record_playing_popwindow=new PopupWindow(view, ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.WRAP_CONTENT,true);

    }

    private void showPopupWindow(View view) {



        // һ���Զ���Ĳ��֣���Ϊ��ʾ������
        View contentView = LayoutInflater.from(this).inflate(
                R.layout.popup_tag_view, null);
        // ���úò���֮����show
        TagGroup mTagGroup = (TagGroup) contentView.findViewById(R.id.tag_group);
        mTagGroup.setTags(new String[]{"Tag1", "Tag2", "Tag3"});
        // ���ð�ť�ĵ���¼�

        final PopupWindow popupWindow = new PopupWindow(contentView,
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);


        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels;  // ��Ļ��ȣ����أ�
        int height = metric.heightPixels;  // ��Ļ�߶ȣ����أ�
        popupWindow.setWidth(width-80);
        popupWindow.setHeight(height-400);//���õ������С

        popupWindow.setTouchable(true);
        popupWindow.setAnimationStyle(R.style.PopupAnimation);
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                Log.i("mengdd", "onTouch : ");

                return false;
                // �����������true�Ļ���touch�¼���������
                // ���غ� PopupWindow��onTouchEvent�������ã���������ⲿ�����޷�dismiss
            }
        });

        // ���������PopupWindow�ı����������ǵ���ⲿ������Back�����޷�dismiss����
        // �Ҿ���������API��һ��bug
        popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_popup_tag));

        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
    }
    private void showNoteBookWindow(View view) {

        // һ���Զ���Ĳ��֣���Ϊ��ʾ������
        View contentView = LayoutInflater.from(this).inflate(
                R.layout.popup_note_book, null);
        // ���úò���֮����show
        // ���ð�ť�ĵ���¼�

        final PopupWindow popupWindow = new PopupWindow(contentView,
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);


        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels;  // ��Ļ��ȣ����أ�
        int height = metric.heightPixels;  // ��Ļ�߶ȣ����أ�
        popupWindow.setWidth(width-80);
        popupWindow.setHeight(height-400);//���õ������С

        popupWindow.setTouchable(true);
        popupWindow.setAnimationStyle(R.style.PopupAnimation);
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                Log.i("mengdd", "onTouch : ");

                return false;
                // �����������true�Ļ���touch�¼���������
                // ���غ� PopupWindow��onTouchEvent�������ã���������ⲿ�����޷�dismiss
            }
        });

        // ���������PopupWindow�ı����������ǵ���ⲿ������Back�����޷�dismiss����
        // �Ҿ���������API��һ��bug
        popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_popup_tag));

        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
    }
    private int RECORD_CODE=111;
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
            case R.id.img_btn_complete://��ɰ�ť
                break;
            case R.id.add_audio_note_button:
//                Intent it=new Intent(Intent.ACTION_GET_CONTENT);
//                it.setType("audio/amr");
//                startActivityForResult(it,RECORD_CODE);
                if(!ISRECORDING) {
                    try {
                        File myRecAudioFile = new File(APPPATH, "new.amr");
                        mMediaRecorder01 = new MediaRecorder();
                /* ����¼����ԴΪ��˷� */
                        mMediaRecorder01.setAudioSource(MediaRecorder.AudioSource.MIC);
                        mMediaRecorder01.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
                        mMediaRecorder01.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
                        //�ļ�����λ��
                        mMediaRecorder01.setOutputFile(myRecAudioFile.getAbsolutePath());
                        mMediaRecorder01.prepare();
                        mMediaRecorder01.start();
                        ISRECORDING=true;
                        ShowRecordingDialog();
                    } catch (Exception e) {

                    }
                }else
                {
                    Toast.makeText(getBaseContext(),"recording...",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.img_recording_res:
                try {



                    record_img_forward= (ImageButton) view.findViewById(R.id.record_forward_btn);
                    record_img_play= (ImageButton) view.findViewById(R.id.record_play_btn);
                    record_img_next= (ImageButton) view.findViewById(R.id.record_next_btn);
                    record_img_exit= (ImageButton) view.findViewById(R.id.record_exit_btn);
                    record_playing_popwindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_popup_tag));
                    record_playing_popwindow.setOutsideTouchable(true);

                    if(!record_playing_popwindow.isShowing()) {
                        record_playing_popwindow.setAnimationStyle(R.style.PopupAnimation);
                        record_playing_popwindow.showAtLocation(img_recording_res, Gravity.CENTER, 0, 0);
                    }
                    record_img_forward.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    });
                    record_img_play.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            try {
                                PlayRecording();
                            }catch(Exception e)
                            {
                            }
                        }
                    });
                    record_img_next.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    });
                    record_img_exit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                                record_playing_popwindow.dismiss();
                        }
                    });


                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void ShowRecordingDialog()
    {
        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.setMessage("Recording,Save and exit").setTitle("Tip").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                if (mMediaRecorder01 != null && ISRECORDING)
                    {
                      /* ֹͣ¼�� */
                        mMediaRecorder01.stop();
                        mMediaRecorder01.release();
                        ISRECORDING=false;
                        mMediaRecorder01 = null;
                     }
                img_recording_res.setClickable(true);
                img_recording_res.setVisibility(View.VISIBLE);
                img_recording_res.setImageDrawable(getResources().getDrawable(R.drawable.record_voice));

            }
        }).create().show();
    }

    private void PlayRecording() throws Exception
    {

        mediaPlayer.reset();
        mediaPlayer.setDataSource(APPPATH + "new.amr");
        mediaPlayer.prepare();
        mediaPlayer.start();
    }

    @Override
    protected void onPause() {
        super.onPause();


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK)
        {
            if(record_playing_popwindow.isShowing())
            {
                record_playing_popwindow.dismiss();
            }else
            {
                this.finish();
            }
        }

        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mMediaRecorder01 != null)
        {
            /* ֹͣ¼�� */
            mMediaRecorder01.stop();
            mMediaRecorder01.release();
            mMediaRecorder01 = null;
        }
        if(mediaPlayer!=null)
        {
            mediaPlayer.release();
            mediaPlayer=null;
        }
    }
}
