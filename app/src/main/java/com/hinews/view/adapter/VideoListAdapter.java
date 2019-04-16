package com.hinews.view.adapter;

import android.support.annotation.Nullable;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hinews.R;
import java.util.List;
import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;


public class VideoListAdapter extends BaseQuickAdapter<String,BaseViewHolder> {

    public VideoListAdapter(@Nullable List<String> data) {
        super(R.layout.item_video_list,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        JZVideoPlayerStandard videoPlayer = helper.getView(R.id.video_player);
        videoPlayer.setAllControlsVisiblity(VISIBLE, GONE, VISIBLE, GONE, VISIBLE, VISIBLE, GONE);
        videoPlayer.setState(JZVideoPlayer.CURRENT_STATE_NORMAL);
        videoPlayer.resetProgressAndTime();
        videoPlayer.setUp("http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4",
                JZVideoPlayer.SCREEN_WINDOW_LIST,"视频标题");
        videoPlayer.thumbImageView.setImageResource(R.drawable.bg_motherland);
    }
}
