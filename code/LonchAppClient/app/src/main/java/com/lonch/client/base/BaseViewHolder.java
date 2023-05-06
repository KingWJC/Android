package com.lonch.client.base;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by guodong on 18-4-22.
 */

public class BaseViewHolder extends RecyclerView.ViewHolder {

    public View itemView;
    public View backGround;
    public View marqueeView;

    public BaseViewHolder(View itemView) {
        super(itemView);
//        ButterKnife.bind(this,itemView);
        this.itemView = itemView;
        this.backGround = getBackGroundFromSon();
        this.marqueeView = getMarqueeViewFromSon();
        this.itemView.setTag(this);
    }
    public void setItemView(View itemView) {
        this.itemView = itemView;
    }

    public View getItemView() {
        return itemView;
    }

    protected View getBackGroundFromSon(){
        return null;
    }

    protected View getMarqueeViewFromSon(){
        return null;
    }

}
