package com.rainbow.lightnote.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.rainbow.lightnote.R;
import com.rainbow.lightnote.engin.NoteManager;
import com.rainbow.lightnote.model.Lable;
import com.rainbow.lightnote.model.Note;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import me.gujun.android.taggroup.TagGroup;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;


public class AddImageNoteActivity extends Activity implements View.OnClickListener {
    private TextView tv_catogary;
    private ImageButton img_btn_tag;
    private ImageButton img_btn_back;
    private ImageButton img_btn_complete;

    private EditText et_notetitle;
    private EditText et_notecontent;
    private Note note;
    private List<Lable> lables = new ArrayList<>();
    private int path_size = 1;

    private TagGroup mTagGroup;
    private GridView gridView1;              //������ʾ����ͼ
    private ArrayList<HashMap<String, Object>> imageItem;

    private SimpleAdapter simpleAdapter;
    private Bitmap bmp;
    private String pathImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_add_image_note);
        initData();
        initView();
        initListener();

        initGridView();
    }

    protected void initGridView() {
        gridView1 = (GridView) findViewById(R.id.gridView1);

         /*
29.         * ����Ĭ��ͼƬ���ͼƬ�Ӻ�
30.         * ͨ��������ʵ��
31.         * SimpleAdapter����imageItemΪ����Դ R.layout.griditem_addpicΪ����
32.         */
        //��ȡ��ԴͼƬ�Ӻ�
        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.add_image);
        imageItem = new ArrayList<HashMap<String, Object>>();
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("itemImage", bmp);
        imageItem.add(map);
        simpleAdapter = new SimpleAdapter(this,
                imageItem, R.layout.view_add_img_griditem,
                new String[]{"itemImage"}, new int[]{R.id.imageView1});
        /*
43.         * HashMap����bmpͼƬ��GridView�в���ʾ,�������������ԴID����ʾ ��
44.         * map.put("itemImage", R.drawable.img);
45.         * �������:
46.         *              1.�Զ���̳�BaseAdapterʵ��
47.         *              2.ViewBinder()�ӿ�ʵ��
48.         *  �ο� http://blog.csdn.net/admin_/article/details/7257901
49.         */
        simpleAdapter.setViewBinder(new SimpleAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Object data,
                                        String textRepresentation) {
                // TODO Auto-generated method stub
                if (view instanceof ImageView && data instanceof Bitmap) {
                    ImageView i = (ImageView) view;
                    i.setImageBitmap((Bitmap) data);
                    return true;
                }
                return false;
            }
        });
        gridView1.setAdapter(simpleAdapter);

        /*
66.         * ����GridView����¼�
67.         * ����:�ú���������󷽷� ����Ҫ�ֶ�����import android.view.View;
68.         */
        gridView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                if (imageItem.size() == 10) { //��һ��ΪĬ��ͼƬ
                    Toast.makeText(getBaseContext(), "ͼƬ��9������", Toast.LENGTH_SHORT).show();

                } else if (position == path_size - 1) { //���ͼƬλ��Ϊ+ 0��Ӧ0��ͼƬ
                    // Toast.makeText(MainActivity.this, "���ͼƬ", Toast.LENGTH_SHORT).show();
                    //ѡ��ͼƬ
//                    Intent intent = new Intent(Intent.ACTION_PICK,
//                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                    startActivityForResult(intent, 1);
                    //ͨ��onResume()ˢ������
                    Intent intent = new Intent(getBaseContext(), MultiImageSelectorActivity.class);

// whether show camera
                    intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, true);

// max select image amount
                    intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, 9);

