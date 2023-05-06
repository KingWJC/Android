package com.lonch.client.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lonch.client.R;
import com.lonch.client.base.BaseAdapter;
import com.lonch.client.base.BaseViewHolder;
import com.lonch.client.bean.ToolBarBean;
import com.lonch.client.database.bean.LogEntity;
import com.lonch.client.utils.LogUtils;
import com.lonch.client.utils.Utils;

import java.util.ArrayList;
import java.util.List;


public class MainTopRightAdapter extends BaseAdapter<MainTopRightAdapter.MyViewHolder, ToolBarBean.ServiceResultBean.FormsBean> {

    private OnListener onMusicLibraryListener;
    private Context mContext;
    private Resources resources;

    private int conutMsg;
    private boolean isAdd;

    public MainTopRightAdapter(Context context, List<ToolBarBean.ServiceResultBean.FormsBean> mData) {
        super(mData);
        mContext=context;
    }

    public void dataNotify(List<ToolBarBean.ServiceResultBean.FormsBean> mData ){
       this.mData = mData;
       notifyDataSetChanged();
    }
    public void notifyMsg(int count, boolean isAdd){
        this.conutMsg = count;
        this.isAdd = isAdd;
        notifyDataSetChanged();
    }



    public void setOnMusicLibraryListener(OnListener onMusicLibraryListenerCur) {
        this.onMusicLibraryListener = onMusicLibraryListenerCur;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = null;
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main_right_top, parent, false);
        holder = new MyViewHolder(rootView);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final ToolBarBean.ServiceResultBean.FormsBean leftBean = mData.get(position);
        holder.rootView.setOnClickListener(v -> {
            onMusicLibraryListener.onTopRightSelected(leftBean);
            List<String> points = new ArrayList<>();
            int [] location = new int[2];
            v.getLocationOnScreen(location);
            points.add(location[0]+","+location[1]);
            String res = Utils.setReportDataApp("click",leftBean.getBottombar().getName(),points);
            LogEntity logEntity = new LogEntity();
            logEntity.setTime(Long.parseLong(Utils.getDate(0)));
            logEntity.setArgs(res);
            logEntity.setFromType("2");
            logEntity.setOperation("click");
            LogUtils.getInstance().insert(logEntity);
        });
        if (mContext!=null && !((Activity)mContext).isFinishing()){
            Glide.with(mContext).load(leftBean.getBottombar().getIcon())
                    .into(holder.icon);
        }
        String name = leftBean.getBottombar().getName();

        if (!TextUtils.isEmpty(name) && name.contains("聊天")){
            if(isAdd){//累计增加
                holder.unReadCount.setVisibility(View.VISIBLE);
                holder.unReadCount.setText(conutMsg+"");
            }else {//第一次获取
                if(conutMsg == 0){
                    holder.unReadCount.setVisibility(View.GONE);
                }else {
                    holder.unReadCount.setVisibility(View.VISIBLE);
                    holder.unReadCount.setText(conutMsg+"");
                }
            }
        }
    }


    public class MyViewHolder extends BaseViewHolder {
        private RelativeLayout rootView;
        private TextView unReadCount;
        private ImageView icon;

        public MyViewHolder(View itemView) {
            super(itemView);
            rootView = itemView.findViewById(R.id.id_main_title_right_news);
            unReadCount = itemView.findViewById(R.id.id_main_title_right_unread_news);
            //消息未读
            icon = itemView.findViewById(R.id.id_main_title_right_icon);
        }
    }

    public interface OnListener {
        void onTopRightSelected(ToolBarBean.ServiceResultBean.FormsBean position);
    }

    @Override
    public int getItemCount() {
        return null == mData ? 0 : mData.size();
    }

}
