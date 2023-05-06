[Android App 开发技术图谱 - 简书 (jianshu.com)](https://www.jianshu.com/p/39c63eff3c36)

# 基础知识

## 界面布局

[Android常见界面布局_android 布局_香蕉道突破手牛爷爷的博客-CSDN博客](https://blog.csdn.net/qq_50587771/article/details/122494034)

## fragment

fragment译为“碎片”，是Android 3.0（API 11）提出的，最开始是为了适配大屏的平板。

可以结合多个Fragment到一个activity中来构建一个有多方面功能的UI，还可以重用同一个Fragment在多个activity中。Fragment可以当成是activity的一个组件，每个Fragment有单独的生命周期，并能接收输入事件，可以在activity运行时动态的进行添加和移除。因此，相比较于activity，Fragment更加轻量级，更加灵活。

可以将Fragment添加到被activity管理的后退栈中，这样用户可以通过点击返回按钮来返回之前打开的Fragment。

### 生命周期

![img](C:\Users\lonch\Documents\工作任务\05.App大前端\通用功能\images\watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5a2f6Iqz6Iqz,size_20,color_FFFFFF,t_70,g_se,x_16.png)

流程：

- ①Activity加载Fragment的时候，依次调用：onAttach() -> onCreate() -> onCreateView() -> onActivityCreated() -> onStart() ->onResume()

- ②当做出一个悬浮的对话框风格的Activity或者其他，就是让Fragment所在的Activity可见，但不获得焦点：onPause()

- ③当对话框关闭，Activity又获得了焦点： onResume()

- ④当替换Fragment，并调用addToBackStack()将它添加到后退栈中：onPause() -> onStop() -> onDestoryView() 。注意，此时的Fragment还没有被销毁。

- ⑤当按下键盘的回退键，Fragment会再次显示出来：onCreateView() -> onActivityCreated() -> onStart() -> onResume()

- ⑥如果替换后，在事务commit之前没有调用addToBackStack()方法将Fragment添加到后退栈中，或者退出了Activity，那么Fragment将会被完全结束，进入销毁状态： onPause() -> onStop() -> onDestoryView() -> onDestory() -> onDetach()

### 创建



### 交互





fragment导航：https://github.com/listenzz/AndroidNavigation#building-hierarchy

### 示例：侧滑菜单

https://www.cnblogs.com/whycxb/p/8506137.html

### 示例：左侧导航

```java
public class MainActivity extends FragmentActivity {
 // private Handler handler;
 private ViewPager fragmentViewPager;
 private ListView leftListView;
 private List<Fragment> fragmentlist;
 private Fragment1 fragment1;
 private Fragment2 fragment2;
 private ArrayList<String> leftlist = new ArrayList<String>();// left list 
 
 private ListAdapter listAdapter = null;
 @Override
 protected void onCreate(Bundle savedInstanceState) {
  // TODO Auto-generated method stub
  super.onCreate(savedInstanceState);
  setContentView(R.layout.activity_main);
  fragmentViewPager = (ViewPager) findViewById(R.id.fragmentViewPager);
  leftListView = (ListView) findViewById(R.id.leftListView); 
 
  initDatas();// left初始化。
  initDatasViewPager();
  // aTextViewCallBack back = (aTextViewCallBack)
  // findViewById(R.id.aTextViewCallBack);
  // back.setCalBack(new calBack() {
  // @Override
  // public void onclick() {
  // // TODO Auto-generated method stub
  // Toast.makeText(MainActivity.this, "这是回调的点击事件哦~",
  // Toast.LENGTH_LONG).show();
  // }
  // });
  //
  // new Thread(new Runnable() {
  //
  // @Override
  // public void run() {
  // // TODO Auto-generated method stub
  // String str = "http://www.easyicon.net/";
  // String st = getHtmlByURL.getHtmlByURL(str);
  // Message msg = handler.obtainMessage(0,st);
  // handler.sendMessage(msg);
  // }
  // }).start();
  // handler = new Handler() {
  // public void handleMessage(Message msg) {
  // super.handleMessage(msg);
  // String x = msg.obj.toString();
  // Log.i("GEtHtml", x);
  // };
  // };
 } 
 
 public void initDatas() {
  for (int i = 0; i < 5; i++) {
   leftlist.add("第" + i + "个");
   Log.i("65", leftlist.get(i).toString());
  } 
 
  ArrayList<ButtonView> buttonListView = new ArrayList<ButtonView>();
  ButtonView a = new ButtonView(R.string.pay_name_weixin);
  buttonListView.add(a);
  ButtonView b = new ButtonView(R.string.pay_name_alipay);
  buttonListView.add(b); 
 
  listAdapter = new ListAdapter(buttonListView);
  leftListView.setAdapter(listAdapter);
  leftListView.setDividerHeight(0);
  // listview点击事件
  leftListView.setOnItemClickListener(new OnItemClickListener() {
   @Override
   public void onItemClick(AdapterView<?> parent, View view,
     int position, long id) {
    Log.e("tag", Integer.toString(position));
    // TODO Auto-generated method stub
    listAdapter.setSelectedPosition(position);
    listAdapter.notifyDataSetInvalidated();
    if (position % 2 == 1) {
     fragmentViewPager.setCurrentItem(1);
    }else{
     fragmentViewPager.setCurrentItem(0);
    }
   }
  });
 } 
 
 public void initDatasViewPager() {
  fragmentlist = new ArrayList<Fragment>();
  fragment1 = new Fragment1();//
  fragment2 = new Fragment2();//
  fragmentlist.add(fragment1);
  fragmentlist.add(fragment2);
  fragmentViewPager.setAdapter(new FragmentAdapter(
    getSupportFragmentManager(), fragmentlist, this));
  fragmentViewPager.setOnPageChangeListener(new MyOnPageChangeListener());
 } 
 
 // set OnPageChangeListener in inner class
 class MyOnPageChangeListener implements OnPageChangeListener { 
 
  @Override
  public void onPageScrollStateChanged(int arg0) {
   // TODO Auto-generated method stub 
 
  } 
 
  @Override
  public void onPageScrolled(int arg0, float arg1, int arg2) {
   // TODO Auto-generated method stub 
 
  }
  /**
   * 当点击不同id的ViewPage的时候才触发
   * */
  @Override
  public void onPageSelected(int arg0) {
   Log.e("tag1", Integer.toString(arg0));
   switch (arg0) {
   case 0: 
 
    break;
   case 1: 
 
    break;
   case 2: 
 
    break;
   }
  } 
 } 
 
 public class ListAdapter extends BaseAdapter { 
  ArrayList<ButtonView> arrayList = null;
  LayoutInflater inflater;
  View view;
  ButtonLayoutHolder buttonLayoutHolder;
  LinearLayout buttonLayout = null;
  TextView buttonText = null; 
 
  private int selectedPosition = -1;// 选中的位置 
 
  public ListAdapter(ArrayList<ButtonView> buttonListView) {
   // TODO Auto-generated constructor stub
   arrayList = buttonListView;
  } 
 
  @Override
  public int getCount() {
   // TODO Auto-generated method stub
   return arrayList.size();
  } 
 
  @Override
  public Object getItem(int position) {
   // TODO Auto-generated method stub
   return arrayList.get(position);
  } 
 
  @Override
  public long getItemId(int position) {
   // TODO Auto-generated method stub
   return position;
  } 
 
  public void setSelectedPosition(int position) {
   selectedPosition = position;
  } 
 
  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
   // TODO Auto-generated method stub
   inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
   view = inflater.inflate(R.layout.button_layout, null, false);
   buttonLayoutHolder = (ButtonLayoutHolder) view.getTag(); 
 
   if (buttonLayoutHolder == null) {
    buttonLayoutHolder = new ButtonLayoutHolder();
    buttonLayoutHolder.buttonLayout = (LinearLayout) view
      .findViewById(R.id.LinearLayoutButton);
    buttonLayoutHolder.textView = (TextView) view
      .findViewById(R.id.TextViewButton);
    view.setTag(buttonLayoutHolder);
   }
   buttonLayout = buttonLayoutHolder.buttonLayout;
   buttonText = buttonLayoutHolder.textView;
   if (selectedPosition == position) {
    buttonText.setSelected(true);
    buttonText.setPressed(true);
    buttonLayout.setBackgroundColor(Color.parseColor("#e4e8e9"));
    buttonText.setTextColor(Color.BLUE); 
   } else {
    buttonText.setSelected(false);
    buttonText.setPressed(false);
    buttonLayout.setBackgroundColor(Color.parseColor("#2f4471"));
    buttonText.setTextColor(Color.WHITE); 
   }
   buttonText.setHeight(40);
   buttonText.setText(arrayList.get(position).textViewId);
   return view; 
  } 
 };
}

class ButtonView {
 int textViewId; 
 
 ButtonView(int tId) {
  textViewId = tId;
 }
} 
 
class ButtonLayoutHolder {
 LinearLayout buttonLayout;
 TextView textView;
}

// FragmentAdapter.java
public class FragmentAdapter extends FragmentPagerAdapter{
 private List<Fragment> listmVp;
 private Context context; 
 
 public FragmentAdapter(FragmentManager fm, List<Fragment> listmVp,Context context) {
  super(fm);
  this.listmVp = listmVp;
  this.context = context;
 }
 @Override
 public int getCount() {
  // TODO Auto-generated method stub
  return listmVp.size();
 } 
 
 @Override
 public Fragment getItem(int arg0) {
  // TODO Auto-generated method stub
  return listmVp.get(arg0);
 }
}
```

Fragment1.java & Fragment1.java

```java
public class Fragment1 extends Fragment{
 @Override
 public View onCreateView(LayoutInflater inflater, ViewGroup container,
   Bundle savedInstanceState) {
  // TODO Auto-generated method stub
  return inflater.inflate(R.layout.fragment1, container, false);
 }
}

public class Fragment2 extends Fragment{
 @Override
 public View onCreateView(LayoutInflater inflater, ViewGroup container,
   Bundle savedInstanceState) {
  // TODO Auto-generated method stub
  return inflater.inflate(R.layout.fragment2, container, false);
 }
}
```

## Intent

中文意思是“意图，意向”， Intent对Android的核心和灵魂，是各组件之间的桥梁。

1. 四大组件分别为Activity 、Service、BroadcastReceiver、ContentProvider。这四种组件是独立的，它们之间可以互相调用，协调工作，最终组成一个真正的Android应用。
2. Activity、Service和Broadcast receiver之间是通过Intent进行通信的，而另外一个组件Content Provider本身就是一种通信机制，不需要通过Intent。

## WebView

框架：AgentWeb ，是一个基于的 Android WebView ，极度容易使用以及功能强大的库，提供了 Android WebView 一系列的问题解决方案 ，并且轻量和极度灵活。

https://github.com/Justson/AgentWeb

## 二维码

android-zxingLibrary

## App格式

- **APK**（全称：Android application package，Android应用程序包）是Android操作系统使用的一种应用程序包文件格式，用于分发和安装移动应用及中间件。一个Android应用程序的代码想要在Android设备上运行，必须先进行编译，然后被打包成为一个被Android系统所能识别的文件才可以被运行，而这种能被Android系统识别并运行的文件格式便是“APK”。 一个APK文件内包含被编译的代码文件(.dex 文件)，文件资源（resources）， 原生资源文件（assets），证书（certificates），和清单文件（manifest file）
- **AAB**（全称为Android App Bundles），谷歌应用商店的App格式。谷歌声称这种新格式将使应用程序文件更小，意味着aab分布式应用程序比通用apk平均少占用15% 的空间。更重要的是，它拓展了应用程序捆缚包的定义，只包含运行App时的必要代码。也就是说，下载了一部分之后，App就可以直接运行，无需等待下载完成再安装。

## okhttp

OKHttp是一个处理网络请求的开源项目，Android 当前最火热网络框架，由移动支付Square公司贡献，用于替代HttpUrlConnection和Apache HttpClient  (android API23 6.0里已移除HttpClient）。里面集成了 okio (一个I/O框架，优化内存与CPU)。在接收数据的时候使用了 okio框架 来做一些I/O处理，okio框架是弥补Java.io 上的不足，节省CPU与内存资源，

官网：https://square.github.io/okhttp/

### 介绍

**优点**

1. 支持HTTP2/SPDY（SPDY是Google开发的基于TCP的传输层协议，用以最小化网络延迟，提升网络速度，优化用户的网络使用体验。）
2. socket自动选择最好路线，并支持自动重连，拥有自动维护的socket连接池，减少握手次数，减少了请求延迟，共享Socket,减少对服务器的请求次数。
3. 基于Headers的缓存策略减少重复的网络请求。
4. 拥有Interceptors轻松处理请求与响应（自动处理GZip压缩）。

**功能**

1. PUT，DELETE，POST，GET等请求
2. 文件的上传下载
3. 加载图片(内部会图片大小自动压缩)
4. 支持请求回调，直接返回对象、对象集合
5. 支持session的保持

### 使用

添加OkHttp3的依赖

```python
compile 'com.squareup.okhttp3:okhttp:3.7.0'
compile 'com.squareup.okio:okio:1.12.0'
```

添加网络权限

```cobol
<uses-permission android:name="android.permission.INTERNET"/>
```

#### 异步GET请求

**4个步骤**

1. 创建OkHttpClient对象
2. 通过Builder模式创建Request对象，参数必须有个url参数，可以通过Request.Builder设置更多的参数比如：header、method等
3. 通过request的对象去构造得到一个Call对象，Call对象有execute()和cancel()等方法。
4. 以异步的方式去执行请求，调用的是call.enqueue，将call加入调度队列，任务执行完成会在Callback中得到结果。

```java
//1.创建OkHttpClient对象
OkHttpClient okHttpClient = new OkHttpClient();
//2.创建Request对象，设置一个url地址（百度地址）,设置请求方式。
Request request = new Request.Builder().url("http://www.baidu.com").method("GET",null).build();
//3.创建一个call对象,参数就是Request请求对象
Call call = okHttpClient.newCall(request);
//4.请求加入调度，重写回调方法
call.enqueue(new Callback() {
    //请求失败执行的方法
    @Override
    public void onFailure(Call call, IOException e) {
    }
    //请求成功执行的方法
    @Override
    public void onResponse(Call call, Response response) throws IOException {
    }
});
```

**注意事项：**

1. 异步调用的回调函数是在子线程,我们不能在子线程更新UI,需要借助于 runOnUiThread() 方法或者 Handler 来处理。
2. onResponse回调有一个参数是response，如果我们想获得返回的是字符串，可以通过response.body().string()获取；如果希望获得返回的二进制字节数组，则是response.body().bytes()；如果你想拿到返回的inputStream，则调response.body().byteStream()，有inputStream我们就可以通过IO的方式写文件（后面会有例子）。

#### 同步GET请求

```java
//4.同步调用会阻塞主线程,这边在子线程进行
new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                //同步请求调用Call的execute(),返回Response,会抛出IO异常
                Response response = call.execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }).start();
```

#### Post请求

```java
//1.创建OkHttpClient对象
OkHttpClient  okHttpClient = new OkHttpClient();

//2.通过new FormBody()调用build方法,创建一个RequestBody,可以用add添加键值对 
RequestBody  requestBody = new FormBody.Builder().add("name","zhangqilu").add("age","25").build();

//2.通过RequestBody.create 创建requestBody对象,可以提交字符串
MediaType mediaType = MediaType.parse("application/json; charset=utf-8");//"类型,字节码"
//字符串
String value = "{username:admin;password:admin}"; 
RequestBody requestBody =RequestBody.create(mediaType, value);

//3.创建Request对象，设置URL地址，将RequestBody作为post方法的参数传入
Request request = new Request.Builder().url("url").post(requestBody).build();
//4.创建一个call对象,参数就是Request请求对象
Call call = okHttpClient.newCall(request);
//5.请求加入调度,重写回调方法
call.enqueue(new Callback() {
    @Override
    public void onFailure(Call call, IOException e) {
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
    }
});
```

#### 上传文件

要添加存储卡读写权限,在 AndroidManifest.xml 文件中添加如下代码:

```cobol
 <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
 <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
```

文件上传代码：使用**异步POST请求**

```java
//1.创建OkHttpClient对象
OkHttpClient  okHttpClient = new OkHttpClient();
//上传的图片
File file = new File(Environment.getExternalStorageDirectory(), "zhuangqilu.png");
//2.通过RequestBody.create 创建requestBody对象,application/octet-stream 表示文件是任意二进制数据流
RequestBody requestBody =RequestBody.create(MediaType.parse("application/octet-stream"), file);
//3.创建Request对象，设置URL地址，将RequestBody作为post方法的参数传入
Request request = new Request.Builder().url("url").post(requestBody).build();
//4.创建一个call对象,参数就是Request请求对象
Call call = okHttpClient.newCall(request);
//5.请求加入调度,重写回调方法
call.enqueue(new Callback() {
    @Override
    public void onFailure(Call call, IOException e) {
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
    }
});
```

#### 下载文件

设置下载地址，在回调函数中拿到了图片的字节流,然后保存为了本地的一张图片：

```java
//1.创建OkHttpClient对象
OkHttpClient okHttpClient = new OkHttpClient();
//2.创建Request对象，设置一个url地址（百度地址）,设置请求方式。
Request request = new Request.Builder().url("https://www.baidu.com/img/bd_logo1.png").get().build();
//3.创建一个call对象,参数就是Request请求对象
Call call = okHttpClient.newCall(request);
//4.请求加入调度,重写回调方法
call.enqueue(new Callback() {
    @Override
    public void onFailure(Call call, IOException e) {
        Log.e(TAG, "onFailure: "+call.toString() );
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        //拿到字节流
        InputStream is = response.body().byteStream();
        int len = 0;
        //设置下载图片存储路径和名称
        File file = new File(Environment.getExternalStorageDirectory(),"baidu.png");
        FileOutputStream fos = new FileOutputStream(file);
        byte[] buf = new byte[128];
        while((len = is.read(buf))!= -1){
            fos.write(buf,0,len);
            Log.e(TAG, "onResponse: "+len );
        }
        fos.flush();
        fos.close();
        is.close();
    }
});
```

从网络下载一张图片并直接设置到ImageView中：

```java
@Override
public void onResponse(Call call, Response response) throws IOException {
    InputStream is = response.body().byteStream();
    //使用 BitmapFactory 的 decodeStream 将图片的输入流直接转换为 Bitmap 
    final Bitmap bitmap = BitmapFactory.decodeStream(is);
    //在主线程中操作UI
    runOnUiThread(new Runnable() {
        @Override
        public void run() {
            //然后将Bitmap设置到 ImageView 中
            imageView.setImageBitmap(bitmap);
        }
    });
 
    is.close();
}
```

#### 上传Multipart文件

既要上传文件还要上传其他类型字段。比如在个人中心我们可以修改名字，年龄，修改图像，这其实就是一个表单。MuiltipartBody是RequestBody 的一个子类，提交表单就是利用这个类来构建一个 RequestBody。

```java
//1.创建OkHttpClient对象
OkHttpClient  okHttpClient = new OkHttpClient();
//上传的图片
File file = new File(Environment.getExternalStorageDirectory(), "zhuangqilu.png");
//2.通过new MultipartBody build() 创建requestBody对象，
RequestBody  requestBody = new MultipartBody.Builder()
        //设置类型是表单
        .setType(MultipartBody.FORM)
        //添加数据
        .addFormDataPart("username","zhangqilu")
        .addFormDataPart("age","25")
        .addFormDataPart("image","zhangqilu.png",
RequestBody.create(MediaType.parse("image/png"),file))
        .build();
//3.创建Request对象，设置URL地址，将RequestBody作为post方法的参数传入
Request request = new Request.Builder().url("url").post(requestBody).build();
//4.创建一个call对象,参数就是Request请求对象
Call call = okHttpClient.newCall(request);
//5.请求加入调度,重写回调方法
call.enqueue(new Callback() {
    @Override
    public void onFailure(Call call, IOException e) {
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
    }
});
```

**注意事项**

1. 如果提交的是表单,一定要设置表单类型， `setType(MultipartBody.FORM)`
2. 提交文件 addFormDataPart() 方法的第一个参数就是类似于键值对的键,是供服务端使用的，第二个参数是文件的本地的名字，第三个参数是 RequestBody，里面包含了我们要上传的文件的路径以及 MidiaType。

### 源码



## 文件下载

### 封装URLConnection

```java
public void downloadFile1() {
    try{
        //下载路径，如果路径无效了，可换成你的下载路径
        String url = "http://c.qijingonline.com/test.mkv";
        String path = Environment.getExternalStorageDirectory().getAbsolutePath();

        final long startTime = System.currentTimeMillis();
        Log.i("DOWNLOAD","startTime="+startTime);
        //下载函数
       String filename=url.substring(url.lastIndexOf("/") + 1);
        //获取文件名
        URL myURL = new URL(url);
        URLConnection conn = myURL.openConnection();
        conn.connect();
        InputStream is = conn.getInputStream();
        int fileSize = conn.getContentLength();//根据响应获取文件大小
        if (fileSize <= 0) throw new RuntimeException("无法获知文件大小 ");
        if (is == null) throw new RuntimeException("stream is null");
        File file1 = new File(path);
        if(!file1.exists()){
            file1.mkdirs();
        }
        //把数据存入路径+文件名
        FileOutputStream fos = new FileOutputStream(path+"/"+filename);
        byte buf[] = new byte[1024];
        int downLoadFileSize = 0;
        do{
            //循环读取
            int numread = is.read(buf);
            if (numread == -1)
            {
                break;
            }
            fos.write(buf, 0, numread);
            downLoadFileSize += numread;
            //更新进度条
        } while (true);

        Log.i("DOWNLOAD","download success");
        Log.i("DOWNLOAD","totalTime="+ (System.currentTimeMillis() - startTime));

        is.close();
    } catch (Exception ex) {
        Log.e("DOWNLOAD", "error: " + ex.getMessage(), ex);
    }
}
```

### Android自定的下载管理

交给了Android系统的另一个app去下载。好处是：不会消耗该APP的 CPU资源。缺点是：控制起来很不灵活。

```java
private void downloadFile2(){
    //下载路径，如果路径无效了，可换成你的下载路径
    String url = "http://c.qijingonline.com/test.mkv";
    //创建下载任务,downloadUrl就是下载链接
    DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
    //指定下载路径和下载文件名
    request.setDestinationInExternalPublicDir("", url.substring(url.lastIndexOf("/") + 1));
    //获取下载管理器
    DownloadManager downloadManager= (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
    //将下载任务加入下载队列，否则不会进行下载
    downloadManager.enqueue(request);
}
```

### okhttp 网络请求框架

```java
private void downloadFile3(){
    //下载路径，如果路径无效了，可换成你的下载路径
    final String url = "http://c.qijingonline.com/test.mkv";
    final long startTime = System.currentTimeMillis();
    Log.i("DOWNLOAD","startTime="+startTime);

    Request request = new Request.Builder().url(url).build();
    new OkHttpClient().newCall(request).enqueue(new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
            // 下载失败
            e.printStackTrace();
            Log.i("DOWNLOAD","download failed");
        }
        @Override
        public void onResponse(Call call, Response response) throws IOException {
            Sink sink = null;
            BufferedSink bufferedSink = null;
            try {
                String mSDCardPath= Environment.getExternalStorageDirectory().getAbsolutePath();
                File dest = new File(mSDCardPath,   url.substring(url.lastIndexOf("/") + 1));
                sink = Okio.sink(dest);
                bufferedSink = Okio.buffer(sink);
                bufferedSink.writeAll(response.body().source());

                bufferedSink.close();
                Log.i("DOWNLOAD","download success");
                Log.i("DOWNLOAD","totalTime="+ (System.currentTimeMillis() - startTime));
            } catch (Exception e) {
                e.printStackTrace();
                Log.i("DOWNLOAD","download failed");
            } finally {
                if(bufferedSink != null){
                    bufferedSink.close();
                }

            }
        }
    });
}
```

也可以自己来接收字符流，然后获取下载的进度

```java
public void downloadFile(){
        final String url = "http://c.qijingonline.com/test.mkv";
        final long startTime = System.currentTimeMillis();
        Log.i("DOWNLOAD","startTime="+startTime);
        OkHttpClient okHttpClient = new OkHttpClient();

        Request request = new Request.Builder().url(url).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // 下载失败
                e.printStackTrace();
                Log.i("DOWNLOAD","download failed");
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream is = null;
                byte[] buf = new byte[2048];
                int len = 0;
                FileOutputStream fos = null;
                // 储存下载文件的目录
                String savePath = Environment.getExternalStorageDirectory().getAbsolutePath();
                try {
                    is = response.body().byteStream();
                    long total = response.body().contentLength();
                    File file = new File(savePath, url.substring(url.lastIndexOf("/") + 1));
                    fos = new FileOutputStream(file);
                    long sum = 0;
                    while ((len = is.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                        sum += len;
                        int progress = (int) (sum * 1.0f / total * 100);
                        // 下载中
//                        listener.onDownloading(progress);
                    }
                    fos.flush();
                    // 下载完成
//                    listener.onDownloadSuccess();
                    Log.i("DOWNLOAD","download success");
                    Log.i("DOWNLOAD","totalTime="+ (System.currentTimeMillis() - startTime));
                } catch (Exception e) {
                    e.printStackTrace();
//                    listener.onDownloadFailed();
                    Log.i("DOWNLOAD","download failed");
                } finally {
                    try {
                        if (is != null)
                            is.close();
                    } catch (IOException e) {
                    }
                    try {
                        if (fos != null)
                            fos.close();
                    } catch (IOException e) {
                    }
                }
            }
        });
    }
```

## EventBus

[Github源码](https://github.com/greenrobot/EventBus)

## MMKV

使用

```java
// 依赖
dependencies {
    implementation 'com.tencent:mmkv:1.0.15'
}

// Application里面初始化：
MMKV.initialize(this)
    
//……一个全局的实例
// MMKV kv = MMKV.defaultMMKV();

 MMKV mMkv = MMKV.mmkvWithID(Constant.MMKV_PREFERENCES, MMKV.SINGLE_PROCESS_MODE);
// 存储数据：
mMkv.encode("Stingid", "123456");
mMkv.encode("bool", true);
mMkv.encode("int", Integer.MIN_VALUE);

// 取数据：
String id=mMkv.decodeString("id", null);
boolean bValue = mMkv.decodeBool("bool");
int iValue = kv.decodeInt("int");
// 移除数据：
 mMkv.remove("Stingid");
 mMkv.remove("bool");
 mMkv.remove("int");
```



# Gradle

定义：是一个完全开源的自动化构建工具。IDEA 默认集成了该工具。可以方便的帮我们将项目代码进行构建打包，是一个脚本工具。

Gradle是用了一种基于Groovy的领域特定语言（DSL，Domain Specific Language）来声明项目设置，摒弃了XML（如ANT和Maven）的各种繁琐配置。

Gradle官网：[gradle.org](https://gradle.org/)

项目中一般会出现2个或者多个build.gradle文件，一个在根目录下，一个在app目录下。如果切换到Android模式下则全部在Gradle Scripts。

## Android Gradle

使用指定gradle版本：https://www.jianshu.com/p/10de5d4f8a8a

Android Studio 是采用Gradle 进行代码的打包编译以及构建项目的。

但是Gradle是一个开源的自动构建工具，是面向全平台的。所以Android 在基于Gradle进行了二次开发，整合了几项专门用于构建Android应用的功能。

而该工具命名为：com.android.tools.build:gradle:xxxx 。

```json
//这个地方是android 自己定制化Gradle的插件版本
classpath 'com.android.tools.build:gradle:3.6.4'
 
//gradle/wrapper/gradle-wrapper.properties  这个是系统独立的Gradle的版本号。
distributionUrl=https://downloads.gradle-dn.com/distributions/gradle-5.6.4-all.zip
```

### 构建流程

![在这里插入图片描述](https://img-blog.csdnimg.cn/326ef81c9a3744cd8ee48f56dd83c5db.png#pic_center)

常规步骤执行：

1. 编译器将源代码转换成 DEX 文件（Dalvik 可执行文件，其中包括在 Android 设备上运行的字节码），并将其他所有内容转换成编译后的资源。
2. 打包器将 DEX 文件和编译后的资源组合成 APK 或 AAB（具体取决于所选的 build 目标）。 必须先为 APK 或 AAB 签名，然后才能将应用安装到 Android 设备或分发到 Google Play 等商店。
3. 打包器使用调试或发布密钥库为 APK 或 AAB 签名：
   1. 如果构建的是调试版应用（即专门用来测试和分析的应用），则打包器会使用调试密钥库为应用签名。Android Studio 会自动使用调试密钥库配置新项目。
   2. 如果构建的是打算对外发布的发布版应用，则打包器会使用发布密钥库（需要进行配置）为应用签名。
4. 在生成最终 APK 之前，打包器会使用 zipalign 工具对应用进行优化，以减少其在设备上运行时所占用的内存。
5. 构建流程结束时，将获得应用的调试版或发布版 APK/AAB，以用于部署、测试或向外部用户发布。

### 版本对应

以前两个插件的版本一直对应不上，容易造成大家的误解，以及造成更多的编译错误。Android Gradle的版本号 在Gradle发布7.0后，进行了同步

| Android Gradle 插件版本 | 所需的 Gradle 版本 |
| :---------------------- | :----------------- |
| 1.0.0 - 1.1.3           | 2.2.1 - 2.3        |
| 1.2.0 - 1.3.1           | 2.2.1 - 2.9        |
| 1.5.0                   | 2.2.1 - 2.13       |
| 2.0.0 - 2.1.2           | 2.10 - 2.13        |
| 2.1.3 - 2.2.3           | 2.14.1 - 3.5       |
| 2.3.0+                  | 3.3+               |
| 3.0.0+                  | 4.1+               |
| 3.1.0+                  | 4.4+               |
| 3.2.0 - 3.2.1           | 4.6+               |
| 3.3.0 - 3.3.3           | 4.10.1+            |
| 3.4.0 - 3.4.3           | 5.1.1+             |
| 3.5.0 - 3.5.4           | 5.4.1+             |
| 3.6.0 - 3.6.4           | 5.6.4+             |
| 4.0.0+                  | 6.1.1+             |
| 4.1.0+                  | 6.5+               |
| 4.2.0+                  | 6.7.1+             |
| 7.0                     | 7.0+               |

## 配置文件

文件目录如下:

```
App/
|--build.gradle
|--settings.gradle
|--app/
	|--build.gradle
	|--build/
	|--libs/
	|--src/
		|--main/
            |--java/
            	|--com.example.app/
            |--res/
                |--drawable/
                |--layout/
                |--...
            |--AndroidManifest.xml
            |--aidl/
```

### settings.gradle

`settings.gradle`用于定义项目级代码库设置，并告知 Gradle 在构建应用时应将哪些模块包含在内。

```json
pluginManagement {//插件管理
    repositories {//搜索或下载Gradle插件的仓库依赖
        gradlePluginPortal()
        google()//Google's Maven repository
        mavenCentral()//Maven Central Repository
    }
}
dependencyResolutionManagement {//依赖项解析管理
    /**
     * 存储库模式：
     * PREFER_PROJECT --首选项目远程仓库,表示如果工程单独设置了仓库，就优先使用工程配置的，忽略settings里面的
     * PREFER_SETTINGS--首选settings远程仓库，表示任何通过工程单独设置或插件设置的仓库，都会被忽略
     * FAIL_ON_PROJECT_REPOS--强制settings远程仓库，表示如果工程单独设置了仓库，或工程的插件设置了仓库，构建就直接报错抛出异常
     */
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)//设置存储库的方法
    repositories {
        google()//Google's Maven repository
        mavenCentral()//Maven Central Repository
    }
}
rootProject.name = "My Application"//根工程的名字
include ':app'//包含的子模块
```

### build.gradle

`build.gradle`用于定义适用于项目中所有模块的依赖项。

**顶层build配置**

```json
plugins {//插件
    //根build配置中应该声明 apply false ，而在子工程中不应该声明 apply false
    id 'com.android.application' version '7.2.2' apply false //app
    id 'com.android.library' version '7.2.2' apply false //library
    id 'org.jetbrains.kotlin.android' version '1.7.0' apply false //kotlin
}

task clean(type: Delete) { //任务
    delete rootProject.buildDir //删除build目录
}

ext { //全局配置, 供其他子模块使用
    sdkVersion = 28
    supportLibVersion = "28.0.0"
}

// 全局配置使用的时候
android {
    // rootProject.ext.property_name
    compileSdkVersion rootProject.ext.compileSdkVersion
    ...
} 
```

**模块build配置**

```json
plugins { //必须在第一行，配置gradle插件
    id 'com.android.application' //app
}

android { //配置android构建选项
    compileSdk 32 //编译应用程序时应使用的Android API级别

    defaultConfig { //声明默认配置。封装所有构建变体的默认设置和条目，并可以覆盖main/AndroidManifest中的某些属性
        applicationId "com.android" //包名
        minSdk 26 //最小支持的API级别
        targetSdk 32 //用于测试应用程序的API级别
        versionCode 1 //版本号
        versionName "1.0" //版本名

        testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner" //测试环境
    }

    //默认情况下，Android Studio使用minifyEnabled配置发布版本类型以启用代码收缩，并指定默认的Proguard混淆规则文件。
    buildTypes { //build 类型
        release { //发布版本
            minifyEnabled false //是否启动混淆 ture:打开   false:关闭
            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
//混淆规则文件
        }
    }
    compileOptions { //编译选项
        sourceCompatibility JavaVersion.VERSION_1_8 //编译.java文件的jdk版本
        targetCompatibility JavaVersion.VERSION_1_8 //确保生成的类文件与targetCompatible指定的VM兼容
    }

    //如果声明产品变种，还必须声明产品的维度，并将每个变种分配给一个维度。
    flavorDimensions "product" //声明产品的维度
    //创建应用程序的不同版本，这些版本可以使用自己的设置覆盖defaultConfig块。
    //产品变种是可选的，默认情况下构建系统不会创建它们。
    productFlavors {//配置多种产品变种
        A { //产品变种
            dimension "product" //产品维度
            applicationId 'com.android.a' //重新声明的包名
        }

        B { //产品变种
            dimension "product"
            applicationId 'com.android.b'
        }
    }
}

dependencies {//依赖
    implementation 'androidx.appcompat:appcompat:1.3.0'
    implementation 'com.google.android.material:material:1.4.0'
    implementation 'androidx.constraintlayout:constraintlayout:2.0.4'
    testImplementation 'junit:junit:4.13.2'
    androidTestImplementation 'androidx.test.ext:junit:1.1.3'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.4.0'
}
```

## 属性文件

位于项目的根目录下，可用于指定 Gradle 构建工具包本身的设置：

- `gradle.properties`

  可以在其中配置项目全局 Gradle 设置，如 Gradle 守护程序的最大堆大小。

  ```java
  //指定用于守护进程的JVM参数
  org.gradle.jvmargs=-Xmx2048m -Dfile.encoding=UTF-8
  //使用AndroidX软件包结构
  android.useAndroidX=true
  //启用每个库的R类的名称空间，以便其R类仅包含库本身中声明的资源，而不包含库依赖项中的资源，从而减少该库的R类别的大小
  android.nonTransitiveRClass=true
  ```

- `local.properties`

  为构建系统配置本地环境属性，其中包括：

  - `ndk.dir` - NDK 的路径。此属性已被弃用。NDK 的所有下载版本都将安装在 Android SDK 目录下的 `ndk` 目录中。
  - `sdk.dir` - SDK 的路径。
  - `cmake.dir` - CMake 的路径。
  - `ndk.symlinkdir` - 在 Android Studio 3.5 及更高版本中，创建指向 NDK 的符号链接，该符号链接的路径可比 NDK 安装路径短。

## 源代码集

Android Studio 按逻辑关系将每个模块的源代码和资源分组为源代码集。模块的 `main/` 源代码集包含其所有 build 变体共用的代码和资源。其他源代码集目录是可选的，在配置新的 build 变体时，Android Studio 不会自动为您创建这些目录。

- `src/main/`
  此源代码集包含所有 build 变体共用的代码和资源。

- `src/buildType/`
  创建此源代码集可加入特定 build 类型专用的代码和资源。

- `src/productFlavor/`
  创建此源代码集可加入特定产品变种专用的代码和资源。

  > 注意：如果配置 build 以组合多个产品变种，则可以为变种维度之间的每个产品变种组合创建源代码集目录：`src/productFlavor1ProductFlavor2/`

- `src/productFlavorBuildType/`
  创建此源代码集可加入特定 build 变体专用的代码和资源。

例如，如需生成应用的`fullDebug`版本，构建系统需要合并来自以下源代码集的代码、设置和资源：

- `src/fullDebug/`（build 变体源代码集）
- `src/debug/`（build 类型源代码集）
- `src/full/`（产品变种源代码集）
- `src/main/`（主源代码集）

文件使用顺序

如果不同源代码集包含同一文件的不同版本，Gradle 将按以下优先顺序决定使用哪一个文件

**build 变体 > build 类型 > 产品变种 > 主源代码集 > 库依赖项**

## 更改代码路径配置

使用 `sourceSets` 代码块更改 Gradle 为源代码集的每个组件收集文件的位置。`sourceSets` 代码块必须位于 `android` 代码块中。

```java
sourceSets {//代码源文件配置。可以列出多个目录，Gradle将使用所有目录来收集
    main {//封装main代码集的配置
        //更改Java源的目录。默认是 'src/main/java'.
        java.srcDirs = ['other/java']

        //资源文件源的目录。默认目录是“src/main/res”。
        //由于Gradle赋予这些目录同等的优先级，如果在多个目录中定义相同的资源，则在合并资源时会出现错误。
        res.srcDirs = ['other/res1', 'other/res2']

        //aidl文件源的目录。默认目录是“src/main/aidl”。
        aidl.srcDirs = ['src/main/aidl', 'other/aidl']

        //对于每个源集，您只能指定一个Android清单。
        //默认情况下，Android Studio为main源创建AndroidManifest，设置在src/main/目录中。
        manifest.srcFile 'other/AndroidManifest.xml'
        //注意：应该避免指定一个目录，该目录是指定的一个或多个其他目录的父目录。
    }

    //创建其他块配置的源代码集。
    androidTest {
        //如果源代码集的所有文件都位于单个根目录下，则可以使用setRoot属性指定该目录。
        //为源代码集收集源时，Gradle只查找相对于指定的根目录的位置。
        //例如，在将下面的配置应用于androidTest源集之后，Gradle仅在src/tests/Java/目录中查找Java源。
        setRoot 'src/tests'
    }
}
```

一个源目录只能属于一个源代码集。例如，不能同时与 `test` 和 `androidTest` 源代码集共享同一测试源代码。

一个源目录也只能有一个`AndroidManifest.xml`配置

## 依赖项

### 配置

在 `dependencies` 代码块内，可以从多种不同的依赖项配置中选择其一 来声明库依赖项。

- implementation
  Gradle 会将依赖项添加到编译类路径，并将依赖项打包到构建输出。不过，当模块配置 `implementation` 依赖项时，其他模块只有在运行时才能使用该依赖项。

  使用此依赖项配置代替 `api` 或 `compile`（已弃用）可以**显著缩短构建时间**，因为这样可以减少构建系统需要重新编译的模块数。例如，如果 `implementation` 依赖项更改了其 API，Gradle 只会重新编译该依赖项以及直接依赖于它的模块。大多数应用和测试模块都应使用此配置。

- api
  Gradle 会将依赖项添加到编译类路径和构建输出。当一个模块包含 `api` 依赖项时，会让 Gradle 了解该模块要以传递方式将该依赖项导出到其他模块，以便这些模块在运行时和编译时都可以使用该依赖项。

  此配置的行为类似于 `compile`（现已弃用），但使用它时应格外小心，只能对需要以传递方式导出到其他上游消费者的依赖项使用它。这是因为，如果 `api` 依赖项更改了其外部 API，Gradle 会在编译时重新编译所有有权访问该依赖项的模块。因此，拥有大量的 `api` 依赖项会显著增加构建时间。除非要将依赖项的 API 公开给单独的模块，否则库模块应改用 `implementation` 依赖项。

- compileOnly
  Gradle 只会将依赖项添加到编译类路径（也就是说，不会将其添加到构建输出）。如果您创建 Android 模块时在编译期间需要相应依赖项，但它在运行时可有可无，此配置会很有用。

  如果使用此配置，那么库模块必须包含一个运行时条件，用于检查是否提供了相应依赖项，然后适当地改变该模块的行为，以使该模块在未提供相应依赖项的情况下仍可正常运行。这样做不会添加不重要的瞬时依赖项，因而有助于减小最终 APK 的大小。此配置的行为类似于 `provided`（现已弃用）。

  > **注意：不能将 `compileOnly` 配置与 AAR 依赖项配合使用。**

- runtimeOnly
  Gradle 只会将依赖项添加到构建输出，以便在运行时使用。也就是说，不会将其添加到编译类路径。此配置的行为类似于 `apk`（现已弃用）。

- annotationProcessor
  如需添加对作为注解处理器的库的依赖，必须使用 `annotationProcessor` 配置将其添加到注解处理器的类路径。这是因为，使用此配置可以将编译类路径与注释处理器类路径分开，从而提高构建性能。如果 Gradle 在编译类路径上找到注释处理器，则会禁用避免编译功能，这样会对构建时间产生负面影响（Gradle 5.0 及更高版本会忽略在编译类路径上找到的注释处理器）。
  如果 JAR 文件包含以下文件，则 Android Gradle 插件会假定依赖项是注释处理器：
  `META-INF/services/javax.annotation.processing.Processor`。 如果插件检测到编译类路径上包含注解处理器，则会产生构建错误。

- lintChecks
  使用此配置可以添加您希望 Gradle 在构建项目时执行的 lint 检查。

  > **注意**：使用 Android Gradle 插件 3.4.0 及更高版本时，此依赖项配置不再将 lint 检查打包在 Android 库项目中。如需将 lint 检查依赖项包含在 AAR 库中，请使用下面介绍的 `lintPublish` 配置。

- lintPublish
  在 Android 库项目中使用此配置可以添加希望 Gradle 编译成 `lint.jar` 文件并打包在 AAR 中的 lint 检查。这会使得使用 AAR 的项目也应用这些 lint 检查。如果之前使用 `lintChecks` 依赖项配置将 lint 检查添加到已发布的 AAR 中，则需要迁移这些依赖项以改用 `lintPublish` 配置。

- 弃用的配置

  apk：Gradle 只会将依赖项添加到构建输出，以便在运行时使用。也就是说，不会将其添加到编译类路径。

  compile  Gradle 会将依赖项添加到编译类路径和构建输出。 将依赖项导出到其他模块。

  provided  Gradle 只会将依赖项添加到编译类路径（也就是说，不会将其添加到构建输出）。

### 依赖关系

项目中的依赖关系有三种：一是本地依赖(主要是对本地的jar包)，二是模块依赖，三是远程依赖；

## 签名配置

除非明确定义发布 build 的签名配置，否则 Gradle 不会为该 build 的 APK 或 AAB 文件签名。

```java
//buildTypes {
//  release {
//     signingConfig signingConfigs.release
//  }
//}
signingConfigs {
    release {
        storeFile file("releasekey.keystore")
            storePassword "password"
            keyAlias "ReleaseKey"
            keyPassword "password"
    }
}
```

如需从环境变量获取这些密码，请添加以下代码：

```java
storePassword System.getenv("KSTOREPWD")
keyPassword System.getenv("KEYPWD")
```

## 术语

- build 类型

  build 类型定义 Gradle 在构建和打包应用时使用的某些属性，通常针对开发生命周期的不同阶段进行配置。例如，调试 build 类型会启用调试选项，并会使用调试密钥为应用签名；而发布 build 类型则可能会缩减应用大小、对应用进行混淆处理，并使用发布密钥为应用签名以进行分发。

- 产品变种 (Product flavor)

  产品变种代表您可以向用户发布的不同应用版本，如免费版应用和付费版应用。可以自定义产品变种以使用不同的代码和资源，同时共享并重用所有应用版本共用的部分。产品变种是可选的，必须手动创建。

- build 变体

  build 变体是 build 类型与产品变种的交叉产物，也是 Gradle 用来构建应用的配置。利用 build 变体，可以在开发期间构建产品变种的调试版本，或者构建产品变种的已签名发布版本以供分发。虽然无法直接配置 build 变体，但可以配置组成它们的 build 类型和产品变种。创建额外的 build 类型或产品变种也会产生额外的 build 变体。

- 清单 (Manifest) 条目

  可以在 build 变体配置中为清单文件的某些属性指定值。这些 build 值会覆盖清单文件中的现有值。如果要为应用生成多个变体，让每一个变体都具有不同的应用名称、最低 SDK 版本或目标 SDK 版本，便可运用这一技巧。当存在多个清单时，Gradle 会合并清单设置。

- 依赖项

  构建系统会管理来自本地文件系统以及来自远程代码库的项目依赖项。这样一来，您就不必手动搜索、下载依赖项的二进制文件包以及将它们复制到项目目录中。

- 签名

  构建系统既允许在 build 配置中指定签名设置，也可以在构建流程中自动为应用签名。构建系统通过已知凭据使用默认密钥和证书为调试版本签名，以避免在构建时提示输入密码。除非为此 build 明确定义签名配置，否则，构建系统不会为发布版本签名。

- 代码和资源缩减

  构建系统允许为每个 build 变体指定不同的 ProGuard 规则文件。在构建应用时，构建系统会应用一组适当的规则以使用其内置的缩减工具（如 R8）缩减代码和资源 。

- 多 APK 支持

  通过构建系统可以自动构建不同的 APK，并让每个 APK 只包含特定屏幕密度或应用二进制接口 (ABI) 所需的代码和资源。

## 7.0+更新.

| 工具            | 最低版本 | 默认版本     |
| :-------------- | :------- | :----------- |
| Gradle          | 7.0.2    | 7.0.2        |
| SDK Build Tools | 30.0.2   | 30.0.2       |
| NDK             | 不适用   | 21.4.7075529 |
| JDK             | 11       | 11           |

**依赖项配置变更**

- `compile`：根据用例，该配置已被 `api` 或 `implementation`替换。同样适用于 *Compile 变体，例如 `debugCompile`。
- `provided`：该配置已被 `compileOnly`替换。同样适用于 *Provided 变体，例如 `releaseProvided`。
- `apk`：该配置已被 `runtimeOnly` 替换。
- `publish`：该配置已被`runtimeOnly`替换。

**移除属性和Task**

`android.enableBuildCache` 属性、`android.buildCacheDir` 属性移除。

`cleanBuildCache` 任务---我们在Gradle面板上将看不到这个Task任务了。

**针对依赖库提高了lint性能检查**

```json
// 在编译时针对我们依赖的其他库进行了更高的lint
android {
  ...
  lintOptions {
    checkDependencies true
  }
}
 
//如果你的项目是 build.gradle.kts。就用下面的配置
android {
  ...
  lint {
    isCheckDependencies = true
  }
}
```

**针对缺少类警告**

在进行编译时，R8编译器将会更精确的查找依赖项里面的定义或者引用的类是否存在。

不存在的时候会输出：`R8: Missing class: java.lang.instrument.ClassFileTransformer`

这样，我们就不用在运行时崩溃了才知道有哪些类没有被依赖了。

但我们要忽略该警告，可以通过在proguard-rules.pro 文件中，如下配置，来告诉Gradle 忽略缺少类的警告。

```diff
-dontwarn java.lang.instrument.ClassFileTransformer
```

**settings.gradle增加插件配置**

```java
pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
```

由于项目中配置了私服maven，且使用http地址，需要在maven中配置`allowInsecureProtocol = true`

**build.gradle的变化**

于项目中使用了`arouter`插件，故仍旧使用buildscript+plugins同时存在的模式，同时`apply from`其他的gradle文件，注意`顺序不能错`，否则会提示错误的。

```json
buildscript {
    ext {
        kotlin_version = '1.6.10'
    }
    dependencies {
        classpath "com.alibaba:arouter-register:1.0.2"
    }
}
plugins {
    id 'com.android.application' version '7.3.0-alpha04' apply false
    id 'com.android.library' version '7.3.0-alpha04' apply false
    id 'org.jetbrains.kotlin.android' version '1.6.10' apply false
}
apply from: "config.gradle"
```

**app或者module内的build.gradle的变化**

部分关键词进行了缩短优化：`apply plugin`变更为`plugins`，`apply from`还可以正常使用

```java
plugins {
    id 'com.android.application'
    id 'org.jetbrains.kotlin.android'
    id 'kotlin-kapt'
}
apply from 'xxx.gradle'

android {
    compileSdk 31
    defaultConfig {

        versionCode 1
        versionName '1.0'
        minSdk 21
        targetSdk 31

	}
	...
}
```

## 引用本地包

1. Project视图中，在app目录下新建libs文件夹，放入下载的第三方组件包。Android视图下，会显示在jniLibs文件夹下。

2. 7.0之前:

   ```json
   android { repositories { flatDir { dirs 'libs' } } }
   dependecies { 
       implementation fileTree(dir: 'libs', include: ['*.jar', '*.aar'])
       implementation(name:'xxx', ext: 'aar) //方式一
       implementation "com.xxx.xxx@aar" //方式二
   }
   ```

3. 7.0以后

   ```json
   dependecies { 
       implementation fileTree(dir: 'libs', include: ['*.jar', '*.aar'])
       implementation files('libs/xxx.aar')
   }
   ```

4. 错误：Null extracted folder for artifact app\nuisdk-release.aar.

   原因：包的路径错误。

## 插件引用

1. 在最外层的build.gradle里增加classpath的引用（**greendao**）

   ```java
   buildscript {
       repositories {
           jcenter()
       }
       dependencies {
           classpath 'com.android.tools.build:gradle:7.3.1'//你用到的gradle版本号
           classpath 'org.greenrobot:greendao-gradle-plugin:3.3.0'
       }
   }
   
   // Top-level build file where you can add configuration options common to all sub-projects/modules.
   plugins {
       id 'com.android.application' version '7.3.1' apply false
       id 'com.android.library' version '7.3.1' apply false
   }
   ```

2. 修改settings.gradle里的配置，增加阿里云的jcenter镜像，并修改repositoriesMode的参数，改为**RepositoriesMode.PREFER_SETTINGS**（原本的参数为RepositoriesMode.FAIL_ON_PROJECT_REPOS）

   ```java
   pluginManagement {
       repositories {
           //阿里云镜像
           maven { url 'https://maven.aliyun.com/repository/google' }
           maven { url 'https://maven.aliyun.com/repository/public' }
           maven { url 'https://maven.aliyun.com/repository/gradle-plugin' }
   
           gradlePluginPortal()
           google()
           mavenCentral()
       }
   }
   
   dependencyResolutionManagement {
       repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
       repositories {
           google()
           mavenCentral()
       }
   }
   rootProject.name = "Client"
   include ':app'
   ```

3. 在app目录下的build.gradle里增加插件引用

   ```json
   plugins {
       id 'com.android.application'
       id 'org.greenrobot.greendao'
   }
   ```

# 版本更新

应用更新的方式：**使用OkHttp实现应用版本更新**，**使用三方服务进行应用版本更新**

## OkHttp

1. 首先检测是否有新的版本

   ```java
   /**
    * 检测是否有新的版本，如有则进行下载更新：
    * 1. 请求服务器, 获取数据；2. 解析json数据；3. 判断是否有更新；4. 弹出升级弹窗或直接进入主页面
    */
   private void checkVersion() {
       showLaunchInfo("正在检测是否有新版本...", false);
       String checkUrl = "http://localhost:8080/AndroidAPK/AndroidUpdate.json";
    
       OkHttpClient okHttpClient = new OkHttpClient();//创建 OkHttpClient 对象
       Request request = new Request.Builder().url(checkUrl).build();//创建 Request
       okHttpClient.newCall(request).enqueue(new Callback() {//发送请求
           @Override
           public void onFailure(@NotNull Call call, @NotNull IOException e) {
               Log.w(TAG, "onFailure: e = " + e.getMessage());
               mProcess = 30;
               showLaunchInfo("新版本检测失败，请检查网络！", true);
           }
    
           @Override
           public void onResponse(@NotNull Call call, @NotNull Response response) {
               try {
                   Log.w(TAG, "onResponse: response = " + response);
                   mProcess = 30;
                   final ResponseBody responseBody = response.body();
                   if (response.isSuccessful() && responseBody != null) {
                       final String responseString = responseBody.string();
                       Log.w(TAG, "onResponse: responseString = " + responseString);
                       //解析json
                       final JSONObject jo = new JSONObject(responseString);
                       final String versionName = jo.getString("VersionName");
                       final int versionCode = jo.getInt("VersionCode");
                       final String versionDes = jo.getString("VersionDes");
                       final String versionUrl = jo.getString("VersionUrl");
                       //本地版本号和服务器进行比对, 如果小于服务器, 说明有更新
                       if (BuildConfig.VERSION_CODE < versionCode) {
                           //本地版本小于服务器版本，存在新版本
                           showLaunchInfo("检测到新版本！", false);
                           progressBar.setProgress(mProcess);
                           //有更新, 弹出升级对话框
                           showUpdateDialog(versionDes, versionUrl);
                       } else {
                           showLaunchInfo("该版本已是最新版本，正在初始化项目...", true);
                       }
                   } else {
                       showLaunchInfo("新版本检测失败，请检查服务！", true);
                   }
               } catch (Exception e) {
                   e.printStackTrace();
                   showLaunchInfo("新版本检测出现异常，请检查服务！", true);
               }
           }
       });
   }
   ```

2. 然后进行APK文件下载

   ```java
   private void downloadNewApk(String apkName) {
       showLaunchInfo("检测到新版本，正在下载...", false);
       final File downloadDir = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
       if (downloadDir != null && downloadDir.exists() && downloadDir.isDirectory()) {
           //删除(/storage/emulated/0/Android/data/包名/files/Download)文件夹下的所有文件，避免一直下载文件堆积
           File[] files = downloadDir.listFiles();
           if (files != null) {
               for (final File file : files) {
                   if (file != null && file.exists() && file.isFile()) {
                       boolean delete = file.delete();
                   }
               }
           }
       }
       //显示进度条
       final ProgressDialog dialog = new ProgressDialog(this);
       dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);//水平方向进度条, 可以显示进度
       dialog.setTitle("正在下载新版本...");
       dialog.setCancelable(false);
       dialog.show();
       //APK文件路径
       final String url = "http://localhost:7090/AndroidAPK/" + apkName;
       Request request = new Request.Builder().url(url).build();
       new OkHttpClient().newCall(request).enqueue(new Callback() {
           @Override
           public void onFailure(@NotNull Call call, @NotNull IOException e) {
               e.printStackTrace();
               String strFailure = "新版本APK下载失败";
               showLaunchInfo(strFailure, false);
               showFailureDialog(strFailure, apkName);
               dialog.dismiss();
           }
    
           @Override
           public void onResponse(@NotNull Call call, @NotNull Response response) {
               try {
                   final ResponseBody responseBody = response.body();
                   if (response.isSuccessful() && responseBody != null) {
                       final long total = responseBody.contentLength();
                       final InputStream is = responseBody.byteStream();
                       final File file = new File(downloadDir, apkName);
                       final FileOutputStream fos = new FileOutputStream(file);
    
                       int len;
                       final byte[] buf = new byte[2048];
                       long sum = 0L;
                       while ((len = is.read(buf)) != -1) {
                           fos.write(buf, 0, len);
                           sum += len;
                           float downloadProgress = (sum * 100F / total);
                           dialog.setProgress((int) downloadProgress);//下载中，更新进度
                           progressBar.setProgress(mProcess + (int) (downloadProgress * 0.7));
                       }
                       fos.flush();
                       responseBody.close();
                       is.close();
                       fos.close();
                       installAPKByFile(file);//下载完成，开始安装
                   } else {
                       String strFailure = "新版本APK获取失败";
                       showLaunchInfo(strFailure, false);
                       showFailureDialog(strFailure, apkName);
                   }
               } catch (Exception e) {
                   e.printStackTrace();
                   String strException = "新版本APK下载安装出现异常";
                   showLaunchInfo(strException, false);
                   showFailureDialog(strException, apkName);
               } finally {
                   /*正常应该在finally中进行关流操作，以避免异常情况时没有关闭IO流，导致内存泄露
                    *因为本场景下异常情况可能性较小，为了代码可读性直接在正常下载结束后关流
                    */
                   dialog.dismiss();//dialog消失
               }
           }
       });
   }
   ```

3. 最后安装新的APK。

   ```java
   /**
    * 7.0以上系统APK安装
    */
   private void installAPKByFile(File file) {
       showLaunchInfo("新版本下载成功，正在安装中...", false);
       Intent intent = new Intent(Intent.ACTION_VIEW);
       //intent.putExtra("pwd", "soft_2694349");//根据密码判断升级文件是否允许更新
       intent.addCategory(Intent.CATEGORY_DEFAULT);
       Uri uri = FileProvider.getUriForFile(this, "com.lxb.demo0325.fileProvider", file);
       //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
       intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
       intent.setDataAndType(uri, "application/vnd.android.package-archive");
       startActivityForResult(intent, REQUEST_INSTALL);
   }
   ```

参考示例：C:\Users\lonch\Documents\codeup\项目参考\AppUpdate-Android。

服务端配置的更新文件，可以是Json形式的：

```json
{
    "VersionName":"3.1.1.2",
    "VersionCode":545,
    "VersionDes":"发现新版本, 赶紧更新吧!",
    "VersionUrl":"ApkName"
}
```

## Bugly

腾讯Bugly是腾讯为开发者提供的一种三方服务，主要包含有：异常上报、运营统计、应用升级等三大功能，对于我们可以使用其应用升级和异常上报功能，官网地址为：https://bugly.qq.com/v2/。

首先使用Bugly功能需要在客户端集成其相关SDK。

**bugly功能介绍：**

1. 应用升级：将我们更新的apk文件上传至bugly后台，设置下发策略（其中可选强制升级和下发上限等，也可填写更新说明），客户端检测到bugly后台有新版本就会进行下载。

   应用升级官网介绍连接：https://bugly.qq.com/docs/introduction/app-upgrade-introduction/?v=20180709165613

2. 异常上报：客户端集成后，对于线上用户使用过程中的出现的异常问题，会汇总至bugly后台自动进行汇总分类，便于分析用户端出现的问题。（此日志在用户端设备上），bugly的推出初衷就是汇总异常信息。

   异常上报官网介绍连接：https://bugly.qq.com/docs/introduction/bugly-introduction/?v=20181014122344#_3

bugly优缺点分析：

优点：

1、应用升级使用第三方服务后不需要占用我们自己的服务器资源，升级并发量也有保障。

2、异常上报可以监测用户端异常情况，这部分我们平时自己较难检测。

缺点：

1、信息安全问题：客户端需集成相关SDK并且需要将我们的APK文件上传至三方服务器上；

2、更新应用时平板终端需要连接bugly后台外网；

3、我们各个地区的apk文件需要进行区分,避免下载时串线（都从bugly后台下载）

# 问题解决

项目运行中的问题：

1. 问题：项目中great-front-component组件未加载成功

   解决方案：检查settings.gradle文件中依赖关系，great-front-component的路径是否正确，重启Android Studio。

2. 错误信息：

   ```
   Android Studio is using the following JDK location when running Gradle:
       /Applications/Android Studio.app/Contents/jre/jdk/Contents/Home
       Using different JDK locations on different processes might cause Gradle to
       spawn multiple daemons, for example, by executing Gradle tasks from a terminal
       while using Android Studio.
   ```

   原因：电脑上有jdk1.8 和 Android sdk，然后在Android Studio中没有找到你的jdk，使用了Android sdk，而程序中gradle需要使用jdk，因此需要明确指定下。

   解决方案：使用File -> Settings -> Build Toos -> Gradle进行设置，或者：

   1. Right click on app in project window
   2. open module settings
   3. SDK location -> Gradle Settings ->  Gradle JDK

3. 错误信息：Could not reserve enough space for 1572864KB object heap

   原因：JVM没有足够的内存空间

   解决方案：在Android Studio中gradle.properties文件中修改：org.gradle.jvmargs=-Xmx512m  -XX:MaxPermSize=512m

4. 问题：项目Build时报错乱码

   打开**Help -> Edit Custom VM Options**，文件的最后一行添加 **-Dfile.encoding=UTF-8** ；

   在**File -> Invalidate Caches** ，点击**Invalidate and Restart**清除缓存并重启即可。

5. 错误信息：可以生成，但无法直接运行，可以把生成的apk拖到虚拟机上运行。52 = Java8，55 = Java11.

   ```
   com/android/tools/idea/gradle/run/OutputBuildAction has been compiled by a more recent version of the Java Runtime (class file version 55.0), this version of the Java Runtime only recognizes class file versions up to 52.0
   
   * Try:
   Run with --info or --debug option to get more log output. Run with --scan to get full insights.
   
   * Exception is:
   java.lang.UnsupportedClassVersionError: com/android/tools/idea/gradle/run/OutputBuildAction has been compiled by a more recent version of the Java Runtime (class file version 55.0), this version of the Java Runtime only recognizes class file versions up to 52.0
   	at org.gradle.tooling.internal.provider.serialization.DefaultPayloadClassLoaderRegistry$2.resolveClass(DefaultPayloadClassLoaderRegistry.java:93)
   	at org.gradle.internal.concurrent.ManagedExecutorImpl$1.run(ManagedExecutorImpl.java:48)
   	at org.gradle.internal.concurrent.ThreadFactoryImpl$ManagedThreadRunnable.run(ThreadFactoryImpl.java:56)
   ```

   原因：Java版本错误。Mac和Windows不一样。

   解决方案：检查gradle中的java版本是否选择1.8，然后在build.gradle文件中，compileOptions中使用 **JavaVersion.VERSION_1_8** 替换 1.8 。（**未解决**）

6. Java1.8环境变量的切换配置（Android运行与之无关，都是内置）

   **配置变量 Path** **新建(N)** ：

   ​    %JAVA_HOME%\bin;

   ​    %JAVA_HOME%\jre\bin;

   **系统变量(S)** 中，点击 **新建(W).**

   1. 变量名(N)：JAVA_HOME	 变量值(V)：下载好的 jdk 安装目录 C:\Program Files\Java\jdk1.8.0_361
   2. 变量名(N)：CLASS_PATH	变量值(V)：.;%JAVA_HOME%\lib;%JAVA_HOME%\lib\tools.jar;

7. 错误信息：Gradle expiring daemon because jvm heap space is exhausted

   原因：项目大的时候gradle构建特别慢，或者最后内存溢出

   解决方案：调整JVM的大小，并开启多线程并行构建功能

   ```yaml
   #===========编译设置===============#
   #开启线程守护，第一次编译时开线程，之后就不会再开
   org.gradle.daemon=true
   #配置编译时的虚拟机大小
   org.gradle.jvmargs=-Xmx3096m -XX:MaxPermSize=512m -XX:+HeapDumpOnOutOfMemoryError -Dfile.encoding=UTF-8
   #开启并行编译，相当于多条线程构建
   org.gradle.parallel=true
   #启用新的孵化模式
   org.gradle.configureondemand=true
   ```

8. 错误信息：Using insecure protocols with repositories, without explicit opt-in, is unsupported. Switch Maven

   原因：gradle为了安全考虑，默认禁用使用非官方的中央仓库（比如阿里云），所以如果确认信任该仓库，需要显示声明信任它

   解决方案：在仓库前添加关键字：`allowInsecureProtocol = true`，或将仓库的http换成https

9. 运行项目报错，错误信息：Manifest Merger failed with multiple errors

   查找原因的方法如下：打开AndroidManifest.xml，点击左下角的“Merged Manifest” Tab。

10. 问题：Android Studio中的模拟器一直在加载（Connecting to the emulator···）

    解决方案：调整模拟器的设置，把Boot Option中的Quick Boot改成**Cold Boot**，关掉avd，再启动就可以了。

11. 错误：Lambda expressions are allowed only at source level 1.8 or above

    原因：Lambda表达式仅允许在源级别 1.8 或更高版本。

    解决方案：删除引用 org.greenrobot.greendao.annotation.NotNull

12. 错误：android.content.res.Resources$NotFoundException

    原因：指向对应XML文件报错。使用的文件都为port文件夹下的文件，**xml资源文件后有(port)**.

    layout-port：为纵向方向必须更改的窗口小部件的布局

    layout-land：对于横向方向必须更改的窗口小部件的布局

    layout：为任意布局

    解决方案：创建一个新的xml文件，不进行任何指定，他会自适应方向。

13. 错误：Could not find method dependencyResolutionManagement() for arguments

    原因：配置问题（sdk platforms 版本），修改根目录的build.gradle。

14. [This view is not constrained, it only has designtime positions, so it will jump to (0,0) unless you_一口吃不成胖子的博客-CSDN博客](https://blog.csdn.net/BSSYNHDJZMH/article/details/79728625)

15. [Android Studio项目中三种依赖的添加方式 - 凭栏倚窗 - 博客园 (cnblogs.com)](https://www.cnblogs.com/remote/p/10169737.html#:~:text=Android Studio项目中三种依赖的添加方式,通常一个AS项目中的依赖关系有三种，一是本地依赖(主要是对本地的jar包)，二是模块依赖，三是远程依赖；添加这些依赖的目的在于上我们想要在项目的某一个模块中使用其中的功能，比如okttp这个网络框架库，如果我们想要在项目的app模块下使用这个库的功能，则需要在app模块的build.gradle文件中添加相应的依赖，Gradle插件首先会在本地文件系统上去查找是否存在，如果不存在，然后会到全局的build.gradle文件中指定的代码仓库中去获取，需要联网%2C下面是指定的项目可以在哪些代码仓库当中去获取开源项目 全局的build.gradle文件中的部分)

