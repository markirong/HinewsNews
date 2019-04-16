package com.hinews.view.adapter;
import android.support.annotation.Nullable;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hinews.R;
import java.util.List;

public class SearchAdapter extends BaseQuickAdapter<String,BaseViewHolder> {
    public SearchAdapter( @Nullable List<String> data) {
        super(R.layout.item_search_hot, data);
    }
    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_hot_name,item);
    }
}
