package com.rainbow.lightnote.ui.activity;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.ActionBar;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.ikimuhendis.ldrawer.ActionBarDrawerToggle;
import com.ikimuhendis.ldrawer.DrawerArrowDrawable;
import com.melnykov.fab.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;
import com.rainbow.lightnote.R;
import com.rainbow.lightnote.adapter.CategoryAdapter;
import com.rainbow.lightnote.adapter.MainViewPageAdapter;
import com.rainbow.lightnote.adapter.SlideMenuAdapter;
import com.rainbow.lightnote.adapter.TimeLineAdapter;
import com.rainbow.lightnote.engin.NoteManager;
import com.rainbow.lightnote.model.Note;
import com.rainbow.lightnote.model.TimeLineEntity;
import com.rainbow.lightnote.model.User;
import com.rainbow.lightnote.ui.view.JazzyViewPager;
import com.yalantis.contextmenu.lib.ContextMenuDialogFragment;
import com.yalantis.contextmenu.lib.MenuObject;
import com.yalantis.contextmenu.lib.MenuParams;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemClickListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;



public class MainActivity extends FragmentActivity implements OnMenuItemClickListener,PullToRefreshBase.OnRefreshListener<ListView> {

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    public PullToRefreshListView notelist;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerArrowDrawable drawerArrow;//菜单
    private DialogFragment mMenuDialogFragment;
    private FragmentManager fragmentManager;
    FloatingActionButton fab = null;
    public static  List<Note> notes = new ArrayList<Note>();
    private User user;
    private SlideMenuAdapter slideAdapter;



    private JazzyViewPager mJazzy;
    private List<View> lists = new ArrayList<View>();

    private NoteManager noteManager;
    View catogery;
    View timeLine;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     /*   DbUserManager userManager = new DbUserManager(this);
        User user = new User("zeng","123456");
        userManager.insertUser(user);*/

        setContentView(R.layout.activity_main);
        ActionBar ab = getActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setHomeButtonEnabled(true);
        initData();





        fragmentManager = getSupportFragmentManager();
        initMenuFragment();

        initView();
        initSlideMenu();
        initListener();

        setupJazziness(JazzyViewPager.TransitionEffect.RotateDown);
        mDrawerList.setAdapter(slideAdapter);


