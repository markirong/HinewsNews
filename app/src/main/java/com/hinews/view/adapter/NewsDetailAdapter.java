package com.hinews.view.adapter;
import android.support.annotation.Nullable;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hinews.R;
import java.util.List;

public class NewsDetailAdapter extends BaseQuickAdapter<String,BaseViewHolder> {
    public NewsDetailAdapter(@Nullable List<String> data) {
        super(R.layout.item_news_simple_photo, data);
    }
    @Override
    protected void convert(BaseViewHolder helper, String item) {

    }
}
