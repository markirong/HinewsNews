package com.hinews.utils;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

    public class OkHttpUtils {
        private static OkHttpUtils instance;
        private final OkHttpClient mOkHttpClient;
//        private final Handler mHandler;

        public OkHttpUtils() {
            mOkHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS)
                    .build();

//            mHandler = new Handler(Looper.getMainLooper());
        }

        public static OkHttpUtils getInstance() {
            if (instance == null) {
                synchronized (OkHttpUtils.class) {
                    if (instance == null) {
                        instance = new OkHttpUtils();
                    }
                }
            }
            return instance;
        }

        /**
         * get
         */
        public void doGet(String url, final ICallBack iCallBack) {
            Request request = new Request.Builder()
                    .url(url).build();
            mOkHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, final IOException e) {
                    if (iCallBack != null) {
//                        mHandler.post(new Runnable() {
//                            @Override
//                            public void run() {
//                                iCallBack.failed(e);
//                            }
//                        });
                    }
                }
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response != null && response.isSuccessful()) {
                        final String json = response.body().string();
                        if (iCallBack != null) {
//                            mHandler.post(new Runnable() {
//                                @Override
//                                public void run() {
//                                    iCallBack.success(json);
//                                }
//                            });
                        }
                    }
                }
            });
        }

        /**
         * post
         */
        public void doPost(String url, Map<String, String> map, final ICallBack iCallBack) {
            FormBody.Builder builder = new FormBody.Builder();
            if (map != null) {
                for (String key : map.keySet()) {
                    builder.add(key, map.get(key));
                }
            }
            FormBody formBody = builder.build();
            Request request = new Request.Builder()
                    .url(url)
                    .post(formBody)
                    .build();
            mOkHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, final IOException e) {
                    Log.i("yz", e.getMessage());



                    if (iCallBack != null) {
//                        mHandler.post(new Runnable() {
//                            @Override
//                            public void run() {
                                iCallBack.failed(e);
//                            }
//                        });
                    }
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response != null && response.isSuccessful()) {
                        final String json = response.body().string();
                        if (iCallBack != null) {
//                            mHandler.post(new Runnable() {
//                                @Override
//                                public void run() {
                                    iCallBack.success(json);
//                                }
//                            });
                        }
                    }
                }
            });
        }


        public interface ICallBack {
            void failed(Exception e);
            void success(String json);
        }
    }