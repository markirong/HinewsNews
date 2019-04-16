package com.hinews.view.fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hinews.R;
import com.hinews.base.BaseFragment;
import com.hinews.bean.NewsContentBean;
import com.hinews.utils.AnimationUtil;
import com.hinews.utils.GsonUtil;
import com.hinews.utils.OkHttpUtils;
import com.hinews.utils.ToastUtil;
import com.hinews.utils.InfoUtil;
import com.hinews.utils.UIUtils;
import com.hinews.view.activity.AlbumActivity;
import com.hinews.view.activity.WebViewActivity;
import com.hinews.view.adapter.NewsListAdapter;
import com.hinews.widget.popup.LoseInterestPopup;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.jzvd.JZMediaManager;
import cn.jzvd.JZUtils;
import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerManager;
/**
 *  markirong  2019 3 19
 **/
public class NewsListFragment extends BaseFragment implements RecyclerView.OnChildAttachStateChangeListener,
        BaseQuickAdapter.OnItemClickListener,  BaseQuickAdapter.OnItemChildClickListener,
        LoseInterestPopup.OnLoseInterestListener ,  OnLoadMoreListener ,  OnRefreshListener{

    public static String CHANNELNAME="channelNanme";
    public static String H5URL="H5URL"; public static String LISTTYPE="LISTTYPE";
    static String ONFRESH="ONREFRESH";  static String ONLOAD ="ONLOAD";
    private static String TAG =NewsListFragment.class.getSimpleName();
    private  RecyclerView mRecyclerView;      private TextView mTvTip;
    private SmartRefreshLayout mSmartRefreshLayout;
    private NewsListAdapter mAdapter;
    public String channelName,h5Url,listType, PageNo;
    private Map<String,String> urlMap =new HashMap<>();
    private  NewsContentBean newsContentBean;
    private  List<NewsContentBean.ContentBeans> contentBean;
    //异步 ，ANR与upgrade
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            String info= (String) msg.obj;
            if(msg.what ==1){
                finishLnR();

//                if(newsContentBean.getContent().size()>0 && newsContentBean.getContent()!=null){
//                    mAdapter = new NewsListAdapter(newsContentBean.getContent());
//                    mRecyclerView.setAdapter(mAdapter);
//                }

                if(mAdapter!=null){
                    mAdapter.notifyDataSetChanged();
                }
                else{
                    mAdapter = new NewsListAdapter(newsContentBean.getContent());
                    mRecyclerView.setAdapter(mAdapter);
                }
                return true;
            }
            return false;
        }
    });
    @Override
    protected int provideContentViewId() {
        return R.layout.fragment_news_list;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        channelName = args != null ? args.getString(NewsListFragment.CHANNELNAME) : "44";
        h5Url = args != null ? args.getString(NewsListFragment.H5URL) : "";
        listType = args != null ? args.getString(NewsListFragment.LISTTYPE) : "api";

        Log.i(TAG,channelName +"  //h5Url:    " +  h5Url +"  //listType:  " +listType);
    }
    @Override
    public void initView(View rootView) {
        mTvTip = findViewById(R.id.tv_tip);
        mSmartRefreshLayout = findViewById(R.id.refreshLayout);
        mSmartRefreshLayout.setOnRefreshListener(this);
        mSmartRefreshLayout.setOnLoadMoreListener(this);
        mSmartRefreshLayout.setEnableOverScrollBounce(false);//是否启用越界回弹
        mSmartRefreshLayout.setEnableOverScrollDrag(false);
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        mRecyclerView.addOnChildAttachStateChangeListener(this);
        //
//        mAdapter = new NewsListAdapter(newsContentBean.getContent());
//        mRecyclerView.setAdapter(mAdapter);
//        mAdapter.setOnItemClickListener(this);
//        mAdapter.setOnItemChildClickListener(this);

//        this.registerEventBus(this);
    }

    @Override
    public void initListener() {
        if(mAdapter!=null){
            mAdapter.setOnItemClickListener(this);
            mAdapter.setOnItemChildClickListener(this);
        }
    }
    @Override
    protected void loadData() {
        PageNo = "1";
        InitChannelData11(PageNo,"");
    }
    @Override
    public void onChildViewAttachedToWindow(View view) { }
    @Override
    public void onChildViewDetachedFromWindow(View view) {
        JZVideoPlayer jzvd = view.findViewById(R.id.video_player);
        if (jzvd!=null){
            Object[] dataSourceObjects = jzvd.dataSourceObjects;
            if (dataSourceObjects!=null&&
                    JZUtils.dataSourceObjectsContainsUri(dataSourceObjects, JZMediaManager.getCurrentDataSource())) {
                JZVideoPlayer currentJzvd = JZVideoPlayerManager.getCurrentJzvd();
                if (currentJzvd != null && currentJzvd.currentScreen != JZVideoPlayer.SCREEN_WINDOW_FULLSCREEN) {
                    JZVideoPlayer.releaseAllVideos();
                }
            }
        }
    }
    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
