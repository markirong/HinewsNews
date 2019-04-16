package com.hinews;
import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import com.hinews.utils.ToastUtil;
import com.socks.library.KLog;
/**
 * 2018，11月开始markirong . wonderful shit!
 */
public class hinewsApplication extends Application {

    private static Context mContext;
    private static Thread mMainThread;//主线程
    private static long mMainThreadId;//主线程id
    private static Looper mMainLooper;//循环队列
    private static Handler mHandler;//主线程Handler

    @Override
    public void onCreate() {
        super.onCreate();
        if (mContext == null){
            mContext = getApplicationContext();
            init();
        }

    }

    private void init() {
        KLog.init(true,"charco");//初始化KLog
        ToastUtil.init(getApplicationContext());
        mMainThread = Thread.currentThread();
        mMainThreadId = android.os.Process.myTid();
        mHandler = new Handler();
    }

    public static Context getContext(){
        return mContext;
    }

    public static Thread getMainThread() {
        return mMainThread;
    }


    public static long getMainThreadId() {
        return mMainThreadId;
    }


    public static Looper getMainThreadLooper() {
        return mMainLooper;
    }


    public static Handler getMainHandler() {
        return mHandler;
    }
}
