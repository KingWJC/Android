package com.lonch.client.base;


import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public abstract class BaseAdapter<VH extends BaseViewHolder, T extends Object> extends RecyclerView.Adapter<VH> {

    protected List<T> mData;

    public BaseAdapter(List mData) {
        this.mData = mData;
        this.setHasStableIds(true);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return null == mData ? 0 : mData.size();
    }

}
