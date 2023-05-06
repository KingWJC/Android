package com.lonch.client.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.view.ViewCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.lonch.client.LonchCloudApplication;
import com.lonch.client.R;
import com.lonch.client.adapter.MainDrOrganizationAdapter;
import com.lonch.client.adapter.MainDrawerAdapter;
import com.lonch.client.adapter.MainTopRightAdapter;
import com.lonch.client.base.BaseWebActivity;
import com.lonch.client.bean.AppClientUpdateBean;
import com.lonch.client.bean.AppZipBean;
import com.lonch.client.bean.HumanOrganizationBean;
import com.lonch.client.bean.ImLoginBean;
import com.lonch.client.bean.PlistPackageBean;
import com.lonch.client.bean.RefreshOrgBen;
import com.lonch.client.bean.SaveClientDevicesBean;
import com.lonch.client.bean.ToolBarBean;
import com.lonch.client.bean.ToolBarBeanMy;
import com.lonch.client.bean.UpdateInfoVBean;
import com.lonch.client.bean.WebJsFunction;
import com.lonch.client.bean.YfcUserBean;
import com.lonch.client.database.bean.LogEntity;
import com.lonch.client.interfaces.HtmlContract;
import com.lonch.client.interfaces.ImLoginContract;
import com.lonch.client.interfaces.JsDataContract;
import com.lonch.client.interfaces.OrgDataContract;
import com.lonch.client.model.HtmlZipModel;
import com.lonch.client.model.IMLoginModel;
import com.lonch.client.model.JsDataModel;
import com.lonch.client.utils.DensityUtils;
import com.lonch.client.utils.LogUtils;
import com.lonch.client.utils.OkHttpUtil;
import com.lonch.client.utils.SpUtils;
import com.lonch.client.utils.Utils;
import com.lonch.client.view.BridgeFragment;
import com.lonch.client.view.WebFragment;
import com.tencent.mmkv.MMKV;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseWebActivity implements RadioGroup.OnCheckedChangeListener,
        MainDrawerAdapter.OnMusicLibraryListener, HtmlContract.HtmlCodeView,
        HtmlContract.UpdateJsonInfoCodeView, HtmlContract.QueryToolBarMenusInfoCodeView,
        ImLoginContract.ImLoginView, MainDrOrganizationAdapter.OnOrganizationLibraryListener,
        HtmlContract.OrganizationInfoCodeView, OrgDataContract.RefreshDataView,
        HtmlContract.YaoFaCaiInfoView, HtmlContract.AppClientUpdate, MainTopRightAdapter.OnListener,
        ImLoginContract.SaveClientDevicesView, JsDataContract.JsGetDataView,
        WebJsFunction.CallbackJsFun {

    private static final String TAG = "MainActivity";

    private RecyclerView mainTopRight;
    public DrawerLayout mDrawerlayout;
    private RelativeLayout id_main_title_rl;
    private String token;
    private TextView iconText;
    private RelativeLayout id_main_title_left;
    private RadioGroup radiogroup;

    private List<ToolBarBean.ServiceResultBean.FormsBean> rightBtnList;
    private ArrayList<ToolBarBean.ServiceResultBean.FormsBean> centerList = new ArrayList<>();
    private List<ToolBarBean.ServiceResultBean.SiderbarsBean> siderbarsBeanArrayList = new ArrayList<>();
    private final List<ToolBarBeanMy> fragmentList = new ArrayList<>();

    private int fragmentTag;
    private String toolBarVersion = "";
    private int uIcheckBtn = 0;
    private MainTopRightAdapter mainTopRightAdapter;
    private MMKV mmkv;
    private HtmlZipModel htmlZipModel;
    private IMLoginModel imLoginModel;
    private JsDataModel jsDataModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.MainTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mmkv = MMKV.defaultMMKV();
        initView();

        token = (String) SpUtils.get("token", "");
        htmlZipModel = new HtmlZipModel(this, this, this, this, this, this);
//        imLoginModel = new IMLoginModel(this, this);
        jsDataModel = new JsDataModel(this, token);

        htmlZipModel.queryToolBar(token);
        OkHttpUtil.getInstance().getAcceleratedLink(token);

//        EventBus.getDefault().register(this);
    }

    private void initView() {
        myWebView.addJavascriptInterface(new WebJsFunction(this, myWebView, this), "LonchJsApi");

        //主页
        radiogroup = findViewById(R.id.tabs_rg);
//        mDrawerlayout = findViewById(R.id.id_drawer_layout);
        iconText = findViewById(R.id.id_main_title_icon_text);
        id_main_title_left = findViewById(R.id.id_main_title_left);
        //设置个人信息
        String userInfo = (String) SpUtils.get("userInfo", "");
        if (!TextUtils.isEmpty(userInfo)) {
            iconText.setText(userInfo.substring(0, 1));
        }

        rightBtnList = new ArrayList<>();//右边
        //右栏
        mainTopRight = findViewById(R.id.id_main_top_right_rl);
        mainTopRight.setVisibility(View.INVISIBLE);
        mainTopRightAdapter = new MainTopRightAdapter(this, rightBtnList);
        mainTopRightAdapter.setOnMusicLibraryListener(this);
        LinearLayoutManager linearLayoutManagerR = new LinearLayoutManager(this);
        linearLayoutManagerR.setOrientation(LinearLayoutManager.HORIZONTAL);
        mainTopRight.setLayoutManager(linearLayoutManagerR);
        mainTopRight.setAdapter(mainTopRightAdapter);
        //toolbar
        id_main_title_rl = findViewById(R.id.id_main_title_rl);
        String toolBarString = mmkv.decodeString("toolBarData");
        if (!TextUtils.isEmpty(toolBarString)) {
            ToolBarBean toolBarBean = new Gson().fromJson(toolBarString, ToolBarBean.class);
            if (null == toolBarBean) {
                return;
            }
            ToolBarBean.ServiceResultBean serviceResultBean = toolBarBean.getServiceResult();
            if (null == serviceResultBean) {
                return;
            }
            toolBarVersion = serviceResultBean.getVersion();
            boolean isShowHeaderPortrait = serviceResultBean.isShowHeaderPortrait();
            if (!isShowHeaderPortrait) {
                id_main_title_left.setVisibility(View.INVISIBLE);
            } else {
                id_main_title_left.setVisibility(View.VISIBLE);
            }
            setToolBarData(toolBarBean);
        }
    }

    private void setToolBarData(ToolBarBean bean) {
        if (null != bean) {
            List<ToolBarBean.ServiceResultBean.FormsBean> forms1 = bean.getServiceResult().getForms();//全部
            List<ToolBarBean.ServiceResultBean.FormsBean> forms = new ArrayList<>();
            ToolBarBean.ServiceResultBean.FormsBean item1 = new ToolBarBean.ServiceResultBean.FormsBean();
            ToolBarBean.ServiceResultBean.FormsBean.BottombarBean barBean1 = new ToolBarBean.ServiceResultBean.FormsBean.BottombarBean();
            barBean1.setAlign("2");
            barBean1.setIcon("https://resources.lonch.com.cn/bi-test/control-selling/2021/03/16/gaoMaoKongXiao-unchecked20210316161326.png");
            barBean1.setName("桥接协议");
            barBean1.setType("1");
            barBean1.setWeb_app_id("d9ef715c0ce143909fd619aee0778354");
            barBean1.setUrl_path("http://10.2.2.8080/#/bridge");
            item1.setBottombar(barBean1);
            item1.setTitle("桥接协议");
            forms.add(item1);

            ToolBarBean.ServiceResultBean.FormsBean item2 = new ToolBarBean.ServiceResultBean.FormsBean();
            ToolBarBean.ServiceResultBean.FormsBean.BottombarBean barBean2 = new ToolBarBean.ServiceResultBean.FormsBean.BottombarBean();
            barBean2.setAlign("2");
            barBean2.setName("腾讯直播");
            barBean2.setType("2");
            item2.setBottombar(barBean2);
            item2.setTitle("腾讯直播");
            forms.add(item2);

            ToolBarBean.ServiceResultBean.FormsBean item3 = new ToolBarBean.ServiceResultBean.FormsBean();
            ToolBarBean.ServiceResultBean.FormsBean.BottombarBean barBean3 = new ToolBarBean.ServiceResultBean.FormsBean.BottombarBean();
            barBean3.setAlign("2");
            barBean3.setName("IM即时通讯");
            barBean3.setType("3");
            item3.setBottombar(barBean3);
            item3.setTitle("IM即时通讯");
            forms.add(item3);

            ToolBarBean.ServiceResultBean.FormsBean item4 = new ToolBarBean.ServiceResultBean.FormsBean();
            ToolBarBean.ServiceResultBean.FormsBean.BottombarBean barBean4 = new ToolBarBean.ServiceResultBean.FormsBean.BottombarBean();
            barBean4.setAlign("2");
            barBean4.setName("短视频直播");
            barBean4.setType("4");
            item4.setBottombar(barBean4);
            item4.setTitle("短视频直播");
            forms.add(item4);

            ToolBarBean.ServiceResultBean.FormsBean item5 = new ToolBarBean.ServiceResultBean.FormsBean();
            ToolBarBean.ServiceResultBean.FormsBean.BottombarBean barBean5 = new ToolBarBean.ServiceResultBean.FormsBean.BottombarBean();
            barBean5.setAlign("2");
            barBean5.setName("更新系统");
            barBean5.setType("5");
            item5.setBottombar(barBean5);
            item5.setTitle("更新系统");
            forms.add(item5);

            centerList.clear();
            List<ToolBarBean.ServiceResultBean.FormsBean> leftBtnList = new ArrayList<>();//左边
            rightBtnList.clear();//右边
            if (forms == null) {
                return;
            }
            for (int i = 0; i < forms.size(); i++) {
                ToolBarBean.ServiceResultBean.FormsBean formsBean = forms.get(i);
                String align = formsBean.getBottombar().getAlign();
                if (TextUtils.isEmpty(align)) {
                    return;
                }
                switch (align) {
                    case "1"://左边
                        leftBtnList.add(formsBean);
                        break;
                    case "2"://中间
                        centerList.add(formsBean);
                        break;
                    case "3"://右边
                        rightBtnList.add(formsBean);
                        break;
                }
            }
            //---------中部------
            addGroupview(radiogroup, centerList);//(居中GroupButton)只有顶栏 不区分
        }
    }

    //动态添加视图
    public void addGroupview(RadioGroup radiogroup, List<ToolBarBean.ServiceResultBean.FormsBean> list) {
        int index = 0;
        radiogroup.removeAllViews();
        for (ToolBarBean.ServiceResultBean.FormsBean formsBean : list) {
            ToolBarBean.ServiceResultBean.FormsBean.BottombarBean bean = formsBean.getBottombar();

            RadioButton button = new RadioButton(this);
            setRaidBtnAttribute(button, bean.getName(), bean, index);
            radiogroup.addView(button);

            //外部布局
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) button
                    .getLayoutParams();
            layoutParams.setMargins(DensityUtils.dp2px(this, 0), 0, -10, 0);//4个参数按顺序分别是左上右下
            button.setLayoutParams(layoutParams);
            index++;
        }
        radiogroup.setOnCheckedChangeListener(this);
    }

    @SuppressLint("ResourceType")
    private void setRaidBtnAttribute(final RadioButton codeBtn, String btnContent, ToolBarBean.ServiceResultBean.FormsBean.BottombarBean serviceResultBean, int index) {
        if (null == codeBtn) {
            return;
        }
        //按钮背景文字
        codeBtn.setBackgroundResource(R.drawable.radio_group_selector);
        codeBtn.setTextColor(this.getResources().getColorStateList(LonchCloudApplication.getAppConfigDataBean().color_radiobutton));
        codeBtn.setTextSize(15);
        codeBtn.setButtonDrawable(new StateListDrawable()); //设置为空
        codeBtn.setId(index);
        // TODO: 2020/12/28  默认选中哪一个 ；云服务
        if (index == 0) {
            ViewCompat.animate(codeBtn)
                    .setDuration(200)
                    .scaleX(1.2f)
                    .scaleY(1.2f)
                    .start();
            uIcheckBtn = 0;
            codeBtn.setChecked(true);
        }
        codeBtn.setText(btnContent);
        codeBtn.setPadding(25, 5, 25, 5);
        codeBtn.setGravity(Gravity.CENTER);
        Drawable drawable_news = getResources().getDrawable(R.drawable.sel_face_main);
        //当这个图片被绘制时，给他绑定一个矩形 ltrb规定这个矩形
        drawable_news.setBounds(0, 0, DensityUtils.dp2px(this, 50), 8);
        //设置图片在文字的哪个方向
        codeBtn.setCompoundDrawables(null, null, null, drawable_news);
        LinearLayout.LayoutParams rlp = new LinearLayout.LayoutParams(new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT,
                RadioGroup.LayoutParams.MATCH_PARENT, 1f));
        codeBtn.setLayoutParams(rlp);
    }

    @Override
    public void webCallAppV2(String msg) {

    }

    @Override
    public void webCallApp(String msg) {

    }

    @Override
    public void webCallSn(String sn) {

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (fragmentList.size() == 0) {
            return;
        }

        ViewCompat.animate(group.getChildAt(checkedId))
                .setDuration(200)
                .scaleX(1.2f)
                .scaleY(1.2f)
                .start();
        ViewCompat.animate(group.getChildAt(uIcheckBtn))
                .setDuration(200)
                .scaleX(1f)
                .scaleY(1f)
                .start();
        uIcheckBtn = checkedId;
        ToolBarBeanMy serviceResultBean = fragmentList.get(checkedId);//当前点击对象
        Fragment curFragment = fragmentList.get(fragmentTag).getFragment();
        replaceFragment(curFragment, serviceResultBean.getFragment(), checkedId);
        List<String> points = new ArrayList<>();
        int[] location = new int[2];
        group.getLocationOnScreen(location);
        points.add(location[0] + "," + location[1]);
        String res = Utils.setReportDataApp("click", serviceResultBean.getName(), points);
        LogEntity logEntity = new LogEntity();
        logEntity.setFromType("2");
        logEntity.setOperation("click");
        logEntity.setTime(Long.parseLong(Utils.getDate(0)));
        logEntity.setArgs(res);
        LogUtils.getInstance().insert(logEntity);
        if (LonchCloudApplication.getAppConfigDataBean().APP_PROCESS_NAME.equals("com.lonch.client")
                && serviceResultBean.getName().contains("短视频")) {
            Intent intent = new Intent(MainActivity.this, SmallVideoActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onOrSelected(HumanOrganizationBean.ServiceResultBean.DataOwnerOrgListBean position) {

    }

    @Override
    public void onOrFocusSelected(int position) {

    }

    @Override
    public void onTabSelected(ToolBarBean.ServiceResultBean.SiderbarsBean position) {


    }

    @Override
    public void onFocusSelected(int position) {

    }

    @Override
    public void onTopRightSelected(ToolBarBean.ServiceResultBean.FormsBean position) {

    }

    @Override
    public void onHtmlSuccess(AppZipBean bean) {

    }

    @Override
    public void onHtmlFaile(String fileMessage) {

    }

    @Override
    public void onSaveSuccess(UpdateInfoVBean bean) {

    }

    @Override
    public void onSaveFaile(String fileMessage) {

    }

    @Override
    public void onQuerySuccess(ToolBarBean bean) {
        if (null == bean) {
            return;
        }
        if (null == bean.getServiceResult()) {
            return;
        }
        htmlZipModel.getOrganizationList(token);
        mmkv.encode("toolBarData", new Gson().toJson(bean));
        boolean isShowHeaderPortrait = bean.getServiceResult().isShowHeaderPortrait();
        if (!isShowHeaderPortrait) {
            id_main_title_left.setVisibility(View.INVISIBLE);
        } else {
            id_main_title_left.setVisibility(View.VISIBLE);
        }
        if (!toolBarVersion.equals(bean.getServiceResult().getVersion())) {
            setToolBarData(bean);
        }
        siderbarsBeanArrayList = bean.getServiceResult().getSiderbars();

        showFragment(centerList);
    }

    private void showFragment(List<ToolBarBean.ServiceResultBean.FormsBean> centerBtnList) {
        mainTopRight.setVisibility(View.VISIBLE);
        for (int i = 0; i < centerBtnList.size(); i++) {
            ToolBarBean.ServiceResultBean.FormsBean.BottombarBean serviceResultBean = centerBtnList.get(i).getBottombar();
            ToolBarBeanMy toolBarBeanMy = new ToolBarBeanMy(serviceResultBean.getId(), serviceResultBean.getForm_id(), serviceResultBean.getName(), serviceResultBean.getType(), serviceResultBean.getWeb_app_id(), serviceResultBean.getUrl_path(), serviceResultBean.getIcon(), serviceResultBean.getIcon_hover(), serviceResultBean.getAlign(), serviceResultBean.getToolbar_type());
            if (serviceResultBean.getType().equals("1")) {
                BridgeFragment webFragment = new BridgeFragment();
                Bundle bundle = new Bundle();
                bundle.putParcelable("toolBarBean", toolBarBeanMy);
                bundle.putInt("id", i);
                webFragment.setArguments(bundle);
                toolBarBeanMy.setFragment(webFragment);
            }
            else if (serviceResultBean.getType().equals("2")) {
                WebFragment webFragment = new WebFragment();
                Bundle bundle = new Bundle();
                bundle.putParcelable("toolBarBean", toolBarBeanMy);
                bundle.putInt("id", i);
                webFragment.setArguments(bundle);
                toolBarBeanMy.setFragment(webFragment);
            }
            fragmentList.add(toolBarBeanMy);//添加到自定义集合（多了Fragment）
        }
        if (fragmentList.size() > 0) {
            //默认加载第一页
            replaceFragment(fragmentList.get(0).getFragment(), fragmentList.get(0).getFragment(), 0);
            //--------中部结束-------
            //--------底部刷新-----------
            mainTopRightAdapter.dataNotify(rightBtnList);
        }
        if (siderbarsBeanArrayList.size() > 0) {
            try {
                Thread.sleep(800);
            } catch (Exception e) {
            }
            ToolBarBean.ServiceResultBean.SiderbarsBean siderbarsBean = siderbarsBeanArrayList.get(0);
            String webId = siderbarsBean.getWeb_app_id();
            String url = siderbarsBean.getUrl_path();
            PlistPackageBean resources = Utils.getPackageBean(webId);
            if (null != resources) {
                final String app_package_namea = getFilesDir().getAbsolutePath() + "/" + "App/";
                String filePath = app_package_namea + resources.getApp_package_name() + "/" + resources.getVersion() + "/index.html";
                if (new File(filePath).exists()) {
                    url = LonchCloudApplication.getAppConfigDataBean().LOCAL_HOST + ":" + LonchCloudApplication.getAppConfigDataBean().PORT + "/" + resources.getApp_package_name() + "/" + resources.getVersion() + "/" + url;
                    if (myWebView != null) {
                        myWebView.loadUrl(url);
//                        if (isReload) {
//                            myWebView.loadUrl("javascript:window.location.reload(true)");
//                            isReload = false;
//                        }
                    }

                }
            }
        }
        initAllLog();
    }

    public void replaceFragment(Fragment from, Fragment to, int index) {
        fragmentTag = index;
        String tag = fragmentList.get(index).getWeb_app_id();
        if (isFinishing()) {
            Log.e("TAG", "MainActivity have no init finished and getActivity() == null");
            return;
        }
        if (from != to) {//不相等就是新页面
            if (!to.isAdded()) {    // 先判断是否被add过
                getSupportFragmentManager().beginTransaction().hide(from).add(R.id.ly_content, to, tag).commitAllowingStateLoss(); // 隐藏当前的fragment，add下一个到Activity中
            } else {
                getSupportFragmentManager().beginTransaction().hide(from).show(to).commitAllowingStateLoss(); // 隐藏当前的fragment，显示下一个
            }
        } else {//相等就是替换(第一次相等)加载云服
            if (!to.isAdded()) {    // 先判断是否被add过
                getSupportFragmentManager().beginTransaction().add(R.id.ly_content, to, tag).commitAllowingStateLoss(); // 隐藏当前的fragment，add下一个到Activity中
            } else {
                getSupportFragmentManager().beginTransaction().show(to).commitAllowingStateLoss(); // 隐藏当前的fragment，显示下一个
            }
        }
    }

    @Override
    public void onQueryFaile(String fileMessage) {

    }

    @Override
    public void onOrganizationSuccss(HumanOrganizationBean bean) {

    }

    @Override
    public void onOrganizationFaile(String fileMessage) {

    }

    @Override
    public void onYaoFaCaiInfoSuccess(YfcUserBean bean) {

    }

    @Override
    public void onYaoFaCaiInfoFaile(String fileMessage) {

    }

    @Override
    public void onUpdateSuccess(AppClientUpdateBean bean) {

    }

    @Override
    public void onUpdateFaile(String fileMessage) {

    }

    @Override
    public void onImLoginSuccess(ImLoginBean bean) {

    }

    @Override
    public void onImLoginFatal(String fileMessage) {

    }

    @Override
    public void onSaveDevicesSuccess(SaveClientDevicesBean bean) {

    }

    @Override
    public void onSaveDevicesFaile(String fileMessage) {

    }

    @Override
    public void onResponseSuccess(String sn, String bean, boolean isUpdateToken) {

    }

    @Override
    public void onResponseFailed(String sn, String fileMessage) {

    }

    @Override
    public void onRefreshSuccess(RefreshOrgBen bean) {

    }

    @Override
    public void onRefreshFailed(String fileMessage) {

    }
}

