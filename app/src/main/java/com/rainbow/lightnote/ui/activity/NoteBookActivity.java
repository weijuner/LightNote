package com.rainbow.lightnote.ui.activity;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.rainbow.lightnote.R;
import com.rainbow.lightnote.adapter.NoteBookAdapter;
import com.rainbow.lightnote.engin.CategoryManager;

public class NoteBookActivity extends Activity implements PullToRefreshBase.OnRefreshListener<ListView>{
    private ImageButton img_btn_back;
    public PullToRefreshListView notelist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_book);
        initView();
        notelist.setAdapter(new NoteBookAdapter(this, new CategoryManager().getCategorys()));
    }
    private void initView() {
        img_btn_back = (ImageButton) findViewById(R.id.img_btn_back);
        img_btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
        notelist = (PullToRefreshListView) findViewById(android.R.id.list);
        ILoadingLayout startLabels = notelist.getLoadingLayoutProxy(true, false);
        startLabels.setPullLabel("想看笔记本吗，拉拉我");// 刚下拉时，显示的提示
        startLabels.setRefreshingLabel("等灯等瞪");// 刷新时
        startLabels.setReleaseLabel("放开我你个禽兽!!!");// 下来达到一定距离时，显示的提示
        notelist.setOnRefreshListener(this);

    }

    @Override
    public void onRefresh(PullToRefreshBase<ListView> refreshView) {
        new GetDataTask(refreshView).execute();
    }
    private static class GetDataTask extends AsyncTask<Void, Void, Void> {

        PullToRefreshBase<?> mRefreshedView;

        public GetDataTask(PullToRefreshBase<?> refreshedView) {
            mRefreshedView = refreshedView;
        }

        @Override
        protected Void doInBackground(Void... params) {
            // Simulates a background job.
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            mRefreshedView.onRefreshComplete();
            super.onPostExecute(result);
        }
    }


}