// select mode (MultiImageSelectorActivity.MODE_SINGLE OR MultiImageSelectorActivity.MODE_MULTI)
                    intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, MultiImageSelectorActivity.MODE_MULTI);

                    startActivityForResult(intent, 1);

                } else {
                    //Toast.makeText(getBaseContext(),position+"",Toast.LENGTH_SHORT).show();
                    dialog(position);

                    //Toast.makeText(MainActivity.this, "�����"+(position + 1)+" ��ͼƬ",
                    //      Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public Bitmap convertToBitmap(String path, int w, int h) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        // ����Ϊtureֻ��ȡͼƬ��С
        opts.inJustDecodeBounds = true;
        opts.inPreferredConfig = Bitmap.Config.ARGB_8888;
        // ����Ϊ��
        BitmapFactory.decodeFile(path, opts);
        int width = opts.outWidth;
        int height = opts.outHeight;
        float scaleWidth = 0.f, scaleHeight = 0.f;
        if (width > w || height > h) {
            // ����
            scaleWidth = ((float) width) / w;
            scaleHeight = ((float) height) / h;
        }
        opts.inJustDecodeBounds = false;
        float scale = Math.max(scaleWidth, scaleHeight);
        opts.inSampleSize = (int) scale;
        WeakReference<Bitmap> weak = new WeakReference<Bitmap>(BitmapFactory.decodeFile(path, opts));
        return Bitmap.createScaledBitmap(weak.get(), w, h, true);
    }


    protected void dialog(final int position) {
        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Are you sure?")
                .setContentText("删除这么重要的东西!")
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
                        imageItem.remove(position);
                        path_size--;
                        simpleAdapter.notifyDataSetChanged();
                    }
                })
                .show();
    }

    private List<String> path;
    private boolean first_to_choose = true;

    //��ȡͼƬ·�� ��ӦstartActivityForResult
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //��ͼƬ
        if (resultCode == RESULT_OK && requestCode == 1) {
            // Get the result list of select image paths
            path = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
            imageItem.remove(imageItem.size() - 1);
            // do your logic ....
            for (int i = 0; i < path.size(); i++) {
                HashMap<String, Object> map = new HashMap<String, Object>();
                Bitmap bmp = convertToBitmap(path.get(i), 100, 100);
                map.put("itemImage", bmp);
                imageItem.add(map);
            }

            bmp = BitmapFactory.decodeResource(getResources(), R.drawable.add_image);
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("itemImage", bmp);
            imageItem.add(map);
            path_size = imageItem.size();
            first_to_choose = false;
            Toast.makeText(getBaseContext(), path_size + "", Toast.LENGTH_SHORT).show();
        }
    }


    //ˢ��ͼƬ

    @Override


    protected void onResume() {
        super.onResume();

        simpleAdapter = new SimpleAdapter(this,
                imageItem, R.layout.view_add_img_griditem,
                new String[]{"itemImage"}, new int[]{R.id.imageView1});
        simpleAdapter.setViewBinder(new SimpleAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Object data,
                                        String textRepresentation) {
                // TODO Auto-generated method stub
                if (view instanceof ImageView && data instanceof Bitmap) {
                    ImageView i = (ImageView) view;
                    //  i.setImageBitmap(getScaleBitmap((Bitmap) data, 0.5f, 0.5f));
                    i.setImageBitmap((Bitmap) data);
                    return true;
                }
                return false;
            }
        });
        //simpleAdapter.notifyDataSetChanged();
        simpleAdapter.notifyDataSetInvalidated();
        gridView1.setAdapter(simpleAdapter);
        // Toast.makeText(getBaseContext(), imageItem.size() + "", Toast.LENGTH_SHORT).show();
        //ˢ�º��ͷŷ�ֹ�ֻ����ߺ��Զ����
        pathImage = null;
    }

    protected Bitmap getScaleBitmap(Bitmap bt, float w, float h) {
        Matrix matrix = new Matrix();
        matrix.postScale(w, h);
        Bitmap resizeBmp = Bitmap.createBitmap(bt, 0, 0, bt.getWidth(), bt.getHeight(), matrix, true);
        return resizeBmp;

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
        tv_catogary = (TextView) findViewById(R.id.tv_catogary);
        img_btn_tag = (ImageButton) findViewById(R.id.img_btn_tag);
        img_btn_back = (ImageButton) findViewById(R.id.img_btn_back);
        img_btn_complete = (ImageButton) findViewById(R.id.img_btn_complete);
        et_notetitle = (EditText) findViewById(R.id.et_notetitle);
        et_notecontent = (EditText) findViewById(R.id.et_notecontent);
    }

    private void showPopupWindow(View view) {

        // һ���Զ���Ĳ��֣���Ϊ��ʾ������
        View contentView = LayoutInflater.from(this).inflate(
                R.layout.popup_tag_view, null);
        // ���úò���֮����show

        mTagGroup = (TagGroup) contentView.findViewById(R.id.tag_group);
        if (null != note.getLables())
            mTagGroup.setTags(note.getLableArray());
        // ���ð�ť�ĵ���¼�

        final PopupWindow popupWindow = new PopupWindow(contentView,
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);


        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels;  // ��Ļ��ȣ����أ�
        int height = metric.heightPixels;  // ��Ļ�߶ȣ����أ�
        popupWindow.setWidth(width - 80);
        popupWindow.setHeight(height - 400);//���õ������С

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
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {

                String tags[] = mTagGroup.getTags();
                for (int i = 0; i < tags.length; i++) {
                    Lable lable = new Lable(tags[i]);
                    lables.add(lable);
                }
                note.setLables(lables);
            }
        });
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
        popupWindow.setWidth(width - 80);
        popupWindow.setHeight(height - 400);//���õ������С

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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_notebook:
                showNoteBookWindow(tv_catogary);
                break;
            case R.id.img_btn_tag:
                showPopupWindow(tv_catogary);
                break;
            case R.id.img_btn_back:
                finish();
                break;
            case R.id.img_btn_complete://��ɰ�ť

                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                Date curDate = new Date(System.currentTimeMillis());//��ȡ��ǰʱ��
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
