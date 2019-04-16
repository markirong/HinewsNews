package com.hinews.view.adapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hinews.R;
import com.hinews.bean.NewsContentBean.ContentBeans;
import com.hinews.utils.ToastUtil;
import com.hinews.utils.UIUtils;
import com.hinews.widget.HotRefrechHead;
import com.hinews.widget.MyHorizontalRefreshLayout;
import java.util.List;
import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;
import xiao.free.horizontalrefreshlayout.HorizontalRefreshLayout;
import xiao.free.horizontalrefreshlayout.RefreshCallBack;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;
public class NewsListAdapter extends BaseMultiItemQuickAdapter<ContentBeans,BaseViewHolder> implements View.OnClickListener {
     //专题 大图
    public static final int NEWS_SUBJECT = 100;
     //热点列表,可横向滚动
    public static final int NEWS_HOT_LIST = 200;
     //热点列表,纯文字滚动
    public static final int NEWS_HOT_TEXT = 201;
    // 单图右侧小图布局(1.小图新闻；2.视频类型，右下角 时长)
    public static final int  NEWS_SIMPLE_PHOTO = 300;
    // 三张图片布局(文章、广告)
    public static final int NEWS_THREE_PHOTO = 400;
    //图集
    public static final int NEWS_ALBUM_PHOTO = 500;
    //视频
    public static final int NEWS_VIDEO = 600;
    // 一行两张领导
    public static final  int LEADERS_ROW =700;
    // 二级栏目 夜读
    public static final int SECOND_LANMU=800;
    //BANBER广告位
    public static final int BANNER =900;
    //广告位 大图
    public static final int BANNER_BIG=1000;
    //无图新闻
    public static  final int TITLELAB =1100;
    //24小时
    public static  final  int HOURSENEWS=1200;


    public NewsListAdapter(List<ContentBeans> data) {
        super(data);
        addItemType(NEWS_SUBJECT, R.layout.item_news_subject);
        addItemType(NEWS_SIMPLE_PHOTO, R.layout.item_news_simple_photo);
        addItemType(NEWS_THREE_PHOTO, R.layout.item_news_three_photo);
        addItemType(NEWS_ALBUM_PHOTO, R.layout.item_news_album_photo);
        addItemType(NEWS_VIDEO, R.layout.item_news_video);
        addItemType(NEWS_HOT_LIST, R.layout.item_news_hot_list);
        addItemType(NEWS_HOT_TEXT, R.layout.item_hot_text_srcoll);
        addItemType(SECOND_LANMU, R.layout.item_hot_text_srcoll);
        addItemType(BANNER, R.layout.item_hot_text_srcoll);
        addItemType(BANNER_BIG, R.layout.item_hot_text_srcoll);
        addItemType(TITLELAB, R.layout.item_hot_text_srcoll);
        addItemType(HOURSENEWS, R.layout.item_hot_text_srcoll);
        addItemType(LEADERS_ROW, R.layout.item_leader_row);
    }

    @Override
    protected void convert(BaseViewHolder helper, ContentBeans item) {
        switch (helper.getItemViewType()){
            case NEWS_HOT_LIST:    //
                setHotList(helper,item);
                break;
            case NEWS_VIDEO:  //视频
                setScrollText(helper);
                JZVideoPlayerStandard videoPlayer = helper.getView(R.id.video_player);
                videoPlayer.setAllControlsVisiblity(GONE, GONE, VISIBLE, GONE, VISIBLE, VISIBLE, GONE);
                videoPlayer.setState(JZVideoPlayer.CURRENT_STATE_NORMAL);
                videoPlayer.resetProgressAndTime();
//                videoPlayer.setUp("http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4", JZVideoPlayer.SCREEN_WINDOW_LIST,"hinews news");
                videoPlayer.setUp(item.getVideo_src(), JZVideoPlayer.SCREEN_WINDOW_LIST,item.getTitle());
                videoPlayer.thumbImageView.setImageResource(R.drawable.bg_motherland);
                break;
            case NEWS_HOT_TEXT:
                setScrollText(helper);
                break;
            case NEWS_SIMPLE_PHOTO:  //小图+文
              TextView tv_title = helper.getView(R.id.tv_title);
              tv_title.setText(item.getTitle());
              ImageView iv_simple=  helper.getView(R.id.iv_simple);
                Glide.with(UIUtils.getContext()).load(item.getImg().get(0)).crossFade()
                        .diskCacheStrategy(DiskCacheStrategy.NONE).into(iv_simple);
                break;
            case NEWS_THREE_PHOTO:  //三张图
                ImageView image1 =helper.getView(R.id.image1);
                ImageView image2 =helper.getView(R.id.image2);
                ImageView image3 =helper.getView(R.id.image3);
                if(item.getImg().size()>2){
                    Glide.with(UIUtils.getContext()).load(item.getImg().get(0)).crossFade()
                            .diskCacheStrategy(DiskCacheStrategy.NONE).into(image1);
                    Glide.with(UIUtils.getContext()).load(item.getImg().get(1)).crossFade()
                            .diskCacheStrategy(DiskCacheStrategy.NONE).into(image2);
                    Glide.with(UIUtils.getContext()).load(item.getImg().get(2)).crossFade()
                            .diskCacheStrategy(DiskCacheStrategy.NONE).into(image3);
                }
                break;
            case NEWS_SUBJECT:   // 大图专题
                ImageView iv_bigsimple =helper.getView(R.id.iv_bigsimple);
                TextView tv_bigtitle =helper.getView(R.id.tv_bigtitle);
                tv_bigtitle.setText(item.getTitle());
                Glide.with(UIUtils.getContext()).load(item.getImg().get(0)).crossFade()
                        .diskCacheStrategy(DiskCacheStrategy.NONE).into(iv_bigsimple);
                break;
            case LEADERS_ROW:   // 书籍省长
                ImageView iv_leader1 =helper.getView(R.id.iv_leader1);
                ImageView iv_leader2 =helper.getView(R.id.iv_leader2);

                Glide.with(UIUtils.getContext()).load(item.getItems().get(0).getImg().get(0)).crossFade()
                        .diskCacheStrategy(DiskCacheStrategy.NONE).into(iv_leader1);
                Glide.with(UIUtils.getContext()).load(item.getItems().get(1).getImg().get(0)).crossFade()
                        .diskCacheStrategy(DiskCacheStrategy.NONE).into(iv_leader2);


                break;

//                case :   // 书籍省长
////                ImageView iv_leader1 =helper.getView(R.id.iv_leader1);
////                ImageView iv_leader2 =helper.getView(R.id.iv_leader2);
//
//                Glide.with(UIUtils.getContext()).load(item.getItems().get(0).getImg().get(0)).crossFade()
//                        .diskCacheStrategy(DiskCacheStrategy.NONE).into(iv_leader1);
//                Glide.with(UIUtils.getContext()).load(item.getItems().get(1).getImg().get(0)).crossFade()
//                        .diskCacheStrategy(DiskCacheStrategy.NONE).into(iv_leader2);
//
//                break;
        }
        //
        helper.addOnClickListener(R.id.iv_delete);
    }

