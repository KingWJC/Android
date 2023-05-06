package com.lonch.client.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.lonch.client.LonchCloudApplication;
import com.lonch.client.R;
import com.lonch.client.base.BaseAdapter;
import com.lonch.client.base.BaseViewHolder;
import com.lonch.client.bean.VideoBean;
import com.lonch.client.database.bean.SmallVideoEntity;
import com.lonch.client.database.tabutils.SmallVideoUtils;
import com.lonch.client.view.SmallVideoView;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack;
import com.tencent.mm.opensdk.modelbiz.WXLaunchMiniProgram;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.List;

public class SmallVideoAdapter extends BaseAdapter<SmallVideoAdapter.MyViewHolder, VideoBean.SmallVideoBean> {
    private Context mContext;
    private OnVideoAutoCompleteListener myClickListener;
    private long startTime;
    private boolean isAuto = false;
    private boolean isSeek = false;


    public SmallVideoAdapter(Context context, List mData) {
        super(mData);
        this.mContext = context;
    }

    public void setListData(List<VideoBean.SmallVideoBean> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    public void setInsertedListData(List<VideoBean.SmallVideoBean> data) {
        this.mData = data;
        notifyItemInserted(this.mData != null ? this.mData.size() : 0);
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MyViewHolder holder = null;
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video, parent, false);
        holder = new MyViewHolder(rootView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        VideoBean.SmallVideoBean smallVideoBean = mData.get(position);
        holder.tvTitle.setText(smallVideoBean.getVideoName());
        holder.tvContent.setText(smallVideoBean.getVideoDescription());
        holder.ivIcon.setImageResource(R.mipmap.ic_launcher);
        holder.smallVideoView.setUp(smallVideoBean.getUrl(), false, "");
        holder.smallVideoView.setVideoAllCallBack(new GSYSampleCallBack() {
            @Override
            public void onAutoComplete(String url, Object... objects) {
                super.onAutoComplete(url, objects);
                //视频正常播放完成 自动进入下一个
                isAuto = true;
                if (myClickListener != null) {
                    myClickListener.onComplete(position);
                }
            }

            @Override
            public void onPrepared(String url, Object... objects) {
                super.onPrepared(url, objects);
                //视频播放开始 插入数据
                startTime = System.currentTimeMillis();
            }

            @Override
            public void onClickSeekbar(String url, Object... objects) {
                super.onClickSeekbar(url, objects);
                isSeek = true;
            }

            @Override
            public void onComplete(String url, Object... objects) {
                super.onComplete(url, objects);
                // 视频非正常播放完成 用户手动滑走
                String videoId = smallVideoBean.getVideoId();
                SmallVideoEntity smallVideoEntity = new SmallVideoEntity();
                smallVideoEntity.setVideoId(videoId);
                smallVideoEntity.setStartTime(startTime);
                smallVideoEntity.setEndTime(System.currentTimeMillis());
                if (isAuto) {
                    smallVideoEntity.setIsLeaveActive(0);
                } else {
                    smallVideoEntity.setIsLeaveActive(1);
                }
                if (isSeek) {
                    smallVideoEntity.setIsForward(1);
                } else {
                    smallVideoEntity.setIsForward(0);
                }
                smallVideoEntity.setLeaveVideoTime(GSYVideoManager.instance().getCurrentPosition());
                SmallVideoUtils.getInstance().insert(smallVideoEntity);
                isAuto = false;
                isSeek = false;

            }
        });
        holder.img_shopping_cart.setVisibility(smallVideoBean.isNeedGoMiniProgram() ? View.VISIBLE : View.GONE);
        holder.img_shopping_cart.setOnClickListener(v -> {
            jumpToWXProgram(smallVideoBean.getAppletsId(), smallVideoBean.getUrlPath());
        });
    }

    public void setVideoAutoCompleteListener(OnVideoAutoCompleteListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public interface OnVideoAutoCompleteListener {
        void onComplete(int position);
    }


    public static class MyViewHolder extends BaseViewHolder {
        private ImageView ivIcon;
        private TextView tvTitle;
        private TextView tvContent;
        private SmallVideoView smallVideoView;
        private ImageView img_shopping_cart;

        public MyViewHolder(View itemView) {
            super(itemView);
            smallVideoView = itemView.findViewById(R.id.gsyVideo);
            ivIcon = itemView.findViewById(R.id.img_icon);
            tvContent = itemView.findViewById(R.id.tv_content);
            tvTitle = itemView.findViewById(R.id.tv_app_name);
            img_shopping_cart = itemView.findViewById(R.id.img_shopping_cart);
        }
    }


    /**
     * 跳转到微信小程序
     */
    private void jumpToWXProgram(String userName, String path) {
        IWXAPI api = WXAPIFactory.createWXAPI(LonchCloudApplication.getApplicationsContext(), LonchCloudApplication.getAppConfigDataBean().WECHAT_ID);
        WXLaunchMiniProgram.Req req = new WXLaunchMiniProgram.Req();
        req.userName = userName; // 填小程序原始id
        req.path = path;
        if (LonchCloudApplication.getAppConfigDataBean().SERVICE_URL.contains("test")) {
            req.miniprogramType = WXLaunchMiniProgram.Req.MINIPROGRAM_TYPE_PREVIEW;// 可选打开 开发版，体验版和正式版
        } else {
            req.miniprogramType = WXLaunchMiniProgram.Req.MINIPTOGRAM_TYPE_RELEASE;// 可选打开 开发版，体验版和正式版
        }
        api.sendReq(req);
    }
}
