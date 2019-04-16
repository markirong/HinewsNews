package com.hinews.view.activity;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import com.hinews.R;
import com.hinews.base.BaseActivity;
import com.hinews.utils.UIUtils;
import com.hinews.view.adapter.NewsDetailAdapter;

import java.util.Arrays;

public class WebViewActivity extends BaseActivity {

    private WebView mWebView;
    private RecyclerView mRecyclerView;
    private NewsDetailAdapter mAdapter;
    private AppBarLayout mAppBarLayout;
    private TextView mTvPublisher;
    private View mLltHead, mBarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        initView();
        initListener();
    }
    private void initView() {
        setWhiteStatus();
        mTvPublisher = findViewById(R.id.tv_publisher);
        mAppBarLayout = findViewById(R.id.app_bar);
        mLltHead = findViewById(R.id.llt_head);
        mBarLayout = findViewById(R.id.rlt_bar);
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mAdapter = new NewsDetailAdapter(Arrays.asList(getResources().getStringArray(R.array.hot)));
        mRecyclerView.setAdapter(mAdapter);
        //
        initWebview();
    }
    private void initWebview() {
        View view = View.inflate(this, R.layout.item_webview, null);
        mWebView = view.findViewById(R.id.webview);
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);//适应
        mWebView.loadUrl("http://www.hinews.cn/english/system/2019/04/11/032071133.shtml");
        mAdapter.addHeaderView(view);
    }
    private void initListener() {
        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                float a = Math.abs(verticalOffset) * 1.0f / appBarLayout.getTotalScrollRange();
                mLltHead.setAlpha(1f - a);
                mTvPublisher.setAlpha(a);
                mBarLayout.setBackgroundColor(UIUtils.changeAlpha(getResources().getColor(R.color.white), (int) (a*255)));
            }
        });

    }

}
