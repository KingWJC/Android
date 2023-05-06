package com.lonch.client.view;


import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import moe.codeest.enviews.ENDownloadView;

public class SmallVideoView extends StandardGSYVideoPlayer {
    private final String TAG = "SmallVideoView";
    public SmallVideoView(Context context, Boolean fullFlag) {
        super(context, fullFlag);
        init();
    }

    public SmallVideoView(Context context) {
        super(context);
        init();
    }

    public SmallVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mNeedShowWifiTip = false;
        getBackButton().setVisibility(View.GONE);
        getFullscreenButton().setVisibility(View.GONE);
        mBottomContainer.setVisibility(View.VISIBLE);
        mCurrentTimeTextView.setVisibility(View.VISIBLE);
        mTotalTimeTextView.setVisibility(View.VISIBLE);
        mBottomProgressBar.setVisibility(View.INVISIBLE);
        mProgressBar.setVisibility(View.VISIBLE);
        mLoadingProgressBar.setVisibility(View.GONE);
    }

    public String getUrl() {
        return mOriginUrl;
    }

    float x1, y1, x2, y2;
    long donwTime;

    @Override
    protected void resolveUIState(int state) {
        Log.i(TAG, "resolveUIState = " + state);
        super.resolveUIState(state);
    }

    @Override
    protected void touchSurfaceDown(float x, float y) {
//        super.touchSurfaceDown(x, y);
        x1 = x;
        x2 = x;
        y1 = y;
        y2 = y;
        donwTime = System.currentTimeMillis();
    }
    @Override
    protected void touchSurfaceMove(float deltaX, float deltaY, float y) {
//        super.touchSurfaceMove(deltaX, deltaY, y);
        x2 = deltaX;
        y2 = deltaY;
    }
    @Override
    protected void touchSurfaceUp() {
        Log.i(TAG, "touchSurfaceUp");
//        super.touchSurfaceUp();
        if (System.currentTimeMillis() - donwTime < 500 && Math.abs(x2 - x1) < 20 && Math.abs(y2 - y1) < 20) {
            clickStartIcon();
        }

    }

    @Override
    public void onClick(View v) {
//        super.onClick(v);
//        if (v.getId() == R.id.start) {
//            clickStartIcon();
//        }
    }

    @Override
    protected void startDismissControlViewTimer() {
           //super.startDismissControlViewTimer();
        Log.i(TAG, "startDismissControlViewTimer = ");
    }

    @Override
    protected void cancelDismissControlViewTimer() {
        //super.cancelDismissControlViewTimer();
        Log.i(TAG, "cancelDismissControlViewTimer = ");
    }

    @Override
    protected void touchDoubleUp(MotionEvent e) {
//        super.touchDoubleUp(e);
    }

    @Override
    protected void showProgressDialog(float deltaX, String seekTime, int seekTimePosition, String totalTime, int totalTimeDuration) {
//        super.showProgressDialog(deltaX, seekTime, seekTimePosition, totalTime, totalTimeDuration);
    }

    @Override
    protected void dismissProgressDialog() {
//        super.dismissProgressDialog();
    }

    @Override
    protected void changeUiToNormal() {
        Log.i(TAG, "changeUiToNormal");
        setViewShowState(mTopContainer, INVISIBLE);
        setViewShowState(mBottomContainer, VISIBLE);
        setViewShowState(mStartButton, INVISIBLE);
        setViewShowState(mLoadingProgressBar, INVISIBLE);
        setViewShowState(mThumbImageViewLayout, VISIBLE);
        setViewShowState(mBottomProgressBar, INVISIBLE);
        setViewShowState(mLockScreen, (mIfCurrentIsFullscreen && mNeedLockFull) ? VISIBLE : GONE);
        updateStartImage();
        if (mLoadingProgressBar instanceof ENDownloadView) {
            ((ENDownloadView) mLoadingProgressBar).reset();
        }
    }

    @Override
    protected void changeUiToPlayingShow() {
        Log.i(TAG, "changeUiToPlayingShow");

        setViewShowState(mTopContainer, INVISIBLE);
        setViewShowState(mBottomContainer, VISIBLE);
        setViewShowState(mStartButton, INVISIBLE);
        setViewShowState(mLoadingProgressBar, INVISIBLE);
//        setViewShowState(mThumbImageViewLayout, VISIBLE);
        setViewShowState(mBottomProgressBar, INVISIBLE);
        setViewShowState(mLockScreen, (mIfCurrentIsFullscreen && mNeedLockFull) ? VISIBLE : GONE);

        if (mLoadingProgressBar instanceof ENDownloadView) {
            ((ENDownloadView) mLoadingProgressBar).reset();
        }
        updateStartImage();
        mThumbImageViewLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                mThumbImageViewLayout.setVisibility(View.GONE);
            }
        },200);
    }

    @Override
    protected void changeUiToPreparingShow() {
        Log.i(TAG, "changeUiToPreparingShow");

        setViewShowState(mTopContainer, INVISIBLE);
        setViewShowState(mBottomContainer, VISIBLE);
        setViewShowState(mStartButton, INVISIBLE);
        setViewShowState(mLoadingProgressBar, INVISIBLE);
        setViewShowState(mThumbImageViewLayout, VISIBLE);
        setViewShowState(mBottomProgressBar, INVISIBLE);
        setViewShowState(mLockScreen, GONE);

        if (mLoadingProgressBar instanceof ENDownloadView) {
            ENDownloadView enDownloadView = (ENDownloadView) mLoadingProgressBar;
            if (enDownloadView.getCurrentState() == ENDownloadView.STATE_PRE) {
                ((ENDownloadView) mLoadingProgressBar).start();
            }
        }
    }

    @Override
    protected void changeUiToPauseShow() {
        Log.i(TAG, "changeUiToPauseShow");
        setViewShowState(mTopContainer, INVISIBLE);
        setViewShowState(mBottomContainer, VISIBLE);
        setViewShowState(mStartButton, VISIBLE);
        setViewShowState(mLoadingProgressBar, INVISIBLE);
        setViewShowState(mThumbImageViewLayout, INVISIBLE);
        setViewShowState(mBottomProgressBar, INVISIBLE);
        setViewShowState(mLockScreen, (mIfCurrentIsFullscreen && mNeedLockFull) ? VISIBLE : GONE);

        if (mLoadingProgressBar instanceof ENDownloadView) {
            ((ENDownloadView) mLoadingProgressBar).reset();
        }
        updateStartImage();
        updatePauseCover();
    }

    @Override
    protected void changeUiToPauseClear() {
        Log.i(TAG, "changeUiToPauseClear");
    }

    @Override
    protected void changeUiToClear() {
        Log.i(TAG, "changeUiToClear");
    }

    @Override
    protected void hideAllWidget() {
        Log.i(TAG, "hideAllWidget");
        setViewShowState(mBottomContainer, INVISIBLE);
        setViewShowState(mTopContainer, INVISIBLE);
        setViewShowState(mBottomProgressBar, INVISIBLE);
        setViewShowState(mStartButton, INVISIBLE);
    }

}
