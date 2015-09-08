package com.rainbow.lightnote.ui.activity;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.ActionBar;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.ikimuhendis.ldrawer.ActionBarDrawerToggle;
import com.ikimuhendis.ldrawer.DrawerArrowDrawable;
import com.melnykov.fab.FloatingActionButton;
import com.melnykov.fab.ScrollDirectionListener;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;
import com.rainbow.lightnote.R;
import com.rainbow.lightnote.adapter.SlideMenuAdapter;
import com.rainbow.lightnote.adapter.CategoryAdapter;
import com.rainbow.lightnote.db.dao.NoteManager;
import com.rainbow.lightnote.db.dao.UserManager;
import com.rainbow.lightnote.model.Note;
import com.rainbow.lightnote.model.User;
import com.yalantis.contextmenu.lib.ContextMenuDialogFragment;
import com.yalantis.contextmenu.lib.MenuObject;
import com.yalantis.contextmenu.lib.MenuParams;
import com.yalantis.phoenix.PullToRefreshView;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout.BGARefreshLayoutDelegate;
import cn.bingoogolapple.refreshlayout.BGARefreshViewHolder;


public class MainActivity extends FragmentActivity implements BGARefreshLayoutDelegate {

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private SwipeMenuListView list;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerArrowDrawable drawerArrow;//菜单
    private boolean drawerArrowColor;
    PullToRefreshView mPullToRefreshView;
    private DialogFragment mMenuDialogFragment;
    private FragmentManager fragmentManager;
    final String[] mStringList = {"LightNote", "Just Do It"};
    FloatingActionButton fab = null;
    private BGARefreshLayout mRefreshLayout;
    private NoteManager noteManager;
    private  List<Note> notes = new ArrayList<Note>();
    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UserManager userManager = new UserManager(this);
        User user = new User("zeng","123456");
        userManager.insertUser(user);

        setContentView(R.layout.activity_main);
        ActionBar ab = getActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setHomeButtonEnabled(true);
        Log.e("MainActivity", ab.getHeight() + "");
        fragmentManager = getSupportFragmentManager();
        initMenuFragment();
        initData();
        initView();
        initSlideMenu();
        initSwipeMenu();

        String[] values = new String[]{
            "所有笔记",
            "笔记分类",
            "设置", "关于"
        };

        String[] values2 = new String[]{
                "所有笔记",
                "笔记分类",
                "笔记分类",
                "笔记分类",
                "笔记分类",
                "笔记分类",
                "笔记分类",
                "笔记分类",
                "笔记分类",
                "笔记分类",
                "笔记分类",
                "笔记分类",
                "笔记分类",
                "笔记分类",
                "笔记分类",
                "笔记分类",
                "笔记分类",
                "笔记分类",
                "笔记分类",
                "笔记分类",
                "设置"
        };

        list.setAdapter(new CategoryAdapter(this, notes));
        mDrawerList.setAdapter(new SlideMenuAdapter(this, values));
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                switch (position) {
                    case 0:
                        Intent intent = new Intent(MainActivity.this,PersonalInfoActivity.class);
                        startActivity(intent);
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                }

            }
        });

        initRightBottomMenu();

        initRefreshLayout(mRefreshLayout);
    }

    private void initData() {
        Note note = new Note(1,"学习","我的笔记","这是内容","1994-03-12");
        notes.add(note);
        Note note2 = new Note(1,"生活","生活好难","这是内容","1995-03-12");
        notes.add(note2);
    }

    /**
     * 右下角菜单
     */
    private void initRightBottomMenu() {
        fab.attachToListView(list, new ScrollDirectionListener() {
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
        });



        SubActionButton.Builder itemBuilder = new SubActionButton.Builder(this);
// repeat many times:
        ImageView btn_record_icon = new ImageView(this);
        btn_record_icon.setImageDrawable(getResources().getDrawable(R.drawable.btn_record_menu));
        SubActionButton btn_record = itemBuilder.setContentView(btn_record_icon).build();

        SubActionButton.Builder itemBuilder2 = new SubActionButton.Builder(this);
        ImageView btn_picture_icon = new ImageView(this);
        btn_picture_icon.setImageDrawable(getResources().getDrawable(R.drawable.icn_picture));
        SubActionButton btn_picture = itemBuilder2.setContentView(btn_picture_icon).build();

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
     * listview滑动菜单
     */
    private void initSwipeMenu() {
        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem openItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
                        0xCE)));
                // set item width
                openItem.setWidth(130);
                // set item title
                openItem.setTitle("Open");
           //     openItem.setIcon(R.drawable.ic_favorite);
                // set item title fontsize
                openItem.setTitleSize(18);
                // set item title font color
                openItem.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(openItem);

                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
                        0xCE)));
                // set item width
                deleteItem.setWidth(130);
                deleteItem.setTitle("delete");
                // set a icon
                      deleteItem.setIcon(R.drawable.ic_delete);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };

// set creator
        list.setMenuCreator(creator);
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
        mRefreshLayout = (BGARefreshLayout)findViewById(R.id.rl_modulename_refresh);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.navdrawer);
        list = (SwipeMenuListView) findViewById(android.R.id.list);
        fab = (FloatingActionButton)findViewById(R.id.fab);
    }

    /**
     * 初始化下拉刷新
     * @param refreshLayout
     */
    private void initRefreshLayout(BGARefreshLayout refreshLayout) {
    //     为BGARefreshLayout设置代理
        mRefreshLayout.setDelegate(this);
     //    设置下拉刷新和上拉加载更多的风格     参数1：应用程序上下文，参数2：是否具有上拉加载更多功能
        BGARefreshViewHolder refreshViewHolder = new BGANormalRefreshViewHolder(this, true);
   //      设置下拉刷新和上拉加载更多的风格
        mRefreshLayout.setRefreshViewHolder(refreshViewHolder);
        mRefreshLayout.setIsShowLoadingMoreView(false);

    //     为了增加下拉刷新头部和加载更多的通用性，提供了以下可选配置选项  -------------START
     //    设置正在加载更多时不显示加载更多控件
     //    mRefreshLayout.setIsShowLoadingMoreView(false);
    //     设置正在加载更多时的文本
    /*    refreshViewHolder.setLoadingMoreText(loadingMoreText);
     //    设置整个加载更多控件的背景颜色资源id
        refreshViewHolder.setLoadMoreBackgroundColorRes(loadMoreBackgroundColorRes);
     //    设置整个加载更多控件的背景drawable资源id
        refreshViewHolder.setLoadMoreBackgroundDrawableRes(loadMoreBackgroundDrawableRes);
     //    设置下拉刷新控件的背景颜色资源id
        refreshViewHolder.setRefreshViewBackgroundColorRes(refreshViewBackgroundColorRes);
      //   设置下拉刷新控件的背景drawable资源id
        refreshViewHolder.setRefreshViewBackgroundDrawableRes(refreshViewBackgroundDrawableRes);
       //  设置自定义头部视图（也可以不用设置）     参数1：自定义头部视图（例如广告位）， 参数2：上拉加载更多是否可用
        mRefreshLayout.setCustomHeaderView(mBanner, false);
      //   可选配置  -------------END*/
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        // 在这里加载最新数据

        if (true) {
            // 如果网络可用，则加载网络数据
            new AsyncTask<Void, Void, Void>() {

                @Override
                protected Void doInBackground(Void... params) {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    // 加载完毕后在UI线程结束下拉刷新
                    mRefreshLayout.endRefreshing();
                 //   mDatas.addAll(0, DataEngine.loadNewData());
               //     mAdapter.setDatas(mDatas);
                }
            }.execute();
        } else {
            // 网络不可用，结束下拉刷新
            Toast.makeText(this, "网络不可用", Toast.LENGTH_SHORT).show();
            mRefreshLayout.endRefreshing();
        }
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout bgaRefreshLayout) {
        return false;
    }

    // 通过代码方式控制进入正在刷新状态。应用场景：某些应用在activity的onStart方法中调用，自动进入正在刷新状态获取最新数据
    public void beginRefreshing() {
    //    mRefreshLayout.beginRefreshing();
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

        List<MenuObject> menuObjects = new ArrayList<>();

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


}
