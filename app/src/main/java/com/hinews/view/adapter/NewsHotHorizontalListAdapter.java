package com.hinews.view.adapter;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.hinews.R;
import com.hinews.bean.NewsContentBean;
import com.hinews.utils.UIUtils;
import java.util.List;
import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;
public class NewsHotHorizontalListAdapter extends BaseMultiItemQuickAdapter<NewsContentBean.ContentBeans, BaseViewHolder>  {
    private int width;
    private  static  int VEDIO_TYPE = 100;
    private  static  int TEXT_TYPE = 200;
    public NewsHotHorizontalListAdapter(@Nullable List<NewsContentBean.ContentBeans> data) {
        super(data);
        width = UIUtils.getResource().getDisplayMetrics().widthPixels - UIUtils.dip2Px(35);
        addItemType(TEXT_TYPE, R.layout.item_news_hot_image);
        addItemType(VEDIO_TYPE,R.layout.item_news_hot_video);
    }

    @Override
    protected void convert(BaseViewHolder helper, NewsContentBean.ContentBeans item) {
        helper.getConvertView().getLayoutParams().width = width;
        if (helper.getItemViewType() == VEDIO_TYPE){
            JZVideoPlayerStandard videoPlayer = helper.getView(R.id.video_player);
            videoPlayer.setAllControlsVisiblity(GONE, GONE, VISIBLE, GONE, VISIBLE, VISIBLE, GONE);
            videoPlayer.setState(JZVideoPlayer.CURRENT_STATE_NORMAL);
            videoPlayer.resetProgressAndTime();
//            videoPlayer.setUp("http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4", JZVideoPlayer.SCREEN_WINDOW_LIST,"视频标题");
            videoPlayer.setUp(item.getVideo_src(), JZVideoPlayer.SCREEN_WINDOW_LIST,item.getTitle());
//            videoPlayer.thumbImageView.setImageResource(R.drawable.bg_motherland);
            Glide.with(UIUtils.getContext()).load(item.getImg().get(0)).crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.NONE).into(videoPlayer.thumbImageView);


        }else{
            TextView tv_loop_title =helper.getView(R.id.tv_loop_title);
            ImageView iv_loop_cover =helper.getView(R.id.iv_loop_cover);


            Glide.with(UIUtils.getContext()).load(item.getImg().get(0)).crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.NONE).into(iv_loop_cover);
            tv_loop_title.setText(item.getTitle());


        }
    }

    @Override
    public int getItemCount() {
        return getData().size();
    }
    @Override
    public int getItemViewType(int position) {
        if (position == 1){
            return VEDIO_TYPE;
        }else {
            return TEXT_TYPE;
        }

    }
}
