package com.hinews.view.activity;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import com.chaychan.library.BottomBarItem;
import com.chaychan.library.BottomBarLayout;
import com.hinews.R;
import com.hinews.base.BaseActivity;
import com.hinews.base.BaseFragment;
import com.hinews.utils.ToastUtil;
import com.hinews.view.adapter.BaseFragmentAdapter;
import com.hinews.view.fragment.MineFragment;
import com.hinews.view.fragment.NewsFragment;
import com.hinews.view.fragment.RecommendFragment;

import java.util.ArrayList;
import java.util.List;
import cn.jzvd.JZVideoPlayer;
public class MainActivity extends BaseActivity implements BottomBarLayout.OnItemSelectedListener {

    private ViewPager mViewPager;
    private List<BaseFragment> mFragments;
    private BaseFragmentAdapter mAdapter;
    private BottomBarLayout mBottomBarLayout;
    private long mLastClick;
    private NewsFragment mNewsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //
        initView();
    }

    private void initView() {
        setWhiteStatus();
        mViewPager = findViewById(R.id.viewpager);
        mBottomBarLayout = findViewById(R.id.bottom_bar);
        mFragments = new ArrayList<>();
        mNewsFragment = new NewsFragment();
        mFragments.add(mNewsFragment);
        mFragments.add(new RecommendFragment());
        mFragments.add(new RecommendFragment());
        mFragments.add(new MineFragment());
        mFragments.add(new MineFragment());
        //
        mAdapter = new BaseFragmentAdapter(mFragments,getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);

        mBottomBarLayout.setViewPager(mViewPager);
        mBottomBarLayout.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(BottomBarItem bottomBarItem, int i) {}
    public void showBottomBarLayout() {
        mBottomBarLayout.setVisibility(View.VISIBLE);
    }
    public void hideBottomBarLayout() {
        mBottomBarLayout.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        if (JZVideoPlayer.backPress()) {
        } else if (mNewsFragment.isSeacherOpen()) {
            mNewsFragment.closeSeacher();
        } else {
            if (System.currentTimeMillis() - mLastClick > 2000) {
                ToastUtil.showToast("再按一次退出南海网客户端");
                mLastClick = System.currentTimeMillis();
            } else {
                super.onBackPressed();
            }
        }
    }
}