//        mSmartRefreshLayout.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                mSmartRefreshLayout.finishRefresh(0);
//                AnimationUtil.showTipView(mTvTip,mRecyclerView);
//            }
//        },1400);
     PageNo ="1";
     InitChannelData11(PageNo,ONFRESH);
    }
    @Override
    public void onLoadMore(RefreshLayout refreshLayout) {
        Log.i(TAG,"onLoadMore");
//        mSmartRefreshLayout.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                mSmartRefreshLayout.finishLoadMore(0);
//            }
//        },1400);
        InitChannelData11(PageNo,ONLOAD);
    }
    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//        switch (position % 7){
        switch (adapter.getItemViewType(position)){
            case 0:
                startActivity(new Intent(getActivity(), WebViewActivity.class));
                break;
            case 1:
                break;
//                return NEWS_HOT_LIST;
            case 2:
                break;
//                return NEWS_HOT_TEXT;
            case NewsListAdapter.NEWS_ALBUM_PHOTO:
                ToastUtil.showToast("NEWS_ALBUM_PHOTO");
                break;
//                return NEWS_SIMPLE_PHOTO;
            case 4:
                break;
//                return NEWS_THREE_PHOTO;
            case 5:
                startActivity(new Intent(getActivity(), AlbumActivity.class));
                break;
            case 6:
                break;
        }
    }
    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        switch (view.getId()){
            case R.id.iv_delete:
                new LoseInterestPopup(getActivity()) .setSource("南海网")
                    .setPosition(position).setOnLoseInterestListener(this)
                    .showPopup(view);
                break;
        }
    }
    @Override
    public void onLoseInterestListener(int poor_quality, int repeat, String source, int position) {
        ToastUtil.showToast("position = "+position);
    }

    public void InitChannelData11(String pageNo , final String operation){
        urlMap.put("action","lists");
        urlMap.put("version",2+"");
        urlMap.put("name",channelName);
        urlMap.put("page","");
        Log.i(TAG,pageNo);

        OkHttpUtils.getInstance().doPost(InfoUtil.ChannelsUrl, urlMap, new OkHttpUtils.ICallBack() {
            @Override
            public void failed(Exception e) {
                mSmartRefreshLayout.finishRefresh();
                mSmartRefreshLayout.finishLoadMore();
                ToastUtil.showToast("服务器无响应，程序员各各已经在拼命了..");
            }
            @Override
            public void success(String json) {
                Log.i(TAG,json.toString());
                try {
                    newsContentBean = GsonUtil.GsonToBean(json, NewsContentBean.class);
                    PageNo =newsContentBean.getPage();
                }catch (Exception e){
                    e.printStackTrace();
                }


//              SharePreUtil.putString(getActivity(),ChannelActivity.district,GsonUtil.GsonString(channelBeans.getResult().get(2).getSection_data()));
                Message message=handler.obtainMessage();
                message.what=1;
                message.obj =json.toString();
                handler.sendMessage(message);
            }
        });

    }
    //
    private  void finishLnR(){
        AnimationUtil.showTipView(mTvTip,mRecyclerView);
        mSmartRefreshLayout.finishRefresh();
        mSmartRefreshLayout.finishLoadMore();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }




}


