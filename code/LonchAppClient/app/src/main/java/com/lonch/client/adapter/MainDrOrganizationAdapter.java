package com.lonch.client.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lonch.client.R;
import com.lonch.client.base.BaseAdapter;
import com.lonch.client.base.BaseViewHolder;
import com.lonch.client.bean.HumanOrganizationBean;

import java.util.List;


public class MainDrOrganizationAdapter extends BaseAdapter<MainDrOrganizationAdapter.ViewHolder, HumanOrganizationBean.ServiceResultBean.DataOwnerOrgListBean> {

    private OnOrganizationLibraryListener onMusicLibraryListener;
    private Context mContext;
    private int selectedPosition = 0;
    private String dataOrgId;

    public MainDrOrganizationAdapter(Context mContext, List<HumanOrganizationBean.ServiceResultBean.DataOwnerOrgListBean> mData) {
        super(mData);
        this.mContext = mContext;
    }
    public void organizationNotify(String dataOrgId,List<HumanOrganizationBean.ServiceResultBean.DataOwnerOrgListBean> mData ){
        this.mData = mData;
        this.dataOrgId = dataOrgId;
        notifyDataSetChanged();
    }
    public void setOnMusicLibraryListener(OnOrganizationLibraryListener onMusicLibraryListener) {
        this.onMusicLibraryListener = onMusicLibraryListener;
    }

    public void setLibrarySelectedPosition(int selpos) {
        this.selectedPosition = selpos;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = null;
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main_left_organization, parent, false);
        holder = new ViewHolder(rootView);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final HumanOrganizationBean.ServiceResultBean.DataOwnerOrgListBean leftBean = mData.get(position);
        if(dataOrgId.equals(leftBean.getCaid())){
            holder.tvText.setTextColor(mContext.getResources().getColor(R.color.colorAccent));
            holder.ivIcon.setVisibility(View.VISIBLE);
        }

        holder.tvText.setText(leftBean.getName());
        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onMusicLibraryListener.onOrSelected(leftBean);
            }
        });
    }


    public class ViewHolder extends BaseViewHolder {
        private LinearLayout rootView;
        private ImageView ivIcon;
        private TextView tvText;

        public ViewHolder(View itemView) {
            super(itemView);
//            rootView = itemView.findViewById(R.id.id_item_main_left_rootview_org);
//            ivIcon = itemView.findViewById(R.id.id_item_main_left_icon_oraganzition);
//            tvText = itemView.findViewById(R.id.id_item_main_left_name_oraganzition);
        }
    }

    public interface OnOrganizationLibraryListener {
        void onOrSelected(HumanOrganizationBean.ServiceResultBean.DataOwnerOrgListBean position);
        void onOrFocusSelected(int position);
    }

    @Override
    public int getItemCount() {
        return null == mData ? 0 : mData.size();
    }

}
