package com.lonch.client.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lonch.client.R;
import com.lonch.client.base.BaseAdapter;
import com.lonch.client.base.BaseViewHolder;
import com.lonch.client.bean.ToolBarBean;

import java.util.List;


public class MainDrawerAdapter extends BaseAdapter<MainDrawerAdapter.MyViewHolder, ToolBarBean.ServiceResultBean.SiderbarsBean> {

    private OnMusicLibraryListener onMusicLibraryListener;
    private Context mContext;
    private Resources resources;
    private int selectedPosition = 0;

    public MainDrawerAdapter(Context mContext, List<ToolBarBean.ServiceResultBean.SiderbarsBean> mData) {
        super(mData);
        this.mContext = mContext;
    }
    public void drawerNotify(List<ToolBarBean.ServiceResultBean.SiderbarsBean> mData ){
        this.mData = mData;
        notifyDataSetChanged();
    }

    public void setOnMusicLibraryListener(OnMusicLibraryListener onMusicLibraryListenerCur) {
        this.onMusicLibraryListener = onMusicLibraryListenerCur;
    }

    public void setLibrarySelectedPosition(int selpos) {
        this.selectedPosition = selpos;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = null;
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main_left_drawer, parent, false);
        holder = new MyViewHolder(rootView);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final ToolBarBean.ServiceResultBean.SiderbarsBean leftBean = mData.get(position);
        Glide.with(mContext).load(leftBean.getIcon())
//                .bitmapTransform(new BlurTransformation(this, 25), new CenterCrop(this))
                .into(holder.ivIcon);
        holder.tvText.setText(leftBean.getName());
        holder.rootView.setOnClickListener(v -> onMusicLibraryListener.onTabSelected(leftBean));
    }


    public class MyViewHolder extends BaseViewHolder {
        private LinearLayout rootView;
        private ImageView ivIcon;
        private TextView tvText;

        public MyViewHolder(View itemView) {
            super(itemView);
//            rootView = itemView.findViewById(R.id.id_item_main_left_rootview);
//            ivIcon = itemView.findViewById(R.id.id_item_main_left_icon);
//            tvText = itemView.findViewById(R.id.id_item_main_left_name);

        }
    }

    public interface OnMusicLibraryListener {
        void onTabSelected(ToolBarBean.ServiceResultBean.SiderbarsBean position);
        void onFocusSelected(int position);
    }

    @Override
    public int getItemCount() {
        return null == mData ? 0 : mData.size();
    }

}
