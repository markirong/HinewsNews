package com.hinews.view.fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hinews.R;
import com.hinews.base.BaseFragment;
import com.hinews.bean.ChannelBean;
import com.hinews.bean.ChannelBean.ResultBean.SectionDataBean;
import com.hinews.channel.Channel;
import com.hinews.utils.GsonUtil;
import com.hinews.utils.OkHttpUtils;
import com.hinews.utils.SharePreUtil;
import com.hinews.utils.StringUtils;
import com.hinews.utils.ToastUtil;
import com.hinews.utils.UIUtils;
import com.hinews.utils.InfoUtil;
import com.hinews.view.activity.ChannelActivity;
import com.hinews.view.activity.MainActivity;
import com.hinews.view.adapter.BaseFragmentAdapter;
import com.hinews.view.adapter.SearchAdapter;
import com.hinews.widget.SearchRecyclerView;
import com.hinews.widget.magicindicator.MagicIndicator;
import com.hinews.widget.magicindicator.buildins.UIUtil;
import com.hinews.widget.magicindicator.buildins.commonnavigator.CommonNavigator;
import com.hinews.widget.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import com.hinews.widget.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import com.hinews.widget.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import com.hinews.widget.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import com.hinews.widget.magicindicator.buildins.commonnavigator.titles.ClipPagerTitleView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class NewsFragment extends BaseFragment implements View.OnClickListener, BaseQuickAdapter.OnItemClickListener {
    public String TAG = getClass().getSimpleName();
    public static final int offset = 100;
    private ViewPager mViewPager;
    private MainActivity mMainActivity;
    private View mLltNews;
    private DrawerLayout mDrawerLayout;
    private List<BaseFragment> fragments = new ArrayList<>();
    private MagicIndicator mIndicator;
    public List<Channel> channelList= new ArrayList<>();
    private SearchRecyclerView mSearchRecyclerView;
    private SearchAdapter mSearchAdapter;
    private Map<String,String> urlMap =new HashMap<>();
    private int mDisplayWidth;  private ImageView iv_navi; protected  NewsListFragment newsListFragment;
    private String ChannelInfo;
    List<ChannelBean.ResultBean.SectionDataBean> myChannels;

    static{
       InitChannelData();
       Log.i("ddd","dd");
    }
    @Override
    protected int provideContentViewId() {
        return R.layout.fragment_news;
    }

    @Override
    public void initView(View rootView) {
        mViewPager = findViewById(R.id.viewpager);  mIndicator= findViewById(R.id.magic_indicator);
        mDrawerLayout = findViewById(R.id.drawer_layout);   mLltNews = findViewById(R.id.llt_news);
        iv_navi= findViewById(R.id.iv_navi); iv_navi.setOnClickListener(this);
        //设置搜索界面宽度为屏幕宽度
        mDisplayWidth = getResources().getDisplayMetrics().widthPixels;
        LinearLayout mLltSearch = rootView.findViewById(R.id.llt_search);
        ViewGroup.LayoutParams layoutParams = mLltSearch.getLayoutParams();
        layoutParams.width = mDisplayWidth;
        mLltSearch.setLayoutParams(layoutParams);

        mSearchRecyclerView = findViewById(R.id.recyclerView);
        mSearchRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        mSearchAdapter = new SearchAdapter(Arrays.asList(getResources().getStringArray(R.array.hot)));
        mSearchRecyclerView.setAdapter(mSearchAdapter);
        mSearchAdapter.setOnItemClickListener(this);
        //
//        InitChannelData();
    }
    @Override
    protected void loadData() {
        String navigation = SharePreUtil.getString(getActivity(), ChannelActivity.navigation);
        if(StringUtils.isEmpty(navigation)){
            ChannelInfo= InfoUtil.originChannels;
            myChannels =GsonUtil.GsonToBean(ChannelInfo , ChannelBean.class).getResult().get(0).getSection_data();
        }else{
            myChannels = GsonUtil.jsonToList(navigation,SectionDataBean.class);
        }

        Log.i("ddd",myChannels.size()+"");


        for ( int i =0; i<myChannels.size();i++){
            newsListFragment =new NewsListFragment();
            Bundle bundle = new Bundle();
            bundle.putString(NewsListFragment.CHANNELNAME,myChannels.get(i).getChannelname());
            bundle.putString(NewsListFragment.H5URL,myChannels.get(i).getUrl());
            bundle.putString(NewsListFragment.LISTTYPE,myChannels.get(i).getListtype());
			newsListFragment.setArguments(bundle);
            fragments.add(newsListFragment);
            //
            channelList.add(new Channel(myChannels.get(i).getChannelname(),
                     Integer.parseInt(myChannels.get(i).getChannelselected()),
                     myChannels.get(i).getListtype(),
                     myChannels.get(i).getUrl()));
         }

        //
        initIndicator();
        //
        mViewPager.setAdapter(new BaseFragmentAdapter(fragments,getChildFragmentManager()));
    }

    @Override
    public void initListener() {
        findViewById(R.id.tv_seacher).setOnClickListener(this);
        findViewById(R.id.tv_close_search).setOnClickListener(this);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {  }
            @Override
            public void onPageSelected(int position) {
                //position为0的时候才能打开搜索界面
                if (position == 0){
                    unlockDrawer();
                }else {
                    lockDrawer();
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) { }
        });
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                mIndicator.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }
            @Override
            public void onPageSelected(int position) {
                mIndicator.onPageSelected(position);
            }
            @Override
            public void onPageScrollStateChanged(int state) {
                mIndicator.onPageScrollStateChanged(state);
            }
        });
        mDrawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                mLltNews.scrollTo((int) (-mDisplayWidth * slideOffset * 0.8),0);
            }
            @Override
            public void onDrawerOpened(View drawerView) {
                mMainActivity.hideBottomBarLayout();
            }
            @Override
            public void onDrawerClosed(View drawerView) {
                mMainActivity.showBottomBarLayout();
            }
            @Override
            public void onDrawerStateChanged(int newState) { }
        });
    }

    private void initIndicator(){
        CommonNavigator mCommonNavigator = new CommonNavigator(UIUtils.getContext());
        mCommonNavigator.setSkimOver(true);
        mCommonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return channelList.size();
            }
            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                ClipPagerTitleView clipPagerTitleView = new ClipPagerTitleView(context);
                clipPagerTitleView.setText(channelList.get(index).getChannelname());
                clipPagerTitleView.setTextSize(UIUtils.sp2px(15));
                clipPagerTitleView.setClipColor(getResources().getColor(R.color.colorPrimary));
                clipPagerTitleView.setTextColor(getResources().getColor(R.color.color_BDBDBD));
                clipPagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mViewPager.setCurrentItem(index);
                    }
                });
                return clipPagerTitleView;
            }
            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
                indicator.setYOffset(UIUtil.dip2px(context, 3));
                indicator.setRoundRadius(5);
                indicator.setColors(getResources().getColor(R.color.colorPrimary));
                return indicator;
            }
        });
        mIndicator.setNavigator(mCommonNavigator);
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mMainActivity = (MainActivity) getActivity();
    }
    private void lockDrawer(){
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }
    private void unlockDrawer(){
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
    }
    public boolean isSeacherOpen(){
        return mDrawerLayout.isDrawerOpen(Gravity.START);
    }
    public void closeSeacher(){
        mDrawerLayout.closeDrawer(Gravity.START);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_seacher:
                mDrawerLayout.openDrawer(Gravity.START);
                break;
            case R.id.tv_close_search:
                mDrawerLayout.closeDrawer(Gravity.START);
                break;
            case R.id.iv_navi:
                startActivityForResult(new Intent().setClass(getActivity(), ChannelActivity.class),123);

                break;
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        channelList.clear();
        fragments.clear();
        //
        loadData();
    }
    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        ToastUtil.showToast(mSearchAdapter.getItem(position));
    }

    public static void InitChannelData(){

        Map<String,String> urlMap =new HashMap<>();
        urlMap.put("action","navlists");
        urlMap.put("version",2+"");
        urlMap.put("cid","news");
        OkHttpUtils.getInstance().doPost(InfoUtil.ChannelsUrl, urlMap, new OkHttpUtils.ICallBack() {
            @Override
            public void failed(Exception e) {
                ToastUtil.showToast("程序员各各已经在拼命修..");
            }
            @Override
            public void success(String json) {
                ChannelBean channelBeans =GsonUtil.GsonToBean(json,ChannelBean.class);
//                Log.i(TAG,channelBeans.getResult().get(0).getSection_data().toString());
                SharePreUtil.putString(UIUtils.getContext(),ChannelActivity.navigation,GsonUtil.GsonString(channelBeans.getResult().get(0).getSection_data()));
                SharePreUtil.putString(UIUtils.getContext(),ChannelActivity.recommend,GsonUtil.GsonString(channelBeans.getResult().get(1).getSection_data()));
                SharePreUtil.putString(UIUtils.getContext(),ChannelActivity.district,GsonUtil.GsonString(channelBeans.getResult().get(2).getSection_data()));
            }
        });
    }

}
