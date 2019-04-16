package com.hinews.view.activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.hinews.R;
import com.hinews.base.BaseActivity;
import com.hinews.bean.ChannelBean;
import com.hinews.bean.ChannelBean.ResultBean.SectionDataBean;
import com.hinews.channel.Channel;
import com.hinews.channel.ChannelView;
import com.hinews.utils.GsonUtil;
import com.hinews.utils.InfoUtil;
import com.hinews.utils.SharePreUtil;
import com.hinews.utils.StringUtils;
import java.util.ArrayList;
import java.util.List;
public class ChannelActivity extends BaseActivity implements ChannelView.OnChannelListener {
    private String TAG = getClass().getSimpleName();
    public static String navigation ="navigation";
    public static String recommend ="recommend"; public static String district ="district";
    private ChannelView channelView;
    List<Channel> myChannelList = new ArrayList<>();
    List<Channel> recommendChannelList = new ArrayList<>();
    List<Channel> districtChannelList = new ArrayList<>();
    public String ChannelListCache;
    List<SectionDataBean> myChannels ,recommendChannels ,districtChannels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel_view);
        setWhiteStatus();
        channelView = findViewById(R.id.channelView);
        //
        init();
    }
    private void init() {
        if(StringUtils.isEmpty(SharePreUtil.getString(this,ChannelActivity.navigation))){
            ChannelListCache = InfoUtil.originChannels;
            myChannels = GsonUtil.GsonToBean(ChannelListCache, ChannelBean.class).getResult().get(0).getSection_data();
        }else{
            myChannels = GsonUtil.jsonToList(SharePreUtil.getString(this,ChannelActivity.navigation),SectionDataBean.class);
        }
        if(StringUtils.isEmpty(SharePreUtil.getString(this,ChannelActivity.recommend))){
            ChannelListCache = InfoUtil.originChannels;
            recommendChannels = GsonUtil.GsonToBean(ChannelListCache, ChannelBean.class).getResult().get(1).getSection_data();
        }else{
            recommendChannels = GsonUtil.jsonToList(SharePreUtil.getString(this,ChannelActivity.recommend),SectionDataBean.class);
        }
        if(StringUtils.isEmpty(SharePreUtil.getString(this,ChannelActivity.district))){
            ChannelListCache = InfoUtil.originChannels;
            districtChannels = GsonUtil.GsonToBean(ChannelListCache, ChannelBean.class).getResult().get(2).getSection_data();
        }else{
            districtChannels = GsonUtil.jsonToList(SharePreUtil.getString(this,ChannelActivity.district),SectionDataBean.class);
        }

//        if(StringUtils.isEmpty(SharePreUtil.getString(this,ChannelActivity.recommend))){
//            recommendChannel1 = getResources().getStringArray(R.array.recommentChannel);
//            for (String aMyChannel : recommendChannel1) {
//                Channel channel = new Channel(aMyChannel);
//                recommendChannelList1.add(channel);
//            }
//        }else{
//            recommendChannelList1 = GsonUtil.jsonToList(SharePreUtil.getString(this,ChannelActivity.recommend),Channel.class);
//        }
//        if(StringUtils.isEmpty(SharePreUtil.getString(this,ChannelActivity.district))){
//            recommendChannel2 = getResources().getStringArray(R.array.districtChannel);
//            for (String aMyChannel : recommendChannel2) {
//                Channel channel = new Channel(aMyChannel);
//                recommendChannelList2.add(channel);
//            }
//        }else{
//            recommendChannelList2 = GsonUtil.jsonToList(SharePreUtil.getString(this,ChannelActivity.district),Channel.class);
//        }
//        ChannelBean channelBean = GsonUtil.GsonToBean(ChannelListCache , ChannelBean.class);
//        List<SectionDataBean> myChannels List<SectionDataBean> myChannels =channelBean.getResult().get(0).getSection_data();
//        List<SectionDataBean> recommendChannels =channelBean.getResult().get(1).getSection_data();
//        List<SectionDataBean> districtChannels =channelBean.getResult().get(2).getSection_data();

        for(int i =0 ; i <myChannels.size();i++){
            myChannelList.add(new Channel(myChannels.get(i).getChannelname(),
                    Integer.parseInt(myChannels.get(i).getChannelselected()),
                    myChannels.get(i).getListtype(),
                    myChannels.get(i).getUrl()));
        }
        for(int i =0 ; i <recommendChannels.size();i++){
            recommendChannelList.add(new Channel(recommendChannels.get(i).getChannelname(),
                    Integer.parseInt(recommendChannels.get(i).getChannelselected()),
                    recommendChannels.get(i).getListtype(),
                    recommendChannels.get(i).getUrl()));
        }
        for(int i =0 ; i <districtChannels.size();i++){
            districtChannelList.add(new Channel(districtChannels.get(i).getChannelname(),
                    Integer.parseInt(districtChannels.get(i).getChannelselected()),
                    districtChannels.get(i).getListtype(),
                    districtChannels.get(i).getUrl()));
        }


        channelView.setChannelFixedCount(2);
        channelView.addPlate("我的频道", myChannelList);
        channelView.addPlate("推荐频道", recommendChannelList);
        channelView.addPlate("区域频道", districtChannelList);
        channelView.inflateData();
        channelView.setChannelNormalBackground(R.drawable.bg_channel_normal);
        channelView.setChannelEditBackground(R.drawable.bg_channel_edit);
        channelView.setChannelFocusedBackground(R.drawable.bg_channel_focused);
        channelView.setOnChannelItemClickListener(this);
        channelView.setPlatesTitleBold(true);
        channelView.setSubTitleName("点击进入频道，拖动排序或移除");
        channelView.setOtherSubTitleName("单击移入'我的频道'");
        channelView.setChannelFixedTextColor(Color.RED);
        channelView.setChannelFocusedTextColor(Color.BLACK);
        channelView.setChannelNormalTextColor(Color.BLACK);
        channelView.setChannelTextSize(1,15);
    }
    @Override
    public void channelItemClick(int position, Channel channel) {
        Log.i(TAG, position + ".." + channel);
    }
    @Override
    public void channelEditFinish(List<Channel> channelList) {
        Log.i(TAG, "channelList"+channelList.toString());
        Log.i(TAG, "channelView.getMyChannel()"+channelView.getMyChannel().toString());
        Log.i(TAG, "channelView.getsubstitute1()"+channelView.getsubstitute1().toString());
        Log.i(TAG, "channelView.getsubstitute2()"+channelView.getsubstitute2().toString());



            SharePreUtil.putString(this,ChannelActivity.navigation, GsonUtil.GsonString(channelList));

            SharePreUtil.putString(this,ChannelActivity.recommend,  GsonUtil.GsonString(channelView.getsubstitute1()));

            SharePreUtil.putString(this,ChannelActivity.district,   GsonUtil.GsonString(channelView.getsubstitute2()));


    }
    @Override
    public void channelEditStart() { }
    public void aty_back(View view){ this.finish();}

}
