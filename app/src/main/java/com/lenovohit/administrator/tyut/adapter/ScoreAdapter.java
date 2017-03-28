package com.lenovohit.administrator.tyut.adapter;

import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lenovohit.administrator.tyut.R;
import com.lenovohit.administrator.tyut.domain.MySection;
import com.lenovohit.administrator.tyut.greendao.Score;

import java.util.List;

/**
 * Created by Administrator on 2017/3/16.
 */

public class ScoreAdapter extends BaseSectionQuickAdapter<MySection,BaseViewHolder>{


    private Score t;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param layoutResId      The layout resource id of each item.
     * @param sectionHeadResId The section head layout id for each item
     * @param data             A new list is created out of this one to avoid mutable list
     */

    public ScoreAdapter(int layoutResId, int sectionHeadResId, List<MySection> data) {
        super(layoutResId, sectionHeadResId, data);
    }

    //父节点
    @Override
    protected void convertHead(BaseViewHolder helper, MySection item) {
        helper.setText(R.id.head,item.header);
    }
    //子节点
    @Override
    protected void convert(BaseViewHolder helper, MySection item) {
        t = item.t;
        helper.setText(R.id.tvName,t.getTitle());
        helper.setText(R.id.tvXuefen,t.getXuefen());
        helper.setText(R.id.tvChengji,t.getChengji());
    }
}
