package com.lonch.client.manager;

import android.os.AsyncTask;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import com.lonch.client.LonchCloudApplication;
import com.lonch.client.bean.NetLinkBean;
import com.lonch.client.database.bean.AccelerationOrderEntity;
import com.lonch.client.database.bean.NetLinkEntity;
import com.lonch.client.utils.AccelerationOrderUtils;
import com.lonch.client.utils.GsonUtils;
import com.lonch.client.utils.HeaderUtils;
import com.lonch.client.utils.NetLinkUtils;
import com.lonch.client.utils.NetWorkInfoUtils;
import com.lonch.client.utils.SpUtils;
import com.lonch.client.utils.Utils;
import com.tencent.mmkv.MMKV;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CheckNetManger {
    private static final String TAG = "CheckNetManger";
    private static volatile CheckNetManger instance;
    private int[] numbers = new int[]{1, 2, 3, 4, 5};
    private static OkHttpClient client = new OkHttpClient();
    private static MMKV mmkv = MMKV.defaultMMKV();
    private static List<NetLinkEntity> list = new ArrayList<>();
    private static int count;
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public CheckNetManger() {
    }

    public static CheckNetManger getInstance() {
        if (instance == null) {
            synchronized (CheckNetManger.class) {
                if (instance == null) {
                    instance = new CheckNetManger();
                }
            }
        }
        return instance;
    }

    public void CountLink(NetLinkBean netLinkBean, String token) {
        if (null == netLinkBean) {
            return;
        }
        if (null == netLinkBean.getServiceResult()){
            return;
        }
        if (null == netLinkBean.getServiceResult().getData()){
            return;
        }
        if (TextUtils.isEmpty(token)) {
            return;
        }
        mmkv.encode("userToken", token);
        List<NetLinkBean.ServiceTestUrlsBean> serviceTestUrlsBeanList = netLinkBean.getServiceResult().getData().getServiceTestUrls();
        List<NetLinkBean.AcceleratedLinkUrlsBean> acceleratedLinkUrlsBeanList = netLinkBean.getServiceResult().getData().getAcceleratedLinkUrls();
        if (serviceTestUrlsBeanList != null && acceleratedLinkUrlsBeanList != null) {
            if (serviceTestUrlsBeanList.size() > 0 && acceleratedLinkUrlsBeanList.size() > 0) {
                for (int i = 1; i < numbers.length + 1; i++) {
                    for (NetLinkBean.ServiceTestUrlsBean serviceTestUrlsBean : serviceTestUrlsBeanList) {
                        for (NetLinkBean.AcceleratedLinkUrlsBean acceleratedLinkUrlsBean : acceleratedLinkUrlsBeanList) {
                            String url = acceleratedLinkUrlsBean.getAccelerationLinkUrl() + serviceTestUrlsBean.getPath() + "?num=" + i;
                            boolean isCheck = mmkv.decodeBool(Utils.getDate(0) + url);
                            if (!isCheck) {
                                new SingleTask(i, url, acceleratedLinkUrlsBean, serviceTestUrlsBean, acceleratedLinkUrlsBeanList.size(), serviceTestUrlsBeanList.size()).execute();
                            }
                        }
                    }
                }
            }
        }
    }

    public int countSize(NetLinkBean netLinkBean) {
        if (null == netLinkBean) {
            return 0;
        }
        if (null == netLinkBean.getServiceResult()){
            return 0;
        }
        if (null == netLinkBean.getServiceResult().getData()){
            return 0;
        }
        List<NetLinkBean.ServiceTestUrlsBean> serviceTestUrlsBeanList = netLinkBean.getServiceResult().getData().getServiceTestUrls();
        List<NetLinkBean.AcceleratedLinkUrlsBean> acceleratedLinkUrlsBeanList = netLinkBean.getServiceResult().getData().getAcceleratedLinkUrls();
        if (null != serviceTestUrlsBeanList && null != acceleratedLinkUrlsBeanList) {
            if (serviceTestUrlsBeanList.size() > 0 && acceleratedLinkUrlsBeanList.size() > 0) {
                return serviceTestUrlsBeanList.size() * acceleratedLinkUrlsBeanList.size() * 4;
            } else {
                return 0;
            }
        } else {
            return 0;
        }
    }

    public NetLinkBean getNetLinkBean() {
        String linkData = mmkv.decodeString("netLinkInfo");
        if (!TextUtils.isEmpty(linkData)) {
            return GsonUtils.getInstance().fromJson(linkData, NetLinkBean.class);
        }
        return null;
    }

    public List<NetLinkBean.AcceleratedLinkUrlsBean> getAcceleratedList() {
        String linkData = mmkv.decodeString("netLinkInfo");
        if (!TextUtils.isEmpty(linkData)) {
            NetLinkBean linkBean = GsonUtils.getInstance().fromJson(linkData, NetLinkBean.class);
            return linkBean.getServiceResult().getData().getAcceleratedLinkUrls();
        }
        return null;
    }

    static class SingleTask extends AsyncTask<Object, Integer, NetLinkEntity> {
        private String url;
        private NetLinkBean.AcceleratedLinkUrlsBean acceleratedLinkUrlsBean;
        private NetLinkBean.ServiceTestUrlsBean serviceTestUrlsBean;
        private long startTime;
        private int i;
        private int size;
        private int serveSize;
        private int sum;

        public SingleTask(int i, String url, NetLinkBean.AcceleratedLinkUrlsBean acceleratedLinkUrlsBean, NetLinkBean.ServiceTestUrlsBean serviceTestUrlsBean, int size, int serveSize) {
            this.url = url;
            this.acceleratedLinkUrlsBean = acceleratedLinkUrlsBean;
            this.serviceTestUrlsBean = serviceTestUrlsBean;
            this.i = i;
            this.size = size;
            this.serveSize = serveSize;
            sum = this.size * this.serveSize;
        }

        @Override
        protected void onPostExecute(NetLinkEntity s) {
            if (null != s) {
                list.add(s);
                if (list.size() == size) {
                    new Thread(()->{
                        compareTestData(list);
                        list.clear();
                    }).start();
                }
            }
        }

        @Override
        protected NetLinkEntity doInBackground(Object... objects) {
            String userToken = mmkv.decodeString("userToken");
            if (TextUtils.isEmpty(userToken)) {
                return null;
            }
            count++;
            if (count > sum) {
                if (count % sum == 1) {
                    sleepTime(10000);
                } else {
                    sleepTime(1000);
                }
            } else {
                sleepTime(1000);
            }
            NetLinkEntity netLinkEntity = new NetLinkEntity();
            netLinkEntity.setApiId(serviceTestUrlsBean.getId());
            netLinkEntity.setLinkId(acceleratedLinkUrlsBean.getId());
            netLinkEntity.setParamValue(i);
            netLinkEntity.setIp((String) SpUtils.get("serviceIp", ""));
            netLinkEntity.setLossRate(0);
            netLinkEntity.setTime(Long.parseLong(Utils.getDate(0)));
            netLinkEntity.setId(UUID.randomUUID().toString().replace("-", ""));
            int netType = NetWorkInfoUtils.verify(LonchCloudApplication.getApplicationsContext());
            if (netType == 0) {
                netType = 2;
            }
            if(netType == -1){
                netType = 0;
            }
            netLinkEntity.setNetType(netType);
            netLinkEntity.setReportTime(Utils.getReportTime());
            assert userToken != null;
            HashMap<String, Object> map = new HashMap<>();
            map.put("num", i);
            String serviceUrl = acceleratedLinkUrlsBean.getAccelerationLinkUrl() + serviceTestUrlsBean.getPath();
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("sid", "android" + UUID.randomUUID().toString().replace("-", ""));
            hashMap.put("timestamp", System.currentTimeMillis());
            map.put("ip", (String) SpUtils.get("serviceIp", ""));
            hashMap.put("demand", map);
            RequestBody requestBody = RequestBody.create(JSON, GsonUtils.getInstance().toJson(hashMap));
            Request request = new Request.Builder()
                    .url(serviceUrl)
                    .addHeader("ACCESS-TOKEN", userToken)
                    .addHeader("protocol-version", "2.0")
                    .addHeader("User-Agent", "Mozilla/ 5.0  (Linux; Android  "+ Build.VERSION.RELEASE +")" + LonchCloudApplication.getAppConfigDataBean().APP_TYPE + "_" + HeaderUtils.getAppVersion())
                    .post(requestBody)
                    .build();


            startTime = System.currentTimeMillis();
            try {
                Call requestCall = client.newCall(request);
                Response response = requestCall.execute();
                netLinkEntity.setResposeTime((int) ((int) System.currentTimeMillis() - startTime));
                try {
                    if (response.isSuccessful()) {
                        String data = Objects.requireNonNull(response.body()).string();
                        Log.i(TAG, "success");
                        if (url.contains("num=5")) {
                            mmkv.encode(Utils.getDate(0) + url, true);
                        }
                        netLinkEntity.setType(1);
                        String excuteTime = response.header("execute-time");
                        if (!TextUtils.isEmpty(excuteTime)) {
                            netLinkEntity.setTranferTime(Integer.parseInt(excuteTime));
                        }
                        NetLinkUtils.getInstance().insert(netLinkEntity);
                    } else {
                        netLinkEntity.setType(0);
                        NetLinkUtils.getInstance().insert(netLinkEntity);
                    }
                } catch (Exception e) {
                    Log.i(TAG, "Exception---" + e.getMessage());
                    netLinkEntity.setType(0);
                    NetLinkUtils.getInstance().insert(netLinkEntity);
                }
            } catch (Exception e) {
                Log.i(TAG, "error---" + e.getMessage());
                netLinkEntity.setType(0);
                NetLinkUtils.getInstance().insert(netLinkEntity);
            }
            return netLinkEntity;
        }

        private void compareTestData(List<NetLinkEntity> list) {
            List<AccelerationOrderEntity> accelerationOrderEntities = new ArrayList<>();
            if (list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    NetLinkEntity netLinkBean = list.get(i);
                    AccelerationOrderEntity accelerationOrderEntity = new AccelerationOrderEntity();
                    accelerationOrderEntity.setLinkId(netLinkBean.getLinkId());
                    accelerationOrderEntity.setTime(Long.parseLong(Utils.getDate(0)));
                    if (netLinkBean.getType() == 0) { //失败
                        accelerationOrderEntity.setOrder(Integer.MAX_VALUE);
                    } else {
                        accelerationOrderEntity.setOrder(netLinkBean.getResposeTime());
                    }
                    accelerationOrderEntities.add(accelerationOrderEntity);
                }
                Collections.sort(accelerationOrderEntities, new Comparator<AccelerationOrderEntity>() {
                    @Override
                    public int compare(AccelerationOrderEntity o1, AccelerationOrderEntity o2) {
                        return o1.getOrder() - o2.getOrder();
                    }
                });
                for (int i = 0; i < accelerationOrderEntities.size(); i++) {
                    AccelerationOrderEntity accelerationOrderEntity = accelerationOrderEntities.get(i);
                    if (accelerationOrderEntity.getOrder() == Integer.MAX_VALUE) {
                        accelerationOrderEntity.setOrder(accelerationOrderEntities.size());
                    } else {
                        accelerationOrderEntity.setOrder(i + 1);
                    }
                    AccelerationOrderEntity storeEntity = AccelerationOrderUtils.getInstance().queryByLinkIdAndTime(accelerationOrderEntity.getLinkId(), accelerationOrderEntity.getTime());
                    if (null != storeEntity) {
                        int order = storeEntity.getOrder() + accelerationOrderEntity.getOrder();
                        accelerationOrderEntity.setOrder(order);
                        accelerationOrderEntity.setId(storeEntity.getId());
                        AccelerationOrderUtils.getInstance().updateDevice(accelerationOrderEntity);
                    } else {
                        AccelerationOrderUtils.getInstance().insert(accelerationOrderEntity);
                    }

                }
            }

        }

        private void sleepTime(int time) {
            try {
                Thread.sleep(time);
            } catch (Exception e) {
            }
        }
    }

}