        initRightBottomMenu();

    }

    /**
     * 初始化viewPage
     * @param effect
     */
    private void setupJazziness(JazzyViewPager.TransitionEffect effect) {
        mJazzy.setTransitionEffect(effect);
        mJazzy.setAdapter(new MainViewPageAdapter(lists, mJazzy));
        mJazzy.setPageMargin(30);
    }

    /**
     * 初始化诶分类监听
     * @param catogery
     */
    private void initCatogeryListener(View catogery) {
        notelist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, ShowNoteActiviy.class);
                startActivity(intent);
            }
        });
    }

    private void initListener() {
    //侧滑菜单监听
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                slideAdapter.setSelectItem(position);
                slideAdapter.notifyDataSetInvalidated();

                switch (position) {
                    case 0:
                        Intent intent = new Intent(MainActivity.this, PersonalInfoActivity.class);
                        startActivity(intent);
                        break;
                    case 1:
                        Intent notebook = new Intent(MainActivity.this, NoteBookActivity.class);
                        startActivity(notebook);
                        break;
                    case 2:
                        Intent setting = new Intent(MainActivity.this, SettingActivity.class);
                        startActivity(setting);
                        break;
                    case 3:
                        Intent about = new Intent(MainActivity.this, AboutActivity.class);
                        startActivity(about);
                        break;
                }

            }
        });
        notelist.setOnRefreshListener(this);
    }

    private void initData() {

        String[] values = new String[]{
                "笔记本",
                "设置", "关于"
        };
        slideAdapter = new SlideMenuAdapter(this,values);
        Note note = new Note(1,"学习","我的笔记","这是内容","1994-03-12");
        Note note2 = new Note(1,"生活","生活好难","这是内容", "1995-03-12");

        noteManager = new NoteManager();
        noteManager.addNote(note);
        noteManager.addNote(note2);
    }

    /**
     * 右下角菜单
     */
    private void initRightBottomMenu() {
   /*     fab.attachToListView(notelist, new ScrollDirectionListener() {
            @Override
            public void onScrollDown() {
                Log.d("ListViewFragment", "onScrollDown()");
            }

            @Override
            public void onScrollUp() {
                Log.d("ListViewFragment", "onScrollUp()");
            }
        }, new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                Log.d("ListViewFragment", "onScrollStateChanged()");
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                Log.d("ListViewFragment", "onScroll()");
            }
        });*/



        SubActionButton.Builder itemBuilder = new SubActionButton.Builder(this);
// repeat many times:
        ImageView btn_record_icon = new ImageView(this);
        btn_record_icon.setImageDrawable(getResources().getDrawable(R.drawable.btn_record_menu));
        SubActionButton btn_record = itemBuilder.setContentView(btn_record_icon).build();
        btn_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddAaudioNoteActivity.class);
                startActivity(intent);

            }
        });


        SubActionButton.Builder itemBuilder2 = new SubActionButton.Builder(this);
        ImageView btn_picture_icon = new ImageView(this);
        btn_picture_icon.setImageDrawable(getResources().getDrawable(R.drawable.icn_picture));
        SubActionButton btn_picture = itemBuilder2.setContentView(btn_picture_icon).build();
        btn_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddImageNoteActivity.class);
                startActivity(intent);

            }
        });

        SubActionButton.Builder itemBuilder3 = new SubActionButton.Builder(this);
        ImageView btn_text_icon = new ImageView(this);
        btn_text_icon.setImageDrawable(getResources().getDrawable(R.drawable.icn_text));
        SubActionButton btn_text = itemBuilder2.setContentView(btn_text_icon).build();

        btn_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddNoteActivity.class);
                startActivity(intent);

            }
        });


        FloatingActionMenu rightLowerMenu = new FloatingActionMenu.Builder(this)
                .addSubActionView(btn_record)
                .addSubActionView(btn_picture)
                .addSubActionView(btn_text)
                .attachTo(fab)
                .build();

        rightLowerMenu.setStateChangeListener(new FloatingActionMenu.MenuStateChangeListener() {
            @Override
            public void onMenuOpened(FloatingActionMenu menu) {
                // Rotate the icon of rightLowerButton 45 degrees clockwise
                fab.setRotation(0);
                PropertyValuesHolder pvhR = PropertyValuesHolder.ofFloat(View.ROTATION, 45);
                ObjectAnimator animation = ObjectAnimator.ofPropertyValuesHolder(fab, pvhR);
                animation.start();
            }

            @Override
            public void onMenuClosed(FloatingActionMenu menu) {
                // Rotate the icon of rightLowerButton 45 degrees counter-clockwise
                fab.setRotation(45);
                PropertyValuesHolder pvhR = PropertyValuesHolder.ofFloat(View.ROTATION, 0);
                ObjectAnimator animation = ObjectAnimator.ofPropertyValuesHolder(fab, pvhR);
                animation.start();
            }
        });
    }



    /**
     * 初始化滑动菜单
     */
    private void initSlideMenu() {
        drawerArrow = new DrawerArrowDrawable(this) {
            @Override
            public boolean isLayoutRtl() {
                return false;
            }
        };
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                drawerArrow, R.string.drawer_open,
                R.string.drawer_close) {

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                //     invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                //     invalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
    }

    /**
     * 初始化控件
     */
    private void initView() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.navdrawer);
        fab = (FloatingActionButton)findViewById(R.id.fab);
        mJazzy = (JazzyViewPager) findViewById(R.id.jazzy_pager);

        catogery = getLayoutInflater().inflate(R.layout.activity_category, null);
        lists.add(catogery);

        timeLine = getLayoutInflater().inflate(R.layout.activity_time_line, null);
        lists.add(timeLine);

        initCatogeryView(catogery);
        initCatogeryListener(catogery);
        initExpandListView(timeLine);
    }
    /**
     * 初始化分类控件
     */
    private void initCatogeryView(View view){
        notelist = (PullToRefreshListView) view.findViewById(android.R.id.list);
        ILoadingLayout startLabels = notelist.getLoadingLayoutProxy(true, false);
        startLabels.setPullLabel("下拉看笔记");// 刚下拉时，显示的提示
        startLabels.setRefreshingLabel("快来啦...");// 刷新时
        startLabels.setReleaseLabel("放开我你个禽兽!!!");// 下来达到一定距离时，显示的提示
    }


    /**
     * 初始化菜单fragment
     */
    private void initMenuFragment() {
        MenuParams menuParams = new MenuParams();
        menuParams.setActionBarSize((int) getResources().getDimension(R.dimen.tool_bar_height));
        menuParams.setMenuObjects(getMenuObjects());
        menuParams.setClosableOutside(false);
        mMenuDialogFragment = ContextMenuDialogFragment.newInstance(menuParams);
    }

    /**
     * 获取菜单对象
     * @return 菜单对象集
     */
    private List<MenuObject> getMenuObjects() {
        // You can use any [resource, bitmap, drawable, color] as image:
        // item.setResource(...)
        // item.setBitmap(...)
        // item.setDrawable(...)
        // item.setColor(...)
        // You can set image ScaleType:
        // item.setScaleType(ScaleType.FIT_XY)
        // You can use any [resource, drawable, color] as background:
        // item.setBgResource(...)
        // item.setBgDrawable(...)
        // item.setBgColor(...)
        // You can use any [color] as text color:
        // item.setTextColor(...)
        // You can set any [color] as divider color:
        // item.setDividerColor(...)

        List<MenuObject> menuObjects = new ArrayList();

        MenuObject close = new MenuObject();
        close.setResource(R.drawable.icn_close);

        MenuObject category = new MenuObject("按分类查看");
        category.setResource(R.drawable.icn_category);

        MenuObject time = new MenuObject("时间线");
        Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.icn_time);
        time.setBitmap(b);

        MenuObject tag = new MenuObject("按标签查看");
        BitmapDrawable bd = new BitmapDrawable(getResources(),
                BitmapFactory.decodeResource(getResources(), R.drawable.icn_tag));
        tag.setDrawable(bd);

        menuObjects.add(close);
        menuObjects.add(category);
        menuObjects.add(time);
        menuObjects.add(tag);
        return menuObjects;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (mDrawerLayout.isDrawerOpen(mDrawerList)) {
                mDrawerLayout.closeDrawer(mDrawerList);
            } else {
                mDrawerLayout.openDrawer(mDrawerList);
            }
        }
        switch (item.getItemId()) {
            case R.id.context_menu:
                mMenuDialogFragment.show(fragmentManager, "ContextMenuDialogFragment");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public void onMenuItemClick(View clickedView, int position) {
        switch (position){
            case 1:
                mJazzy.setCurrentItem(0);
                break;
            case 2:
                mJazzy.setCurrentItem(1);
                break;
        }
    }

    @Override
    protected void onResume() {
       setPageTrasition();
        notelist.setAdapter(new CategoryAdapter(this, noteManager.getNotes()));
        super.onResume();
    }

    /**
     * 设置过场动画
     */
    private void setPageTrasition() {
        int ram = new Random().nextInt(12);
        switch (ram){
            case 0:
                setupJazziness(JazzyViewPager.TransitionEffect.Standard);
                break;
            case 1:
                setupJazziness(JazzyViewPager.TransitionEffect.Tablet);
                break;
            case 2:
                setupJazziness(JazzyViewPager.TransitionEffect.CubeIn);
                break;
            case 3:
                setupJazziness(JazzyViewPager.TransitionEffect.CubeOut);
                break;
            case 4:
                setupJazziness(JazzyViewPager.TransitionEffect.FlipVertical);
                break;
            case 5:
                setupJazziness(JazzyViewPager.TransitionEffect.FlipHorizontal);
                break;
            case 6:
                setupJazziness(JazzyViewPager.TransitionEffect.Stack);
                break;
            case 7:
                setupJazziness(JazzyViewPager.TransitionEffect.ZoomIn);
                break;
            case 8:
                setupJazziness(JazzyViewPager.TransitionEffect.ZoomOut);
                break;
            case 9:
                setupJazziness(JazzyViewPager.TransitionEffect.RotateUp);
                break;
            case 10:
                setupJazziness(JazzyViewPager.TransitionEffect.RotateDown);
                break;
            case 11:
                setupJazziness(JazzyViewPager.TransitionEffect.Accordion);
                break;
        }
    }

    /**
     * 初始化可拓展列表
     */
    private void initExpandListView(View view) {
        ExpandableListView timelistView = (ExpandableListView) view.findViewById(R.id.lv_timeline);
        TimeLineAdapter statusAdapter = new TimeLineAdapter(this, getTimeLineData());
        timelistView.setAdapter(statusAdapter);
        timelistView.setGroupIndicator(null); // 去掉默认带的箭头
        timelistView.setSelection(0);// 设置默认选中项

        // 遍历所有group,将所有项设置成默认展开
        int groupCount = timelistView.getCount();
        for (int i = 0; i < groupCount; i++) {
            timelistView.expandGroup(i);
        }

        timelistView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                // TODO Auto-generated method stub
                return true;
            }
        });
    }
    private List<TimeLineEntity> getTimeLineData() {
        List<TimeLineEntity> timelists;
        String[] strArray = new String[] { "2015年10月22日", "2015年10月23日", "2015年10月25日" };
        timelists = new ArrayList<TimeLineEntity>();
        for (int i = 0; i < strArray.length; i++) {
            TimeLineEntity timeline = new TimeLineEntity();
            timeline.setTime(strArray[i]);
            timeline.setNoteList(notes);
            timelists.add(timeline);
        }
        return timelists;
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
