## 功能特性

+ 断点续传
+ 支持多线程 *(目前版本仅支持单任务单线程，多任务才多线程，未来会继续完善单任务的多线程执行)*

## 使用本项目的理由

- **可靠稳定** *(我们拥有近百万用户的某个app项目，迭代了近二十个版本，该下载模块久经考验,工作正常)*
- **体积很小** *(总计只有数十个java文件)*
- **无其他依赖** *(仅使用sdk本身的api，没有依赖任何第三方库)*
- **接入方式简单**

![download_demo](https://github.com/yaowen369/DownloadHelper/blob/master/docs/img/download_three.gif)

## 导入项目
```
implementation 'com.yaoxiaowen:download:1.4.1'
```

## 使用方式
#### 1, **注册**
`AndroidManifest.xml`中需要注册权限和`service`.(读写文件的权限要在代码中动态申请，不要忘记这点了)
```
    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />

```

#### 2,  **开始/暂停/重启 下载任务。**

+ 开始

```java
//先获得这个单例对象
        mDownloadHelper = DownloadHelper.getInstance();

//执行两个下载任务
        mDownloadHelper.addTask(firstUrl, firstFile, FIRST_BC_ACTION)
                .addTask(secondUrl, secondFile, SECOND_BC_ACTION)
                .submit(this);

//当然，下面这样分开写，自然也可以
       mDownloadHelper.addTask(firstUrl, firstFile, FIRST_BC_ACTION)
                .submit(this);

        mDownloadHelper.addTask(secondUrl, secondFile, SECOND_BC_ACTION)
                .submit(this);
```

+ 暂停
```java
        mDownloadHelper.pauseTask(firstUrl, firstFile, FIRST_BC_ACTION)
                .submit(this);
```

+ 重启

当下载任务被暂停/结束后，想要重新启动时，和开始下载操作相同，直接 `addTask().submit`即可从上一次下载断点处开始下载。


> 当我们执行多个下载任务时，内部会维护任务列表。线程池维护多个线程，执行下载任务。 单个任务只采用单一线程执行任务。



#### 3, **广播接收信息**
我们通过广播来进行通讯。广播携带下载文件的相关信息。

以下情况会发送广播。
> + 当下载任务的状态发生改变时。(比如开始，准备，失败,暂停等状态改变).
> + 在 **LOADING(下载中)** 状态时，默认每间隔1s就发送广播。

###### 注册广播
```java
       //广播不要忘记注册和反注册。
        IntentFilter filter = new IntentFilter();
        filter.addAction(FIRST_BC_ACTION);
        registerReceiver(receiver, filter);
```
###### 接收信息

```java
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (null != intent) {
                switch (intent.getAction()) {
                    case FIRST_BC_ACTION:{
                        /**
                         * 我们接收到的FileInfo对象，包含了下载文件的各种信息。
                         * 然后我们就可以做我们想做的事情了。
                         * 比如更新进度条，改变状态等。
                         */
                        com.yaoxiaowen.download.FileInfo fileInfo =
                                (FileInfo) intent.getSerializableExtra(
                                        com.yaoxiaowen.download.DownloadConstant.DOWNLOAD_EXTRA);

                    }
                    break;
                    default:
                }
            }
        }//end of "onReceive(..."
    };

```

> 使用方式很简单，这里有两个demo。[执行三个下载任务](https://github.com/yaowen369/DownloadHelper/blob/master/sample/src/main/java/com/yaoxiaowen/download/sample/MainActivity.java)  或 [执行一个下载任务](https://github.com/yaowen369/DownloadHelper/blob/master/sample/src/main/java/com/yaoxiaowen/download/sample/SimpleMainActivity.java)

## API和相关常量
#### **1. DownloadHelper.java**
```java
package com.yaoxiaowen.download;
/**
 * 该类采用 单例 设计模式
 */
public class DownloadHelper {
    /**
     * 提交  下载/暂停  等任务.(提交就意味着开始执行生效)
     * @param context
     */
    public synchronized void submit(Context context);

    /**
     *  添加 新的下载任务
     *
     * @param url  下载的url
     * @param file  存储在某个位置上的文件
     * @param action  下载过程会发出广播信息.该参数是广播的action
     * @return   DownloadHelper自身 (方便链式调用)
     */
    public DownloadHelper addTask(String url, File file, @Nullable String action);

    /**
     *  暂停某个下载任务
     *
     * @param url   下载的url
     * @param file  存储在某个位置上的文件
     * @param action  下载过程会发出广播信息.该参数是广播的action
     * @return DownloadHelper自身 (方便链式调用)
     */
    public DownloadHelper pauseTask(String url, File file, @Nullable String action);
}

```

#### **2. FileInfo.java**

```java
package com.yaoxiaowen.download;
/**
 * 文件信息，javaBean形式
 */
public class FileInfo implements Serializable{
    private String id;   //文件的唯一标识符 (url+文件存储路径)
    private String downloadUrl;   //下载的url
    private String filePath;  //文件存放的路径位置
    private long size;   //文件的总尺寸
    private long downloadLocation; // 下载的位置(就是当前已经下载过的size，也是断点的位置)

    @IntRange(from = DownloadStatus.WAIT, to = DownloadStatus.FAIL)
    private int downloadStatus = DownloadStatus.PAUSE;   //下载的状态信息
}
```

#### **3. DownloadConstant.java**
```java
package com.yaoxiaowen.download;
public class DownloadConstant {
    /**
     * 下载过程会通过发送广播, 广播通过intent携带文件数据的 信息。
     * intent 的key值就是该字段
     * eg : FileInfo fileInfo = (FileInfo) intent.getSerializableExtra(DownloadConstant.EXTRA_INTENT_DOWNLOAD);
     */
    public static final String EXTRA_INTENT_DOWNLOAD = "yaoxiaowen_download_extra";
}
```


#### **4. DownloadConstant.java**

```java
package com.yaoxiaowen.download;
/**
 * 标示着 下载过程中的状态
 */
public class DownloadStatus {
    // Answer to the Ultimate Question of Life, The Universe, and Everything is 42
    public static final int WAIT = 42;       //等待
    public static final int PREPARE = 43;    //准备
    public static final int LOADING = 44;    //下载中
    public static final int PAUSE = 45;      //暂停
    public static final int COMPLETE = 46;   //完成
    public static final int FAIL = 47;       //失败
}
```

## TODO
+ 对于单一任务的多线程执行。
+ 能够监听网络状态，自动暂停和恢复。
+ 多线程的加锁方式的优化，提高效率。

## 技术原理简介
   所谓断点下载，其实也不复杂。注意以下几点内容。
   + `httpURLconnection#setRequestProperty`方法通过设置，可以从服务器指定位置读取数据。eg:`conn.setRequestProperty("Range", "bytes=" + 500 + "-" + 1000);`
   + 普通的`File`对象并不支持从指定位置写入数据，我们需要使用`RandomAccessFile`来实现从指定位置给文件写入数据的功能。`void seek(long offset)`
   + 每次下载时，需要记录断点的位置信息。(本项目中使用`sqlite`数据库来实现持久化)
   + 通过`ThreadPoolExecutor`来维护线程池。

> 关于多线程断点续传下载的文章，网上很多了，大家可以参考。


## other
+ *使用过程中有什么问题，可以提交issues或联系本人，尽力予以解决。*