    private void setScrollText(BaseViewHolder helper){
        ViewFlipper viewFlipper = helper.getView(R.id.viewFlipper);
        if (viewFlipper != null){
            if (viewFlipper.getChildCount() > 0){
                viewFlipper.removeAllViews();
            }
            String[] array = UIUtils.getContext().getResources().getStringArray(R.array.hot);
                for (String string : array){
                    TextView textView = (TextView) View.inflate(mContext,R.layout.item_hot_flipper,null);
                    textView.setText(string);
                    textView.setTag(string);
                    textView.setOnClickListener(this);
                    viewFlipper.addView(textView);
            }
            viewFlipper.startFlipping();
        }
    }

    @Override
    public int getItemViewType(int position) {
        switch (getData().get(position).getItemType()){
            case 1:
                return NEWS_HOT_LIST ;
            case 2:
                return  NEWS_ALBUM_PHOTO;
            case 3:
                return LEADERS_ROW ;
            case 4:
                return TITLELAB;
            case 5:
                return NEWS_HOT_TEXT;
            case 6:
                return NEWS_SUBJECT;
            case 7:
                return NEWS_THREE_PHOTO;
            case 8:
                return HOURSENEWS;
            case 9:
                return NEWS_SIMPLE_PHOTO;
            case 10:
                return BANNER_BIG;
            case 11:
                return NEWS_VIDEO;
             case 12:
                return SECOND_LANMU;
            case 13:
                return BANNER;
        }
        return super.getItemViewType(position);
    }

    private void setHotList(BaseViewHolder helper , ContentBeans contentBean){
            RecyclerView recyclerView = helper.getView(R.id.recyclerView);
            RecyclerView.Adapter adapter = recyclerView.getAdapter();
            if (adapter == null){
                new PagerSnapHelper().attachToRecyclerView(recyclerView);
                final MyHorizontalRefreshLayout refreshLayout = helper.getView(R.id.refreshLayout);
                refreshLayout.setRefreshHeader(new HotRefrechHead(UIUtils.getContext()), HorizontalRefreshLayout.RIGHT);
                refreshLayout.setRefreshCallback(new RefreshCallBack() {
                    @Override
                    public void onLeftRefreshing() { }
                    @Override
                    public void onRightRefreshing() {
                        refreshLayout.onRefreshComplete();
//                        ToastUtil.showToast("查看更多");
                    }
                });
                //
                final NewsHotHorizontalListAdapter hotAdapter = new NewsHotHorizontalListAdapter(null);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
                recyclerView.setAdapter(hotAdapter);
                refreshLayout.setLayoutManager(linearLayoutManager);
                recyclerView.setLayoutManager(linearLayoutManager);
                refreshLayout.setItemSize(4);
                hotAdapter.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                    }
                });
                helper.addOnClickListener(R.id.tv_more_hot);
            }

    }


    @Override
    public int getItemCount() {
        return getData().size();
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_hot_item:
                ToastUtil.showToast((String) view.getTag());
            break;
        }
    }
}
