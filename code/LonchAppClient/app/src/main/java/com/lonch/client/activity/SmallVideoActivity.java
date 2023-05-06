package com.lonch.client.activity;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.alibaba.fastjson.JSON;
import com.lonch.client.LonchCloudApplication;
import com.lonch.client.R;
import com.lonch.client.adapter.SmallVideoAdapter;
import com.lonch.client.bean.VideoBean;
import com.lonch.client.database.bean.LogEntity;
import com.lonch.client.interfaces.ImLoginContract;
import com.lonch.client.model.IMLoginModel;
import com.lonch.client.utils.LogUtils;
import com.lonch.client.utils.SpUtils;
import com.lonch.client.utils.ToastUtils;
import com.lonch.client.utils.Utils;
import com.lonch.client.view.SmallVideoView;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.utils.Debuger;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SmallVideoActivity extends AppCompatActivity implements ImLoginContract.VideoListView {
    private ViewPager2 viewPager2;
    private List<VideoBean.SmallVideoBean> list = new ArrayList<>();
    private SmallVideoAdapter smallVideoAdapter;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView recyclerView;
    private int mPosition = 0;
    private IMLoginModel imLoginModel;
    private boolean isLoadFinish = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_small_video);
        ImageView close = findViewById(R.id.close);
        close.setOnClickListener(v ->finish());
        viewPager2 = findViewById(R.id.view_pager);
        imLoginModel = new IMLoginModel(this);
        init();
        getVideoData("");

        String res = Utils.setReportDataApp("watchShortVideo",(String) SpUtils.get("caId", ""), new ArrayList<>());
        LogEntity logEntity = new LogEntity();
        logEntity.setFromType("2");
        logEntity.setTime(Long.parseLong(Utils.getDate(0)));
        logEntity.setArgs(res);
        LogUtils.getInstance().insert(logEntity);
        Debuger.enable();
    }
    private void init(){
        smallVideoAdapter = new SmallVideoAdapter(this,list);
        viewPager2.setOrientation(ViewPager2.ORIENTATION_VERTICAL);
        viewPager2.setAdapter(smallVideoAdapter);
        recyclerView = (RecyclerView) viewPager2.getChildAt(0);
        linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                mPosition = position;
                startVideo();
                if (!isLoadFinish){
                    try {
                        if (position == list.size()-1){
                            VideoBean.SmallVideoBean smallVideoBean = list.get(position);
                            getVideoData(JSON.toJSONString(smallVideoBean));
                        }
                    }catch (Exception ignored){}
                }
                if (isLoadFinish){
                    if (position == list.size() -1){
                        list.addAll(list);
                        smallVideoAdapter.setInsertedListData(list);
                    }
                }
            }

            /**
             * 代码翻页，state 是2、0（ViewPager.SCROLL_STATE_SETTLING、ViewPager.SCROLL_STATE_IDLE）
             * 手动滑动翻页，state 是1、2、0（ViewPager.SCROLL_STATE_DRAGGING、ViewPager.SCROLL_STATE_SETTLING、ViewPager.SCROLL_STATE_IDLE）
             * @param state
             */
            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
                switch (state) {
                    case ViewPager2.SCROLL_STATE_IDLE:
                        
                        break;
                    case ViewPager2.SCROLL_STATE_DRAGGING: //
                       
                        break;
                    case ViewPager2.SCROLL_STATE_SETTLING:
                        break;
                    default:
                        break;
                }
            }
        });
        smallVideoAdapter.setVideoAutoCompleteListener(position -> {
            int index = position +1 ;
            if (index <= list.size()-1){
                viewPager2.setCurrentItem(index,true);
            }

        });
    }
    private void getVideoData(String content){
        imLoginModel.getVideList((String) SpUtils.get("token", ""),content);
    }
    private void startVideo(){
        GSYVideoManager.releaseAllVideos();
        LonchCloudApplication.handler.postDelayed(() -> {
            SmallVideoView smallVideoView  = Objects.requireNonNull(linearLayoutManager.findViewByPosition(mPosition)).findViewById(R.id.gsyVideo);
            smallVideoView.startPlayLogic();
            smallVideoView.requestFocus();
        },200);
    }

    @Override
    protected void onPause() {
        super.onPause();
        GSYVideoManager.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        GSYVideoManager.releaseAllVideos();
    }

    @Override
    protected void onResume() {
        super.onResume();
        GSYVideoManager.onResume(false);
    }

    @Override
    public void onVideoListSuccess(VideoBean videoBean) {
        if (videoBean == null){
            ToastUtils.showLongText(getString(R.string.video_list_error));
            return;
        }
        List<VideoBean.SmallVideoBean> serviceResultBeanList = videoBean.getServiceResult();
        if (serviceResultBeanList!=null && serviceResultBeanList.size() >0){
            list.addAll(serviceResultBeanList);
            smallVideoAdapter.setInsertedListData(list);
        }else{
            isLoadFinish = true;
            list.addAll(list);
            smallVideoAdapter.setInsertedListData(list);
        }
    }

    @Override
    public void onVideoListError() {
        ToastUtils.showLongText(getString(R.string.video_list_error));
    }
}